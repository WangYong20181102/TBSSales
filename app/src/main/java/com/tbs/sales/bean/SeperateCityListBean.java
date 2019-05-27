package com.tbs.sales.bean;

import java.util.List;

/**
 * Created by Mr.Wang on 2019/5/24 09:15.
 */
public class SeperateCityListBean {


    /**
     * 全选
     */
    private boolean bAll = false;
    private List<ListBean> list;

    public boolean isbAll() {
        return bAll;
    }

    public void setbAll(boolean bAll) {
        this.bAll = bAll;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {

        /**
         * area_id : 1
         * area_name : 华东区
         * city : [{"city_id":73,"city_name":"上海","area_id":1},{"city_id":87,"city_name":"杭州","area_id":1},{"city_id":88,"city_name":"宁波","area_id":1},{"city_id":89,"city_name":"温州","area_id":1},{"city_id":90,"city_name":"嘉兴","area_id":1},{"city_id":91,"city_name":"湖州","area_id":1},{"city_id":92,"city_name":"绍兴","area_id":1},{"city_id":93,"city_name":"金华","area_id":1},{"city_id":94,"city_name":"衢州","area_id":1},{"city_id":95,"city_name":"舟山","area_id":1},{"city_id":96,"city_name":"台州","area_id":1},{"city_id":97,"city_name":"丽水","area_id":1},{"city_id":115,"city_name":"福州","area_id":1},{"city_id":116,"city_name":"厦门","area_id":1},{"city_id":117,"city_name":"莆田","area_id":1},{"city_id":118,"city_name":"三明","area_id":1},{"city_id":119,"city_name":"泉州","area_id":1},{"city_id":120,"city_name":"漳州","area_id":1},{"city_id":121,"city_name":"南平","area_id":1},{"city_id":122,"city_name":"龙岩","area_id":1},{"city_id":123,"city_name":"宁德","area_id":1},{"city_id":345,"city_name":"台北","area_id":1},{"city_id":353,"city_name":"福清","area_id":1},{"city_id":358,"city_name":"余姚","area_id":1},{"city_id":359,"city_name":"慈溪","area_id":1},{"city_id":364,"city_name":"永康","area_id":1},{"city_id":488,"city_name":"台湾","area_id":1}]
         */

        private int area_id;
        private String area_name;
        private boolean bAreaName = false;
        private List<CityBean> city;

        public boolean isbAreaName() {
            return bAreaName;
        }

        public void setbAreaName(boolean bAreaName) {
            this.bAreaName = bAreaName;
        }

        public int getArea_id() {
            return area_id;
        }

        public void setArea_id(int area_id) {
            this.area_id = area_id;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public List<CityBean> getCity() {
            return city;
        }

        public void setCity(List<CityBean> city) {
            this.city = city;
        }

        public static class CityBean {
            /**
             * city_id : 73
             * city_name : 上海
             * area_id : 1
             */

            private int city_id;
            private String city_name;
            private int area_id;
            /**
             * 是否选中
             */
            private boolean bSelect = false;

            public boolean isbSelect() {
                return bSelect;
            }

            public void setbSelect(boolean bSelect) {
                this.bSelect = bSelect;
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

            public int getArea_id() {
                return area_id;
            }

            public void setArea_id(int area_id) {
                this.area_id = area_id;
            }

            @Override
            public String toString() {
                return "CityBean{" +
                        "city_id=" + city_id +
                        ", city_name='" + city_name + '\'' +
                        ", area_id=" + area_id +
                        ", bSelect=" + bSelect +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "area_id=" + area_id +
                    ", area_name='" + area_name + '\'' +
                    ", bAreaName=" + bAreaName +
                    ", city=" + city +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SeperateCityListBean{" +
                "bAll=" + bAll +
                ", list=" + list +
                '}';
    }
}
