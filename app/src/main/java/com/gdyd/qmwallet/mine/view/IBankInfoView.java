package com.gdyd.qmwallet.mine.view;

import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.mine.model.BlankBean;
import com.gdyd.qmwallet.mine.model.BranchBankBean;

import java.util.List;

/**
 * Created by zanzq on 2017/2/28.
 */

public interface IBankInfoView {
    void ISumBankInfoView(BlankBean blankBean);
    void IBranchBankInfoView(BranchBankBean branchBankBean);
    void ISubmitInfoBack(String backInfo);
    void IUserInfoView(LoginInfoBean merchantBean);
}
