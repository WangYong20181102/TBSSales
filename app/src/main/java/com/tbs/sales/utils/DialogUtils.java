package com.tbs.sales.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tbs.sales.R;
import com.tbs.sales.adapter.CityMessageAdapter;
import com.tbs.sales.bean.CityBean;

import java.util.ArrayList;
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
    private static DialogUtils dialogUtils = null;
    private Dialog dialog;
    private List<CityBean> listResult;
    private OnCityResultListener onCityResultListener = null;
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
     *
     * @param context
     * @param listCity
     * @return
     */
    public Dialog showCityMessage(Context context, final List<CityBean> listCity) {
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

    public void setOnCityResultListener(OnCityResultListener onCityResultListener) {
        this.onCityResultListener = onCityResultListener;
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

}
