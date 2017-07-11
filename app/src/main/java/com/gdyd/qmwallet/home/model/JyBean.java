package com.gdyd.qmwallet.home.model;

/**
 * Created by zanzq on 2017/3/7.
 */

public class JyBean {
    private String money;
    private String mchtNo;
    private int payType;
    private String type;
    private int mode;
    private int business;
    private String authCode;
    private String RechargeMerNo;
    private int Level;
    private int channel;
    private String peopleName;
    private String myIDCardNo;
    private String cardNo;
    private String phoneNumber;
    private  String outTradeNo;

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getMyIDCardNo() {
        return myIDCardNo;
    }

    public void setMyIDCardNo(String myIDCardNo) {
        this.myIDCardNo = myIDCardNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public JyBean(String money, String outTradeNo, String phoneNumber, String cardNo, String myIDCardNo, String peopleName, String mchtNo) {
        this.money = money;
        this.outTradeNo = outTradeNo;
        this.phoneNumber = phoneNumber;
        this.cardNo = cardNo;
        this.myIDCardNo = myIDCardNo;
        this.peopleName = peopleName;
        this.mchtNo = mchtNo;
    }

    public String getRechargeMerNo() {
        return RechargeMerNo;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public void setRechargeMerNo(String rechargeMerNo) {
        RechargeMerNo = rechargeMerNo;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public JyBean(String money, String mchtNo, int payType, String type, int mode, int business, String authCode, String rechargeMerNo, int level) {
        this.money = money;
        this.mchtNo = mchtNo;
        this.payType = payType;
        this.type = type;
        this.mode = mode;
        this.business = business;
        this.authCode = authCode;
        RechargeMerNo = rechargeMerNo;
        Level = level;
    }


    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMchtNo() {
        return mchtNo;
    }

    public void setMchtNo(String mchtNo) {
        this.mchtNo = mchtNo;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getBusiness() {
        return business;
    }

    public void setBusiness(int business) {
        this.business = business;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public JyBean(String money, String mchtNo, int payType, String type, int mode, int business, String authCode) {

        this.money = money;
        this.mchtNo = mchtNo;
        this.payType = payType;
        this.type = type;
        this.mode = mode;
        this.business = business;
        this.authCode = authCode;
    }

    public JyBean(String money, String mchtNo, int payType, String type, int mode, int business, String authCode, int channel) {
        this.money = money;
        this.mchtNo = mchtNo;
        this.payType = payType;
        this.type = type;
        this.mode = mode;
        this.business = business;
        this.authCode = authCode;
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "JyBean{" +
                "money='" + money + '\'' +
                ", mchtNo='" + mchtNo + '\'' +
                ", payType=" + payType +
                ", type='" + type + '\'' +
                ", mode=" + mode +
                ", business=" + business +
                ", authCode='" + authCode + '\'' +
                ", RechargeMerNo='" + RechargeMerNo + '\'' +
                ", Level=" + Level +
                ", channel=" + channel +
                '}';
    }
}
