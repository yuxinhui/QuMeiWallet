package com.gdyd.qmwallet.talk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.talk.model.ContactBean;

import io.rong.imkit.RongIM;


/**
 * Created by fx-168 on 17/7/6.
 */

public class ContactDetailActivity extends BaseActivity{


    TextView name,phone;
    Button send;

    ContactBean contactBean;
    String name2;
    private ImageView mBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail);



        contactBean = (ContactBean) getIntent().getSerializableExtra("contactBean");

        name = (TextView) findViewById(R.id.name);
        phone = (TextView) findViewById(R.id.phone);
        send = (Button) findViewById(R.id.send);


         name2 = contactBean.getName();

        if(TextUtils.isEmpty(name2) || "null".equals(name2)){
            name2 = "[未实名]";
        }
        name.setText(name2);
        phone.setText("手机号： " +contactBean.getPhoneNumber());


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RongIM.getInstance().startPrivateChat(ContactDetailActivity.this, contactBean.getMerchantNo(), name2);

            }
        });


        mBack = (ImageView) findViewById(R.id.image_return);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }




}
