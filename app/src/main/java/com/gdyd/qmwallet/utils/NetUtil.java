package com.gdyd.qmwallet.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by ASUS on 2016/10/30.
 */

public class NetUtil {
    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .build();
    public static OkHttpClient getClient(){
        return client;
    }
    public static boolean getNetworkState(Context context){
        ConnectivityManager ss = (ConnectivityManager) context.getSystemService (context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = ss.getActiveNetworkInfo();
        //如果手机没联网或者网络不可用，则不执行网络请求
        if (activeNetwork == null || !activeNetwork.isAvailable()) {
            return false;
        }
        return true;
    }
    /**
     * 判断网络是否连接,需加入"android.permission.ACCESS_NETWORK_STATE"权限
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     *
     * @param context
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWiFiNetworkInfo = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if(mWiFiNetworkInfo.isConnected())
        {
            return true ;
        }
        return false;
    }

    /**
     *
     * @param context
     * @return
     */
    public static boolean isConnectionNet(Context context) {
        boolean flag = false;
        if (isConnected(context)) {
            flag = true;
        } else if (isWifi(context)) {
            flag = true;
        }
        return flag;
    }
}
