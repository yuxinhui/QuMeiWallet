package com.gdyd.qmwallet.friends.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 单例土司工具类
 * @author zhouyou
 */
public class ToastUtils {
	
    public static Toast mToast;

    /**
     * 显示吐司
     * @param context
     * @param message
     */
    public static void showToast(final Context context, final String message){
        if (mToast == null){
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(message);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 显示吐司
     * @param context
     * @param messageResId
     */
    public static void showToast(final Context context, final int messageResId){
        if (mToast == null){
            mToast = Toast.makeText(context, messageResId, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(messageResId);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
}
