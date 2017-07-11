package com.gdyd.qmwallet.Other.presenter;

import android.os.Handler;

import com.gdyd.qmwallet.Other.model.IPhotoData;
import com.gdyd.qmwallet.Other.model.IPhotoDataImpl;
import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.Other.view.IPhotoView;
import com.gdyd.qmwallet.mine.model.PhotoBean;

/**
 * Created by zanzq on 2017/3/2.
 */

public class PhotoPresenter {
    private IPhotoData iPhotoData;
    private IPhotoView iPhotoView;
    private Handler handler=new Handler();

    public PhotoPresenter(IPhotoView iPhotoView) {
        this.iPhotoView = iPhotoView;
        iPhotoData=new IPhotoDataImpl();
    }
    public void SubmitPhoto(PhotoBean photoBean, final int type){
        iPhotoData.UpPhotoData(photoBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
              handler.post(new Runnable() {
                  @Override
                  public void run() {
                  iPhotoView.UpPhoto((String) object,type);
                  }
              }) ;
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                 handler.post(new Runnable() {
                     @Override
                     public void run() {
                    iPhotoView.UpPhoto(null,0);
                     }
                 });
            }
        });
    }
    public void SubmitPhotoPath(PhotoBean photoBean){
        iPhotoData.UpPhotoPathData(photoBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iPhotoView.UpPhotoPath((String) object);
                    }
                }) ;
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iPhotoView.UpPhotoPath(null);
                    }
                });
            }
        });
    }
    public void SubmitHeandPhoto(PhotoBean photoBean,String image){
        iPhotoData.UPHeandData(photoBean,image, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iPhotoView.UpPhotoHead((String) object);
                    }
                }) ;
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iPhotoView.UpPhotoHead(null);
                    }
                });
            }
        });
    }
}
