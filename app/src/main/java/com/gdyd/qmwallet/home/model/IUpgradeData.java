package com.gdyd.qmwallet.home.model;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;

/**
 * Created by zanzq on 2017/3/6.
 */

public interface IUpgradeData {
    void getUpgradeInfo(String merchantNo,OnDataLoadListener onDataLoadListener);
    void getUpgradeInfo(OnDataLoadListener onDataLoadListener);
}
