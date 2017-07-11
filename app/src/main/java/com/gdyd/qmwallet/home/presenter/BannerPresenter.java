package com.gdyd.qmwallet.home.presenter;

import android.os.Handler;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.home.model.BannerBean;
import com.gdyd.qmwallet.home.model.IBannerData;
import com.gdyd.qmwallet.home.model.IBannerDataImpl;
import com.gdyd.qmwallet.home.view.IBannerView;
import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by zanzq on 2017/3/14.
 */

public class BannerPresenter {
    private Handler handler=new Handler();
    private IBannerView iBannerView;
    private IBannerData iBannerData;

    public BannerPresenter(IBannerView iBannerView) {
        this.iBannerView = iBannerView;
        iBannerData=new IBannerDataImpl();
    }
    public void getBanner(PlaceBean placeBean){
        iBannerData.getBannerData(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                      iBannerView.getBannerData((BannerBean) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
             handler.post(new Runnable() {
                 @Override
                 public void run() {
                  iBannerView.getBannerData(null);
                 }
             });
            }
        });
    }
}
