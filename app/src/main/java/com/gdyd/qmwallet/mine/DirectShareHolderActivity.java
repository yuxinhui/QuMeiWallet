package com.gdyd.qmwallet.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.percent.PercentRelativeLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.mine.model.BlankBean;
import com.gdyd.qmwallet.mine.model.BranchBankBean;
import com.gdyd.qmwallet.mine.presenter.BankPresenter;
import com.gdyd.qmwallet.mine.view.IBankInfoView;
import com.gdyd.qmwallet.myview.XCRoundRectImageView;
import com.gdyd.qmwallet.utils.Is;
import com.gdyd.qmwallet.utils.ZXingUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by hebei on 2017/7/5.
 */

public class DirectShareHolderActivity extends BaseActivity implements View.OnClickListener,IBankInfoView {//activity_direct_shareholder.xml
    private TextView title;
    private PercentRelativeLayout left_return;
    private XCRoundRectImageView imgHeader;
    private ImageView imgQrcode;
    private TextView textName;
    private TextView textPhone;
    private LoginInfoBean loginInfoBean;
    private BankPresenter bankPresenter=new BankPresenter(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_shareholder);

        imgHeader = ((XCRoundRectImageView) findViewById(R.id.share_holder_header));
        textName = ((TextView)findViewById(R.id.name));
        textPhone= ((TextView)findViewById(R.id.phone));
        imgQrcode = ((ImageView) findViewById(R.id.img_Qrcode));
        left_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        left_return.setOnClickListener(this);

        loginInfoBean  = ((LoginInfoBean) getIntent().getSerializableExtra(APPConfig.LOGIN_INFO));

        if (loginInfoBean!=null && loginInfoBean.getUserData()!=null && loginInfoBean.getUserData().getShareholderInfo()!=null){
            bankPresenter.getUserInfo(loginInfoBean.getUserData().getShareholderInfo().getMerchantNo());
            textName.setText(loginInfoBean.getUserData().getShareholderInfo().getName());
            textPhone.setText(String.format("电话 ：%s",loginInfoBean.getUserData().getShareholderInfo().getPhoneNumber()));
        }


        String url= "http://qm.qiaomeishidai.com/qiaomeiqianbao/qmShar_personal.jsp?AgentID="+APPConfig.AgentID+"&RecommendNo=%s&Type=1";
        String fullUrl = String.format(url,loginInfoBean.getUserData().getMerchant().getMerchantNo());
        imgQrcode.setImageBitmap(new ZXingUtils().createQRImage(Is.isNoEmptyAll(fullUrl)? fullUrl :"",
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics()),null));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_return:
                finish();
                return;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

   @Override
  public  void ISumBankInfoView(BlankBean blankBean){

}

    @Override
   public void IBranchBankInfoView(BranchBankBean branchBankBean){

   }
   @Override
   public void ISubmitInfoBack(String backInfo){

   }
   @Override
   public void IUserInfoView(LoginInfoBean merchantBean){
       if (merchantBean!=null&&merchantBean.getnResul()==1){
        if (merchantBean.getUserData().getMerchant().getHeadImage()!=null)
            Picasso.with(this).load(UrlConfig.PHOTO_URI+merchantBean.getUserData().getMerchant().getHeadImage()).resize(400,400).centerCrop().into(imgHeader);
       }

   }
}
