package com.tbs.sales.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.bean.HomeDataBean;

import java.util.List;

/**
 * Created by Mr.Wang on 2019/2/27 15:19.
 */
public class HomeEarlyWarningFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<HomeDataBean.ListBean> beanList;

    public HomeEarlyWarningFragmentAdapter(Context context, List<HomeDataBean.ListBean> beanList) {
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
            View view = LayoutInflater.from(context).inflate(R.layout.early_warning_layout, parent, false);
            MyViewHolder2 holder2 = new MyViewHolder2(view);
            return holder2;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HomeEarlyWarningFragmentAdapter.MyViewHolder2) {
            //公司名称
            ((MyViewHolder2) holder).textCompanyName.setText(beanList.get(position - 1).getCo_name());
            //右侧信息
            ((MyViewHolder2) holder).textRemarks.setText(beanList.get(position - 1).getCo_type_name());
            //id
            ((MyViewHolder2) holder).textId.setText(beanList.get(position - 1).getCo_id() + "");
            //姓名
            ((MyViewHolder2) holder).textUserName.setText(beanList.get(position - 1).getName());
            //地址
            ((MyViewHolder2) holder).textEarlyWarning.setText(beanList.get(position - 1).getWarn_state_desc());

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

    private class MyViewHolder2 extends RecyclerView.ViewHolder {
        private TextView textCompanyName;   //公司名称
        private TextView textRemarks;//标注
        private TextView textId;//id
        private TextView textUserName;//姓名
        private TextView textEarlyWarning;//预警

        public MyViewHolder2(View itemView) {
            super(itemView);
            textEarlyWarning = itemView.findViewById(R.id.text_early_warning);
            textCompanyName = itemView.findViewById(R.id.text_company_name);
            textRemarks = itemView.findViewById(R.id.text_remarks);
            textId = itemView.findViewById(R.id.text_id);
            textUserName = itemView.findViewById(R.id.text_user_name);
        }
    }
}
