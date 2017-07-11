package com.gdyd.qmwallet.mine.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zanzq on 2017/2/28.
 */

public class CityBean implements Serializable {


    /**
     * UserData : [{"ID":1210,"OWNER":"CITY","PID":1200,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"石家庄市               "},{"ID":1240,"OWNER":"CITY","PID":1200,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"唐山市                 "},{"ID":1260,"OWNER":"CITY","PID":1200,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"秦皇岛市               "},{"ID":1270,"OWNER":"CITY","PID":1200,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"邯郸市                 "},{"ID":1310,"OWNER":"CITY","PID":1200,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"邢台市                 "},{"ID":1340,"OWNER":"CITY","PID":1200,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"保定市                 "},{"ID":1380,"OWNER":"CITY","PID":1200,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"张家口市               "},{"ID":1410,"OWNER":"CITY","PID":1200,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"承德市                 "},{"ID":1430,"OWNER":"CITY","PID":1200,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"沧州市                 "},{"ID":1460,"OWNER":"CITY","PID":1200,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"廊坊市                 "},{"ID":1480,"OWNER":"CITY","PID":1200,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"衡水市                 "}]
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
         * ID : 1210
         * OWNER : CITY
         * PID : 1200
         * RESERVE : 勿动
         * SWITCH : 0
         * TYPE : 00
         * VALUE : 石家庄市
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
