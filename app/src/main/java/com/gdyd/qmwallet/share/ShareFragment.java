package com.gdyd.qmwallet.share;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gdyd.qmwallet.BaseFragment;


import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.Other.view.LoginActivity;
import com.gdyd.qmwallet.Other.view.RegisterActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.friends.activity.MainActivity;
import com.gdyd.qmwallet.utils.DtoB;

import java.io.File;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;

/**
 *
 */
public class ShareFragment extends BaseFragment implements View.OnClickListener{
    private View view;
    private ImageView share_zc;
  //  private LoginInfoBean bean;
    private ImageView friends;
    private ImageView zc_mdm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_share, container, false);
        }
        bean = ((LoginInfoBean) getActivity().getIntent().getSerializableExtra(APPConfig.LOGIN_INFO));
        share_zc = ((ImageView) view.findViewById(R.id.share_zu));
        friends = ((ImageView) view.findViewById(R.id.friends));
        zc_mdm = ((ImageView) view.findViewById(R.id.zc_mdm));
        zc_mdm.setOnClickListener(this);
        friends.setOnClickListener(this);
        share_zc.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (bean==null){
            checkBean();
            return;
        }

        switch (v.getId()){
            case R.id.share_zu:
                if (bean.getUserData().getMerchant().getCheckState()!=2){
                    checkSMRZBean();
                    return;
                }
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
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
//                builder.show();
                //http://qm.qiaomeishidai.com:8080/qiaomeiqianbao/share/qmqb_share.html?
                String url= "http://qm.qiaomeishidai.com/qiaomeiqianbao/share/qm_share.html?AgentID="+APPConfig.AgentID+"&RecommendNo=%s&Type=1";
                // String url="http://120.76.28.241:8080/qiaomeiqianbao/qmShar_personal.jsp?AgentID=564&RecommendNo=%s&Type=1";
                String merchantNo = bean.getUserData().getMerchant().getMerchantNo();
                startActivity(new Intent(getActivity(),RWebActivity.class).putExtra("type",0).putExtra("no",merchantNo).
                        putExtra("url",String.format(url, merchantNo)));
                break;
            case R.id.friends:
                startActivity(new Intent(getActivity(), MainActivity.class)
                        .putExtra("ID",bean.getUserData().getMerchant().getID())
                .putExtra("Permissions",bean.getUserData().getMerchant().getPermissions()));
                break;
            case R.id.zc_mdm:
                if (bean.getUserData().getMerchant().getCheckState()!=2){
                    checkSMRZBean();
                    return;
                }
                startActivity(new Intent(getActivity(),RegisterActivity.class)
                        .putExtra(APPConfig.TYPE,1)
                .putExtra("tjr",bean.getUserData().getMerchant().getPhoneNumber()));
                break;
        }
    }

    private void initTG() {

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
        oks2.setTitle("俏美钱包-国内领先无卡支付创业平台");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks2.setTitleUrl(getResources().getString(R.string.service_tg));
        // text是分享文本，所有平台都需要这个字段
        //  http://zgy.tunnel.qydev.com/onlinepay/wxZfbPay.jsp?name=%@&money=%@&payurl=%@&type=Z0
        oks2.setText("俏美钱包正在召唤你，刷卡即时到账，超低费率，分润秒结，全民创业，月入过万不是梦，赶紧来注册吧！");
        Drawable db = getResources().getDrawable(R.drawable.logo);
        Bitmap bitmap2 = DtoB.byD(db);
        File file2= DtoB.saveBitmap(getActivity(),bitmap2,R.drawable.logo+"");
        //oks.setImageUrl(file.getAbsolutePath().toString());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks2.setImagePath(file2.getAbsolutePath().toString());//确保SDcard下面存在此张图片
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        String url= "http://qm.qiaomeishidai.com/qiaomeiqianbao/share/qm_share.html?AgentID="+APPConfig.AgentID+"&RecommendNo=%s&Type=1";
       // String url="http://120.76.28.241:8080/qiaomeiqianbao/qmShar_personal.jsp?AgentID=564&RecommendNo=%s&Type=1";
        oks2.setUrl(String.format(url,bean.getUserData().getMerchant().getMerchantNo()));
        Log.d("zanZQ", "onContextItemSelected: "+url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        // oks.setComment(PlayUtil.currentMusic.getSongname());
        // site是分享此内容的网站名称，仅在QQ空间使用
        //  oks.setSite(PlayUtil.currentMusic.getSongname());
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        //  oks.setSiteUrl(PlayUtil.currentMusic.getUrl());
        oks2.show(getActivity());
    }
}
