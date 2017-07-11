package com.gdyd.qmwallet;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;

import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.event.LoginEvent;
import com.gdyd.qmwallet.home.MainFragment;
import com.gdyd.qmwallet.home.model.InfoBean;
import com.gdyd.qmwallet.mine.MineFragment;
import com.gdyd.qmwallet.mine.model.PhotoBean;
import com.gdyd.qmwallet.share.ShareFragment;
import com.gdyd.qmwallet.talk.TalkFragment;
import com.gdyd.qmwallet.talk.model.ContactBean;
import com.gdyd.qmwallet.trans.IInfoView;
import com.gdyd.qmwallet.trans.InfoFragment;
import com.gdyd.qmwallet.trans.InfoPresenter;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.Is;
import com.gdyd.qmwallet.utils.NetUtil;
import com.gdyd.qmwallet.utils.SharePreUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends BaseActivity  implements IInfoView {

    private Fragment mainfragment;
    private Fragment sharefragment;
    private Fragment infofragment;
    private Fragment minefragment;
    private Fragment layout_footer;
    private Fragment talkfragment;
    private InfoPresenter infoPresenter=new InfoPresenter(this);
    private mFooter footer;
    private int id;
    private int size=888;

    private LoginInfoBean bean;

    private String token;
    private String merchantNO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        footer = (mFooter) fragmentManager.findFragmentById(R.id.layout_footer);
        int type = getIntent().getIntExtra("type", 0);
        id = getIntent().getIntExtra("ID", 0);
        initFragment(type);

        EventBus.getDefault().register(this);

        bean = ((LoginInfoBean)getIntent().getSerializableExtra(APPConfig.LOGIN_INFO));
        if(bean !=null){
            token = bean.getUserData().getMerchant().getToken();
            merchantNO = bean.getUserData().getMerchant().getMerchantNo();
            if(!TextUtils.isEmpty(token)){
                reconnect(token);
            }else{
                getToken(merchantNO);
            }
        }


        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

            @Override
            public UserInfo getUserInfo(String userId) {
                Log.e("getUserInfo","userId="+userId);
                String  merchantList =   SharePreUtil.getStringData(MainActivity.this,"merchantList","");
                if(!TextUtils.isEmpty(merchantList)){
                    final List<ContactBean> datas = new Gson().fromJson(merchantList, new TypeToken<List<ContactBean>>() {
                    }.getType());

                    for (ContactBean bean:datas) {
                        if(userId.equals(bean.getMerchantNo())){
                            String  name  = bean.getName();
                            if(TextUtils.isEmpty(name)){
                                name = bean.getPhoneNumber();
                            }
                            Uri uri;
                            String headImage = bean.getHeadImage();
                            if (headImage==null||headImage.trim().equals("")){
                                //uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResources().getResourcePackageName(R.drawable.icon_tourist));
                                uri = Uri.parse("android.resource://"+ getPackageName()+"/"+R.drawable.icon_tourist);

                            }else{
                                headImage =  UrlConfig.PHOTO_URI+headImage;
                                uri = Uri.parse(headImage);
                            }
                            UserInfo userInfo =  new UserInfo(userId,name,uri);
                            return userInfo;//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。

                        }

                    }

                }

                /**
                 * 设置当前用户信息，
                 *
                 * @param  //
                 */
                if(bean!=null){
                    if(userId.equals(bean.getUserData().getMerchant().getMerchantNo())){
                        String  name  = bean.getUserData().getMerchant().getName();
                        if(TextUtils.isEmpty(name)){
                            name = bean.getUserData().getMerchant().getPhoneNumber();
                        }
                        Uri uri;
                        String headImage = bean.getUserData().getMerchant().getHeadImage();
                        if (headImage==null||headImage.trim().equals("")){
                            //uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResources().getResourcePackageName(R.drawable.icon_tourist));
                            uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.icon_tourist);

                        }else{
                            headImage =  UrlConfig.PHOTO_URI+headImage;
                            uri = Uri.parse(headImage);
                        }
                        UserInfo userInfo =  new UserInfo(userId,name,uri);
                        return userInfo;//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
                    }
                }

                return null;//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
            }

        }, true);



    }
    private void initFragment(int type) {
        replaceFragment(type);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (id!=0){
            long transTyp = EncryptionHelper.getDate();
            String transTy="1070"+transTyp+"";
            String transKe = EncryptionHelper.md5(transTy);
            infoPresenter.getInfoSign(new PhotoBean("1070",transKe,transTyp,id));



        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int type = intent.getIntExtra("type", 0);
        initFragment(type);
        footer.changeStyle(type);
    }



    public void replaceFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (Is.isNoEmptyAll(mainfragment)) {
            ft.hide(mainfragment);
        }
        if (Is.isNoEmptyAll(sharefragment)) {
            ft.hide(sharefragment);
        }
        if (Is.isNoEmptyAll(infofragment)) {
            ft.hide(infofragment);
        }
        if (Is.isNoEmptyAll(talkfragment)) {
            ft.hide(talkfragment);
        }
        if (Is.isNoEmptyAll(minefragment)) {
            ft.hide(minefragment);
        }
        switch (index) {
            case APPConfig.MAIN_HOME:
                if (Is.isNoEmptyAll(mainfragment)) {
                    ft.show(mainfragment);
                } else {
                    mainfragment = new MainFragment();
                    ft.add(R.id.ll, mainfragment);
                }

                break;
            case APPConfig.MAIN_SHARE:

                if (Is.isNoEmptyAll(sharefragment)) {
                    ft.show(sharefragment);
                } else {
                    sharefragment = new ShareFragment();
                    ft.add(R.id.ll, sharefragment);
                }

                break;
            case APPConfig.MAIN_TRANS:
                if (Is.isNoEmptyAll(infofragment)) {
                    ft.show(infofragment);
                } else {
                    infofragment = new InfoFragment();
                    // mReceivables.
                    ft.add(R.id.ll, infofragment);
                }


                break;
            case APPConfig.MAIN_MINE:
                if (Is.isNoEmptyAll(minefragment)) {
                    ft.show(minefragment);
                } else {
                    minefragment = new MineFragment();
                    // mReceivables.
                    ft.add(R.id.ll, minefragment);
                }


                break;
            case APPConfig.MAIN_TALK:
                if (Is.isNoEmptyAll(talkfragment)) {
                    ft.show(talkfragment);
                } else {
                    talkfragment = new TalkFragment();
                    // mReceivables.
                    ft.add(R.id.ll, talkfragment);
                }


                break;
        }
        ft.commit();
    }


    @Override
    public void UpDataInfoView(InfoBean infoBean) {

    }

    @Override
    public void SignInfoView(String info) {
        Log.d("zanZQ", "SignInfoView登录: "+info);
       // {"Data":"0","nResul":1,"sMessage":null}
        if (info==null||info.trim().equals("")){
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(info);
            if (jsonObject.getInt("nResul")==1){
                String data = jsonObject.getString("Data");
               // data="999";
                footer.setSumInfo(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    /**
     * 重连
     * @param token
     */
    private void reconnect(String token) {
        if (getApplicationInfo().packageName.equals(App.getCurProcessName(this))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {

                }

                @Override
                public void onSuccess(String s) {
                    Log.e("onSuccess","userId="+s);

//                    /**
//                     * 设置当前用户信息，
//                     *
//                     * @param  //
//                     */
//                    String  name  = bean.getUserData().getMerchant().getName();
//                    if(TextUtils.isEmpty(name)){
//                        name = bean.getUserData().getMerchant().getPhoneNumber();
//                    }
//
//                    Uri uri;
//                    String headImage = bean.getUserData().getMerchant().getHeadImage();
//                    if (headImage==null||headImage.trim().equals("")){
//                        uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResources().getResourcePackageName(R.drawable.icon_tourist));
//                    }else{
//                        headImage =  UrlConfig.PHOTO_URI+headImage;
//
//                        uri = Uri.parse(headImage);
//                    }
//
//                    UserInfo userInfo =  new UserInfo(s,name,uri);
//
//                    RongIM.getInstance().setCurrentUserInfo(userInfo);
//
//                /**
//                 * 设置消息体内是否携带用户信息。
//                 *
//                 * @param // 是否携带用户信息，true 携带，false 不携带。
//                    */
//                    RongIM.getInstance().setMessageAttachedUserInfo(true);


                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.e("onSuccess","errorCode="+errorCode);

                }
            });
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void getToken(String merchantNO) {
        OkHttpClient client = NetUtil.getClient();
        long transTyp = EncryptionHelper.getDate();
        String transTy="1083"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);
        JSONObject jsonObject  = new JSONObject();
        String value = "";
        try {
            String key = EncryptionHelper.key;
            Log.d("zanZQ", "getBannerData:"+key);
            jsonObject.put("TransType","1083");
            jsonObject.put("TransKey",transKe);
            jsonObject.put("TrasnTimeSpan",transTyp);
            jsonObject.put("MerchantNo",merchantNO);
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
                    try {
                        string = EncryptionHelper.aesDecrypt(string, EncryptionHelper.key);
                        Log.e("onSuccess","onResponse="+string);

                        JSONObject jsonObject1 = new JSONObject(string);

                        if("1".equals(jsonObject1.optString("nResul"))){
                            token = jsonObject1.getString("Data");
                            reconnect(token);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



    @Subscribe(threadMode = ThreadMode.BackgroundThread)
    public void getLoginEvent(LoginEvent event) {
        token  =event.getToken();
        merchantNO =  event.getMerchantNO();
        bean = ((LoginInfoBean)getIntent().getSerializableExtra(APPConfig.LOGIN_INFO));
        Log.e("TAG","token=="+token +"   merchantNO=="+ merchantNO +"  name==" +bean.getUserData().getMerchant().getName());
        if(TextUtils.isEmpty(token)){
            getToken(merchantNO);
        }else{
            reconnect(token);
        }
    }



}
