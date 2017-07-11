package com.gdyd.qmwallet.utils;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by zanzq on 2017/4/25.
 */

public class Tool {
    public static String[] chnNumChar = {"零","一","二","三","四","五","六","七","八","九"};
    public static char[] chnNumChinese = {'零','一','二','三','四','五','六','七','八','九'};
    //节权位
    public static String[] chnUnitSection = {"","万","亿","万亿"};
    //权位
    public static String[] chnUnitChar = {"","十","百","千"};
    public static HashMap intList = new HashMap();
    static{
        for(int i=0;i<chnNumChar.length;i++){
            intList.put(chnNumChinese[i], i);
        }

        intList.put('十',10);
        intList.put('百',100);
        intList.put('千', 1000);
    }
    public static String getxing(int length){
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < length; i++) {
            stringBuffer.append("*");
        }
        return stringBuffer.toString();
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
