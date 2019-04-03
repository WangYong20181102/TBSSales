package com.tbs.sales.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.tbs.sales.R;
import com.tbs.sales.bean.KeyValueDataBean;

import java.util.List;

/**
 * Created by Mr.Wang on 2019/4/1 16:50.
 */
public class BottomSelectItemAdapter extends BaseAdapter {
    private Context context;
    private List<KeyValueDataBean> strList;

    public BottomSelectItemAdapter(Context context, List<KeyValueDataBean> strList) {
        this.context = context;
        this.strList = strList;
    }

    @Override
    public int getCount() {
        return strList.size();
    }

    @Override
    public Object getItem(int position) {
        return strList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.bottom_selsect_item, parent, false);
            holder = new MyViewHolder();
            holder.textView = convertView.findViewById(R.id.tv_item);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        holder.textView.setText(strList.get(position).getName());
        return convertView;
    }

    public class MyViewHolder {
        TextView textView;
    }
}
