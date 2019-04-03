package com.tbs.sales.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.Wang on 2019/4/2 15:58.
 */
public class IntegratedQueryDataBean implements Serializable {

    /**
     * list : [{"co_id":2791,"com_id":0,"co_type":0,"evaluate":0,"co_name":"土拨鼠大公司","name":"张三","sex":1,"contacts":"18200000000","phone":"18200000000","tel":"0455-8443538","weixin":"","email":"","address":"大是大非","province":19,"city":199,"county":1772,"province_name":"广东省","city_name":"深圳市","county_name":"宝安区","add_uid":649,"pre_time":"","follow_time":"","finish_time":"","update_time":null,"source":1,"invalid_reason":0,"is_grab":0,"respond_uid":0,"add_time":"2019-04-01 09:52:15","grab_time":"","invite_time":"","give_uid":0,"give_name":"","grab_uid":0,"grab_name":"","grab_state":0,"grab_type":0,"co_type_name":"新客户","source_name":"创建","invalid_reason_name":"","area_id":3,"area_name":"华南区","real_phone":"18200000000","add_name":"陈海生","is_grab_desc":"公共客户","invite_uid":"0","invite_name":""},{"co_id":2790,"com_id":0,"co_type":0,"evaluate":0,"co_name":"土拨鼠","name":"土拨鼠","sex":1,"contacts":"18300762713","phone":"18300762713","tel":"","weixin":"","email":"","address":"天安门","province":1,"city":1,"county":1,"province_name":"北京市","city_name":"北京市","county_name":"东城区","add_uid":649,"pre_time":"","follow_time":"","finish_time":"","update_time":null,"source":1,"invalid_reason":0,"is_grab":0,"respond_uid":0,"add_time":"2019-03-30 14:43:33","grab_time":"","invite_time":"","give_uid":0,"give_name":"","grab_uid":0,"grab_name":"","grab_state":0,"grab_type":0,"co_type_name":"新客户","source_name":"创建","invalid_reason_name":"","area_id":5,"area_name":"华北区","real_phone":"18300762713","add_name":"陈海生","is_grab_desc":"公共客户","invite_uid":"0","invite_name":""},{"co_id":2789,"com_id":0,"co_type":0,"evaluate":0,"co_name":"兰州恒超建筑劳务有限公司","name":"曹","sex":1,"contacts":"13679487439","phone":"13679487439","tel":"","weixin":"","email":"","address":"雁西路1415号一楼","province":28,"city":298,"county":2618,"province_name":"甘肃省","city_name":"兰州市","county_name":"城关区","add_uid":641,"pre_time":"","follow_time":"","finish_time":"","update_time":null,"source":1,"invalid_reason":0,"is_grab":0,"respond_uid":0,"add_time":"2019-03-27 16:10:33","grab_time":"","invite_time":"","give_uid":0,"give_name":"","grab_uid":0,"grab_name":"","grab_state":0,"grab_type":0,"co_type_name":"新客户","source_name":"创建","invalid_reason_name":"","area_id":14,"area_name":"西北区","real_phone":"13679487439","add_name":"周德志","is_grab_desc":"公共客户","invite_uid":"0","invite_name":""},{"co_id":2788,"com_id":0,"co_type":0,"evaluate":0,"co_name":"哈哈哈","name":"小小子222","sex":1,"contacts":"13712345685","phone":"13712345685","tel":"0755-85285266","weixin":"","email":"32145685@qq.com","address":"啊啊","province":19,"city":199,"county":1770,"province_name":"广东省","city_name":"深圳市","county_name":"福田区","add_uid":698,"pre_time":"","follow_time":"","finish_time":"","update_time":null,"source":1,"invalid_reason":0,"is_grab":1,"respond_uid":698,"add_time":"2019-03-18 16:10:00","grab_time":"2019-03-19 15:25:41","invite_time":"","give_uid":649,"give_name":"陈海生","grab_uid":698,"grab_name":"李文杰","grab_state":1,"grab_type":3,"co_type_name":"新客户","source_name":"创建","invalid_reason_name":"","area_id":3,"area_name":"华南区","real_phone":"13712345685","add_name":"李文杰","is_grab_desc":"私有客户","invite_uid":"0","invite_name":""},{"co_id":2787,"com_id":0,"co_type":1,"evaluate":0,"co_name":"土拨鼠","name":"老鼠","sex":1,"contacts":"18300000000","phone":"18300000000","tel":"","weixin":"","email":"","address":"11号线","province":19,"city":199,"county":1769,"province_name":"广东省","city_name":"深圳市","county_name":"罗湖区","add_uid":802,"pre_time":"2019-03-11 14:26:49","follow_time":"2019-03-29 10:31:22","finish_time":"","update_time":"2019-03-29 10:31:22","source":1,"invalid_reason":0,"is_grab":1,"respond_uid":802,"add_time":"2019-03-11 14:15:57","grab_time":"2019-03-11 14:15:57","invite_time":"","give_uid":0,"give_name":"","grab_uid":802,"grab_name":"吴春笋","grab_state":1,"grab_type":1,"co_type_name":"潜在","source_name":"创建","invalid_reason_name":"","area_id":3,"area_name":"华南区","real_phone":"18300000000","add_name":"吴春笋","is_grab_desc":"私有客户","invite_uid":"0","invite_name":""},{"co_id":2786,"com_id":0,"co_type":0,"evaluate":0,"co_name":"xbudbudbsub","name":"xbudbudbsubbudbdubdu","sex":1,"contacts":"13725825325","phone":"13725825325","tel":"","weixin":"fbbvvc","email":"","address":"jsjsjsn","province":1,"city":1,"county":1,"province_name":"北京市","city_name":"北京市","county_name":"东城区","add_uid":649,"pre_time":"","follow_time":"","finish_time":"","update_time":"2019-02-20 10:12:50","source":1,"invalid_reason":0,"is_grab":1,"respond_uid":649,"add_time":"2019-02-20 10:09:32","grab_time":"2019-02-20 10:09:32","invite_time":"","give_uid":0,"give_name":"","grab_uid":649,"grab_name":"陈海生","grab_state":1,"grab_type":1,"co_type_name":"新客户","source_name":"创建","invalid_reason_name":"","area_id":5,"area_name":"华北区","real_phone":"13725825325","add_name":"陈海生","is_grab_desc":"私有客户","invite_uid":"0","invite_name":""},{"co_id":2785,"com_id":0,"co_type":0,"evaluate":0,"co_name":"xbudbudbsub","name":"xbudbudbsubbudbdubdu","sex":1,"contacts":"13725825825","phone":"13725825825","tel":"","weixin":"","email":"","address":"jsjsjsn","province":1,"city":1,"county":1,"province_name":"北京市","city_name":"北京市","county_name":"东城区","add_uid":649,"pre_time":"","follow_time":"","finish_time":"","update_time":null,"source":1,"invalid_reason":0,"is_grab":1,"respond_uid":649,"add_time":"2019-02-20 10:09:01","grab_time":"2019-02-21 14:57:36","invite_time":"","give_uid":0,"give_name":"","grab_uid":649,"grab_name":"陈海生","grab_state":1,"grab_type":1,"co_type_name":"新客户","source_name":"创建","invalid_reason_name":"","area_id":5,"area_name":"华北区","real_phone":"13725825825","add_name":"陈海生","is_grab_desc":"私有客户","invite_uid":"0","invite_name":""},{"co_id":2784,"com_id":0,"co_type":0,"evaluate":0,"co_name":"三生三世","name":"啥事手","sex":1,"contacts":"13538044127","phone":"13538044127","tel":"","weixin":"","email":"","address":"啥事手","province":1,"city":1,"county":1,"province_name":"北京市","city_name":"北京市","county_name":"东城区","add_uid":649,"pre_time":"","follow_time":"","finish_time":"","update_time":null,"source":1,"invalid_reason":0,"is_grab":1,"respond_uid":649,"add_time":"2019-02-19 17:41:29","grab_time":"2019-02-19 17:41:30","invite_time":"","give_uid":0,"give_name":"","grab_uid":649,"grab_name":"陈海生","grab_state":1,"grab_type":1,"co_type_name":"新客户","source_name":"创建","invalid_reason_name":"","area_id":5,"area_name":"华北区","real_phone":"13538044127","add_name":"陈海生","is_grab_desc":"私有客户","invite_uid":"0","invite_name":""},{"co_id":2783,"com_id":0,"co_type":0,"evaluate":0,"co_name":"二愣子","name":"二愣子","sex":1,"contacts":"15322004600","phone":"15322004600","tel":"","weixin":"","email":"","address":"深圳","province":19,"city":199,"county":1773,"province_name":"广东省","city_name":"深圳市","county_name":"龙岗区","add_uid":802,"pre_time":"2019-03-11 14:26:49","follow_time":"","finish_time":"","update_time":null,"source":1,"invalid_reason":0,"is_grab":1,"respond_uid":802,"add_time":"2019-02-19 15:23:35","grab_time":"2019-02-19 15:23:37","invite_time":"","give_uid":0,"give_name":"","grab_uid":802,"grab_name":"吴春笋","grab_state":1,"grab_type":1,"co_type_name":"新客户","source_name":"创建","invalid_reason_name":"","area_id":3,"area_name":"华南区","real_phone":"15322004600","add_name":"吴春笋","is_grab_desc":"私有客户","invite_uid":"0","invite_name":""},{"co_id":2782,"com_id":0,"co_type":0,"evaluate":0,"co_name":"大家拉丝机的看了看来","name":"好的徕卡的来看","sex":1,"contacts":"13784577487","phone":"13784577487","tel":"","weixin":"","email":"","address":"好的啦啦","province":19,"city":199,"county":1771,"province_name":"广东省","city_name":"深圳市","county_name":"南山区","add_uid":649,"pre_time":"","follow_time":"","finish_time":"","update_time":null,"source":1,"invalid_reason":0,"is_grab":1,"respond_uid":940,"add_time":"2019-02-19 15:10:05","grab_time":"2019-02-21 15:00:09","invite_time":"","give_uid":0,"give_name":"","grab_uid":940,"grab_name":"余毓铭","grab_state":1,"grab_type":1,"co_type_name":"新客户","source_name":"创建","invalid_reason_name":"","area_id":3,"area_name":"华南区","real_phone":"13784577487","add_name":"陈海生","is_grab_desc":"私有客户","invite_uid":"0","invite_name":""},{"co_id":2781,"com_id":276687,"co_type":4,"evaluate":0,"co_name":"宜春亦格平价建材超市","name":"李小琴","sex":2,"contacts":"13672286093","phone":"13672286093","tel":"","weixin":"","email":"","address":"樟树路108香樟儿童城","province":14,"city":132,"county":1190,"province_name":"江西省","city_name":"宜春市","county_name":"袁州区","add_uid":0,"pre_time":"2019-03-11 14:26:49","follow_time":"","finish_time":"2017-10-30 10:34:23","update_time":"2019-02-13 10:37:20","source":6,"invalid_reason":0,"is_grab":1,"respond_uid":802,"add_time":"2019-01-26 18:01:57","grab_time":"2019-02-15 18:21:33","invite_time":"","give_uid":649,"give_name":"陈海生","grab_uid":802,"grab_name":"吴春笋","grab_state":1,"grab_type":3,"co_type_name":"成交","source_name":"erp刷入","invalid_reason_name":"","area_id":7,"area_name":"华中区","real_phone":"13672286093","add_name":"","is_grab_desc":"私有客户","invite_uid":"0","invite_name":""},{"co_id":2778,"com_id":278246,"co_type":4,"evaluate":0,"co_name":"广州鼎装装饰工程有限公司","name":"袁圣李","sex":2,"contacts":"18613030808","phone":"18613030808","tel":"020-2334618","weixin":"","email":"","address":"京溪中路28号京鹏大厦7020室","province":19,"city":197,"county":1753,"province_name":"广东省","city_name":"广州市","county_name":"白云区","add_uid":0,"pre_time":"","follow_time":"2019-03-19 14:07:39","finish_time":"2017-11-09 13:57:37","update_time":"2019-03-19 14:07:39","source":6,"invalid_reason":0,"is_grab":0,"respond_uid":0,"add_time":"2019-01-26 18:01:56","grab_time":"","invite_time":"","give_uid":0,"give_name":"","grab_uid":0,"grab_name":"","grab_state":0,"grab_type":0,"co_type_name":"成交","source_name":"erp刷入","invalid_reason_name":"","area_id":3,"area_name":"华南区","real_phone":"18613030808","add_name":"","is_grab_desc":"公共客户","invite_uid":"0","invite_name":""},{"co_id":2779,"com_id":276678,"co_type":4,"evaluate":0,"co_name":"成象空间设计","name":"","sex":2,"contacts":"18157332800","phone":"18157332800","tel":"","weixin":"","email":"","address":"城北路450号（碧桂园旁）","province":11,"city":90,"county":884,"province_name":"浙江省","city_name":"嘉兴市","county_name":"平湖市","add_uid":0,"pre_time":"","follow_time":"","finish_time":"2017-10-28 16:06:20","update_time":"2019-01-26 18:01:56","source":6,"invalid_reason":0,"is_grab":0,"respond_uid":0,"add_time":"2019-01-26 18:01:56","grab_time":"","invite_time":"","give_uid":0,"give_name":"","grab_uid":0,"grab_name":"","grab_state":0,"grab_type":0,"co_type_name":"成交","source_name":"erp刷入","invalid_reason_name":"","area_id":1,"area_name":"华东区","real_phone":"18157332800","add_name":"","is_grab_desc":"公共客户","invite_uid":"0","invite_name":""},{"co_id":2780,"com_id":276683,"co_type":4,"evaluate":0,"co_name":"洛阳苏工源装饰工程有限公司","name":"王乐君","sex":2,"contacts":"17737981299","phone":"17737981299","tel":"","weixin":"","email":"","address":"九都西路与龙鳞路交叉口北航科技园","province":16,"city":154,"county":1388,"province_name":"河南省","city_name":"洛阳市","county_name":"涧西区","add_uid":0,"pre_time":"","follow_time":"2019-01-26 18:01:56","finish_time":"2018-04-04 16:06:48","update_time":"2019-01-26 18:01:56","source":6,"invalid_reason":0,"is_grab":1,"respond_uid":535,"add_time":"2019-01-26 18:01:56","grab_time":"2019-01-26 18:01:56","invite_time":"","give_uid":0,"give_name":"","grab_uid":535,"grab_name":"程学明","grab_state":1,"grab_type":1,"co_type_name":"成交","source_name":"erp刷入","invalid_reason_name":"","area_id":7,"area_name":"华中区","real_phone":"17737981299","add_name":"","is_grab_desc":"私有客户","invite_uid":"0","invite_name":""},{"co_id":2775,"com_id":276655,"co_type":3,"evaluate":0,"co_name":"鞍山绿叶装饰工程有限公司","name":"短距离大","sex":2,"contacts":"18640377914","phone":"18640377914","tel":"","weixin":"shhshs","email":"","address":"","province":6,"city":39,"county":0,"province_name":"辽宁省","city_name":"鞍山市","county_name":"","add_uid":0,"pre_time":"","follow_time":"2019-02-19 14:11:05","finish_time":"2019-01-26 18:01:55","update_time":"2019-02-20 10:07:15","source":6,"invalid_reason":0,"is_grab":1,"respond_uid":940,"add_time":"2019-01-26 18:01:55","grab_time":"2019-02-21 14:59:56","invite_time":"","give_uid":0,"give_name":"","grab_uid":940,"grab_name":"余毓铭","grab_state":1,"grab_type":1,"co_type_name":"意向","source_name":"erp刷入","invalid_reason_name":"","area_id":11,"area_name":"东北区","real_phone":"18640377914","add_name":"","is_grab_desc":"私有客户","invite_uid":"0","invite_name":""},{"co_id":2776,"com_id":276668,"co_type":4,"evaluate":0,"co_name":"北京天亨旺盛装饰工程有限公司","name":"","sex":2,"contacts":"13671310510","phone":"13671310510","tel":"","weixin":"","email":"","address":"通州区路远北大街甲1号旺盛装饰","province":1,"city":1,"county":11,"province_name":"北京市","city_name":"北京市","county_name":"通州区","add_uid":0,"pre_time":"","follow_time":"2019-01-26 18:01:55","finish_time":"2017-10-31 17:31:26","update_time":"2019-02-13 10:37:19","source":6,"invalid_reason":0,"is_grab":1,"respond_uid":134,"add_time":"2019-01-26 18:01:55","grab_time":"2019-01-26 18:01:55","invite_time":"","give_uid":0,"give_name":"","grab_uid":134,"grab_name":"王俊祁","grab_state":1,"grab_type":1,"co_type_name":"成交","source_name":"erp刷入","invalid_reason_name":"","area_id":5,"area_name":"华北区","real_phone":"13671310510","add_name":"","is_grab_desc":"私有客户","invite_uid":"0","invite_name":""},{"co_id":2777,"com_id":276669,"co_type":3,"evaluate":0,"co_name":"郴州宅速美装饰工程有限公司","name":"饭了激动啦","sex":2,"contacts":"17307310318","phone":"17307310318","tel":"0735-2888100","weixin":"","email":"","address":"北湖区文化路21号文秀楼3楼","province":18,"city":192,"county":0,"province_name":"湖南省","city_name":"郴州市","county_name":"","add_uid":0,"pre_time":"","follow_time":"2019-02-19 14:11:28","finish_time":"2019-01-26 18:01:55","update_time":"2019-02-19 14:11:28","source":6,"invalid_reason":0,"is_grab":1,"respond_uid":649,"add_time":"2019-01-26 18:01:55","grab_time":"2019-02-21 10:28:29","invite_time":"","give_uid":0,"give_name":"","grab_uid":649,"grab_name":"陈海生","grab_state":1,"grab_type":1,"co_type_name":"意向","source_name":"erp刷入","invalid_reason_name":"","area_id":3,"area_name":"华南区","real_phone":"17307310318","add_name":"","is_grab_desc":"私有客户","invite_uid":"0","invite_name":""},{"co_id":2772,"com_id":276639,"co_type":5,"evaluate":0,"co_name":"三人行装饰公司","name":"许小飞","sex":2,"contacts":"13705483185","phone":"13705483185","tel":"","weixin":"","email":"","address":"卜楼小区东门北88米路西","province":15,"city":143,"county":0,"province_name":"山东省","city_name":"泰安市","county_name":"","add_uid":0,"pre_time":"","follow_time":"2019-02-19 14:28:30","finish_time":"2019-01-26 18:01:54","update_time":"2019-02-19 14:28:30","source":6,"invalid_reason":1,"is_grab":1,"respond_uid":649,"add_time":"2019-01-26 18:01:54","grab_time":"2019-02-19 18:09:33","invite_time":"","give_uid":649,"give_name":"陈海生","grab_uid":649,"grab_name":"陈海生","grab_state":1,"grab_type":3,"co_type_name":"无效","source_name":"erp刷入","invalid_reason_name":"号码错误","area_id":12,"area_name":"山东区","real_phone":"13705483185","add_name":"","is_grab_desc":"私有客户","invite_uid":"0","invite_name":""},{"co_id":2773,"com_id":276649,"co_type":4,"evaluate":0,"co_name":"先创装饰工程有限公司","name":"","sex":2,"contacts":"17798986909","phone":"17798986909","tel":"","weixin":"","email":"","address":"西安路9-32","province":10,"city":81,"county":803,"province_name":"江苏省","city_name":"淮安市","county_name":"清河区","add_uid":0,"pre_time":"","follow_time":"2019-01-26 18:01:54","finish_time":"2018-04-06 17:31:20","update_time":"2019-02-13 10:37:18","source":6,"invalid_reason":0,"is_grab":1,"respond_uid":534,"add_time":"2019-01-26 18:01:54","grab_time":"2019-01-26 18:01:54","invite_time":"","give_uid":0,"give_name":"","grab_uid":534,"grab_name":"戚明亮","grab_state":1,"grab_type":1,"co_type_name":"成交","source_name":"erp刷入","invalid_reason_name":"","area_id":2,"area_name":"江苏区","real_phone":"17798986909","add_name":"","is_grab_desc":"私有客户","invite_uid":"0","invite_name":""},{"co_id":2774,"com_id":276654,"co_type":1,"evaluate":0,"co_name":"高新园区爱绿空间室内设计工作室","name":"","sex":2,"contacts":"13166821161","phone":"13166821161","tel":"","weixin":"","email":"","address":"","province":7,"city":51,"county":529,"province_name":"吉林省","city_name":"长春市","county_name":"南关区","add_uid":0,"pre_time":"","follow_time":"","finish_time":"2019-01-26 18:01:54","update_time":"2019-01-26 18:01:54","source":6,"invalid_reason":0,"is_grab":1,"respond_uid":940,"add_time":"2019-01-26 18:01:54","grab_time":"2019-02-21 14:59:56","invite_time":"","give_uid":0,"give_name":"","grab_uid":940,"grab_name":"余毓铭","grab_state":1,"grab_type":1,"co_type_name":"潜在","source_name":"erp刷入","invalid_reason_name":"","area_id":11,"area_name":"东北区","real_phone":"13166821161","add_name":"","is_grab_desc":"私有客户","invite_uid":"0","invite_name":""}]
     * all_count : 1227
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

    public static class ListBean implements Serializable{
        /**
         * co_id : 2791
         * com_id : 0
         * co_type : 0
         * evaluate : 0
         * co_name : 土拨鼠大公司
         * name : 张三
         * sex : 1
         * contacts : 18200000000
         * phone : 18200000000
         * tel : 0455-8443538
         * weixin :
         * email :
         * address : 大是大非
         * province : 19
         * city : 199
         * county : 1772
         * province_name : 广东省
         * city_name : 深圳市
         * county_name : 宝安区
         * add_uid : 649
         * pre_time :
         * follow_time :
         * finish_time :
         * update_time : null
         * source : 1
         * invalid_reason : 0
         * is_grab : 0
         * respond_uid : 0
         * add_time : 2019-04-01 09:52:15
         * grab_time :
         * invite_time :
         * give_uid : 0
         * give_name :
         * grab_uid : 0
         * grab_name :
         * grab_state : 0
         * grab_type : 0
         * co_type_name : 新客户
         * source_name : 创建
         * invalid_reason_name :
         * area_id : 3
         * area_name : 华南区
         * real_phone : 18200000000
         * add_name : 陈海生
         * is_grab_desc : 公共客户
         * invite_uid : 0
         * invite_name :
         */

        private int co_id;
        private int com_id;
        private int co_type;
        private int evaluate;
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
        private int city;
        private int county;
        private String province_name;
        private String city_name;
        private String county_name;
        private int add_uid;
        private String pre_time;
        private String follow_time;
        private String finish_time;
        private Object update_time;
        private int source;
        private int invalid_reason;
        private int is_grab;
        private int respond_uid;
        private String add_time;
        private String grab_time;
        private String invite_time;
        private int give_uid;
        private String give_name;
        private int grab_uid;
        private String grab_name;
        private int grab_state;
        private int grab_type;
        private String co_type_name;
        private String source_name;
        private String invalid_reason_name;
        private int area_id;
        private String area_name;
        private String real_phone;
        private String add_name;
        private String is_grab_desc;
        private String invite_uid;
        private String invite_name;

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

        public int getEvaluate() {
            return evaluate;
        }

        public void setEvaluate(int evaluate) {
            this.evaluate = evaluate;
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

        public String getFinish_time() {
            return finish_time;
        }

        public void setFinish_time(String finish_time) {
            this.finish_time = finish_time;
        }

        public Object getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(Object update_time) {
            this.update_time = update_time;
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

        public int getIs_grab() {
            return is_grab;
        }

        public void setIs_grab(int is_grab) {
            this.is_grab = is_grab;
        }

        public int getRespond_uid() {
            return respond_uid;
        }

        public void setRespond_uid(int respond_uid) {
            this.respond_uid = respond_uid;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getGrab_time() {
            return grab_time;
        }

        public void setGrab_time(String grab_time) {
            this.grab_time = grab_time;
        }

        public String getInvite_time() {
            return invite_time;
        }

        public void setInvite_time(String invite_time) {
            this.invite_time = invite_time;
        }

        public int getGive_uid() {
            return give_uid;
        }

        public void setGive_uid(int give_uid) {
            this.give_uid = give_uid;
        }

        public String getGive_name() {
            return give_name;
        }

        public void setGive_name(String give_name) {
            this.give_name = give_name;
        }

        public int getGrab_uid() {
            return grab_uid;
        }

        public void setGrab_uid(int grab_uid) {
            this.grab_uid = grab_uid;
        }

        public String getGrab_name() {
            return grab_name;
        }

        public void setGrab_name(String grab_name) {
            this.grab_name = grab_name;
        }

        public int getGrab_state() {
            return grab_state;
        }

        public void setGrab_state(int grab_state) {
            this.grab_state = grab_state;
        }

        public int getGrab_type() {
            return grab_type;
        }

        public void setGrab_type(int grab_type) {
            this.grab_type = grab_type;
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

        public String getInvalid_reason_name() {
            return invalid_reason_name;
        }

        public void setInvalid_reason_name(String invalid_reason_name) {
            this.invalid_reason_name = invalid_reason_name;
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

        public String getReal_phone() {
            return real_phone;
        }

        public void setReal_phone(String real_phone) {
            this.real_phone = real_phone;
        }

        public String getAdd_name() {
            return add_name;
        }

        public void setAdd_name(String add_name) {
            this.add_name = add_name;
        }

        public String getIs_grab_desc() {
            return is_grab_desc;
        }

        public void setIs_grab_desc(String is_grab_desc) {
            this.is_grab_desc = is_grab_desc;
        }

        public String getInvite_uid() {
            return invite_uid;
        }

        public void setInvite_uid(String invite_uid) {
            this.invite_uid = invite_uid;
        }

        public String getInvite_name() {
            return invite_name;
        }

        public void setInvite_name(String invite_name) {
            this.invite_name = invite_name;
        }
    }
}
