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
 * Created by Mr.Wang on 2019/2/27 16:39.
 */
public class HomeSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<HomeDataBean> beanList;

    public HomeSearchAdapter(Context context, List<HomeDataBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0){
            View view = LayoutInflater.from(context).inflate(R.layout.today_top_bg, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.my_content, parent, false);
            MyViewHolder2 holder2 = new MyViewHolder2(view);
            return holder2;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return beanList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return 0;
        }else {
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
        private TextView textAddress;//地址

        public MyViewHolder2(View itemView) {
            super(itemView);
            textAddress = itemView.findViewById(R.id.text_address);
            textCompanyName = itemView.findViewById(R.id.text_company_name);
            textRemarks = itemView.findViewById(R.id.text_remarks);
            textId = itemView.findViewById(R.id.text_id);
            textUserName = itemView.findViewById(R.id.text_user_name);
        }
    }
}
