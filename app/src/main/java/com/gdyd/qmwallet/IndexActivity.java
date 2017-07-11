package com.gdyd.qmwallet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.mine.model.BlankBean;
import com.gdyd.qmwallet.mine.model.BranchBankBean;
import com.gdyd.qmwallet.mine.presenter.BankPresenter;
import com.gdyd.qmwallet.mine.view.IBankInfoView;
import com.gdyd.qmwallet.service.MyService;
import com.gdyd.qmwallet.utils.NetUtil;
import com.gdyd.qmwallet.utils.SharePreUtil;

public class IndexActivity extends BaseActivity implements IBankInfoView {
   private BankPresenter presenter=new BankPresenter(this);
    private Handler handler=new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what){
                case 0:
                    LoginInfoBean merchantBean = (LoginInfoBean) msg.obj;
                    if (merchantBean!=null&&merchantBean.getnResul()==1){
                        SharePreUtil.saveStringData(IndexActivity.this,"token",merchantBean.getUserData().getMerchant().getToken());
                        SharePreUtil.saveStringData(IndexActivity.this,"merchantNO",merchantBean.getUserData().getMerchant().getMerchantNo());
                        startActivity(new Intent(IndexActivity.this, MainActivity.class)
                                .putExtra(APPConfig.LOGIN_INFO,merchantBean)
                                .putExtra("ID",merchantBean.getUserData().getMerchant().getID()));
                        finish();
                    }else{
                        startActivity(new Intent(IndexActivity.this,MainActivity.class));
                        finish();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        SharedPreferences shared = getSharedPreferences("main",
                Activity.MODE_PRIVATE);
        ImageView back = (ImageView) findViewById(R.id.back);
        init2(back);
        startService( new Intent(IndexActivity.this, MyService.class));
        SharedPreferences.Editor editor = shared.edit();
        String mchtNo = shared.getString("mchtNo","");
   //  mchtNo="QMC0000000020";
        if (NetUtil.isConnectionNet(IndexActivity.this)){
            presenter.getUserInfo(mchtNo);
        }else{
            Toast.makeText(this, "当前无网络连接", Toast.LENGTH_SHORT).show();
            presenter.getUserInfo("");
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void ISumBankInfoView(BlankBean blankBean) {

    }

    @Override
    public void IBranchBankInfoView(BranchBankBean list) {

    }

    @Override
    public void ISubmitInfoBack(String backInfo) {

    }

    @Override
    public void IUserInfoView(final LoginInfoBean merchantBean) {
        Message msg=Message.obtain();
        msg.obj=merchantBean;
        msg.what=0;
        handler.sendMessageDelayed(msg,1500);

    }
    private void init2(ImageView img) {
        img.setImageResource(R.drawable.back);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.75f, 1f);
        //设置动画执行时间
        alphaAnimation.setDuration(2000);
        //动画执行完毕时是否保留View执行完时的状态
        alphaAnimation.setFillAfter(true);
        //延迟启动，参数以毫秒为单位
        alphaAnimation.setRepeatMode(RotateAnimation.INFINITE);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        img.startAnimation(alphaAnimation);
    }
}
