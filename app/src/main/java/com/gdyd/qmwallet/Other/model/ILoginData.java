package com.gdyd.qmwallet.Other.model;

import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by zanzq on 2017/3/1.
 */

public interface ILoginData {
    void getLogin(PlaceBean placeBean,OnDataLoadListener onDataLoadListener);
    void getAlterPwd(PlaceBean placeBean,OnDataLoadListener onDataLoadListener);
}
