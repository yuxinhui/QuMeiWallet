package com.gdyd.qmwallet.home.presenter;

import android.os.Handler;
import com.gdyd.qmwallet.home.model.IYuDianData;
import com.gdyd.qmwallet.home.model.IYuDianDataImpl;
import com.gdyd.qmwallet.home.model.NewYuDianBean;
import com.gdyd.qmwallet.home.view.IYuDianCreditView;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.Other.model.OnDataLoadListener;



/**
 * Created by hebei on 2017/7/4.
 */

public class YuDianPresenter {
    private Handler handler=new Handler();
    private IYuDianData iProfitData;
    private IYuDianCreditView iProfitView;

    public YuDianPresenter(IYuDianCreditView iProfitView) {
        this.iProfitView = iProfitView;
        iProfitData=new IYuDianDataImpl();
    }
    public  void getYuDianData(PlaceBean placeBean){
        iProfitData.getYuDianData(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iProfitView.getYudianNewCredit((NewYuDianBean) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iProfitView.getYudianNewCredit(null);
                    }
                });
            }
        });
    }

}
