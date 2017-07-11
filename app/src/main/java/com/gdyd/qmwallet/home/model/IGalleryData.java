package com.gdyd.qmwallet.home.model;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by zanzq on 2017/4/17.
 */

public interface IGalleryData {
     void getGalleryData(PlaceBean placeBean,OnDataLoadListener onDataLoadListener);
}
