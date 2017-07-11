package com.gdyd.qmwallet.mine.model;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;

/**
 * Created by zanzq on 2017/3/6.
 */

public interface IBmikeceData {
    void getSubmitTx(PlaceBean placeBean,OnDataLoadListener onDataLoadListener);
    void getTxInfo(PlaceBean placeBean,OnDataLoadListener onDataLoadListener);
}
