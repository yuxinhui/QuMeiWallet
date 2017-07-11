package com.gdyd.qmwallet.Other.model;

import com.gdyd.qmwallet.mine.model.PhotoBean;

/**
 * Created by zanzq on 2017/3/2.
 */

public interface IPhotoData {
    void UpPhotoData(PhotoBean photoBean,OnDataLoadListener onDataLoadListener);
    void UpPhotoPathData(PhotoBean photoBean,OnDataLoadListener onDataLoadListener);
    void UPHeandData(PhotoBean photoBean,String image,OnDataLoadListener onDataLoadListener);
}
