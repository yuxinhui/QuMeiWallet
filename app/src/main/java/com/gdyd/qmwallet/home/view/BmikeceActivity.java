package com.gdyd.qmwallet.home.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.Other.presenter.BmikecePresenter;
import com.gdyd.qmwallet.Other.view.IBmikeceView;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.home.MemberDetailsActivity;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.myview.RiseNumberTextView;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.Tool;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class BmikeceActivity extends BaseActivity implements IBmikeceView,View.OnClickListener {
  private BmikecePresenter presenter=new BmikecePresenter(this);
    private LoginInfoBean bean;
    private TextView submit;
    private PercentRelativeLayout image_return;
    private TextView left_title;
    private PercentRelativeLayout layout_circle;
    private RiseNumberTextView ktx_money;
    private TextView ytx_money;
    private TextView curtx_money;
    private RiseNumberTextView sum_money;
    private double balance;
    private double sumMonty;
    private boolean isShow=true;
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Handler forgetPwd_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    int js = Integer.parseInt(msg.obj.toString());
                    if (js > 0) {  //逐步增加金额
                        String result = String .format("%.2f",balance/js);
                        String result2 = String .format("%.2f",sumMonty/js);
                        ktx_money.setText(result);
                        sum_money.setText("共计:"+result2+"元");
                    } else { //显示总金额
                        ktx_money.setClickable(true);
                        ktx_money.setText(String .format("%.2f",balance)+"");
                        sum_money.setClickable(true);

                        sum_money.setText("共计:"+ String .format("%.2f",sumMonty)+"元");
                        isShow = false;
                    }
                    break;
            }
        }
    };
    private double cashRate=0.006;
    private double singleCashRate=2.0;
    private PopupWindow pw;
    private Double aDouble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmikece);
        submit = ((TextView) findViewById(R.id.submit));
        image_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        left_title = ((TextView) findViewById(R.id.left_title));
        ktx_money = ((RiseNumberTextView) findViewById(R.id.ktx_money));
        ytx_money = ((TextView) findViewById(R.id.ytx_money));
        curtx_money = ((TextView) findViewById(R.id.curtx_money));
        sum_money = ((RiseNumberTextView) findViewById(R.id.sum_money));
        layout_circle = ((PercentRelativeLayout) findViewById(R.id.layout_circle));
     PercentRelativeLayout.LayoutParams params = new PercentRelativeLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels * 7 / 10, getResources().getDisplayMetrics().widthPixels * 7 / 10);
//        PercentRelativeLayout.LayoutParams layoutParams = (PercentRelativeLayout.LayoutParams) layout_circle.getLayoutParams();
//        layoutParams.height=getResources().getDisplayMetrics().widthPixels*7/10;
//        layoutParams.height=getResources().getDisplayMetrics().widthPixels*7/10;
       params.setMargins(getResources().getDisplayMetrics().widthPixels * 3 / 20,20,getResources().getDisplayMetrics().widthPixels * 3 / 20,0);
        layout_circle.setLayoutParams(params);
        submit.setOnClickListener(this);
        image_return.setOnClickListener(this);
        left_title.setOnClickListener(this);
        layout_circle.setOnClickListener(this);
        bean = ((LoginInfoBean) getIntent().getSerializableExtra(APPConfig.LOGIN_INFO));
        long transTyp = EncryptionHelper.getDate();
        String transTy="1039"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);
        presenter.getProfitInfo(new PlaceBean("1039",transKe,bean.getUserData().getMerchant().getMerchantNo(),0.00,transTyp));
    }

    @Override
    public void UpdataSubmit(String info) {
        //获取提现返回信息
        if (info==null||info.equals("")){
            return;
        }
        Log.d("zanZQ", "UpdataSubmit: "+info);
        try {
            JSONObject object = new JSONObject(info);
            int nResul = object.getInt("nResul");
            String sMessage = object.getString("sMessage");
            if (sMessage.equals("该商户没有钱包")){
                sMessage="余额不足";
            }
            if (nResul==1){
                long transTyp = EncryptionHelper.getDate();
                String transTy="1039"+transTyp+"";
                String transKe = EncryptionHelper.md5(transTy);
                presenter.getProfitInfo(new PlaceBean("1039",transKe,bean.getUserData().getMerchant().getMerchantNo(),0.00,transTyp));
                createPw();
            }else{
                Toast.makeText(this, sMessage, Toast.LENGTH_SHORT).show();
//                presenter.getProfitInfo(new PlaceBean("1039","4C15FC3006863CBB31F7044132FFCAFC",bean.getUserData().getMerchant().getMerchantNo(),0.00));
//                createPw();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getProfitInfo(String info) {
        //获取分润信息
        if (info!=null){
            try {
                JSONObject jsonObject = new JSONObject(info);
                int nResul = jsonObject.getInt("nResul");
                if (nResul==1){
                    JSONObject data = new JSONObject(jsonObject.getString("Data"));
                  balance = data.getDouble("balance");

                    double alreadyPostCash = data.getDouble("alreadyPostCash");
                    double yesterdayProfit = data.getDouble("yesterdayProfit");
                    double dayProfit = data.getDouble("dayProfit");
//                    Date date = new Date();
//                    int day = date.getDate();
//                    int hours = date.getHours();
//                    long time = date.getTime()/1000/60/60/24;
//                    long l = time - 17284;
//                    Random random = new Random();
//                            balance=random.nextDouble()*300*l+l*3200;
//                    dayProfit= 3500/24*hours+random.nextDouble()*500/24*hours;
//                    yesterdayProfit=2800+random.nextDouble()*1000;
//                    alreadyPostCash=random.nextDouble()*300+l*1500;
//                    Log.d("zanZQ", "getProfitInfo: "+day+"xiaoshi"+hours+"day"+time);
//                        balance=12062.22;
//                    yesterdayProfit=3202.56;
//                    dayProfit=2522.42;
//                    alreadyPostCash=8124.22;
                    cashRate = data.getDouble("cashRate");
                    singleCashRate = data.getDouble("singleCashRate");
                    sumMonty=balance +alreadyPostCash;
                     //ktx_money.setText(balance +"");
                  //  initData();
                //    balance=100;
                    ktx_money.withNumber(Float.valueOf(balance+"")).start();
                    ytx_money.setText(String .format("%.2f",yesterdayProfit)+"");
                    curtx_money.setText(String .format("%.2f",dayProfit)+"");
                    sum_money.withNumber(Float.valueOf(sumMonty+"")).start();
                  //  aDouble=100.0;
                //    createPw();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //我要提现对话框
            case R.id.submit:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_HOLO_LIGHT);
                LayoutInflater inflater = LayoutInflater.from(this);
               View view = inflater.inflate(R.layout.bmikece_submit, null);
                builder.setTitle("我要提现");
                final EditText viewById2 = (EditText) view.findViewById(R.id.edit_money);
//                TextView textView = (TextView) view.findViewById(R.id.tip);
//                String a="温馨提示：需扣除%s税点进行清算，单笔提现费为%s元。";
//                textView.setText(String.format(a,cashRate*100+"%",singleCashRate));
                builder.setView(view);
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("提现", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s = viewById2.getText().toString();
                        if (!s.equals("")){
                            aDouble = Double.valueOf(s);
                            if (aDouble >= 10) {
                                String s1 = ktx_money.getText().toString();
                              //  s1 = s1.replaceAll(",", "");
                                if (aDouble>Double.valueOf(s1)){
                                    Toast.makeText(BmikeceActivity.this, "提现金额不能大于账户余额", Toast.LENGTH_SHORT).show();
                                }else{
                                    long transTyp = EncryptionHelper.getDate();
                                    String transTy="1038"+transTyp+"";
                                    String transKe = EncryptionHelper.md5(transTy);
                                    presenter.getSubmit(new PlaceBean("1038",transKe,bean.getUserData().getMerchant().getMerchantNo(), aDouble,transTyp));
                                }
                            }else{
                                Toast.makeText(BmikeceActivity.this, "提现金额不能少于10元", Toast.LENGTH_SHORT).show();
                            }
                            
                       }else{
                            Toast.makeText(BmikeceActivity.this, "请输入金额", Toast.LENGTH_SHORT).show();
                        }
                   }
                });
                builder.show();
              break;
            case R.id.left_return:
                finish();
            break;
            case R.id.left_title:
                //提现记录
                startActivity(new Intent(BmikeceActivity.this,EarningRecordActivity.class).putExtra(APPConfig.LOGIN_MACHNO,bean.getUserData().getMerchant().getMerchantNo()));
            break;
            case R.id.layout_circle:
//                if (balance>0&&isShow){
//                    initData();
//                }

            break;
        }
    }

    private void initData() {  //初始化金额数据
        new Thread() {
            @Override
            public void run() {
                super.run();
                // 倒数时间
                for (int i = 20; i >= 0; i--) {
                    if(isShow){
                        Message mess = Message.obtain();
                        mess.what = 0;
                        mess.obj = i;
                        forgetPwd_handler.sendMessage(mess);
                        try {
                            Thread.sleep(80);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }
    /**
     * 创建查询PopupWindow
     */
    private void createPw(){
        backgroundAlpha(0.7f);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_rate, null);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView phone = (TextView) view.findViewById(R.id.phone);
        TextView tx_money = (TextView) view.findViewById(R.id.tx_money);
        TextView tx_rate = (TextView) view.findViewById(R.id.tx_rate);
        TextView sj_money = (TextView) view.findViewById(R.id.sj_money);
        TextView card = (TextView) view.findViewById(R.id.card);
        TextView time = (TextView) view.findViewById(R.id.time);
        TextView start_search = (TextView) view.findViewById(R.id.start_search);
        try {
            LoginInfoBean.UserData.MerchantBean merchant = bean.getUserData().getMerchant();
            String name1 = merchant.getName();
            if (name1!=null&&name1.length()>0){
                int length = name1.length();
                if (length ==2){
                    name1=name1.substring(0,1)+"*";
                }
                else if (length >2){
                    name1=name1.substring(0,1)+ Tool.getxing(length-2)+name1.substring(length -1, length);
                }
            }else{
                name1="";
            }
            name.setText(name1);
            String reserveNumber = merchant.getReserveNumber();
            phone.setText(reserveNumber.substring(0,3)+Tool.getxing(reserveNumber.length()-7)+ reserveNumber.substring(reserveNumber.length() -4, reserveNumber.length()));
            tx_money.setText(getResources().getString(R.string.CNY)+String .format("%.2f",aDouble));
            tx_rate.setText(((int) (cashRate * 100))+"%"+"+"+ ((int) singleCashRate)+"元");
            double v = aDouble - (aDouble * 0.06);
            sj_money.setText(String .format("%.2f",v-2)+"");
            String cardNo = bean.getUserData().getBankInfo().getCardNo();
            int length = cardNo.length();
            card.setText(Tool.getxing(cardNo.length()-4)+cardNo.substring(length -4, length));
            time.setText(dateFormat2.format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //构造一个popupWindow对象，
        //1.pw的View
        //2、3表示pw的宽和高
        //4.表示pw是否具有抢焦点的能力，效果同setFocusable
        pw = new PopupWindow(view, getResources().getDisplayMetrics().widthPixels*6/7, getResources().getDisplayMetrics().heightPixels*4/6, true);
        //  pw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        //点击外部区域时关闭popupWindow，必须设置setBackgroundDrawable才有效
        pw.setOutsideTouchable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        start_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (pw.isShowing()){
                 pw.dismiss();

             }
            }
        });

        // pw.showAsDropDown(title, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -16, getResources().getDisplayMetrics()), 20);
        pw.showAtLocation(curtx_money, Gravity.CENTER,0,0);
        //设置当pw打开时点击后退按钮时关闭pw
//        pw.setFocusable(true);
        //showAsDropDown表示pw在某一个控件的下方显示
        //代码中的尺寸都为px
        //第2、3个参数表示pw在x、y轴上的偏移量
    }
    /**
     * 设计屏幕亮度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0 1f为不透明
        getWindow().setAttributes(lp);
    }
}
