package com.gdyd.qmwallet.home.model;

import android.util.Log;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.config.UrlConfig;
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
 * Created by zanzq on 2017/3/14.、
 *
 */

public class IBannerDataImpl implements IBannerData {
    private Gson gson = new Gson();
    @Override
    public void getBannerData(PlaceBean placeBean, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        String value = gson.toJson(placeBean);
        Log.d("zanZQ", "getBannerData: "+value);
        String encrypt="";
        try {
            String key = EncryptionHelper.key;
            encrypt = EncryptionHelper.aesEncrypt(value, key);
//            String qqq = EncryptionHelper.aesDecrypt(encrypt, EncryptionHelper.key);
//            Log.d("zanZQ", "getBannerDataqqq: "+qqq);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String mm = EncryptionHelper.aes(value, EncryptionHelper.key, EncryptionHelper.ENCODE);
//        String mm2 = EncryptionHelper.aes(mm, "EncryptionHelper.key", EncryptionHelper.DECODE);
//        Log.d("zanZQ", "getBannerData:mm2 "+qq);
     //   Log.d("zanZQ", "getManageMentInfo:mm1 "+encrypt);
        RequestBody body = new FormBody.Builder()
                //添加键值对
                .add("sPostParam", encrypt)
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
                   // Log.d("zanZQ", "onResponse: "+string);
                    try {
                        string = EncryptionHelper.aesDecrypt(string, EncryptionHelper.key);
                        Log.d("zanZQ", "onResponse:bande "+string);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    BannerBean bannerBean = gson.fromJson(string, BannerBean.class);
                    try {
                        if (bannerBean!=null&&bannerBean.getnResul()==1){
                            JSONArray array = new JSONArray(bannerBean.getData());
                            if (array.length()>0){
                               ArrayList<BannerBean.DataBean> list=new ArrayList<BannerBean.DataBean>();
                                for (int i = 0; i < array.length(); i++) {
                                    list.add(gson.fromJson(array.getString(i), BannerBean.DataBean.class));
                                }
                                bannerBean.setBeanArrayList(list);
                               bannerBean.setData(null);
                                onDataLoadListener.onLoadSuccess(bannerBean);
                            }else{
                                onDataLoadListener.onLoadSuccess(bannerBean);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        onDataLoadListener.onLoadSuccess(null);
                    }

                }else{
                    Log.d("zanZQ", "onResponse: 不成功");
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }
}
