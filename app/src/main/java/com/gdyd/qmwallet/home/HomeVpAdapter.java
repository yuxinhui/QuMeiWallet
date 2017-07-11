package com.gdyd.qmwallet.home;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gdyd.qmwallet.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * 主界面轮播图适配器
 */
public class HomeVpAdapter extends PagerAdapter {
    private ArrayList<String> list;
    private Context context;

    public HomeVpAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = new ImageView(context);
        Picasso.with(context).load(list.get(position % list.size())).error(R.drawable.banner).into(iv);
       // iv.setImageResource(imgs[position % imgs.length]);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
