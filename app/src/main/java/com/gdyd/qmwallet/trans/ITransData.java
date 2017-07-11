package com.gdyd.qmwallet.trans;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by zanzq on 2017/3/6.
 */

public interface ITransData {
    void getTransData(PlaceBean placeBean, OnDataLoadListener onDataLoadListener);
}
