package com.gdyd.qmwallet.friends;

import android.util.Log;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.mine.model.PhotoBean;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.mine.model.RateBean;
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
 * Created by zanzq on 2017/5/4.
 */

public class ILodeFriendsDataImpl implements ILodeFriendsData {
    private Gson gson=new Gson();
    @Override
    public void getData(PlaceBean placeBean, final OnDataLoadListener onDataLoadListener) {
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
                    if (string!=null){
                        try {
                            string = EncryptionHelper.aesDecrypt(string, EncryptionHelper.key);
                            Log.d("zanZQ", "onResponse:bande "+string);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        FriendDataBean friendDataBean = gson.fromJson(string, FriendDataBean.class);
                        if (friendDataBean!=null&&friendDataBean.getNResul()==1){
                            FriendDataBean.DataBean dataBean = gson.fromJson(friendDataBean.getData(), FriendDataBean.DataBean.class);
                            friendDataBean.setDataBean(dataBean);
                            friendDataBean.setData(null);
                            try {
                                ArrayList<FriendDataBean.DataBean.GraphicSharing2> list=new ArrayList<FriendDataBean.DataBean.GraphicSharing2>();
                                JSONArray array = new JSONArray(dataBean.getGraphicSharing());
                                for (int i = 0; i < array.length(); i++) {
                                    FriendDataBean.DataBean.GraphicSharing2 graphicSharing2 = gson.fromJson(array.getString(i), FriendDataBean.DataBean.GraphicSharing2.class);
                                      list.add(graphicSharing2);
                                }
                                dataBean.setGraphicSharing2(list);
                                dataBean.setGraphicSharing(null);
                                onDataLoadListener.onLoadSuccess(friendDataBean);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }else{
                        onDataLoadListener.onLoadSuccess(null);
                    }

                }else{
                    onDataLoadListener.onLoadSuccess(null);
                }
            }
        });
    }


}
