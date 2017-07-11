package com.gdyd.qmwallet.mine.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zanzq on 2017/2/28.
 */

public class AreaBean implements Serializable {

    /**
     * UserData : [{"ID":1243,"OWNER":"AREA","PID":1240,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"滦县                          "},{"ID":1244,"OWNER":"AREA","PID":1240,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"滦南县                        "},{"ID":1245,"OWNER":"AREA","PID":1240,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"乐亭县                        "},{"ID":1246,"OWNER":"AREA","PID":1240,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"迁安市                        "},{"ID":1247,"OWNER":"AREA","PID":1240,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"迁西县                        "},{"ID":1248,"OWNER":"AREA","PID":1240,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"遵化市                        "},{"ID":1249,"OWNER":"AREA","PID":1240,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"玉田县                        "},{"ID":1251,"OWNER":"AREA","PID":1240,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"唐海县                        "},{"ID":9491,"OWNER":"AREA","PID":1240,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"路北区"},{"ID":9492,"OWNER":"AREA","PID":1240,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"路南区"},{"ID":9493,"OWNER":"AREA","PID":1240,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"古冶区"},{"ID":9494,"OWNER":"AREA","PID":1240,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"开平区"},{"ID":9495,"OWNER":"AREA","PID":1240,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"丰润区"},{"ID":9496,"OWNER":"AREA","PID":1240,"RESERVE":"勿动","SWITCH":"0","TYPE":"00","VALUE":"丰南市"}]
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
         * ID : 1243
         * OWNER : AREA
         * PID : 1240
         * RESERVE : 勿动
         * SWITCH : 0
         * TYPE : 00
         * VALUE : 滦县
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
