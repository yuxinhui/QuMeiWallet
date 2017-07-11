package com.gdyd.qmwallet.mine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.percent.PercentLinearLayout;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.MainActivity;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.Other.presenter.RegisterPresenter;
import com.gdyd.qmwallet.Other.view.IRegisterAndForgetView;
import com.gdyd.qmwallet.Other.view.RegisterActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.config.HandleConfig;
import com.gdyd.qmwallet.mine.model.BlankBean;
import com.gdyd.qmwallet.mine.model.BranchBankBean;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.mine.model.UserInfoBean;
import com.gdyd.qmwallet.mine.presenter.BankPresenter;
import com.gdyd.qmwallet.mine.view.IBankInfoView;
import com.gdyd.qmwallet.myview.ChangeAddressPopwindow;
import com.gdyd.qmwallet.myview.kankan.wheel.widget.WheelView;
import com.gdyd.qmwallet.myview.kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.KeyBoardUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class AddCardActivity  extends BaseActivity implements
        ChangeAddressPopwindow.OnAddressCListener,IBankInfoView,IRegisterAndForgetView {
    private Button submit;

    private PercentRelativeLayout layout_bank_site;
    private BankPresenter presenter=new BankPresenter(this);
    private RegisterPresenter codepresenter=new RegisterPresenter(this);
    private WheelView wvCitys;
    private List<BlankBean.DataBean> data;
    private WheelView wvCitys2;
    private PopupWindow pw;
    private PopupWindow pw2;
    private TextView write_ssq;
    private TextView write_bank_type;
    private PercentRelativeLayout layout_bank_type;
    private TextView write_psw_two;
    private PercentRelativeLayout layout_bank_type2;
    private String cityName;
    private BlankBean.DataBean bankData;
    private EditText write_sk_name;
    private EditText write_sk_id;
    private EditText write_sk_site;
    private EditText write_bank_number;
    private String phone="";
    private long no=0;
    private String province;
    private BranchBankBean bean;
    private String s;
    private int ID;
    private LoginInfoBean loginInfoBean;
    //    private String BigBankCode;
    private String OPEN_STLNO;
    private ArrayList<String> list=new ArrayList<>();
    private PercentRelativeLayout left_return;
    private int cityId;
    private int provinceId;
    private int checkState;
    private String bankcode;
    private String fhname;
    private EditText write_sk_phone;
    private PercentRelativeLayout tip_ddsq;
    private Button send;
    private boolean isCounting = false;
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
    private EditText phonecode;
    private String psw_two;
    private String ssq;
    private String number;
    private String type;
    private int add_type;
    private TextView title;
    private PercentRelativeLayout layout_body;
    private PercentLinearLayout layout_body2;
    private LoginInfoBean.UserData.MerchantBean merchant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        submit = ((Button) findViewById(R.id.submit));
        left_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        left_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        send = ((Button) findViewById(R.id.send));
        //    presenter.getUserInfo(loginInfoBean.getUserData().getMerchant().getMerchantNo());
        tip_ddsq = ((PercentRelativeLayout) findViewById(R.id.tip_ddsq));
        write_sk_name = ((EditText) findViewById(R.id.write_sk_name));
        write_sk_id = ((EditText) findViewById(R.id.write_sk_id));
        write_sk_site = ((EditText) findViewById(R.id.write_sk_site));
        phonecode = ((EditText) findViewById(R.id.write_psw_two2));
        write_bank_number = ((EditText) findViewById(R.id.write_bank_number));
        write_sk_phone = ((EditText) findViewById(R.id.write_sk_phone));
        layout_body = ((PercentRelativeLayout) findViewById(R.id.layout_body));
        layout_body2 = ((PercentLinearLayout) findViewById(R.id.layout_body2));
        write_ssq = ((TextView) findViewById(R.id.write_ssq));
        layout_bank_site = ((PercentRelativeLayout) findViewById(R.id.layout_bank_site));
        write_bank_type = ((TextView) findViewById(R.id.write_bank_type));
        write_psw_two = ((TextView) findViewById(R.id.write_psw_two));
        layout_bank_type2 = ((PercentRelativeLayout) findViewById(R.id.layout_bank_type2));
        layout_bank_type = ((PercentRelativeLayout) findViewById(R.id.layout_bank_type));

        loginInfoBean = ((LoginInfoBean) getIntent().getSerializableExtra(APPConfig.LOGIN_INFO));
        merchant = loginInfoBean.getUserData().getMerchant();
        checkState = merchant.getCheckState();
        list=loginInfoBean.getUserData().getImagelistUrl();
        ID = merchant.getID();
        phone = merchant.getPhoneNumber();
        Log.d("zanZQ", "onCreate: "+phone);
        add_type = getIntent().getIntExtra("type",2);
        title = ((TextView) findViewById(R.id.title));
        write_sk_name.setText(merchant.getName());
        write_sk_id.setText(merchant.getIDCardNo());
        write_sk_phone.setText(merchant.getReserveNumber());
        write_sk_name.setFocusable(false);
        write_sk_id.setFocusable(false);
        if (add_type==2){
            title.setText("添加信用卡");
            findViewById(R.id.v_2).setVisibility(View.GONE);
            findViewById(R.id.v_1).setVisibility(View.GONE);
//             write_sk_name.setText(merchant.getName());
//            write_sk_id.setText(merchant.getIDCardNo());
//            write_sk_phone.setText(merchant.getReserveNumber());
          //  write_sk_name.setFocusable(false);
            layout_bank_type2.setVisibility(View.GONE);
            layout_bank_site.setVisibility(View.GONE);
            PercentRelativeLayout.LayoutParams lp = new PercentRelativeLayout.LayoutParams(
                    getResources().getDisplayMetrics().widthPixels,
                  getResources().getDisplayMetrics().heightPixels*32/100);
            lp.topMargin=getResources().getDisplayMetrics().heightPixels*29/100;

            PercentRelativeLayout.LayoutParams layoutParams = (PercentRelativeLayout.LayoutParams) layout_body2.getLayoutParams();
            layoutParams.height=getResources().getDisplayMetrics().heightPixels*32/100;
            layoutParams.width=  getResources().getDisplayMetrics().widthPixels;

            layout_body2.setLayoutParams(lp);
         //   layout_body2.requestLayout();

        }else{

            title.setText("添加储蓄卡");
        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = write_bank_number.getText().toString();
                if (number ==null|| number.equals("")){
                    Toast.makeText(AddCardActivity.this, "银行卡号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String phoneNo = write_sk_phone.getText().toString();
                if (phoneNo!=null&&phoneNo.length()==11){
                    // codepresenter.getCode(phoneNo,"2");
                    long transTyp = EncryptionHelper.getDate();
                    String transTy="1071"+transTyp+"";
                    String transKe = EncryptionHelper.md5(transTy);
                    codepresenter.Add_card_code(new PlaceBean("1071",transKe,transTyp,number,
                            merchant.getIDCardNo(), phoneNo, merchant.getName(),add_type));
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
                    Toast.makeText(AddCardActivity.this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String phone = write_sk_phone.getText().toString();
                if (phone==null||phone.equals("")||phone.length()!=11){
                    Toast.makeText(AddCardActivity.this, "预留手机不正确", Toast.LENGTH_SHORT).show();
                    return;
                }

                number = write_bank_number.getText().toString();
                if (number ==null|| number.equals("")){
                    Toast.makeText(AddCardActivity.this, "银行卡号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }else if (number.length()<12){
                    Toast.makeText(AddCardActivity.this, "银行卡号最少为12位", Toast.LENGTH_SHORT).show();
                    return;
                }
                type = write_bank_type.getText().toString();
                if (type ==null|| type.equals("")){
                    Toast.makeText(AddCardActivity.this, "请选择所属银行", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (add_type!=2){
                    ssq = write_ssq.getText().toString();
                    if (ssq ==null|| ssq.equals("")){
                        Toast.makeText(AddCardActivity.this, "请选择开户地址", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    psw_two = write_psw_two.getText().toString();
                    if (psw_two ==null|| psw_two.equals("")){
                        Toast.makeText(AddCardActivity.this, "请选择所属支行", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (cityId==0||provinceId==0){
                        Toast.makeText(AddCardActivity.this, "请重新选择开户地址", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                String code = phonecode.getText().toString();
                if (code==null||code.equals("")){
                    Toast.makeText(AddCardActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("zanZQ", "onClick: "+cityId+"pro"+provinceId);
             //   if (checkState ==3||checkState==0||checkState==5){

                    tip_ddsq.setVisibility(View.VISIBLE);
                    backgroundAlpha(0.7f);
                    long transTyp = EncryptionHelper.getDate();
                    String transTy="1013"+transTyp+"";
                    String transKe = EncryptionHelper.md5(transTy);
                    codepresenter.UPcode(new PlaceBean("1013",transKe,"","",phone,"1","1",code,transTyp));
                //    presenter.getSubmitInfo(userInfoBean);
//                }else{
//                    startActivity(new Intent(AddCardActivity.this, MainActivity.class).putExtra(APPConfig.LOGIN_INFO, loginInfoBean));
//                    finish();
//                }
            }
        });

        layout_bank_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.closeKeybordText(write_bank_type,AddCardActivity.this);
                presenter.getSumBank();
            }
        });
        layout_bank_type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bankData!=null){
                    bankcode = bankData.getBankcode();
                }

                if (bankcode !=null&&cityName!=null){
                    //  presenter.getBranchBank(bankcode,cityName);
                    Intent intent = new Intent(AddCardActivity.this,ZhActivity.class);
                    intent.putExtra("BankCode",bankcode);
                    intent.putExtra("CityName",cityName);
                    intent.putExtra("AeeaName","");
                    startActivityForResult(intent, HandleConfig.SUCCESS);
                }else{
                    if (bankcode==null||bankcode.equals("")){
                        Toast.makeText(AddCardActivity.this, "请选择所属银行", Toast.LENGTH_SHORT).show();
                    }else if (cityName==null ||cityName.equals("")){
                        Toast.makeText(AddCardActivity.this, "请选择开户地址", Toast.LENGTH_SHORT).show();
                    }
                    //    Toast.makeText(UserInfoActivity.this, "请先选择开户地址和所属银行", Toast.LENGTH_SHORT).show();
                }

            }
        });
        layout_bank_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeAddressPopwindow pw = new ChangeAddressPopwindow(AddCardActivity.this);
                pw.setAddress(province,cityName,"");
                pw.setOutsideTouchable(true);
                pw.setAddresskListener(AddCardActivity.this);

                pw.setBackgroundDrawable(new BitmapDrawable());
                pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        backgroundAlpha(1.0f);
                    }
                });
                //设置当pw打开时点击后退按钮时关闭pw
//        pw.setFocusable(true);
                //showAsDropDown表示pw在某一个控件的下方显示
                //代码中的尺寸都为px
                //第2、3个参数表示pw在x、y轴上的偏移量
                backgroundAlpha(1.0f);
                pw.showAsDropDown(write_ssq, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -16, getResources().getDisplayMetrics()), 20);
            }
        });
//        if (checkState==1||checkState==2){
//            submit.setText("关闭");
//            write_sk_name.setEnabled(false);
//            write_sk_id.setEnabled(false);
//            write_sk_site.setEnabled(false);
//            write_bank_number.setEnabled(false);
//            write_sk_phone.setEnabled(false);
//            layout_bank_site.setOnClickListener(null);
//            layout_bank_type2.setOnClickListener(null);
//            layout_bank_type.setOnClickListener(null);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (ContextCompat.checkSelfPermission(AddCardActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                ActivityCompat.requestPermissions(AddCardActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                        APPConfig.save_send    );
//            }
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == APPConfig.save_send) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //  Toast.makeText(this,"已获得授权！",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"相机未获得授权！",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(String province, String city, String area,int p,int c) {
        Log.d("zanZQ", "onClick: "+c+",,,,"+p);
        if (c!=0){
            cityId=c;
        }
        if (p!=0){
            provinceId=p;
        }

        cityName = city.trim();
        this.province = province.trim();
        write_ssq.setText(this.province +cityName+area.trim());
    }

    @Override
    public void ISumBankInfoView(BlankBean blankBean) {
        data = blankBean.getData();
        ArrayList<String> a=new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            a.add(data.get(i).getBankname().trim());
        }
        createPw();
        wvCitys.setViewAdapter(new AddCardActivity.AddressTextAdapter(AddCardActivity.this,a,0));
        backgroundAlpha(0.7f);
        //pw.showAsDropDown(write_ssq, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -16, getResources().getDisplayMetrics()), 20);
        pw.showAtLocation(write_ssq, Gravity.BOTTOM,0,0);
    }

    @Override
    public void IBranchBankInfoView(BranchBankBean branchBankBean) {
//        if (list==null||list.size()==0){
//            Toast.makeText(this, "该区没有此支行", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        ArrayList<String> a=new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//         a.add(list.get(i).getBANK_NAME().trim());
//        }
//        createPw2(list);
//        wvCitys2.setViewAdapter(new AddressTextAdapter(UserInfoActivity.this,a,0));
//        backgroundAlpha(0.7f);
//        pw2.showAsDropDown(write_ssq, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -16, getResources().getDisplayMetrics()), 20);

    }

    @Override
    public void ISubmitInfoBack(String backInfo) {
        Log.d("zanZQ", "ISubmitInfoBack: "+backInfo);
        tip_ddsq.setVisibility(View.GONE);
        backgroundAlpha(1f);
        try {
            if(backInfo==null||backInfo.equals("")){
                Toast.makeText(this, "信息提交失败", Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject jsonObject = new JSONObject(backInfo);
            int nResul = jsonObject.getInt("nResul");
            String sMessage = jsonObject.getString("sMessage");
            //   Toast.makeText(this, sMessage, Toast.LENGTH_SHORT).show();
            if (nResul==1){
                String status="-1";
                String message="";
                try {
                    String data1 = URLDecoder.decode(jsonObject.getString("Data"), "utf-8");
                    JSONObject data = new JSONObject(data1);
                    status =  data.getString("status");
                    message = data.getString("message");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if (status.equals("0")){
                    Toast.makeText(this, sMessage, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddCardActivity.this, RealNameActivity.class).putExtra(APPConfig.LOGIN_INFO, loginInfoBean).putExtra("list",list));
                    finish();
                }else{

                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }

            }else{
                if(sMessage.equals("未将对象引用设置到对象的实例。")){
                    sMessage="信息验证不通过";
                }
                Toast.makeText(this, sMessage, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void IUserInfoView(LoginInfoBean merchantBean) {
        if (merchantBean==null||merchantBean.getNResul()!=1){
            return;
        }
        try {
            loginInfoBean=merchantBean;
            checkState=merchantBean.getUserData().getMerchant().getCheckState();
            LoginInfoBean.UserData.BankInfoBean bankInfo = merchantBean.getUserData().getBankInfo();
            if (bankInfo!=null){
                String bankName = bankInfo.getBankName();
                if (bankName !=null&&!bankName.trim().equals("")){
                    LoginInfoBean.UserData.MerchantBean merchant = merchantBean.getUserData().getMerchant();
                    bankcode=bankInfo.getBigBankCode();
                    write_sk_name.setText( merchant.getName());
                    String reserveNumber = merchant.getReserveNumber();
                    if (reserveNumber==null){
                        reserveNumber="";
                    }
                    write_sk_phone.setText(reserveNumber);
                    write_sk_id.setText( merchant.getIDCardNo());
                    write_sk_site.setText( merchant.getDetailAddress());
                    write_bank_number.setText(bankInfo.getCardNo());
                    LoginInfoBean.UserData.BankStlnoBean bank_stlno = merchantBean.getUserData().getBank_stlno();
                    OPEN_STLNO= bank_stlno.getOPEN_STLNO();
                    write_bank_type.setText(bank_stlno.getBANK());
                    bankcode=bank_stlno.getBANK_CODE();
                    Log.d("zanZQ", "IUserInfoView: "+bankcode);
                    cityId=Integer.valueOf(merchant.getCity().trim());
                    provinceId=Integer.valueOf(merchant.getProvince().trim());
                    cityName=bank_stlno.getCITY().trim();
                    province=bank_stlno.getPROV();
                    fhname=bank_stlno.getPROV().trim()+ bank_stlno.getCITY().trim();
                    write_ssq.setText(fhname);
                    write_psw_two.setText(bank_stlno.getBANK_NAME());
                    ArrayList<String> imagelistUrl = (ArrayList<String>) merchantBean.getUserData().getImagelistUrl();
                    if (imagelistUrl!=null&&imagelistUrl.size()>0){
                        list.clear();
                        list=imagelistUrl;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void createPw(){
        View view = LayoutInflater.from(this).inflate(R.layout.edit_changeaddress_pop_layout, null);
        WheelView    wvProvince = (WheelView) view.findViewById(R.id.wv_address_province);
        wvCitys = (WheelView) view.findViewById(R.id.wv_address_city);
        wvCitys.setWheelBackground(R.color.colorWhite);
        final WheelView  wvArea = (WheelView) view. findViewById(R.id.wv_address_area);
        TextView  btnSure = (TextView) view.findViewById(R.id.btn_myinfo_sure);
        TextView   btnCancel = (TextView) view. findViewById(R.id.btn_myinfo_cancel);
        wvProvince.setVisibility(View.GONE);
        wvArea.setVisibility(View.GONE);
        //构造一个popupWindow对象，
        //1.pw的View
        //2、3表示pw的宽和高
        //4.表示pw是否具有抢焦点的能力，效果同setFocusable
        pw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        //点击外部区域时关闭popupWindow，必须设置setBackgroundDrawable才有效
        pw.setOutsideTouchable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        //设置当pw打开时点击后退按钮时关闭pw
//        pw.setFocusable(true);
        //showAsDropDown表示pw在某一个控件的下方显示
        //代码中的尺寸都为px
        //第2、3个参数表示pw在x、y轴上的偏移量
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pw.dismiss();
            }
        });
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankData = data.get(wvCitys.getCurrentItem());
                write_bank_type.setText(bankData.getBankname());
                bankcode=bankData.getBankcode();
                pw.dismiss();
            }
        });

    }
    private void createPw2(final List<BranchBankBean> list){
        View view = LayoutInflater.from(this).inflate(R.layout.edit_changeaddress_pop_layout, null);
        WheelView    wvProvince = (WheelView) view.findViewById(R.id.wv_address_province);
        wvCitys2 = (WheelView) view.findViewById(R.id.wv_address_city);
        wvCitys2.setWheelBackground(R.color.colorWhite);

        final WheelView  wvArea = (WheelView) view. findViewById(R.id.wv_address_area);
        TextView  btnSure = (TextView) view.findViewById(R.id.btn_myinfo_sure);
        TextView   btnCancel = (TextView) view. findViewById(R.id.btn_myinfo_cancel);
        wvProvince.setVisibility(View.GONE);
        wvArea.setVisibility(View.GONE);
        //构造一个popupWindow对象，
        //1.pw的View
        //2、3表示pw的宽和高
        //4.表示pw是否具有抢焦点的能力，效果同setFocusable
        pw2 = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        //点击外部区域时关闭popupWindow，必须设置setBackgroundDrawable才有效
        pw2.setOutsideTouchable(true);
        pw2.setBackgroundDrawable(new BitmapDrawable());
        pw2.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        //设置当pw打开时点击后退按钮时关闭pw
//        pw.setFocusable(true);
        //showAsDropDown表示pw在某一个控件的下方显示
        //代码中的尺寸都为px
        //第2、3个参数表示pw在x、y轴上的偏移量
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pw2.dismiss();
            }
        });
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                bean = list.get(wvArea.getCurrentItem());
//                write_psw_two.setText(bean.getBANK_NAME());
//                OPEN_STLNO=bean.getOPEN_STLNO();
//                pw2.dismiss();
            }
        });
    }

    public void image_return(View view) {
        finish();
    }

    @Override
    public void UpData(String msg) {
        if (msg!=null&&!msg.equals("")){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void RegisterGoBackMsg(String msg) {

    }

    @Override
    public void VerifyInfo(String msg) {
        if (msg!=null&&!msg.equals("")){
            try {
                JSONObject jsonObject = new JSONObject(msg);
                String sMessage = jsonObject.getString("sMessage");

                int nResul = jsonObject.getInt("nResul");
                if (nResul==1){
                    long transTyp = EncryptionHelper.getDate();
                    String transTy="1066"+transTyp+"";
                    String transKe = EncryptionHelper.md5(transTy);
               codepresenter.Add_card(new PlaceBean("1066",transKe,transTyp,
                       new PlaceBean.BankInfoBean(ID,type,bankcode,
                               OPEN_STLNO,number,cityId+"",provinceId+"",
                               add_type,loginInfoBean.getUserData().getMerchant().getName(),add_type,0)));
                }else{
                    Toast.makeText(this, ""+sMessage, Toast.LENGTH_SHORT).show();
                    tip_ddsq.setVisibility(View.GONE);
                    backgroundAlpha(1f);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            tip_ddsq.setVisibility(View.GONE);
            backgroundAlpha(1f);
        }
    }

    @Override
    public void AlterPWD(String msg) {
        tip_ddsq.setVisibility(View.GONE);
        backgroundAlpha(1f);
        if (msg!=null&&!msg.equals("")){
            try {
                JSONObject jsonObject = new JSONObject(msg);
                String sMessage = jsonObject.getString("sMessage");
                Toast.makeText(this, ""+sMessage, Toast.LENGTH_SHORT).show();
                int nResul = jsonObject.getInt("nResul");
                if (nResul==1){
               //     startActivity(new Intent(AddCardActivity.this,ZmmActivity.class).putExtra("phone",phoneNo));
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void bankCode(String msg) {
        if (msg!=null&&!msg.equals("")){
            try {
                JSONObject jsonObject = new JSONObject(msg);
                String sMessage = jsonObject.getString("sMessage");
                int nResul = jsonObject.getInt("nResul");
                Toast.makeText(this, sMessage, Toast.LENGTH_SHORT).show();
                if (nResul!=1){

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class AddressTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<String> list;

        protected AddressTextAdapter(Context context, ArrayList<String> list, int currentItem) {
            super(context, R.layout.item_birth_year, NO_RESOURCE);
            this.list = list;
            setItemTextResource(R.id.country_name);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0 1f为不透明
        getWindow().setAttributes(lp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==HandleConfig.SUCCESS&&data!=null){
            fhname = data.getStringExtra("name");
            String open_stlno = data.getStringExtra("OPEN_STLNO");
            write_psw_two.setText(fhname);
            OPEN_STLNO=open_stlno;
        }
    }
}
