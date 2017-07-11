package com.gdyd.qmwallet.home.model;

import android.util.Log;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.home.model.NewYuDianBean;
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
 * Created by hebei on 2017/7/4.
 */

public class IYuDianDataImpl implements IYuDianData{
    private Gson gson = new Gson();
    @Override
    public void getYuDianData(PlaceBean placeBean,final OnDataLoadListener onDataLoadListener){

        OkHttpClient client = NetUtil.getClient();
        String value = gson.toJson(placeBean);
        Log.d("zanZQ", "getIProfitData: "+value);
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
                Log.d("zanZQ", "onFailure: "+e.getMessage());
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
                    NewYuDianBean profitBean = gson.fromJson(string, NewYuDianBean.class);
                    if (profitBean!=null&&profitBean.getnResul()==1){
                        NewYuDianBean.NewYuDianItem dataBean = gson.fromJson(profitBean.getData(), NewYuDianBean.NewYuDianItem.class);
                        profitBean.setData(null);
                        profitBean.setDataBean(dataBean);

                        onDataLoadListener.onLoadSuccess(profitBean);

                    }else{
                        onDataLoadListener.onLoadSuccess(null);
                    }
                    //     new JSONArray(dataBean)
                }else{
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }

}
