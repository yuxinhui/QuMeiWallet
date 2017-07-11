package com.gdyd.qmwallet.talk;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.talk.model.ContactBean;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.NetUtil;
import com.gdyd.qmwallet.utils.SharePreUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by fx-168 on 17/7/6.
 */

public class ContactActivity extends BaseActivity{


    private String merchantNO;

    ListView listview;

    ContactAdapter adapter;

    private ArrayList<ContactBean> list =  new ArrayList<>();

    private ImageView mBack;

    IndexableLayout indexableLayout;

    ContactAdapter2 mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);


        listview = (ListView) findViewById(R.id.listview);
        indexableLayout = (IndexableLayout) findViewById(R.id.indexableLayout);

        merchantNO =  getIntent().getStringExtra("merchantNO");
        getContact(merchantNO);

        //adapter = new ContactAdapter(list,this);
       // listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent =  new Intent(ContactActivity.this,ContactDetailActivity.class);
                intent.putExtra("contactBean",list.get(i));
                startActivity(intent);

            }
        });

        mBack = (ImageView) findViewById(R.id.image_return);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        indexableLayout.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ContactAdapter2(this);
        indexableLayout.setAdapter(mAdapter);

        // 快速排序。  排序规则设置为：只按首字母  （默认全拼音排序）  效率很高，是默认的10倍左右。  按需开启～
        indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);
       // search_fragment.bindDatas(mDatas);
        indexableLayout.setOverlayStyle_MaterialDesign(Color.RED);  //显示右下角



        // set Listener
        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<ContactBean>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, ContactBean entity) {
                Intent intent =  new Intent(ContactActivity.this,ContactDetailActivity.class);
                intent.putExtra("contactBean",entity);
                startActivity(intent);


            }
        });

    }



    public void getContact(String merchantNO) {
        OkHttpClient client = NetUtil.getClient();

        long transTyp = EncryptionHelper.getDate();
        String transTy="1084"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);

        JSONObject jsonObject  = new JSONObject();
        String value = "";

        try {
            String key = EncryptionHelper.key;
            Log.d("zanZQ", "getBannerData:"+key);
            jsonObject.put("TransType","1084");
            jsonObject.put("TransKey",transKe);
            jsonObject.put("TrasnTimeSpan",transTyp);
            jsonObject.put("ExecPro","1");
            jsonObject.put("merchantNO",merchantNO);
            jsonObject.put("pageSize","50");
            jsonObject.put("pageIndex","1");
            value = EncryptionHelper.aesEncrypt(jsonObject.toString(), key);

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

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String string = response.body().string();
                    Log.d("zanZQ", "onResponse: "+string);

                    try {
                        string = EncryptionHelper.aesDecrypt(string, EncryptionHelper.key);
                        Log.d("zanZQ", "onResponse:bande "+string);

                        JSONObject jsonObject1 = new JSONObject(string);
                        String  data =  jsonObject1.getString("Data");
                        Log.d("data", "onResponse:data "+string);

                        if("1".equals(jsonObject1.optString("nResul"))){
                            JSONObject Data = new JSONObject(data);
                            SharePreUtil.saveStringData(ContactActivity.this,"merchantList",Data.optString("merchantList"));
                            final List<ContactBean> datas = new Gson().fromJson(Data.optString("merchantList"), new TypeToken<List<ContactBean>>() {
                            }.getType());

                           final List<ContactBean> list2 = new ArrayList<ContactBean>();
                            for (ContactBean bean :datas){
                                if(TextUtils.isEmpty(bean.getName())){
                                    bean.setName("");
                                    list2.add(bean);
                                }else{
                                    list2.add(bean);
                                }
                            }


                            //list.addAll(datas);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                   // adapter.notifyDataSetChanged();
                                    mAdapter.setDatas(list2);

                                }
                            });
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


}
