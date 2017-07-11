package com.gdyd.qmwallet.mine.presenter;

import android.os.Handler;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.mine.model.AreaBean;
import com.gdyd.qmwallet.mine.model.CityBean;
import com.gdyd.qmwallet.mine.model.ILoadPlaceData;
import com.gdyd.qmwallet.mine.model.ILoadPlaceDataImpl;
import com.gdyd.qmwallet.mine.model.ProvinceBean;
import com.gdyd.qmwallet.mine.view.IUserInfoView;

/**
 * Created by zanzq on 2017/2/28.
 */

public class PwPresenter {
    private ILoadPlaceData iLoadPlaceData;
    private IUserInfoView iUserInfoView;
    private Handler handler=new Handler();
    public PwPresenter( IUserInfoView iUserInfoView) {
       iLoadPlaceData = new ILoadPlaceDataImpl();
        this.iUserInfoView = iUserInfoView;
    }
    public void SearchProvince (){
     iLoadPlaceData.getLoadProvinceData(new OnDataLoadListener() {
         @Override
         public void onLoadSuccess(final Object object) {
           handler.post(new Runnable() {
               @Override
               public void run() {
                iUserInfoView.IProvincePlaceView((ProvinceBean) object);
               }
           })  ;
         }

         @Override
         public void onLoadFailed(String errorMsg) {
               handler.post(new Runnable() {
                   @Override
                   public void run() {
                       iUserInfoView.IProvincePlaceView(null);
                   }
               });
         }
     });
    }
    public void SearchCity (String id){
        iLoadPlaceData.getLoadCityData(id,new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iUserInfoView.ICityPlaceView((CityBean) object);
                    }
                })  ;
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iUserInfoView.ICityPlaceView(null);
                    }
                });
            }
        });
    }
    public void SearchArea (String id){
        iLoadPlaceData.getLoadAreaData(id,new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iUserInfoView.IAreaPlaceView((AreaBean) object);
                    }
                })  ;
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iUserInfoView.IAreaPlaceView(null);
                    }
                });
            }
        });
    }
}
