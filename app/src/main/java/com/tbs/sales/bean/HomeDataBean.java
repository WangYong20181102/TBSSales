package com.tbs.sales.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mr.Wang on 2019/2/27 13:58.
 */
public class HomeDataBean {
    private int all_count;
    private String page;
    private String page_size;
    private int can_grab_num;
    private StateInfoBean state_info;
    private int follow_num;
    private int today_num;
    private int warn_num;
    private int all_self_num;
    private int finish_num;
    private int range_type;
    private int range_id;
    private String range_name;
    private List<ListBean> list;

    public int getAll_count() {
        return all_count;
    }

    public void setAll_count(int all_count) {
        this.all_count = all_count;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPage_size() {
        return page_size;
    }

    public void setPage_size(String page_size) {
        this.page_size = page_size;
    }

    public int getCan_grab_num() {
        return can_grab_num;
    }

    public void setCan_grab_num(int can_grab_num) {
        this.can_grab_num = can_grab_num;
    }

    public StateInfoBean getState_info() {
        return state_info;
    }

    public void setState_info(StateInfoBean state_info) {
        this.state_info = state_info;
    }

    public int getFollow_num() {
        return follow_num;
    }

    public void setFollow_num(int follow_num) {
        this.follow_num = follow_num;
    }

    public int getToday_num() {
        return today_num;
    }

    public void setToday_num(int today_num) {
        this.today_num = today_num;
    }

    public int getWarn_num() {
        return warn_num;
    }

    public void setWarn_num(int warn_num) {
        this.warn_num = warn_num;
    }

    public int getAll_self_num() {
        return all_self_num;
    }

    public void setAll_self_num(int all_self_num) {
        this.all_self_num = all_self_num;
    }

    public int getFinish_num() {
        return finish_num;
    }

    public void setFinish_num(int finish_num) {
        this.finish_num = finish_num;
    }

    public int getRange_type() {
        return range_type;
    }

    public void setRange_type(int range_type) {
        this.range_type = range_type;
    }

    public int getRange_id() {
        return range_id;
    }

    public void setRange_id(int range_id) {
        this.range_id = range_id;
    }

    public String getRange_name() {
        return range_name;
    }

    public void setRange_name(String range_name) {
        this.range_name = range_name;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class StateInfoBean {
        /**
         * follow_num : 15
         * today_num : 0
         * warn_num : 0
         * all_self_num : 17
         * no_touch_num : 5
         * yet_follow_num : 10
         * finish_num : 5
         * audit_num : 1
         * warn_no_follow_num : 0
         * warn_no_finish_num : 0
         * delay_num : 20
         * already_delay_num : 1
         */

        private int follow_num;
        private int today_num;
        private int warn_num;
        private int all_self_num;
        private int no_touch_num;
        private int yet_follow_num;
        private int finish_num;
        private int audit_num;
        private int warn_no_follow_num;
        private int warn_no_finish_num;
        private int delay_num;
        private int already_delay_num;


        public int getFollow_num() {
            return follow_num;
        }

        public void setFollow_num(int follow_num) {
            this.follow_num = follow_num;
        }

        public int getToday_num() {
            return today_num;
        }

        public void setToday_num(int today_num) {
            this.today_num = today_num;
        }

        public int getWarn_num() {
            return warn_num;
        }

        public void setWarn_num(int warn_num) {
            this.warn_num = warn_num;
        }

        public int getAll_self_num() {
            return all_self_num;
        }

        public void setAll_self_num(int all_self_num) {
            this.all_self_num = all_self_num;
        }

        public int getNo_touch_num() {
            return no_touch_num;
        }

        public void setNo_touch_num(int no_touch_num) {
            this.no_touch_num = no_touch_num;
        }

        public int getYet_follow_num() {
            return yet_follow_num;
        }

        public void setYet_follow_num(int yet_follow_num) {
            this.yet_follow_num = yet_follow_num;
        }

        public int getFinish_num() {
            return finish_num;
        }

        public void setFinish_num(int finish_num) {
            this.finish_num = finish_num;
        }

        public int getAudit_num() {
            return audit_num;
        }

        public void setAudit_num(int audit_num) {
            this.audit_num = audit_num;
        }

        public int getWarn_no_follow_num() {
            return warn_no_follow_num;
        }

        public void setWarn_no_follow_num(int warn_no_follow_num) {
            this.warn_no_follow_num = warn_no_follow_num;
        }

        public int getWarn_no_finish_num() {
            return warn_no_finish_num;
        }

        public void setWarn_no_finish_num(int warn_no_finish_num) {
            this.warn_no_finish_num = warn_no_finish_num;
        }

        public int getDelay_num() {
            return delay_num;
        }

        public void setDelay_num(int delay_num) {
            this.delay_num = delay_num;
        }

        public int getAlready_delay_num() {
            return already_delay_num;
        }

        public void setAlready_delay_num(int already_delay_num) {
            this.already_delay_num = already_delay_num;
        }
    }

    public static class ListBean {

        private int co_id;
        private int com_id;
        private int co_type;
        private String co_name;
        private String name;
        private int sex;
        private String contacts;
        private String phone;
        private String tel;
        private String weixin;
        private String email;
        private String address;
        private int province;
        private int evaluate;
        private int city;
        private int county;
        private String province_name;
        private String city_name;
        private String county_name;
        private int add_uid;
        private String pre_time;
        private String follow_time;
        private Object finish_time;
        private Object create_time;
        private String update_time;
        private int respond_uid;
        private int source;
        private int audit_state;
        private int exp_id;
        private int grab_id;
        private int tb_grab_state;
        private int warn_state;
        private int warn_time;
        private int follow_delay;
        private int finish_delay;
        private int delay_state;
        private int grab_add_uid;
        private String grab_add_name;
        private String grab_add_time;
        private int follow_times;
        private String respond_name;
        private String add_name;
        private String source_name;
        private String co_type_name;
        private String audit_state_name;
        private int area_id;
        private String area_name;
        private String grab_state_name;
        private String fm_warn_time;
        private String warn_state_desc;
        private String callphone;
        private String sphone;
        private List<?> respond_list;

        public int getCo_id() {
            return co_id;
        }

        public void setCo_id(int co_id) {
            this.co_id = co_id;
        }

        public int getCom_id() {
            return com_id;
        }

        public void setCom_id(int com_id) {
            this.com_id = com_id;
        }

        public int getCo_type() {
            return co_type;
        }

        public void setCo_type(int co_type) {
            this.co_type = co_type;
        }

        public String getCo_name() {
            return co_name;
        }

        public void setCo_name(String co_name) {
            this.co_name = co_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getWeixin() {
            return weixin;
        }

        public void setWeixin(String weixin) {
            this.weixin = weixin;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getProvince() {
            return province;
        }

        public void setProvince(int province) {
            this.province = province;
        }

        public int getEvaluate() {
            return evaluate;
        }

        public void setEvaluate(int evaluate) {
            this.evaluate = evaluate;
        }

        public int getCity() {
            return city;
        }

        public void setCity(int city) {
            this.city = city;
        }

        public int getCounty() {
            return county;
        }

        public void setCounty(int county) {
            this.county = county;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getCounty_name() {
            return county_name;
        }

        public void setCounty_name(String county_name) {
            this.county_name = county_name;
        }

        public int getAdd_uid() {
            return add_uid;
        }

        public void setAdd_uid(int add_uid) {
            this.add_uid = add_uid;
        }

        public String getPre_time() {
            return pre_time;
        }

        public void setPre_time(String pre_time) {
            this.pre_time = pre_time;
        }

        public String getFollow_time() {
            return follow_time;
        }

        public void setFollow_time(String follow_time) {
            this.follow_time = follow_time;
        }

        public Object getFinish_time() {
            return finish_time;
        }

        public void setFinish_time(Object finish_time) {
            this.finish_time = finish_time;
        }

        public Object getCreate_time() {
            return create_time;
        }

        public void setCreate_time(Object create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public int getRespond_uid() {
            return respond_uid;
        }

        public void setRespond_uid(int respond_uid) {
            this.respond_uid = respond_uid;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getAudit_state() {
            return audit_state;
        }

        public void setAudit_state(int audit_state) {
            this.audit_state = audit_state;
        }

        public int getExp_id() {
            return exp_id;
        }

        public void setExp_id(int exp_id) {
            this.exp_id = exp_id;
        }

        public int getGrab_id() {
            return grab_id;
        }

        public void setGrab_id(int grab_id) {
            this.grab_id = grab_id;
        }

        public int getTb_grab_state() {
            return tb_grab_state;
        }

        public void setTb_grab_state(int tb_grab_state) {
            this.tb_grab_state = tb_grab_state;
        }

        public int getWarn_state() {
            return warn_state;
        }

        public void setWarn_state(int warn_state) {
            this.warn_state = warn_state;
        }

        public int getWarn_time() {
            return warn_time;
        }

        public void setWarn_time(int warn_time) {
            this.warn_time = warn_time;
        }

        public int getFollow_delay() {
            return follow_delay;
        }

        public void setFollow_delay(int follow_delay) {
            this.follow_delay = follow_delay;
        }

        public int getFinish_delay() {
            return finish_delay;
        }

        public void setFinish_delay(int finish_delay) {
            this.finish_delay = finish_delay;
        }

        public int getDelay_state() {
            return delay_state;
        }

        public void setDelay_state(int delay_state) {
            this.delay_state = delay_state;
        }

        public int getGrab_add_uid() {
            return grab_add_uid;
        }

        public void setGrab_add_uid(int grab_add_uid) {
            this.grab_add_uid = grab_add_uid;
        }

        public String getGrab_add_name() {
            return grab_add_name;
        }

        public void setGrab_add_name(String grab_add_name) {
            this.grab_add_name = grab_add_name;
        }

        public String getGrab_add_time() {
            return grab_add_time;
        }

        public void setGrab_add_time(String grab_add_time) {
            this.grab_add_time = grab_add_time;
        }

        public int getFollow_times() {
            return follow_times;
        }

        public void setFollow_times(int follow_times) {
            this.follow_times = follow_times;
        }

        public String getRespond_name() {
            return respond_name;
        }

        public void setRespond_name(String respond_name) {
            this.respond_name = respond_name;
        }

        public String getAdd_name() {
            return add_name;
        }

        public void setAdd_name(String add_name) {
            this.add_name = add_name;
        }

        public String getSource_name() {
            return source_name;
        }

        public void setSource_name(String source_name) {
            this.source_name = source_name;
        }

        public String getCo_type_name() {
            return co_type_name;
        }

        public void setCo_type_name(String co_type_name) {
            this.co_type_name = co_type_name;
        }

        public String getAudit_state_name() {
            return audit_state_name;
        }

        public void setAudit_state_name(String audit_state_name) {
            this.audit_state_name = audit_state_name;
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

        public String getGrab_state_name() {
            return grab_state_name;
        }

        public void setGrab_state_name(String grab_state_name) {
            this.grab_state_name = grab_state_name;
        }

        public String getFm_warn_time() {
            return fm_warn_time;
        }

        public void setFm_warn_time(String fm_warn_time) {
            this.fm_warn_time = fm_warn_time;
        }

        public String getWarn_state_desc() {
            return warn_state_desc;
        }

        public void setWarn_state_desc(String warn_state_desc) {
            this.warn_state_desc = warn_state_desc;
        }

        public String getCallphone() {
            return callphone;
        }

        public void setCallphone(String callphone) {
            this.callphone = callphone;
        }

        public String getSphone() {
            return sphone;
        }

        public void setSphone(String sphone) {
            this.sphone = sphone;
        }

        public List<?> getRespond_list() {
            return respond_list;
        }

        public void setRespond_list(List<?> respond_list) {
            this.respond_list = respond_list;
        }
    }
}
