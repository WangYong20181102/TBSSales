package com.tbs.sales.utils;

import com.tbs.sales.bean.KeyValueDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Wang on 2019/4/3 15:39.
 */
public class KeyValueUtils {
    /**
     * 性别
     *
     * @return
     */
    public static List<KeyValueDataBean> getSex() {
        List<KeyValueDataBean> dataBeanList = new ArrayList<>();
        dataBeanList.add(new KeyValueDataBean("男", 1));
        dataBeanList.add(new KeyValueDataBean("女", 2));
        return dataBeanList;
    }

    /**
     * 放弃类型
     *
     * @return
     */
    public static List<KeyValueDataBean> getGiveUpType() {
        List<KeyValueDataBean> dataBeanList = new ArrayList<>();
        dataBeanList.add(new KeyValueDataBean("还未联系过", 1));
        dataBeanList.add(new KeyValueDataBean("沟通过，跟不过来", 2));
        dataBeanList.add(new KeyValueDataBean("明显无意向", 3));
        dataBeanList.add(new KeyValueDataBean("其他", 9));
        return dataBeanList;
    }

    /**
     * 客户类型
     *
     * @return
     */
    public static List<KeyValueDataBean> getClientType() {
        List<KeyValueDataBean> dataBeanList = new ArrayList<>();
        dataBeanList.add(new KeyValueDataBean("无效", 5));
        dataBeanList.add(new KeyValueDataBean("暂无意向", 7));
        dataBeanList.add(new KeyValueDataBean("潜在", 1));
        dataBeanList.add(new KeyValueDataBean("意向", 3));
        return dataBeanList;
    }

    /**
     * 业务范围
     *
     * @return
     */
    public static List<KeyValueDataBean> getYeWuFanWei() {
        List<KeyValueDataBean> dataBeanList = new ArrayList<>();
        dataBeanList.add(new KeyValueDataBean("全市", 1));
        dataBeanList.add(new KeyValueDataBean("市区", 2));
        return dataBeanList;
    }

    /**
     * 合作平台
     *
     * @return
     */
    public static List<KeyValueDataBean> getHeZuoPingTai() {
        List<KeyValueDataBean> dataBeanList = new ArrayList<>();
        dataBeanList.add(new KeyValueDataBean("有", 1));
        dataBeanList.add(new KeyValueDataBean("无", 2));
        return dataBeanList;
    }

    /**
     * 电话
     */
    public static List<KeyValueDataBean> getPhone(List<String> stringList) {
        List<KeyValueDataBean> dataBeanList = new ArrayList<>();
        for (int i = 0; i < stringList.size(); i++) {
            dataBeanList.add(new KeyValueDataBean(stringList.get(i), i));
        }
        return dataBeanList;
    }

    /**
     * 新客户、潜在客户(客户标签)
     */
    public static List<KeyValueDataBean> getDetailPageClientType() {
        List<KeyValueDataBean> dataBeanList = new ArrayList<>();
        dataBeanList.add(new KeyValueDataBean("开场白挂机", 1));
        dataBeanList.add(new KeyValueDataBean("接通后挂机", 2));
        dataBeanList.add(new KeyValueDataBean("接通后无应答", 3));
        dataBeanList.add(new KeyValueDataBean("通话中断", 4));
        dataBeanList.add(new KeyValueDataBean("晚点回电", 5));
        dataBeanList.add(new KeyValueDataBean("其他", 99));
        return dataBeanList;
    }

    /**
     * 暂无意向(客户标签)
     */
    public static List<KeyValueDataBean> getDetailNoYiXiang() {
        List<KeyValueDataBean> dataBeanList = new ArrayList<>();
        dataBeanList.add(new KeyValueDataBean("合作过但不想继续合作", 11));
        dataBeanList.add(new KeyValueDataBean("不考虑网单", 12));
        dataBeanList.add(new KeyValueDataBean("已和其他平台合作", 13));
        dataBeanList.add(new KeyValueDataBean("需求不合理", 14));
        dataBeanList.add(new KeyValueDataBean("其他", 99));
        return dataBeanList;
    }
    /**
     * 成交(客户标签)
     */
    public static List<KeyValueDataBean> getDetailChengJiao() {
        List<KeyValueDataBean> dataBeanList = new ArrayList<>();
        dataBeanList.add(new KeyValueDataBean("已准备续费", 41));
        dataBeanList.add(new KeyValueDataBean("有续费意愿", 42));
        dataBeanList.add(new KeyValueDataBean("订单质量低", 43));
        dataBeanList.add(new KeyValueDataBean("服务态度差", 44));
        dataBeanList.add(new KeyValueDataBean("收费太高", 45));
        dataBeanList.add(new KeyValueDataBean("其他", 99));
        return dataBeanList;
    }

    /**
     * 无效(无效原因)
     */
    public static List<KeyValueDataBean> getWuXiao() {
        List<KeyValueDataBean> dataBeanList = new ArrayList<>();
        dataBeanList.add(new KeyValueDataBean("号码错误", 1));
        dataBeanList.add(new KeyValueDataBean("不是装修公司", 2));
        dataBeanList.add(new KeyValueDataBean("公司倒闭", 3));
        dataBeanList.add(new KeyValueDataBean("合作中", 5));
        dataBeanList.add(new KeyValueDataBean("家居/建材公司", 6));
        dataBeanList.add(new KeyValueDataBean("同行登记", 7));
        dataBeanList.add(new KeyValueDataBean("其他", 4));
        return dataBeanList;
    }

    /**
     * 客户类型
     *
     * @return
     */
    public static List<KeyValueDataBean> getClientType1() {
        List<KeyValueDataBean> dataBeanList = new ArrayList<>();
        dataBeanList.add(new KeyValueDataBean("无效", 5));
        dataBeanList.add(new KeyValueDataBean("潜在", 1));
        dataBeanList.add(new KeyValueDataBean("暂无意向", 7));
        dataBeanList.add(new KeyValueDataBean("意向", 3));
        dataBeanList.add(new KeyValueDataBean("待签约", 8));
        return dataBeanList;
    }

    /**
     * 客户类型
     *
     * @return
     */
    public static List<KeyValueDataBean> getClientType2() {
        List<KeyValueDataBean> dataBeanList = new ArrayList<>();
        dataBeanList.add(new KeyValueDataBean("待签约", 8));
        dataBeanList.add(new KeyValueDataBean("意向", 3));
        return dataBeanList;
    }

    /**
     * 客户类型
     *
     * @return
     */
    public static List<KeyValueDataBean> getClientType3() {
        List<KeyValueDataBean> dataBeanList = new ArrayList<>();
        dataBeanList.add(new KeyValueDataBean("成交", 4));
        return dataBeanList;
    }

    /**
     * 客户类型(综合查询)
     *
     * @return
     */
    public static List<KeyValueDataBean> getIQClientType() {
        List<KeyValueDataBean> dataBeanList = new ArrayList<>();
        dataBeanList.add(new KeyValueDataBean("全部", -1));
        dataBeanList.add(new KeyValueDataBean("新客户", 0));
        dataBeanList.add(new KeyValueDataBean("潜在", 1));
        dataBeanList.add(new KeyValueDataBean("暂无意向", 7));
        dataBeanList.add(new KeyValueDataBean("意向", 3));
        dataBeanList.add(new KeyValueDataBean("到期会员", 2));
        dataBeanList.add(new KeyValueDataBean("待签约", 8));
        dataBeanList.add(new KeyValueDataBean("成交", 4));
        dataBeanList.add(new KeyValueDataBean("无效", 5));
        return dataBeanList;
    }

    /**
     * 客户类型(综合查询)
     *
     * @return
     */
    public static List<KeyValueDataBean> getHomeClientType() {
        List<KeyValueDataBean> dataBeanList = new ArrayList<>();
        dataBeanList.add(new KeyValueDataBean("全部", -1));
        dataBeanList.add(new KeyValueDataBean("新客户", 0));
        dataBeanList.add(new KeyValueDataBean("潜在客户", 1));
        dataBeanList.add(new KeyValueDataBean("意向客户", 3));
        dataBeanList.add(new KeyValueDataBean("待签约", 8));
        return dataBeanList;
    }
}
