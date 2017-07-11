package com.gdyd.qmwallet;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.List;


/**
 * Created by ASUS on 2016/10/30.
 */

public class BaseActivity extends AppCompatActivity {

    private Handler baseHandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==100){
                isFirst=true;
            }
        }
    };
    public boolean isFirst=true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onBackPressed() {
        ActivityManager activityManager=(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        runningActivity=  runningActivity.substring(runningActivity.lastIndexOf(".")+1,+runningActivity.length());
        Log.d("zan", "onBackPressed: "+runningActivity);

        if (!runningActivity.equals("MainActivity")){
            //  sendBroadcast(new Intent(PlayUtil.CLOSE_PLAYACTIVITY));
            BaseActivity.this.finish();
        }else{
            if (isFirst){
                isFirst=false;
                Toast.makeText(BaseActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                baseHandler.sendEmptyMessageDelayed(100,2500);
            }else{
                BaseActivity.this.finish();
            }

        }
    }
    public  boolean checkMain(){
        ActivityManager activityManager=(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
        String runningActivity= runningTasks.get(0).topActivity.getClassName();
        int i = runningTasks.get(0).describeContents();
        String className = runningTasks.get(0).baseActivity.getClassName();
        Log.d("zanZQ", "checkMain: className:"+runningActivity+" className:"+className);
        if (className.equals(runningActivity)&&(runningActivity.equals("com.gdyd.qmwallet.trans.TransDetails")||runningActivity.equals("com.gdyd.qmwallet.home.ProfitDetailsActivity"))){
            return true;
        }else{
            return false;
        }




    }
}
