package com.gdyd.qmwallet.trans;

import android.content.Intent;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.MainActivity;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.home.ProfitDetailsActivity;
import com.gdyd.qmwallet.receiver.JGPush;

public class TransDetails extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_details);
        TextView jy_money = (TextView) findViewById(R.id.jy_money);
        TextView name = ((TextView) findViewById(R.id.jy_name));
        TextView phone = (TextView) findViewById(R.id.jy_phone);
        TextView fs = (TextView) findViewById(R.id.jy_fs);
        TextView time = (TextView) findViewById(R.id.jy_time);
        TextView no = (TextView) findViewById(R.id.jy_No);
        TextView zt = (TextView) findViewById(R.id.jy_zt);
        ImageView iv = (ImageView) findViewById(R.id.icon);
        TextView zfzt = (TextView) findViewById(R.id.zfzt);
        //TextView skr = (TextView) findViewById(R.id.jy_skr);
        PercentRelativeLayout left_return = (PercentRelativeLayout) findViewById(R.id.left_return);
        left_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkMain()){
                    Intent intent1 = new Intent(TransDetails.this, MainActivity.class);
                    startActivity(intent1.putExtra(APPConfig.LOGIN_INFO, JGPush.bean).putExtra("type",0));
                }
                    finish();

            }
        });
        Trans.DataBean.TransRecordsBean trans = (Trans.DataBean.TransRecordsBean) getIntent().getSerializableExtra("Trans");
        String name1 = getIntent().getStringExtra("name");
        String phone1 = getIntent().getStringExtra("phone");
        //    LoginInfoBean bean = (LoginInfoBean) getIntent().getSerializableExtra(APPConfig.LOGIN_INFO);
        Log.d("zanZQ", "onCreate: "+trans.toString());
        jy_money.setText(String .format("%.2f",trans.getTransMoney())+"");
        name.setText(name1);
        phone.setText(phone1);
        checkMain();
     //   int payType = trans.getPayType();
  //      tr

        fs.setText( trans.getTypeName());
        time.setText(trans.getPayTime()+"");
        String type = trans.getType();
        if (type.equals(getResources().getString(R.string.home_wx_Z0))){
            iv.setImageResource(R.drawable.icon_wx_color);
        }else if (type.equals(getResources().getString(R.string.home_zfb_AO))){
            iv.setImageResource(R.drawable.icon_zfb_color);
        }else if(type.equals(APPConfig.KJ)){
            iv.setImageResource(R.drawable.icon_kj_color);
        }else if (type.equals(APPConfig.JD)){
            iv.setImageResource(R.drawable.icon_jd_color);
        }else  if (type.equals(APPConfig.QQ)){
            iv.setImageResource(R.drawable.icon_qq_color);
        }else  if (type.equals(APPConfig.YL)){
            iv.setImageResource(R.drawable.icon_kj_color);
        }
        no.setText(trans.getOrderNo());
        if (trans.getOrderState().equals("00000")){
            zfzt.setText("支付成功");
            zt.setText("支付成功");
        }else{
            zfzt.setText("未支付");
            zt.setText("未支付");
        }
    }

    @Override
    public void onBackPressed() {

        if (checkMain()){
            Intent intent1 = new Intent(TransDetails.this, MainActivity.class);
            startActivity(intent1.putExtra(APPConfig.LOGIN_INFO, JGPush.bean).putExtra("type",0));
        }
            super.onBackPressed();

    }
}
