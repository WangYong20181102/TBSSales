package com.tbs.sales.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.bean.SeperateCityListBean;
import com.tbs.sales.widget.MyGridView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Wang on 2019/5/24 09:19.
 */
public class SeperateCitySelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<SeperateCityListBean.ListBean> beanList;
    private SeperateCityListBean cityListBean;
    //存放GridView适配器，防止重复new一个adapter
    private HashMap<Integer, GridViewBaseAdapter> hashMap;

    @SuppressLint("UseSparseArrays")
    public SeperateCitySelectAdapter(Context context, List<SeperateCityListBean.ListBean> beanList, SeperateCityListBean cityListBean) {
        this.context = context;
        this.beanList = beanList;
        hashMap = new HashMap<>();
        this.cityListBean = cityListBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.top_select_all, parent, false);
            return new MyViewHolder1(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.seperate_city_select_item, parent, false);
            MyViewHolder2 holder2 = new MyViewHolder2(view);
            holder2.imageSelect.setOnClickListener(this);
            holder2.linearSelect.setOnClickListener(this);
            holder2.textSelect.setOnClickListener(this);
            return holder2;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder1) {
            //全选按钮点击事件
            ((MyViewHolder1) holder).linearSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int h = beanList.size();
                    if (cityListBean.isbAll()) {//判断全选状态，等于true是全选
                        cityListBean.setbAll(false);
                        ((MyViewHolder1) holder).imageSelect.setImageResource(R.mipmap.un_xuanzhong);
                        for (int i = 0; i < h; i++) {
                            //将所有区域置为false（即非全选状态）
                            beanList.get(i).setbAreaName(false);
                            int k = beanList.get(i).getCity().size();
                            for (int j = 0; j < k; j++) {
                                //将区域下边所有城市置为false（即非选择状态）
                                beanList.get(i).getCity().get(j).setbSelect(false);
                            }
                        }
                    } else {
                        cityListBean.setbAll(true);
                        ((MyViewHolder1) holder).imageSelect.setImageResource(R.mipmap.xuanzhong);
                        for (int i = 0; i < h; i++) {
                            beanList.get(i).setbAreaName(true);
                            int k = beanList.get(i).getCity().size();
                            for (int j = 0; j < k; j++) {
                                beanList.get(i).getCity().get(j).setbSelect(true);
                            }
                        }
                    }
                    notifyDataSetChanged();
                }
            });
            //用于notifyDataSetChanged来判断选中状态
            if (cityListBean.isbAll()) {
                ((MyViewHolder1) holder).imageSelect.setImageResource(R.mipmap.xuanzhong);
            } else {
                ((MyViewHolder1) holder).imageSelect.setImageResource(R.mipmap.un_xuanzhong);
            }


        } else if (holder instanceof MyViewHolder2) {
            //区域名称
            ((MyViewHolder2) holder).textSelect.setText(beanList.get(position - 1).getArea_name());
            //判断hashmap对应位置adapter是否为空，为空添加一个adapter
            if (hashMap.get(position - 1) == null) {
                GridViewBaseAdapter adapter = new GridViewBaseAdapter(context, beanList.get(position - 1).getCity(), cityListBean);
                hashMap.put(position - 1, adapter);
                ((MyViewHolder2) holder).gridView.setAdapter(adapter);
            } else {//不为空，直接setAdapter（有效避免重复new一个Adapter）
                ((MyViewHolder2) holder).gridView.setAdapter(hashMap.get(position - 1));
            }
            //用于notifyDataSetChanged来判断区域全选按钮状态
            if (beanList.get(position - 1).isbAreaName()) {
                ((MyViewHolder2) holder).imageSelect.setImageResource(R.mipmap.xuanzhong);
            } else {
                ((MyViewHolder2) holder).imageSelect.setImageResource(R.mipmap.un_xuanzhong);
            }
            //记录当前position位置信息
            ((MyViewHolder2) holder).linearSelect.setTag(R.id.tag_first, position - 1);
            ((MyViewHolder2) holder).linearSelect.setTag(R.id.tag_second, holder);
            ((MyViewHolder2) holder).imageSelect.setTag(R.id.tag_first, position - 1);
            ((MyViewHolder2) holder).imageSelect.setTag(R.id.tag_second, holder);

            hashMap.get(position - 1).setOnItemClickListener(new GridViewBaseAdapter.OnItemClickListener() {
                @Override
                public void onClick(boolean b) {
                    if (b) {
                        ((MyViewHolder2) holder).imageSelect.setImageResource(R.mipmap.xuanzhong);
                        beanList.get(position - 1).setbAreaName(true);
                    } else {
                        ((MyViewHolder2) holder).imageSelect.setImageResource(R.mipmap.un_xuanzhong);
                        beanList.get(position - 1).setbAreaName(false);
                    }
                }
            });
            //用于顶部全选按钮回调显示
            hashMap.get(position - 1).setOnItemClickAllListener(new GridViewBaseAdapter.OnItemClickAllListener() {
                @Override
                public void onClick(boolean b) {
                    if (b) {
                        cityListBean.setbAll(true);
                    } else {
                        cityListBean.setbAll(false);
                    }
                    notifyDataSetChanged();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_select:
            case R.id.text_select:
            case R.id.linear_select:
                //位置
                int position = (int) v.getTag(R.id.tag_first);
                MyViewHolder2 holder2 = (MyViewHolder2) v.getTag(R.id.tag_second);
                //区域城市集合数量
                int numSize = beanList.get(position).getCity().size();
                //判断选中状态
                if (beanList.get(position).isbAreaName()) {
                    (holder2).imageSelect.setImageResource(R.mipmap.un_xuanzhong);
                    beanList.get(position).setbAreaName(false);
                    for (int i = 0; i < numSize; i++) {
                        beanList.get(position).getCity().get(i).setbSelect(false);
                    }
                } else {
                    (holder2).imageSelect.setImageResource(R.mipmap.xuanzhong);
                    beanList.get(position).setbAreaName(true);
                    for (int i = 0; i < numSize; i++) {
                        beanList.get(position).getCity().get(i).setbSelect(true);
                    }
                }
                if (beanList.toString().contains("false")){
                    cityListBean.setbAll(false);
                }else {
                    cityListBean.setbAll(true);
                }
                notifyDataSetChanged();

                break;
        }
    }

    class MyViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.image_select)
        ImageView imageSelect;
        @BindView(R.id.linear_select)
        LinearLayout linearSelect;
        @BindView(R.id.text_select)
        TextView textSelect;

        public MyViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.image_select)
        ImageView imageSelect;
        @BindView(R.id.linear_select)
        LinearLayout linearSelect;
        @BindView(R.id.text_select)
        TextView textSelect;
        @BindView(R.id.grid_view)
        MyGridView gridView;

        public MyViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 清空所有选择的城市
     */
    public void clearAllSelectCity(){
        int h = beanList.size();
        cityListBean.setbAll(false);
        for (int i = 0; i < h; i++) {
            beanList.get(i).setbAreaName(false);
            int k = beanList.get(i).getCity().size();
            for (int j = 0; j < k; j++) {
                beanList.get(i).getCity().get(j).setbSelect(false);
            }
        }
        notifyDataSetChanged();
    }
}
