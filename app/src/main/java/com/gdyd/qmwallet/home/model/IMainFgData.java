package com.gdyd.qmwallet.home.model;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;

/**
 * Created by zanzq on 2017/3/7.
 */

public interface IMainFgData {
    void getMainWx(JyBean jyBean,OnDataLoadListener onDataLoadListener);
    void getMainKJ(JyBean jyBean,OnDataLoadListener onDataLoadListener);
    void getMainZFB(JyBean jyBean,OnDataLoadListener onDataLoadListener);
}
