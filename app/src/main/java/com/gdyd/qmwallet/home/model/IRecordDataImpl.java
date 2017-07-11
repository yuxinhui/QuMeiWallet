package com.gdyd.qmwallet.home.model;

import android.util.Log;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.Is;
import com.gdyd.qmwallet.utils.NetUtil;
import com.google.gson.Gson;

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

public class IRecordDataImpl implements IRecordData {
    private Gson gson = new Gson();
    @Override
    public void getRecordData(PlaceBean placeBean, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
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
                    try {
                        EarningRecordBean earningRecordBean1 = gson.fromJson(string, EarningRecordBean.class);
                        int nResul = earningRecordBean1.getnResul();
                        if (nResul==1){
                            EarningRecordBean.DataBean dataBean = gson.fromJson(earningRecordBean1.getData(), EarningRecordBean.DataBean.class);
                            earningRecordBean1.setDataBean(dataBean);
                            earningRecordBean1.setData(null);
                            JSONArray array = new JSONArray(dataBean.getRecommendPostCash());
                            if (Is.isNoEmptyAll(array)){
                                ArrayList<EarningRecordBean.DataBean.RecommendPostCashBean> list=new ArrayList<>();
                                for (int i = 0; i < array.length(); i++) {
                                    EarningRecordBean.DataBean.RecommendPostCashBean recommendPostCashBean = gson.fromJson(array.getString(i), EarningRecordBean.DataBean.RecommendPostCashBean.class);
                                    list.add(recommendPostCashBean);
                                }
                                earningRecordBean1.getDataBean().setList(list);
                                earningRecordBean1.getDataBean().setRecommendPostCash(null);
                                onDataLoadListener.onLoadSuccess(earningRecordBean1);
                            }else{
                                onDataLoadListener.onLoadSuccess(earningRecordBean1);
                            }

                        }else{
                            onDataLoadListener.onLoadSuccess(null);
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
}
