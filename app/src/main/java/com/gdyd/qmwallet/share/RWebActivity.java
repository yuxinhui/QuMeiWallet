package com.gdyd.qmwallet.share;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.Other.view.WebViewActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.home.view.PayCodeActivity;
import com.gdyd.qmwallet.utils.DtoB;
import com.gdyd.qmwallet.utils.Is;

import java.io.File;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;

public class RWebActivity extends BaseActivity implements View.OnClickListener {

    private WebView web;
    private PercentRelativeLayout left_return;
    private TextView left_title;
    private String url;
    private TextView left_save;
    private int type=0;
    private TextView title;
    private String name;
    private String money;
   private String FXzhuceurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rweb);
        url = getIntent().getStringExtra("url");
        type = getIntent().getIntExtra("type", APPConfig.save_send);
        web = (WebView) findViewById(R.id.webView);
        left_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        left_return.setOnClickListener(this);
        title = ((TextView) findViewById(R.id.title));
        left_title = ((TextView) findViewById(R.id.left_title));
        left_save = ((TextView) findViewById(R.id.left_save));
        left_save.setOnClickListener(this);
        left_title.setOnClickListener(this);
        if (type==APPConfig.send_shoukuang){
            left_save.setVisibility(View.GONE);
            left_title.setVisibility(View.GONE);
            title.setText("俏美-快捷支付");
            name = getIntent().getStringExtra("name");
            money = getIntent().getStringExtra("money");
        }else if (type==APPConfig.no_save_send){
            left_save.setVisibility(View.GONE);
            left_title.setVisibility(View.GONE);
            title.setText(getIntent().getStringExtra(APPConfig.TITLE));
        }else if (type==APPConfig.send_yingli){
            left_save.setVisibility(View.GONE);
            title.setText(getIntent().getStringExtra(APPConfig.TITLE));
        }else  if(type==APPConfig.save_send){
             FXzhuceurl = getIntent().getStringExtra("no");
        }
        web.loadUrl(url);
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setBuiltInZoomControls(true);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setSavePassword(true);
        s.setSaveFormData(true);
        s.setCacheMode(WebSettings.LOAD_NO_CACHE);
        s.setJavaScriptEnabled(true);     // enable navigator.geolocation
        s.setGeolocationEnabled(true);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("zanZQ", "shouldOverrideUrlLoading: "+url);
                if (url.contains("http://wx.gdydit.cn/onlinepay/paySuccess.jsp")){
                    finish();
                }else if (url.contains("http://wx.gdydit.cn:8088/register/inreview.html")){
                    finish();
                    //   startActivity(new Intent(WebViewActivity.this, Login.class));
                }else{
                    web.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("zanZQ", "onPageStarted: ");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("zanZQ", "onPageFinished: ");
            }
        });
    }

    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.left_title:
//                             AlertDialog.Builder builder = new AlertDialog.Builder(RWebActivity.this, AlertDialog.THEME_HOLO_LIGHT);
//                builder.setTitle("分享提示");
//               // builder.setIcon(R.drawable.logo);
//
//                builder.setMessage("\t\t分享裂变收益可观，人人创业，月入过万不是梦!");
//                builder.setPositiveButton("分享", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        initTG();
//                        //      context.startService(new Intent(context, AppService.class).putExtra("UpdateUrl",UpdateUrl));
//                    }
//                });
//                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
             //   builder.show();
             initTG();
             break;
         case R.id.left_save:
             web.setDrawingCacheEnabled(true);
//        my_code_layout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
             //   my_code_layout.layout(my_code_layout.getLeft(), my_code_layout.getTop(), my_code_layout.getMeasuredWidth(), my_code_layout.getMeasuredHeight());
             Bitmap bitmap= Bitmap.createBitmap(web.getDrawingCache());
             web.setDrawingCacheEnabled(false);
             String save_result = MediaStore.Images.Media.insertImage(getContentResolver(),bitmap ,"俏美钱包","分享注册");
             Log.d("zanZQ", "onContextItemSelected: "+save_result);
             if(Is.isNoEmpty(save_result)){
                 Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
             }else {
                 Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
             }
             break;
         case R.id.left_return:
             finish();
             break;
     }
    }
    private void initTG() {
        if (type==APPConfig.send_shoukuang){
            //    Toast.makeText(UserCode.this, "send", Toast.LENGTH_SHORT).show();
            //   file= DtoB.saveBitmap(UserCode.this,bitmap,Login.PHONE);
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.setType("image/*");
//                      //  File f = new File(externalCacheDir,str);
//                Uri u = Uri.fromFile(file);
//                sendIntent.putExtra(Intent.EXTRA_STREAM, u);
//                startActivity(sendIntent);
            OnekeyShare oks2 = new OnekeyShare();
            //关闭sso授权
            oks2.disableSSOWhenAuthorize();

            oks2.addHiddenPlatform(TencentWeibo.NAME);
            //  oks.addHiddenPlatform(WechatFavorite.NAME);
            oks2.addHiddenPlatform(QQ.NAME);
            oks2.addHiddenPlatform(SinaWeibo.NAME);
            oks2.addHiddenPlatform(QZone.NAME);
            // oks.addHiddenPlatform(AlipayMoments.NAME);
            // oks.addHiddenPlatform(Alipay.NAME);
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
            // oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
            oks2.setTitle("俏美钱包-收款");
            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks2.setTitleUrl(getResources().getString(R.string.service_tg));
            // text是分享文本，所有平台都需要这个字段
            //  http://zgy.tunnel.qydev.com/onlinepay/wxZfbPay.jsp?name=%@&money=%@&payurl=%@&type=Z0
            oks2.setText(name+"正在使用俏美钱包向你收取"+money+"元，请点击支付！");
            Drawable db = getResources().getDrawable(R.drawable.logo);
            Bitmap bitmap2 = ((BitmapDrawable) db).getBitmap();
            File file2= DtoB.saveBitmap(RWebActivity.this,bitmap2,R.drawable.logo+"");
            //oks.setImageUrl(file.getAbsolutePath().toString());
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            oks2.setImagePath(file2.getAbsolutePath().toString());//确保SDcard下面存在此张图片
            //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博


            oks2.setUrl(url);
            Log.d("zanZQ", "onContextItemSelected: "+url);
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            // oks.setComment(PlayUtil.currentMusic.getSongname());
            // site是分享此内容的网站名称，仅在QQ空间使用
            //  oks.setSite(PlayUtil.currentMusic.getSongname());
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            //  oks.setSiteUrl(PlayUtil.currentMusic.getUrl());
            oks2.show(RWebActivity.this);
        }else if (type==APPConfig.save_send){
            //    Toast.makeText(UserCode.this, "send", Toast.LENGTH_SHORT).show();
            //   file= DtoB.saveBitmap(UserCode.this,bitmap,Login.PHONE);
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.setType("image/*");
//                      //  File f = new File(externalCacheDir,str);
//                Uri u = Uri.fromFile(file);
//                sendIntent.putExtra(Intent.EXTRA_STREAM, u);
//                startActivity(sendIntent);
            OnekeyShare oks2 = new OnekeyShare();
            //关闭sso授权
            oks2.disableSSOWhenAuthorize();
            oks2.addHiddenPlatform(TencentWeibo.NAME);
            //  oks.addHiddenPlatform(WechatFavorite.NAME);
            oks2.addHiddenPlatform(QQ.NAME);
            oks2.addHiddenPlatform(SinaWeibo.NAME);
            oks2.addHiddenPlatform(QZone.NAME);
            // oks.addHiddenPlatform(AlipayMoments.NAME);
            // oks.addHiddenPlatform(Alipay.NAME);
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
            // oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
            oks2.setTitle("俏美钱包-注册");
            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks2.setTitleUrl(getResources().getString(R.string.service_tg));
            // text是分享文本，所有平台都需要这个字段
            //  http://zgy.tunnel.qydev.com/onlinepay/wxZfbPay.jsp?name=%@&money=%@&payurl=%@&type=Z0
            oks2.setText("刷卡即时到账，超低费率，分润秒结，全民创业，月入过万不是梦，赶紧来注册吧！");
            Drawable db = getResources().getDrawable(R.drawable.logo);
            Bitmap bitmap2 = DtoB.byD(db);
            File file2= DtoB.saveBitmap(RWebActivity.this,bitmap2,R.drawable.logo+"");
            //oks.setImageUrl(file.getAbsolutePath().toString());
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            oks2.setImagePath(file2.getAbsolutePath().toString());//确保SDcard下面存在此张图片
            //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
            //  String url= "http://120.76.28.241:8080/qiaomeiqianbao/share/qm_share.html?AgentID=&RecommendNo=%s&Type=1";
            // String url="http://120.76.28.241:8080/qiaomeiqianbao/qmShar_personal.jsp?AgentID=1&RecommendNo=%s&Type=1";
            String url2= "http://qm.qiaomeishidai.com/qiaomeiqianbao/qmShar_personal.jsp?AgentID="+APPConfig.AgentID+"&RecommendNo=%s&Type=1";
           // http://qm.qiaomeishidai.com:8080/qiaomeiqianbao/qmShar_personal.jsp?AgentID=1&RecommendNo=QMC0000000004&Type=1
            oks2.setUrl(String.format(url2, FXzhuceurl));
            Log.d("zanZQ", "onContextItemSelected: "+url2);
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            // oks.setComment(PlayUtil.currentMusic.getSongname());
            // site是分享此内容的网站名称，仅在QQ空间使用
            //  oks.setSite(PlayUtil.currentMusic.getSongname());
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            //  oks.setSiteUrl(PlayUtil.currentMusic.getUrl());
            oks2.show(RWebActivity.this);
        }else  if (type==APPConfig.send_yingli){
            //    Toast.makeText(UserCode.this, "send", Toast.LENGTH_SHORT).show();
            //   file= DtoB.saveBitmap(UserCode.this,bitmap,Login.PHONE);
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.setType("image/*");
//                      //  File f = new File(externalCacheDir,str);
//                Uri u = Uri.fromFile(file);
//                sendIntent.putExtra(Intent.EXTRA_STREAM, u);
//                startActivity(sendIntent);
            OnekeyShare oks2 = new OnekeyShare();
            //关闭sso授权
            oks2.disableSSOWhenAuthorize();
            oks2.addHiddenPlatform(TencentWeibo.NAME);
            //  oks.addHiddenPlatform(WechatFavorite.NAME);
            oks2.addHiddenPlatform(QQ.NAME);
            oks2.addHiddenPlatform(SinaWeibo.NAME);
            oks2.addHiddenPlatform(QZone.NAME);
            // oks.addHiddenPlatform(AlipayMoments.NAME);
            // oks.addHiddenPlatform(Alipay.NAME);
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
            // oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
            oks2.setTitle("俏美钱包");
            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks2.setTitleUrl(getResources().getString(R.string.service_tg));
            // text是分享文本，所有平台都需要这个字段
            //  http://zgy.tunnel.qydev.com/onlinepay/wxZfbPay.jsp?name=%@&money=%@&payurl=%@&type=Z0
           if (title.getText().equals(getResources().getString(R.string.ylms))){
               oks2.setText("入门级用户赚钱教程分析");
               oks2.setTitle("俏美钱包-"+getResources().getString(R.string.ylms));
           }else if (title.getText().equals(getResources().getString(R.string.tip_my_help))){
               oks2.setTitle("俏美钱包-"+getResources().getString(R.string.tip_my_help));
               oks2.setText("轻松帮您解决日常问题");
           }else if (title.getText().equals(getResources().getString(R.string.tip_my_about))){
               oks2.setTitle("俏美钱包-"+getResources().getString(R.string.tip_my_about));
               oks2.setText("俏美钱包公司简介");
           }
            Drawable db = getResources().getDrawable(R.drawable.logo);
            Bitmap bitmap2 = DtoB.byD(db);
            File file2= DtoB.saveBitmap(RWebActivity.this,bitmap2,R.drawable.logo+"");
            //oks.setImageUrl(file.getAbsolutePath().toString());
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            oks2.setImagePath(file2.getAbsolutePath().toString());//确保SDcard下面存在此张图片
            //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
            //  String url= "http://120.76.28.241:8080/qiaomeiqianbao/share/qm_share.html?AgentID=&RecommendNo=%s&Type=1";
            // String url="http://120.76.28.241:8080/qiaomeiqianbao/qmShar_personal.jsp?AgentID=1&RecommendNo=%s&Type=1";
            oks2.setUrl(url);
            Log.d("zanZQ", "onContextItemSelected: "+url);
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            // oks.setComment(PlayUtil.currentMusic.getSongname());
            // site是分享此内容的网站名称，仅在QQ空间使用
            //  oks.setSite(PlayUtil.currentMusic.getSongname());
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            //  oks.setSiteUrl(PlayUtil.currentMusic.getUrl());
            oks2.show(RWebActivity.this);
        }else if (type==APPConfig.send_about){

        }
    }
    @Override
    public void onBackPressed() {

        if (web.canGoBack()){

                web.goBack();

        }else{
            super.onBackPressed();
        }
    }
}

