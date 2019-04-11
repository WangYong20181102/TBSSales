package com.tbs.sales.bean;

import java.util.ArrayList;

/**
 * Created by Mr.Wang on 2019/4/4 09:09.
 */
public class _UpdateInfo {
    private String type;
    private String apk_url;
    private String is_update;
    private ArrayList<String> content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApk_url() {
        return apk_url;
    }

    public void setApk_url(String apk_url) {
        this.apk_url = apk_url;
    }

    public String getIs_update() {
        return is_update;
    }

    public void setIs_update(String is_update) {
        this.is_update = is_update;
    }

    public ArrayList<String> getContent() {
        return content;
    }

    public void setContent(ArrayList<String> content) {
        this.content = content;
    }
}
