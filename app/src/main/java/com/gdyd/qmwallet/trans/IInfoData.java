package com.gdyd.qmwallet.trans;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.mine.model.PhotoBean;
import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by zanzq on 2017/3/15.
 */

public interface IInfoData {
    void getInfoData(PlaceBean placeBean, OnDataLoadListener onDataLoadListener);
    void getInfoFuture(PhotoBean placeBean, OnDataLoadListener onDataLoadListener);
}
