package com.gdyd.qmwallet.home.model;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by zanzq on 2017/3/7.
 */

public interface IProfitData {
    void getIProfitData(PlaceBean placeBean,OnDataLoadListener onDataLoadListener);
    void getYuDianRecord(PlaceBean placeBean,OnDataLoadListener onDataLoadListener);
}
