package com.gdyd.qmwallet.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;
import android.widget.Toast;


import com.gdyd.qmwallet.Other.view.WebViewActivity;
import com.gdyd.qmwallet.config.APPConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by ASUS on 2017/2/17.
 */

public class IntentUtils {
    //获得app的外部缓存位置
    public static int typeUpdata=-1;
    public static String getAppPath(Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        if (externalCacheDir!=null){
            return externalCacheDir.getAbsolutePath().toString();
        }
        return null;
    }
    public static void tipAppUpdate(final Context context, JSONObject data){

        String UpdateUrl="";
        String VersionNumber="";
        String UpdateContent ="";
        int UpdateType=0;
        try {
            UpdateUrl = data.getString("UpdateUrl");
             UpdateType = data.getInt("UpdateType");
             VersionNumber = data.getString("VersionNumber");
            UpdateContent = data.getString("UpdateContent");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        PackageManager manager;

        PackageInfo info = null;

        manager = context.getPackageManager();

        try {

            info = manager.getPackageInfo(context.getPackageName(), 0);
            int versionCode = info.versionCode;
            String versionName = info.versionName;
            String packageName = info.packageName;
            Signature[] signatures = info.signatures;
            Log.d("zanZQ", "tipAppUpdate: "+versionCode+","+versionName+"，"+packageName);
            String substring = VersionNumber.substring(0, VersionNumber.length());
            Log.d("zanZQ", "tipAppUpdate: "+substring);
            int i = versionName.compareTo(substring);
            if (i>=0){
                typeUpdata=0;
                Toast.makeText(context, "当前版本最新", Toast.LENGTH_SHORT).show();
            }else{
                if (Is.isNoEmpty(UpdateUrl)) {
                    if (UpdateType == 1) {
                        // 强制更新
                        typeUpdata=1;
                        // context.startService(new Intent(context, AppService.class).putExtra("UpdateUrl",UpdateUrl));
                        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
                        builder.setTitle("发现新版本\n更新版本：" + VersionNumber);
                        if (Is.isNoEmpty(UpdateContent)){
                            builder.setMessage(UpdateContent);
                        }
                        final String finalUpdateUrl = UpdateUrl;
                        builder.setCancelable(false);
                        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //    context.startService(new Intent(context, AppService.class).putExtra("UpdateUrl",UpdateUrl));
                                update(finalUpdateUrl,context);
                            }
                        });

                        builder.show();
                       // update(UpdateUrl,context);
                    } else if (UpdateType == 2) {
                        // 一般更新
                        typeUpdata=2;
                        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
                        builder.setTitle("发现新版本\n更新版本：" + VersionNumber);
                        if (Is.isNoEmpty(UpdateContent)){
                            builder.setMessage(UpdateContent);
                        }
                        final String finalUpdateUrl = UpdateUrl;
                        builder.setCancelable(false);
                        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //    context.startService(new Intent(context, AppService.class).putExtra("UpdateUrl",UpdateUrl));
                                update(finalUpdateUrl,context);
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {

// TODO Auto-generated catch block

            e.printStackTrace();

        }


    }
    public  static void update(String UpdateUrl,Context context) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", UpdateUrl);
       intent.putExtra(APPConfig.TITLE, "版本更新");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       context.startActivity(intent);
        //  AppUtils.Download(AppService.this, UpdateUrl, 3, mHandler);
    }
}
