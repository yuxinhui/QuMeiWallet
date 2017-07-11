package com.gdyd.qmwallet.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.gdyd.qmwallet.MainActivity;
import com.gdyd.qmwallet.R;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import java.util.Locale;
import java.util.Set;

/**
 * Created by zanzq on 2017/3/22.
 */

public class MyService extends Service implements TextToSpeech.OnInitListener {
    private BroadcastReceiver
            receiver;
    private TextToSpeech mSpeech;
//    private SynthesizerListener mSynListener = new SynthesizerListener(){
//        //会话结束回调接口，没有错误时，error为null
//        public void onCompleted(SpeechError error) {}
//        //缓冲进度回调
//        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
//        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {}
//        //开始播放
//        public void onSpeakBegin() {}
//        //暂停播放
//        public void onSpeakPaused() {}
//        //播放进度回调
//        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
//        public void onSpeakProgress(int percent, int beginPos, int endPos) {}
//        //恢复播放回调接口
//        public void onSpeakResumed() {}
//        //会话事件回调接口
//        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {}
//    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("zanZQ", "onCreate: 服务开启！！！");
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.restart");
        filter.addAction("cn.jpush.android.intent.REGISTER");
        filter.addAction("cn.jpush.android.intent.REPORT");
        filter.addAction("cn.jpush.android.intent.PushService");
        filter.addAction("cn.jpush.android.intent.PUSH_TIME");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                Intent a = new Intent(MyService.this, MyService.class);
                Log.d("zanZQ", "onReceive: 重启服务");
                startService(a);
            }
        };
        registerReceiver(receiver, filter, "custom.permission", null);
        /**
         *创建Notification
         */
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        builder.setSmallIcon(R.drawable.logo);
//        builder.setContentTitle("前台服务");
//        builder.setContentText("这是前台服务");
//        Intent intent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity
//                (this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
//        Notification notification = builder.build();
////启动到前台
//        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("zanZQ", "onCreate: 服务开启2！！！");
        if (mSpeech != null) {
            mSpeech.stop();
            mSpeech.shutdown();
        }
        if (intent!=null){
            String type = intent.getStringExtra("type");
            if (type!=null&&type.equals("yuying")){
                final String msg = intent.getStringExtra("msg");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mSpeech = new TextToSpeech(MyService.this.getApplicationContext(), new TextToSpeech.OnInitListener() {

                            @Override
                            public void onInit(int status) {
                                // TODO Auto-generated method stub
                                if (status == TextToSpeech.SUCCESS) {
                                    int result = mSpeech.setLanguage(Locale.CHINESE);
                                    if (result == TextToSpeech.LANG_MISSING_DATA
                                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                        Log.e("lanageTag", "not use");
                                        result = mSpeech.setLanguage(Locale.CHINA);
                                        if (result == TextToSpeech.LANG_MISSING_DATA
                                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                            Log.e("lanageTag", "not use");
                                            result = mSpeech.setLanguage(Locale.ENGLISH);
                                            if (result == TextToSpeech.LANG_MISSING_DATA
                                                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                                Log.e("lanageTag", "not use");

                                            } else {
                                                mSpeech.speak(msg, TextToSpeech.QUEUE_ADD,
                                                        null);
                                            }
                                        } else {
                                            mSpeech.speak(msg, TextToSpeech.QUEUE_ADD,
                                                    null);
                                        }
                                    } else {
                                        mSpeech.speak(msg, TextToSpeech.QUEUE_ADD,
                                                null);
                                    }
                                }
                            }
                        });
                        mSpeech.setPitch(0.8f);
//1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener

//                    SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer(MyService.this.getApplicationContext(), null);
//
////2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
//                    mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
//                    mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
//                    mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
//                    mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
////设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
////保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
////如果不需要保存合成音频，注释该行代码
//                    mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
////3.开始合成
//                    mTts.startSpeaking(msg, mSynListener);

                    }
                }).start();
            }
            flags = START_STICKY;
        }


        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        Log.d("zanZQ", "onDestroy: 服务死亡！");
        super.onDestroy();
        unregisterReceiver(receiver);
        Log.d("zanZQ", "onDestroy: 服务死亡！");
        Intent intent = new Intent("android.restart");
        sendBroadcast(intent);

    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mSpeech.setLanguage(Locale.CHINA);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("lanageTag", "not use");
            } else {
//                mSpeech.speak(msg, TextToSpeech.QUEUE_FLUSH,
//                        null);
            }
        }
    }
}
