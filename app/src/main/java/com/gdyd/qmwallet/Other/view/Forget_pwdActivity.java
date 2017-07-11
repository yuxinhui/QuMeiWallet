package com.gdyd.qmwallet.Other.view;

import android.os.Handler;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.Other.presenter.RegisterPresenter;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.utils.EncryptionHelper;

public class Forget_pwdActivity extends BaseActivity implements IRegisterAndForgetView{
    private RegisterPresenter presenter=new RegisterPresenter(this);
    private boolean isCounting = false;
    private Button send;
    private Handler forgetPwd_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    int js = Integer.parseInt(msg.obj.toString());
                    if (js > 0) {
                        send.setText("重新发送(" + js + ")");
                    } else {
                        send.setClickable(true);
                        send.setText(getResources().getString(
                                R.string.send_verification_code));
                        isCounting = false;
                    }
                    break;
            }
        }
    };
    private PercentRelativeLayout image_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        TextView title = (TextView) findViewById(R.id.title);
        image_return = (PercentRelativeLayout) findViewById(R.id.left_return);
        image_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final EditText phone = (EditText) findViewById(R.id.write_phone_no);
        final EditText write_psw = (EditText) findViewById(R.id.write_psw);
        final EditText write_psw_two = (EditText) findViewById(R.id.write_psw_two);
        final EditText write_code = (EditText) findViewById(R.id.write_code);
        send = (Button) findViewById(R.id.send);
        Button submit = (Button) findViewById(R.id.submit);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = phone.getText().toString();
                if (phoneNo!=null&&phoneNo.length()==11){
                    presenter.getCode(phoneNo,"2");
                    //   send.setClickable(false);
                    isCounting = true;
                    send.setClickable(false);
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            // 倒数时间
                            for (int i = 59; i >= 0; i--) {
                                if(isCounting){
                                    Message mess = Message.obtain();
                                    mess.what = 0;
                                    mess.obj = i;
                                    forgetPwd_handler.sendMessage(mess);
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }.start();
                }else{
                    Toast.makeText(Forget_pwdActivity.this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = phone.getText().toString();
                String psw = write_psw.getText().toString();
                String psw_two = write_psw_two.getText().toString();
                if (phoneNo==null||phoneNo.length()!=11){
                    Toast.makeText(Forget_pwdActivity.this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (psw ==null||psw.equals("")){
                    Toast.makeText(Forget_pwdActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!psw.equals(psw_two)){
                    Toast.makeText(Forget_pwdActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = write_code.getText().toString();
                if (code==null||code.equals("")){
                    Toast.makeText(Forget_pwdActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                long transTyp = EncryptionHelper.getDate();
                String transTy="1013"+transTyp+"";
                String transKe = EncryptionHelper.md5(transTy);

                presenter.getSumbit(new PlaceBean("1013",transKe,"",psw,phoneNo,"1","1",code,transTyp));
            }
        });
    }

    @Override
    public void UpData(String msg) {
        if (msg!=null&&!msg.equals("")){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void RegisterGoBackMsg(String msg) {
        if (msg!=null&&!msg.equals("")){

            Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
            if(!msg.equals("验证码不正确")){
                finish();
            }
        }
    }

    @Override
    public void VerifyInfo(String msg) {

    }

    @Override
    public void AlterPWD(String msg) {

    }

    @Override
    public void bankCode(String msg) {

    }
}