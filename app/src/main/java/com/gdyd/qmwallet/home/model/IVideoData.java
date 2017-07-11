package com.gdyd.qmwallet.home.model;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by zanzq on 2017/4/6.
 */

public interface IVideoData {
    void getVideoData(PlaceBean placeBean, OnDataLoadListener onDataLoadListener);
}
