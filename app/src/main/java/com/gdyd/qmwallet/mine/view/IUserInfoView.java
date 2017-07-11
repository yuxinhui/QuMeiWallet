package com.gdyd.qmwallet.mine.view;

import com.gdyd.qmwallet.mine.model.AreaBean;
import com.gdyd.qmwallet.mine.model.BlankBean;
import com.gdyd.qmwallet.mine.model.BranchBankBean;
import com.gdyd.qmwallet.mine.model.CityBean;
import com.gdyd.qmwallet.mine.model.ProvinceBean;

import java.util.List;

/**
 * Created by zanzq on 2017/2/28.
 */

public interface IUserInfoView {
    void IProvincePlaceView(ProvinceBean provinceBean);
    void IAreaPlaceView(AreaBean areaBean);
    void ICityPlaceView(CityBean cityBean);
}
