package com.tbs.sales.adapter;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr.Wang on 2019/5/24 09:19.
 */
public class SeperateCitySelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<SeperateCityListBean.ListBean> beanList;
    private GridViewBaseAdapter adapter;
    private SeperateCityListBean cityListBean;
    private HashMap<Integer, GridViewBaseAdapter> hashMap;
    private HashMap<Integer, MyGridView> gridViewHashMap;

    public SeperateCitySelectAdapter(Context context, List<SeperateCityListBean.ListBean> beanList, SeperateCityListBean cityListBean) {
        this.context = context;
        this.beanList = beanList;
        hashMap = new HashMap<>();
        gridViewHashMap = new HashMap<>();
        this.cityListBean = cityListBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.top_select_all, parent, false);
            MyViewHolder1 holder1 = new MyViewHolder1(view);
            return holder1;
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
            ((MyViewHolder1) holder).linearSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int h = beanList.size();
                    if (cityListBean.isbAll()) {
                        cityListBean.setbAll(false);
                        ((MyViewHolder1) holder).imageSelect.setImageResource(R.mipmap.un_xuanzhong);
                        for (int i = 0; i < h; i++) {
                            beanList.get(i).setbAreaName(false);
                            int k = beanList.get(i).getCity().size();
                            for (int j = 0; j < k; j++) {
                                beanList.get(i).getCity().get(j).setbSelect(false);
                            }
                        }
                    }else {
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
        } else if (holder instanceof MyViewHolder2) {
            ((MyViewHolder2) holder).textSelect.setText(beanList.get(position - 1).getArea_name());
            if (hashMap.get(position - 1) == null) {
                adapter = new GridViewBaseAdapter(context, beanList.get(position - 1).getCity());
                hashMap.put(position - 1, adapter);
                gridViewHashMap.put(position - 1, ((MyViewHolder2) holder).gridView);
                ((MyViewHolder2) holder).gridView.setAdapter(adapter);
            } else {
                ((MyViewHolder2) holder).gridView.setAdapter(hashMap.get(position - 1));
            }

            if (beanList.get(position - 1).isbAreaName()) {
                ((MyViewHolder2) holder).imageSelect.setImageResource(R.mipmap.xuanzhong);
            } else {
                ((MyViewHolder2) holder).imageSelect.setImageResource(R.mipmap.un_xuanzhong);
            }
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
                int numSize = beanList.get((Integer) v.getTag(R.id.tag_first)).getCity().size();
                if (beanList.get((Integer) v.getTag(R.id.tag_first)).isbAreaName()) {
                    ((MyViewHolder2) v.getTag(R.id.tag_second)).imageSelect.setImageResource(R.mipmap.un_xuanzhong);
                    beanList.get((Integer) v.getTag(R.id.tag_first)).setbAreaName(false);
                    for (int i = 0; i < numSize; i++) {
                        beanList.get((Integer) v.getTag(R.id.tag_first)).getCity().get(i).setbSelect(false);
                    }
                } else {
                    ((MyViewHolder2) v.getTag(R.id.tag_second)).imageSelect.setImageResource(R.mipmap.xuanzhong);
                    beanList.get((Integer) v.getTag(R.id.tag_first)).setbAreaName(true);
                    for (int i = 0; i < numSize; i++) {
                        beanList.get((Integer) v.getTag(R.id.tag_first)).getCity().get(i).setbSelect(true);
                    }
                }
                hashMap.get((Integer) v.getTag(R.id.tag_first)).notifyDataSetChanged();
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
}
