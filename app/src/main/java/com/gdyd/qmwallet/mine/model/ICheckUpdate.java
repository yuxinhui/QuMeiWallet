package com.gdyd.qmwallet.mine.model;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;

/**
 * Created by zanzq on 2017/3/9.
 */

public interface ICheckUpdate {
    void ICheckData(PhotoBean photoBean, OnDataLoadListener onDataLoadListener);
    void IRateData(PhotoBean photoBean, OnDataLoadListener onDataLoadListener);
    void ICheckNewNOticeData(PhotoBean photoBean, OnDataLoadListener onDataLoadListener);
}
