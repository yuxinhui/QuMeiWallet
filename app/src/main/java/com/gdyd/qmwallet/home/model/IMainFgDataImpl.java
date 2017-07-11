package com.gdyd.qmwallet.home.model;

import android.util.Log;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.utils.Is;
import com.gdyd.qmwallet.utils.NetUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zanzq on 2017/3/7.
 */

public class IMainFgDataImpl implements IMainFgData {

    @Override
    public void getMainWx(JyBean jyBean, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        RequestBody body;
        if (jyBean.getLevel()==0){
            int channel = jyBean.getChannel();
            String a;
            if (channel==0){
                a="";
            }else{
                a=channel+"";
            }
            body = new FormBody.Builder()
                    //添加键值对
                    .add("mchtNo",jyBean.getMchtNo())
                    .add("money",jyBean.getMoney())
                    .add("type",jyBean.getType())
                    .add("business",jyBean.getBusiness()+"")
                    .add("mode",jyBean.getMode()+"")
                    .add("payType",jyBean.getPayType()+"")
                    .add("channel",a)
                    .build();
        }else{
            body = new FormBody.Builder()
                    //添加键值对
                    .add("mchtNo",jyBean.getMchtNo())
                    .add("money",jyBean.getMoney())
                    .add("type",jyBean.getType())
                    .add("business",jyBean.getBusiness()+"")
                    .add("mode",jyBean.getMode()+"")
                    .add("payType",jyBean.getPayType()+"")
                    .add("Level",jyBean.getLevel()+"")
                    .add("RechargeMerNo",jyBean.getRechargeMerNo())
                    .build();
        }
        Log.d("zanZQ", "getMainWx: "+jyBean);
        Request request=new Request.Builder().url(UrlConfig.TYJK).post(body).build();
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

                        onDataLoadListener.onLoadSuccess(string);
                }else{
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }

    @Override
    public void getMainKJ(JyBean jyBean, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        RequestBody body;
        if (jyBean.getLevel()==0){
            int channel = jyBean.getChannel();
            String a;
            if (channel==0){
                a="";
            }else{
                a=channel+"";
            }
            body = new FormBody.Builder()
                    //添加键值对
                    .add("mchtNo",jyBean.getMchtNo())
                    .add("money",jyBean.getMoney())
                    .add("type",jyBean.getType())
                    .add("business",jyBean.getBusiness()+"")
                    .add("mode",jyBean.getMode()+"")
                    .add("payType",jyBean.getPayType()+"")
                    .add("channel",a)
                    .build();
        }else{
            body = new FormBody.Builder()
                    //添加键值对
                    .add("mchtNo",jyBean.getMchtNo())
                    .add("money",jyBean.getMoney())
                    .add("type",jyBean.getType())
                    .add("business",jyBean.getBusiness()+"")
                    .add("mode",jyBean.getMode()+"")
                    .add("payType",jyBean.getPayType()+"")
                    .add("Level",jyBean.getLevel()+"")
                    .add("RechargeMerNo",jyBean.getRechargeMerNo())
                    .build();
        }
        Log.d("zanZQ", "getMainKJ: "+jyBean.toString());
        Request request=new Request.Builder().url(UrlConfig.TYJK).post(body).build();
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
                    onDataLoadListener.onLoadSuccess(string);
                }else{
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }

    @Override
    public void getMainZFB(JyBean jyBean, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        RequestBody body;
        if (jyBean.getLevel()==0){
            int channel = jyBean.getChannel();
            String a;
            if (channel==0){
                a="";
            }else{
                a=channel+"";
            }
            body = new FormBody.Builder()
                    //添加键值对
                    .add("mchtNo",jyBean.getMchtNo())
                    .add("money",jyBean.getMoney())
                    .add("type",jyBean.getType())
                    .add("business",jyBean.getBusiness()+"")
                    .add("mode",jyBean.getMode()+"")
                    .add("payType",jyBean.getPayType()+"")
                    .add("channel",a)
                    .build();
        }else{
            body = new FormBody.Builder()
                    //添加键值对
                    .add("mchtNo",jyBean.getMchtNo())
                    .add("money",jyBean.getMoney())
                    .add("type",jyBean.getType())
                    .add("business",jyBean.getBusiness()+"")
                    .add("mode",jyBean.getMode()+"")
                    .add("payType",jyBean.getPayType()+"")
                    .add("Level",jyBean.getLevel()+"")
                    .add("RechargeMerNo",jyBean.getRechargeMerNo())
                    .build();
        }
        Log.d("zanZQ", "getMainZFB: "+jyBean.toString());
        Request request=new Request.Builder().url(UrlConfig.TYJK).post(body).build();
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
                    onDataLoadListener.onLoadSuccess(string);
                }else{
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }
}
