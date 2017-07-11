package com.gdyd.qmwallet.Other.model;

/**
 * Created by zanzq on 2017/2/28.
 */
public interface OnDataLoadListener {
    void onLoadSuccess(Object object);
    void onLoadFailed(String errorMsg);
}

