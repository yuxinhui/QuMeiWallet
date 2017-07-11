package com.gdyd.qmwallet.Other.model;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by zanzq on 2017/3/1.
 */

public interface IRegisterData {
    void getCode(String phone,String type,OnDataLoadListener onDataLoadListener);
    void getRegister(PlaceBean placeBean,OnDataLoadListener onDataLoadListener);
    void getVerify(PlaceBean placeBean,OnDataLoadListener onDataLoadListener);
    void getAlter(PlaceBean placeBean,OnDataLoadListener onDataLoadListener);
    void getBankCode(PlaceBean placeBean,OnDataLoadListener onDataLoadListener);
}
