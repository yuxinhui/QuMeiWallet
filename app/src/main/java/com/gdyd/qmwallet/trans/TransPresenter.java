package com.gdyd.qmwallet.trans;

import android.os.Handler;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by zanzq on 2017/3/6.
 */

public class TransPresenter {
    private ITransData iTransData;
    private ITransView iTransView;
    private Handler handler=new Handler();
    public TransPresenter(ITransView iTransView) {
        this.iTransView = iTransView;
        iTransData=new ITransDataImpl();
    }
    public void getTransData(PlaceBean placeBean){
        iTransData.getTransData(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                     iTransView.UpdateTransView((Trans) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
               handler.post(new Runnable() {
                   @Override
                   public void run() {
                     iTransView.UpdateTransView(null);
                   }
               });
            }
        });
    }
}
