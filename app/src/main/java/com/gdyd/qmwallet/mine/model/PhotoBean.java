package com.gdyd.qmwallet.mine.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zanzq on 2017/3/2.
 */

public class PhotoBean  implements Serializable {
    private String TransType;
    private String TransKey;
    private String ImageBuf;
    private String Num;
    private String VersionNumber;
    private int Type;
    private int ID;
    private ArrayList<String> ListImageUrl;
    private String MerchantNo;
    private String PayType;
    private int PayClass;
    private int MerchantID;
    private long TrasnTimeSpan;
    private   int AgentID;
    private String CardNo;
    private String SMSPushID;

    public PhotoBean(String transType, String transKey, long trasnTimeSpan, String SMSPushID) {
        TransType = transType;
        TransKey = transKey;
        TrasnTimeSpan = trasnTimeSpan;
        this.SMSPushID = SMSPushID;
    }

    public void setTransType(String transType) {
        TransType = transType;
    }

    public void setTransKey(String transKey) {
        TransKey = transKey;
    }

    public void setImageBuf(String imageBuf) {
        ImageBuf = imageBuf;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getVersionNumber() {
        return VersionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        VersionNumber = versionNumber;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ArrayList<String> getListImageUrl() {
        return ListImageUrl;
    }

    public void setListImageUrl(ArrayList<String> listImageUrl) {
        ListImageUrl = listImageUrl;
    }

    public String getMerchantNo() {
        return MerchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        MerchantNo = merchantNo;
    }

    public String getPayType() {
        return PayType;
    }

    public void setPayType(String payType) {
        PayType = payType;
    }

    public int getPayClass() {
        return PayClass;
    }

    public void setPayClass(int payClass) {
        PayClass = payClass;
    }

    public PhotoBean(String transType, String transKey, long trasnTimeSpan, int merchantID) {
        TransType = transType;
        TransKey = transKey;
        TrasnTimeSpan = trasnTimeSpan;
        MerchantID = merchantID;
    }

    public PhotoBean(String cardNo, int merchantID, long trasnTimeSpan, String transKey, String transType) {
        CardNo = cardNo;
        MerchantID = merchantID;
        TrasnTimeSpan = trasnTimeSpan;
        TransKey = transKey;
        TransType = transType;
    }

    public PhotoBean(String transType, String transKey, int type, String versionNumber, long trasnTimeSpan) {
        TransType = transType;
        TransKey = transKey;
        Type = type;
        VersionNumber = versionNumber;
        TrasnTimeSpan = trasnTimeSpan;
    }
    public PhotoBean(String transType, String transKey, String merchantNo, long trasnTimeSpan) {
        TransType = transType;
        TransKey = transKey;
        MerchantNo = merchantNo;
        TrasnTimeSpan = trasnTimeSpan;

    }
    public PhotoBean(String transType, String transKey, String merchantNo, long trasnTimeSpan,int agentID) {
        TransType = transType;
        TransKey = transKey;
        MerchantNo = merchantNo;
        TrasnTimeSpan = trasnTimeSpan;
        AgentID=agentID;
    }

    public PhotoBean(String transType, String transKey, String imageBuf, String num, long trasnTimeSpan,String merchantNo) {
        TransType = transType;
        TransKey = transKey;
        ImageBuf = imageBuf;
        Num = num;
        TrasnTimeSpan = trasnTimeSpan;
        MerchantNo = merchantNo;
    }

    public PhotoBean(String transType, String transKey,int ID, ArrayList<String> list, long trasnTimeSpan) {
        TransType = transType;
        TransKey = transKey;
        this.ID = ID;
        ListImageUrl = list;
        TrasnTimeSpan = trasnTimeSpan;
    }

    public String getTransType() {
        return TransType;
    }

    public String getTransKey() {
        return TransKey;
    }

    public String getImageBuf() {
        return ImageBuf;
    }

    public String getNum() {
        return Num;
    }
}
