package com.tbs.sales.bean;

import java.io.Serializable;

/**
 * Created by Mr.Wang on 2019/4/2 14:44.
 */
public class PeopleBean implements Serializable {

    /**
     * id : 3
     * name : 胡晓泉
     * email_account : spring.hu@tobosu.cn
     */

    private int id;
    private String name;
    private String email_account;
    private int can_grab;

    public int getCan_grab() {
        return can_grab;
    }

    public void setCan_grab(int can_grab) {
        this.can_grab = can_grab;
    }

    public PeopleBean(int s, String s1) {
        this.id = s;
        this.name = s1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail_account() {
        return email_account;
    }

    public void setEmail_account(String email_account) {
        this.email_account = email_account;
    }
}
