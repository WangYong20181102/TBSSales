package com.tbs.sales.bean;

import java.io.Serializable;

/**
 * Created by Mr.Wang on 2019/3/1 13:44.
 */
public class MyMessageBean implements Serializable {

    /**
     * id : 2151
     * title :
     * content : 你好啊
     * notice_type : 12
     * city_id : 0
     * city_name :
     * order_id : 0
     * co_id : 0
     * exp_id : 0
     * audit_uid : 0
     * audit_name :
     * create_time : 2019-03-07 10:49:40
     */

    private int id;
    private String title;
    private String content;
    private int notice_type;
    private int city_id;
    private String city_name;
    private int order_id;
    private int co_id;
    private int exp_id;
    private int audit_uid;
    private String audit_name;
    private String create_time;
    private int wf_type;

    public int getWf_type() {
        return wf_type;
    }

    public void setWf_type(int wf_type) {
        this.wf_type = wf_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNotice_type() {
        return notice_type;
    }

    public void setNotice_type(int notice_type) {
        this.notice_type = notice_type;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getCo_id() {
        return co_id;
    }

    public void setCo_id(int co_id) {
        this.co_id = co_id;
    }

    public int getExp_id() {
        return exp_id;
    }

    public void setExp_id(int exp_id) {
        this.exp_id = exp_id;
    }

    public int getAudit_uid() {
        return audit_uid;
    }

    public void setAudit_uid(int audit_uid) {
        this.audit_uid = audit_uid;
    }

    public String getAudit_name() {
        return audit_name;
    }

    public void setAudit_name(String audit_name) {
        this.audit_name = audit_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
