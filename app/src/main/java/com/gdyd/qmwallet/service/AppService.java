package com.gdyd.qmwallet.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.gdyd.qmwallet.Other.view.WebViewActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.config.HandleConfig;
import com.gdyd.qmwallet.utils.AppUtils;
import com.gdyd.qmwallet.utils.Is;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * app下载服务
 * Created by Administrator on 2016/12/7.
 */

public class AppService extends Service {

    private static Notification notification;
    private static NotificationManager manager;
    private static RemoteViews views;
    private static boolean isUpdete = false;

    /**
     * 接收消息
     */
    public static final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HandleConfig.NOTIFICATION_DOWNLOAD:
                    //显示或更新通知栏信息
                    String[] array = (String[]) msg.obj;
                    // 判断速度是否超过1mb/s
                    if (array[2].equals("true")) {
                        views.setTextViewText(R.id.download_speed_company, "mb/s");
                    } else {
                        views.setTextViewText(R.id.download_speed_company, "kb/s");
                    }
                    views.setTextViewText(R.id.download_speed, array[0]);
                    views.setTextViewText(R.id.download_percent, array[1]);
                    views.setProgressBar(R.id.download_progress,
                            APPConfig.countLength, APPConfig.currentProgress, false);

                    notification.contentView = views;
                    manager.notify(200, notification);
                    break;
                case HandleConfig.NOTIFICATION_DOWNLOAD_FINISH:
                    // 提示下载完成
                    RemoteViews rv = new RemoteViews("com.gdyd.qmwallet", R.layout.notification_download_finish);
                    rv.setTextViewText(R.id.download_finish_time,
                            new SimpleDateFormat("hh:mm", Locale.getDefault())
                                    .format(new Date()));
                    notification.contentView = rv;
                    notification.contentIntent =pendingIntent;

                            manager.notify(200, notification);
                    isUpdete = false;
                    break;
            }
        }
    };
    private  static  PendingIntent pendingIntent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notification();
        final String UpdateUrl = intent.getStringExtra("UpdateUrl");
        if (Is.isNoEmpty(UpdateUrl)) {
            //if(UpdateUrl.endsWith(".apk")){
                isUpdete = true;
            //    update(UpdateUrl);
            //开启多线程下载
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AppUtils.Download(AppService.this, UpdateUrl, 5, mHandler);
                }
            }).start();

            //}else{
            //    T.showShort(AppService.this,"不能识别该版本信息");
            //}
        } else {
            Toast.makeText(this, "更新失败,获得版本信息失败", Toast.LENGTH_SHORT).show();

        }
        return START_STICKY;
    }

    /**
     * 下载并安装
     * @param UpdateUrl
     */
    public void update(String UpdateUrl) {
        Intent intent = new Intent(AppService.this, WebViewActivity.class);
       intent.putExtra("url", UpdateUrl);
        intent.  putExtra("titleStyle", "");
       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
      //  AppUtils.Download(AppService.this, UpdateUrl, 3, mHandler);
    }

    /**
     * 创建通知管理器及样式
     */
    public void notification() {
        if (manager == null) {
            manager = (NotificationManager) AppService.this
                    .getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (notification == null) {
            notification = new Notification();
        }
        notification.icon = R.drawable.logo;
        views = new RemoteViews(AppService.this.getPackageName(),
                R.layout.notification_download);
        views.setTextViewText(R.id.download_time, new SimpleDateFormat("hh:mm",
                Locale.getDefault()).format(new Date()));
        notification.contentView = views;

        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.when = System.currentTimeMillis();
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), APPConfig.APK_NAME);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        // 完成后提示打开
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pendingIntent = PendingIntent.getActivity(AppService.this,
                0, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (manager != null) {
            manager.cancel(200);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
