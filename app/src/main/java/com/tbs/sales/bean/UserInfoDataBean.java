package com.tbs.sales.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.Wang on 2019/4/3 09:41.
 */
public class UserInfoDataBean implements Serializable {

    /**
     * co_id : 300
     * com_id : 0
     * co_type : 8
     * co_name : 中国装饰公司
     * name : 熊先生
     * sex : 0
     * contacts : 17781578145
     * phone : 17781578145
     * tel :
     * weixin :
     * wx_flag : 0
     * email :
     * address :
     * province : 19
     * city : 199
     * county : 0
     * province_name : 广东省
     * city_name : 深圳市
     * county_name :
     * respond_uid : 649
     * respond_name : 陈海生
     * maintain_uid : 0
     * maintain_name :
     * add_uid : 0
     * pre_time : 2019-03-28 14:16:59
     * follow_time : 2019-03-28 14:18:08
     * finish_time : null
     * renew_time : 0000-00-00 00:00:00
     * create_time : null
     * update_time : 2019-04-01 14:11:02
     * is_delete : 0
     * source : 4
     * invalid_reason : 0
     * renew_warn : 0
     * is_renew : 0
     * is_grab : 1
     * is_black : 0
     * evaluate : 0
     * audit_state : 0
     * exp_id : 0
     * serv_range : 2
     * has_serv_plat : 0
     * co_type_name : 待签约
     * source_name : 商务合作(PC)
     * audit_state_name : 未发起
     * is_ben_grab : 0
     * warn_state : 0
     * delay_state : 0
     * respond_list : []
     * can_delay_num : 19
     * grab_desc : 陈海生
     * trans_desc :
     * trans_time :
     * grab_time : 2019-01-25 15:58:06
     * grab_type_desc : 由“陈海生”指派
     * real_phone : 17781578145
     * sphone : 17781578145
     * callphone :
     * is_invite : 0
     * add_name :
     * is_hide_phone : 0
     */

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
    private int wx_flag;
    private String email;
    private String address;
    private int province;
    private int city;
    private int county;
    private String province_name;
    private String city_name;
    private String county_name;
    private int respond_uid;
    private String respond_name;
    private int maintain_uid;
    private String maintain_name;
    private int add_uid;
    private String pre_time;
    private String follow_time;
    private String finish_time;
    private String renew_time;
    private String create_time;
    private String update_time;
    private int is_delete;
    private int source;
    private int invalid_reason;
    private int renew_warn;
    private int is_renew;
    private int is_grab;
    private int is_black;
    private int evaluate;
    private int audit_state;
    private int exp_id;
    private int serv_range;
    private int has_serv_plat;
    private String co_type_name;
    private String source_name;
    private String audit_state_name;
    private int is_ben_grab;
    private int warn_state;
    private int delay_state;
    private int can_delay_num;
    private String grab_desc;
    private String trans_desc;
    private String trans_time;
    private String grab_time;
    private String grab_type_desc;
    private String real_phone;
    private String sphone;
    private String callphone;
    private int is_invite;
    private String add_name;
    private int is_hide_phone;
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

    public int getWx_flag() {
        return wx_flag;
    }

    public void setWx_flag(int wx_flag) {
        this.wx_flag = wx_flag;
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

    public int getRespond_uid() {
        return respond_uid;
    }

    public void setRespond_uid(int respond_uid) {
        this.respond_uid = respond_uid;
    }

    public String getRespond_name() {
        return respond_name;
    }

    public void setRespond_name(String respond_name) {
        this.respond_name = respond_name;
    }

    public int getMaintain_uid() {
        return maintain_uid;
    }

    public void setMaintain_uid(int maintain_uid) {
        this.maintain_uid = maintain_uid;
    }

    public String getMaintain_name() {
        return maintain_name;
    }

    public void setMaintain_name(String maintain_name) {
        this.maintain_name = maintain_name;
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

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getRenew_time() {
        return renew_time;
    }

    public void setRenew_time(String renew_time) {
        this.renew_time = renew_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getInvalid_reason() {
        return invalid_reason;
    }

    public void setInvalid_reason(int invalid_reason) {
        this.invalid_reason = invalid_reason;
    }

    public int getRenew_warn() {
        return renew_warn;
    }

    public void setRenew_warn(int renew_warn) {
        this.renew_warn = renew_warn;
    }

    public int getIs_renew() {
        return is_renew;
    }

    public void setIs_renew(int is_renew) {
        this.is_renew = is_renew;
    }

    public int getIs_grab() {
        return is_grab;
    }

    public void setIs_grab(int is_grab) {
        this.is_grab = is_grab;
    }

    public int getIs_black() {
        return is_black;
    }

    public void setIs_black(int is_black) {
        this.is_black = is_black;
    }

    public int getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(int evaluate) {
        this.evaluate = evaluate;
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

    public int getServ_range() {
        return serv_range;
    }

    public void setServ_range(int serv_range) {
        this.serv_range = serv_range;
    }

    public int getHas_serv_plat() {
        return has_serv_plat;
    }

    public void setHas_serv_plat(int has_serv_plat) {
        this.has_serv_plat = has_serv_plat;
    }

    public String getCo_type_name() {
        return co_type_name;
    }

    public void setCo_type_name(String co_type_name) {
        this.co_type_name = co_type_name;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    public String getAudit_state_name() {
        return audit_state_name;
    }

    public void setAudit_state_name(String audit_state_name) {
        this.audit_state_name = audit_state_name;
    }

    public int getIs_ben_grab() {
        return is_ben_grab;
    }

    public void setIs_ben_grab(int is_ben_grab) {
        this.is_ben_grab = is_ben_grab;
    }

    public int getWarn_state() {
        return warn_state;
    }

    public void setWarn_state(int warn_state) {
        this.warn_state = warn_state;
    }

    public int getDelay_state() {
        return delay_state;
    }

    public void setDelay_state(int delay_state) {
        this.delay_state = delay_state;
    }

    public int getCan_delay_num() {
        return can_delay_num;
    }

    public void setCan_delay_num(int can_delay_num) {
        this.can_delay_num = can_delay_num;
    }

    public String getGrab_desc() {
        return grab_desc;
    }

    public void setGrab_desc(String grab_desc) {
        this.grab_desc = grab_desc;
    }

    public String getTrans_desc() {
        return trans_desc;
    }

    public void setTrans_desc(String trans_desc) {
        this.trans_desc = trans_desc;
    }

    public String getTrans_time() {
        return trans_time;
    }

    public void setTrans_time(String trans_time) {
        this.trans_time = trans_time;
    }

    public String getGrab_time() {
        return grab_time;
    }

    public void setGrab_time(String grab_time) {
        this.grab_time = grab_time;
    }

    public String getGrab_type_desc() {
        return grab_type_desc;
    }

    public void setGrab_type_desc(String grab_type_desc) {
        this.grab_type_desc = grab_type_desc;
    }

    public String getReal_phone() {
        return real_phone;
    }

    public void setReal_phone(String real_phone) {
        this.real_phone = real_phone;
    }

    public String getSphone() {
        return sphone;
    }

    public void setSphone(String sphone) {
        this.sphone = sphone;
    }

    public String getCallphone() {
        return callphone;
    }

    public void setCallphone(String callphone) {
        this.callphone = callphone;
    }

    public int getIs_invite() {
        return is_invite;
    }

    public void setIs_invite(int is_invite) {
        this.is_invite = is_invite;
    }

    public String getAdd_name() {
        return add_name;
    }

    public void setAdd_name(String add_name) {
        this.add_name = add_name;
    }

    public int getIs_hide_phone() {
        return is_hide_phone;
    }

    public void setIs_hide_phone(int is_hide_phone) {
        this.is_hide_phone = is_hide_phone;
    }

    public List<?> getRespond_list() {
        return respond_list;
    }

    public void setRespond_list(List<?> respond_list) {
        this.respond_list = respond_list;
    }
}
