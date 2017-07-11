package com.gdyd.qmwallet.home.view;

import android.content.Context;
import android.content.Intent;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.IndexActivity;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.Other.view.WebViewActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.home.model.JyBean;
import com.gdyd.qmwallet.home.presenter.MainFgPresenter;
import com.gdyd.qmwallet.share.RWebActivity;
import com.gdyd.qmwallet.utils.Is;
import com.gdyd.qmwallet.utils.NetUtil;
import com.gdyd.qmwallet.utils.RegexUtils;
import com.gdyd.qmwallet.utils.ReplaceTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public class PayActivity extends BaseActivity implements IMainFgView,View.OnClickListener{

    private TextView title;
    private PercentRelativeLayout image_return;
    private TextView name;
    private TextView tip_money;
    private TextView money;
    private LoginInfoBean bean;
    private String stringExtra;
   private MainFgPresenter presenter=new MainFgPresenter(this);
    private String userName;
    private String moneyInfo;
   private boolean isFirst=true;
    private String merchantNo;
    private ImageView type_zf;
    private TextView type_tv;
    private TextView submit;
    private Double valueOf;
    private PercentRelativeLayout tip_ddsq;
    private String max="20000";
    private String min="10";
    private double maxmoney=20000;
    private double minmoney=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        title = ((TextView) findViewById(R.id.title));
        image_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        image_return.setOnClickListener(this);
        name = ((TextView) findViewById(R.id.name));
        tip_money = ((TextView) findViewById(R.id.tip_money));
        money = ((TextView) findViewById(R.id.money));
        type_zf = ((ImageView) findViewById(R.id.type_zf));
        type_tv = ((TextView) findViewById(R.id.type_tv));
        tip_ddsq = ((PercentRelativeLayout) findViewById(R.id.tip_ddsq));
        bean = ((LoginInfoBean) getIntent().getSerializableExtra(APPConfig.LOGIN_INFO));
        merchantNo = bean.getUserData().getMerchant().getMerchantNo();
      //  merchantNo="SMD0000001591";
        for (int i = 0; i < 13; i++) { //按钮初始化
           findViewById(getResources().getIdentifier("key_" + i, "id", this.getPackageName())).setOnClickListener(this);
        }
        submit = ((TextView) findViewById(R.id.key_13));
        submit.setOnClickListener(this);
        stringExtra = getIntent().getStringExtra(APPConfig.TRANS_TYPE);
        max = getIntent().getStringExtra(APPConfig.max);
        min = getIntent().getStringExtra(APPConfig.min);
        try {
            if (max!=null&&!max.equals("")){
                maxmoney=Double.valueOf(max);
            }
            if (min!=null&&!min.equals("")){
//                minmoney=Double.valueOf(min);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        int a=0;
        //判断用户是选择
        if (stringExtra.equals(APPConfig.KJ)){
            title.setText(getResources().getString(R.string.home_kj));
            type_zf.setImageResource(R.drawable.icon_kj);
            type_tv.setText(getResources().getString(R.string.tip_kj));
            a=14;
       //     submit.setBackgroundColor(getResources().getColor(R.color.blue));
        }else if (stringExtra.equals(APPConfig.WX)){
            title.setText(getResources().getString(R.string.home_wx));
            type_zf.setImageResource(R.drawable.icon_wx);
            type_tv.setText(getResources().getString(R.string.tip_wx));
           a=15;
           // submit.setBackgroundColor(getResources().getColor(R.color.green));
        }else if (stringExtra.equals(APPConfig.ZFB)){
            title.setText(getResources().getString(R.string.home_zfb));
            type_zf.setImageResource(R.drawable.icon_zfb);
            type_tv.setText(getResources().getString(R.string.tip_zfb));
            a=15;
         //   submit.setBackgroundColor(getResources().getColor(R.color.blue_));
        }else if (stringExtra.equals(APPConfig.JD)){
            title.setText(getResources().getString(R.string.home_JD));
            type_zf.setImageResource(R.drawable.icon_jd_s);
            type_tv.setText("京东安全支付");
            a=15;
        }else if (stringExtra.equals(APPConfig.QQ)){
            title.setText(getResources().getString(R.string.home_QQ));
            type_zf.setImageResource(R.drawable.icon_qq_s);
            type_tv.setText("QQ安全支付");
            a=15;
        }else if (stringExtra.equals(APPConfig.YL)){
            title.setText(getResources().getString(R.string.home_kj));
            type_zf.setImageResource(R.drawable.icon_kj);
            type_tv.setText("银联安全支付");
            a=15;
        }
        type_zf.setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, a, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, a, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, a, getResources().getDisplayMetrics()));
        userName = bean.getUserData().getMerchant().getName();
        name.setText(userName);
        money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              if (s==null||s.equals("")||s.equals(getResources().getString(R.string.zroe))){ //未输入状态 显示提示
                  tip_money.setVisibility(View.VISIBLE);
              }else{
                  tip_money.setVisibility(View.GONE);
              }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        checkMain();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("zanZQ", "onKeyDown: "+keyCode);
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                // 关闭popowWindow
                break;
            case KeyEvent.KEYCODE_MENU:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        return super.dispatchKeyEvent(event);
    }

    @Override
    public void getWXInfo(String info) {  //微信下单回调
        tip_ddsq.setVisibility(View.GONE);
        backgroundAlpha(1f);
        Log.d("zanZQ", "getWXInfo: "+info);
      //  Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        isFirst=true;
        if (info!=null&&!info.equals("")){
            try {
                JSONObject object = new JSONObject(info);
                String code = object.getString("code");

                if (code.equals("00")){
                    String pay_url = object.getString("pay_url");
                   // String format = String.format(UrlConfig.Z_OR_W, userName, moneyInfo, object.getString("pay_url"), "Z0");
                    Toast.makeText(this, "下单成功", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(PayActivity.this, PayCodeActivity.class)
                            .putExtra("url", pay_url)
                            .putExtra(APPConfig.TITLE,getResources().getString(R.string.home_wx))
                            .putExtra("name",userName).putExtra("money",String .format("%.2f",valueOf)+""));
                  //  startActivity(new Intent(PayActivity.this, WebViewActivity.class).putExtra("url",format).putExtra(APPConfig.TITLE,getResources().getString(R.string.home_wx)));
                }else{
                    String msg = object.getString("msg");
                    if (msg==null||msg.equals("")){
                        msg="下单失败！";
                    }
                    if (msg.equals("下单成功")){
                        msg="下单失败！";
                    }
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "下单失败！", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "下单失败！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getZFBInfo(String info) { // 支付宝下单回调
        tip_ddsq.setVisibility(View.GONE);
        backgroundAlpha(1f);
        isFirst=true;
        Log.d("zanZQ", "getZFBInfo: "+info);
      //  Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        if (info!=null&&!info.equals("")){
            try {
                JSONObject object = new JSONObject(info);
                String code = object.getString("code");


                if (code.equals("00")){
                    String pay_url = object.getString("pay_url");
                  //  String format = String.format(UrlConfig.Z_OR_W, userName, moneyInfo, object.getString("pay_url"), "A0");
                    Toast.makeText(this, "下单成功", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(PayActivity.this, PayCodeActivity.class)
                            .putExtra("url", pay_url)
                            .putExtra(APPConfig.TITLE,getResources().getString(R.string.home_zfb))
                    .putExtra("name",userName).putExtra("money",String .format("%.2f",valueOf)+""));
                }else{
                    String msg = object.getString("msg");
                    if (msg==null||msg.equals("")){
                        msg="下单失败！";
                    }
                    if (msg.equals("下单成功")){
                        msg="下单失败！";
                    }
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(this, "下单失败！", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this, "下单失败！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getKJInfo(String info) { // 快捷支付下单回调
        tip_ddsq.setVisibility(View.GONE);
        backgroundAlpha(1f);
        Log.d("zanZQ", "getKJInfo: "+info);
      //  Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        isFirst=true;
       if (info!=null&&!info.equals("")){
           try {
               JSONObject object = new JSONObject(info);
               String code = object.getString("code");
             //  String msg = object.getString("msg");
           //    String pay_url = object.getString("pay_url");
               if (code.equals("00")){
                   Toast.makeText(this, "下单成功", Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(PayActivity.this, RWebActivity.class).
                           putExtra("url", object.getString("pay_url")).
                           putExtra(APPConfig.TITLE,getResources().getString(R.string.home_kj))
                   .putExtra("type",1) .putExtra("name",userName).putExtra("money",String .format("%.2f",valueOf)+""));
               }else{
                   String msg = object.getString("msg");
                   if (msg==null||msg.equals("")){
                       msg="下单失败！";
                   }
                   if (msg.equals("下单成功")){
                       msg="下单失败！";
                   }
                   Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
               }
           } catch (JSONException e) {
               e.printStackTrace();
               Toast.makeText(this, "下单失败！", Toast.LENGTH_SHORT).show();
           }
       }else{
           Toast.makeText(this, "下单失败！", Toast.LENGTH_SHORT).show();
       }
    }

    @Override
    public void onClick(View v) { // 按键处理
        if (v instanceof TextView) {
            try {
                TextView tv = (TextView) v;
                String text = tv.getText().toString();
                String oldmoy = money.getText().toString();
                String oldmoney;
                 oldmoney = oldmoy.substring(1, oldmoy.length());
                if (getResources().getString(R.string.key_backspace).equals(text) && Is.isNoEmpty(oldmoy)) {
                    // 回退
                    if (Is.isNoEmpty(oldmoy) && ReplaceTools.parseMoneyThousand(oldmoney) > 0D) {
                        money.setText(getResources().getString(R.string.CNY) + ReplaceTools.formatMoneyThousand(ReplaceTools.parseMoneyThousand(oldmoney + text) / 10));
                    } else {
                        money.setText(getResources().getString(R.string.zroe));
                    }
                } else if (getResources().getString(R.string.key_empty).equals(text)) {
                    //清空
                    money.setText(getResources().getString(R.string.zroe));
                }else if (getResources().getString(R.string.key_10).equals(text) ){
                    if (Is.isNoEmpty(oldmoy)){
                        if (oldmoy.contains(getResources().getString(R.string.key_10))){
                            Toast.makeText(this, "输入有误！", Toast.LENGTH_SHORT).show();
                        }else{
                           String newmoy = oldmoney+getResources().getString(R.string.key_10);
                            money.setText(newmoy);
                        }
                    }else{
                        Toast.makeText(this, "输入有误！", Toast.LENGTH_SHORT).show();
                    }

                }else if (getResources().getString(R.string.submit).equals(text) && Is.isNoEmpty(oldmoy)){
                    if (!NetUtil.isConnectionNet(PayActivity.this)){
                        Toast.makeText(this, "当前无网络连接", Toast.LENGTH_SHORT).show();
                       return;
                    }
                    if (isFirst) {
                      //  isFirst=false;
                        valueOf = ReplaceTools.parseMoneyThousand(oldmoney);
                        if (stringExtra.equals(APPConfig.KJ)){
                     //     isFirst=false;
                            if (valueOf >=minmoney){
//                                if (valueOf <=maxmoney){
//                                    tip_ddsq.setVisibility(View.VISIBLE);
//                                    backgroundAlpha(0.7f);
//                                    presenter.KJTrans(new JyBean(valueOf +""
//                                            ,merchantNo,
//                                            1,"K0",3,4,""
//                                    ));
                                    startActivity(new Intent(PayActivity.this,GalleryActivity.class)
                                            .putExtra("type","K0").putExtra("money",valueOf).
                                                    putExtra(APPConfig.MERCHANTNO,merchantNo)
                                            .putExtra("userName",userName));
//                                }else{
////                                    isFirst=true;
////                                    Toast.makeText(this, "单笔交易金额为"+ ((int) maxmoney)+"元内", Toast.LENGTH_SHORT).show();
//                                }
                            }else{
                                isFirst=true;
                                Toast.makeText(this, "最低交易金额为"+ ((int) minmoney)+"元", Toast.LENGTH_SHORT).show();
                            }
                        }else if (stringExtra.equals(APPConfig.WX)){
                            if (valueOf >=minmoney){
//                                if (valueOf <=maxmoney){
//                                    tip_ddsq.setVisibility(View.VISIBLE);
//                                    backgroundAlpha(0.7f);
//                                    presenter.WXTrans(new JyBean(valueOf +""
//                                            ,merchantNo,
//                                            1,"Z0",1,4,""
//                                    ));
                                    startActivity(new Intent(PayActivity.this,GalleryActivity.class)
                                            .putExtra("type","Z0").putExtra("money",valueOf).
                                                    putExtra(APPConfig.MERCHANTNO,merchantNo).putExtra("userName",userName));
//                                }else{
//                                    isFirst=true;
//                                    Toast.makeText(this, "单笔交易金额为"+ ((int) maxmoney)+"元内", Toast.LENGTH_SHORT).show();
//                                }
                            }else{
                                isFirst=true;
                                Toast.makeText(this, "最低交易金额为"+ ((int) minmoney)+"元", Toast.LENGTH_SHORT).show();
                            }
                        }else if (stringExtra.equals(APPConfig.JD)){
                            if (valueOf >=minmoney){
//                                if (valueOf <=maxmoney){
//                                    tip_ddsq.setVisibility(View.VISIBLE);
//                                    backgroundAlpha(0.7f);
//                                    presenter.WXTrans(new JyBean(valueOf +""
//                                            ,merchantNo,
//                                            1,"Z0",1,4,""
//                                    ));
                                    startActivity(new Intent(PayActivity.this,GalleryActivity.class)
                                            .putExtra("type",stringExtra).putExtra("money",valueOf).
                                                    putExtra(APPConfig.MERCHANTNO,merchantNo).putExtra("userName",userName));
//                                }else{
//                                    isFirst=true;
//                                    Toast.makeText(this, "单笔交易金额为"+ ((int) maxmoney)+"元内", Toast.LENGTH_SHORT).show();
//                                }
                            }else{
                                isFirst=true;
                                Toast.makeText(this, "最低交易金额为"+ ((int) minmoney)+"元", Toast.LENGTH_SHORT).show();
                            }
                        }else if (stringExtra.equals(APPConfig.QQ)){
                            if (valueOf >=minmoney){
//                                if (valueOf <=maxmoney){
//                                    tip_ddsq.setVisibility(View.VISIBLE);
//                                    backgroundAlpha(0.7f);
//                                    presenter.WXTrans(new JyBean(valueOf +""
//                                            ,merchantNo,
//                                            1,"Z0",1,4,""
//                                    ));
                                    startActivity(new Intent(PayActivity.this,GalleryActivity.class)
                                            .putExtra("type",stringExtra).putExtra("money",valueOf).
                                                    putExtra(APPConfig.MERCHANTNO,merchantNo).putExtra("userName",userName));
//                                }else{
//                                    isFirst=true;
//                                    Toast.makeText(this, "单笔交易金额为"+ ((int) maxmoney)+"元内", Toast.LENGTH_SHORT).show();
//                                }
                            }else{
                                isFirst=true;
                                Toast.makeText(this, "最低交易金额为"+ ((int) minmoney)+"元", Toast.LENGTH_SHORT).show();
                            }
                        }else if (stringExtra.equals(APPConfig.YL)){
                            if (valueOf >=minmoney){
//                                if (valueOf <=maxmoney){
//                                    tip_ddsq.setVisibility(View.VISIBLE);
//                                    backgroundAlpha(0.7f);
//                                    presenter.WXTrans(new JyBean(valueOf +""
//                                            ,merchantNo,
//                                            1,"Z0",1,4,""
//                                    ));
                                    startActivity(new Intent(PayActivity.this,GalleryActivity.class)
                                            .putExtra("type",stringExtra).putExtra("money",valueOf).
                                                    putExtra(APPConfig.MERCHANTNO,merchantNo).putExtra("userName",userName));
//                                }else{
//                                    isFirst=true;
//                                    Toast.makeText(this, "单笔交易金额为"+ ((int) maxmoney)+"元内", Toast.LENGTH_SHORT).show();
//                                }
                            }else{
                                isFirst=true;
                                Toast.makeText(this, "最低交易金额为"+ ((int) minmoney)+"元", Toast.LENGTH_SHORT).show();
                            }
                        }else if (stringExtra.equals(APPConfig.ZFB)){
                            if (valueOf >=minmoney){
//                                if (valueOf <=maxmoney){
//                                    tip_ddsq.setVisibility(View.VISIBLE);
//                                    backgroundAlpha(0.7f);
//                                    presenter.ZFBTrans(new JyBean(valueOf +""
//                                            , merchantNo,
//                                            1,"A0",1,4,""
//                                    ));
                                    startActivity(new Intent(PayActivity.this,GalleryActivity.class)
                                            .putExtra("type","A0").putExtra("money",valueOf).
                                                    putExtra(APPConfig.MERCHANTNO,merchantNo)
                                    .putExtra("userName",userName));

//                                }else{
//                                    isFirst=true;
//                                    Toast.makeText(this, "单笔交易金额为"+ ((int) maxmoney)+"元内", Toast.LENGTH_SHORT).show();
//                                }
                            }else{
                                isFirst=true;
                                Toast.makeText(this, "最低交易金额为"+ ((int) minmoney)+"元", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                }else if (RegexUtils.isNumber(text)) {
                    String newmoy = "";
                    // 输入为数字
                    if (!Is.isNoEmpty(oldmoy)) {
                        newmoy = getResources().getString(R.string.zroe);
                    } else {
                        newmoy = getResources().getString(R.string.CNY) + ReplaceTools.formatMoneyThousand(ReplaceTools.parseMoneyThousand(oldmoney + text) * 10);
                    }
                    if (Is.isNoEmpty(newmoy) && newmoy.length() < 11 && ReplaceTools.parseMoneyThousand((newmoy.substring(1, newmoy.length()))) < 100000D) {
                        money.setText(newmoy);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        switch (v.getId()){
            case R.id.left_return:
                finish();
                break;
        }
    }
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 0f-1f,1f为不透明
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp =getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0 1f为不透明
        getWindow().setAttributes(lp);
    }
}
