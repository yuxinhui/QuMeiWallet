package com.gdyd.qmwallet.home.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zanzq on 2017/3/6.
 */

public class MemberInfoBean implements Serializable {

    /**
     * Data : {
     "RecomMemberLevel": "[{\"FirstProfit\":0,\"ID\":1,\"LevelStandard\":1,\"LevelType\":1,\"LevelValue\":1,\"Name\":\"金牌\",\"Rate\":0,\"RateK0\":0,\"RateZ0\":0,\"SecondProfit\":0},{\"FirstProfit\":0,\"ID\":2,\"LevelStandard\":3,\"LevelType\":1,\"LevelValue\":2,\"Name\":\"钻石\",\"Rate\":0.03,\"RateK0\":0.03,\"RateZ0\":0.03,\"SecondProfit\":0},{\"FirstProfit\":0,\"ID\":3,\"LevelStandard\":5,\"LevelType\":1,\"LevelValue\":3,\"Name\":\"至尊\",\"Rate\":0.09,\"RateK0\":0.06,\"RateZ0\":0.09,\"SecondProfit\":0},{\"FirstProfit\":2,\"ID\":4,\"LevelStandard\":10,\"LevelType\":2,\"LevelValue\":4,\"Name\":\"代理\",\"Rate\":0.12,\"RateK0\":0.08,\"RateZ0\":0.12,\"SecondProfit\":1},{\"FirstProfit\":3,\"ID\":5,\"LevelStandard\":100,\"LevelType\":2,\"LevelValue\":5,\"Name\":\"合伙人\",\"Rate\":0.15,\"RateK0\":0.09,\"RateZ0\":0.15,\"SecondProfit\":2},{\"FirstProfit\":15000,\"ID\":6,\"LevelStandard\":36000,\"LevelType\":2,\"LevelValue\":6,\"Name\":\"股东\",\"Rate\":0.18,\"RateK0\":0.1,\"RateZ0\":0.18,\"SecondProfit\":5000},{\"FirstProfit\":0,\"ID\":7,\"LevelStandard\":0,\"LevelType\":1,\"LevelValue\":0,\"Name\":\"普通会员\",\"Rate\":0,\"RateK0\":0,\"RateZ0\":0,\"SecondProfit\":0}]",
     "MerchantCount": 3,
     "TransMoney": 1565.52
     }
     * nResul : 1
     * sMessage : null
     */

    private String Data;
    private int nResul;
    private String sMessage;
    private  DataBean dataBean;

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
         * RecomMemberLevel : [{"FirstProfit":0,"ID":1,"LevelStandard":1,"LevelType":1,"LevelValue":1,"Name":"金牌","Rate":0,"RateK0":0,"RateZ0":0,"SecondProfit":0},{"FirstProfit":0,"ID":2,"LevelStandard":3,"LevelType":1,"LevelValue":2,"Name":"钻石","Rate":0.03,"RateK0":0.03,"RateZ0":0.03,"SecondProfit":0},{"FirstProfit":0,"ID":3,"LevelStandard":5,"LevelType":1,"LevelValue":3,"Name":"至尊","Rate":0.09,"RateK0":0.06,"RateZ0":0.09,"SecondProfit":0},{"FirstProfit":2,"ID":4,"LevelStandard":10,"LevelType":2,"LevelValue":4,"Name":"代理","Rate":0.12,"RateK0":0.08,"RateZ0":0.12,"SecondProfit":1},{"FirstProfit":3,"ID":5,"LevelStandard":100,"LevelType":2,"LevelValue":5,"Name":"合伙人","Rate":0.15,"RateK0":0.09,"RateZ0":0.15,"SecondProfit":2},{"FirstProfit":15000,"ID":6,"LevelStandard":36000,"LevelType":2,"LevelValue":6,"Name":"股东","Rate":0.18,"RateK0":0.1,"RateZ0":0.18,"SecondProfit":5000},{"FirstProfit":0,"ID":7,"LevelStandard":0,"LevelType":1,"LevelValue":0,"Name":"普通会员","Rate":0,"RateK0":0,"RateZ0":0,"SecondProfit":0}]
         * MerchantCount : 3
         * TransMoney : 1565.52
         */

        private String RecomMemberLevel;
        private int MerchantCount;
        private double TransMoney;
        private ArrayList<RecomMemberLevelBean> list;

        public ArrayList<RecomMemberLevelBean> getList() {
            return list;
        }

        public void setList(ArrayList<RecomMemberLevelBean> list) {
            this.list = list;
        }

        public static class RecomMemberLevelBean implements Serializable{

            /**
             * FirstProfit : 0
             * ID : 1
             * LevelStandard : 1
             * LevelType : 1
             * LevelValue : 1
             * Name : 金牌
             * Rate : 0
             * RateK0 : 0
             * RateZ0 : 0
             * SecondProfit : 0
             */

            private int FirstProfit;
            private int ID;
            private int LevelStandard;
            private int LevelType;
            private int LevelValue;
            private String Name;
            private double Rate;
            private double RateK0;
            private double RateZ0;
            private double RateQ0;
            private double RateJ0;
            private int SecondProfit;
            private int SecondCount;
            private int FirstCount;

            public int getSecondCount() {
                return SecondCount;
            }

            public void setSecondCount(int secondCount) {
                SecondCount = secondCount;
            }

            public int getFirstCount() {
                return FirstCount;
            }

            public void setFirstCount(int firstCount) {
                FirstCount = firstCount;
            }

            public int getFirstProfit() {
                return FirstProfit;
            }

            public double getRateQ0() {
                return RateQ0;
            }

            public void setRateQ0(double rateQ0) {
                RateQ0 = rateQ0;
            }

            public double getRateJ0() {
                return RateJ0;
            }

            public void setRateJ0(double rateJ0) {
                RateJ0 = rateJ0;
            }

            public void setFirstProfit(int FirstProfit) {
                this.FirstProfit = FirstProfit;
            }

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public int getLevelStandard() {
                return LevelStandard;
            }

            public void setLevelStandard(int LevelStandard) {
                this.LevelStandard = LevelStandard;
            }

            public int getLevelType() {
                return LevelType;
            }

            public void setLevelType(int LevelType) {
                this.LevelType = LevelType;
            }

            public int getLevelValue() {
                return LevelValue;
            }

            public void setLevelValue(int LevelValue) {
                this.LevelValue = LevelValue;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public double getRate() {
                return Rate;
            }

            public void setRate(double Rate) {
                this.Rate = Rate;
            }

            public double getRateK0() {
                return RateK0;
            }

            public void setRateK0(double RateK0) {
                this.RateK0 = RateK0;
            }

            public double getRateZ0() {
                return RateZ0;
            }

            public void setRateZ0(double RateZ0) {
                this.RateZ0 = RateZ0;
            }

            public int getSecondProfit() {
                return SecondProfit;
            }

            public void setSecondProfit(int SecondProfit) {
                this.SecondProfit = SecondProfit;
            }
        }
        public String getRecomMemberLevel() {
            return RecomMemberLevel;
        }

        public void setRecomMemberLevel(String RecomMemberLevel) {
            this.RecomMemberLevel = RecomMemberLevel;
        }

        public int getMerchantCount() {
            return MerchantCount;
        }

        public void setMerchantCount(int MerchantCount) {
            this.MerchantCount = MerchantCount;
        }

        public double getTransMoney() {
            return TransMoney;
        }

        public void setTransMoney(double TransMoney) {
            this.TransMoney = TransMoney;
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
