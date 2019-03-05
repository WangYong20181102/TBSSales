package com.tbs.sales.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.activity.HomeSearchActivity;
import com.tbs.sales.activity.LoginActivity;
import com.tbs.sales.bean.HomeDataBean;
import com.tbs.sales.utils.AppInfoUtils;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Mr.Wang on 2019/2/27 13:51.
 */
public class HomeMineFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<HomeDataBean.ListBean> beanList;
    private int tittleHeight;


    public static interface OnFilterClickListener {
        void onFilterClick(View view);
    }

    private static OnFilterClickListener onFilterClick = null;

    //用于筛选按钮回调事件
    public static void setOnFilterClickListener(OnFilterClickListener onFilterClickListener) {
        onFilterClick = onFilterClickListener;
    }

    public HomeMineFragmentAdapter(Context context, List<HomeDataBean.ListBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.my_head_content, parent, false);
            MyViewHolder1 holder1 = new MyViewHolder1(view);
            holder1.imageFilter.setOnClickListener(this);
            if (beanList.size() == 0){
                holder1.viewBg.setVisibility(View.GONE);
            }else {
                holder1.viewBg.setVisibility(View.VISIBLE);
            }
            return holder1;
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.my_content, parent, false);
            MyViewHolder2 holder2 = new MyViewHolder2(view);
            return holder2;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder1) {
            //获取输入框高度
            ((MyViewHolder1) holder).relativeClick.post(new Runnable() {
                @Override
                public void run() {
                    tittleHeight = ((MyViewHolder1) holder).relativeClick.getMeasuredHeight();
                }
            });
            //文本输入框父布局点击事件
            ((MyViewHolder1) holder).relativeClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(AppInfoUtils.getId(context))) {
                        context.startActivity(new Intent(context, LoginActivity.class));
                    } else {
                        context.startActivity(new Intent(context, HomeSearchActivity.class));
                    }
                }
            });
        } else if (holder instanceof MyViewHolder2) {
            //公司名称
            ((MyViewHolder2) holder).textCompanyName.setText(beanList.get(position - 1).getCo_name());
            //右侧信息
            ((MyViewHolder2) holder).textRemarks.setText(beanList.get(position - 1).getCo_type_name());
            //id
            ((MyViewHolder2) holder).textId.setText(beanList.get(position - 1).getCo_id() + "");
            //姓名
            ((MyViewHolder2) holder).textUserName.setText(beanList.get(position - 1).getName());
            //地址
            ((MyViewHolder2) holder).textAddress.setText(beanList.get(position - 1).getAddress());
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

    private class MyViewHolder1 extends RecyclerView.ViewHolder {

        private RelativeLayout relativeClick;
        private LinearLayout imageFilter;//筛选
        private View viewBg;

        public MyViewHolder1(View itemView) {
            super(itemView);
            relativeClick = itemView.findViewById(R.id.relative_click);
            imageFilter = itemView.findViewById(R.id.image_filter);
            viewBg = itemView.findViewById(R.id.view_bg);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_filter:
                if (TextUtils.isEmpty(AppInfoUtils.getId(context))) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                } else {
                    if (onFilterClick != null) {
                        onFilterClick.onFilterClick(v);
                    }
                }
                break;
        }
    }

    /**
     * 高度
     *
     * @return
     */
    public int getTittleHeight() {
        return tittleHeight;
    }
}
