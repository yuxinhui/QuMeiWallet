package com.gdyd.qmwallet;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.gdyd.qmwallet.friends.application.BaseApplication;
import com.gdyd.qmwallet.utils.CrashHandler;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.crashreport.CrashReport;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;
import io.rong.imkit.RongIM;

/**
 * Created by zanzq on 2017/3/17.
 */

public class App extends BaseApplication {
    public static String key;
    public static String key2;
    @Override
    public void onCreate() {
        super.onCreate();
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=58f051d8"+SpeechConstant.FORCE_LOGIN +"=true");
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        ShareSDK.initSDK(this);

        CrashReport.initCrashReport(getApplicationContext(), "f26e6ff299", true);
        key = getResources().getString(R.string.key);
        key2 = getResources().getString(R.string.key2);
        // 异常处理，不需要处理时注释掉这两句即可！


        CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
        crashHandler.init(getApplicationContext());

        RongIM.init(this);






        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }

}
