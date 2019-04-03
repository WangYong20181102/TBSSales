package com.tbs.sales.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.bean.PeopleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Wang on 2019/2/28 13:58.
 */
public class PeopleChoseAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<PeopleBean> peopleBeanList;
    private MyFilter filter = null;
    private FilterListener listener = null;

    public PeopleChoseAdapter(Context context, List<PeopleBean> listCity) {
        this.context = context;
        this.peopleBeanList = listCity;
    }

    @Override
    public int getCount() {
        return peopleBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return peopleBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.city_item, parent, false);
            holder = new MyViewHolder();
            holder.textCity = convertView.findViewById(R.id.text_city);
            holder.tvRemaning = convertView.findViewById(R.id.tv_remaining);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        holder.textCity.setText(peopleBeanList.get(position).getName());
        holder.tvRemaning.setText("(剩余" + peopleBeanList.get(position).getCan_grab() + ")");
        return convertView;
    }

    @Override
    public Filter getFilter() {
        // 如果MyFilter对象为空，那么重写创建一个
        if (filter == null) {
            filter = new MyFilter(peopleBeanList);
        }
        return filter;
    }

    public class MyViewHolder {
        private TextView textCity;
        private TextView tvRemaning;
    }

    /**
     * 创建内部类MyFilter继承Filter类，并重写相关方法，实现数据的过滤
     */
    class MyFilter extends Filter {
        //创建集合保存原始数据
        private List<PeopleBean> original;

        public MyFilter(List<PeopleBean> oruginal) {
            this.original = oruginal;
        }

        /**
         * 该方法返回搜索过滤后的数据
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            //创建FilterResults对象
            FilterResults results = new FilterResults();
            /**
             * 没有搜索内容的话就还是给results赋值原始数据的值和大小
             * 执行了搜索的话，根据搜索的规则过滤即可，最后把过滤后的数据的值和大小赋值给results
             */
            if (TextUtils.isEmpty(constraint)) {
                results.values = original;
                results.count = original.size();
            } else {
                // 创建集合保存过滤后的数据
                List<PeopleBean> mList = new ArrayList<PeopleBean>();
                // 遍历原始数据集合，根据搜索的规则过滤数据
                for (PeopleBean s : original) {
                    // 这里就是过滤规则的具体实现【规则有很多，大家可以自己决定怎么实现】
                    if (s.getName().trim().toLowerCase().contains(constraint.toString().trim().toLowerCase())) {
                        // 规则匹配的话就往集合中添加该数据
                        mList.add(s);
                    }
                }
                results.values = mList;
                results.count = mList.size();
            }
            // 返回FilterResults对象

            return results;
        }

        /**
         * 该方法用来刷新用户界面，根据过滤后的数据重新展示列表
         */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // 获取过滤后的数据
            peopleBeanList = (List<PeopleBean>) results.values;
            // 如果接口对象不为空，那么调用接口中的方法获取过滤后的数据，具体的实现在new这个接口的时候重写的方法里执行
            if (listener != null) {
                listener.getFilterData(peopleBeanList);
            }
            // 刷新数据源显示
            notifyDataSetChanged();

        }
    }

    public void setListener(FilterListener listener) {
        this.listener = listener;
    }

    public interface FilterListener {
        void getFilterData(List<PeopleBean> list);// 获取过滤后的数据
    }
}
