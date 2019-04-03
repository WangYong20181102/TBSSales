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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.activity.ClientDetailsActivity;
import com.tbs.sales.activity.InteratedQuerySearchActivity;
import com.tbs.sales.activity.LoginActivity;
import com.tbs.sales.bean.FollowRecordingBean;
import com.tbs.sales.bean.HomeDataBean;
import com.tbs.sales.utils.AppInfoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Wang on 2019/2/27 13:51.
 */
public class FollowRecordingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<FollowRecordingBean.ListBean> beanList;


    public FollowRecordingAdapter(Context context, List<FollowRecordingBean.ListBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.follow_recording_head, parent, false);
            MyViewHolder1 holder1 = new MyViewHolder1(view);
            return holder1;
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.follow_recording_content, parent, false);
            MyViewHolder2 holder2 = new MyViewHolder2(view);
            return holder2;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder1) {

        } else if (holder instanceof MyViewHolder2) {
            //公司名称
            ((MyViewHolder2) holder).textCompanyName.setText(beanList.get(position - 1).getCo_name());
            //时间
            ((MyViewHolder2) holder).tvDataTime.setText(beanList.get(position - 1).getFollow_time());
            //姓名
            ((MyViewHolder2) holder).tvName.setText(beanList.get(position - 1).getName());
            //电话
            ((MyViewHolder2) holder).tvNumber.setText(beanList.get(position - 1).getPhone());
            ((MyViewHolder2) holder).linearClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ClientDetailsActivity.class);
                    intent.putExtra("type",3);
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

        public MyViewHolder1(View itemView) {
            super(itemView);
        }
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.text_company_name)
        TextView textCompanyName;
        @BindView(R.id.tv_date_time)
        TextView tvDataTime;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone_number)
        TextView tvNumber;
        @BindView(R.id.linear_click)
        LinearLayout linearClick;

        public MyViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
