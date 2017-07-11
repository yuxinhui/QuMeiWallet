package com.gdyd.qmwallet.home.model;

import android.util.Log;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.config.UrlConfig;
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
 * Created by zanzq on 2017/4/26.
 */

public class IyDataImpl implements IylData {
    private Gson gson = new Gson();
    @Override
    public void getJYinfo(JyBean jyBean, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        RequestBody body;
            body = new FormBody.Builder()
                    //添加键值对
                    .add("mchtNo",jyBean.getMchtNo())
                    .add("amount",jyBean.getMoney())
                    .add("phoneNumber",jyBean.getPhoneNumber())
                    .add("outTradeNo",jyBean.getOutTradeNo()+"")
                    .add("cardNo",jyBean.getCardNo()+"")
                    .add("myIDCardNo",jyBean.getMyIDCardNo()+"")
                    .add("peopleName",jyBean.getPeopleName()+"")
                    .build();

        Log.d("zanZQ", "getMainWx: "+jyBean);
        Request request=new Request.Builder().url(UrlConfig.TYyljy).post(body).build();
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
