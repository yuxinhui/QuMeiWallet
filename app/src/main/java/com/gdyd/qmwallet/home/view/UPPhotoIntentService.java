package com.gdyd.qmwallet.home.view;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.friends.selector.Bimp;
import com.gdyd.qmwallet.friends.utils.ToastUtils;
import com.gdyd.qmwallet.mine.model.PhotoBean;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.utils.NetUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

public class UPPhotoIntentService extends IntentService {
    private Gson gson = new Gson();

    public UPPhotoIntentService() {
        super("");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("zanZQ", "onCreate: ");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("zanZQ", "onHandleIntent: 111");
        int type = intent.getIntExtra("type", 0);

        if (type==0){
            PhotoBean photoBean = (PhotoBean) intent.getSerializableExtra("photobean");
           UpPhotoData(photoBean);
        }else{
            PlaceBean photo = (PlaceBean) intent.getSerializableExtra("photo");
        //    photo.setImageBuf(Bimp.list);
          UpPhotoDataPath(photo);
        }

    }
    public void UpPhotoData(PhotoBean photoBean) {
        OkHttpClient client = NetUtil.getClient();
        String value = gson.toJson(photoBean);
           Log.d("zanZQ", "UpPhotoData: "+value);
        RequestBody body = new FormBody.Builder()
                //添加键值对
                .add("sPostParam", value)
                .build();
        Request request=new Request.Builder().url(UrlConfig.URI).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String string = response.body().string();
                           Log.d("zanZQ", "onResponse:上传 "+string);
                    try {
                        JSONObject object = new JSONObject(string);
                        int nResul = object.getInt("nResul");
                        if (nResul==1){
                            String data = object.getString("Data");
                            Bimp.list.add(data);
                        }else{
                           ToastUtils.showToast(UPPhotoIntentService.this,"上传图片失败");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
            }
        });
    }
    public void UpPhotoDataPath(PlaceBean photoBean) {
        OkHttpClient client = NetUtil.getClient();
        String value = gson.toJson(photoBean);
            Log.d("zanZQ", "UpPhotoDatapath: "+value);
        RequestBody body = new FormBody.Builder()
                //添加键值对
                .add("sPostParam", value)
                .build();
        Request request=new Request.Builder().url(UrlConfig.URI).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String string = response.body().string();
                          Log.d("zanZQ", "onResponse:上传图片地址 "+string);
                    try {
                        JSONObject object = new JSONObject(string);
                        int nResul = object.getInt("nResul");
                        if (nResul==1){
                            String data = object.getString("sMessage");
                       //     ToastUtils.showToast(UPPhotoIntentService.this,data);
                            Bimp.list.clear();
                        }else{

                       }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
            }
        });
    }
}
