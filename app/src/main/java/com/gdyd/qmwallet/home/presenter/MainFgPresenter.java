package com.gdyd.qmwallet.home.presenter;

import android.os.Handler;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.home.model.IMainFgData;
import com.gdyd.qmwallet.home.model.IMainFgDataImpl;
import com.gdyd.qmwallet.home.model.JyBean;
import com.gdyd.qmwallet.home.view.IMainFgView;

/**
 * Created by zanzq on 2017/3/7.
 */

public class MainFgPresenter {
    private Handler handler=new Handler();
    private IMainFgData iMainFgData;
    private IMainFgView iMainFgView;

    public MainFgPresenter(IMainFgView iMainFgView) {
        this.iMainFgView = iMainFgView;
        iMainFgData=new IMainFgDataImpl();
    }
    public void WXTrans(JyBean jyBean){
        iMainFgData.getMainWx(jyBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                    iMainFgView.getWXInfo((String) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                   handler.post(new Runnable() {
                       @Override
                       public void run() {
                      iMainFgView.getWXInfo(null);
                       }
                   });
            }
        });
    }
    public void ZFBTrans(JyBean jyBean){
        iMainFgData.getMainZFB(jyBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iMainFgView.getZFBInfo((String) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iMainFgView.getZFBInfo(null);
                    }
                });
            }
        });
    }
    public void KJTrans(JyBean jyBean){
        iMainFgData.getMainKJ(jyBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iMainFgView.getKJInfo((String) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iMainFgView.getKJInfo(null);
                    }
                });
            }
        });
    }
}
