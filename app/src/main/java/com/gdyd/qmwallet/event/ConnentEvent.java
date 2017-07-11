package com.gdyd.qmwallet.event;

/**
 * Created by fx-168 on 17/7/7.
 */

public class ConnentEvent {
    private String connFlag;

    public String getConnFlag() {
        return connFlag;
    }

    public ConnentEvent(String connFlag){
        this.connFlag = connFlag;
    }
}
