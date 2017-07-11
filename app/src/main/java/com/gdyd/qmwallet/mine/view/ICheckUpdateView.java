package com.gdyd.qmwallet.mine.view;

import com.gdyd.qmwallet.mine.model.RateBean;

/**
 * Created by zanzq on 2017/3/9.
 */

public interface ICheckUpdateView {
    void CheckUpdateBack(String a);
    void getRateInfo(RateBean rateBean);
    void checkUpdateNewNotice(String notice);
}
