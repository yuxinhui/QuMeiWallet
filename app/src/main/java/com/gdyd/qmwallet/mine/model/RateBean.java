package com.gdyd.qmwallet.mine.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zanzq on 2017/4/25.
 */

public class RateBean implements Serializable{

    /**
     * Data : [{"Channel_Discount":"0.49","DayMaxMoney":null,"EndTime":"2017-12-31 00:00:00","ID":1,"OrderNumber":1,"PayClass":"2","PayType":"A0","Remark":"支付宝支付，T+1，无需提现|额度：单笔10000.00元；单天支付无限制；","RoutingType":"17","SingleMaxMoney":10000.00,"StartTime":"2017-04-19 00:00:00"},{"Channel_Discount":"0.49","DayMaxMoney":null,"EndTime":"2017-12-31 22:00:00","ID":2,"OrderNumber":2,"PayClass":"1","PayType":"A0","Remark":"支付宝支付，T+0，365天实时到帐|额度：单笔10000.00元；单天支付无限制；","RoutingType":"17","SingleMaxMoney":10000.00,"StartTime":"2017-01-01 09:30:00"},{"Channel_Discount":"0.49","DayMaxMoney":null,"EndTime":"2017-12-31 00:00:00","ID":3,"OrderNumber":3,"PayClass":"2","PayType":"Z0","Remark":"微信支付，T+1，无需提现|额度：单笔10000.00元；单天支付无限制；","RoutingType":"17","SingleMaxMoney":10000.00,"StartTime":"2017-01-01 00:00:00"},{"Channel_Discount":"0.49","DayMaxMoney":null,"EndTime":"2017-12-31 22:00:00","ID":4,"OrderNumber":4,"PayClass":"1","PayType":"Z0","Remark":"微信支付，T+0，365天实时到帐|额度：单笔10000.00元；单天支付无限制；","RoutingType":"17","SingleMaxMoney":10000.00,"StartTime":"2017-01-01 09:30:00"},{"Channel_Discount":"0.56","DayMaxMoney":50000.00,"EndTime":"2017-12-31 00:00:00","ID":5,"OrderNumber":5,"PayClass":"2","PayType":"K0","Remark":"快捷支付，T+1，无需提现|额度：单笔20000.00元；单天50000.00元；","RoutingType":"17","SingleMaxMoney":20000.00,"StartTime":"2017-01-01 00:00:00"},{"Channel_Discount":"0.56","DayMaxMoney":50000.00,"EndTime":"2017-12-31 22:00:00","ID":6,"OrderNumber":6,"PayClass":"1","PayType":"K0","Remark":"快捷支付，T+0，365天实时到帐|额度：单笔20000.00元；单天50000.00元；","RoutingType":"17","SingleMaxMoney":20000.00,"StartTime":"2017-01-01 09:30:00"}]
     * nResul : 1
     * sMessage : null
     */

    private String Data;
    private int nResul;
    private String sMessage;
    private ArrayList<DataBean> list;

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

    public ArrayList<DataBean> getList() {
        return list;
    }

    public void setList(ArrayList<DataBean> list) {
        this.list = list;
    }

    public static class DataBean implements Serializable{

      /**
       * Channel_Discount : 0.49
       * DayMaxMoney : null
       * EndTime : 2017-12-31 00:00:00
       * ID : 1
       * OrderNumber : 1
       * PayClass : 2
       * PayType : A0
       * Remark : 支付宝支付，T+1，无需提现|额度：单笔10000.00元；单天支付无限制；
       * RoutingType : 17
       * SingleMaxMoney : 10000.0
       * StartTime : 2017-04-19 00:00:00
       */

      private String Channel_Discount;
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

      public String getChannel_Discount() {
          return Channel_Discount;
      }

      public void setChannel_Discount(String Channel_Discount) {
          this.Channel_Discount = Channel_Discount;
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
