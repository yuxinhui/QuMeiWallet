package com.gdyd.qmwallet.home.presenter;

import android.os.Handler;

import com.gdyd.qmwallet.Other.model.IPhotoData;
import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.home.model.IUpgradeData;
import com.gdyd.qmwallet.home.model.IUpgradeDataImpl;
import com.gdyd.qmwallet.home.model.MemberInfoBean;
import com.gdyd.qmwallet.home.view.IUpgradeView;

/**
 * Created by zanzq on 2017/3/6.
 */

public class UpgradePresenter {
    private Handler handler=new Handler();
    private IUpgradeView iUpgradeView;
    private IUpgradeData iUpgradeData;

    public UpgradePresenter(IUpgradeView iUpgradeView) {
        this.iUpgradeView = iUpgradeView;
        iUpgradeData=new IUpgradeDataImpl();
    }
    public void getUpgradeInfo(String merchantNo){
        iUpgradeData.getUpgradeInfo(merchantNo,new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iUpgradeView.UpgradeInfo((MemberInfoBean) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                     iUpgradeView.UpgradeInfo(null);
                    }
                });
            }
        });
    }
    public void getUpgradeInfo(){
        iUpgradeData.getUpgradeInfo(new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iUpgradeView.UpgradeInfo((MemberInfoBean) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iUpgradeView.UpgradeInfo(null);
                    }
                });
            }
        });
    }
}
