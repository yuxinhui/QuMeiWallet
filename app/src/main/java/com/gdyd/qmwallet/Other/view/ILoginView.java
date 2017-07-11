package com.gdyd.qmwallet.Other.view;

import com.gdyd.qmwallet.Other.model.LoginInfoBean;

/**
 * Created by zanzq on 2017/3/1.
 */

public interface ILoginView {
    void getLoginInfo(LoginInfoBean loginInfoBean);
    void getAlterPwd(String a);
}
