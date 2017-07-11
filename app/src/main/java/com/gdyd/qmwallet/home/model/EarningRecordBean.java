package com.gdyd.qmwallet.home.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zanzq on 2017/3/6.
 * 提现记录
 */
public class EarningRecordBean implements Serializable{


    /**
     * Data : {
     "RecommendPostCash": "[{\"ClientID\":null,\"FailResaon\":null,\"ID\":7,\"MerchantNO\":\"SMC0000136695\",\"PayMoney\":null,\"PostDate\":\"2017-03-22 11:15:49\",\"PostMoney\":182.00,\"SettleState\":1,\"SettleTime\":null,\"SettleUserID\":null,\"StateCode\":null,\"TaxRate\":null,\"UserID\":null},{\"ClientID\":null,\"FailResaon\":null,\"ID\":6,\"MerchantNO\":\"SMC0000136695\",\"PayMoney\":null,\"PostDate\":\"2017-03-22 11:15:04\",\"PostMoney\":100.00,\"SettleState\":1,\"SettleTime\":null,\"SettleUserID\":null,\"StateCode\":null,\"TaxRate\":null,\"UserID\":null}]",
     "PageCount": 1
     }
     * nResul : 1
     * sMessage : null
     */

    private String Data;
    private int nResul;
    private String sMessage;
    private DataBean dataBean;

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

    public DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public static class DataBean implements Serializable{

    /**
     * RecommendPostCash : [{"ClientID":null,"FailResaon":null,"ID":7,"MerchantNO":"SMC0000136695","PayMoney":null,"PostDate":"2017-03-22 11:15:49","PostMoney":182.00,"SettleState":1,"SettleTime":null,"SettleUserID":null,"StateCode":null,"TaxRate":null,"UserID":null},{"ClientID":null,"FailResaon":null,"ID":6,"MerchantNO":"SMC0000136695","PayMoney":null,"PostDate":"2017-03-22 11:15:04","PostMoney":100.00,"SettleState":1,"SettleTime":null,"SettleUserID":null,"StateCode":null,"TaxRate":null,"UserID":null}]
     * PageCount : 1
     */

    private String RecommendPostCash;
    private int PageCount;
    private ArrayList<RecommendPostCashBean> list;

    public ArrayList<RecommendPostCashBean> getList() {
        return list;
    }

    public void setList(ArrayList<RecommendPostCashBean> list) {
        this.list = list;
    }

    public static class RecommendPostCashBean implements Serializable{

        /**
         * ClientID : null
         * FailResaon : null
         * ID : 7
         * MerchantNO : SMC0000136695
         * PayMoney : null
         * PostDate : 2017-03-22 11:15:49
         * PostMoney : 182.0
         * SettleState : 1
         * SettleTime : null
         * SettleUserID : null
         * StateCode : null
         * TaxRate : null
         * UserID : null
         */

        private Object ClientID;
        private Object FailResaon;
        private int ID;
        private String MerchantNO;
        private double PayMoney;
        private String PostDate;
        private double PostMoney;
        private int SettleState;
        private String SettleTime;
        private Object SettleUserID;
        private Object StateCode;
        private Object TaxRate;
        private Object UserID;

        public Object getClientID() {
            return ClientID;
        }

        public void setClientID(Object ClientID) {
            this.ClientID = ClientID;
        }

        public Object getFailResaon() {
            return FailResaon;
        }

        public void setFailResaon(Object FailResaon) {
            this.FailResaon = FailResaon;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getMerchantNO() {
            return MerchantNO;
        }

        public void setMerchantNO(String MerchantNO) {
            this.MerchantNO = MerchantNO;
        }

        public double getPayMoney() {
            return PayMoney;
        }

        public void setPayMoney(double PayMoney) {
            this.PayMoney = PayMoney;
        }

        public String getPostDate() {
            return PostDate;
        }

        public void setPostDate(String PostDate) {
            this.PostDate = PostDate;
        }

        public double getPostMoney() {
            return PostMoney;
        }

        public void setPostMoney(double PostMoney) {
            this.PostMoney = PostMoney;
        }

        public int getSettleState() {
            return SettleState;
        }

        public void setSettleState(int SettleState) {
            this.SettleState = SettleState;
        }

        public String getSettleTime() {
            return SettleTime;
        }

        public void setSettleTime(String SettleTime) {
            this.SettleTime = SettleTime;
        }

        public Object getSettleUserID() {
            return SettleUserID;
        }

        public void setSettleUserID(Object SettleUserID) {
            this.SettleUserID = SettleUserID;
        }

        public Object getStateCode() {
            return StateCode;
        }

        public void setStateCode(Object StateCode) {
            this.StateCode = StateCode;
        }

        public Object getTaxRate() {
            return TaxRate;
        }

        public void setTaxRate(Object TaxRate) {
            this.TaxRate = TaxRate;
        }

        public Object getUserID() {
            return UserID;
        }

        public void setUserID(Object UserID) {
            this.UserID = UserID;
        }
    }
    public String getRecommendPostCash() {
        return RecommendPostCash;
    }

    public void setRecommendPostCash(String RecommendPostCash) {
        this.RecommendPostCash = RecommendPostCash;
    }

    public int getPageCount() {
        return PageCount;
    }

    public void setPageCount(int PageCount) {
        this.PageCount = PageCount;
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
