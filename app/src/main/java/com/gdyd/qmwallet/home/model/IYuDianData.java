package com.gdyd.qmwallet.home.model;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by hebei on 2017/7/4.
 */

public interface IYuDianData {
    public void getYuDianData(PlaceBean placeBean, OnDataLoadListener onDataLoadListener);
}
