package com.gdyd.qmwallet.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.Other.view.WebViewActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.config.UrlConfig;

import android.support.annotation.Nullable;


/**
 * Created by hebei on 2017/7/4.
 */

public class ApplyCreditCardActivity extends BaseActivity implements View.OnClickListener{
    private TextView title;
    private PercentRelativeLayout left_return;
    private LinearLayout pf_xyk_layout;
    private LinearLayout pa_xyk_layout;
    private LinearLayout sh_xyk_layout;
    private LinearLayout jt_xyk_layout;
    private LinearLayout xy_xyk_layout;
    private LinearLayout ms_xyk_layout;
    private LinearLayout gz_xyk_layout;
    private LinearLayout gd_xyk_layout;
    private LinearLayout zs_xyk_layout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_apply);
        title = ((TextView) findViewById(R.id.title));
        pf_xyk_layout = ((LinearLayout) findViewById(R.id.pf_xyk_layout));
        pa_xyk_layout = ((LinearLayout) findViewById(R.id.pa_xyk_layout));
        sh_xyk_layout = ((LinearLayout) findViewById(R.id.sh_xyk_layout));
        jt_xyk_layout = ((LinearLayout) findViewById(R.id.jt_xyk_layout));
        xy_xyk_layout = ((LinearLayout) findViewById(R.id.xy_xyk_layout));
        ms_xyk_layout = ((LinearLayout) findViewById(R.id.ms_xyk_layout));
        gz_xyk_layout = ((LinearLayout) findViewById(R.id.gz_xyk_layout));
        gd_xyk_layout = ((LinearLayout) findViewById(R.id.gd_xyk_layout));
        zs_xyk_layout = ((LinearLayout) findViewById(R.id.zs_xyk_layout));

        pf_xyk_layout.setOnClickListener(this);
        pa_xyk_layout.setOnClickListener(this);
        sh_xyk_layout.setOnClickListener(this);
        jt_xyk_layout.setOnClickListener(this);
        xy_xyk_layout.setOnClickListener(this);
        ms_xyk_layout.setOnClickListener(this);
        gz_xyk_layout.setOnClickListener(this);
        gd_xyk_layout.setOnClickListener(this);
        zs_xyk_layout.setOnClickListener(this);

        title.setText("信用卡申请");
        left_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        left_return.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_return:
                finish();
                return;
            case R.id.pf_xyk_layout:
                startActivity(new Intent(this, WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.xyk_pufa)).putExtra("url", UrlConfig.PUFA_XYK));
                break;
            case R.id.pa_xyk_layout:
                startActivity(new Intent(this, WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.xyk_pingan)).putExtra("url", UrlConfig.PINGAN_XYK));
                break;
            case R.id.jt_xyk_layout:
                startActivity(new Intent(this, WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.xyk_jiaotong)).putExtra("url", UrlConfig.JIAOTONG_XYK));
                break;
            case R.id.ms_xyk_layout:
                startActivity(new Intent(this, WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.xyk_mingsheng)).putExtra("url", UrlConfig.MINGSHENG_XYK));
                break;
            case R.id.xy_xyk_layout:
                startActivity(new Intent(this, WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.xyk_xingye)).putExtra("url", UrlConfig.XINGYE_XYK));
                break;
            case R.id.gz_xyk_layout:
                startActivity(new Intent(this, WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.xyk_guangzhou)).putExtra("url", UrlConfig.GUANGZHOU_XYK));
                break;
            case R.id.zs_xyk_layout:
                startActivity(new Intent(this, WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.xyk_zhaoshang)).putExtra("url", UrlConfig.ZHAOSHANG_XYK));
                break;
            case R.id.gd_xyk_layout:
                startActivity(new Intent(this, WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.xyk_guangda)).putExtra("url", UrlConfig.GUANGDA_XYK));
                break;
            case R.id.sh_xyk_layout:
                startActivity(new Intent(this, WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.xyk_shanghai)).putExtra("url", UrlConfig.SHANGHAI_XYK));
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


}
