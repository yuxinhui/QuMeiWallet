package com.gdyd.qmwallet.home.model;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.mine.model.PhotoBean;
import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by zanzq on 2017/3/6.
 */

public interface IManageMentData {
    void getManageMentInfo(PlaceBean placeBean, OnDataLoadListener onDataLoadListener);
    void getcardInfo(PhotoBean photoBean, OnDataLoadListener onDataLoadListener);
    void setCard(PhotoBean photoBean,OnDataLoadListener onDataLoadListener);
    void deleteBankCard(PhotoBean photoBean,OnDataLoadListener onDataLoadListener);
}
