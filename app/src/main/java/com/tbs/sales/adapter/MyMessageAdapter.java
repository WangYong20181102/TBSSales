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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.activity.ClientDetailsActivity;
import com.tbs.sales.activity.NoticeActivity;
import com.tbs.sales.activity.WebViewActivity;
import com.tbs.sales.bean.MyMessageBean;
import com.tbs.sales.constant.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Wang on 2019/3/1 13:43.
 */
public class MyMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<MyMessageBean> beanList;
    private Context context;

    public MyMessageAdapter(Context context, List<MyMessageBean> beanList) {
        this.beanList = beanList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_message_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.linearMessage.setOnClickListener(this);
        holder.relativeLookDetail.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            switch (beanList.get(position).getNotice_type()) {
                case 1://新订单推送
                    ((MyViewHolder) holder).textTittle.setText("订单");
                    ((MyViewHolder) holder).imageIcon.setImageResource(R.mipmap.message_order);
                    ((MyViewHolder) holder).textClientId.setText("订单号: " + beanList.get(position).getOrder_id());
                    break;
                case 10://客户消息
                    ((MyViewHolder) holder).textTittle.setText("客户");
                    ((MyViewHolder) holder).imageIcon.setImageResource(R.mipmap.message_client);
                    ((MyViewHolder) holder).textClientId.setText("客户ID: " + beanList.get(position).getCo_id());
                    break;
                case 11://审批消息
                    ((MyViewHolder) holder).textTittle.setText("审批");
                    ((MyViewHolder) holder).imageIcon.setImageResource(R.mipmap.message_approval);
                    ((MyViewHolder) holder).textClientId.setText("申请人: " + beanList.get(position).getAudit_name());
                    break;
                case 12://系统通知
                    ((MyViewHolder) holder).textTittle.setText(beanList.get(position).getTitle());
                    ((MyViewHolder) holder).imageIcon.setImageResource(R.mipmap.message_notice);
                    break;
            }
            if (beanList.get(position).getNotice_type() == 12) {
                ((MyViewHolder) holder).linearAction.setVisibility(View.GONE);
                ((MyViewHolder) holder).textAction.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).textAction.setText(beanList.get(position).getContent());
            } else {
                ((MyViewHolder) holder).linearAction.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).textAction.setVisibility(View.GONE);
                ((MyViewHolder) holder).textContent.setText(beanList.get(position).getContent());
            }
            //时间
            ((MyViewHolder) holder).textData.setText(beanList.get(position).getCreate_time());
            //城市
            ((MyViewHolder) holder).textCity.setText("城市: " + beanList.get(position).getCity_name());
            ((MyViewHolder) holder).relativeLookDetail.setTag(position);
            ((MyViewHolder) holder).linearMessage.setTag(position);
        }
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        switch (v.getId()) {
            case R.id.linear_message:
            case R.id.relative_look_detail:
                Intent intent = null;
                if (beanList.get(position).getNotice_type() == 11) {//审批
                    String value = "";
                    int wf_type = beanList.get(position).getWf_type();
                    switch (wf_type) {
                        case 1://合同
                            value = Constant.BUSINESS_CONTRACTDETAIL + "?exp_id=" + beanList.get(position).getExp_id() + "&type=1";
                            break;
                        case 2://收款
                            value = Constant.BUSINESS_RECEIPTDETAIL + "?exp_id=" + beanList.get(position).getExp_id() + "&type=1";
                            break;
                        case 3://推广
                            value = Constant.BUSINESS_EXPANDDETAIL + "?exp_id=" + beanList.get(position).getExp_id() + "&type=1";
                            break;
                        case 4://费用
                            value = Constant.BUSINESS_PAYMENTDETAIL + "?exp_id=" + beanList.get(position).getExp_id() + "&type=1";
                            break;
                    }
                    intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("mLoadingUrl", value);
                } else if (beanList.get(position).getNotice_type() == 1) {//订单
                    intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("mLoadingUrl", Constant.ORDER_DETAIL + "?orderid=" + beanList.get(position).getOrder_id());
                } else if (beanList.get(position).getNotice_type() == 10) {//客户
                    intent = new Intent(context, ClientDetailsActivity.class);
                    intent.putExtra("type", 3);
                    intent.putExtra("co_id", beanList.get(position).getCo_id());
                } else {//系统通知
                    intent = new Intent(context, NoticeActivity.class);
                    intent.putExtra(MyMessageBean.class.getName(), beanList.get(position));
                }
                context.startActivity(intent);
                break;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.linear_message)
        LinearLayout linearMessage;
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
        @BindView(R.id.text_action)
        TextView textAction;
        @BindView(R.id.linear_action)
        LinearLayout linearAction;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
