package com.tbs.sales.utils;

import android.content.Context;

import com.tbs.sales.bean.CityListBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Wang on 2019/3/2 15:20.
 */
public class CityUtils {
    private static List<CityListBean> listBeans;

    public static List<CityListBean> getAllCity(Context context) {
        listBeans = new ArrayList<>();
        InputStream is = null;
        try {
            is = context.getAssets().open("cities.json");
            int len = 0;
            byte[] buf = new byte[1024];
            StringBuilder builder = new StringBuilder();
            while ((len = is.read(buf)) != -1) {
                builder.append(new String(buf, 0, len));
            }
            JSONObject json = new JSONObject(builder.toString());
            JSONArray cities = json.getJSONArray("data");
            int length = cities.length();
            for (int i = 0; i < length; i++) {
                JSONObject cityObj = cities.getJSONObject(i);
                String id = cityObj.getString("cityid");
                String nm = cityObj.getString("simpname");
                String py = cityObj.getString("pinyin_sort");
                String hot = cityObj.getString("hot_flag");
                CityListBean city = new CityListBean(id, nm, py, py.toUpperCase(), hot);
                listBeans.add(city);
            }
            return listBeans;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
