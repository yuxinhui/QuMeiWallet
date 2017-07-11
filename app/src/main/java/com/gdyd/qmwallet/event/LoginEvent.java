package com.gdyd.qmwallet.event;

/**
 * Created by fx-168 on 17/7/7.
 */

public class LoginEvent {

    private String msg;

    private String token;
    private String merchantNO;

    public String getMerchantNO() {
        return merchantNO;
    }

    public String getToken() {
        return token;
    }

    public String getMsg() {
        return msg;
    }
    public LoginEvent(String msg){
        this.msg = msg;
    }

    public LoginEvent(String token,String merchantNO){
        this.token = token;
        this.merchantNO = merchantNO;
    }

}
