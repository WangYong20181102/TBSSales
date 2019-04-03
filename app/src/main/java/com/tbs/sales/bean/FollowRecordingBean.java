package com.tbs.sales.bean;

import java.util.List;

/**
 * Created by Mr.Wang on 2019/4/3 10:33.
 */
public class FollowRecordingBean {

    /**
     * list : [{"co_id":2778,"co_type":4,"co_name":"广州鼎装装饰工程有限公司","name":"袁圣李","sex":2,"contacts":"18613030808","phone":"18613030808","tel":"020-2334618","follow_time":"2019-03-19 14:07:39","address":"京溪中路28号京鹏大厦7020室","evaluate":0},{"co_id":2777,"co_type":3,"co_name":"郴州宅速美装饰工程有限公司","name":"饭了激动啦","sex":2,"contacts":"17307310318","phone":"17307310318","tel":"0735-2888100","follow_time":"2019-02-19 14:11:28","address":"北湖区文化路21号文秀楼3楼","evaluate":0},{"co_id":2775,"co_type":3,"co_name":"鞍山绿叶装饰工程有限公司","name":"短距离大","sex":2,"contacts":"18640377914","phone":"18640377914","tel":"","follow_time":"2019-02-19 14:11:05","address":"","evaluate":0},{"co_id":2772,"co_type":5,"co_name":"三人行装饰公司","name":"许小飞","sex":2,"contacts":"13705483185","phone":"13705483185","tel":"","follow_time":"2019-02-19 14:28:30","address":"卜楼小区东门北88米路西","evaluate":0},{"co_id":2768,"co_type":5,"co_name":"啄木鸟装饰工程有限公司","name":"湿哒哒","sex":2,"contacts":"13645231052","phone":"13645231052","tel":"","follow_time":"2019-02-19 14:28:47","address":"","evaluate":0},{"co_id":2764,"co_type":5,"co_name":"厦门崇庭装饰设计有限公司","name":"出差","sex":2,"contacts":"18650116063","phone":"18650116063","tel":"","follow_time":"2019-02-19 14:29:01","address":"五缘东一里23号","evaluate":0},{"co_id":2566,"co_type":5,"co_name":"内蒙古图拉嘎装饰工程有限公司","name":"","sex":2,"contacts":"15174843332","phone":"15174843332","tel":"0476-3996000","follow_time":"2019-02-19 17:53:54","address":"新城区 临潢大街 和美建材城 B5-6 图拉嘎装饰工程有限公司","evaluate":0},{"co_id":2563,"co_type":3,"co_name":"九江大兴装饰工程有限公司","name":"","sex":2,"contacts":"18170257318","phone":"18170257318","tel":"","follow_time":"2019-02-19 11:48:55","address":"九江市长江大道669号喜盈门建材家居A馆12层大兴装饰","evaluate":0},{"co_id":2564,"co_type":5,"co_name":"同济装饰有限公司","name":"","sex":2,"contacts":"13616173355","phone":"13616173355","tel":"","follow_time":"2019-02-19 11:50:13","address":"","evaluate":0},{"co_id":707,"co_type":3,"co_name":"土拨鼠网络有限公司","name":"张三","sex":1,"contacts":"15322897756","phone":"15322897756","tel":"0797-8707456","follow_time":"2019-01-22 11:53:45","address":"文化博览园C028","evaluate":0},{"co_id":706,"co_type":1,"co_name":"土拨鼠网络有限公司","name":"张三","sex":1,"contacts":"15322897803","phone":"15322897803","tel":"0797-8707889","follow_time":"2019-01-15 14:02:38","address":"文化博览园C028","evaluate":0},{"co_id":704,"co_type":7,"co_name":"阿斯蒂芬的身份的撒十大","name":"士大夫十大","sex":1,"contacts":"13285285276","phone":"13285285276","tel":"0755-85285276","follow_time":"2019-01-23 12:00:12","address":"阿瑟东范德萨发达","evaluate":0},{"co_id":703,"co_type":9,"co_name":"的说法的撒发射点士大夫","name":"大师傅","sex":1,"contacts":"13712312378","phone":"13712312378","tel":"","follow_time":"0000-00-00 00:00:00","address":"手动阀是的范德萨","evaluate":0},{"co_id":700,"co_type":4,"co_name":"fffffffffffffff","name":"发电公司","sex":1,"contacts":"13985666667","phone":"13985666667","tel":"","follow_time":"2019-03-04 19:56:03","address":"78989812313123","evaluate":0},{"co_id":698,"co_type":3,"co_name":"fffffffffffffff","name":"发电公司","sex":1,"contacts":"13985698544","phone":"13985698544","tel":"","follow_time":"2019-01-24 16:23:13","address":"11111111111","evaluate":0},{"co_id":691,"co_type":3,"co_name":"原子阿斯蒂芬","name":"tp5返回查询sql","sex":1,"contacts":"15322004611","phone":"15322004611","tel":"","follow_time":"2019-02-19 17:06:44","address":"深圳","evaluate":0},{"co_id":686,"co_type":1,"co_name":"zaaaaaaaaaaaaaa公司","name":"水水水水水水水水所所所所所所","sex":1,"contacts":"13778945785","phone":"13778945785","tel":"","follow_time":"2019-01-11 14:00:55","address":"水水水水水水水水","evaluate":0},{"co_id":685,"co_type":5,"co_name":"123456公司","name":"123","sex":1,"contacts":"13713713719","phone":"13713713719","tel":"0755-12345677","follow_time":"2019-01-10 19:08:38","address":"123","evaluate":0},{"co_id":671,"co_type":3,"co_name":"超级玛利亚先生8","name":"玛利亚先生","sex":1,"contacts":"13712312319","phone":"13712312319","tel":"","follow_time":"2019-01-10 19:10:53","address":"超级马路","evaluate":0},{"co_id":669,"co_type":3,"co_name":"超级玛利亚先生6","name":"玛利亚先生","sex":1,"contacts":"13712312317","phone":"13712312317","tel":"","follow_time":"2019-01-09 16:34:06","address":"超级马路","evaluate":0}]
     * all_count : 56
     * page : 1
     * page_size : 20
     */

    private int all_count;
    private String page;
    private String page_size;
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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * co_id : 2778
         * co_type : 4
         * co_name : 广州鼎装装饰工程有限公司
         * name : 袁圣李
         * sex : 2
         * contacts : 18613030808
         * phone : 18613030808
         * tel : 020-2334618
         * follow_time : 2019-03-19 14:07:39
         * address : 京溪中路28号京鹏大厦7020室
         * evaluate : 0
         */

        private int co_id;
        private int co_type;
        private String co_name;
        private String name;
        private int sex;
        private String contacts;
        private String phone;
        private String tel;
        private String follow_time;
        private String address;
        private int evaluate;

        public int getCo_id() {
            return co_id;
        }

        public void setCo_id(int co_id) {
            this.co_id = co_id;
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

        public String getFollow_time() {
            return follow_time;
        }

        public void setFollow_time(String follow_time) {
            this.follow_time = follow_time;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getEvaluate() {
            return evaluate;
        }

        public void setEvaluate(int evaluate) {
            this.evaluate = evaluate;
        }
    }
}
