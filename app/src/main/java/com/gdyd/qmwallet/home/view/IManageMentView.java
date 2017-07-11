package com.gdyd.qmwallet.home.view;

import com.gdyd.qmwallet.home.model.MemberDetailsBean;
import com.gdyd.qmwallet.mine.model.CardBean;

/**
 * Created by zanzq on 2017/3/6.
 */

public interface IManageMentView {
    void ManageMentInfo(MemberDetailsBean info);
    void cardInfo(CardBean cardBean);
    void setcardInfo(String info);
    void delectBankCard(String info);
}
