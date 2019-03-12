package com.tbs.sales.constant;

/**
 * Created by Mr.Wang on 2019/2/20 16:36.
 */
public class Constant {
    /**
     * 测试环境
     */
    public static final String HTTP_URL = "http://bip.test.tobosu.cn";
    public static final String HTTP_URL_H5 = "http://m.test.tobosu.com";


    /**
     * 图片上传地址
     */
    public static final String IMAGE_LOAD_URL = "https://www.tobosu.com";


    //首页信息
    public static final String SALE_GETCOMLIST = HTTP_URL + "/api/sale/getcomlist";
    //登录
    public static final String LOGIN_DOLOGIN = HTTP_URL + "/api/login/dologin";
    //退出登录
    public static final String LOGIN_DOLOGOUT = HTTP_URL + "/api/login/dologout";


    /*****************************************************     应用     ****************************************************************/
    //待分单
    public static final String WXDISTRIBUTE_WAIT = HTTP_URL_H5 + "/wxdistribute/#/wait";
    //已分单
    public static final String WXDISTRIBUTE_ALREADY = HTTP_URL_H5 + "/wxdistribute/#/already";
    //撤单
    public static final String WXDISTRIBUTE_REVOKE = HTTP_URL_H5 + "/wxdistribute/#/revoke";
    //合同报备
    public static final String WXDISTRIBUTE_CONTRACT = HTTP_URL_H5 + "/wxdistribute/#/business/contract";
    //收款报备
    public static final String WXDISTRIBUTE_RECEIPT = HTTP_URL_H5 + "/wxdistribute/#/business/receipt";
    //审批
    public static final String WXDISTRIBUTE_APPROVE = HTTP_URL_H5 + "/wxdistribute/#/business/approve";
    //计算器
    public static final String WXDISTRIBUTE_COMPUTE = HTTP_URL_H5 + "/wxdistribute/#/tool/compute";
    /*****************************************************     客户     ****************************************************************/
    //我的客户
    public static final String WXDISTRIBUTE_CUSTOMER_MY = HTTP_URL_H5 + "/wxdistribute/#/customer/my";
    //公共客户
    public static final String WXDISTRIBUTE_CUSTOMER_COMMON = HTTP_URL_H5 + "/wxdistribute#/customer/common";
    //无效客户
    public static final String WXDISTRIBUTE_CUSTOMER_INVALID = HTTP_URL_H5 + "/wxdistribute/#/customer/invalid";
    //跟进记录
    public static final String WXDISTRIBUTE_CUSTOMER_FOLLOW = HTTP_URL_H5 + "/wxdistribute/#/customer/follow";
    //客户跟进
//    public static final String WXDISTRIBUTE_customer_my = HTTP_URL_H5 + "/wxdistribute/#/customer/my";
    //外呼统计
//    public static final String WXDISTRIBUTE_customer_my = HTTP_URL_H5 + "/wxdistribute/#/customer/my";
    //排行榜
    public static final String STATS_GETSTATSINDEX = HTTP_URL + "/api/stats/getStatsIndex";


    //详情页
    public static final String CUSTOMER_MY_DETAIL = HTTP_URL_H5 + "/wxdistribute#/customer/my/detail";


    /********************************************************           榜单         ****************************************************************/


    //成交榜
    public static final String WXDISTRIBUTE_FINISH = HTTP_URL_H5 + "/wxdistribute/#/customer/count/list?type=finish";
    //跟进榜
    public static final String WXDISTRIBUTE_FOLLOW = HTTP_URL_H5 + "/wxdistribute/#/customer/count/list?type=follow";
    //新增榜
    public static final String WXDISTRIBUTE_ADD = HTTP_URL_H5 + "/wxdistribute/#/customer/count/list?type=add";


    //简报
    public static final String WXDISTRIBUTE_CUSTOMER_COUNT = HTTP_URL_H5 + "/wxdistribute/#/customer/count";
    //添加
    public static final String WXDISTRIBUTE_CUSTOMER_ADD = HTTP_URL_H5 + "/wxdistribute/#/customer/add";
    //图片上传
    public static final String UPLOAD_DYNAMIC_IMAGE = IMAGE_LOAD_URL + "/cloud/upload/app_upload";

    //修改账号密码
    public static final String USER_CHANGEPWD = HTTP_URL + "/api/user/changePwd";
    public static final String USER_EDITACCOUNTALONE = HTTP_URL + "/api/user/editAccountAlone";
    //获取用户信息
    public static final String USER_GETINFO = HTTP_URL + "/api/user/getinfo";
    //我的消息
    public static final String PUSH_RECORD = HTTP_URL + "/api/push/record";


}
