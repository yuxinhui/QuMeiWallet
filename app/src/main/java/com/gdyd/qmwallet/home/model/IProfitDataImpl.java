package com.gdyd.qmwallet.home.model;

import android.util.Log;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.trans.Trans;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.NetUtil;
import com.gdyd.qmwallet.home.model.YuDianRecordBean;
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
 * Created by zanzq on 2017/3/7.
 */

public class IProfitDataImpl implements IProfitData {
    private Gson gson = new Gson();
    @Override
    public void getIProfitData(PlaceBean placeBean, final OnDataLoadListener onDataLoadListener) {
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
                   ProfitBean profitBean = gson.fromJson(string, ProfitBean.class);
                  if (profitBean!=null&&profitBean.getNResul()==1){
                      ProfitBean.DataBean dataBean = gson.fromJson(profitBean.getData(), ProfitBean.DataBean.class);
                        profitBean.setData(null);
                      profitBean.setDataBean(dataBean);
                      try {
                          JSONArray array = new JSONArray(dataBean.getRecommendProfitView());
                          if (array.length()>0){
                             ArrayList< ProfitBean.DataBean.RecommendProfitViewBean> list=new ArrayList<ProfitBean.DataBean.RecommendProfitViewBean>();
                              for (int i = 0; i < array.length(); i++) {
                                  ProfitBean.DataBean.RecommendProfitViewBean recommendProfitViewBean = gson.fromJson(array.getString(i), ProfitBean.DataBean.RecommendProfitViewBean.class);
                                list.add(recommendProfitViewBean);
                              }
                              dataBean.setList(list);
                              dataBean.setRecommendProfitView(null);
                          }
                          onDataLoadListener.onLoadSuccess(profitBean);
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
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

    @Override
   public void getYuDianRecord(PlaceBean placeBean,final OnDataLoadListener onDataLoadListener){
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
                    YuDianRecordBean profitBean = gson.fromJson(string, YuDianRecordBean.class);
                    if (profitBean!=null&&profitBean.getnResul()==1){
                        YuDianRecordBean.DataBean dataBean = gson.fromJson(profitBean.getData(), YuDianRecordBean.DataBean.class);
                        profitBean.setData(null);
                        profitBean.setDataBean(dataBean);
                        try {
                            JSONArray array = new JSONArray(dataBean.getRainCredit());
                            if (array.length()>0){
                                ArrayList< YuDianRecordBean.DataBean.RainCreditItem> list=new ArrayList<YuDianRecordBean.DataBean.RainCreditItem>();
                                for (int i = 0; i < array.length(); i++) {
                                    YuDianRecordBean.DataBean.RainCreditItem recommendProfitViewBean = gson.fromJson(array.getString(i), YuDianRecordBean.DataBean.RainCreditItem.class);
                                    list.add(recommendProfitViewBean);
                                }
                                dataBean.setList(list);
                                dataBean.setRainCredit(null);
                            }
                            onDataLoadListener.onLoadSuccess(profitBean);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
