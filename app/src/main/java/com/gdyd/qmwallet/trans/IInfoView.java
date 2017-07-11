package com.gdyd.qmwallet.trans;

import com.gdyd.qmwallet.home.model.InfoBean;

/**
 * Created by zanzq on 2017/3/15.
 */

public interface IInfoView {
    void UpDataInfoView(InfoBean infoBean);
    void SignInfoView(String info);
}
