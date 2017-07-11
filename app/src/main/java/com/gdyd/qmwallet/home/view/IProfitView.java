package com.gdyd.qmwallet.home.view;

import com.gdyd.qmwallet.home.model.ProfitBean;
import com.gdyd.qmwallet.home.model.YuDianRecordBean;

/**
 * Created by zanzq on 2017/3/7.
 */

public interface IProfitView {
    void UpDataInfo(ProfitBean profitBean);
    void getDataYuDianRecord(YuDianRecordBean yuDianRecordBean);
}
