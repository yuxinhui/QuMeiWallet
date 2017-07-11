package com.gdyd.qmwallet.home.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hebei on 2017/7/4.
 */

public class YuDianRecordBean implements Serializable {

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

    public DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(DataBean dataBean) {
        this.dataBean = dataBean;
    }

    private String Data;
    private int nResul;
    private String sMessage;
    private DataBean dataBean;


    public static class DataBean implements Serializable{
        private String RainCredit;
        private int count;
        ArrayList<RainCreditItem> list;

        public String getRainCredit() {
            return RainCredit;
        }

        public void setRainCredit(String rainCredit) {
            RainCredit = rainCredit;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public ArrayList<RainCreditItem> getList() {
            return list;
        }

        public void setList(ArrayList<RainCreditItem> list) {
            this.list = list;
        }

        public static class RainCreditItem implements Serializable{
            private String ComputerTime;
            private int ID;
            private int IsNew;
            private String MerchantNO;
            private int Source;

            public int getIsNew() {
                return IsNew;
            }

            public void setIsNew(int isNew) {
                IsNew = isNew;
            }

            public String getComputerTime() {
                return ComputerTime;
            }

            public void setComputerTime(String computerTime) {
                ComputerTime = computerTime;
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

            public void setMerchantNO(String merchantNO) {
                MerchantNO = merchantNO;
            }

            public int getSource() {
                return Source;
            }

            public void setSource(int source) {
                Source = source;
            }
        }
    }
}
