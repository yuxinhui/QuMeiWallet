package com.gdyd.qmwallet.mine.model;

import android.util.Log;

import com.gdyd.qmwallet.App;
import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.config.UrlConfig;
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
 * Created by zanzq on 2017/2/28.
 */

public class ILoadPlaceDataImpl implements ILoadPlaceData {
    private Gson gson = new Gson();

    @Override
    public void getLoadProvinceData(final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        long transTyp = EncryptionHelper.getDate();
        String transTy="1004"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);
        String value = gson.toJson(new PlaceBean("1004", transKe,transTyp));
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
        Log.d("zanZQ", "getLoadProvinceData: "+body.toString());
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
                   ProvinceBean provinceBean = gson.fromJson(string, ProvinceBean.class);
                    onDataLoadListener.onLoadSuccess(provinceBean);
                }else{
                    Log.d("zanZQ", "onResponse: ++++++++++++");
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }

    @Override
    public void getLoadCityData(String id, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        long transTyp = EncryptionHelper.getDate();
        String transTy="1005"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);
        String value = gson.toJson(new PlaceBean("1005", transKe, id, transTyp));
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
        Log.d("zanZQ", "getLoadProvinceData: "+body.toString());
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
                    CityBean cityBean = gson.fromJson(string, CityBean.class);
                    onDataLoadListener.onLoadSuccess(cityBean);
                }else{
                    Log.d("zanZQ", "onResponse: ++++++++++++");
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }

    @Override
    public void getLoadAreaData(String id, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        long transTyp = EncryptionHelper.getDate();
        String transTy="1005"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);
        String value = gson.toJson(new PlaceBean("1005", transKe, id, transTyp));
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
                    AreaBean areaBean = gson.fromJson(string, AreaBean.class);
                    onDataLoadListener.onLoadSuccess(areaBean);
                }else{
                    Log.d("zanZQ", "onResponse: ++++++++++++");
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }
}
