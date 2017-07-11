package com.gdyd.qmwallet.Other.presenter;

import android.os.Handler;

import com.gdyd.qmwallet.Other.view.IRegisterAndForgetView;
import com.gdyd.qmwallet.Other.model.IRegisterData;
import com.gdyd.qmwallet.Other.model.IRegisterDataImpl;
import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by zanzq on 2017/3/1.
 */

public class RegisterPresenter {
    private Handler handler=new Handler();
    private IRegisterData iRegisterData;
    private IRegisterAndForgetView iRegisterAndForgetView;

    public RegisterPresenter(IRegisterAndForgetView iRegisterAndForgetView) {
        this.iRegisterAndForgetView = iRegisterAndForgetView;
        iRegisterData=new IRegisterDataImpl();
    }
    public void getCode(String phone,String type){
        iRegisterData.getCode(phone,type, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iRegisterAndForgetView.UpData((String) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iRegisterAndForgetView.UpData(null);
                    }
                });
            }
        });
    }
    public void getSumbit(PlaceBean placeBean){
        iRegisterData.getRegister(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iRegisterAndForgetView.RegisterGoBackMsg((String) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iRegisterAndForgetView.RegisterGoBackMsg(null);
                    }
                });
            }
        });
    }
    public void UPcode(PlaceBean placeBean){
        iRegisterData.getVerify(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iRegisterAndForgetView.VerifyInfo((String) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iRegisterAndForgetView.VerifyInfo(null);
                    }
                });
            }
        });
    }
    public void Add_card(PlaceBean placeBean){
        iRegisterData.getAlter(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iRegisterAndForgetView.AlterPWD((String) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iRegisterAndForgetView.AlterPWD(null);
                    }
                });
            }
        });
    }
    public void Add_card_code(PlaceBean placeBean){
        iRegisterData.getBankCode(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iRegisterAndForgetView.bankCode((String) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iRegisterAndForgetView.bankCode(null);
                    }
                });
            }
        });
    }
}
