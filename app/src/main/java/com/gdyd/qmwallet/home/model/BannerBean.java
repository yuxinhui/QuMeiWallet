package com.gdyd.qmwallet.home.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zanzq on 2017/3/14.
 * 轮播图实体类
 */

public class BannerBean implements Serializable{

    /**
     * Data : [{"BannerUrl":"http:\/\/120.76.28.241\/BannerPic\/510415078794312609.jpg","ID":1,"PonitUrl":null,"State":1,"Type":0}]
     * nResul : 1
     * sMessage : null
     */

    private String Data;
    private int nResul;
    private String sMessage;
    private ArrayList<DataBean> beanArrayList;

    public int getnResul() {
        return nResul;
    }

    public void setnResul(int nResul) {
        this.nResul = nResul;
    }

    public Object getsMessage() {
        return sMessage;
    }

    public void setsMessage(String sMessage) {
        this.sMessage = sMessage;
    }

    public ArrayList<DataBean> getBeanArrayList() {
        return beanArrayList;
    }

    public void setBeanArrayList(ArrayList<DataBean> beanArrayList) {
        this.beanArrayList = beanArrayList;
    }

    public BannerBean(String data, int nResul, String sMessage, ArrayList<DataBean> beanArrayList) {

        Data = data;
        this.nResul = nResul;
        this.sMessage = sMessage;
        this.beanArrayList = beanArrayList;
    }

    public static  class DataBean implements Serializable {

        /**
         * BannerUrl : http://120.76.28.241/BannerPic/510415078794312609.jpg
         * ID : 1
         * PonitUrl : null
         * State : 1
         * Type : 0
         */

        private String BannerUrl;
        private int ID;
        private Object PonitUrl;
        private int State;
        private int Type;

        public String getBannerUrl() {
            return BannerUrl;
        }

        public void setBannerUrl(String BannerUrl) {
            this.BannerUrl = BannerUrl;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public Object getPonitUrl() {
            return PonitUrl;
        }

        public void setPonitUrl(Object PonitUrl) {
            this.PonitUrl = PonitUrl;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
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

    public Object getSMessage() {
        return sMessage;
    }

    public void setSMessage(String sMessage) {
        this.sMessage = sMessage;
    }
}
