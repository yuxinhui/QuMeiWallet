package com.gdyd.qmwallet.home.model;

import android.util.Log;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.mine.model.CardBean;
import com.gdyd.qmwallet.mine.model.PhotoBean;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.NetUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zanzq on 2017/3/6.
 */

public class IManageMentDataImpl implements IManageMentData {
    private Gson gson = new Gson();
    @Override
    public void getManageMentInfo(PlaceBean placeBean, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        String value = gson.toJson(placeBean);
        try {
            String key = EncryptionHelper.key;
            Log.d("zanZQ", "getBannerData:"+key);
            value = EncryptionHelper.aesEncrypt(value, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("zanZQ", "getManageMentInfo: "+value);
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
                    MemberDetailsBean memberDetailsBean = gson.fromJson(string, MemberDetailsBean.class);
                    if (memberDetailsBean!=null&&memberDetailsBean.getnResul()==1){
                        MemberDetailsBean.DataBean dataBean = gson.fromJson(memberDetailsBean.getData(), MemberDetailsBean.DataBean.class);
                        try {
                            memberDetailsBean.setData(null);
                            memberDetailsBean.setDataBean(dataBean);
                            JSONArray array = new JSONArray(dataBean.getMerchant());
                            if (array.length()>0){
                                ArrayList<MemberDetailsBean.DataBean.MerchantBean> list=new ArrayList<MemberDetailsBean.DataBean.MerchantBean>();
                                for (int i = 0; i < array.length(); i++) {
                                    list.add(gson.fromJson(array.getString(i), MemberDetailsBean.DataBean.MerchantBean.class));
                                }
                                dataBean.setList(list);
                                dataBean.setMerchant(null);
                                onDataLoadListener.onLoadSuccess(memberDetailsBean);
                            }else{
                                onDataLoadListener.onLoadSuccess(memberDetailsBean);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            onDataLoadListener.onLoadSuccess(memberDetailsBean);
                        }
                    }else{
                        onDataLoadListener.onLoadSuccess(null);
                    }


                }else{
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }

    @Override
    public void getcardInfo(PhotoBean photoBean, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        String value = gson.toJson(photoBean);
        try {
            String key = EncryptionHelper.key;
            Log.d("zanZQ", "getBannerData:"+key);
            value = EncryptionHelper.aesEncrypt(value, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("zanZQ", "getManageMentInfo: "+value);
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
                //    Log.d("zanZQ", "onResponse: "+string);
                    try {
                        string = EncryptionHelper.aesDecrypt(string, EncryptionHelper.key);
                        Log.d("zanZQ", "onResponse:bande "+string);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    CardBean cardBean = gson.fromJson(string, CardBean.class);
                    if (cardBean!=null&&cardBean.getNResul()==1){
                        try {
                            JSONArray array = new JSONArray(cardBean.getData());
                            if (array.length()>0){
                                ArrayList<CardBean.DataBean> list=new ArrayList<>();
                                for (int i = 0; i < array.length(); i++) {
                                    list.add(gson.fromJson(array.getString(i), CardBean.DataBean.class));
                                }
                                cardBean.setDataBeen(list);
                                cardBean.setData(null);
                                onDataLoadListener.onLoadSuccess(cardBean);
                            }else{
                                onDataLoadListener.onLoadSuccess(cardBean);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            onDataLoadListener.onLoadSuccess(cardBean);
                        }
                    }else{
                        onDataLoadListener.onLoadSuccess(null);
                    }


                }else{
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }

    @Override
    public void setCard(PhotoBean photoBean, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        String value = gson.toJson(photoBean);
        try {
            String key = EncryptionHelper.key;
            Log.d("zanZQ", "getBannerData:"+key);
            value = EncryptionHelper.aesEncrypt(value, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("zanZQ", "getManageMentInfo: "+value);
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
                    //    Log.d("zanZQ", "onResponse: "+string);
                    try {
                        string = EncryptionHelper.aesDecrypt(string, EncryptionHelper.key);
                        Log.d("zanZQ", "onResponse:bande "+string);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                        onDataLoadListener.onLoadSuccess(string);


                }else{
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }

    @Override
   public void deleteBankCard(PhotoBean photoBean,final OnDataLoadListener onDataLoadListener){
        OkHttpClient client = NetUtil.getClient();
        String value = gson.toJson(photoBean);
        try {
            String key = EncryptionHelper.key;
            Log.d("zanZQ", "getBannerData:"+key);
            value = EncryptionHelper.aesEncrypt(value, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("zanZQ", "getManageMentInfo: "+value);
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
                    //    Log.d("zanZQ", "onResponse: "+string);
                    try {
                        string = EncryptionHelper.aesDecrypt(string, EncryptionHelper.key);
                        Log.d("zanZQ", "onResponse:bande "+string);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    onDataLoadListener.onLoadSuccess(string);


                }else{
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });

    }
}
