package com.gdyd.qmwallet.Other.presenter;

import android.os.Handler;

import com.gdyd.qmwallet.Other.model.ILoginData;
import com.gdyd.qmwallet.Other.model.ILoginDataImpl;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.Other.view.ILoginView;
import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by zanzq on 2017/3/1.
 */

public class LoginPresenter {
    private ILoginView iLoginView;
    private ILoginData iLoginData;
    private Handler handler=new Handler();
    public LoginPresenter(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
        iLoginData=new ILoginDataImpl();
    }
    public void Login(PlaceBean placeBean){
        iLoginData.getLogin(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLoginView.getLoginInfo((LoginInfoBean) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLoginView.getLoginInfo(null);
                    }
                });
            }
        });
    }
    public void AlterPwd(PlaceBean placeBean){
        iLoginData.getAlterPwd(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLoginView.getAlterPwd((String) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLoginView.getAlterPwd(null);
                    }
                });
            }
        });
    }
}
