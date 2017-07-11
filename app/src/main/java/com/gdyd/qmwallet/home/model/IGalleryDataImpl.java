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
 * Created by zanzq on 2017/4/17.
 */

public class IGalleryDataImpl implements IGalleryData {
    private Gson gson = new Gson();
    @Override
    public void getGalleryData(PlaceBean placeBean,final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        String value = gson.toJson(placeBean);
        String encrypt="";
        try {
            String key = EncryptionHelper.key;
            Log.d("zanZQ", "getBannerData:"+key);
            value = EncryptionHelper.aesEncrypt(value, key);
//            String qqq = EncryptionHelper.aesDecrypt(encrypt, EncryptionHelper.key);
//            Log.d("zanZQ", "getBannerDataqqq: "+qqq);
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
                    Log.d("zanZQ", "onResponse: "+string);
                    try {
                        string = EncryptionHelper.aesDecrypt(string, EncryptionHelper.key);
                        Log.d("zanZQ", "onResponse:bande "+string);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    GalleryBean galleryBean = gson.fromJson(string, GalleryBean.class);
                  if (galleryBean!=null&&galleryBean.getnResul()==1)
                    try {
                        ArrayList<GalleryBean.DataBean> list=new ArrayList<GalleryBean.DataBean>();
                        JSONArray array = new JSONArray(galleryBean.getData());
                        for (int i = 0; i < array.length(); i++) {
                            list.add(gson.fromJson(array.getString(i), GalleryBean.DataBean.class));
                        }
                        galleryBean.setGallerys(list);
                        galleryBean.setData(null);
                        onDataLoadListener.onLoadSuccess(galleryBean);
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
}
