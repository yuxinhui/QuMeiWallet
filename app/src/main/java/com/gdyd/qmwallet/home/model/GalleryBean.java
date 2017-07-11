package com.gdyd.qmwallet.home.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zanzq on 2017/4/17.
 */

public class GalleryBean  implements Serializable{

    /**
     * Data : [{"DayMaxMoney":null,"EndTime":"2017-12-31 22:00:00","ID":2,"OrderNumber":2,"PayClass":"1","PayType":"A0","Remark":"支付宝支付，T+0，365天实时到帐|额度：单笔10000.00元；单天支付无限制；","RoutingType":"17","SingleMaxMoney":10000.00,"StartTime":"2017-01-01 09:30:00"}]
     * nResul : 1
     * sMessage : null
     */

    private String Data;
    private int nResul;
    private String sMessage;
    private ArrayList<DataBean> gallerys;

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

    public ArrayList<DataBean> getGallerys() {
        return gallerys;
    }

    public void setGallerys(ArrayList<DataBean> gallerys) {
        this.gallerys = gallerys;
    }

    public static class DataBean implements Serializable {


        /**
         * Enable : 1
         * merchantPass : {"DayMaxMoney":null,"EndTime":"2017-12-31 22:00:00","ID":4,"OrderNumber":4,"PayClass":"1","PayType":"Z0","Remark":"微信支付，T+0，365天实时到帐|额度：单笔10000.00元；单天支付无限制；","RoutingType":"17","SingleMaxMoney":10000,"StartTime":"2017-01-01 09:30:00"}
         */

        private int Enable;
        private MerchantPassBean merchantPass;

        public int getEnable() {
            return Enable;
        }

        public void setEnable(int Enable) {
            this.Enable = Enable;
        }

        public MerchantPassBean getMerchantPass() {
            return merchantPass;
        }

        public void setMerchantPass(MerchantPassBean merchantPass) {
            this.merchantPass = merchantPass;
        }

        public static class MerchantPassBean  implements Serializable {
            /**
             * DayMaxMoney : null
             * EndTime : 2017-12-31 22:00:00
             * ID : 4
             * OrderNumber : 4
             * PayClass : 1
             * PayType : Z0
             * Remark : 微信支付，T+0，365天实时到帐|额度：单笔10000.00元；单天支付无限制；
             * RoutingType : 17
             * SingleMaxMoney : 10000.0
             * StartTime : 2017-01-01 09:30:00
             */

            private double DayMaxMoney;
            private String EndTime;
            private int ID;
            private int OrderNumber;
            private String PayClass;
            private String PayType;
            private String Remark;
            private String RoutingType;
            private double SingleMaxMoney;
            private String StartTime;
            private double SingleMinMoney;

            public double getSingleMinMoney() {
                return SingleMinMoney;
            }

            public void setSingleMinMoney(double singleMinMoney) {
                SingleMinMoney = singleMinMoney;
            }

            public double getDayMaxMoney() {
                return DayMaxMoney;
            }

            public void setDayMaxMoney(double DayMaxMoney) {
                this.DayMaxMoney = DayMaxMoney;
            }

            public String getEndTime() {
                return EndTime;
            }

            public void setEndTime(String EndTime) {
                this.EndTime = EndTime;
            }

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public int getOrderNumber() {
                return OrderNumber;
            }

            public void setOrderNumber(int OrderNumber) {
                this.OrderNumber = OrderNumber;
            }

            public String getPayClass() {
                return PayClass;
            }

            public void setPayClass(String PayClass) {
                this.PayClass = PayClass;
            }

            public String getPayType() {
                return PayType;
            }

            public void setPayType(String PayType) {
                this.PayType = PayType;
            }

            public String getRemark() {
                return Remark;
            }

            public void setRemark(String Remark) {
                this.Remark = Remark;
            }

            public String getRoutingType() {
                return RoutingType;
            }

            public void setRoutingType(String RoutingType) {
                this.RoutingType = RoutingType;
            }

            public double getSingleMaxMoney() {
                return SingleMaxMoney;
            }

            public void setSingleMaxMoney(double SingleMaxMoney) {
                this.SingleMaxMoney = SingleMaxMoney;
            }

            public String getStartTime() {
                return StartTime;
            }

            public void setStartTime(String StartTime) {
                this.StartTime = StartTime;
            }
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
