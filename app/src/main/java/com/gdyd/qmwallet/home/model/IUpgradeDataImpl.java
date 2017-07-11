package com.gdyd.qmwallet.home.model;

import android.util.Log;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.mine.model.BlankBean;
import com.gdyd.qmwallet.mine.model.MerchantInfoBean;
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

public class IUpgradeDataImpl implements IUpgradeData {
    private Gson gson = new Gson();
    @Override
    public void getUpgradeInfo(String merchantNo,final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        long transTyp = EncryptionHelper.getDate();
        String transTy="1049"+transTyp;
        String transKe = EncryptionHelper.md5(transTy);
        String value = gson.toJson(new PhotoBean("1049", transKe, merchantNo,transTyp));

        Log.d("zanZQ", "getUpgradeInfo: "+value);
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
                    MemberInfoBean memberInfoBean = gson.fromJson(string, MemberInfoBean.class);
                    if (memberInfoBean.getnResul()==1){
                        MemberInfoBean.DataBean dataBean = gson.fromJson(memberInfoBean.getData(), MemberInfoBean.DataBean.class);
                        ArrayList<MemberInfoBean.DataBean.RecomMemberLevelBean> list=new ArrayList<MemberInfoBean.DataBean.RecomMemberLevelBean>();
                        memberInfoBean.setDataBean(dataBean);
                        memberInfoBean.setData(null);
                        try {
                            JSONArray  jsonArray = new JSONArray(memberInfoBean.getDataBean().getRecomMemberLevel());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                MemberInfoBean.DataBean.RecomMemberLevelBean dataList = gson.fromJson(jsonArray.getString(i), MemberInfoBean.DataBean.RecomMemberLevelBean.class);
                                list.add(dataList);
                            }
                            memberInfoBean.getDataBean().setList(list);
                            memberInfoBean.getDataBean().setRecomMemberLevel(null);
                            onDataLoadListener.onLoadSuccess(memberInfoBean);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else{
                        onDataLoadListener.onLoadSuccess(memberInfoBean);
                    }

                }else{
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }

    @Override
    public void getUpgradeInfo(final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        long transTyp = EncryptionHelper.getDate();
        String transTy="1061"+transTyp;
        String transKe = EncryptionHelper.md5(transTy);
        String value = gson.toJson(new PlaceBean("1061", transKe,transTyp, APPConfig.AgentID));
        try {
            String key = EncryptionHelper.key;
            Log.d("zanZQ", "getBannerData:"+key);
            value = EncryptionHelper.aesEncrypt(value, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("zanZQ", "getUpgradeInfo: "+value);
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
                    MemberInfoBean memberInfoBean = gson.fromJson(string, MemberInfoBean.class);
                    if (memberInfoBean!=null&&memberInfoBean.getnResul()==1){
                        MemberInfoBean.DataBean dataBean = new MemberInfoBean.DataBean();
                        dataBean.setRecomMemberLevel(memberInfoBean.getData());
                        ArrayList<MemberInfoBean.DataBean.RecomMemberLevelBean> list=new ArrayList<MemberInfoBean.DataBean.RecomMemberLevelBean>();
                        memberInfoBean.setDataBean(dataBean);
                        memberInfoBean.setData(null);
                        try {
                            JSONArray  jsonArray = new JSONArray(memberInfoBean.getDataBean().getRecomMemberLevel());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                MemberInfoBean.DataBean.RecomMemberLevelBean dataList = gson.fromJson(jsonArray.getString(i), MemberInfoBean.DataBean.RecomMemberLevelBean.class);
                                list.add(dataList);
                            }
                            memberInfoBean.getDataBean().setList(list);
                            memberInfoBean.getDataBean().setRecomMemberLevel(null);
                            onDataLoadListener.onLoadSuccess(memberInfoBean);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else{
                        onDataLoadListener.onLoadSuccess(memberInfoBean);
                    }

                }else{
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }
}
