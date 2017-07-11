package com.gdyd.qmwallet.home.presenter;

import android.os.Handler;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.home.model.IManageMentData;
import com.gdyd.qmwallet.home.model.IManageMentDataImpl;
import com.gdyd.qmwallet.home.model.MemberDetailsBean;
import com.gdyd.qmwallet.home.view.IManageMentView;
import com.gdyd.qmwallet.mine.model.CardBean;
import com.gdyd.qmwallet.mine.model.PhotoBean;
import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by zanzq on 2017/3/6.
 */

public class ManageMentPresenter {
    private Handler handler=new Handler();
    private IManageMentData iManageMentData;
    private IManageMentView iManageMentView;

    public ManageMentPresenter(IManageMentView iManageMentView) {
        this.iManageMentView = iManageMentView;
        iManageMentData=new IManageMentDataImpl();
    }
    public void  getManageMentInfo(PlaceBean placeBean){
        iManageMentData.getManageMentInfo(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iManageMentView.ManageMentInfo((MemberDetailsBean) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iManageMentView.ManageMentInfo(null);
                    }
                });
            }
        });
    }
    public void  getcardInfo(PhotoBean placeBean){
        iManageMentData.getcardInfo(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iManageMentView.cardInfo((CardBean) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iManageMentView.cardInfo(null);
                    }
                });
            }
        });
    }
    public void  setcard(PhotoBean placeBean){
        iManageMentData.setCard(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iManageMentView.setcardInfo((String) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iManageMentView.setcardInfo(null);
                    }
                });
            }
        });
    }

    public  void  deleteCard(PhotoBean placeBean){

        iManageMentData.deleteBankCard(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iManageMentView.delectBankCard((String) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iManageMentView.delectBankCard(null);
                    }
                });
            }
        });
    }
}
