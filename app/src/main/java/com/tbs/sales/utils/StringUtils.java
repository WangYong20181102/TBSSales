package com.tbs.sales.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mr.Wang on 2019/3/2 15:51.
 */
public class StringUtils {
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    private final static Pattern phone = Pattern
            .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 返回当前系统时间
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    /**
     * 返回当前系统时间
     */
    public static String getDataTime() {
        return getDataTime("HH:mm");
    }

    /**
     * 毫秒值转换为mm:ss
     *
     * @param ms
     * @author kymjs
     */
    public static String timeFormat(int ms) {
        StringBuilder time = new StringBuilder();
        time.delete(0, time.length());
        ms /= 1000;
        int s = ms % 60;
        int min = ms / 60;
        if (min < 10) {
            time.append(0);
        }
        time.append(min).append(":");
        if (s < 10) {
            time.append(0);
        }
        time.append(s);
        return time.toString();
    }

    /**
     * 将字符串转位日期类型
     *
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 判断给定字符串是否空白串 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

//    /**
//     * 判断是不是一个合法的手机号码
//     */
//    public static boolean isPhone(String phoneNum) {
//        if (phoneNum == null || phoneNum.trim().length() == 0)
//            return false;
//        return phone.matcher(phoneNum).matches();
//    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * String转long
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * String转double
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static double toDouble(String obj) {
        try {
            return Double.parseDouble(obj);
        } catch (Exception e) {
        }
        return 0D;
    }

    /**
     * 字符串转布尔
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 判断一个字符串是不是数字
     */
    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 获取AppKey
     */
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return apiKey;
    }

    /**
     * MD5加密
     */
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * KJ加密
     */
    public static String KJencrypt(String str) {
        char[] cstr = str.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char c : cstr) {
            hex.append((char) (c + 5));
        }
        return hex.toString();
    }

    /**
     * KJ解密
     */
    public static String KJdecipher(String str) {
        char[] cstr = str.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char c : cstr) {
            hex.append((char) (c - 5));
        }
        return hex.toString();
    }

    /**
     * 判断字符串是否为纯数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否为纯字母
     *
     * @param str
     * @return
     */
    public static boolean isAlphabet(String str) {
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否为纯汉字
     *
     * @param str
     * @return
     */
    public static boolean isChineseCharacters(String str) {
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否包含特殊符号
     *
     * @param str
     * @return
     */
    public static boolean isSpecialCharacters(String str) {
        Pattern pattern = Pattern.compile("^[a-zA-Z\u4E00-\u9FA5\\d]*$");
        Matcher m = pattern.matcher(str);
        return m.matches();
    }

    /**
     * 判断是否为手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isPhone(String mobiles) {
        String str = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1})|(14[0-9]{1})|(16[6])|(19[9]))+\\d{8})$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断密码是否符合生产密码的条件，不能是纯数字，不能输纯字母，由数字加字母或下划线结合的6到16位
     *
     * @param password 密码
     */
    public static boolean isPassword(String password) {
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)(?![_]+$)[0-9A-Za-z_]{6,16}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 判断密码是否符合生产密码的条件，不能是纯数字，不能输纯字母，由数字加字母或下划线结合的8到16位
     *
     * @param password 设置注册密码
     */
    public static boolean isPasswordRegist(String password) {
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)(?![_]+$)[0-9A-Za-z_]{8,16}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 去掉特殊字符
     *
     * @param str
     * @return
     */
    public static boolean isExcptional(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 设置模糊手机号码
     *
     * @return
     */
    public static String setBlurryPhone(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 11) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(phoneNumber.subSequence(0, 3));
        builder.append("******");
        builder.append(phoneNumber.subSequence(9, 11));
        return builder.toString();
    }

    /**
     * 设置座机号码
     *
     * @return
     */
    public static String setPhone(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        if (phoneNumber.length() > 3) {
            builder.append(phoneNumber.subSequence(0, 3));
            builder.append("-");
            builder.append(phoneNumber.subSequence(4, phoneNumber.length()));
        }else {
            builder.append(phoneNumber.subSequence(0, phoneNumber.length()));
        }
        return builder.toString();
    }

    /**
     * 设置模糊姓名
     *
     * @return
     */
    public static String setBlurryName(String sName) {
        if (TextUtils.isEmpty(sName)) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(sName.subSequence(0, 1));
        for (int i = 1; i < sName.length(); i++) {
            builder.append("*");
            if (i >= 10) {
                builder.setLength(0);
                builder.append(sName.subSequence(0, 1)).append("*********...");
                break;
            }
        }
        return builder.toString();
    }

    /**
     * 设置模糊银行卡号
     *
     * @return
     */
    public static String setBlurryBankNumber(String bankNumber) {
        if (TextUtils.isEmpty(bankNumber)) {
            return "";
        } else {
            int length = bankNumber.length();
            String blurryPhone = null;
            if (length >= 11) {
                blurryPhone = bankNumber.substring(0, length - (length - 4))
                        + "   ****   ****   " + bankNumber.substring((length - 3), length);
            } else {
                blurryPhone = "";
            }
            return blurryPhone;
        }
    }

    /**
     * 设置模糊银行卡号
     *
     * @return
     */
    public static String setBlurryBankNumber2(String bankNumber) {
        if (TextUtils.isEmpty(bankNumber)) {
            return "";
        } else {
            int length = bankNumber.length();
            String blurryPhone = null;
            if (length >= 11) {
                blurryPhone = bankNumber.substring(0, length - (length - 4))
                        + " **** **** " + bankNumber.substring((length - 3), length);
            } else {
                blurryPhone = "";
            }
            return blurryPhone;
        }
    }
}
