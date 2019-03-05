package com.tbs.sales.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.bean.ClientLeaderboardBean;

import java.util.List;

/**
 * Created by Mr.Wang on 2019/2/22 09:01.
 */
public class ClientViewPagerAdapter extends PagerAdapter {
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
        TextView tvNoOne = view.findViewById(R.id.tv_no_one);   //第一名
        TextView tvNoTwo = view.findViewById(R.id.tv_no_two);   //第二名
        TextView tvNoThree = view.findViewById(R.id.tv_no_three);   //第三名
        ImageView imageNoOne = view.findViewById(R.id.image_no_one);
        ImageView imageNoTwo = view.findViewById(R.id.image_no_two);
        ImageView imageNoThree = view.findViewById(R.id.image_no_three);
        container.addView(view);

        if (position == 0){
            tvNoOne.setText(listList.get(0).getFinish_list().get(0).getMember());
            tvNoTwo.setText(listList.get(0).getFinish_list().get(1).getMember());
            tvNoThree.setText(listList.get(0).getFinish_list().get(2).getMember());
        }else if (position == 1){
            tvNoOne.setText(listList.get(0).getFollow_list().get(0).getMember());
            tvNoTwo.setText(listList.get(0).getFollow_list().get(1).getMember());
            tvNoThree.setText(listList.get(0).getFollow_list().get(2).getMember());
        }else {
            tvNoOne.setText(listList.get(0).getAdd_list().get(0).getMember());
            tvNoTwo.setText(listList.get(0).getAdd_list().get(1).getMember());
            tvNoThree.setText(listList.get(0).getAdd_list().get(2).getMember());
        }

        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
    }
}
