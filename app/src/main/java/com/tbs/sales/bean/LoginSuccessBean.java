package com.tbs.sales.bean;

/**
 * Created by Mr.Wang on 2019/3/2 09:51.
 */
public class LoginSuccessBean {

    /**
     * token : 83df3eb1e9123da123c318e98d004b2b252@215244b2acdd3a35b5ac89eaf41f33150bb
     * userinfo : {"id":649,"email_account":"hison.chen@tobosu.cn","real_name":"陈海生","depart_id":9,"rank":"manager","phone":"18566775045","city_id_list":"|73|87|88|89|90|91|92|93|94|95|96|97|115|116|117|118|119|120|121|122|123|345|353|358|359|364|488|74|75|76|77|78|79|80|81|82|83|84|85|86|346|348|349|350|354|356|357|363|183|184|185|186|187|188|189|190|191|192|193|194|195|196|197|198|199|200|201|202|203|204|205|206|207|208|209|210|211|212|213|214|215|216|217|218|219|220|221|222|223|224|225|226|227|228|229|230|231|232|233|343|344|352|365|372|373|374|375|376|377|378|379|380|381|382|383|384|407|408|412|413|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|98|99|100|101|102|103|104|105|106|107|108|109|110|111|112|113|114|124|125|126|127|128|129|130|131|132|133|134|152|153|154|155|156|157|158|159|160|161|162|163|164|165|166|167|168|169|170|171|172|173|174|175|176|177|178|179|180|181|182|370|371|404|405|234|235|236|237|238|239|240|241|242|243|244|245|246|247|248|249|250|251|252|253|254|255|256|257|258|259|260|261|262|263|264|265|266|267|268|269|270|271|272|273|274|275|276|277|278|279|280|281|282|283|284|285|286|288|289|290|291|292|293|294|295|296|297|298|299|300|301|302|303|304|305|306|307|308|309|310|311|312|313|314|315|316|317|318|319|320|321|322|323|324|325|326|327|328|329|330|331|332|333|334|335|336|337|338|339|340|341|342|369|473|482|486|489|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|57|58|59|60|61|62|63|64|65|66|67|68|69|70|71|72|135|136|137|138|139|140|141|142|143|144|145|146|147|148|149|150|151|","state":1,"password":"83aa400af464c76d","agent_id":848,"agent_pwd":"tbs048","task_id":"201811","role_id":2,"update_time":null,"group_type":2,"area":"0","voice_choice":1,"rank_level":3,"depart_data":{"id":9,"title":"技术部","pid":0,"system_code":"technology","state":1,"sort":0},"nids":"1,9,11,12,13,14,42,43,44,51,54,55,2,3,4,15,16,17,18,19,5,10,20,22,23,24,25,35,6,26,33,7,27,32,37,38,46,47,50,53,8,28,34,36,39,40,41,45","content_ids":"29,30,31,48","organ_role":"leader","organ_area_ids":-1,"is_maintain":1}
     */

    private String token;
    private UserinfoBean userinfo;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public static class UserinfoBean {
        /**
         * id : 649
         * email_account : hison.chen@tobosu.cn
         * real_name : 陈海生
         * depart_id : 9
         * rank : manager
         * phone : 18566775045
         * city_id_list : |73|87|88|89|90|91|92|93|94|95|96|97|115|116|117|118|119|120|121|122|123|345|353|358|359|364|488|74|75|76|77|78|79|80|81|82|83|84|85|86|346|348|349|350|354|356|357|363|183|184|185|186|187|188|189|190|191|192|193|194|195|196|197|198|199|200|201|202|203|204|205|206|207|208|209|210|211|212|213|214|215|216|217|218|219|220|221|222|223|224|225|226|227|228|229|230|231|232|233|343|344|352|365|372|373|374|375|376|377|378|379|380|381|382|383|384|407|408|412|413|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|98|99|100|101|102|103|104|105|106|107|108|109|110|111|112|113|114|124|125|126|127|128|129|130|131|132|133|134|152|153|154|155|156|157|158|159|160|161|162|163|164|165|166|167|168|169|170|171|172|173|174|175|176|177|178|179|180|181|182|370|371|404|405|234|235|236|237|238|239|240|241|242|243|244|245|246|247|248|249|250|251|252|253|254|255|256|257|258|259|260|261|262|263|264|265|266|267|268|269|270|271|272|273|274|275|276|277|278|279|280|281|282|283|284|285|286|288|289|290|291|292|293|294|295|296|297|298|299|300|301|302|303|304|305|306|307|308|309|310|311|312|313|314|315|316|317|318|319|320|321|322|323|324|325|326|327|328|329|330|331|332|333|334|335|336|337|338|339|340|341|342|369|473|482|486|489|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|57|58|59|60|61|62|63|64|65|66|67|68|69|70|71|72|135|136|137|138|139|140|141|142|143|144|145|146|147|148|149|150|151|
         * state : 1
         * password : 83aa400af464c76d
         * agent_id : 848
         * agent_pwd : tbs048
         * task_id : 201811
         * role_id : 2
         * update_time : null
         * group_type : 2
         * area : 0
         * voice_choice : 1
         * rank_level : 3
         * depart_data : {"id":9,"title":"技术部","pid":0,"system_code":"technology","state":1,"sort":0}
         * nids : 1,9,11,12,13,14,42,43,44,51,54,55,2,3,4,15,16,17,18,19,5,10,20,22,23,24,25,35,6,26,33,7,27,32,37,38,46,47,50,53,8,28,34,36,39,40,41,45
         * content_ids : 29,30,31,48
         * organ_role : leader
         * organ_area_ids : -1
         * is_maintain : 1
         */

        private String id;
        private String email_account;
        private String real_name;
        private int depart_id;
        private String rank;
        private String phone;
        private String city_id_list;
        private int state;
        private String password;
        private int agent_id;
        private String agent_pwd;
        private String task_id;
        private int role_id;
        private Object update_time;
        private int group_type;
        private String area;
        private int voice_choice;
        private int rank_level;
        private DepartDataBean depart_data;
        private String nids;
        private String content_ids;
        private String organ_role;
        private String organ_area_ids;
        private int is_maintain;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail_account() {
            return email_account;
        }

        public void setEmail_account(String email_account) {
            this.email_account = email_account;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public int getDepart_id() {
            return depart_id;
        }

        public void setDepart_id(int depart_id) {
            this.depart_id = depart_id;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCity_id_list() {
            return city_id_list;
        }

        public void setCity_id_list(String city_id_list) {
            this.city_id_list = city_id_list;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getAgent_id() {
            return agent_id;
        }

        public void setAgent_id(int agent_id) {
            this.agent_id = agent_id;
        }

        public String getAgent_pwd() {
            return agent_pwd;
        }

        public void setAgent_pwd(String agent_pwd) {
            this.agent_pwd = agent_pwd;
        }

        public String getTask_id() {
            return task_id;
        }

        public void setTask_id(String task_id) {
            this.task_id = task_id;
        }

        public int getRole_id() {
            return role_id;
        }

        public void setRole_id(int role_id) {
            this.role_id = role_id;
        }

        public Object getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(Object update_time) {
            this.update_time = update_time;
        }

        public int getGroup_type() {
            return group_type;
        }

        public void setGroup_type(int group_type) {
            this.group_type = group_type;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getVoice_choice() {
            return voice_choice;
        }

        public void setVoice_choice(int voice_choice) {
            this.voice_choice = voice_choice;
        }

        public int getRank_level() {
            return rank_level;
        }

        public void setRank_level(int rank_level) {
            this.rank_level = rank_level;
        }

        public DepartDataBean getDepart_data() {
            return depart_data;
        }

        public void setDepart_data(DepartDataBean depart_data) {
            this.depart_data = depart_data;
        }

        public String getNids() {
            return nids;
        }

        public void setNids(String nids) {
            this.nids = nids;
        }

        public String getContent_ids() {
            return content_ids;
        }

        public void setContent_ids(String content_ids) {
            this.content_ids = content_ids;
        }

        public String getOrgan_role() {
            return organ_role;
        }

        public void setOrgan_role(String organ_role) {
            this.organ_role = organ_role;
        }

        public String getOrgan_area_ids() {
            return organ_area_ids;
        }

        public void setOrgan_area_ids(String organ_area_ids) {
            this.organ_area_ids = organ_area_ids;
        }

        public int getIs_maintain() {
            return is_maintain;
        }

        public void setIs_maintain(int is_maintain) {
            this.is_maintain = is_maintain;
        }

        public static class DepartDataBean {
            /**
             * id : 9
             * title : 技术部
             * pid : 0
             * system_code : technology
             * state : 1
             * sort : 0
             */

            private int id;
            private String title;
            private int pid;
            private String system_code;
            private int state;
            private int sort;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public String getSystem_code() {
                return system_code;
            }

            public void setSystem_code(String system_code) {
                this.system_code = system_code;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }
        }
    }
}
