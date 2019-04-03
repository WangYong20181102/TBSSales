package com.tbs.sales.bean;

/**
 * Created by Mr.Wang on 2019/4/3 15:37.
 */
public class KeyValueDataBean {
    private String name;
    private int id;

    public KeyValueDataBean(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
