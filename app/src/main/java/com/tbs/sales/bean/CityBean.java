package com.tbs.sales.bean;

/**
 * Created by Mr.Wang on 2019/2/28 14:38.
 */
public class CityBean {

    /**
     * id : 2
     * province_id : 2
     * name : 天津市
     * sort_name : 天津
     * month_price : 6666.00
     * order_price : 370.33
     */

    private String id;
    private int province_id;
    private String name;
    private String sort_name;
    private String month_price;
    private String order_price;

    public CityBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort_name() {
        return sort_name;
    }

    public void setSort_name(String sort_name) {
        this.sort_name = sort_name;
    }

    public String getMonth_price() {
        return month_price;
    }

    public void setMonth_price(String month_price) {
        this.month_price = month_price;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }
}
