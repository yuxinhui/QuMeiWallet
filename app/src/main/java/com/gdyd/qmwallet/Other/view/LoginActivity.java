package com.gdyd.qmwallet.Other.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.BaseFragment;
import com.gdyd.qmwallet.MainActivity;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.Other.presenter.LoginPresenter;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.event.LoginEvent;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.KeyBoardUtils;
import com.gdyd.qmwallet.utils.NetUtil;
import com.gdyd.qmwallet.utils.SharePreUtil;

import de.greenrobot.event.EventBus;

public class LoginActivity extends BaseActivity implements ILoginView,View.OnClickListener{

    private TextView is_forget;
    private EditText phone_no;
    private EditText password;
    private Button login_submit;
    private LoginPresenter presenter=new LoginPresenter(this);
    private String phone;
    private RelativeLayout layout_rember;
    private ImageView mRemember;
    private ImageView background;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phone_no = ((EditText) findViewById(R.id.phone_no));
        password = ((EditText) findViewById(R.id.password));
        login_submit = ((Button) findViewById(R.id.login_submit));
        background = ((ImageView) findViewById(R.id.background));
        init2(background);
        layout_rember = ((RelativeLayout) findViewById(R.id.layout_rember));
        is_forget = ((TextView) findViewById(R.id.is_forget));
        mRemember = (ImageView)findViewById(R.id.is_remember);
        TextView button_register = (TextView) findViewById(R.id.button_register);
        button_register.setOnClickListener(this);
        layout_rember.setOnClickListener(this);
        login_submit.setOnClickListener(this);
        is_forget.setOnClickListener(this);
      //  getWindow().addFlags(WindowManager.LayoutParams. FLAG_SECURE);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
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
        }else{

        }
        SharedPreferences shared = getSharedPreferences("main",
                Activity.MODE_PRIVATE);
       phone_no.setText( shared.getString("name",""));
        password.setText(shared.getString("pwd",""));
    }


    @Override
    public void getLoginInfo(LoginInfoBean loginInfoBean) {
        if (loginInfoBean!=null&&loginInfoBean.getnResul()==1){
            SharedPreferences shared = getSharedPreferences("main",
                    Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();
            if (!getString(R.string.tip_remember).equals(mRemember.getContentDescription().toString())) {
              editor.putString("mchtNo",loginInfoBean.getUserData().getMerchant().getMerchantNo());
              editor.putString("name",phone);
                editor.putString("pwd",pwd);

                editor.putBoolean("isRemember",true);
                editor.putString("mchtName",loginInfoBean.getUserData().getMerchant().getName());
            //    editor.putString("RecommendNo",loginInfoBean.getUserData().getMerchant().getRecommendNo());
               editor.putInt("ID",loginInfoBean.getUserData().getMerchant().getID());
            }else{
                editor.remove("mchtNo");
                editor.putBoolean("isRemember",false);
                editor.putString("name",phone);
                editor.putString("mchtName",loginInfoBean.getUserData().getMerchant().getName());
             //   editor.putString("RecommendNo",loginInfoBean.getUserData().getMerchant().getRecommendNo());
                editor.remove("ID");
            }
            editor.commit();
            String text = loginInfoBean.getsMessage();
            if (text==null||text.equals("")){
                text="登录成功";
            }else if (text.equals("未将对象引用设置到对象的实例。")){
                text="用户不存在";
            }
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

            SharePreUtil.saveStringData(this,"token",loginInfoBean.getUserData().getMerchant().getToken());
            SharePreUtil.saveStringData(this,"merchantNO",loginInfoBean.getUserData().getMerchant().getMerchantNo());

            EventBus.getDefault().post(new LoginEvent(loginInfoBean.getUserData().getMerchant().getToken(),loginInfoBean.getUserData().getMerchant().getMerchantNo()));


            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("phone",phone);
            BaseFragment.bean=loginInfoBean;
            intent.putExtra("ID",loginInfoBean.getUserData().getMerchant().getID());
            intent.putExtra(APPConfig.LOGIN_INFO,loginInfoBean);
            startActivity(intent);



            finish();
        }else if (loginInfoBean!=null){
            password.setText("");
            String text = loginInfoBean.getsMessage();
            if (text.contains("Object reference not set to an instance of an object.")||text.contains("未将对象引用")){
                text="用户不存在";
            }
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getAlterPwd(String a) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_register:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class).putExtra(APPConfig.TYPE,0));
            break;
            case R.id.login_submit:
                KeyBoardUtils.closeKeybordText(is_forget,LoginActivity.this);
                phone = phone_no.getText().toString();
                if (phone ==null|| phone.length()!=11){
                    Toast.makeText(LoginActivity.this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                pwd = password.getText().toString();
                if (pwd ==null|| pwd.length()==0){
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else  if (pwd.length()<6){
                    Toast.makeText(LoginActivity.this, "密码长度不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (NetUtil.isConnectionNet(LoginActivity.this)){
                    long transTyp = EncryptionHelper.getDate();
                    String transTy="1003"+transTyp+"";
                    String transKe = EncryptionHelper.md5(transTy);
                    presenter.Login(new PlaceBean("1003" ,transKe, APPConfig.TYPE,"", phone, pwd,"1",transTyp));

                }else{
                    Toast.makeText(this, "当前无网络连接", Toast.LENGTH_SHORT).show();  
                }
        
                break;
            case R.id.is_forget:
                KeyBoardUtils.closeKeybordText(is_forget,LoginActivity.this);
                startActivity(new Intent(LoginActivity.this,Forget_pwdActivity.class));
                break;
            case R.id.layout_rember:
                if (getResources().getString(R.string.tip_remember).equals(
                        mRemember.getContentDescription().toString())) {
                    // 记住密码
                    rememberPwd(mRemember);
                } else {
                    // 取消记住密码
                    cancelPwd(mRemember);
                }
                break;
        }
    }
    private void rememberPwd(ImageView mRemember) {
        mRemember.setImageDrawable(getResources()
                .getDrawable(R.drawable.icon_yes));
        mRemember.setContentDescription(getResources().getString(
                R.string.tip_qxremember));
    }

    private void cancelPwd(ImageView mRemember) {
        mRemember.setImageDrawable(getResources().getDrawable(
                R.drawable.icon_no));
        mRemember.setContentDescription(getResources()
                .getString(R.string.tip_remember));
    }
    private void init2(ImageView img) {
        img.setImageResource(R.drawable.login);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.45f);
        //设置动画执行时间
        alphaAnimation.setDuration(2500);
        alphaAnimation.setStartOffset(500);
        //动画执行完毕时是否保留View执行完时的状态
        alphaAnimation.setFillAfter(true);
        //延迟启动，参数以毫秒为单位
        alphaAnimation.setRepeatMode(RotateAnimation.REVERSE);
        alphaAnimation.setRepeatCount(RotateAnimation.INFINITE);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        img.startAnimation(alphaAnimation);
    }
}
