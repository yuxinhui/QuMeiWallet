package com.gdyd.qmwallet.mine.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zanzq on 2017/2/28.
 */

public class BlankBean implements Serializable {

    /**
     * UserData : [{"Id":1,"bankcode":"01020000","bankcode2":"9040","bankcode3":"102","bankname":"工商银行"},{"Id":2,"bankcode":"01030000","bankcode2":"9009","bankcode3":"103","bankname":"农业银行"},{"Id":3,"bankcode":"01050000","bankcode2":"9008","bankcode3":"105","bankname":"建设银行"},{"Id":4,"bankcode":"03010000","bankcode2":"9005","bankcode3":"301","bankname":"交通银行"},{"Id":5,"bankcode":"03080000","bankcode2":"9004","bankcode3":"308","bankname":"招商银行"},{"Id":6,"bankcode":"63030000","bankcode2":"9002","bankcode3":"303","bankname":"光大银行"},{"Id":7,"bankcode":"63040000","bankcode2":"9003","bankcode3":"304","bankname":"华夏银行"},{"Id":8,"bankcode":"03100000","bankcode2":"9020","bankcode3":"310","bankname":"浦发银行"},{"Id":9,"bankcode":"03050000","bankcode2":"9010","bankcode3":"305","bankname":"民生银行"},{"Id":11,"bankcode":"03060000","bankcode2":"9012","bankcode3":"306","bankname":"广发银行"},{"Id":24,"bankcode":"06100000","bankcode2":"9013","bankcode3":"307","bankname":"平安银行"},{"Id":25,"bankcode":"01040000","bankcode2":null,"bankcode3":null,"bankname":"中国银行"}]
     * nResul : 1
     * sMessage : null
     */

    private int nResul;
    private Object sMessage;
    private List<DataBean> Data;

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

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean implements Serializable{
        /**
         * Id : 1
         * bankcode : 01020000
         * bankcode2 : 9040
         * bankcode3 : 102
         * bankname : 工商银行
         */

        private int Id;
        private String bankcode;
        private String bankcode2;
        private String bankcode3;
        private String bankname;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getBankcode() {
            return bankcode;
        }

        public void setBankcode(String bankcode) {
            this.bankcode = bankcode;
        }

        public String getBankcode2() {
            return bankcode2;
        }

        public void setBankcode2(String bankcode2) {
            this.bankcode2 = bankcode2;
        }

        public String getBankcode3() {
            return bankcode3;
        }

        public void setBankcode3(String bankcode3) {
            this.bankcode3 = bankcode3;
        }

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }
    }
}
