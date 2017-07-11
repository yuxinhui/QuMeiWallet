package com.gdyd.qmwallet.trans;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.home.model.InfoBean;

public class InfoDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_details);
        TextView titles = (TextView) findViewById(R.id.titles);
        TextView times = (TextView) findViewById(R.id.times);
        TextView msg = (TextView) findViewById(R.id.msg);
        ImageView image_return = (ImageView) findViewById(R.id.image_return);
        image_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        InfoBean.DataBean.SmsPushDetailBean infoBean = (InfoBean.DataBean.SmsPushDetailBean) getIntent().getSerializableExtra("infoBean");
        times.setText(infoBean.getNotifyTime());

            titles.setText(infoBean.getMessTitle());
            times.setVisibility(View.VISIBLE);


        msg.setText("\t\t"+infoBean.getMessContent());

    }
}
