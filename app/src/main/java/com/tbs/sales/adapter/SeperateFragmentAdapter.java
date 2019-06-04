package com.tbs.sales.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.activity.SeperateCitySelectActivity;
import com.tbs.sales.activity.WebViewActivity;
import com.tbs.sales.bean.SeperateDateBean;
import com.tbs.sales.constant.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Wang on 2019/5/23 15:16.
 */
public class SeperateFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<SeperateDateBean.ListBean> beanList;
    //城市名称集
    private String city_str;
    //城市id集
    private String city;

    public SeperateFragmentAdapter(Context context, List<SeperateDateBean.ListBean> beanList, String city_str, String city) {
        this.context = context;
        this.city_str = city_str;
        this.beanList = beanList;
        this.city = city;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.seperate_head_location, parent, false);
            MyViewHolder1 holder1 = new MyViewHolder1(view);
            return holder1;
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.seperate_item, parent, false);
            MyViewHolder2 holder2 = new MyViewHolder2(view);
            return holder2;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder1) {
            //当前筛选城市信息
            ((MyViewHolder1) holder).textRegion.setText("当前区域："+city_str);
            //跳转城市选择界面
            ((MyViewHolder1) holder).rlClickGoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SeperateCitySelectActivity.class);
                    intent.putExtra("cityList", city);
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof MyViewHolder2) {
            //订单id
            ((MyViewHolder2) holder).textOrder.setText(beanList.get(position - 1).getOrderid() + "");
            //时间
            ((MyViewHolder2) holder).textDateTime.setText(beanList.get(position - 1).getDiff_time());
            //订单状态
            ((MyViewHolder2) holder).textOrderType.setText(beanList.get(position - 1).getStatus_desc());
            //面积
            ((MyViewHolder2) holder).textArea.setText(beanList.get(position - 1).getHousearea() + "㎡");
            //区域
            ((MyViewHolder2) holder).textRegion.setText(beanList.get(position - 1).getQu_name());
            //类型
            ((MyViewHolder2) holder).textHomeType.setText(beanList.get(position - 1).getHousetype());
            //户型
            ((MyViewHolder2) holder).textHouseType.setText(beanList.get(position - 1).getRoomnumber());
            //小区
            ((MyViewHolder2) holder).textCommunity.setText(beanList.get(position - 1).getHousename());

            //分单详情页（H5）
            ((MyViewHolder2) holder).linearSperate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("mLoadingUrl", Constant.ORDER_DETAIL + "?orderid=" + beanList.get(position - 1).getOrderid());
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
        @BindView(R.id.text_region)
        TextView textRegion;
        @BindView(R.id.rl_click_goto)
        RelativeLayout rlClickGoto;

        public MyViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.linear_sperate)
        LinearLayout linearSperate;
        @BindView(R.id.text_order_name)
        TextView textOrderName;
        @BindView(R.id.text_order)
        TextView textOrder;
        @BindView(R.id.ll_seperate_type)
        LinearLayout llSeperateType;
        @BindView(R.id.text_date_time)
        TextView textDateTime;
        @BindView(R.id.text_area)
        TextView textArea;
        @BindView(R.id.text_region)
        TextView textRegion;
        @BindView(R.id.text_home_type)
        TextView textHomeType;
        @BindView(R.id.text_house_type)
        TextView textHouseType;
        @BindView(R.id.text_community)
        TextView textCommunity;
        @BindView(R.id.text_order_type)
        TextView textOrderType;

        public MyViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
