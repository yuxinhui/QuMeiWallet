package com.gdyd.qmwallet.Other.presenter;

import android.os.Handler;

import com.gdyd.qmwallet.Other.model.IBmikeceDataImpl;
import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.Other.view.IBmikeceView;
import com.gdyd.qmwallet.mine.model.IBmikeceData;
import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by zanzq on 2017/3/6.
 */

public class BmikecePresenter {
    private Handler handler=new Handler();
    private IBmikeceData iBmikeceData;
    private IBmikeceView iBmikeceView;

    public BmikecePresenter(IBmikeceView iBmikeceView) {
        this.iBmikeceView = iBmikeceView;
        iBmikeceData=new IBmikeceDataImpl();
    }
    public void getSubmit(PlaceBean placeBean){
        iBmikeceData.getSubmitTx(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iBmikeceView.UpdataSubmit((String) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
             handler.post(new Runnable() {
                 @Override
                 public void run() {
                  iBmikeceView.UpdataSubmit(null);
                 }
             });
            }
        });
    }
    public void getProfitInfo(PlaceBean placeBean){
        iBmikeceData.getTxInfo(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                      iBmikeceView.getProfitInfo((String) object
                      );
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iBmikeceView.getProfitInfo(null);
                    }
                });
            }
        });
    }
}
