package com.gdyd.qmwallet.home.model;

import com.iflytek.cloud.thirdparty.S;

import java.io.Serializable;

/**
 * Created by hebei on 2017/7/4.
 */

public class NewYuDianBean implements Serializable{
    private String Data;
    private int nResul;
    private String sMessage;

    public NewYuDianItem getDataBean() {
        return dataBean;
    }

    public void setDataBean(NewYuDianItem dataBean) {
        this.dataBean = dataBean;
    }

    private NewYuDianItem dataBean;

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

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

    public class  NewYuDianItem implements Serializable{
        private int creditSource;
        private int creditMoney;
        private String computerTime;
        private int totalSource;
        private String creditEvaluation;

        public int getCreditSource() {
            return creditSource;
        }

        public void setCreditSource(int creditSource) {
            this.creditSource = creditSource;
        }

        public int getCreditMoney() {
            return creditMoney;
        }

        public void setCreditMoney(int creditMoney) {
            this.creditMoney = creditMoney;
        }

        public String getComputerTime() {
            return computerTime;
        }

        public void setComputerTime(String computerTime) {
            this.computerTime = computerTime;
        }

        public int getTotalSource() {
            return totalSource;
        }

        public void setTotalSource(int totalSource) {
            this.totalSource = totalSource;
        }

        public String getCreditEvaluation() {
            return creditEvaluation;
        }

        public void setCreditEvaluation(String creditEvaluation) {
            this.creditEvaluation = creditEvaluation;
        }



    }


}
