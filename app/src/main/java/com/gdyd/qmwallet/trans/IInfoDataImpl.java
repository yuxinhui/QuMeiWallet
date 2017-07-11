package com.gdyd.qmwallet.trans;

import android.util.Log;

import com.gdyd.qmwallet.App;
import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.home.model.InfoBean;
import com.gdyd.qmwallet.mine.model.PhotoBean;
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
 * Created by zanzq on 2017/3/15.
 */

public class IInfoDataImpl implements IInfoData {
    @Override
    public void getInfoData(PlaceBean placeBean, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
         final Gson gson = new Gson();
        String value = gson.toJson(placeBean);
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
        Log.d("zanZQ", "getLoadProvinceData: "+value);
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
                    //    Log.d("zanZQ", "onResponse:bande "+string);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.d("zanZQ", "onResponse: "+string.replaceAll("\\s","").replaceAll("\\n","").replaceAll("\\t","").trim());
                    InfoBean infoBean = gson.fromJson(string.replaceAll("\n","").replaceAll("\t","").trim(), InfoBean.class);
                    if (infoBean!=null&&infoBean.getnResul()==1){
                        InfoBean.DataBean dataBean = gson.fromJson(infoBean.getData(), InfoBean.DataBean.class);
                        infoBean.setDataBean(dataBean);
                        infoBean.setData(null);
                    }
                    onDataLoadListener.onLoadSuccess(infoBean);
                }else{
                    Log.d("zanZQ", "onResponse: ++++++++++++");
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }

    @Override
    public void getInfoFuture(PhotoBean placeBean, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        final Gson gson = new Gson();
        String value = gson.toJson(placeBean);
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
        Log.d("zanZQ", "getLoadProvinceData: "+value);
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
                 //   Log.d("zanZQ", "onResponse: "+string.replaceAll("\n","").replaceAll("\t","").trim());
                    onDataLoadListener.onLoadSuccess(string);
                }else{
                    Log.d("zanZQ", "onResponse: ++++++++++++");
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }

}
