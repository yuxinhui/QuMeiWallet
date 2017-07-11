package com.gdyd.qmwallet.myview;

/**
 * Created by zanzq on 2017/4/5.
 */

public interface RiseNumberBase {
    public void start();

    public RiseNumberTextView withNumber(float number);

    public RiseNumberTextView withNumber(float number, boolean flag);

    public RiseNumberTextView withNumber(int number);

    public RiseNumberTextView setDuration(long duration);

    public void setOnEnd(RiseNumberTextView.EndListener callback);
}
