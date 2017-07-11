package com.gdyd.qmwallet.home.presenter;

import android.os.Handler;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.home.model.GalleryBean;
import com.gdyd.qmwallet.home.model.IGalleryData;
import com.gdyd.qmwallet.home.model.IGalleryDataImpl;
import com.gdyd.qmwallet.home.view.IGalleryView;
import com.gdyd.qmwallet.mine.model.GradeBean;
import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by zanzq on 2017/4/17.
 */

public class GalleryPresenter {
    private IGalleryView iGalleryView;
    private IGalleryData iGalleryData;
    private Handler handler=new Handler();
    public GalleryPresenter(IGalleryView iGalleryView) {
        this.iGalleryView = iGalleryView;
        iGalleryData=new IGalleryDataImpl();
    }
    public  void getGalleryInfo(PlaceBean placeBean){
        iGalleryData.getGalleryData(placeBean,new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                     iGalleryView.UpdateGalleryInfo((GalleryBean) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iGalleryView.UpdateGalleryInfo(null);
                    }
                });
            }
        });
    }
}
