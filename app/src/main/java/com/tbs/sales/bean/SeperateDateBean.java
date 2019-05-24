package com.tbs.sales.bean;

import java.util.List;

/**
 * Created by Mr.Wang on 2019/5/23 16:22.
 */
public class SeperateDateBean {

    /**
     * page : 1
     * page_size : 20
     */

    private String city;
    private String city_str;
    private List<ListBean> list;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_str() {
        return city_str;
    }

    public void setCity_str(String city_str) {
        this.city_str = city_str;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * orderid : 2807161
         * shi : 197
         * qu : 1749
         * contact : 接机牌
         * housename :
         * housetype : 0
         * roomnumber : 1室1厅
         * effecttime : 2019-05-22 11:22:14
         * housearea : 0
         * last_kefen_time : 2019-05-22 11:22:14
         * dealstatus : 6
         * delay_time : 2017-12-27 23:59:59
         * add_fendan_status : 0
         * chcode :
         * order_time : 2019-05-22 11:22:14
         * status_desc : 可分
         * show_mark : 0
         * qu_name : 越秀区
         * last_time : 1558495334
         * diff_time : 1天前
         */

        private int orderid;
        private int shi;
        private int qu;
        private String contact;
        private String housename;
        private String housetype;
        private String roomnumber;
        private String effecttime;
        private String housearea;
        private String last_kefen_time;
        private int dealstatus;
        private String delay_time;
        private int add_fendan_status;
        private String chcode;
        private String order_time;
        private String status_desc;
        private int show_mark;
        private String qu_name;
        private int last_time;
        private String diff_time;

        public int getOrderid() {
            return orderid;
        }

        public void setOrderid(int orderid) {
            this.orderid = orderid;
        }

        public int getShi() {
            return shi;
        }

        public void setShi(int shi) {
            this.shi = shi;
        }

        public int getQu() {
            return qu;
        }

        public void setQu(int qu) {
            this.qu = qu;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getHousename() {
            return housename;
        }

        public void setHousename(String housename) {
            this.housename = housename;
        }

        public String getHousetype() {
            return housetype;
        }

        public void setHousetype(String housetype) {
            this.housetype = housetype;
        }

        public String getRoomnumber() {
            return roomnumber;
        }

        public void setRoomnumber(String roomnumber) {
            this.roomnumber = roomnumber;
        }

        public String getEffecttime() {
            return effecttime;
        }

        public void setEffecttime(String effecttime) {
            this.effecttime = effecttime;
        }

        public String getHousearea() {
            return housearea;
        }

        public void setHousearea(String housearea) {
            this.housearea = housearea;
        }

        public String getLast_kefen_time() {
            return last_kefen_time;
        }

        public void setLast_kefen_time(String last_kefen_time) {
            this.last_kefen_time = last_kefen_time;
        }

        public int getDealstatus() {
            return dealstatus;
        }

        public void setDealstatus(int dealstatus) {
            this.dealstatus = dealstatus;
        }

        public String getDelay_time() {
            return delay_time;
        }

        public void setDelay_time(String delay_time) {
            this.delay_time = delay_time;
        }

        public int getAdd_fendan_status() {
            return add_fendan_status;
        }

        public void setAdd_fendan_status(int add_fendan_status) {
            this.add_fendan_status = add_fendan_status;
        }

        public String getChcode() {
            return chcode;
        }

        public void setChcode(String chcode) {
            this.chcode = chcode;
        }

        public String getOrder_time() {
            return order_time;
        }

        public void setOrder_time(String order_time) {
            this.order_time = order_time;
        }

        public String getStatus_desc() {
            return status_desc;
        }

        public void setStatus_desc(String status_desc) {
            this.status_desc = status_desc;
        }

        public int getShow_mark() {
            return show_mark;
        }

        public void setShow_mark(int show_mark) {
            this.show_mark = show_mark;
        }

        public String getQu_name() {
            return qu_name;
        }

        public void setQu_name(String qu_name) {
            this.qu_name = qu_name;
        }

        public int getLast_time() {
            return last_time;
        }

        public void setLast_time(int last_time) {
            this.last_time = last_time;
        }

        public String getDiff_time() {
            return diff_time;
        }

        public void setDiff_time(String diff_time) {
            this.diff_time = diff_time;
        }
    }
}
