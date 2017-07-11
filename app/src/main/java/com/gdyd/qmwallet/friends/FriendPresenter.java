package com.gdyd.qmwallet.friends;

import android.os.Handler;

import com.gdyd.qmwallet.Other.model.OnDataLoadListener;
import com.gdyd.qmwallet.mine.model.PlaceBean;

/**
 * Created by zanzq on 2017/5/4.
 */

public class FriendPresenter {
    private ILodeFriendsData iLodeFriendsData;
    private ILodeFriendsView iLodeFriendsView;
    private Handler handler=new Handler();
    public FriendPresenter(ILodeFriendsView iLodeFriendsView) {
        this.iLodeFriendsView = iLodeFriendsView;
        iLodeFriendsData=new ILodeFriendsDataImpl();
    }
    public void getInfo(PlaceBean placeBean){
        iLodeFriendsData.getData(placeBean, new OnDataLoadListener() {
            @Override
            public void onLoadSuccess(final Object object) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                     iLodeFriendsView.getFriendData((FriendDataBean) object);
                    }
                });
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLodeFriendsView.getFriendData(null);
                    }
                });
            }

        });
    }
}
