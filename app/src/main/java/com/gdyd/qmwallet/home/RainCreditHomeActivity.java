package com.gdyd.qmwallet.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;


import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.Other.view.WebViewActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.home.model.NewYuDianBean;
import com.gdyd.qmwallet.home.presenter.ProfitPresenter;
import com.gdyd.qmwallet.home.view.IYuDianCreditView;
import com.gdyd.qmwallet.home.presenter.YuDianPresenter;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.home.view.RainCreditCircle;


/**
 * Created by hebei on 2017/6/29.
 */

public class RainCreditHomeActivity extends BaseActivity implements IYuDianCreditView,View.OnClickListener{
    private TextView title;
    private PercentRelativeLayout left_return;
    private TextView rightTitle;
    private TextView text_credit_value;
    private LoginInfoBean bean;
    private ImageView img_shaishaifen;
    private ImageView img_creditjilu;
    private RainCreditCircle credit_circle;
    private NewYuDianBean newYuDianBean;
    private YuDianPresenter presenter=new YuDianPresenter(this);



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rain_credit_home);
        title = ((TextView) findViewById(R.id.title));
        title.setText("雨点信用");
        bean = ((LoginInfoBean) getIntent().getSerializableExtra(APPConfig.LOGIN_INFO));
        left_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        left_return.setOnClickListener(this);
        rightTitle = ((TextView)findViewById(R.id.left_title));
        rightTitle.setOnClickListener(this);
        img_shaishaifen = ((ImageView) findViewById(R.id.img_shaishaifen));
        img_shaishaifen.setOnClickListener(this);
        img_creditjilu =  ((ImageView) findViewById(R.id.img_creditjilu));
        img_creditjilu.setOnClickListener(this);
        credit_circle = ((RainCreditCircle)findViewById(R.id.view_crdit_circle));
        text_credit_value = ((TextView)findViewById(R.id.text_credit_value));
        sendRequestgetYuDian();
    }


    public void sendRequestgetYuDian(){
        long transTyp = EncryptionHelper.getDate();
        String transTy = "1081" + transTyp;
        String transKe = EncryptionHelper.md5(transTy);
        presenter.getYuDianData(new PlaceBean("1081", transKe, bean.getUserData().getMerchant().getMerchantNo(), 1, 25, transTyp));
    }

    @Override
    public void getYudianNewCredit(NewYuDianBean newBean){
        if (bean!=null&&(bean.getnResul()==1)){
            newYuDianBean = newBean;
            credit_circle.setCreditValue(newBean.getDataBean().getCreditSource());
            credit_circle.setElavateTime(newBean.getDataBean().getComputerTime().substring(0,10));
            credit_circle.setmLevel(newBean.getDataBean().getCreditEvaluation());
            text_credit_value.setText(newBean.getDataBean().getCreditMoney()+"");

        }

    }

    @Override
    public void onClick(View v) {//http://qm.qiaomeishidai.com/qiaomeiqianbao/yudian/yudianRule.html
        switch (v.getId()){
            case R.id.left_return:
                finish();
                return;
            case R.id.img_shaishaifen:
                startActivity(new Intent(this, ShaiShaiFenActivity.class).putExtra(APPConfig.LOGIN_INFO,bean).putExtra("yudianfen",newYuDianBean.getDataBean().getCreditSource()));
                break;
            case R.id.img_creditjilu://
                startActivity(new Intent(this, RainRecordActivity.class).putExtra(APPConfig.LOGIN_INFO,bean).putExtra("yudianfen",newYuDianBean.getDataBean().getCreditSource()));
                break;
            case R.id.left_title:
                startActivity(new Intent(this, WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.yudian_guize)).putExtra("url", UrlConfig.YUDIAN_GUIZE));
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
