package com.gdyd.qmwallet.home.presenter;

import android.os.Handler;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.home.model.IProfitData;
import com.gdyd.qmwallet.home.model.IProfitDataImpl;
import com.gdyd.qmwallet.home.model.ProfitBean;
import com.gdyd.qmwallet.home.model.YuDianRecordBean;
import com.gdyd.qmwallet.home.view.IProfitView;
import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by zanzq on 2017/3/7.
 */

public class ProfitPresenter {
    private Handler handler=new Handler();
    private IProfitData iProfitData;
    private IProfitView iProfitView;

    public ProfitPresenter(IProfitView iProfitView) {
        this.iProfitView = iProfitView;
        iProfitData=new IProfitDataImpl();
    }
    public  void getProfitData(PlaceBean placeBean){
        iProfitData.getIProfitData(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iProfitView.UpDataInfo((ProfitBean) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                     iProfitView.UpDataInfo(null);
                    }
                });
            }
        });
    }

    public void getYuDianRecord(PlaceBean placeBean){
        iProfitData.getYuDianRecord(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iProfitView.getDataYuDianRecord((YuDianRecordBean) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iProfitView.getDataYuDianRecord(null);
                    }
                });
            }
        });
    }
}
