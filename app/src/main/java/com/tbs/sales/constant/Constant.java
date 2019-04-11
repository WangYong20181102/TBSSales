package com.tbs.sales.constant;

/**
 * Created by Mr.Wang on 2019/2/20 16:36.
 */
public class Constant {


    /**
     * 正式环境
     */
//    private static final String HTTP_URL = "http://bip.tobosu.cn";
//    private static final String HTTP_URL_H5 = "http://m.tobosu.com";


    /**
     * 测试环境
     */
    private static final String HTTP_URL = "http://bip.test.tobosu.cn";
    private static final String HTTP_URL_H5 = "http://m.test.tobosu.com";


    //检查当前App是否需要更新
    public static final String CHECK_APP_IS_UPDATA = HTTP_URL + "/api/public/isUpdateApp";


    /**
     * 图片上传地址
     */
    private static final String IMAGE_LOAD_URL = "https://www.tobosu.com";


    //首页信息
    public static final String SALE_GETCOMLIST = HTTP_URL + "/api/sale/getcomlist";
    //登录
    public static final String LOGIN_DOLOGIN = HTTP_URL + "/api/login/dologin";
    //退出登录
    public static final String LOGIN_DOLOGOUT = HTTP_URL + "/api/login/dologout";


    /*****************************************************     应用     ****************************************************************/
    //待分单
    public static final String WXDISTRIBUTE_WAIT = HTTP_URL_H5 + "/wxdistribute#/order/wait";
    //已分单
    public static final String WXDISTRIBUTE_ALREADY = HTTP_URL_H5 + "/wxdistribute#/order/already";
    //撤单
    public static final String WXDISTRIBUTE_REVOKE = HTTP_URL_H5 + "/wxdistribute#/order/revoke";
    //合同报备
    public static final String WXDISTRIBUTE_CONTRACT = HTTP_URL_H5 + "/wxdistribute#/business/contract";
    //续费充值
    public static final String WXDISTRIBUTE_RECEIPT = HTTP_URL_H5 + "/wxdistribute#/business/receipt";
    //审批
    public static final String WXDISTRIBUTE_APPROVE = HTTP_URL_H5 + "/wxdistribute#/business/approve";
    //计算器
    public static final String WXDISTRIBUTE_COMPUTE = HTTP_URL_H5 + "/wxdistribute#/tool/compute";
    /*****************************************************     客户     ****************************************************************/
    //我的客户
    public static final String WXDISTRIBUTE_CUSTOMER_MY = HTTP_URL_H5 + "/wxdistribute/#/customer/my";
    //综合查询
    public static final String WXDISTRIBUTE_CUSTOMER_COMMON = HTTP_URL_H5 + "/wxdistribute/#/customer/scan";
    //无效客户
    public static final String WXDISTRIBUTE_CUSTOMER_INVALID = HTTP_URL_H5 + "/wxdistribute/#/customer/invalid";
    //跟进记录
    public static final String WXDISTRIBUTE_CUSTOMER_FOLLOW = HTTP_URL_H5 + "/wxdistribute/#/customer/follow";
    //数据概览
    public static final String WXDISTRIBUTE_CALL_FOLLOW = HTTP_URL_H5 + "/wxdistribute/#/customer/count";
    //外呼统计
    public static final String WXDISTRIBUTE_CUSTOMER_CALL_COM = HTTP_URL_H5 + "/wxdistribute#/customer/count/call?type=call_com";
    //排行榜
    public static final String STATS_GETSTATSINDEX = HTTP_URL + "/api/stats/getStatsIndex";
    //跟进记录
    public static final String SALE_GETCOMFOLLOW = HTTP_URL + "/api/sale/getcomfollow";
    //流转记录
    public static final String SALE_GETCOMOPERATELIST = HTTP_URL + "/api/sale/getComOperateList";
    //新增客户
    public static final String SALE_ADDCOM = HTTP_URL + "/api/sale/addCom";
    //综合查询
    public static final String SALE_GETCOMPOSITELIST = HTTP_URL + "/api/sale/getCompositeList";
    //获取领取人列表
    public static final String ORGAN_GETALLSTAFF = HTTP_URL + "/api/organ/getAllStaff";
    //跟进记录
    public static final String SALE_GETOWNERFOLLOWLIST = HTTP_URL + "/api/sale/getownerfollowlist";
    //客户数据
    public static final String SALE_GETCOMINFO = HTTP_URL + "/api/sale/getcominfo";
    //获取转移客户负责人
    public static final String ORGAN_GETTRANSUSER = HTTP_URL + "/api/organ/getTransUser";
    //转移客户
    public static final String GRAB_TRANSORDER = HTTP_URL + "/api/grab/transOrder";
    //跟进记录
    public static final String SALE_ADDFOLLOW = HTTP_URL + "/api/sale/addfollow";
    //客户信息
    public static final String SALE_EDITCOM = HTTP_URL + "/api/sale/editCom";
    //放弃客户
    public static final String GRAB_CANCELORDER = HTTP_URL + "/api/grab/cancelOrder";
    //申请延期
    public static final String GRAB_DELAYORDER = HTTP_URL + "/api/grab/delayOrder";


    //详情页
    public static final String CUSTOMER_MY_DETAIL = HTTP_URL_H5 + "/wxdistribute#/customer/my/detail";
    //成交续费
    public static final String BUSINESS_CONTRACT = HTTP_URL_H5 + "/wxdistribute#/business/contract";


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
    //获取城市信息
    public static final String USER_GETUSERCITYLIST = HTTP_URL + "/api/user/getusercitylist";


}
