package com.tbs.sales.fragment;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.google.gson.Gson;
import com.tbs.sales.R;
import com.tbs.sales.adapter.FollowUpRecordAdapter;
import com.tbs.sales.bean.ComFollowRecordingBean;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Mr.Wang on 2019/3/28 17:49.
 * 跟进记录
 */
@SuppressLint("ValidFragment")
public class FollowUpRecordFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private FollowUpRecordAdapter adapter;
    private List<ComFollowRecordingBean> beanList;
    private Gson gson;
    private Timer timer;
    /**
     * 进度条
     */
    private boolean isSeekBarChanging;
    private SimpleDateFormat format;
    private int mPosition = 0;
    private HashMap<Integer, MediaPlayer> mediaPlayers;//mediaplayers集合
    /**
     * 客户id
     */
    private int co_id;

    @SuppressLint("ValidFragment")
    public FollowUpRecordFragment(int co_id) {
        this.co_id = co_id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow_up_record, null);
        unbinder = ButterKnife.bind(this, view);
        gson = new Gson();
        initView();
        initRequestFollowRecording();
        return view;
    }

    /**
     * 跟进记录请求
     */
    private void initRequestFollowRecording() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("co_id", co_id);
        params.put("token", AppInfoUtils.getToekn(getActivity()));
        OkHttpUtils.get(Constant.SALE_GETCOMFOLLOW, params, new Callback() {
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
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONArray jsonArray = data.getJSONArray("list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            ComFollowRecordingBean recordingBean = gson.fromJson(jsonArray.get(i).toString(), ComFollowRecordingBean.class);
                            beanList.add(recordingBean);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (adapter == null) {
                                    adapter = new FollowUpRecordAdapter(getActivity(), beanList);
                                    adapter.setOnPlayVideoListener(new FollowUpRecordAdapter.OnPlayVideoListener() {
                                        @Override
                                        public void onResult(final MediaPlayer mediaPlayer, final FollowUpRecordAdapter.MyViewHolder holder, final int position) {
                                            if (mediaPlayers.isEmpty()) {
                                                mediaPlayers.put(position, mediaPlayer);
                                            } else {
                                                if (mediaPlayers.get(position) == null) {   //添加mediaplayer到map
                                                    mediaPlayers.put(position, mediaPlayer);
                                                }
                                            }
                                            if (mPosition != position) {
                                                if (mediaPlayers.get(mPosition) != null) {
                                                    if (mediaPlayers.get(mPosition).isPlaying()) {
                                                        mediaPlayers.get(mPosition).pause();//暂停播放
                                                        if (timer != null) {
                                                            timer.cancel();
                                                            timer = null;
                                                        }
                                                    }
                                                }
                                                if (mediaPlayers.get(position).isPlaying()) {
                                                    mediaPlayers.get(position).pause();//暂停播放
                                                    if (timer != null) {
                                                        timer.cancel();
                                                        timer = null;
                                                    }
                                                } else {
                                                    mediaPlayers.get(position).start();//开始播放
                                                    //监听播放时回调函数
                                                    timer = new Timer();
                                                    timer.schedule(new TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            if (!isSeekBarChanging) {
                                                                holder.seekBar.setProgress(mediaPlayers.get(position).getCurrentPosition());
                                                                getActivity().runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        holder.tvCurrentTime.setText(format.format(mediaPlayers.get(position).getCurrentPosition()) + "");
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    }, 0, 50);
                                                }
                                            } else {
                                                if (!mediaPlayers.get(position).isPlaying()) {
                                                    mediaPlayers.get(position).start();//开始播放
                                                    //监听播放时回调函数
                                                    timer = new Timer();
                                                    timer.schedule(new TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            if (!isSeekBarChanging) {
                                                                holder.seekBar.setProgress(mediaPlayers.get(position).getCurrentPosition());
                                                                getActivity().runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        holder.tvCurrentTime.setText(format.format(mediaPlayers.get(position).getCurrentPosition()) + "");
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    }, 0, 50);
                                                } else {
                                                    mediaPlayers.get(position).pause();//暂停播放
                                                    if (timer != null) {
                                                        timer.cancel();
                                                        timer = null;
                                                    }
                                                }
                                            }
                                            mPosition = position;

                                        }

                                        @Override
                                        public void onMoveResult(boolean b, SeekBar seekBar, int position) {
                                            isSeekBarChanging = b;
                                            if (seekBar != null) {
                                                if (mPosition == position) {
                                                    if (mediaPlayers.get(mPosition) != null) {
                                                        mediaPlayers.get(mPosition).seekTo(seekBar.getProgress());
                                                    }
                                                }
                                            }
                                        }
                                    });
                                    recyclerView.setAdapter(adapter);
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 初始化视图
     */
    private void initView() {
        mediaPlayers = new HashMap<>();
        format = new SimpleDateFormat("mm:ss");
        beanList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        isSeekBarChanging = true;
        for (Integer mediaPlay : mediaPlayers.keySet()) {
            if (mediaPlayers.get(mediaPlay) != null) {
                mediaPlayers.get(mediaPlay).stop();
                mediaPlayers.get(mediaPlay).release();
            }
        }

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}
