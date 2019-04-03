package com.tbs.sales.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.bean.KeyValueDataBean;

import java.util.List;

/**
 * Created by Mr.Wang on 2019/2/28 10:27.
 */
public class FilterClientTypeAdapter extends BaseAdapter {
    private Context context;
    private List<KeyValueDataBean> listClientType;
    private int selectPosition;

    public FilterClientTypeAdapter(Context context, List<KeyValueDataBean> listClientType, int selectPosition) {

        this.context = context;
        this.listClientType = listClientType;
        this.selectPosition = selectPosition;
    }

    @Override
    public int getCount() {
        return listClientType.size();
    }

    @Override
    public Object getItem(int position) {
        return listClientType.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.filter_item_layout,parent,false);
            holder = new MyViewHolder();
            holder.BtntextView = convertView.findViewById(R.id.text_item);
            convertView.setTag(holder);
        }else {
            holder = (MyViewHolder) convertView.getTag();
        }
        holder.BtntextView.setText(listClientType.get(position).getName());
        if (position == selectPosition) {
            holder.BtntextView.setTextColor(Color.parseColor("#ffffff"));
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(Color.parseColor("#107BFD"));
            gradientDrawable.setCornerRadius(54);
            holder.BtntextView.setBackgroundDrawable(gradientDrawable);
        } else {
            holder.BtntextView.setTextColor(Color.parseColor("#333333"));
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setStroke(1, Color.parseColor("#e5e5e5"));
            gradientDrawable.setColor(Color.parseColor("#ffffff"));
            gradientDrawable.setCornerRadius(54);
            holder.BtntextView.setBackgroundDrawable(gradientDrawable);
        }
        return convertView;
    }
    class MyViewHolder {
        private TextView BtntextView;
    }
    /**
     * 设置当前选中状态
     * @param position
     */
    public void setSelectPosition(int position) {
        selectPosition = position;
    }
}
