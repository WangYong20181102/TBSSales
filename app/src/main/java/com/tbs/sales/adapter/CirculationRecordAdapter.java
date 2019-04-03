package com.tbs.sales.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.bean.CirculationRecordBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Wang on 2019/3/30 09:28.
 */
public class CirculationRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<CirculationRecordBean> beanList;

    public CirculationRecordAdapter(Context context, List<CirculationRecordBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.circulation_record_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyViewHolder) {
            if (position == 0) {//顶部空白区域
                ((MyViewHolder) holder).viewTopBg.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).imageSelect.setImageResource(R.mipmap.select);
            } else {
                ((MyViewHolder) holder).viewTopBg.setVisibility(View.GONE);
                ((MyViewHolder) holder).imageSelect.setImageResource(R.mipmap.un_select);
            }
            if (position == beanList.size() - 1) {  //隐藏最后一条线
                ((MyViewHolder) holder).viewLeftLine.setVisibility(View.GONE);
            } else {
                ((MyViewHolder) holder).viewLeftLine.setVisibility(View.VISIBLE);
            }
            //类型(操作类型 0-未知 1-领取 2-放弃 3-延期 4-流失 5-成交 6-新增 7-修改 8-续费 9-移入黑名单 10-移出黑名单 11-续费到期 12-续费分配)
            switch (beanList.get(position).getType()) {
                case 1:
                    ((MyViewHolder) holder).tvType.setTextColor(Color.parseColor("#47BF63"));
                    break;
                case 2:
                    ((MyViewHolder) holder).tvType.setTextColor(Color.parseColor("#F43737"));
                    break;
                case 3:
                    ((MyViewHolder) holder).tvType.setTextColor(Color.parseColor("#FE911D"));
                    break;
                case 4:
                    ((MyViewHolder) holder).tvType.setTextColor(Color.parseColor("#F7D101"));
                    break;
                case 5:
                    ((MyViewHolder) holder).tvType.setTextColor(Color.parseColor("#10C093"));
                    break;
                case 6:
                    ((MyViewHolder) holder).tvType.setTextColor(Color.parseColor("#498AF6"));
                    break;
                case 7:
                    ((MyViewHolder) holder).tvType.setTextColor(Color.parseColor("#379EF6"));
                    break;
                default:
                    ((MyViewHolder) holder).tvType.setTextColor(Color.parseColor("#F43737"));
                    break;
            }
            ((MyViewHolder) holder).tvType.setText(beanList.get(position).getType_name());
            //姓名
            ((MyViewHolder) holder).tvName.setText(beanList.get(position).getAdd_name());
            //日期
            ((MyViewHolder) holder).tvData.setText(beanList.get(position).getAdd_time());
            //内容
            ((MyViewHolder) holder).tvContent.setText(beanList.get(position).getContent());
        }
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_top_bg)
        View viewTopBg;
        @BindView(R.id.view_left_line)
        View viewLeftLine;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_data)
        TextView tvData;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.image_select)
        ImageView imageSelect;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
