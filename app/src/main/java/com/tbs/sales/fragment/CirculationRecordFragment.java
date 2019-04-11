package com.tbs.sales.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.tbs.sales.R;
import com.tbs.sales.adapter.CirculationRecordAdapter;
import com.tbs.sales.bean.CirculationRecordBean;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.MoveDistanceUtils;
import com.tbs.sales.utils.OkHttpUtils;
import com.tbs.sales.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Mr.Wang on 2019/3/28 17:50.
 */
@SuppressLint("ValidFragment")
public class CirculationRecordFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private Gson gson;
    private List<CirculationRecordBean> beanList;
    private LinearLayoutManager layoutManager;
    private CirculationRecordAdapter adapter;
    private int co_id;
    private int mPage = 1;
    private int pageSize = 20;
    private boolean iMove;//屏幕滑动距离
    private int num = 0;//记录list数量

    @SuppressLint("ValidFragment")
    public CirculationRecordFragment(int co_id) {
        this.co_id = co_id;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circulation_record, null);
        unbinder = ButterKnife.bind(this, view);
        gson = new Gson();
        initData();
        initRequest();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        beanList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setOnScrollListener(onScrollListener);
        new MoveDistanceUtils().setOnMoveDistanceListener(recyclerView, new MoveDistanceUtils.OnMoveDistanceListener() {
            @Override
            public void onMoveDistance(boolean b) {
                iMove = b;
            }
        });
    }

    //上拉加载更多
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (adapter != null) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && layoutManager.findLastVisibleItemPosition() + 1 == adapter.getItemCount()) {
                    if (iMove) {
                        LoadMore();
                    }
                }
            }
        }
    };

    //加载更多数据
    private void LoadMore() {
        mPage++;
        initRequest();
    }

    /**
     * 初始化网络请求
     */
    private void initRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppInfoUtils.getToekn(getActivity()));
        params.put("co_id", co_id);
//        params.put("page", mPage);
//        params.put("page_size", pageSize);
        OkHttpUtils.get(Constant.SALE_GETCOMOPERATELIST, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String code = jsonObject.optString("code");
                    if (code.equals("0")) {
                        JSONObject data = jsonObject.optJSONObject("data");
                        JSONArray jsonArray = data.getJSONArray("list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            CirculationRecordBean recordBean = gson.fromJson(jsonArray.get(i).toString(), CirculationRecordBean.class);
                            beanList.add(recordBean);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (adapter == null) {
                                    adapter = new CirculationRecordAdapter(getActivity(), beanList);
                                    recyclerView.setAdapter(adapter);
                                }
//                                if (num == beanList.size()) {
//                                    if (mPage != 1) {
//                                        ToastUtils.toastShort(getActivity(), "暂无更多数据");
//                                    }
//                                }else {
//                                    if (mPage != 1) {
//                                        adapter.notifyItemInserted(beanList.size() - pageSize + 1);
//                                    }
//                                }
//                                num = beanList.size();

                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
