package com.gdyd.qmwallet.mine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zanzq on 2017/4/25.
 */

public class MapBean implements Serializable{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private ArrayList<RateDetails> list;

    public ArrayList<RateDetails> getList() {
        return list;
    }

    public void setList(ArrayList<RateDetails> list) {
        this.list = list;
    }

    public static  class RateDetails{
        private String type;
        private RateBean.DataBean dataBean;

        public RateDetails(String type, RateBean.DataBean dataBean) {
            this.type = type;
            this.dataBean = dataBean;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public RateBean.DataBean getDataBean() {
            return dataBean;
        }

        public void setDataBean(RateBean.DataBean dataBean) {
            this.dataBean = dataBean;
        }
    }
    private HashMap<String,RateBean.DataBean> hashMap;

    public HashMap<String, RateBean.DataBean> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, RateBean.DataBean> hashMap) {
        this.hashMap = hashMap;
    }
}
