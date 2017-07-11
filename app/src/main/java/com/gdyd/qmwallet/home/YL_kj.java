package com.gdyd.qmwallet.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.BaseFragment;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.home.model.JyBean;
import com.gdyd.qmwallet.home.presenter.YlPresenter;
import com.gdyd.qmwallet.home.view.IylView;
import com.gdyd.qmwallet.mine.UserInfoActivity;
import com.payeco.android.plugin.PayecoPluginPayCallBack;
import com.payeco.android.plugin.PayecoPluginPayIn;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class YL_kj extends BaseActivity implements IylView {

    private TextView write_sk_name;
    private TextView write_sk_id;
    private EditText write_bank_number;
    private EditText write_sk_phone;
    private Button submit;
    private LoginInfoBean bean;
   private YlPresenter presenter=new YlPresenter(this);
    private String money;
    private String merchantNo;
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMddHHmmss");
    private String lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
    //模拟通知商户地址，建议在接收到支付成功结果时，通知商户服务器
    private final static String URL_PAY_NOTIFY="http://wx.gdydit.cn/onlinepay/WFYdkjCallBack.action";
    private String name;
    private String idCard;
    private PercentRelativeLayout left_return;
  private PercentRelativeLayout tip_ddsq;
    private String no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yl_kj);
        write_sk_name = ((TextView) findViewById(R.id.write_sk_name));
        write_sk_id = ((TextView) findViewById(R.id.write_sk_id));
        write_bank_number = ((EditText) findViewById(R.id.write_bank_number));
        write_sk_phone = ((EditText) findViewById(R.id.write_sk_phone));
        tip_ddsq = ((PercentRelativeLayout) findViewById(R.id.tip_ddsq));
        left_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        left_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit = ((Button) findViewById(R.id.submit));
        money = getIntent().getStringExtra("money");
        no = getIntent().getStringExtra("NO");
        merchantNo = getIntent().getStringExtra(APPConfig.MERCHANTNO);
        bean = BaseFragment.bean;
        name = bean.getUserData().getMerchant().getName();
        write_sk_name.setText(name);
        idCard = bean.getUserData().getMerchant().getIDCardNo();
        write_sk_id.setText(idCard);
        SharedPreferences shared = getSharedPreferences("main",
                Activity.MODE_PRIVATE);
        String phone = shared.getString(idCard + "phone", "");
        String card = shared.getString(idCard + "card", "");
        if (phone!=null&&!phone.equals("")){
            write_sk_phone.setText(phone);
        }
        if (card!=null&&!card.equals("")){
            write_bank_number.setText(card);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirst){

                    String name = write_sk_name.getText().toString();
                    if (name==null||name.equals("")){
                        Toast.makeText(YL_kj.this, "收款人姓名不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String id = write_sk_id.getText().toString();
                    if (id==null||id.equals("")||id.length()!=18){
                        Toast.makeText(YL_kj.this, "收款人身份证不正确", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String phone = write_sk_phone.getText().toString();
                    if (phone==null||phone.equals("")||phone.length()!=11){
                        Toast.makeText(YL_kj.this, "预留手机不正确", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String number = write_bank_number.getText().toString();
                    if (number==null||number.equals("")){
                        Toast.makeText(YL_kj.this, "银行卡号不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }else if (number.length()<12){
                        Toast.makeText(YL_kj.this, "银行卡号最少为12位", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    isFirst=false;
                    tip_ddsq.setVisibility(View.VISIBLE);
                    backgroundAlpha(0.7f);
//                    String format = dateFormat2.format(new Date());
//                    Log.d("zanZQ", "onClick: "+format);
                    SharedPreferences shared = getSharedPreferences("main",
                            Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString(id+"phone",phone);
                    editor.putString(id+"card",number);
                    editor.commit();
                    //Android 6.0 请求权限
                    requestNeedPermissions();
                    presenter.getJY(new JyBean(money, no,phone,
                            number,id,
                            name,merchantNo));
                }


            }
        });
    }

    @Override
    public void backinfo(String info) {
        isFirst=true;
        tip_ddsq.setVisibility(View.GONE);
        backgroundAlpha(1f);
        try {
            if (info==null){
                return;
            }
            JSONObject  json = new JSONObject(info);
            if (json.has("code") && "0000".equals(json.getString("code"))) {

                    String upPayReqString = json.getJSONObject("data").toString();
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("Environment", "01"); // 00: 测试环境 01: 生产环境
                    params.put("upPay.Req", upPayReqString);
                    params.put("thePackageName", "com.gdyd.qmwallet"); //提交包名
                    PayecoPluginPayIn.doPay(YL_kj.this, params, new PayecoPluginPayCallBack() {
                        @SuppressLint("ShowToast")
                        @Override
                        public void callBack(String result, String errCode, String errMsg) {
                            if (errCode != null) {
                                Log.e("test", "errCode:" + errCode);
                                Log.e("test", "errMsg:" + errMsg);
                                //支付操作发错错误
                                Toast.makeText(getApplicationContext(),
                                        String.format("发生异常，错误码：%s，错误描述：%s", errCode, errMsg),
                                        Toast.LENGTH_LONG).show();
//								new AlertDialog.Builder(MainActivity.this).setTitle("提示")
//								.setMessage(String.format("发生异常，错误码：%s，错误描述：%s", errCode, errMsg))
//								.setPositiveButton("确定", null).show();
                                return;
                            }

                            final String notifyParams = result;

                            //判断是否是用户主动退出
                            //返回报文为：{"respDesc":"用户主动退出插件","respCode":"W101"}
                            try {
                                JSONObject obj = new JSONObject(result);
                                String code = obj.getString("respCode");
                                String msg = obj.getString("respDesc");
                                if("W101".equals(code)){
                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        //     使用异步通讯
//                new AsyncTask<Void, Void, String>(){
//                    @Override
//                    protected String doInBackground(Void... params) {
//                        //用于接收通讯响应的内容
//                        String respString = null;
//
//                        //通知商户服务器
//                        try {
//                            JSONObject reqJsonParams = new JSONObject(notifyParams);
//
//                            ArrayList<NameValuePair> reqParams = new ArrayList<NameValuePair>();
//                            @SuppressWarnings("unchecked")
//                            Iterator<String> keys = reqJsonParams.keys();
//                            while (keys.hasNext()) {
//                                String key = keys.next();
//                                String value = reqJsonParams.getString(key);
//                                reqParams.add(new BasicNameValuePair(key, value));
//                            }
//
//                            Log.i("test", "正在请求："+URL_PAY_NOTIFY);
//                            respString = httpComm(URL_PAY_NOTIFY, reqParams);
//                        } catch (JSONException e) {
//                            Log.e("test", "解析处理失败！", e);
//                            e.printStackTrace();
//                        } catch (Exception e) {
//                            Log.e("test", "通知失败，通讯发生异常", e);
//                            e.printStackTrace();
//                        }
//                        return respString;
//                    }
//
//                    @Override
//                    protected void onPostExecute(String result) {
//                        super.onPostExecute(result);
//
//                        if (result == null) {
//                            Log.e("test", "通知失败！");
//                            return ;
//                        }
//
//                        Log.i("test", "响应数据："+result);
//
//                        try {
//                            //解析响应数据
//                            JSONObject json = new JSONObject(result);
//
//                            //校验返回结果
//                            if (!json.has("RetMsg")) {
//                                Toast.makeText(YL_kj.this, "返回数据有误:"+result, Toast.LENGTH_LONG).show();
//                                Log.e("test", "返回数据有误:"+result);
//                                return ;
//                            }
//                            Toast.makeText(YL_kj.this, json.getString("RetMsg"), Toast.LENGTH_LONG).show();
//                        } catch (JSONException e) {
//                            Log.e("test", "解析处理失败！", e);
//                            e.printStackTrace();
//                        }
//
//                    }
//                }.execute();

                            //跳转至支付结果页面
                            Intent resultIntent = new Intent(YL_kj.this, ResultActivity.class);
                            resultIntent.putExtra("result", result);
                            startActivity(resultIntent);
                        }
                    });
                }else{
                    Toast.makeText(YL_kj.this, "返回数据有误:", Toast.LENGTH_LONG).show();
                    return;
                }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    //http通讯
    private String httpComm(String reqUrl, ArrayList<NameValuePair> reqParams) throws UnsupportedEncodingException,
            IOException, ClientProtocolException {
        String respString = null;
        HttpPost httpPost = new HttpPost(reqUrl);
        HttpEntity entity = new UrlEncodedFormEntity(reqParams, HTTP.UTF_8);
        httpPost.setEntity(entity);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResp = httpClient.execute(httpPost);
        int statecode = httpResp.getStatusLine().getStatusCode();
        if (statecode == 200) {
            respString = EntityUtils.toString(httpResp.getEntity());
        }else{
            Log.e("test", "通讯发生异常，响应码["+statecode+"]");
        }
        return respString;
    }
    class myAsyncTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            return null;
        }
    }
    //Android 6.0 需要请求权限
    @TargetApi(23)
    private void requestNeedPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissionsNeeded = new ArrayList<String>();
            final List<String> permissionsList = new ArrayList<String>();
            if (!addPermission(YL_kj.this, permissionsList,Manifest.permission.READ_PHONE_STATE))
                permissionsNeeded.add("获取手机信息");
            if (!addPermission(YL_kj.this, permissionsList,Manifest.permission.WRITE_EXTERNAL_STORAGE))
                permissionsNeeded.add("读取数据卡");
            if (!addPermission(YL_kj.this, permissionsList,Manifest.permission.ACCESS_FINE_LOCATION))
                permissionsNeeded.add("定位");
            if (!addPermission(YL_kj.this, permissionsList,Manifest.permission.ACCESS_COARSE_LOCATION))
                permissionsNeeded.add("定位");
            if (permissionsList.size() > 0) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), 111);
                return;
            }
        }
    }
    @TargetApi(23)
    private boolean addPermission(Context Ctx, List<String> permissionsList,
                                  String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }
    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 111:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permissions[i])) {
                            // 缺少数据卡读写权限 （必须）
                            Toast.makeText(getApplicationContext(), "插件必须要数据卡读写权限！", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (Manifest.permission.READ_PHONE_STATE.equals(permissions[i])) {
                            // 缺少读取手机信息权限（可选）
                        }
                        if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permissions[i]) ||
                                Manifest.permission.ACCESS_COARSE_LOCATION.equals(permissions[i])) {
                            // 缺少定位权限 （可选）
                        }
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,grantResults);
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
