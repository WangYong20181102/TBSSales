package com.tbs.sales.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.bean.ClientLeaderboardBean;
import com.tbs.sales.bean.ClientLeaderboardBean.FinishListBean;
import com.tbs.sales.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Wang on 2019/2/22 09:01.
 */
public class ClientViewPagerAdapter extends PagerAdapter {
    @BindView(R.id.image_no_two)
    ImageView imageNoTwo;
    @BindView(R.id.tv_no2)
    TextView tvNo2;
    @BindView(R.id.tv_no_two)
    TextView tvNoTwo;
    @BindView(R.id.image_no_one)
    ImageView imageNoOne;
    @BindView(R.id.tv_no1)
    TextView tvNo1;
    @BindView(R.id.tv_no_one)
    TextView tvNoOne;
    @BindView(R.id.image_no_three)
    ImageView imageNoThree;
    @BindView(R.id.tv_no3)
    TextView tvNo3;
    @BindView(R.id.tv_no_three)
    TextView tvNoThree;
    private Context context;
    private List<ClientLeaderboardBean> listList;

    public ClientViewPagerAdapter(Context context, List<ClientLeaderboardBean> listList) {
        this.context = context;
        this.listList = listList;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.client_view_pager_layout, null);
        ButterKnife.bind(this, view);
        container.addView(view);

        if (position == 0) {
            tvNoOne.setText(listList.get(0).getFinish_list().get(0).getMember());
            tvNoTwo.setText(listList.get(0).getFinish_list().get(1).getMember());
            tvNoThree.setText(listList.get(0).getFinish_list().get(2).getMember());

            for (int i = 0; i < (listList.get(0).getFinish_list().size() > 3 ? 3 : listList.get(0).getFinish_list().size()); i++) {
                showHeadIcon(i, listList.get(0).getFinish_list().get(i).getIcon(), listList.get(0).getFinish_list().get(i).getMember());
            }
        } else if (position == 1) {
            tvNoOne.setText(listList.get(0).getFollow_list().get(0).getMember());
            tvNoTwo.setText(listList.get(0).getFollow_list().get(1).getMember());
            tvNoThree.setText(listList.get(0).getFollow_list().get(2).getMember());
            for (int i = 0; i < (listList.get(0).getFollow_list().size() > 3 ? 3 : listList.get(0).getFollow_list().size()); i++) {
                showHeadIcon(i, listList.get(0).getFollow_list().get(i).getIcon(), listList.get(0).getFollow_list().get(i).getMember());
            }
        } else {
            tvNoOne.setText(listList.get(0).getAdd_list().get(0).getMember());
            tvNoTwo.setText(listList.get(0).getAdd_list().get(1).getMember());
            tvNoThree.setText(listList.get(0).getAdd_list().get(2).getMember());
            for (int i = 0; i < (listList.get(0).getAdd_list().size() > 3 ? 3 : listList.get(0).getAdd_list().size()); i++) {
                showHeadIcon(i, listList.get(0).getAdd_list().get(i).getIcon(), listList.get(0).getAdd_list().get(i).getMember());
            }
        }
        return view;
    }

    /**
     * 显示头像信息
     */
    private void showHeadIcon(int num, String icon, String member) {
        switch (num) {
            case 0:
                if (TextUtils.isEmpty(icon)) {
                    imageNoOne.setVisibility(View.GONE);
                    tvNo1.setVisibility(View.VISIBLE);
                    tvNo1.setText(member.substring(0, 1));
                } else {
                    imageNoOne.setVisibility(View.VISIBLE);
                    tvNo1.setVisibility(View.GONE);
                    GlideUtils.glideLoader(context, icon, imageNoOne);
                }
                break;
            case 1:
                if (TextUtils.isEmpty(icon)) {
                    imageNoTwo.setVisibility(View.GONE);
                    tvNo2.setVisibility(View.VISIBLE);
                    tvNo2.setText(member.substring(0, 1));
                } else {
                    imageNoTwo.setVisibility(View.VISIBLE);
                    tvNo2.setVisibility(View.GONE);
                    GlideUtils.glideLoader(context, icon, imageNoTwo);
                }
                break;
            case 2:
                if (TextUtils.isEmpty(icon)) {
                    imageNoThree.setVisibility(View.GONE);
                    tvNo3.setVisibility(View.VISIBLE);
                    tvNo3.setText(member.substring(0, 1));
                } else {
                    imageNoThree.setVisibility(View.VISIBLE);
                    tvNo3.setVisibility(View.GONE);
                    GlideUtils.glideLoader(context, icon, imageNoThree);
                }
                break;
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
