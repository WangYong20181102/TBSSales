package com.tbs.sales.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.activity.NoticeActivity;
import com.tbs.sales.bean.MyMessageBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr.Wang on 2019/3/1 13:43.
 */
public class MyMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<MyMessageBean> beanList;
    private Context context;
    private Activity activity;

    public MyMessageAdapter(Context context,List<MyMessageBean> beanList) {
        this.beanList = beanList;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_message_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.relativeLookDetail.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            ((MyViewHolder) holder).textTittle.setText(beanList.get(position).getName());
            ((MyViewHolder) holder).textData.setText(beanList.get(position).getDate());
            ((MyViewHolder) holder).textContent.setText(beanList.get(position).getContent());
            ((MyViewHolder) holder).textCity.setText(beanList.get(position).getCity());
            ((MyViewHolder) holder).textClientId.setText(beanList.get(position).getClientId());
        }
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    @Override
    public void onClick(View v) {
        activity.startActivity(new Intent(context, NoticeActivity.class));
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_icon)
        ImageView imageIcon;
        @BindView(R.id.text_tittle)
        TextView textTittle;
        @BindView(R.id.text_data)
        TextView textData;
        @BindView(R.id.text_content)
        TextView textContent;
        @BindView(R.id.text_city)
        TextView textCity;
        @BindView(R.id.text_client_id)
        TextView textClientId;
        @BindView(R.id.relative_look_detail)
        RelativeLayout relativeLookDetail;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
