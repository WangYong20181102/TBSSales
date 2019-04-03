package com.tbs.sales.bean;

/**
 * Created by Mr.Wang on 2019/3/30 09:23.
 */
public class CirculationRecordBean {

    /**
     * add_uid : 649
     * add_name : 陈海生
     * add_time : 2019-03-28 14:18:08
     * type : 7
     * type_name : 修改
     * content : 添加跟进记录
     */

    private int add_uid;
    private String add_name;
    private String add_time;
    private int type;
    private String type_name;
    private String content;

    public int getAdd_uid() {
        return add_uid;
    }

    public void setAdd_uid(int add_uid) {
        this.add_uid = add_uid;
    }

    public String getAdd_name() {
        return add_name;
    }

    public void setAdd_name(String add_name) {
        this.add_name = add_name;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
