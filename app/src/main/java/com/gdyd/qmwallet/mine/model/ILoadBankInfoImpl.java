package com.gdyd.qmwallet.mine.model;

import android.util.Log;

import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.config.UrlConfig;
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
 * Created by zanzq on 2017/2/28.
 */

public class ILoadBankInfoImpl implements ILoadBankInfo {
    private Gson gson = new Gson();
    @Override
    public void getSumBankInfo(final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        long transTyp = EncryptionHelper.getDate();
        String transTy="1006"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);

        String value = gson.toJson(new PlaceBean("1006", transKe, transTyp));
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
                    BlankBean blankBean = gson.fromJson(string, BlankBean.class);
                   onDataLoadListener.onLoadSuccess(blankBean);
                }else{
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }

    @Override
    public void SubmitUserInfo(UserInfoBean userInfoBean, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        String value = gson.toJson(userInfoBean);
        Log.d("zanZQ", "SubmitUserInfo: "+value);
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
    public void getBranchBankInfo(PlaceBean placeBean , final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        String value = gson.toJson(placeBean);
        Log.d("zanZQ", "getBranchBankInfo: "+value);
        try {
            String key = EncryptionHelper.key;
         //   Log.d("zanZQ", "getBannerData:"+key);
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
                 //   Log.d("zanZQ", "onResponse: "+string);
                    try {
                        try {
                            string = EncryptionHelper.aesDecrypt(string, EncryptionHelper.key);
                            Log.d("zanZQ", "onResponse:bande "+string);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        BranchBankBean branchBankBean1 = gson.fromJson(string, BranchBankBean.class);
                        int nResul = branchBankBean1.getNResul();
                        if (nResul==1){
                            BranchBankBean.DataBean dataBean = gson.fromJson(branchBankBean1.getData(), BranchBankBean.DataBean.class);
                            branchBankBean1.setDataBean(dataBean);
                            branchBankBean1.setData(null);
                            JSONArray array = new JSONArray(dataBean.getBank_stlno());
                            ArrayList<BranchBankBean.DataBean.Bank_stlnoBean> list=new ArrayList<   BranchBankBean.DataBean.Bank_stlnoBean>();
                            for (int i = 0; i < array.length(); i++) {
                                BranchBankBean.DataBean.Bank_stlnoBean bank_stlnoBean = gson.fromJson(array.getString(i), BranchBankBean.DataBean.Bank_stlnoBean.class);
                                list.add(bank_stlnoBean);
                            }
                            dataBean.setList(list);
                            dataBean.setBank_stlno(null);
                            onDataLoadListener.onLoadSuccess(branchBankBean1);
                        }else{
                            onDataLoadListener.onLoadSuccess(branchBankBean1);
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
    public void getUserInfo(String merchant, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        long transTyp = EncryptionHelper.getDate();
        String transTy="1017"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);
        String value = gson.toJson(new PhotoBean("1017", transKe, merchant, transTyp));
        try {
            String key = EncryptionHelper.key;
          //  Log.d("zanZQ", "getBannerData:"+key);
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
                    String string = response.body().string().trim();
                  //  Log.d("zanZQ", "onResponse: "+string);
                    try {
                        string = EncryptionHelper.aesDecrypt(string, EncryptionHelper.key);
                        Log.d("zanZQ", "onResponse:bande "+string);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    LoginInfoBean merchantBean = gson.fromJson(string, LoginInfoBean.class);
                    LoginInfoBean.UserData userData = gson.fromJson(merchantBean.getData(), LoginInfoBean.UserData.class);
                    merchantBean.setUserData(userData);
                    merchantBean.setData(null);
                    onDataLoadListener.onLoadSuccess(merchantBean);

                }else{
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }
}
