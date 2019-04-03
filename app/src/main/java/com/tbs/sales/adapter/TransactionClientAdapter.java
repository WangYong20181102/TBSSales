package com.tbs.sales.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.activity.ClientDetailsActivity;
import com.tbs.sales.activity.HomeSearchActivity;
import com.tbs.sales.activity.LoginActivity;
import com.tbs.sales.bean.HomeDataBean;
import com.tbs.sales.utils.AppInfoUtils;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Wang on 2019/2/27 13:51.
 */
public class TransactionClientAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private final SimpleDateFormat dateFormat;
    private Context context;
    private List<HomeDataBean.ListBean> beanList;
    //时间
    private String dateTime;
    //客户总数
    private int totalNum;


    public static interface OnFilterClickListener {
        void onFilterClick(View view);
    }

    private static OnFilterClickListener onFilterClick = null;

    //用于筛选按钮回调事件
    public static void setOnFilterClickListener(OnFilterClickListener onFilterClickListener) {
        onFilterClick = onFilterClickListener;
    }

    public TransactionClientAdapter(Context context, List<HomeDataBean.ListBean> beanList, String dateTime, int totalNum) {
        this.context = context;
        this.beanList = beanList;
        this.dateTime = dateTime;
        this.totalNum = totalNum;
        dateFormat = new SimpleDateFormat("yyyy-MM");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.transaction_client_head, parent, false);
            MyViewHolder1 holder1 = new MyViewHolder1(view);
            holder1.linearDateTime.setOnClickListener(this);
            return holder1;
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.transaction_client_item_content, parent, false);
            MyViewHolder2 holder2 = new MyViewHolder2(view);
            return holder2;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder1) {

            if (dateTime.equals(dateFormat.format(System.currentTimeMillis()))) {
                ((MyViewHolder1) holder).tvDateTime.setText("本月");
            } else {
                ((MyViewHolder1) holder).tvDateTime.setText(dateTime);
            }
            ((MyViewHolder1) holder).tvNum.setText(totalNum + "个");
        } else if (holder instanceof MyViewHolder2) {
            //公司名称
            ((MyViewHolder2) holder).textCompanyName.setText(beanList.get(position - 1).getCo_name());
            //右侧信息
            ((MyViewHolder2) holder).textDateTime.setText(beanList.get(position - 1).getFinish_time());
            //id
            ((MyViewHolder2) holder).textId.setText(beanList.get(position - 1).getCo_id() + "");
            //姓名
            ((MyViewHolder2) holder).textUserName.setText(beanList.get(position - 1).getName());
            //性别(1男2nv)
            switch (beanList.get(position - 1).getSex()) {
                case 1:
                    ((MyViewHolder2) holder).imageSex.setImageResource(R.mipmap.boy);
                    break;
                case 2:
                    ((MyViewHolder2) holder).imageSex.setImageResource(R.mipmap.girl);
                    break;
            }
            //地址
            ((MyViewHolder2) holder).textAddress.setText(beanList.get(position - 1).getAddress());
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

    public class MyViewHolder1 extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_date_time)
        TextView tvDateTime;
        @BindView(R.id.linear_date_time)
        LinearLayout linearDateTime;
        @BindView(R.id.tv_num)
        TextView tvNum;

        public MyViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.text_company_name)
        TextView textCompanyName;
        @BindView(R.id.text_date_time)
        TextView textDateTime;
        @BindView(R.id.text_id)
        TextView textId;
        @BindView(R.id.text_user_name)
        TextView textUserName;
        @BindView(R.id.text_address)
        TextView textAddress;
        @BindView(R.id.linear_click)
        LinearLayout linearClick;
        @BindView(R.id.image_sex)
        ImageView imageSex;

        public MyViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_date_time:
                if (onFilterClick != null) {
                    onFilterClick.onFilterClick(v);
                }
                break;
        }
    }

    public void updateDateTime(String dateTime, int totalNum) {
        this.dateTime = dateTime;
        this.totalNum = totalNum;
    }
}
