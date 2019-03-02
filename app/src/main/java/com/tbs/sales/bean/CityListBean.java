package com.tbs.sales.bean;

/**
 * Created by Mr.Wang on 2019/3/2 16:10.
 */
public class CityListBean {
    private String id;
    private String nm;
    private String py;
    private String firstPY;
    private String hot;

    public CityListBean(String id, String nm, String py, String firstPY, String hot) {
        super();
        this.id = id;
        this.nm = nm;
        this.py = py;
        this.firstPY = firstPY;
        this.hot = hot;
    }

    public String getId() {
        return id;
    }

    public String getNm() {
        return nm;
    }

    public String getPy() {
        return py;
    }

    public String getHot() {
        return hot;
    }
}
