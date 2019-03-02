package com.tbs.sales.constant;

/**
 * Created by Mr.Wang on 2019/2/20 16:36.
 */
public class Constant {
    /**
     * 测试环境
     */
    public static final String HTTP_URL = "http://bip.test.tobosu.cn";







    //首页信息
    public static final String SALE_GETCOMLIST = HTTP_URL + "/api/sale/getcomlist";
    //登录
    public static final String LOGIN_DOLOGIN = HTTP_URL + "/api/login/dologin";
    //退出登录
    public static final String LOGIN_DOLOGOUT = HTTP_URL + "/api/login/dologout";



    /*****************************************************     应用     ****************************************************************/
    //待分单
    public static final String WXDISTRIBUTE_WAIT = "https://m.tobosu.com/wxdistribute/#/wait";
    //已分单
    public static final String WXDISTRIBUTE_ALREADY = "https://m.tobosu.com/wxdistribute/#/already";
    //撤单
    public static final String WXDISTRIBUTE_REVOKE = "https://m.tobosu.com/wxdistribute/#/revoke";
    //合同报备
    public static final String WXDISTRIBUTE_CONTRACT = "https://m.tobosu.com/wxdistribute/#/business/contract";
    //收款报备
    public static final String WXDISTRIBUTE_RECEIPT = "https://m.tobosu.com/wxdistribute/#/business/receipt";
    //审批
    public static final String WXDISTRIBUTE_APPROVE = "https://m.tobosu.com/wxdistribute/#/business/approve";
    //计算器
    public static final String WXDISTRIBUTE_COMPUTE = "https://m.tobosu.com/wxdistribute/#/tool/compute";
    /*****************************************************     客户     ****************************************************************/
    //我的客户
    public static final String WXDISTRIBUTE_CUSTOMER_MY = "https://m.tobosu.com/wxdistribute/#/customer/my";
    //公共客户
//    public static final String WXDISTRIBUTE_customer_my = "https://m.tobosu.com/wxdistribute/#/customer/my";
    //无效客户
    public static final String WXDISTRIBUTE_CUSTOMER_INVALID = "https://m.tobosu.com/wxdistribute/#/customer/invalid";
    //跟进记录
    public static final String WXDISTRIBUTE_CUSTOMER_FOLLOW = "https://m.tobosu.com/wxdistribute/#/customer/follow";
    //客户跟进
//    public static final String WXDISTRIBUTE_customer_my = "https://m.tobosu.com/wxdistribute/#/customer/my";
    //外呼统计
//    public static final String WXDISTRIBUTE_customer_my = "https://m.tobosu.com/wxdistribute/#/customer/my";


    //简报
    public static final String WXDISTRIBUTE_CUSTOMER_COUNT = "https://m.tobosu.com/wxdistribute/#/customer/count";


}
