package com.gdyd.qmwallet.home.presenter;

import android.os.Handler;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.home.model.BannerBean;
import com.gdyd.qmwallet.home.model.IyDataImpl;
import com.gdyd.qmwallet.home.model.IylData;
import com.gdyd.qmwallet.home.model.JyBean;
import com.gdyd.qmwallet.home.view.IylView;

/**
 * Created by zanzq on 2017/4/26.
 */

public class YlPresenter {
    private Handler handler=new Handler();
    private IylView iylView;
    private IylData iylData;

    public YlPresenter(IylView iylView) {
        this.iylView = iylView;
        iylData=new IyDataImpl();
    }
    public void getJY(JyBean jyBean){
        iylData.getJYinfo(jyBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iylView.backinfo((String) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iylView.backinfo(null);
                    }
                });
            }
        });
    }
}
