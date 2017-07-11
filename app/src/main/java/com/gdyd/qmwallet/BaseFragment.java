package com.gdyd.qmwallet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.Other.view.LoginActivity;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.mine.UserInfoActivity;

/**
 * Created by ASUS on 2016/10/30.
 */

public class BaseFragment extends Fragment {
    public static  LoginInfoBean bean;
    public void checkBean() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle("提示");
        //builder.setIcon(R.drawable.tg2);
        builder.setMessage("\t\t你还未登录，请登录...");
        builder.setPositiveButton("登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                //      context.startService(new Intent(context, AppService.class).putExtra("UpdateUrl",UpdateUrl));
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();

    }
    public void checkSMRZBean() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle("尚未实名认证");
        //builder.setIcon(R.drawable.tg2);
        builder.setMessage("\t\t未完成实名认证无法使用该功能");
        builder.setPositiveButton("前往认证", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LoginInfoBean loginInfoBean = (LoginInfoBean) getActivity().getIntent().getSerializableExtra(APPConfig.LOGIN_INFO);
                startActivity(new Intent(getActivity(), UserInfoActivity.class).putExtra(APPConfig.LOGIN_INFO,loginInfoBean));
                //      context.startService(new Intent(context, AppService.class).putExtra("UpdateUrl",UpdateUrl));
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();

    }
}

