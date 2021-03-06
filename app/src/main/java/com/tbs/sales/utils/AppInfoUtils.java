package com.tbs.sales.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Mr.Wang on 2019/3/2 09:52.
 */
public class AppInfoUtils {

    //获取App的版本信息
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取手机mac地址<br/>
     * 错误返回12个0
     */
    public static String getMacAddress(Context context) {
        // 获取mac地址：
        String macAddress = "000000000000";
        try {
            WifiManager wifiMgr = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
            if (null != info) {
                if (!TextUtils.isEmpty(info.getMacAddress()))
                    macAddress = info.getMacAddress().replace(":", "");
                else
                    return macAddress;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return macAddress;
        }
        return macAddress;
    }

    /**
     * 判断WIFI网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 根据IP地址获取MAC地址
     *
     * @return
     */
    public static String getLocalMacAddressFromIp() {
        String strMacAddr = null;
        try {
            //获得IpD地址
            InetAddress ip = getLocalInetAddress();
            byte[] b = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        } catch (Exception e) {

        }

        return strMacAddr;
    }

    /**
     * 获取移动设备本地IP
     *
     * @return
     */
    private static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            //列举
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface.getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {//是否还有元素
                NetworkInterface ni = (NetworkInterface) en_netInterface.nextElement();//得到下一个元素
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();//得到一个ip地址的列举
                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1)
                        break;
                    else
                        ip = null;
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException e) {

            e.printStackTrace();
        }
        return ip;
    }

    /**
     * 通过网络接口取
     *
     * @return
     */
    public static String getNewMac() {
        String strMacAddr = "000000000000";
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return strMacAddr;
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    strMacAddr = res1.deleteCharAt(res1.length() - 1).toString();
                }
                return strMacAddr;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return strMacAddr;
    }

    //获取新的渠道代码值 链接中的tbschcode
    public static String getNewChannType(Context context) {
        if (getChannType(context).equals("ali")) {
            return "al_yysc_tbs";
        } else if (getChannType(context).equals("anzhi")) {
            return "az_yysc_tbs";
        } else if (getChannType(context).equals("appbaidu")) {
            return "bd_yysc_tbs";
        } else if (getChannType(context).equals("apphuawei")) {
            return "hw_yysc_tbs";
        } else if (getChannType(context).equals("applenovo")) {
            return "lx_yysc_tbs";
        } else if (getChannType(context).equals("appmeizu")) {
            return "mz_yysc_tbs";
        } else if (getChannType(context).equals("appoppo")) {
            return "oppo_yysc_tbs";
        } else if (getChannType(context).equals("appqihu")) {
            return "360_yysc_tbs";
        } else if (getChannType(context).equals("appttt")) {
            return "cz_yysc_tbs";
        } else if (getChannType(context).equals("appvivo")) {
            return "vivo_yysc_tbs";
        } else if (getChannType(context).equals("appxiaomi")) {
            return "xm_yysc_tbs";
        } else if (getChannType(context).equals("appyyb")) {
            return "tx_yysc_tbs";
        } else if (getChannType(context).equals("yybff")) {
            return "tx_yysc_yybff";
        } else if (getChannType(context).equals("jinli")) {
            return "jl_yysc_tbs";
        } else if (getChannType(context).equals("leshi")) {
            return "tbs";
        } else if (getChannType(context).equals("sougou")) {
            return "sg_yysc_tbs";
        } else if (getChannType(context).equals("sumsung")) {
            return "sx_yysc_tbs";
        } else if (getChannType(context).equals("youyi")) {
            return "yysc_yysc_tbs";
        } else if (getChannType(context).equals("zhuanghaojia")) {
            return "";
        } else if (getChannType(context).equals("tbspc")) {
            return "tbspc_pczy";
        } else {
            return "";
        }
    }

    //获取渠道信息（在AndroidManifest文件中的渠道信息）
    public static String getChannType(Context context) {
        String mChannType = "";
        try {
            ApplicationInfo applicationInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            if (!TextUtils.isEmpty(applicationInfo.metaData.getString("UMENG_CHANNEL"))) {
                mChannType = applicationInfo.metaData.getString("UMENG_CHANNEL");
                return mChannType;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return mChannType;
    }

    //token
    public static String getToekn(Context context) {
        return context.getSharedPreferences("userInfo", 0).getString("token", "");
    }

    public static void setToken(Context context, String token) {
        context.getSharedPreferences("userInfo", 0).edit().putString("token", token).apply();
    }

    //用户的昵称
    public static String getUserNickname(Context context) {
        return context.getSharedPreferences("userInfo", 0).getString("nickname", "");
    }

    public static void setUserNickname(Context context, String nickname) {
        context.getSharedPreferences("userInfo", 0).edit().putString("nickname", nickname).apply();
    }

    //用户的头像
    public static String getUserIcon(Context context) {
        return context.getSharedPreferences("userInfo", 0).getString("icon", "");
    }

    public static void setUserIcon(Context context, String icon) {
        context.getSharedPreferences("userInfo", 0).edit().putString("icon", icon).apply();
    }

    //用户的性别
    public static int getUserSex(Context context) {
        return context.getSharedPreferences("userInfo", 0).getInt("sex", 0);
    }

    public static void setUserSex(Context context, int icon) {
        context.getSharedPreferences("userInfo", 0).edit().putInt("sex", icon).apply();
    }

    //用户的职位
    public static String getUserRoleDesc(Context context) {
        return context.getSharedPreferences("userInfo", 0).getString("roledesc", "");
    }

    public static void setUserRoleDesc(Context context, String icon) {
        context.getSharedPreferences("userInfo", 0).edit().putString("roledesc", icon).apply();
    }

    //用户所在的城市
    public static String getUserCity(Context context) {
        return context.getSharedPreferences("userInfo", 0).getString("cityname", "");
    }

    public static void setUserCity(Context context, String cityname) {
        context.getSharedPreferences("userInfo", 0).edit().putString("cityname", cityname).apply();
    }

    //负责区域
    public static String getOrganAreaIds(Context context) {
        return context.getSharedPreferences("userInfo", 0).getString("organ_area_ids", "");
    }

    public static void setOrganAreaIds(Context context, String province) {
        context.getSharedPreferences("userInfo", 0).edit().putString("organ_area_ids", province).apply();
    }

    //用户所在的省份
    public static String getUserProvince(Context context) {
        return context.getSharedPreferences("userInfo", 0).getString("province", "");
    }

    public static void setUserProvince(Context context, String province) {
        context.getSharedPreferences("userInfo", 0).edit().putString("province", province).apply();
    }

    //用户的id
    public static String getId(Context context) {
        return context.getSharedPreferences("userInfo", 0).getString("id", "");
    }

    public static void setId(Context context, String id) {
        context.getSharedPreferences("userInfo", 0).edit().putString("id", id).apply();
    }

    //用户绑定的手机号码
    public static String getCellPhone(Context context) {
        return context.getSharedPreferences("userInfo", 0).getString("cellphone", "");
    }

    public static void setCellPhone(Context context, String cellphone) {
        context.getSharedPreferences("userInfo", 0).edit().putString("cellphone", cellphone).apply();
    }

    //用户的Userid
    public static String getUserid(Context context) {
        return context.getSharedPreferences("userInfo", 0).getString("userid", "");
    }

    public static void setUserid(Context context, String userid) {
        context.getSharedPreferences("userInfo", 0).edit().putString("userid", userid).commit();
    }

    //领取人
    public static String getPeople(Context context) {
        return context.getSharedPreferences("userInfo", 0).getString("people", "");
    }

    public static void setPeople(Context context, String people) {
        context.getSharedPreferences("userInfo", 0).edit().putString("people", people).commit();
    }

    //用户的md5密码
    public static String getUserMd5PassWord(Context context) {
        return context.getSharedPreferences("userInfo", 0).getString("user_md5_password", "");
    }

    public static void setUserMd5PassWord(Context context, String user_md5_password) {
        context.getSharedPreferences("userInfo", 0).edit().putString("user_md5_password", user_md5_password).commit();
    }

    /**
     * 判断用户是否是第一次启动app
     */
    public static boolean getFirstLaunch(Context context) {
        return context.getSharedPreferences("firstLaunch", 0).getBoolean("storage", false);
    }

    public static void setFirstLaunch(Context context, boolean b) {
        context.getSharedPreferences("firstLaunch", 0).edit().putBoolean("storage", b).commit();
    }

    /**
     * 启动页
     */
    public static boolean getFirstLaunchGlidePage(Context context) {
        return context.getSharedPreferences("glidePage", 0).getBoolean("bGlidePage", false);
    }

    public static void setFirstLaunchGlidePage(Context context, boolean b) {
        context.getSharedPreferences("glidePage", 0).edit().putBoolean("bGlidePage", b).commit();
    }

    //存储推送的唯一标识
    public static String getPushRegisterId(Context context) {
        return context.getSharedPreferences("PushRegisterId", 0).getString("mPushRegisterId", "");
    }

    //获取推送的唯一标识
    public static void setPushRegisterId(Context context, String mPushRegisterId) {
        context.getSharedPreferences("PushRegisterId", 0).edit().putString("mPushRegisterId", mPushRegisterId).commit();
    }

    /**
     * 保存账号到本地
     */
    public static String getLoginAccound(Context context) {
        return context.getSharedPreferences("loginState", 0).getString("loginAccound", "");
    }

    public static void setLoginAccount(Context context, String account) {
        context.getSharedPreferences("loginState", 0).edit().putString("loginAccound", account).apply();
    }

    //是否弹更新弹窗
    public static String getIsShowUpdataDialog(Context context) {
        return context.getSharedPreferences("dialogInfo", 0).getString("updataDialog", "");
    }

    public static void setIsShowUpdataDialog(Context context, String updataDialog) {
        context.getSharedPreferences("dialogInfo", 0).edit().putString("updataDialog", updataDialog).commit();
    }
}
