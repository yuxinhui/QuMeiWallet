package com.gdyd.qmwallet.Other.view;

import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.Other.presenter.LoginPresenter;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.utils.EncryptionHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class ModifyPwdActivity extends BaseActivity implements ILoginView{

    private EditText write_old_pwd;
    private EditText write_new_pwd;
    private EditText write_confirm_pwd;
    private Button submit;
     private LoginPresenter presenter=new LoginPresenter(this);
    private String phone;
    private PercentRelativeLayout left_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        write_old_pwd = ((EditText) findViewById(R.id.write_old_pwd));
        write_new_pwd = ((EditText) findViewById(R.id.write_new_pwd));
        left_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        left_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        write_confirm_pwd = ((EditText) findViewById(R.id.write_confirm_pwd));
        phone = getIntent().getStringExtra("phone");
        submit = ((Button) findViewById(R.id.submit));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old = write_old_pwd.getText().toString();
                String newPwd = write_new_pwd.getText().toString();
                String newPwd2 = write_confirm_pwd.getText().toString();
                if (old==null||old.equals("")){
                    Toast.makeText(ModifyPwdActivity.this, "请输入旧密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if (old.length()<6){
                    Toast.makeText(ModifyPwdActivity.this, "旧密码长度小于6", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newPwd==null||newPwd.equals("")){
                    Toast.makeText(ModifyPwdActivity.this, "请输入旧密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if (newPwd.length()<6){
                    Toast.makeText(ModifyPwdActivity.this, "新密码长度小于6", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!newPwd.equals(newPwd2)){
                    Toast.makeText(ModifyPwdActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newPwd.equals(old)){
                    Toast.makeText(ModifyPwdActivity.this, "旧密码与新密码一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                long transTyp = EncryptionHelper.getDate();
                String transTy="1013"+transTyp+"";
                String transKe = EncryptionHelper.md5(transTy);
                presenter.AlterPwd(new PlaceBean("1013",transKe,old,newPwd,phone, APPConfig.ModifyPwdTYPE,APPConfig.TYPE,"",transTyp));
            }
        });
    }

    @Override
    public void getLoginInfo(LoginInfoBean loginInfoBean) {

    }

    @Override
    public void getAlterPwd(String a) {
      if (a!=null&&!a.equals("")){
          try {
              JSONObject object = new JSONObject(a);
              String sMessage = object.getString("sMessage");
              Toast.makeText(this, sMessage, Toast.LENGTH_SHORT).show();
              if (object.getInt("nResul")==1){
                  finish();
              }
          } catch (JSONException e) {
              e.printStackTrace();
          }
      }
    }
}
