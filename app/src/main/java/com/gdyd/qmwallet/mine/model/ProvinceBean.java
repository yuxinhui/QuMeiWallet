package com.gdyd.qmwallet.mine.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zanzq on 2017/2/28.
 */

public class ProvinceBean implements Serializable {

    /**
     * UserData : [{"ID":1000,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"北京市      "},{"ID":1100,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"天津市      "},{"ID":1200,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"河北省      "},{"ID":1600,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"山西省      "},{"ID":1900,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"内蒙古自治区"},{"ID":2200,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"辽宁省      "},{"ID":2400,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"吉林省      "},{"ID":2600,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"黑龙江省    "},{"ID":2900,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"1","TYPE":"00","VALUE":"上海市      "},{"ID":3000,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"江苏省      "},{"ID":3300,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"浙江省      "},{"ID":3600,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"安徽省      "},{"ID":3900,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"福建省      "},{"ID":4200,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"江西省      "},{"ID":4500,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"1","TYPE":"00","VALUE":"山东省      "},{"ID":4900,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"河南省      "},{"ID":5200,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"1","TYPE":"00","VALUE":"湖北省      "},{"ID":5500,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"1","TYPE":"00","VALUE":"湖南省      "},{"ID":5800,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"1","TYPE":"00","VALUE":"广东省      "},{"ID":6100,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"1","TYPE":"00","VALUE":"广西自治区  "},{"ID":6400,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"1","TYPE":"00","VALUE":"海南省      "},{"ID":6500,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"四川省      "},{"ID":6900,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"1","TYPE":"00","VALUE":"重庆市      "},{"ID":7000,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"贵州省      "},{"ID":7300,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"云南省      "},{"ID":7700,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"西藏自治区  "},{"ID":7900,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"1","TYPE":"00","VALUE":"陕西省      "},{"ID":8200,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"甘肃省      "},{"ID":8500,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"青海省      "},{"ID":8700,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"宁夏自治区  "},{"ID":8800,"OWNER":"PROVINCE","PID":156,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"新疆自治区  "}]
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

    public static class DataBean implements Serializable {
        /**
         * ID : 1000
         * OWNER : PROVINCE
         * PID : 156
         * RESERVE : 勿动
         * SWITCH : 0
         * TYPE : 00
         * VALUE : 北京市
         */

        private int ID;
        private String OWNER;
        private int PID;
        private String RESERVE;
        private String SWITCH;
        private String TYPE;
        private String VALUE;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getOWNER() {
            return OWNER;
        }

        public void setOWNER(String OWNER) {
            this.OWNER = OWNER;
        }

        public int getPID() {
            return PID;
        }

        public void setPID(int PID) {
            this.PID = PID;
        }

        public String getRESERVE() {
            return RESERVE;
        }

        public void setRESERVE(String RESERVE) {
            this.RESERVE = RESERVE;
        }

        public String getSWITCH() {
            return SWITCH;
        }

        public void setSWITCH(String SWITCH) {
            this.SWITCH = SWITCH;
        }

        public String getTYPE() {
            return TYPE;
        }

        public void setTYPE(String TYPE) {
            this.TYPE = TYPE;
        }

        public String getVALUE() {
            return VALUE;
        }

        public void setVALUE(String VALUE) {
            this.VALUE = VALUE;
        }
    }
}
