package com.gdyd.qmwallet.trans;

import android.os.Handler;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.home.model.InfoBean;
import com.gdyd.qmwallet.mine.model.PhotoBean;
import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by zanzq on 2017/3/15.
 */

public class InfoPresenter {
    private Handler handler=new Handler();
    private IInfoData iInfoData;
    private IInfoView iInfoView;

    public InfoPresenter(IInfoView iInfoView) {
        this.iInfoView = iInfoView;
        iInfoData=new IInfoDataImpl();
    }
    public void  getInfoData(PlaceBean placeBean){
        iInfoData.getInfoData(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                   iInfoView.UpDataInfoView((InfoBean) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iInfoView.UpDataInfoView(null);
                    }
                });
            }
        });

    }
    public void  getInfoSign(PhotoBean placeBean){
        iInfoData.getInfoFuture(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iInfoView.SignInfoView((String) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iInfoView.SignInfoView(null);
                    }
                });
            }
        });
    }
}
