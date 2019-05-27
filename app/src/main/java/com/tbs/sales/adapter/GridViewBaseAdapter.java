package com.tbs.sales.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.bean.SeperateCityListBean;
import com.tbs.sales.utils.ToastUtils;

import java.util.List;

/**
 * Created by Mr.Wang on 2019/5/24 09:30.
 */
public class GridViewBaseAdapter extends BaseAdapter {
    private Context context;
    private List<SeperateCityListBean.ListBean.CityBean> cityBeanList;
    private LayoutInflater inflater = null;
    private SeperateCityListBean cityListBean;

    public GridViewBaseAdapter(Context context, List<SeperateCityListBean.ListBean.CityBean> cityBeanList, SeperateCityListBean cityListBean) {
        this.context = context;
        this.cityBeanList = cityBeanList;
        this.cityListBean = cityListBean;
        inflater = LayoutInflater.from(context);
    }

    private OnItemClickListener onItemClickListener;
    private OnItemClickAllListener onItemClickAllListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void onClick(boolean b);
    }

    interface OnItemClickAllListener {
        void onClick(boolean b);
    }

    public void setOnItemClickAllListener(OnItemClickAllListener onItemClickAllListener) {
        this.onItemClickAllListener = onItemClickAllListener;
    }

    /**
     * 更改数据
     *
     * @param list
     */
    public void changeCityList(List<SeperateCityListBean.ListBean.CityBean> list) {
        cityBeanList = list;
    }

    @Override
    public int getCount() {
        return cityBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.gridview_item_city, parent, false);
            holder.tvCityName = convertView.findViewById(R.id.text_city_name);
            holder.linearBg = convertView.findViewById(R.id.linear_bg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvCityName.setText(cityBeanList.get(position).getCity_name());
        final ViewHolder finalHolder = holder;
        holder.linearBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cityBeanList.get(position).isbSelect()) {
                    cityBeanList.get(position).setbSelect(true);
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    finalHolder.tvCityName.setTextColor(Color.parseColor("#ffffff"));
                    gradientDrawable.setColor(Color.parseColor("#107BFD"));
                    gradientDrawable.setCornerRadius(4);
                    finalHolder.linearBg.setBackgroundDrawable(gradientDrawable);
                } else {
                    cityBeanList.get(position).setbSelect(false);
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    finalHolder.tvCityName.setTextColor(Color.parseColor("#000000"));
                    gradientDrawable.setColor(Color.parseColor("#ffffff"));
                    gradientDrawable.setStroke(1, Color.parseColor("#107BFD"));
                    gradientDrawable.setCornerRadius(4);
                    finalHolder.linearBg.setBackgroundDrawable(gradientDrawable);
                }
                if (cityBeanList.toString().contains("false")) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(false);
                    }
                } else {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(true);
                    }
                }
                if (cityListBean.getList().toString().contains("false")) {
                    if (onItemClickAllListener != null) {
                        onItemClickAllListener.onClick(false);
                    }
                } else {
                    if (onItemClickAllListener != null) {
                        onItemClickAllListener.onClick(true);
                    }
                }
            }
        });

        showSelectType(finalHolder, cityBeanList.get(position).isbSelect());
        return convertView;
    }

    class ViewHolder {
        TextView tvCityName;
        LinearLayout linearBg;
    }

    /**
     * 选中状态
     *
     * @param holder
     * @param b
     */
    private void showSelectType(ViewHolder holder, boolean b) {
        if (b) {
            GradientDrawable gradientDrawable = new GradientDrawable();
            holder.tvCityName.setTextColor(Color.parseColor("#ffffff"));
            gradientDrawable.setColor(Color.parseColor("#107BFD"));
            gradientDrawable.setCornerRadius(4);
            holder.linearBg.setBackgroundDrawable(gradientDrawable);
        } else {
            GradientDrawable gradientDrawable = new GradientDrawable();
            holder.tvCityName.setTextColor(Color.parseColor("#000000"));
            gradientDrawable.setColor(Color.parseColor("#ffffff"));
            gradientDrawable.setStroke(1, Color.parseColor("#107BFD"));
            gradientDrawable.setCornerRadius(4);
            holder.linearBg.setBackgroundDrawable(gradientDrawable);
        }
    }

}
