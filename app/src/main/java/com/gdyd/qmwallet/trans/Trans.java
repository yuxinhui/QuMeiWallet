package com.gdyd.qmwallet.trans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zanzq on 2017/3/6.
 */

public class Trans implements Serializable {

    @Override
    public String toString() {
        return "Trans{" +
                "Data='" + Data + '\'' +
                ", nResul=" + nResul +
                ", sMessage=" + sMessage +
                ", dataBean=" + dataBean +
                '}';
    }

    /**
     * Data : {"TransRecords":[{"AgencyOrderNo":null,"ChildMerchantID":0,"FailReason":null,"GoodsName":"Hello","ID":82520,"IsSettleed":false,"MerchantName":"SMD0000004122","MerchantNo":"SMD0000004122","Name":null,"OrderNo":"1703041031114095558","OrderState":"00001","PayTime":null,"PayType":1,"PhoneNumber":null,"Rate":0.38,"RoutingTYpe":"18        ","TransDate":"2017-03-04 10:31:12","TransMoney":100.00,"Type":"Z0","TypeName":"微信","routing":null},{"AgencyOrderNo":null,"ChildMerchantID":0,"FailReason":null,"GoodsName":"Hello","ID":81838,"IsSettleed":false,"MerchantName":"SMD0000004122","MerchantNo":"SMD0000004122","Name":null,"OrderNo":"1703031257388888954","OrderState":"00001","PayTime":null,"PayType":1,"PhoneNumber":null,"Rate":0.38,"RoutingTYpe":"18        ","TransDate":"2017-03-03 12:57:38","TransMoney":100.00,"Type":"Z0","TypeName":"微信","routing":null},{"AgencyOrderNo":null,"ChildMerchantID":0,"FailReason":null,"GoodsName":"Hello","ID":81837,"IsSettleed":false,"MerchantName":"SMD0000004122","MerchantNo":"SMD0000004122","Name":null,"OrderNo":"1703031257218376395","OrderState":"00001","PayTime":null,"PayType":1,"PhoneNumber":null,"Rate":0.38,"RoutingTYpe":"18        ","TransDate":"2017-03-03 12:57:21","TransMoney":100.00,"Type":"Z0","TypeName":"微信","routing":null},{"AgencyOrderNo":null,"ChildMerchantID":0,"FailReason":null,"GoodsName":"Hello","ID":79596,"IsSettleed":false,"MerchantName":"SMD0000004122","MerchantNo":"SMD0000004122","Name":null,"OrderNo":"1703010917372016969","OrderState":"00001","PayTime":null,"PayType":1,"PhoneNumber":null,"Rate":0.38,"RoutingTYpe":"18        ","TransDate":"2017-03-01 09:17:37","TransMoney":100.00,"Type":"Z0","TypeName":"微信","routing":null}],"nPageCount":1}
     * nResul : 1
     * sMessage : null
     */

    private String Data;
    private int nResul;
    private Object sMessage;
   private DataBean dataBean;

    public int getnResul() {
        return nResul;
    }

    public void setnResul(int nResul) {
        this.nResul = nResul;
    }

    public Object getsMessage() {
        return sMessage;
    }

    public void setsMessage(Object sMessage) {
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

    public Object getSMessage() {
        return sMessage;
    }

    public void setSMessage(Object sMessage) {
        this.sMessage = sMessage;
    }
    public static class DataBean implements Serializable{

        /**
         * TransRecords : [{"AgencyOrderNo":null,"ChildMerchantID":0,"FailReason":null,"GoodsName":"Hello","ID":82520,"IsSettleed":false,"MerchantName":"SMD0000004122","MerchantNo":"SMD0000004122","Name":null,"OrderNo":"1703041031114095558","OrderState":"00001","PayTime":null,"PayType":1,"PhoneNumber":null,"Rate":0.38,"RoutingTYpe":"18 ","TransDate":"2017-03-04 10:31:12","TransMoney":100,"Type":"Z0","TypeName":"微信","routing":null},{"AgencyOrderNo":null,"ChildMerchantID":0,"FailReason":null,"GoodsName":"Hello","ID":81838,"IsSettleed":false,"MerchantName":"SMD0000004122","MerchantNo":"SMD0000004122","Name":null,"OrderNo":"1703031257388888954","OrderState":"00001","PayTime":null,"PayType":1,"PhoneNumber":null,"Rate":0.38,"RoutingTYpe":"18 ","TransDate":"2017-03-03 12:57:38","TransMoney":100,"Type":"Z0","TypeName":"微信","routing":null},{"AgencyOrderNo":null,"ChildMerchantID":0,"FailReason":null,"GoodsName":"Hello","ID":81837,"IsSettleed":false,"MerchantName":"SMD0000004122","MerchantNo":"SMD0000004122","Name":null,"OrderNo":"1703031257218376395","OrderState":"00001","PayTime":null,"PayType":1,"PhoneNumber":null,"Rate":0.38,"RoutingTYpe":"18 ","TransDate":"2017-03-03 12:57:21","TransMoney":100,"Type":"Z0","TypeName":"微信","routing":null},{"AgencyOrderNo":null,"ChildMerchantID":0,"FailReason":null,"GoodsName":"Hello","ID":79596,"IsSettleed":false,"MerchantName":"SMD0000004122","MerchantNo":"SMD0000004122","Name":null,"OrderNo":"1703010917372016969","OrderState":"00001","PayTime":null,"PayType":1,"PhoneNumber":null,"Rate":0.38,"RoutingTYpe":"18 ","TransDate":"2017-03-01 09:17:37","TransMoney":100,"Type":"Z0","TypeName":"微信","routing":null}]
         * nPageCount : 1
         */

        private int nPageCount;
        private List<TransRecordsBean> TransRecords;

        public int getNPageCount() {
            return nPageCount;
        }

        public void setNPageCount(int nPageCount) {
            this.nPageCount = nPageCount;
        }

        public List<TransRecordsBean> getTransRecords() {
            return TransRecords;
        }

        public void setTransRecords(List<TransRecordsBean> TransRecords) {
            this.TransRecords = TransRecords;
        }

        public static class TransRecordsBean implements Serializable {
            /**
             * AgencyOrderNo : null
             * ChildMerchantID : 0
             * FailReason : null
             * GoodsName : Hello
             * ID : 82520
             * IsSettleed : false
             * MerchantName : SMD0000004122
             * MerchantNo : SMD0000004122
             * Name : null
             * OrderNo : 1703041031114095558
             * OrderState : 00001
             * PayTime : null
             * PayType : 1
             * PhoneNumber : null
             * Rate : 0.38
             * RoutingTYpe : 18
             * TransDate : 2017-03-04 10:31:12
             * TransMoney : 100.0
             * Type : Z0
             * TypeName : 微信
             * routing : null
             */

            private String AgencyOrderNo;
            private int ChildMerchantID;
            private String FailReason;
            private String GoodsName;
            private int ID;
            private boolean IsSettleed;
            private int LevelValue;
            private String MerchantName;
            private String MerchantNo;
            private String RechargeMerNo;
            private String Name;
            private String OrderNo;
            private String OrderState;
            private String PayTime;
            private int PayType;
            private String PhoneNumber;
            private double Rate;
            private String RoutingTYpe;
            private String TransDate;
            private double TransMoney;
            private String TransType;
            private String Type;
            private String TypeName;
            private String routing;

            public boolean isSettleed() {
                return IsSettleed;
            }

            public void setSettleed(boolean settleed) {
                IsSettleed = settleed;
            }

            public int getLevelValue() {
                return LevelValue;
            }

            public void setLevelValue(int levelValue) {
                LevelValue = levelValue;
            }

            public String getRechargeMerNo() {
                return RechargeMerNo;
            }

            public void setRechargeMerNo(String rechargeMerNo) {
                RechargeMerNo = rechargeMerNo;
            }

            public String getTransType() {
                return TransType;
            }

            public void setTransType(String transType) {
                TransType = transType;
            }

            public String getAgencyOrderNo() {
                return AgencyOrderNo;
            }

            public void setAgencyOrderNo(String AgencyOrderNo) {
                this.AgencyOrderNo = AgencyOrderNo;
            }

            public int getChildMerchantID() {
                return ChildMerchantID;
            }

            public void setChildMerchantID(int ChildMerchantID) {
                this.ChildMerchantID = ChildMerchantID;
            }

            public String getFailReason() {
                return FailReason;
            }

            public void setFailReason(String FailReason) {
                this.FailReason = FailReason;
            }

            public String getGoodsName() {
                return GoodsName;
            }

            public void setGoodsName(String GoodsName) {
                this.GoodsName = GoodsName;
            }

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public boolean isIsSettleed() {
                return IsSettleed;
            }

            public void setIsSettleed(boolean IsSettleed) {
                this.IsSettleed = IsSettleed;
            }

            public String getMerchantName() {
                return MerchantName;
            }

            public void setMerchantName(String MerchantName) {
                this.MerchantName = MerchantName;
            }

            public String getMerchantNo() {
                return MerchantNo;
            }

            public void setMerchantNo(String MerchantNo) {
                this.MerchantNo = MerchantNo;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getOrderNo() {
                return OrderNo;
            }

            public void setOrderNo(String OrderNo) {
                this.OrderNo = OrderNo;
            }

            public String getOrderState() {
                return OrderState;
            }

            public void setOrderState(String OrderState) {
                this.OrderState = OrderState;
            }

            public String getPayTime() {
                return PayTime;
            }

            public void setPayTime(String PayTime) {
                this.PayTime = PayTime;
            }

            public int getPayType() {
                return PayType;
            }

            public void setPayType(int PayType) {
                this.PayType = PayType;
            }

            public String getPhoneNumber() {
                return PhoneNumber;
            }

            public void setPhoneNumber(String PhoneNumber) {
                this.PhoneNumber = PhoneNumber;
            }

            public double getRate() {
                return Rate;
            }

            public void setRate(double Rate) {
                this.Rate = Rate;
            }

            public String getRoutingTYpe() {
                return RoutingTYpe;
            }

            public void setRoutingTYpe(String RoutingTYpe) {
                this.RoutingTYpe = RoutingTYpe;
            }

            public String getTransDate() {
                return TransDate;
            }

            public void setTransDate(String TransDate) {
                this.TransDate = TransDate;
            }

            public double getTransMoney() {
                return TransMoney;
            }

            public void setTransMoney(double TransMoney) {
                this.TransMoney = TransMoney;
            }

            public String getType() {
                return Type;
            }

            public void setType(String Type) {
                this.Type = Type;
            }

            public String getTypeName() {
                return TypeName;
            }

            public void setTypeName(String TypeName) {
                this.TypeName = TypeName;
            }

            public String getRouting() {
                return routing;
            }

            public void setRouting(String routing) {
                this.routing = routing;
            }

            @Override
            public String toString() {
                return "TransRecordsBean{" +
                        "AgencyOrderNo=" + AgencyOrderNo +
                        ", ChildMerchantID=" + ChildMerchantID +
                        ", FailReason=" + FailReason +
                        ", GoodsName='" + GoodsName + '\'' +
                        ", ID=" + ID +
                        ", IsSettleed=" + IsSettleed +
                        ", MerchantName='" + MerchantName + '\'' +
                        ", MerchantNo='" + MerchantNo + '\'' +
                        ", Name=" + Name +
                        ", OrderNo='" + OrderNo + '\'' +
                        ", OrderState='" + OrderState + '\'' +
                        ", PayTime=" + PayTime +
                        ", PayType=" + PayType +
                        ", PhoneNumber=" + PhoneNumber +
                        ", Rate=" + Rate +
                        ", RoutingTYpe='" + RoutingTYpe + '\'' +
                        ", TransDate='" + TransDate + '\'' +
                        ", TransMoney=" + TransMoney +
                        ", Type='" + Type + '\'' +
                        ", TypeName='" + TypeName + '\'' +
                        ", routing=" + routing +
                        '}';
            }
        }
    }
}
