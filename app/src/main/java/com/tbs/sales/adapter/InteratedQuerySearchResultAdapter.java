package com.tbs.sales.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.activity.ClientDetailsActivity;
import com.tbs.sales.bean.HomeDataBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Wang on 2019/2/27 14:55.
 */
public class InteratedQuerySearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<HomeDataBean.ListBean> beanList;

    public InteratedQuerySearchResultAdapter(Context context, List<HomeDataBean.ListBean> beanList) {
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
            View view = LayoutInflater.from(context).inflate(R.layout.integrated_query_content, parent, false);
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
            ((MyViewHolder2) holder).textRemarks.setText(beanList.get(position - 1).getCo_type_name());
            //id
            ((MyViewHolder2) holder).textId.setText(beanList.get(position - 1).getCo_id() + "");
            //领取人
            if (TextUtils.isEmpty(beanList.get(position - 1).getGrab_name().trim())) {
                ((MyViewHolder2) holder).linearUserName.setVisibility(View.GONE);
            } else {
                ((MyViewHolder2) holder).linearUserName.setVisibility(View.VISIBLE);
            }
            ((MyViewHolder2) holder).textUserName.setText(beanList.get(position - 1).getGrab_name());
            //私有
            if (beanList.get(position -1).getIs_grab_desc().equals("私有客户")){
                ((MyViewHolder2) holder).linearPrivate.setVisibility(View.VISIBLE);
            }else {
                ((MyViewHolder2) holder).linearPrivate.setVisibility(View.GONE);
            }
            ((MyViewHolder2) holder).linearClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ClientDetailsActivity.class);
                    intent.putExtra("type",2);
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
        @BindView(R.id.linear_click)
        LinearLayout linearClick;
        @BindView(R.id.linear_user_name)
        LinearLayout linearUserName;
        @BindView(R.id.linear_private)
        LinearLayout linearPrivate;

        public MyViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
