package com.gdyd.qmwallet.home.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zanzq on 2017/3/6.
 */

public class MyAdapter extends FragmentPagerAdapter{
    private List<Fragment> list;
    private String[] titles;

    public MyAdapter(FragmentManager fm, List<Fragment> list, String[] titles) {
        super(fm);
        this.list = list;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    //获取每一页的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
