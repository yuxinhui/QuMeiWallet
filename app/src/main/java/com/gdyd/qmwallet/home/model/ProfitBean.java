package com.gdyd.qmwallet.home.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zanzq on 2017/3/7.
 */

public class ProfitBean implements Serializable {

    /**
     * Data : {
     "RecommendProfitView": "[{\"ID\":9,\"MID\":108,\"Mer2Name\":\"线上快捷测试\",\"MerName\":\"刘奕俊\",\"MerchantNo\":\"SMC0000039915\",\"OrderNo\":\"1702231124404344393\",\"PhoneNumber\":\"13680888888\",\"PhoneNumber2\":\"13680856789\",\"PostCashState\":0,\"PostDate\":null,\"RID\":null,\"RecommendRate\":0.00,\"SettleDate\":\"2017-02-23 11:26:19\",\"SettleMoney\":13.00,\"SettleProfit\":0.01,\"TID\":75277,\"Type\":\"K0\"},{\"ID\":8,\"MID\":108,\"Mer2Name\":\"线上快捷测试\",\"MerName\":\"刘奕俊\",\"MerchantNo\":\"SMC0000039915\",\"OrderNo\":\"1702230949478418337\",\"PhoneNumber\":\"13680888888\",\"PhoneNumber2\":\"13680856789\",\"PostCashState\":0,\"PostDate\":null,\"RID\":null,\"RecommendRate\":0.00,\"SettleDate\":\"2017-02-23 09:51:29\",\"SettleMoney\":12.00,\"SettleProfit\":0.01,\"TID\":75137,\"Type\":\"K0\"},{\"ID\":5,\"MID\":108,\"Mer2Name\":\"线上快捷测试\",\"MerName\":\"刘奕俊\",\"MerchantNo\":\"SMC0000039915\",\"OrderNo\":\"1702091130354817211\",\"PhoneNumber\":\"13680888888\",\"PhoneNumber2\":\"13680856789\",\"PostCashState\":0,\"PostDate\":null,\"RID\":null,\"RecommendRate\":0.00,\"SettleDate\":\"2017-02-09 11:31:59\",\"SettleMoney\":120.00,\"SettleProfit\":0.12,\"TID\":65561,\"Type\":\"K0\"},{\"ID\":4,\"MID\":108,\"Mer2Name\":\"线上快捷测试\",\"MerName\":\"刘奕俊\",\"MerchantNo\":\"SMC0000039915\",\"OrderNo\":\"1702091059526774461\",\"PhoneNumber\":\"13680888888\",\"PhoneNumber2\":\"13680856789\",\"PostCashState\":0,\"PostDate\":null,\"RID\":null,\"RecommendRate\":0.00,\"SettleDate\":\"2017-02-09 11:01:19\",\"SettleMoney\":118.00,\"SettleProfit\":0.11,\"TID\":65545,\"Type\":\"K0\"},{\"ID\":3,\"MID\":108,\"Mer2Name\":\"线上快捷测试\",\"MerName\":\"刘奕俊\",\"MerchantNo\":\"SMC0000039915\",\"OrderNo\":\"1702090837140473400\",\"PhoneNumber\":\"13680888888\",\"PhoneNumber2\":\"13680856789\",\"PostCashState\":0,\"PostDate\":null,\"RID\":null,\"RecommendRate\":0.00,\"SettleDate\":\"2017-02-09 08:37:50\",\"SettleMoney\":10.00,\"SettleProfit\":0.01,\"TID\":65399,\"Type\":\"K0\"},{\"ID\":2,\"MID\":108,\"Mer2Name\":\"线上快捷测试\",\"MerName\":\"刘奕俊\",\"MerchantNo\":\"SMC0000039915\",\"OrderNo\":\"1702081410160486198\",\"PhoneNumber\":\"13680888888\",\"PhoneNumber2\":\"13680856789\",\"PostCashState\":0,\"PostDate\":null,\"RID\":null,\"RecommendRate\":0.00,\"SettleDate\":\"2017-02-08 14:11:20\",\"SettleMoney\":100.00,\"SettleProfit\":0.00,\"TID\":65060,\"Type\":\"K0\"},{\"ID\":1,\"MID\":108,\"Mer2Name\":\"线上快捷测试\",\"MerName\":\"刘奕俊\",\"MerchantNo\":\"SMC0000039915\",\"OrderNo\":\"1702080911502078224\",\"PhoneNumber\":\"13680888888\",\"PhoneNumber2\":\"13680856789\",\"PostCashState\":0,\"PostDate\":null,\"RID\":null,\"RecommendRate\":0.00,\"SettleDate\":\"2017-02-08 09:12:19\",\"SettleMoney\":10.00,\"SettleProfit\":0.00,\"TID\":64694,\"Type\":\"K0\"}]",
     "TransMoney": 383.00,
     "TransProfit": 0.26,
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
    public static class DataBean implements Serializable{

        /**
         * RecommendProfitView : [{"ID":9,"MID":108,"Mer2Name":"线上快捷测试","MerName":"刘奕俊","MerchantNo":"SMC0000039915","OrderNo":"1702231124404344393","PhoneNumber":"13680888888","PhoneNumber2":"13680856789","PostCashState":0,"PostDate":null,"RID":null,"RecommendRate":0.00,"SettleDate":"2017-02-23 11:26:19","SettleMoney":13.00,"SettleProfit":0.01,"TID":75277,"Type":"K0"},{"ID":8,"MID":108,"Mer2Name":"线上快捷测试","MerName":"刘奕俊","MerchantNo":"SMC0000039915","OrderNo":"1702230949478418337","PhoneNumber":"13680888888","PhoneNumber2":"13680856789","PostCashState":0,"PostDate":null,"RID":null,"RecommendRate":0.00,"SettleDate":"2017-02-23 09:51:29","SettleMoney":12.00,"SettleProfit":0.01,"TID":75137,"Type":"K0"},{"ID":5,"MID":108,"Mer2Name":"线上快捷测试","MerName":"刘奕俊","MerchantNo":"SMC0000039915","OrderNo":"1702091130354817211","PhoneNumber":"13680888888","PhoneNumber2":"13680856789","PostCashState":0,"PostDate":null,"RID":null,"RecommendRate":0.00,"SettleDate":"2017-02-09 11:31:59","SettleMoney":120.00,"SettleProfit":0.12,"TID":65561,"Type":"K0"},{"ID":4,"MID":108,"Mer2Name":"线上快捷测试","MerName":"刘奕俊","MerchantNo":"SMC0000039915","OrderNo":"1702091059526774461","PhoneNumber":"13680888888","PhoneNumber2":"13680856789","PostCashState":0,"PostDate":null,"RID":null,"RecommendRate":0.00,"SettleDate":"2017-02-09 11:01:19","SettleMoney":118.00,"SettleProfit":0.11,"TID":65545,"Type":"K0"},{"ID":3,"MID":108,"Mer2Name":"线上快捷测试","MerName":"刘奕俊","MerchantNo":"SMC0000039915","OrderNo":"1702090837140473400","PhoneNumber":"13680888888","PhoneNumber2":"13680856789","PostCashState":0,"PostDate":null,"RID":null,"RecommendRate":0.00,"SettleDate":"2017-02-09 08:37:50","SettleMoney":10.00,"SettleProfit":0.01,"TID":65399,"Type":"K0"},{"ID":2,"MID":108,"Mer2Name":"线上快捷测试","MerName":"刘奕俊","MerchantNo":"SMC0000039915","OrderNo":"1702081410160486198","PhoneNumber":"13680888888","PhoneNumber2":"13680856789","PostCashState":0,"PostDate":null,"RID":null,"RecommendRate":0.00,"SettleDate":"2017-02-08 14:11:20","SettleMoney":100.00,"SettleProfit":0.00,"TID":65060,"Type":"K0"},{"ID":1,"MID":108,"Mer2Name":"线上快捷测试","MerName":"刘奕俊","MerchantNo":"SMC0000039915","OrderNo":"1702080911502078224","PhoneNumber":"13680888888","PhoneNumber2":"13680856789","PostCashState":0,"PostDate":null,"RID":null,"RecommendRate":0.00,"SettleDate":"2017-02-08 09:12:19","SettleMoney":10.00,"SettleProfit":0.00,"TID":64694,"Type":"K0"}]
         * TransMoney : 383.0
         * TransProfit : 0.26
         * PageCount : 1
         */

        private String RecommendProfitView;
        private double TransProfit;
        private int PageCount;
        private int GroupType;
        private ArrayList<RecommendProfitViewBean > list;

        public ArrayList<RecommendProfitViewBean> getList() {
            return list;
        }

        public void setList(ArrayList<RecommendProfitViewBean> list) {
            this.list = list;
        }

        public String getRecommendProfitView() {
            return RecommendProfitView;
        }

        public void setRecommendProfitView(String RecommendProfitView) {
            this.RecommendProfitView = RecommendProfitView;
        }

        public double getTransProfit() {
            return TransProfit;
        }

        public void setTransProfit(double TransProfit) {
            this.TransProfit = TransProfit;
        }

        public int getGroupType() {
            return GroupType;
        }

        public void setGroupType(int groupType) {
            GroupType = groupType;
        }

        public int getPageCount() {
            return PageCount;
        }

        public void setPageCount(int PageCount) {
            this.PageCount = PageCount;
        }



        public static class RecommendProfitViewBean implements Serializable{

            /**
             * ID : 9
             * MID : 108
             * Mer2Name : 线上快捷测试
             * MerName : 刘奕俊
             * MerchantNo : SMC0000039915
             * OrderNo : 1702231124404344393
             * PhoneNumber : 13680888888
             * PhoneNumber2 : 13680856789
             * PostCashState : 0
             * PostDate : null
             * RID : null
             * RecommendRate : 0.0
             * SettleDate : 2017-02-23 11:26:19
             * SettleMoney : 13.0
             * SettleProfit : 0.01
             * TID : 75277
             * Type : K0
             */

            private int ID;
            private int MID;
            private String Mer2Name;
            private String MerName;
            private String MerchantNo;
            private String OrderNo;
            private String PhoneNumber;
            private String PhoneNumber2;
            private int PostCashState;
            private Object PostDate;
            private int ProfitSource;
            private Object RID;
            private double RecommendRate;
            private String SettleDate;
            private double SettleMoney;
            private double SettleProfit;
            private int TID;
            private String Type;

            public int getProfitSource() {
                return ProfitSource;
            }

            public void setProfitSource(int profitSource) {
                ProfitSource = profitSource;
            }

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public int getMID() {
                return MID;
            }

            public void setMID(int MID) {
                this.MID = MID;
            }

            public String getMer2Name() {
                return Mer2Name;
            }

            public void setMer2Name(String Mer2Name) {
                this.Mer2Name = Mer2Name;
            }

            public String getMerName() {
                return MerName;
            }

            public void setMerName(String MerName) {
                this.MerName = MerName;
            }

            public String getMerchantNo() {
                return MerchantNo;
            }

            public void setMerchantNo(String MerchantNo) {
                this.MerchantNo = MerchantNo;
            }

            public String getOrderNo() {
                return OrderNo;
            }

            public void setOrderNo(String OrderNo) {
                this.OrderNo = OrderNo;
            }

            public String getPhoneNumber() {
                return PhoneNumber;
            }

            public void setPhoneNumber(String PhoneNumber) {
                this.PhoneNumber = PhoneNumber;
            }

            public String getPhoneNumber2() {
                return PhoneNumber2;
            }

            public void setPhoneNumber2(String PhoneNumber2) {
                this.PhoneNumber2 = PhoneNumber2;
            }

            public int getPostCashState() {
                return PostCashState;
            }

            public void setPostCashState(int PostCashState) {
                this.PostCashState = PostCashState;
            }

            public Object getPostDate() {
                return PostDate;
            }

            public void setPostDate(Object PostDate) {
                this.PostDate = PostDate;
            }

            public Object getRID() {
                return RID;
            }

            public void setRID(Object RID) {
                this.RID = RID;
            }

            public double getRecommendRate() {
                return RecommendRate;
            }

            public void setRecommendRate(double RecommendRate) {
                this.RecommendRate = RecommendRate;
            }

            public String getSettleDate() {
                return SettleDate;
            }

            public void setSettleDate(String SettleDate) {
                this.SettleDate = SettleDate;
            }

            public double getSettleMoney() {
                return SettleMoney;
            }

            public void setSettleMoney(double SettleMoney) {
                this.SettleMoney = SettleMoney;
            }

            public double getSettleProfit() {
                return SettleProfit;
            }

            public void setSettleProfit(double SettleProfit) {
                this.SettleProfit = SettleProfit;
            }

            public int getTID() {
                return TID;
            }

            public void setTID(int TID) {
                this.TID = TID;
            }

            public String getType() {
                return Type;
            }

            public void setType(String Type) {
                this.Type = Type;
            }
        }
    }
}
