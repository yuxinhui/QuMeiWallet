package com.gdyd.qmwallet.friends;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by zanzq on 2017/5/4.
 */

public interface ILodeFriendsData {
    void getData(PlaceBean placeBean, OnDataLoadListener onDataLoadListener);
}
