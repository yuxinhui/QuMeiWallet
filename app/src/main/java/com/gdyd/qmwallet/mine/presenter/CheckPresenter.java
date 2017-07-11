package com.gdyd.qmwallet.mine.presenter;

import android.os.Handler;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.mine.model.ICheckUpdate;
import com.gdyd.qmwallet.mine.model.ICheckUpdateImpl;
import com.gdyd.qmwallet.mine.model.PhotoBean;
import com.gdyd.qmwallet.mine.model.RateBean;
import com.gdyd.qmwallet.mine.view.ICheckUpdateView;

/**
 * Created by zanzq on 2017/3/9.
 */

public class CheckPresenter {
    private Handler handler=new Handler();
    private ICheckUpdate iCheckUpdate;
    private ICheckUpdateView iCheckUpdateView;

    public CheckPresenter(ICheckUpdateView iCheckUpdateView) {
        this.iCheckUpdateView = iCheckUpdateView;
        iCheckUpdate=new ICheckUpdateImpl();
    }
    public void getCheckUpdate(PhotoBean photoBean){
        iCheckUpdate.ICheckData(photoBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCheckUpdateView.CheckUpdateBack((String) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCheckUpdateView.CheckUpdateBack(null);
                    }
                });
            }
        });
    }
    public void getRateInfo(PhotoBean photoBean){
        iCheckUpdate.IRateData(photoBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCheckUpdateView.getRateInfo((RateBean) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCheckUpdateView.getRateInfo(null);
                    }
                });
            }
        });
    }

    public void getCheckNewNotice(PhotoBean photoBean){
        iCheckUpdate.ICheckNewNOticeData(photoBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCheckUpdateView.checkUpdateNewNotice((String) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
handler.post(new Runnable() {
    @Override
    public void run() {
        iCheckUpdateView.checkUpdateNewNotice(null);
    }
});
            }
        });
    }
}
