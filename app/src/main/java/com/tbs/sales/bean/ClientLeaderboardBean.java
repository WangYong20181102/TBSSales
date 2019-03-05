package com.tbs.sales.bean;

import java.util.List;

/**
 * Created by Mr.Wang on 2019/3/4 11:06.
 * 排行榜
 */
public class ClientLeaderboardBean {


    private List<FinishListBean> finish_list;
    private List<FollowListBean> follow_list;
    private List<AddListBean> add_list;

    public List<FinishListBean> getFinish_list() {
        return finish_list;
    }

    public void setFinish_list(List<FinishListBean> finish_list) {
        this.finish_list = finish_list;
    }

    public List<FollowListBean> getFollow_list() {
        return follow_list;
    }

    public void setFollow_list(List<FollowListBean> follow_list) {
        this.follow_list = follow_list;
    }

    public List<AddListBean> getAdd_list() {
        return add_list;
    }

    public void setAdd_list(List<AddListBean> add_list) {
        this.add_list = add_list;
    }

    public static class FinishListBean {
        /**
         * sort : 1
         * member : 周建平
         * area_name : 华中区,邀约组
         * finish_num : 0
         */

        private int sort;
        private String member;
        private String area_name;
        private int finish_num;

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getMember() {
            return member;
        }

        public void setMember(String member) {
            this.member = member;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public int getFinish_num() {
            return finish_num;
        }

        public void setFinish_num(int finish_num) {
            this.finish_num = finish_num;
        }
    }

    public static class FollowListBean {
        /**
         * sort : 1
         * member : 周建平
         * area_name : 华中区,邀约组
         * follow_num : 0
         */

        private int sort;
        private String member;
        private String area_name;
        private int follow_num;

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getMember() {
            return member;
        }

        public void setMember(String member) {
            this.member = member;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public int getFollow_num() {
            return follow_num;
        }

        public void setFollow_num(int follow_num) {
            this.follow_num = follow_num;
        }
    }

    public static class AddListBean {
        /**
         * sort : 1
         * member : 周建平
         * area_name : 华中区,邀约组
         * add_num : 0
         */

        private int sort;
        private String member;
        private String area_name;
        private int add_num;

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getMember() {
            return member;
        }

        public void setMember(String member) {
            this.member = member;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public int getAdd_num() {
            return add_num;
        }

        public void setAdd_num(int add_num) {
            this.add_num = add_num;
        }
    }
}
