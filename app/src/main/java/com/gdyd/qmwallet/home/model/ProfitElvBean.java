package com.gdyd.qmwallet.home.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zanzq on 2017/6/6.
 */

public class ProfitElvBean implements Serializable {
    private ArrayList<ProfitBean.DataBean.RecommendProfitViewBean>  arrayList;
    private String name;

    public ArrayList<ProfitBean.DataBean.RecommendProfitViewBean> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ProfitBean.DataBean.RecommendProfitViewBean> arrayList) {
        this.arrayList = arrayList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfitElvBean(ArrayList<ProfitBean.DataBean.RecommendProfitViewBean> arrayList, String name) {
        this.arrayList = arrayList;
        this.name = name;
    }

    public ProfitElvBean() {
    }
}
