package com.gdyd.qmwallet.Other.model;

import android.util.Log;

import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.NetUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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

public class IRegisterDataImpl implements IRegisterData {
    private Gson gson = new Gson();
    private String code;
    private String phoneNumber;

    @Override
    public void getCode(String phone,String type, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        long transTyp = EncryptionHelper.getDate();
        String transTy="1000"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);
        String value = gson.toJson(new PlaceBean("1000", transKe, type, phone, APPConfig.TYPE, transTyp));
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
                    Log.d("zanZQ", "onResponse:注册 "+string);
                    try {
                        string = EncryptionHelper.aesDecrypt(string, EncryptionHelper.key);
                        Log.d("zanZQ", "onResponse:bande "+string);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONObject object = new JSONObject(string);
                        int nResul = object.getInt("nResul");
                        if (nResul==1){
                            onDataLoadListener.onLoadSuccess("短信已发送");
                        }else{
                            onDataLoadListener.onLoadSuccess(object.getString("sMessage"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        onDataLoadListener.onLoadSuccess(null);
                    }

                }else{
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }

    @Override
    public void getRegister(final PlaceBean placeBean, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        code = placeBean.getCode();
        phoneNumber = placeBean.getPhoneNo();
        long transTyp = EncryptionHelper.getDate();
        String transTy="1001"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);
        String value = gson.toJson(new PlaceBean("1001", transKe, code, phoneNumber,transTyp));
        try {
            String key = EncryptionHelper.key;
            Log.d("zanZQ", "getBannerData:"+key);
            value = EncryptionHelper.aesEncrypt(value, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("zanZQ", "getRegister: "+value);
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
                    Log.d("zanZQ", "onResponse:注册 "+string);
                    try {
                        string = EncryptionHelper.aesDecrypt(string, EncryptionHelper.key);
                        Log.d("zanZQ", "onResponse:bande "+string);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(string);
                        int nResul = jsonObject.getInt("nResul");
                        if (nResul==1){
                            OkHttpClient client = NetUtil.getClient();

                            String value = gson.toJson(placeBean);
                            Log.d("zanZQ", "onResponse: "+value);
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
                                         JSONObject jsonObject = null;
                                         try {
                                             jsonObject = new JSONObject(string);
                                             int nResul = jsonObject.getInt("nResul");
                                             if (nResul==1){
                                               onDataLoadListener.onLoadSuccess(jsonObject.getString("sMessage"));
                                             }else{
                                                 onDataLoadListener.onLoadSuccess(jsonObject.getString("sMessage"));
                                             }
                                         } catch (JSONException e) {
                                             e.printStackTrace();
                                         }

                                     }
                                }
                            });
                        }else{
                            onDataLoadListener.onLoadSuccess(jsonObject.getString("sMessage"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }

    @Override
    public void getVerify(PlaceBean placeBean, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        code = placeBean.getCode();
        phoneNumber = placeBean.getPhoneNo();
        long transTyp = EncryptionHelper.getDate();
        String transTy="1001"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);
        String value = gson.toJson(new PlaceBean("1001", transKe, code, phoneNumber,transTyp));
        try {
            String key = EncryptionHelper.key;
            Log.d("zanZQ", "getBannerData:"+key);
            value = EncryptionHelper.aesEncrypt(value, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("zanZQ", "getRegister: "+value);
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
                    Log.d("zanZQ", "onResponse:验证 "+string);
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
    public void getAlter(PlaceBean placeBean, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        String value = gson.toJson(placeBean);
        Log.d("zanZQ", "onResponse: "+value);
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
                }else{
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }

    @Override
    public void getBankCode(PlaceBean placeBean, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        String value = gson.toJson(placeBean);
        Log.d("zanZQ", "onResponse: "+value);
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
                }else{
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }
}
