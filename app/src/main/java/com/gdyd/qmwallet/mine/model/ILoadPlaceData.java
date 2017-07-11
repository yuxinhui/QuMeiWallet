package com.gdyd.qmwallet.mine.model;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;

/**
 * Created by zanzq on 2017/2/28.
 */

public interface ILoadPlaceData {
    void getLoadProvinceData(OnDataLoadListener onDataLoadListener);
    void getLoadCityData(String id,OnDataLoadListener onDataLoadListener);
    void getLoadAreaData(String id,OnDataLoadListener onDataLoadListener);
}
