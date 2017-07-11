package com.gdyd.qmwallet.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseFragment;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.Other.view.WebViewActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.home.model.BannerBean;
import com.gdyd.qmwallet.home.presenter.BannerPresenter;
import com.gdyd.qmwallet.home.view.BmikeceActivity;
import com.gdyd.qmwallet.home.view.IBannerView;
import com.gdyd.qmwallet.home.view.PayActivity;
import com.gdyd.qmwallet.mine.model.PhotoBean;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.mine.model.RateBean;
import com.gdyd.qmwallet.mine.presenter.CheckPresenter;
import com.gdyd.qmwallet.mine.view.ICheckUpdateView;
import com.gdyd.qmwallet.receiver.JGPush;
import com.gdyd.qmwallet.trans.TransActivity;
import com.gdyd.qmwallet.utils.AppUtils;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.IntentUtils;
import com.gdyd.qmwallet.utils.Is;
import com.iflytek.cloud.SpeechSynthesizer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 王松 on 2016/9/18.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener,IBannerView ,ICheckUpdateView {
    private View view;
    private ViewPager viewPage;
    private List<ImageView> imageViews;
    private LinearLayout dotLayout;
    private TextView tv;
    //记录当前页面的前一个页面的position
    private int prePosition = 0;
    private boolean isRunning = true;
    private String[] titles = new String[]{"000", "111", "222", "333", "444"};
   // private int[] imgs = new int[]{R.drawable.banner};
    private TextView   home_broadcastText;
    private LinearLayout UpGrade;
    private LinearLayout searcg_trans;
    private LinearLayout profit_details;
    private LinearLayout management;
    private LinearLayout user_money;
    private LinearLayout layoutCredit;
    private LinearLayout layoutHandCar;
    private LinearLayout layoutYingXiao;
    private CheckPresenter checkPresenter=new CheckPresenter(this);
 //   private LoginInfoBean bean;
    private LinearLayout home_kj;
    private LinearLayout home_wx;
    private LinearLayout home_zfb;
    private LinearLayout pf_xyk_layout;
    private LinearLayout pa_xyk_layout;
    private LinearLayout sh_xyk_layout;
    private LinearLayout jt_xyk_layout;
    private LinearLayout xy_xyk_layout;
    private LinearLayout ms_xyk_layout;
    private LinearLayout gz_xyk_layout;
    private LinearLayout gd_xyk_layout;
    private LinearLayout zs_xyk_layout;
    private LinearLayout pf_dk_layout;
    private LinearLayout pa_dk_layout;
    private LinearLayout fd_dk_layout;
    private LinearLayout apply_card_layout;
    private LinearLayout wo_yao_study_layout;
    private LinearLayout wo_yao_loan_layout;
    private ImageView msg_img;
    private BannerPresenter presenter=new BannerPresenter(this);
    private ImageView tip_banner;
    private int levelValue;
    private LinearLayout video_study;
    private LinearLayout xyk_dk;
    private SpeechSynthesizer mTts;
    private BroadcastReceiver payecoPayBroadcastReceiver;
        @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
        }
        bean = ((LoginInfoBean) getActivity().getIntent().getSerializableExtra(APPConfig.LOGIN_INFO));
        viewPage = ((ViewPager) view.findViewById(R.id.viewpager));
        dotLayout = ((LinearLayout)view.findViewById(R.id.dot_layout));
        UpGrade = ((LinearLayout) view.findViewById(R.id.UpGrade));
        video_study = ((LinearLayout) view.findViewById(R.id.video_study));
        searcg_trans = ((LinearLayout) view.findViewById(R.id.search_trans));
        profit_details = ((LinearLayout) view.findViewById(R.id.profit_details));
        layoutCredit = ((LinearLayout) view.findViewById(R.id.layout_waterCredit));
        layoutHandCar = ((LinearLayout) view.findViewById(R.id.layout_handCar));
        layoutYingXiao =((LinearLayout) view.findViewById(R.id.layout_yingXiao));
        pf_xyk_layout = ((LinearLayout) view.findViewById(R.id.pf_xyk_layout));
        pa_xyk_layout = ((LinearLayout) view.findViewById(R.id.pa_xyk_layout));
        sh_xyk_layout = ((LinearLayout) view.findViewById(R.id.sh_xyk_layout));
        jt_xyk_layout = ((LinearLayout) view.findViewById(R.id.jt_xyk_layout));
        xy_xyk_layout = ((LinearLayout) view.findViewById(R.id.xy_xyk_layout));
        ms_xyk_layout = ((LinearLayout) view.findViewById(R.id.ms_xyk_layout));
        gz_xyk_layout = ((LinearLayout) view.findViewById(R.id.gz_xyk_layout));
        gd_xyk_layout = ((LinearLayout) view.findViewById(R.id.gd_xyk_layout));
        zs_xyk_layout = ((LinearLayout) view.findViewById(R.id.zs_xyk_layout));
        pf_dk_layout = ((LinearLayout) view.findViewById(R.id.pf_dk_layout));
        pa_dk_layout = ((LinearLayout) view.findViewById(R.id.pa_dk_layout));
        fd_dk_layout = ((LinearLayout) view.findViewById(R.id.fd_dk_layout));
            apply_card_layout=((LinearLayout) view.findViewById(R.id.layout_apply_creditcard));
            wo_yao_loan_layout=((LinearLayout) view.findViewById(R.id.layout_my_loan));
            wo_yao_study_layout =((LinearLayout) view.findViewById(R.id.layout_my_study));
        tip_banner = ((ImageView) view.findViewById(R.id.tip_banner));
        xyk_dk = ((LinearLayout) view.findViewById(R.id.xyk_dk));
            home_broadcastText=((TextView) view.findViewById(R.id.home_broadcastText));
       xyk_dk.setVisibility(View.GONE);
        video_study.setOnClickListener(this);
        pf_xyk_layout.setOnClickListener(this);
        pa_xyk_layout.setOnClickListener(this);
        sh_xyk_layout.setOnClickListener(this);
        jt_xyk_layout.setOnClickListener(this);
        xy_xyk_layout.setOnClickListener(this);
        ms_xyk_layout.setOnClickListener(this);
        gz_xyk_layout.setOnClickListener(this);
        gd_xyk_layout.setOnClickListener(this);
        zs_xyk_layout.setOnClickListener(this);
        pf_dk_layout.setOnClickListener(this);
        pa_dk_layout.setOnClickListener(this);
        fd_dk_layout.setOnClickListener(this);
            apply_card_layout.setOnClickListener(this);
            wo_yao_loan_layout.setOnClickListener(this);
            wo_yao_study_layout.setOnClickListener(this);

        home_kj = ((LinearLayout) view.findViewById(R.id.home_kj));
        home_wx = ((LinearLayout) view.findViewById(R.id.home_wx));
        home_zfb = ((LinearLayout) view.findViewById(R.id.home_zfb));
        home_kj.setOnClickListener(this);
        home_wx.setOnClickListener(this);
        home_zfb.setOnClickListener(this);
        management = ((LinearLayout) view.findViewById(R.id.management));
        user_money = ((LinearLayout) view.findViewById(R.id.user_money));
        msg_img = ((ImageView) view.findViewById(R.id.msg_img));
        msg_img.setOnClickListener(this);
        UpGrade.setOnClickListener(this);
        searcg_trans.setOnClickListener(this);
        profit_details.setOnClickListener(this);
        management.setOnClickListener(this);
        user_money.setOnClickListener(this);
        layoutCredit.setOnClickListener(this);
        layoutHandCar.setOnClickListener(this);
        layoutYingXiao.setOnClickListener(this);
        tv = ((TextView) view.findViewById(R.id.tv));
            long transType1 = EncryptionHelper.getDate();
            String transType="1057"+transType1;
            String transKey = EncryptionHelper.md5(transType);
            presenter.getBanner(new PlaceBean("1057", transKey,transType1));
            checkUpNewNotice();
          //  checkUP();

            //  初始化支付结果广播接收器
//            initPayecoPayBroadcastReceiver();
//
//            //注册支付结果广播接收器
//            registerPayecoPayBroadcastReceiver();
         //   Intent intent = new Intent(getActivity(),PayecoPluginLoadingActivity.class);
//            String a="{\n" +
//                    "    \"Amount\": \"2\",\n" +
//                    "    \"MerchOrderId\": \"1111\",\n" +
//                    "    \"MerchantId\": \"502050002446\",\n" +
//                    "    \"OrderId\": \"502017042558659765\",\n" +
//                    "    \"Sign\": \"GLdFNYa8DbFWp6/qCZtIqqETm5X4NhmzXgpq36gsNjWILXZL7gYLTY5CShAtf1iETmxKb8jMRp/iG5CcXV+gyO4nouncjxjQ/dUjwyqfjFQf+BXnZo3rUWUZEpZZjFF3rs4RoZm9NnwBJc8F8z/zfbQnQKaafPbLzco4yL0xgIE=\",\n" +
//                    "    \"TradeTime\": \"20170425115132\",\n" +
//                    "    \"Version\": \"2.0.0\"\n" +
//                    "}";
//
//            intent.putExtra("upPay.Req", a);
//            intent.putExtra("Broadcast", APPConfig.BROADCAST_PAY_END); //广播接收地址
//            intent.putExtra("Environment", "01"); // 00: 测试环境, 01: 生产环境
//            startActivity(intent);
            if (IntentUtils.typeUpdata==2){
                checkUP();
            }
        return view;
    }

    private void checkUP() {
        //   检查版本更新
        long transTyp = EncryptionHelper.getDate();
        String transTy="1022"+transTyp;
        String transKe = EncryptionHelper.md5(transTy);
        checkPresenter.getCheckUpdate(new PhotoBean("1022",transKe,2, AppUtils.getVersionName(getActivity()),transTyp));
    }

//检查新消息
    private void checkUpNewNotice(){

        long transTyp = EncryptionHelper.getDate();
        String transTy="1076"+transTyp;
        String transKe = EncryptionHelper.md5(transTy);
        checkPresenter.getCheckNewNotice(new PhotoBean("1076",transKe,transTyp,0));
    }

    /**
     * @Title registerPayecoPayBroadcastReceiver
     * @Description 注册广播接收器
     */
    private void registerPayecoPayBroadcastReceiver() {

        IntentFilter filter = new IntentFilter();
        filter.addAction(APPConfig.BROADCAST_PAY_END);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        getActivity().registerReceiver(payecoPayBroadcastReceiver, filter);
    }

    /**
     * @Title unRegisterPayecoPayBroadcastReceiver
     * @Description 注销广播接收器
     */
    private void unRegisterPayecoPayBroadcastReceiver() {

        if (payecoPayBroadcastReceiver != null) {
            getActivity().unregisterReceiver(payecoPayBroadcastReceiver);
            payecoPayBroadcastReceiver = null;
        }
    }
    //初始化支付结果广播接收器
    private void initPayecoPayBroadcastReceiver() {
        payecoPayBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                //接收易联支付插件的广播回调
                String action = intent.getAction();
                if (!APPConfig.BROADCAST_PAY_END.equals(action)) {
                    Log.e("test", "接收到广播，但与注册的名称不一致["+action+"]");
                    return ;
                }

                //商户的业务处理
                String result = intent.getExtras().getString("upPay.Rsp");
                Log.i("test", "接收到广播内容："+result);

                final String notifyParams = result;

                // 使用异步通讯
                new AsyncTask<Void, Void, String>(){
                    @Override
                    protected String doInBackground(Void... params) {
                        //用于接收通讯响应的内容
                        String respString = null;

                        //通知商户服务器
                        try {
                            JSONObject reqJsonParams = new JSONObject(notifyParams);

                            ArrayList<NameValuePair> reqParams = new ArrayList<NameValuePair>();
                            @SuppressWarnings("unchecked")
                            Iterator<String> keys = reqJsonParams.keys();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                String value = reqJsonParams.getString(key);
                                reqParams.add(new BasicNameValuePair(key, value));
                            }

                           // Log.i("test", "正在请求："+URL_PAY_NOTIFY);
                           // respString = httpComm(URL_PAY_NOTIFY, reqParams);
                        } catch (JSONException e) {
                            Log.e("test", "解析处理失败！", e);
                            e.printStackTrace();
                        } catch (Exception e) {
                            Log.e("test", "通知失败，通讯发生异常", e);
                            e.printStackTrace();
                        }
                        return respString;
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);

                        if (result == null) {
                            Log.e("test", "通知失败！");
                            return ;
                        }

                        Log.i("test", "响应数据："+result);

                        try {
                            //解析响应数据
                            JSONObject json = new JSONObject(result);

                            //校验返回结果
                            if (!json.has("RetMsg")) {
                                Toast.makeText(getActivity(), "返回数据有误:"+result, Toast.LENGTH_LONG).show();
                                Log.e("test", "返回数据有误:"+result);
                                return ;
                            }
                            Toast.makeText(getActivity(), json.getString("RetMsg"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Log.e("test", "解析处理失败！", e);
                            e.printStackTrace();
                        }

                    }
                }.execute();

                //跳转至支付结果页面
                Log.d("zanZQ", "onReceive: "+result);
            }
        };
    }
    @Override
    public void onResume() {
        super.onResume();
        if (bean!=null){
            //设置极光别名
            String phoneNumber = bean.getUserData().getMerchant().getPhoneNumber();
            Log.d("zanZQ", "onCreateView: "+phoneNumber);
            JGPush.bean=bean;
            JGPush.name=bean.getUserData().getMerchant().getName();
            JGPush.phone=phoneNumber;
            JPushInterface.setAlias(getActivity(), phoneNumber,null);

        }else{
            JPushInterface.setAlias(getActivity(), null,null);
        }
        if (!WebViewActivity.isUp){
            if (IntentUtils.typeUpdata==1){
                checkUP();
            }else  if (IntentUtils.typeUpdata==-1){
                checkUP();
            }
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
            builder.setTitle("正在更新");
            builder.setTitle("版本正在下载中,请稍后。如下载中出现问题请点击再次更新。");
            builder.setCancelable(false);
            builder.setPositiveButton("再次更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //    context.startService(new Intent(context, AppService.class).putExtra("UpdateUrl",UpdateUrl));
                    checkUP();
                }
            });

            builder.show();
        }

    }
  // 初始化轮播图
    private void initVp(final ArrayList<String> list) {
        HomeVpAdapter adapter = new HomeVpAdapter(getActivity(), list);
        viewPage.setAdapter(adapter);
        //设置预加载页数，最小值为1，默认为1
        viewPage.setOffscreenPageLimit(1);
        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //页面滑动过程中回调该方法
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //页面滑动结束时回调该方法
            @Override
            public void onPageSelected(int position) {
                position = position % list.size();
                tv.setText(titles[position]);
                dotLayout.getChildAt(prePosition).setEnabled(false);
                dotLayout.getChildAt(position+1).setEnabled(true);
                prePosition = position+1;
            }

            //页面状态发生改变时回调
            //state三种取值：
            //0 表示页面静止
            //1 表示手指在ViewPager上拖动
            //2 表示手指离开ViewPager，页面自由滑动
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPage.setCurrentItem(0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("google_lenve_fb", "run: " + Thread.currentThread().getName());
                while (isRunning) {
                    //睡眠3秒
                    //子线程更新UI
                    getActivity().runOnUiThread(new Runnable() {
                        //该方法运行在主线程
                        @Override
                        public void run() {
                                Log.d("google_lenve_fb", "run: +++++++++++++++++" + Thread.currentThread().getName());
                                viewPage.setCurrentItem(viewPage.getCurrentItem() + 1);

                        }
                    });
                    SystemClock.sleep(5000);
                }
            }
        }).start();
    }
    private void initData(int size) {
        imageViews = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            //创建一个新的ImageView
//            ImageView iv = new ImageView(getActivity());
//            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            iv.setImageResource(imgs[i]);
//            imageViews.add(iv);
            View view = new View(getActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
            lp.leftMargin = 20;
            //设置view的宽高左边距等参数
            view.setLayoutParams(lp);
            //默认情况下所有设置View的所有属性为false
            view.setEnabled(false);
            //给view设置背景选择器，enable属性为true时背景为红色，否则为白色
            view.setBackgroundResource(R.drawable.dot_bg);
            //将View添加到容器中
            dotLayout.addView(view);
        }
        //设置第一个View的enable为true，则该View  背景为红色
        dotLayout.getChildAt(0).setEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
      //  unRegisterPayecoPayBroadcastReceiver();
        isRunning = false;
    }

    @Override
    public void onClick(View v) {
        if (bean==null){
            checkBean(); //显示登录提示框
            return;
        }

        switch (v.getId()){
            case R.id.UpGrade:
                //会员升级
                if (bean.getUserData().getMerchant().getCheckState()!=2){
                    checkSMRZBean(); //显示实名认证对话框
                    return;
                }
                levelValue = bean.getUserData().getMerchant().getLevelValue();
                startActivity(new Intent(getActivity(), UpgradeActivity.class)
                        .putExtra(APPConfig.MERCHANTNO, bean.getUserData().getMerchant().getMerchantNo())
                .putExtra(APPConfig.LEVEL, levelValue));
            break;
            case R.id.management:
                //会员管理
                if (bean.getUserData().getMerchant().getCheckState()!=2){
                    checkSMRZBean();
                    return;
                }
                startActivity(new Intent(getActivity(), ManageMentActivity.class).putExtra(APPConfig.MERCHANTNO, bean.getUserData().getMerchant().getMerchantNo()));
                break;
            case R.id.search_trans:
                //查询订单
                if (bean.getUserData().getMerchant().getCheckState()!=2){
                    checkSMRZBean();
                    return;
                }
              startActivity(new Intent(getActivity(), TransActivity.class).putExtra(APPConfig.LOGIN_INFO,bean));
                break;
            case R.id.profit_details:
                //分润详情
                if (bean.getUserData().getMerchant().getCheckState()!=2){
                    checkSMRZBean();
                    return;
                }
                startActivity(new Intent(getActivity(), ProfitDetailsActivity.class).putExtra(APPConfig.LOGIN_INFO,bean));
                break;
            case R.id.user_money:
                //账户余额
                if (bean.getUserData().getMerchant().getCheckState()!=2){
                    checkSMRZBean();
                    return;
                }
                startActivity(new Intent(getActivity(), BmikeceActivity.class).putExtra(APPConfig.LOGIN_INFO,bean));
                break;
            case R.id.home_kj:
                //快捷支付
                if (bean.getUserData().getMerchant().getCheckState()!=2){
                    checkSMRZBean();
                    return;
                }
                startActivity(new Intent(getActivity(), PayActivity.class).putExtra(APPConfig.LOGIN_INFO,bean).putExtra(APPConfig.TRANS_TYPE,APPConfig.KJ));
                break;
            case R.id.home_wx:
                //微信支付
                if (bean.getUserData().getMerchant().getCheckState()!=2){
                    checkSMRZBean();
                    return;
                }
                startActivity(new Intent(getActivity(), PayActivity.class).putExtra(APPConfig.LOGIN_INFO,bean).putExtra(APPConfig.TRANS_TYPE,APPConfig.WX));
                break;
            case R.id.home_zfb:
                //支付宝支付
                if (bean.getUserData().getMerchant().getCheckState()!=2){
                    checkSMRZBean();
                    return;
                }
                  startActivity(new Intent(getActivity(), PayTypeActivity.class).putExtra(APPConfig.LOGIN_INFO,bean));
                //  startActivity(new Intent(getActivity(), PayActivity.class).putExtra(APPConfig.LOGIN_INFO,bean).putExtra(APPConfig.TRANS_TYPE,APPConfig.ZFB));
                break;
            case R.id.fd_dk_layout:
                startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.dk_feidai)).putExtra("url", UrlConfig.FEIDAI_DK));
                break;
            case R.id.pa_dk_layout:
                startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.dk_pingan)).putExtra("url", UrlConfig.PINGAN_DK));
                break;
            case R.id.pf_dk_layout:
                startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.dk_pufa)).putExtra("url", UrlConfig.PUFA_DK));
                break;
            case R.id.pf_xyk_layout:
                startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.xyk_pufa)).putExtra("url", UrlConfig.PUFA_XYK));
                break;
            case R.id.pa_xyk_layout:
                startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.xyk_pingan)).putExtra("url", UrlConfig.PINGAN_XYK));
                break;
            case R.id.jt_xyk_layout:
                startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.xyk_jiaotong)).putExtra("url", UrlConfig.JIAOTONG_XYK));
                break;
            case R.id.ms_xyk_layout:
                startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.xyk_mingsheng)).putExtra("url", UrlConfig.MINGSHENG_XYK));
                break;
            case R.id.xy_xyk_layout:
                startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.xyk_xingye)).putExtra("url", UrlConfig.XINGYE_XYK));
                break;
            case R.id.gz_xyk_layout:
                startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.xyk_guangzhou)).putExtra("url", UrlConfig.GUANGZHOU_XYK));
                break;
            case R.id.zs_xyk_layout:
                startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.xyk_zhaoshang)).putExtra("url", UrlConfig.ZHAOSHANG_XYK));
                break;
            case R.id.gd_xyk_layout:
                startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.xyk_guangda)).putExtra("url", UrlConfig.GUANGDA_XYK));
                break;
            case R.id.sh_xyk_layout:
                startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra(APPConfig.TITLE,getResources().getString(R.string.xyk_shanghai)).putExtra("url", UrlConfig.SHANGHAI_XYK));
                break;
            case R.id.video_study:
                //视频教学
                startActivity(new Intent(getActivity(),VideoListActivity.class));
              break;
            case R.id.layout_waterCredit:
                startActivity(new Intent(getActivity(),RainCreditHomeActivity.class).putExtra(APPConfig.LOGIN_INFO,bean));
                break;
            case R.id.layout_handCar:
                Toast.makeText(getActivity(), "该功能近期开发，敬请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_yingXiao:
                Toast.makeText(getActivity(), "该功能近期开发，敬请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_apply_creditcard:
                startActivity(new Intent(getActivity(),ApplyCreditCardActivity.class));
                break;
            case R.id.layout_my_loan:
                startActivity(new Intent(getActivity(),MyLoanActivity.class));
                break;
            case R.id.layout_my_study:
                Toast.makeText(getActivity(), "该功能近期开发，敬请期待", Toast.LENGTH_SHORT).show();
                break;



        }

    }

    /**
     * 图片信息返回
     * @param bannerBean
     */
    @Override
    public void getBannerData(BannerBean bannerBean) {

              if (bannerBean!=null&&bannerBean.getnResul()==1){
                  ArrayList<BannerBean.DataBean> beanArrayList = bannerBean.getBeanArrayList();
                  String broadcastMessge = ((String) bannerBean.getSMessage());
                  if (beanArrayList!=null&&beanArrayList.size()>0){
                      Log.d("zanZQ", "getBannerData: "+beanArrayList.size());
                      tip_banner.setVisibility(View.GONE);
                      ArrayList<String> list= new ArrayList<>();
                      for (int i = 0; i < beanArrayList.size(); i++) {
                          String bannerUrl = beanArrayList.get(i).getBannerUrl();
                          list.add(bannerUrl);
                      }
                      // 初始化圆点
                      initData(beanArrayList.size());
                      // 初始化轮播图
                      initVp(list);
                      if (Is.isNoEmpty(broadcastMessge)){
                          home_broadcastText.setText(broadcastMessge);
                      }

                  }

              }
    }

    @Override
    public void CheckUpdateBack(String a) {
        if (a==null){
           return;
        }
        Log.d("zanZQ", "CheckUpdateBack: "+a);
        try {
            JSONObject object = new JSONObject(a);
            int nResul = object.getInt("nResul");
            if (nResul>1){
                JSONObject data = new JSONObject(object.getString("Data"));
                if (data!=null){
                    String versionNumber = data.getString("VersionNumber");
                    if (versionNumber.equals("2.0.1")){
                        xyk_dk.setVisibility(View.VISIBLE);
                    }
                }else{
                    xyk_dk.setVisibility(View.VISIBLE);
                }

                IntentUtils.tipAppUpdate(getActivity(),data);
            }else{
                IntentUtils.typeUpdata=2;
                Toast.makeText(getActivity(), object.getString("sMessage"), Toast.LENGTH_SHORT).show();
                xyk_dk.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkUpdateNewNotice(String notice){
        if (notice==null){
            return;
        }
        Log.d("zanZQ", "CheckUpdateBack: "+notice);
        try {
            JSONObject object = new JSONObject(notice);
            int nResul = object.getInt("nResul");
            if (nResul==1){
                JSONObject data = new JSONObject(object.getString("Data"));
                if (data!=null){
                    String noticeId = data.getString("SMSPushID");
                    SharedPreferences shared = getActivity().getSharedPreferences("main",
                            Activity.MODE_PRIVATE);
                    String lastNoticeId = shared.getString("newAlertNoticeId","");
                    SharedPreferences.Editor editor = shared.edit();
                    if (!noticeId.equals(lastNoticeId) ){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
                        builder.setTitle("提示");
                        builder.setMessage(data.getString("MessContent"));
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                                });

                        builder.show();
                        editor.putString("newAlertNoticeId",noticeId);
                        editor.commit();
                    }

                }else{

                }

            }else{

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void getRateInfo(RateBean rateBean) {

    }


}
