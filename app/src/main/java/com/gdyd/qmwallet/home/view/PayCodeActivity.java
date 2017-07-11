package com.gdyd.qmwallet.home.view;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.utils.DtoB;
import com.gdyd.qmwallet.utils.Is;
import com.gdyd.qmwallet.utils.T;
import com.gdyd.qmwallet.utils.ZXingUtils;

import java.io.File;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
// 二维码显示页面
public class PayCodeActivity extends BaseActivity implements View.OnClickListener {
    private ImageView scan_image;
    private TextView scan_mcht_name;
    private TextView scan_money;
    private TextView scan_tip_text;
    private String zffs;
    private PercentRelativeLayout my_code_layout;
    private String obj="";
    private ProgressBar bar;
    private String mchtName;
    private String money;
    private String title;
    private PercentRelativeLayout left_return;
    private TextView titlename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_code);
        init(); //初始化视图
        initData();//初始化数据
       // checkMain();
    }

    private void initData() {
        title = getIntent().getStringExtra(APPConfig.TITLE);
         money = getIntent().getStringExtra("money");
         mchtName = getIntent().getStringExtra("name");
         obj = getIntent().getStringExtra("url");
        titlename.setText(title);
        scan_money.setText(getResources().getString(R.string.CNY)+ money);
        scan_mcht_name.setText(mchtName);
        if (title.equals(getResources().getString(R.string.home_wx))){
            scan_tip_text.setText(getResources().getString(R.string.scan_wx_tip));
            zffs=APPConfig.WX;
        }else if (title.equals(getResources().getString(R.string.home_zfb))){
            zffs=APPConfig.ZFB;
            scan_tip_text.setText(getResources().getString(R.string.scan_alipay_tip));
        }else if (title.equals(getResources().getString(R.string.home_JD))){
            zffs=APPConfig.JD;
            scan_tip_text.setText("京东钱包扫一扫完成支付");
        }else if (title.equals(getResources().getString(R.string.home_QQ))){
            zffs=APPConfig.QQ;
            scan_tip_text.setText("QQ钱包扫一扫完成支付");
        }else if (title.equals(getResources().getString(R.string.home_YL))){
            zffs=APPConfig.YL;
            scan_tip_text.setText("银联钱包扫一扫完成支付");
        }

        scan_image.setImageBitmap(new ZXingUtils().createQRImage(Is.isNoEmptyAll(this.obj)? this.obj :"",
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics()),null));
    }

    public void init(){
        scan_image = (ImageView) findViewById(R.id.scan_image);
        left_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        left_return.setOnClickListener(this);
        titlename = ((TextView) findViewById(R.id.title));
        bar = ((ProgressBar) findViewById(R.id.pBar));
        scan_mcht_name = (TextView) findViewById(R.id.scan_mcht_name);
        scan_money = (TextView) findViewById(R.id.scan_money);
        scan_tip_text = (TextView) findViewById(R.id.scan_tip_text);
        //     scan_image.set
        my_code_layout = ((PercentRelativeLayout) findViewById(R.id.my_code_layout));
        //     scan_image.set
        registerForContextMenu(my_code_layout); // 注册上下问菜单栏
    }
    //注册上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v==my_code_layout){
            getMenuInflater().inflate(R.menu.menu2, menu);
            //   menu.setHeaderTitle("上下文菜单");
            //   menu.setHeaderIcon(R.drawable.icon_logo);
        }
    }
    //上下文菜单处理
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //  Drawable db = my_code_layout.getDrawable();
//        Bitmap bitmap = Bitmap.createBitmap(db.getIntrinsicWidth(), db.getIntrinsicHeight(),
//                Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        db.setBounds(0, 0, db.getIntrinsicWidth(), db.getIntrinsicHeight());
//        db.draw(canvas);

        my_code_layout.setDrawingCacheEnabled(true);
//        my_code_layout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //   my_code_layout.layout(my_code_layout.getLeft(), my_code_layout.getTop(), my_code_layout.getMeasuredWidth(), my_code_layout.getMeasuredHeight());
        Bitmap bitmap= Bitmap.createBitmap(my_code_layout.getDrawingCache());
        my_code_layout.setDrawingCacheEnabled(false);
//        BitmapDrawable background = new BitmapDrawable(bitmap2);
//        my_code_layout.setBackground(background);
//        Bitmap bitmap = DtoB.byD(background);
        switch (item.getItemId()) {

            case R.id.send: // 发送图片给好友
                //    Toast.makeText(UserCode.this, "send", Toast.LENGTH_SHORT).show();
                //   file= DtoB.saveBitmap(UserCode.this,bitmap,Login.PHONE);
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.setType("image/*");
//                      //  File f = new File(externalCacheDir,str);
//                Uri u = Uri.fromFile(file);
//                sendIntent.putExtra(Intent.EXTRA_STREAM, u);
//                startActivity(sendIntent);
                OnekeyShare oks = new OnekeyShare();
                //关闭sso授权
                oks.disableSSOWhenAuthorize();

                oks.addHiddenPlatform(TencentWeibo.NAME);
                //  oks.addHiddenPlatform(WechatFavorite.NAME);
                oks.addHiddenPlatform(QQ.NAME);
                oks.addHiddenPlatform(SinaWeibo.NAME);
                oks.addHiddenPlatform(QZone.NAME);
                // oks.addHiddenPlatform(AlipayMoments.NAME);
                // oks.addHiddenPlatform(Alipay.NAME);
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
                // oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
                // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                oks.setTitle(getResources().getString(R.string.app_name));
                // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                oks.setTitleUrl(getResources().getString(R.string.service_tg));
                // text是分享文本，所有平台都需要这个字段
                oks.setText(getResources().getString(R.string.service_tg));
                //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
                //  Drawable db = getResources().getDrawable(R.drawable.icon_logo);
                //  Bitmap bitmap = DtoB.byD(db);
                File file= DtoB.saveBitmap(PayCodeActivity.this,bitmap,R.id.img_chack+"");
                //oks.setImageUrl(file.getAbsolutePath().toString());
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                oks.setImagePath(file.getAbsolutePath().toString());//确保SDcard下面存在此张图片
                // url仅在微信（包括好友和朋友圈）中使用
                //  oks.setUrl("http://a.eqxiu.com/s/F21X05e7");
                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                // oks.setComment(PlayUtil.currentMusic.getSongname());
                // site是分享此内容的网站名称，仅在QQ空间使用
                //  oks.setSite(PlayUtil.currentMusic.getSongname());
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                //  oks.setSiteUrl(PlayUtil.currentMusic.getUrl());

// 启动分享GUI
                oks.show(PayCodeActivity.this);
                break;
            case R.id.send2: //发送链接给好友
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
                oks2.setText(mchtName+"正在使用俏美钱包向你收取"+money+"元，请点击支付！");
                Drawable db = getResources().getDrawable(R.drawable.logo);
                Bitmap bitmap2 = ((BitmapDrawable) db).getBitmap();
                File file2= DtoB.saveBitmap(PayCodeActivity.this,bitmap2,R.drawable.logo+"");
                //oks.setImageUrl(file.getAbsolutePath().toString());
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                oks2.setImagePath(file2.getAbsolutePath().toString());//确保SDcard下面存在此张图片
                //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博

                String url="http://qm.qiaomeishidai.com/qiaomeiqianbao/wxZfbPay.jsp?name=%s&money=%s&payurl=%s&type=%s";

                oks2.setUrl(String.format(url,mchtName,money,obj,zffs));
                Log.d("zanZQ", "onContextItemSelected: "+obj);
                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                // oks.setComment(PlayUtil.currentMusic.getSongname());
                // site是分享此内容的网站名称，仅在QQ空间使用
                //  oks.setSite(PlayUtil.currentMusic.getSongname());
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                //  oks.setSiteUrl(PlayUtil.currentMusic.getUrl());
                oks2.show(PayCodeActivity.this);
                break;
            case R.id.save: //保存图片
                String save_result = MediaStore.Images.Media.insertImage(getContentResolver(),bitmap ,"俏美钱包","普通支付");
                Log.d("zanZQ", "onContextItemSelected: "+save_result);
                if(Is.isNoEmpty(save_result)){
                    Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onContextItemSelected(item);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销上下文菜单栏事件
        unregisterForContextMenu(my_code_layout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_return:
                finish();
                break;
        }
    }
}
