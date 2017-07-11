package com.gdyd.qmwallet.home.model;

import android.util.Log;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.config.UrlConfig;
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
 * Created by zanzq on 2017/4/6.
 */

public class IVideoDataImpl implements IVideoData {
    private Gson gson = new Gson();
    @Override
    public void getVideoData(PlaceBean placeBean, final OnDataLoadListener onDataLoadListener) {
        OkHttpClient client = NetUtil.getClient();
        String value = gson.toJson(placeBean);
        Log.d("zanZQ", "getVideoData: "+value);
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
                    VideoBean videoBean = gson.fromJson(string, VideoBean.class);

                    if (videoBean!=null&&videoBean.getnResul()==1){
                        VideoBean.DataBean dataBean = gson.fromJson(videoBean.getData(), VideoBean.DataBean.class);
                        ArrayList<VideoBean.DataBean.Video> list=new ArrayList<VideoBean.DataBean.Video>();
                        videoBean.setDataBean(dataBean);
                        videoBean.setData(null);
                        try {
                            JSONArray jsonArray = new JSONArray(videoBean.getDataBean().getVideo());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                VideoBean.DataBean.Video dataList = gson.fromJson(jsonArray.getString(i),
                                        VideoBean.DataBean.Video.class);
                                list.add(dataList);
                            }
                            videoBean.getDataBean().setList(list);
                            videoBean.getDataBean().setVideo(null);
                            onDataLoadListener.onLoadSuccess(videoBean);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else{
                        onDataLoadListener.onLoadSuccess(videoBean);
                    }

                }else{
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }
}
