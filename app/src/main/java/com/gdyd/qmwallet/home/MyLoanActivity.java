package com.gdyd.qmwallet.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.Other.view.WebViewActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.config.UrlConfig;

/**
 * Created by hebei on 2017/7/4.
 */

public class MyLoanActivity extends BaseActivity implements View.OnClickListener{
    private TextView title;
    private PercentRelativeLayout left_return;
    private LinearLayout pf_dk_layout;
    private LinearLayout pa_dk_layout;
    private LinearLayout fd_dk_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_loan);
        title = ((TextView) findViewById(R.id.title));
        pf_dk_layout = ((LinearLayout) findViewById(R.id.pf_dk_layout));
        pa_dk_layout = ((LinearLayout) findViewById(R.id.pa_dk_layout));
        fd_dk_layout = ((LinearLayout) findViewById(R.id.fd_dk_layout));

        title.setText("我要贷款");
        left_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        left_return.setOnClickListener(this);
        pf_dk_layout.setOnClickListener(this);
        pa_dk_layout.setOnClickListener(this);
        fd_dk_layout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {//activity_yudian_xuanyao
        switch (v.getId()){
            case R.id.left_return:
                finish();
                return;
            case R.id.fd_dk_layout:
                startActivity(new Intent(this, WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.dk_feidai)).putExtra("url", UrlConfig.FEIDAI_DK));
                break;
            case R.id.pa_dk_layout:
                startActivity(new Intent(this, WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.dk_pingan)).putExtra("url", UrlConfig.PINGAN_DK));
                break;
            case R.id.pf_dk_layout:
                startActivity(new Intent(this, WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.dk_pufa)).putExtra("url", UrlConfig.PUFA_DK));
                break;

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
