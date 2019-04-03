package com.tbs.sales.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.activity.ClientDetailsActivity;
import com.tbs.sales.activity.WebViewActivity;
import com.tbs.sales.bean.HomeDataBean;
import com.tbs.sales.constant.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Wang on 2019/2/27 14:55.
 */
public class HomeTodayFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<HomeDataBean.ListBean> beanList;

    public HomeTodayFragmentAdapter(Context context, List<HomeDataBean.ListBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.today_top_bg, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.today_layout, parent, false);
            MyViewHolder2 holder2 = new MyViewHolder2(view);
            return holder2;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder2) {
            //公司名称
            ((MyViewHolder2) holder).textCompanyName.setText(beanList.get(position - 1).getCo_name());
            //右侧信息
            if (beanList.get(position - 1).getWarn_state() == 0) {
                ((MyViewHolder2) holder).textRemarks.setText(beanList.get(position - 1).getCo_type_name());
            } else {
                ((MyViewHolder2) holder).textRemarks.setText(beanList.get(position - 1).getWarn_state_desc());
            }
            //id
            ((MyViewHolder2) holder).textId.setText(beanList.get(position - 1).getCo_id() + "");
            //姓名
            ((MyViewHolder2) holder).textUserName.setText(beanList.get(position - 1).getName());
            //性别
            switch (beanList.get(position - 1).getSex()) {
                case 1:
                    ((MyViewHolder2) holder).imageSex.setImageResource(R.mipmap.boy);
                    break;
                case 2:
                    ((MyViewHolder2) holder).imageSex.setImageResource(R.mipmap.girl);
                    break;
            }
            //时间
            ((MyViewHolder2) holder).textTime.setText(beanList.get(position - 1).getGrab_add_time());
            ((MyViewHolder2) holder).linearClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ClientDetailsActivity.class);
                    intent.putExtra("co_id", beanList.get(position - 1).getCo_id());
                    context.startActivity(intent);
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return beanList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.text_company_name)
        TextView textCompanyName;
        @BindView(R.id.text_remarks)
        TextView textRemarks;
        @BindView(R.id.text_id)
        TextView textId;
        @BindView(R.id.text_user_name)
        TextView textUserName;
        @BindView(R.id.text_time)
        TextView textTime;
        @BindView(R.id.linear_click)
        LinearLayout linearClick;
        @BindView(R.id.image_sex)
        ImageView imageSex;

        public MyViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
