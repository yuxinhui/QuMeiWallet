package com.gdyd.qmwallet.mine;

import android.support.design.widget.TabLayout;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.home.view.MemberDetailsFragment;
import com.gdyd.qmwallet.home.view.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class CardManageActivity extends AppCompatActivity {

    private TabLayout tab_layout;
    private ViewPager viewpager;
    private PercentRelativeLayout left_return;
    List<Fragment> fragmentList = new ArrayList<>();
    private CardDetailsFragment fragment1;
    private CardDetailsFragment fragment2;
    private LoginInfoBean loginInfoBean;
    private int level;
    private TextView viewEdit;
    private boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_manage);
        tab_layout = ((TabLayout) findViewById(R.id.tab_layout));
        viewpager = ((ViewPager) findViewById(R.id.viewpager));
        left_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        left_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String[] array = getResources().getStringArray(R.array.tab_card);
        loginInfoBean = (LoginInfoBean) getIntent().getSerializableExtra(APPConfig.LOGIN_INFO);
        level = getIntent().getIntExtra(APPConfig.LEVEL,0);
        viewEdit = (TextView) findViewById(R.id.bank_edit);
        isEdit =true;
        viewEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                isEdit=!isEdit;
                if (isEdit){
                    viewEdit.setText("编辑");
                    fragment1.showDelete(true);
                    fragment2.showDelete(true);
                }else {
                    viewEdit.setText("完成");
                    fragment1.showDelete(false);
                    fragment2.showDelete(false);
                }
            }


        });

        for (int i = 0; i <2 ; i++) {
            if (i==0){
            fragment1 = CardDetailsFragment.getInstance(loginInfoBean.getUserData().getMerchant().getID(),2);
        }else{
                fragment2 = CardDetailsFragment.getInstance(loginInfoBean.getUserData().getMerchant().getID(),i);
            }
        }
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), fragmentList, array);
        viewpager.setAdapter(adapter);
        //将TabLayout和ViewPager关联起来
        tab_layout.setupWithViewPager(viewpager);
    }

}
