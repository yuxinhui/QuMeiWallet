package com.gdyd.qmwallet.home.presenter;

import android.os.Handler;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.home.model.IVideoData;
import com.gdyd.qmwallet.home.model.IVideoDataImpl;
import com.gdyd.qmwallet.home.model.VideoBean;
import com.gdyd.qmwallet.home.view.IVideoView;
import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by zanzq on 2017/4/6.
 */

public class VideoPresenter {
    private IVideoData iVideoData;
    private IVideoView iVideoView;
   private Handler handler=new Handler();
    public VideoPresenter(IVideoView iVideoView) {
        this.iVideoView = iVideoView;
        iVideoData=new IVideoDataImpl();
    }
    public void getVideoInfo(PlaceBean placeBean){
        iVideoData.getVideoData(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iVideoView.getVideoBack((VideoBean) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iVideoView.getVideoBack(null);
                    }
                });
            }
        });
    }
}
