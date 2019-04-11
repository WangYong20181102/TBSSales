package com.tbs.sales.utils;

/**
 * Created by Mr.Wang on 2019/3/2 14:38.
 */
public class EC {
    public static final class EventCode {
        public static final int UPDATE_HOME_DATA = 0x000001;    //更新首页数据
        public static final int UPDATE_CLIENT_DATA = 0x000006;    //更新客户数据
        public static final int UPDATE_USERINFO = 0x000003;    //个人信息操作成功通知
        public static final int CHANGE_USERNAME = 0x000004;    //更换姓名
        public static final int CHANGE_PHONE = 0x000005;    //更换手机号
        public static final int MAIN_SELECT = 0x000007;    //首页
        public static final int CHANGE_CITY_MESSAGE = 0x000008;    //获取城市信息
        public static final int UPDATE_MINE_CLIENT = 0x000009;    //我的客户
        public static final int CLOSE_DETAIL = 0x000010;    //关闭详情页
        public static final int UPDATE_CITY_MESSAGE = 0x000011;    //更新城市信息
        public static final int UPDATE_CLIENT_DETAIL = 0x000012;    //更新客户详情页数据
    }
}
