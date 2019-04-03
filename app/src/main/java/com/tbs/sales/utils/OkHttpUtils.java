package com.tbs.sales.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mr.Wang on 2019/2/25 10:16.
 */
public class OkHttpUtils {
    private static OkHttpClient client;
    private static String TAG="OKHttpUtils";

    public OkHttpUtils() {
    }

    public static OkHttpClient getInstance() {
        if (client == null) {
            synchronized (OkHttpClient.class) {
                if (client == null) {
                    client = new OkHttpClient();
                }
            }
        }
        return client;
    }

    /**
     * get请求
     *
     * @param url
     * @param callback
     */
    public static void get(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }

    /**
     * get请求
     *
     * @param url
     * @param paramsMap
     * @param callback
     */
    public static void get(String url, HashMap<String, Object> paramsMap, Callback callback) {
        paramsMap.put("plat","android");
        StringBuilder tempParams = new StringBuilder();
        try {
            //处理参数
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                //对参数进行URLEncoder
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(String.valueOf(paramsMap.get(key)), "utf-8")));
                pos++;
            }
            //补全请求地址
            String requestUrl = String.format("%s?%s", url, tempParams.toString());

            Request request = new Request.Builder()
                    .url(requestUrl)
                    .build();
            Call call = getInstance().newCall(request);
            call.enqueue(callback);


        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }




    }


    /**
     * post请求
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void post(String url, HashMap<String, Object> params, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("plat","android");
        for (String key : params.keySet()) {
            builder.add(key, String.valueOf(params.get(key)));
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);

    }

    public static void downFile(final Context context, String url, final File fileDir, final String fileName) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                final Activity activity = (Activity) context;
                try {
                    is = response.body().byteStream();
                    File dirFile = new File(fileDir.getPath());
                    if (!dirFile.exists()) {
                        dirFile.mkdir();
                    }
                    //创建图片文件
                    File file = new File(fileDir.getPath(), fileName);
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    //保存下载文件路径
                    CacheManager.setLoadingAdPath(context, fileDir.getPath() + "/" + fileName);
                    Log.e("HttpUtils", "下载文件成功！===" + file.getPath());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "图片下载成功!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(fileDir, fileName)));
                            activity.sendBroadcast(intent);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("HttpUtils", "下载出错===" + e.toString());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "图片下载失败!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } finally {
                    if (is != null) is.close();
                    if (fos != null) fos.close();
                }
            }
        });
    }

}
