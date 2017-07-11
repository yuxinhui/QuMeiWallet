package com.gdyd.qmwallet.trans;

import java.io.Serializable;

/**
 * Created by zanzq on 2017/3/22.
 */

public class MsgBean implements Serializable  {

    /**
     * Data : {"AgencyOrderNo":"1703220946497322185","ChildMerchantID":0,"GoodsName":"Hello","ID":0,"IsSettleed":false,"LevelValue":null,"MerchantName":"SMC0000136675","MerchantNo":"SMC0000136675","OrderNo":"1703220946497322185","OrderState":"00000","PayTime":"2017/3/22 9:47:21","PayType":null,"RechargeMerNo":null,"RoutingTYpe":"21","TransDate":"2017/3/22 9:46:49","TransMoney":0.01,"TransType":null,"Type":"A0","TypeName":"支付宝"}
     * nResul : 1001
     * sMessage :
     */

    private Trans.DataBean.TransRecordsBean Data;
    private int nResul;
    private String sMessage;

    public Trans.DataBean.TransRecordsBean getData() {
        return Data;
    }

    public void setData(Trans.DataBean.TransRecordsBean Data) {
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

//    public static class DataBean {
//        /**
//         * AgencyOrderNo : 1703220946497322185
//         * ChildMerchantID : 0
//         * GoodsName : Hello
//         * ID : 0
//         * IsSettleed : false
//         * LevelValue : null
//         * MerchantName : SMC0000136675
//         * MerchantNo : SMC0000136675
//         * OrderNo : 1703220946497322185
//         * OrderState : 00000
//         * PayTime : 2017/3/22 9:47:21
//         * PayType : null
//         * RechargeMerNo : null
//         * RoutingTYpe : 21
//         * TransDate : 2017/3/22 9:46:49
//         * TransMoney : 0.01
//         * TransType : null
//         * Type : A0
//         * TypeName : 支付宝
//         */
//
//        private String AgencyOrderNo;
//        private int ChildMerchantID;
//        private String GoodsName;
//        private int ID;
//        private boolean IsSettleed;
//        private int LevelValue;
//        private String MerchantName;
//        private String MerchantNo;
//        private String OrderNo;
//        private String OrderState;
//        private String PayTime;
//        private String PayType;
//        private String RechargeMerNo;
//        private String RoutingTYpe;
//        private String TransDate;
//        private double TransMoney;
//        private String TransType;
//        private String Type;
//        private String TypeName;
//
//        public String getAgencyOrderNo() {
//            return AgencyOrderNo;
//        }
//
//        public void setAgencyOrderNo(String AgencyOrderNo) {
//            this.AgencyOrderNo = AgencyOrderNo;
//        }
//
//        public int getChildMerchantID() {
//            return ChildMerchantID;
//        }
//
//        public void setChildMerchantID(int ChildMerchantID) {
//            this.ChildMerchantID = ChildMerchantID;
//        }
//
//        public String getGoodsName() {
//            return GoodsName;
//        }
//
//        public void setGoodsName(String GoodsName) {
//            this.GoodsName = GoodsName;
//        }
//
//        public int getID() {
//            return ID;
//        }
//
//        public void setID(int ID) {
//            this.ID = ID;
//        }
//
//        public boolean isIsSettleed() {
//            return IsSettleed;
//        }
//
//        public void setIsSettleed(boolean IsSettleed) {
//            this.IsSettleed = IsSettleed;
//        }
//
//        public int getLevelValue() {
//            return LevelValue;
//        }
//
//        public void setLevelValue(int LevelValue) {
//            this.LevelValue = LevelValue;
//        }
//
//        public String getMerchantName() {
//            return MerchantName;
//        }
//
//        public void setMerchantName(String MerchantName) {
//            this.MerchantName = MerchantName;
//        }
//
//        public String getMerchantNo() {
//            return MerchantNo;
//        }
//
//        public void setMerchantNo(String MerchantNo) {
//            this.MerchantNo = MerchantNo;
//        }
//
//        public String getOrderNo() {
//            return OrderNo;
//        }
//
//        public void setOrderNo(String OrderNo) {
//            this.OrderNo = OrderNo;
//        }
//
//        public String getOrderState() {
//            return OrderState;
//        }
//
//        public void setOrderState(String OrderState) {
//            this.OrderState = OrderState;
//        }
//
//        public String getPayTime() {
//            return PayTime;
//        }
//
//        public void setPayTime(String PayTime) {
//            this.PayTime = PayTime;
//        }
//
//        public String getPayType() {
//            return PayType;
//        }
//
//        public void setPayType(String PayType) {
//            this.PayType = PayType;
//        }
//
//        public String getRechargeMerNo() {
//            return RechargeMerNo;
//        }
//
//        public void setRechargeMerNo(String RechargeMerNo) {
//            this.RechargeMerNo = RechargeMerNo;
//        }
//
//        public String getRoutingTYpe() {
//            return RoutingTYpe;
//        }
//
//        public void setRoutingTYpe(String RoutingTYpe) {
//            this.RoutingTYpe = RoutingTYpe;
//        }
//
//        public String getTransDate() {
//            return TransDate;
//        }
//
//        public void setTransDate(String TransDate) {
//            this.TransDate = TransDate;
//        }
//
//        public double getTransMoney() {
//            return TransMoney;
//        }
//
//        public void setTransMoney(double TransMoney) {
//            this.TransMoney = TransMoney;
//        }
//
//        public String getTransType() {
//            return TransType;
//        }
//
//        public void setTransType(String TransType) {
//            this.TransType = TransType;
//        }
//
//        public String getType() {
//            return Type;
//        }
//
//        public void setType(String Type) {
//            this.Type = Type;
//        }
//
//        public String getTypeName() {
//            return TypeName;
//        }
//
//        public void setTypeName(String TypeName) {
//            this.TypeName = TypeName;
//        }
//    }
}
