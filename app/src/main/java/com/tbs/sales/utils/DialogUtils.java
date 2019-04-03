package com.tbs.sales.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.activity.GiveUpClientActivity;
import com.tbs.sales.activity.TransferActivity;
import com.tbs.sales.activity.WebViewActivity;
import com.tbs.sales.adapter.BottomSelectItemAdapter;
import com.tbs.sales.adapter.CityMessageAdapter;
import com.tbs.sales.adapter.PeopleChoseAdapter;
import com.tbs.sales.adapter.PeopleMessageAdapter;
import com.tbs.sales.bean.CityBean;
import com.tbs.sales.bean.KeyValueDataBean;
import com.tbs.sales.bean.PeopleBean;
import com.tbs.sales.bean.UserInfoDataBean;
import com.tbs.sales.constant.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mr.Wang on 2019/2/28 13:34.
 */
public class DialogUtils implements View.OnClickListener, TextWatcher {

    private LinearLayout linearBack;    //返回按钮父布局
    private ImageView imageSearch;  //搜索图片
    private EditText editCityMessage;//城市文本输入框
    private ImageView imageDelete;  //内容清除按钮
    private ListView listView;
    private CityMessageAdapter adapter;
    private PeopleMessageAdapter peopleAdapter;
    private static DialogUtils dialogUtils = null;
    private Dialog dialog;
    private List<CityBean> listResult;
    private List<PeopleBean> peopleListResult;
    private Activity activity;
    private LinearLayout linearLayout;

    /**
     * 单例
     *
     * @return
     */
    public static DialogUtils getInstances() {
        if (dialogUtils == null) {
            dialogUtils = new DialogUtils();
        }
        return dialogUtils;
    }

    /**
     * 显示城市列表
     * 有左边距
     *
     * @param context
     * @param listCity
     * @return
     */
    public Dialog showCityMessage(Context context, final List<CityBean> listCity, final OnCityResultListener onCityResultListener) {
        activity = (Activity) context;
        adapter = new CityMessageAdapter(context, listCity);
        listResult = new ArrayList<>();
        //自定义dialog
        dialog = new Dialog(context, R.style.dialogCityMessage);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_city_message, null);
        linearBack = view.findViewById(R.id.linear_back);
        imageSearch = view.findViewById(R.id.image_search);
        editCityMessage = view.findViewById(R.id.edit_city_message);
        imageDelete = view.findViewById(R.id.image_delete);
        listView = view.findViewById(R.id.list_view);
        linearLayout = view.findViewById(R.id.linear_city);
        dialog.setContentView(view);

        //点击事件监听
        linearBack.setOnClickListener(this);
        imageDelete.setOnClickListener(this);
        imageSearch.setOnClickListener(this);

        editCityMessage.addTextChangedListener(this);
        adapter.setListener(new CityMessageAdapter.FilterListener() {
            @Override
            public void getFilterData(List<CityBean> list) {
                listResult = list;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onCityResultListener != null) {
                    if (listResult.size() == 0) {
                        onCityResultListener.onCityResult(listCity.get(position));
                    } else {
                        onCityResultListener.onCityResult(listResult.get(position));
                    }
                    KeyBoardUtil.hideKeyBoard(activity);
                }
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        listView.setAdapter(adapter);

        //设置无边距
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        dialog.show();
        return dialog;
    }

    /**
     * 显示领取人列表
     * 有左边距
     *
     * @param context
     * @return
     */
    public Dialog showPeopleMessage(Context context, final List<PeopleBean> peopleList, final OnPeopleResultListener onPeopleResultListener) {
        activity = (Activity) context;
        peopleAdapter = new PeopleMessageAdapter(context, peopleList);
        peopleListResult = new ArrayList<>();
        //自定义dialog
        dialog = new Dialog(context, R.style.dialogCityMessage);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_people_message, null);
        LinearLayout linearBack = view.findViewById(R.id.linear_back);
        final EditText editPeopleMessage = view.findViewById(R.id.edit_people_message);
        final ImageView imageDelete = view.findViewById(R.id.image_delete);
        ListView listView = view.findViewById(R.id.list_view);
        dialog.setContentView(view);

        //点击事件监听
        linearBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtil.hideKeyBoard(activity);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPeopleMessage.setText("");
            }
        });

        editPeopleMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    imageDelete.setVisibility(View.VISIBLE);
                } else {
                    imageDelete.setVisibility(View.GONE);
                }
                if (peopleAdapter != null) {
                    peopleAdapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        peopleAdapter.setListener(new PeopleMessageAdapter.FilterListener() {
            @Override
            public void getFilterData(List<PeopleBean> list) {
                peopleListResult = list;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onPeopleResultListener != null) {
                    if (peopleListResult.size() == 0) {
                        onPeopleResultListener.onPeopleResult(peopleList.get(position));
                    } else {
                        onPeopleResultListener.onPeopleResult(peopleListResult.get(position));
                    }
                    KeyBoardUtil.hideKeyBoard(activity);
                }
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        listView.setAdapter(peopleAdapter);

        //设置无边距
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        dialog.show();
        return dialog;
    }

    /**
     * 显示领取人列表
     * 无左边距
     *
     * @param context
     * @return
     */
    public Dialog showPeopleList(final Context context, final List<PeopleBean> peopleList, final OnPeopleResultListener onPeopleResultListener) {
        activity = (Activity) context;
        final PeopleChoseAdapter choseAdapter = new PeopleChoseAdapter(context, peopleList);
        peopleListResult = new ArrayList<>();
        //自定义dialog
        dialog = new Dialog(context, R.style.dialogCityMessage);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_people_chose_list, null);
        LinearLayout linearBack = view.findViewById(R.id.linear_back);
        final EditText editPeopleMessage = view.findViewById(R.id.edit_people_message);
        final ImageView imageDelete = view.findViewById(R.id.image_delete);
        ListView listView = view.findViewById(R.id.list_view);
        dialog.setContentView(view);

        //点击事件监听
        linearBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtil.hideKeyBoard(activity);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPeopleMessage.setText("");
            }
        });

        editPeopleMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    imageDelete.setVisibility(View.VISIBLE);
                } else {
                    imageDelete.setVisibility(View.GONE);
                }
                if (choseAdapter != null) {
                    choseAdapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        choseAdapter.setListener(new PeopleChoseAdapter.FilterListener() {
            @Override
            public void getFilterData(List<PeopleBean> list) {
                peopleListResult = list;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (peopleListResult.size() != 0) {
                    if (peopleListResult.get(position).getCan_grab() == 0) {
                        ToastUtils.toastShort(context, "客户数已满额");
                    } else {
                        if (onPeopleResultListener != null) {
                            onPeopleResultListener.onPeopleResult(peopleListResult.get(position));
                            KeyBoardUtil.hideKeyBoard(activity);
                        }
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                } else {
                    if (peopleList.get(position).getCan_grab() == 0) {
                        ToastUtils.toastShort(context, "客户数已满额");
                    } else {
                        if (onPeopleResultListener != null) {
                            onPeopleResultListener.onPeopleResult(peopleList.get(position));
                            KeyBoardUtil.hideKeyBoard(activity);
                        }
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                }
            }
        });
        listView.setAdapter(choseAdapter);

        //设置无边距
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        dialog.show();
        return dialog;
    }

    /**
     * 显示城市列表
     * 无左边距
     *
     * @param context
     * @param listCity
     * @return
     */
    public Dialog showCityList(Context context, final List<CityBean> listCity, final OnCityResultListener onCityResultListener) {
        activity = (Activity) context;
        adapter = new CityMessageAdapter(context, listCity);
        listResult = new ArrayList<>();
        //自定义dialog
        dialog = new Dialog(context, R.style.dialogCityMessage);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_city_list, null);
        linearBack = view.findViewById(R.id.linear_back);
        imageSearch = view.findViewById(R.id.image_search);
        editCityMessage = view.findViewById(R.id.edit_city_message);
        imageDelete = view.findViewById(R.id.image_delete);
        listView = view.findViewById(R.id.list_view);
        linearLayout = view.findViewById(R.id.linear_city);
        dialog.setContentView(view);

        //点击事件监听
        linearBack.setOnClickListener(this);
        imageDelete.setOnClickListener(this);
        imageSearch.setOnClickListener(this);

        editCityMessage.addTextChangedListener(this);
        adapter.setListener(new CityMessageAdapter.FilterListener() {
            @Override
            public void getFilterData(List<CityBean> list) {
                listResult = list;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onCityResultListener != null) {
                    if (listResult.size() == 0) {
                        onCityResultListener.onCityResult(listCity.get(position));
                    } else {
                        onCityResultListener.onCityResult(listResult.get(position));
                    }
                    KeyBoardUtil.hideKeyBoard(activity);
                }
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        listView.setAdapter(adapter);

        //设置无边距
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        dialog.show();
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_back:  //返回
                KeyBoardUtil.hideKeyBoard(activity);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                break;
            case R.id.image_search: //搜索
                break;
            case R.id.image_delete://清除
                editCityMessage.setText("");
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s.toString().trim())) {
            imageDelete.setVisibility(View.VISIBLE);
        } else {
            imageDelete.setVisibility(View.GONE);
        }
        if (adapter != null) {
            adapter.getFilter().filter(s);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    public interface OnCityResultListener {
        void onCityResult(CityBean city);
    }

    public interface OnPeopleResultListener {
        void onPeopleResult(PeopleBean city);
    }

//    /**
//     * 隐藏系统键盘
//     */
//    private void hideSystemKeyBroad() {
//        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//    }

    /**
     * 隐藏系统键盘
     */
    private void hideSystemKeyBroad() {
        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        System.gc();

    }

    /**
     * 客户详情菜单
     *
     * @param context
     * @return
     */
    public Dialog showMenuDialog(final Context context, final UserInfoDataBean dataBean, int type) {
        final Dialog dialog = new Dialog(context, R.style.dialogMenu);
        View view = LayoutInflater.from(context).inflate(R.layout.client_detail_menu, null);
        RelativeLayout relativeBg = view.findViewById(R.id.relative_bg);
        //成交客户
        LinearLayout linearChengjiao = view.findViewById(R.id.linear_chengjiao);
        final TextView tvClient = view.findViewById(R.id.tv_client);
        //放弃客户
        LinearLayout linearOut = view.findViewById(R.id.linear_out);
        TextView tvOut = view.findViewById(R.id.tv_out);
        linearOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                context.startActivity(new Intent(context, GiveUpClientActivity.class));
            }
        });
        //申请延期
        LinearLayout linearYanqi = view.findViewById(R.id.linear_yanqi);
        TextView tvYanqi = view.findViewById(R.id.tv_yanqi);
        linearYanqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (dataBean.getCan_delay_num() == 0) {
                    compatDialog(context, "我知道了", "剩余延期名额为0", new onSureListener() {
                        @Override
                        public void onSure() {

                        }
                    });
                } else {
                    compatDialog(context, "确定", "取消", "确定提交延期申请？", "（当前剩余" + dataBean.getCan_delay_num() + "个延期名额）", new onSureListener() {
                        @Override
                        public void onSure() {

                        }
                    }, new onCancleListener() {
                        @Override
                        public void onCancle() {

                        }
                    });
                }
            }
        });
        //转移
        LinearLayout linearZhuanYi = view.findViewById(R.id.linear_zhuanyi);
        TextView tvZhuanyi = view.findViewById(R.id.tv_zhuanyi);

        linearZhuanYi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Intent intent = new Intent(context, TransferActivity.class);
                intent.putExtra("co_name", dataBean.getCo_name());
                intent.putExtra("grab_desc", dataBean.getGrab_desc());
                context.startActivity(intent);
            }
        });
        //客户类型id,0-新客户、1-潜在（初步接触）、2-持续跟进（归到1）、3-意向（线下跟进）、4-成交、5-无效、6-已签单客户（归到5）、7-暂无意向、8-待签约
        if (type == 2) {
            linearZhuanYi.setVisibility(View.VISIBLE);
            linearYanqi.setVisibility(View.GONE);
            linearOut.setVisibility(View.GONE);
            linearChengjiao.setVisibility(View.GONE);
        } else {
            if (dataBean.getCo_type() != 4) {
                tvClient.setText("成交");
                linearOut.setVisibility(View.VISIBLE);
                //预警状态，0-正常、1-跟进预警、2-成交预警
                if (dataBean.getWarn_state() != 0) {
                    linearYanqi.setVisibility(View.VISIBLE);
                } else {
                    linearYanqi.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(AppInfoUtils.getOrganAreaIds(context).trim())) {
                    linearZhuanYi.setVisibility(View.VISIBLE);
                } else {
                    linearZhuanYi.setVisibility(View.GONE);
                }
            } else {
                linearYanqi.setVisibility(View.GONE);
                linearOut.setVisibility(View.GONE);
                tvClient.setText("续费");
            }
        }


        relativeBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

        linearChengjiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("mLoadingUrl", Constant.BUSINESS_CONTRACT + "?co_id=" + dataBean.getCo_id() + "&co_name" + dataBean.getCo_name());
                context.startActivity(intent);
            }
        });
        dialog.setContentView(view);
        //设置无边距
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.RIGHT | Gravity.TOP);
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        if (dialog.isShowing()) {
            dialog.dismiss();
        } else {
            dialog.show();
        }
        return dialog;
    }

    /**
     * 1个按钮
     *
     * @param context
     * @param textSure
     * @param message
     */
    public void compatDialog(Context context, String textSure, String message, onSureListener onSureListener) {
        showBulletBox(context, textSure, "", message, "", onSureListener, null);
    }

    /**
     * 2个按钮
     *
     * @param context
     * @param textSure
     * @param message
     */
    public void compatDialog(Context context, String textSure, String textCancle, String message, String prompt, onSureListener onSureListener, onCancleListener onCancleListener) {
        showBulletBox(context, textSure, textCancle, message, prompt, onSureListener, onCancleListener);
    }

    private void showBulletBox(Context context, String textSure, String textCancle, String message, String prompt, final onSureListener onSureListener, final onCancleListener onCancleListener) {
        final Dialog dialog = new Dialog(context, R.style.dialogMenu);
        View view = LayoutInflater.from(context).inflate(R.layout.compat_dialog, null);
        LinearLayout linearLayout = view.findViewById(R.id.linear);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        //内容
        TextView tvContent = view.findViewById(R.id.tv_content);
        tvContent.setText(message);
        TextView tvPromat = view.findViewById(R.id.tv_promat);
        if (!TextUtils.isEmpty(prompt.trim())) {
            tvPromat.setVisibility(View.VISIBLE);
            tvPromat.setText(prompt);
        } else {
            tvPromat.setVisibility(View.GONE);
        }
        //单按钮
        LinearLayout linearOne = view.findViewById(R.id.linear_one);
        TextView tvOneSure = view.findViewById(R.id.tv_one_sure);
        tvOneSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSureListener != null) {
                    onSureListener.onSure();
                }
            }
        });
        //双按钮
        LinearLayout linearTwo = view.findViewById(R.id.linear_two);
        TextView tvTwoSure = view.findViewById(R.id.tv_two_sure);
        TextView tvCancle = view.findViewById(R.id.tv_cancle);
        tvTwoSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSureListener != null) {
                    onSureListener.onSure();
                }
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCancleListener != null) {
                    onCancleListener.onCancle();
                }
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        if (TextUtils.isEmpty(textCancle.trim())) {
            linearOne.setVisibility(View.VISIBLE);
            linearTwo.setVisibility(View.GONE);
            tvOneSure.setText(textSure);
        } else {
            linearOne.setVisibility(View.GONE);
            linearTwo.setVisibility(View.VISIBLE);
            tvTwoSure.setText(textSure);
            tvCancle.setText(textCancle);
        }

        dialog.setContentView(view);
        //设置无边距
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
        if (dialog.isShowing()) {
            dialog.dismiss();
        } else {
            dialog.show();
        }
    }

    public interface onSureListener {
        void onSure();
    }

    public interface onCancleListener {
        void onCancle();
    }

    /**
     * 底部选择弹框
     */
    public void showBottomSelect(Context context, final List<KeyValueDataBean> strList, final OnBottomItemSelectListener onBottomItemSelectListener) {
        View view = View.inflate(context, R.layout.bottom_selsect_dialog, null);
        RelativeLayout relativeLayout = view.findViewById(R.id.relative_bg);//父布局
        BottomSelectItemAdapter adapter = new BottomSelectItemAdapter(context, strList);
        ListView listView = view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();
                if (onBottomItemSelectListener != null) {
                    onBottomItemSelectListener.onItemSelect(position);
                }
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public interface OnBottomItemSelectListener {
        void onItemSelect(int position);
    }
}
