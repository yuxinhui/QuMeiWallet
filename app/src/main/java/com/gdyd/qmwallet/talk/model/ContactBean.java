package com.gdyd.qmwallet.talk.model;

import android.text.TextUtils;

import java.io.Serializable;

import me.yokeyword.indexablerv.IndexableEntity;


/**
 * Created by fx-168 on 17/7/6.
 */

public class ContactBean  implements Serializable,IndexableEntity{


    /**
     * Data : {
     "merchantList": "[{\"rowNum\":1,\"ID\":543,\"Name\":null,\"PhoneNumber\":\"13127909016\",\"LevelValue\":1,\"MerchantNo\":\"QMC0000000542\",\"Token\":null,\"HeadImage\":null,\"IsGuDong\":0}]",
     "pageCount": 44,
     "merchantCount": 44,
     "state": "00",
     "message": ""
     }
     * nResul : 1
     * sMessage :
     */

    private String Data;
    private int nResul;
    private String sMessage;

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }

    public int getNResul() {
        return nResul;
    }

    public void setNResul(int nResul) {
        this.nResul = nResul;
    }

    public String getSMessage() {
        return sMessage;
    }

    public void setSMessage(String sMessage) {
        this.sMessage = sMessage;
    }


    private String rowNum;
    private String ID;
    private String Name;
    private String PhoneNumber;
    private String LevelValue;
    private String MerchantNo;
    private String Token;
    private String HeadImage;
    private String IsGuDong;

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        if(TextUtils.isEmpty(Name)){
            return "";
        }
        return Name;
    }

    public void setName(String name) {
        if(TextUtils.isEmpty(name)){
            this.Name = "";
        }else{
            this.Name = name;
        }

    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getLevelValue() {
        return LevelValue;
    }

    public void setLevelValue(String levelValue) {
        LevelValue = levelValue;
    }

    public String getMerchantNo() {
        return MerchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        MerchantNo = merchantNo;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getHeadImage() {
        return HeadImage;
    }

    public void setHeadImage(String headImage) {
        HeadImage = headImage;
    }

    public String getIsGuDong() {
        return IsGuDong;
    }

    public void setIsGuDong(String isGuDong) {
        IsGuDong = isGuDong;
    }


    @Override
    public String getFieldIndexBy() {
        if(TextUtils.isEmpty(Name)){
            return "";
        }
        return Name;
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.Name = indexField;
    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {

    }
}
