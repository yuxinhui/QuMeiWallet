package com.gdyd.qmwallet.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseFragment;
import com.gdyd.qmwallet.IndexActivity;
import com.gdyd.qmwallet.MainActivity;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.home.ProfitDetailsActivity;
import com.gdyd.qmwallet.service.MyService;
import com.gdyd.qmwallet.trans.MsgBean;
import com.gdyd.qmwallet.trans.Trans;
import com.gdyd.qmwallet.trans.TransDetails;
import com.gdyd.qmwallet.utils.AppUtils;
import com.gdyd.qmwallet.utils.NetUtil;
import com.google.gson.Gson;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2016/12/9.
 */

public class JGPush extends BroadcastReceiver {
    private TextToSpeech mSpeech;
private Gson gson=new Gson();
    public   static  String phone;
    public  static  String name;
    public  static LoginInfoBean bean= BaseFragment.bean;
    private static Trans.DataBean.TransRecordsBean trans1;
    private static  boolean isShow=false;
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences shared = context.getSharedPreferences("main",
                Activity.MODE_PRIVATE);
        name=shared.getString("mchtName","");
        phone=shared.getString("name","");
        boolean background = AppUtils.isApplicationBroughtToBackground(context);
        if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//            if (mSpeech != null) {
//                mSpeech.stop();
//                mSpeech.shutdown();
//            }
            Log.d("zanZQ", "onReceive: QM收到通知。。。");
            Bundle bundle = intent.getExtras();
            final String result2 = bundle.getString(JPushInterface.EXTRA_ALERT);
            Log.d("zanZQ", "onReceivet通知22: "+result2);
            boolean yuyin = shared.getBoolean("yuyin", false);
            if ((result2.contains("新的订单")&&yuyin)||(result2.contains("您产生一笔")&&yuyin)){
                context.startService(new Intent(context, MyService.class).putExtra("msg",result2).putExtra("type","yuying"));
                }

//            mSpeech = new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
//
//                @Override
//                public void onInit(int status) {
//                    // TODO Auto-generated method stub
//                    if (status == TextToSpeech.SUCCESS) {
//                        int result = mSpeech.setLanguage(Locale.CHINA);
//
//                        if (result == TextToSpeech.LANG_MISSING_DATA
//                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                            Log.e("lanageTag", "not use");
//                        } else {
//                            mSpeech.speak(result2, TextToSpeech.QUEUE_FLUSH,
//                                    null);
//                        }
//                    }
//                }
//            });
//            mSpeech.setPitch(0.2f);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            //点击通知栏触发的操作}
            try {
                Bundle bundle = intent.getExtras();
                Log.d("zanZQ", "onReceive: 点击通知栏触发的操作");
                //获取通知消息  跳转消息中心
                String result = bundle.getString(JPushInterface.EXTRA_ALERT);
                Log.d("zanZQ", "onReceivet通知: "+result);
                if (result.equals("您有一笔新的订单")){
                    Intent intent2 ;
                  //  if (isShow){
                        isShow=false;
                        if (trans1!=null){
                            intent2 = new Intent(context, TransDetails.class);
                            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent2.putExtra("Trans",trans1).putExtra("name",bean.getUserData().getMerchant().getName()).putExtra("phone",phone));
                            trans1=null;

                        }else{
                            Toast.makeText(context, "你已查看过此消息", Toast.LENGTH_SHORT).show();
                   //     }
//                    }else{
//                        intent2 = new Intent(context, IndexActivity.class);
//                        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        trans1=null;
//                        context.startActivity(intent2);
//                        Toast.makeText(context, "请打开应用查看", Toast.LENGTH_SHORT).show();
                    }
                }else if (result.contains("您产生一笔")){
                        Intent intent1 = new Intent(context, ProfitDetailsActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent1.putExtra(APPConfig.LOGIN_INFO,bean));
               }else{

                    if (background){
                        Toast.makeText(context, "请打开应用查看", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent1 = new Intent(context, MainActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent1.putExtra(APPConfig.LOGIN_INFO,bean).putExtra("type",2));
                    }
               }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("zanZQ","onReceive:推送异常"+e.getMessage());
            }
            Log.d("zanZQ", "onReceive: QM点击");
        }else if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            String title = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d("zanZQ", "onReceive:QM registration_id--"+title);
        }else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            //获取自定义消息，跳转详情页

            Bundle bundle = intent.getExtras();
            String title = bundle.getString(JPushInterface.EXTRA_TITLE);
            String message =  bundle.getString(JPushInterface.EXTRA_MESSAGE);
            Log.d("zanZQ", "onReceive: QM自定义消息2--title："+title);
            Log.d("zanZQ", "onReceive: QM自定义消息2--message："+message);
            int nResul=0;
            try {
                JSONObject jsonObject = new JSONObject(message);
                nResul = jsonObject.getInt("nResul");
                if (nResul==1001){
                    MsgBean msgBean = gson.fromJson(message, MsgBean.class);
                    Intent intent1 = new Intent(context, TransDetails.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    trans1=msgBean.getData();;
                    Log.d("zanZQ", "onReceive: 在不在后台"+ background);
                    if (background){
                        isShow=false;
                    }else{
                        intent1.putExtra("trans", trans1);
                        context.startActivity(intent1.putExtra("Trans",trans1).putExtra("name",name).putExtra("phone",phone));
                        trans1=null;
                        isShow=true;
                    }
                }else if (nResul==1012){
                    String data = jsonObject.getString("Data");
                    JSONObject object = new JSONObject(data);
                    String subMerchantNo = object.getString("SubMerchantNo");
                    String subMerchantName = object.getString("SubMerchantName");
                    String recommendMoney = object.getString("RecommendMoney");
                    String transMoney = object.getString("TransMoney");

                }else{

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                Log.d("zanZQ", "onReceive:推送异常 "+message);
            }


        }else if (intent.getAction().equals("android.restart")){
            context.startService( new Intent(context, MyService.class));
            Log.d("zanZQ", "onReceive: 重启服务");
        }else if (intent.getAction().equals(intent.ACTION_BOOT_COMPLETED)){
            context.startService( new Intent(context, MyService.class));
            Log.d("zanZQ", "onReceive: 开机启动");
        }else if (intent.getAction().equals("cn.jpush.android.intent.CONNECTION")){
           if ( NetUtil.isConnectionNet(context)){
           }else{
               Toast.makeText(context, "网络已断开", Toast.LENGTH_SHORT).show();
           }
        }else{
            Log.d("zanZQ", "onReceiveQM: 其他"+intent.getAction());
        }
    }
}
