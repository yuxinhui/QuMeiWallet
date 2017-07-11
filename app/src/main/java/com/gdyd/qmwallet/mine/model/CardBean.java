package com.gdyd.qmwallet.mine.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zanzq on 2017/6/2.
 */

public class CardBean implements Serializable {

    /**
     * Data : [{"BankName":"中国建设银行股份有限公司海口绿色佳园支行","BigBankCode":"01050000","BranchBankCode":"105641018041","CardNo":"6227003172240337284","CardType":1,"City":"6410      ","Id":3,"Isdefault":1,"MerchantID":3,"PeopleName":"昝争强","Province":"6400      ","Type":1},{"BankName":"中国建设银行股份有限公司惠州响水河支行","BigBankCode":"01050000","BranchBankCode":"105595610749","CardNo":"6217003320014630084","CardType":1,"City":"5950      ","Id":6335,"Isdefault":0,"MerchantID":3,"PeopleName":"昝争强","Province":"5800      ","Type":1}]
     * nResul : 1
     * sMessage : null
     */

    private String Data;
    private int nResul;
    private String sMessage;
    private ArrayList<DataBean> dataBeen;

    public int getnResul() {
        return nResul;
    }

    public void setnResul(int nResul) {
        this.nResul = nResul;
    }

    public String getsMessage() {
        return sMessage;
    }

    public void setsMessage(String sMessage) {
        this.sMessage = sMessage;
    }

    public ArrayList<DataBean> getDataBeen() {
        return dataBeen;
    }

    public void setDataBeen(ArrayList<DataBean> dataBeen) {
        this.dataBeen = dataBeen;
    }

    public  static  class DataBean implements Serializable{

    /**
     * BankName : 中国建设银行股份有限公司海口绿色佳园支行
     * BigBankCode : 01050000
     * BranchBankCode : 105641018041
     * CardNo : 6227003172240337284
     * CardType : 1
     * City : 6410
     * Id : 3
     * Isdefault : 1
     * MerchantID : 3
     * PeopleName : 昝争强
     * Province : 6400
     * Type : 1
     */

    private String BankName;
    private String BigBankCode;
    private String BranchBankCode;
    private String CardNo;
    private int CardType;
    private String City;
    private int Id;
    private int IsDefault;
    private int MerchantID;
    private String PeopleName;
    private String Province;
    private int Type;

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String BankName) {
        this.BankName = BankName;
    }

    public String getBigBankCode() {
        return BigBankCode;
    }

    public void setBigBankCode(String BigBankCode) {
        this.BigBankCode = BigBankCode;
    }

    public String getBranchBankCode() {
        return BranchBankCode;
    }

    public void setBranchBankCode(String BranchBankCode) {
        this.BranchBankCode = BranchBankCode;
    }

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String CardNo) {
        this.CardNo = CardNo;
    }

    public int getCardType() {
        return CardType;
    }

    public void setCardType(int CardType) {
        this.CardType = CardType;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getIsDefault() {
        return IsDefault;
    }

    public void setIsDefault(int IsDefault) {
        this.IsDefault = IsDefault;
    }

    public int getMerchantID() {
        return MerchantID;
    }

    public void setMerchantID(int MerchantID) {
        this.MerchantID = MerchantID;
    }

    public String getPeopleName() {
        return PeopleName;
    }

    public void setPeopleName(String PeopleName) {
        this.PeopleName = PeopleName;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String Province) {
        this.Province = Province;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }
}
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
}
