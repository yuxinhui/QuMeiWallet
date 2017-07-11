package com.gdyd.qmwallet.mine.model;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;

/**
 * Created by zanzq on 2017/2/28.
 */

public interface ILoadBankInfo {
    void getSumBankInfo(OnDataLoadListener onDataLoadListener);
    void SubmitUserInfo(UserInfoBean userInfoBean,OnDataLoadListener onDataLoadListener);
    void getBranchBankInfo(PlaceBean placeBean,OnDataLoadListener onDataLoadListener);
    void getUserInfo(String merchant, OnDataLoadListener onDataLoadListener);
}
