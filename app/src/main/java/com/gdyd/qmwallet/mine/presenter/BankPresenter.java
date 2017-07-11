package com.gdyd.qmwallet.mine.presenter;

import android.os.Handler;

import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.mine.model.BlankBean;
import com.gdyd.qmwallet.mine.model.BranchBankBean;
import com.gdyd.qmwallet.mine.model.ILoadBankInfo;
import com.gdyd.qmwallet.mine.model.ILoadBankInfoImpl;

import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.mine.model.UserInfoBean;
import com.gdyd.qmwallet.mine.view.IBankInfoView;

import java.util.List;

/**
 * Created by zanzq on 2017/2/28.
 */

public class BankPresenter {
   private IBankInfoView iBankInfoView;
    private ILoadBankInfo iLoadBankInfo;
    private Handler handler=new Handler();
    public BankPresenter(IBankInfoView iBankInfoView) {
        this.iBankInfoView = iBankInfoView;
        iLoadBankInfo=new ILoadBankInfoImpl();
    }
    public void getSumBank(){
        iLoadBankInfo.getSumBankInfo(new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iBankInfoView.ISumBankInfoView((BlankBean) object);
                    }
                })  ;
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                       iBankInfoView.ISumBankInfoView(null);
                    }
                });
            }
        });
    }

    public void getBranchBank(PlaceBean placeBean){
        iLoadBankInfo.getBranchBankInfo(placeBean,new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iBankInfoView.IBranchBankInfoView((BranchBankBean) object);
                    }
                })  ;
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iBankInfoView.IBranchBankInfoView(null);
                    }
                });
            }
        });
    }
    public void getSubmitInfo(UserInfoBean userInfoBean) {
        iLoadBankInfo.SubmitUserInfo(userInfoBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iBankInfoView.ISubmitInfoBack((String) object);
                    }
                });

            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iBankInfoView.ISubmitInfoBack(null);
                    }
                });
            }
        });
    }
    public void getUserInfo(String merchantBean){
        iLoadBankInfo.getUserInfo(merchantBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iBankInfoView.IUserInfoView((LoginInfoBean) object);
                    }
                });

            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iBankInfoView.IUserInfoView(null);
                    }
                });
            }
        });
    }

}
