package com.gdyd.qmwallet.home.presenter;

import android.os.Handler;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.home.model.EarningRecordBean;
import com.gdyd.qmwallet.home.model.IRecordData;
import com.gdyd.qmwallet.home.model.IRecordDataImpl;
import com.gdyd.qmwallet.home.view.IRecordView;
import com.gdyd.qmwallet.mine.model.PlaceBean;

import java.util.List;

/**
 * Created by zanzq on 2017/3/7.
 */

public class RecordPresenter {
    private Handler handler=new Handler();
    private IRecordData iRecordData;
    private IRecordView iRecordView;

    public RecordPresenter(IRecordView iRecordView) {
        this.iRecordView = iRecordView;
        iRecordData=new IRecordDataImpl();
    }
    public void getRecordData(PlaceBean placeBean){
        iRecordData.getRecordData(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                            iRecordView.getRecordBack((EarningRecordBean) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                  iRecordView.getRecordBack(null);
                }
            });
            }
        });
    }
}
