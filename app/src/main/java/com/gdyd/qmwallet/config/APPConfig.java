package com.gdyd.qmwallet.config;

/**
 * Created by zanzq on 2017/2/24.
 */

public class APPConfig {
    public static final int MAIN_HOME=0;
    public static final int MAIN_SHARE=1;
    public static final int MAIN_TRANS=2;
    public static final int MAIN_MINE=3;
    public static final int MAIN_TALK=4;
    public static final int save_send=0;
    public static final int no_save_send=2;
    public static final int send_shoukuang=1;
    public static final int send_yingli=3;
    public static final int send_about=4;
    public static  final String TYPE="1";
    public static final int AgentID=1;
    public static  final String ModifyPwdTYPE="0";
    public static final String LOGIN_INFO="loginInfo";
    public static final String LOGIN_MACHNO="LOGIN_machNo";
    public static final String KJ="K0";
    public static final String ZFB  ="A0";
    public static final String WX="Z0";
    public static final String QQ="Q0";
    public static final String JD="J0";
    public static final String YL="Y0";
    public static final String TRANS_TYPE="TRANS_TYPE";
    public static final String TITLE="title";
    public static final String MERCHANTNO="merchantNo";
    public static final String LEVEL="Level";
    public static final String max="max";
    public static final String min="min";
    public static  final  int maxPhotopage=6;
    public static final String a="Qiao";
    public static final String zz="Qiao";
    public static  final  int UpData=25;
    public final static String BROADCAST_PAY_END="com.merchant.demo.broadcast";
 //   public static final String MERCHANT_INFO="MERCHANT_INFO";
    /**
     * 检查是否是最新版本的网络地址
     */
    public static final String CHECK_ISNEWAPK_URL = "";
    /**
     * APP名
     */
    public static final String APP_CODE = "2016110917452729144";

    /**
     * 要下载的安装包位置
     */
    public static String APK_URL = "";

    /**
     * 安装包临时文件名字
     */
    public static final String APK_NAME = "game.apk";

    /**
     * 下载时的线程数
     */
    public static final int DOWNLOAD_THREAD = 3;
    /**
     * 当前下载进度
     */
    public static int currentProgress = 0;

    /**
     * 要下载的文件总大小
     */
    public static int countLength = 0;

    /**
     * 下载好安装包是否马上安装,true为立即安装
     */
    public static boolean isInstall = true;
    /**
     * 开启监听下载后,大约每多少秒发送一次消息
     */
    public static final int LISTEN_INTERVAL_TIME = 1500;

    /**
     * 第一次打开开启功能提示
     */
    public static boolean isOpenNewVersionTip = false;

    public static final int phone=1;

    public static final int name=2;

    public static final int nameANDphone=3;
}
