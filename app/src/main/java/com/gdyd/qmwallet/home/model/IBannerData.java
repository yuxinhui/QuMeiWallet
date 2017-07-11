package com.gdyd.qmwallet.home.model;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by zanzq on 2017/3/14.
 */

public interface IBannerData {
    void getBannerData(PlaceBean placeBean, OnDataLoadListener onDataLoadListener);
}
