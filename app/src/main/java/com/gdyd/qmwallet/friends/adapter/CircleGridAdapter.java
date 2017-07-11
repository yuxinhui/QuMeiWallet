package com.gdyd.qmwallet.friends.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.friends.utils.MeasureUtils;
import com.gdyd.qmwallet.friends.utils.UiUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CircleGridAdapter extends BaseAdapter {
	
	private String[] mFiles;
	private LayoutInflater mLayoutInflater;
    private  int with;
	public CircleGridAdapter(String[] files,int with) {
		this.mFiles = files;
		this.with=with;
		mLayoutInflater = LayoutInflater.from(UiUtils.getContext());
	}

	@Override
	public int getCount() {
		return mFiles == null ? 0 : mFiles.length;
	}

	@Override
	public String getItem(int position) {
		return mFiles[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.item_gridview_circle,parent, false);
			holder.imageView = (ImageView) convertView.findViewById(R.id.album_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 根据屏幕宽度动态设置图片宽高
		int width = MeasureUtils.getWidth(UiUtils.getContext());
		final float scale = UiUtils.getContext().getResources().getDisplayMetrics().density;
		int imageWidth = ((width-(int)(60 * scale + 0.5f)) / 3 - 40);
		holder.imageView.setLayoutParams(new AbsListView.LayoutParams(imageWidth, imageWidth));
		String url = getItem(position);
		ImageLoader.getInstance().displayImage(UrlConfig.PHOTO_URI+url, holder.imageView);
		return convertView;
	}

	private static class ViewHolder {
		ImageView imageView;
	}
}
