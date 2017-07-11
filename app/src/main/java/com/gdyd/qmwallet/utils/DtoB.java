package com.gdyd.qmwallet.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zanzq on 2017/1/11.
 */

public class DtoB {
    public static Bitmap byD(Drawable db ){

        Bitmap bitmap = Bitmap.createBitmap(db.getIntrinsicWidth(), db.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        db.setBounds(0, 0, db.getIntrinsicWidth(), db.getIntrinsicHeight());
        db.draw(canvas);
        return bitmap;
    }
    public static  File saveBitmap(Context context,Bitmap bitmap,String id){
        File externalCacheDir = context.getExternalCacheDir();
        FileOutputStream fos = null;
        try {
         //   SimpleDateFormat formatter    =   new SimpleDateFormat("yyyyMMddHHmmss");
          //  Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
            // String str = formatter.format(curDate)+".png";
            String str =id+".png";
                    File  file = new File(externalCacheDir, str);
            if (file.exists()){
                file.delete();
            }
                fos = new FileOutputStream(file);
                //1.图片的格式
                //2.表示图片质量，取值为0～100，值越大，图片质量越高，但是PNG为无损压缩，如果第一个参数为PNG，则第二个参数无效
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, fos);


            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
