package com.gdyd.qmwallet.Other.model;

import android.util.Log;

import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.NetUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zanzq on 2017/3/1.
 */

public class ILoginDataImpl implements ILoginData {
    private Gson gson = new Gson();
    @Override
    public void getLogin(PlaceBean placeBean, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        String value = gson.toJson(placeBean);
        Log.d("zanZQ", "getLogin: "+value);
        try {
            String key = EncryptionHelper.key;
            Log.d("zanZQ", "getBannerData:"+key);
            value = EncryptionHelper.aesEncrypt(value, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody body = new FormBody.Builder()
                //添加键值对
                .add("sPostParam", value)
                .build();
        Request request=new Request.Builder().url(UrlConfig.URI).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onDataLoadListener.onLoadFailed(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
             if (response.isSuccessful()){
                 String string = response.body().string();
                 Log.d("zanZQ", "onResponse: "+string);
                 try {
                     string = EncryptionHelper.aesDecrypt(string, EncryptionHelper.key);
                     Log.d("zanZQ", "onResponse:bande "+string);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
                 LoginInfoBean loginInfoBean = gson.fromJson(string, LoginInfoBean.class);
                 LoginInfoBean.UserData userData = gson.fromJson(loginInfoBean.getData(), LoginInfoBean.UserData.class);
                 loginInfoBean.setUserData(userData);
                 loginInfoBean.setData(null);
                 onDataLoadListener.onLoadSuccess(loginInfoBean);
             }
            }
        });
    }

    @Override
    public void getAlterPwd(PlaceBean placeBean, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        String value = gson.toJson(placeBean);
        Log.d("zanZQ", "getAlterPwd: "+value);
        try {
            String key = EncryptionHelper.key;
            Log.d("zanZQ", "getBannerData:"+key);
            value = EncryptionHelper.aesEncrypt(value, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody body = new FormBody.Builder()
                //添加键值对
                .add("sPostParam", value)
                .build();
        Request request=new Request.Builder().url(UrlConfig.URI).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onDataLoadListener.onLoadFailed(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String string = response.body().string();
                    Log.d("zanZQ", "onResponse: "+string);
                    try {
                        string = EncryptionHelper.aesDecrypt(string, EncryptionHelper.key);
                        Log.d("zanZQ", "onResponse:bande "+string);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    onDataLoadListener.onLoadSuccess(string);
                }
            }
        });
    }
}
