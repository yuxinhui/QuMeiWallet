package com.gdyd.qmwallet.friends.base;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.home.YL_kj;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseActivity extends FragmentActivity {

	protected Context context;
	private TextView titleView;
	private TextView leftView;
	private TextView rightView;
	private ImageButton rightImageView;
	private ImageButton leftImageView;

	private OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v == leftView) {
				onLeftClick(v);
			} else if (v == rightView) {
				onRightClick(v);
			} else if (v == rightImageView) {
				onRightImageViewClick(v);
			} else if (v == leftImageView) {
				onLeftImageViewClick(v);
			}
		}
	};

	public void setTitle(String title) {
		titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);
		titleView.setVisibility(View.VISIBLE);
	}

	public void setTitle(String title, int color) {
		titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);
		titleView.setTextColor(color);
		titleView.setVisibility(View.VISIBLE);
	}

	public void setTitleLeftText(String value) {
		leftView = (TextView) findViewById(R.id.title_left_text);
		leftView.setText(value);
		leftView.setVisibility(View.VISIBLE);
		leftView.setOnClickListener(clickListener);
	}

	public void setTitleLeftText(String value, int resid) {
		leftView = (TextView) findViewById(R.id.title_left_text);
		leftView.setText(value);
		leftView.setBackgroundResource(resid);
		leftView.setVisibility(View.VISIBLE);
		leftView.setOnClickListener(clickListener);
	}

	public void setTitleLeftImg(int resid) {
		leftImageView = (ImageButton) findViewById(R.id.title_left_img);
		leftImageView.setImageResource(resid);
		leftImageView.setVisibility(View.VISIBLE);
		leftImageView.setOnClickListener(clickListener);
	}

	public void setTitleRightText(String value, int color) {
		rightView = (TextView) findViewById(R.id.title_right_text);
		rightView.setText(value);
		rightView.setVisibility(View.VISIBLE);
		rightView.setTextColor(color);
		rightView.setOnClickListener(clickListener);
	}

	public void setTitleRightText(String value) {
		rightView = (TextView) findViewById(R.id.title_right_text);
		rightView.setText(value);
		rightView.setVisibility(View.VISIBLE);
		rightView.setOnClickListener(clickListener);
	}

	public void setTitleRightImg(int resid) {
		rightImageView = (ImageButton) findViewById(R.id.title_right_img);
		rightImageView.setImageResource(resid);
		rightImageView.setVisibility(View.VISIBLE);
		rightImageView.setOnClickListener(clickListener);
	}

	public TextView getLeftView() {
		return leftView;
	}

	public TextView getRightView() {
		return rightView;
	}

	public ImageButton getRightImageView() {
		return rightImageView;
	}

	public ImageButton getLeftImageView() {
		return leftImageView;
	}

	protected void onLeftClick(View view) {

	}

	protected void onRightClick(View view) {

	}

	protected void onRightImageViewClick(View view) {

	}

	protected void onLeftImageViewClick(View view) {
		finish();
	}
	//Android 6.0 需要请求权限
	@TargetApi(23)
	public void requestNeedPermissions() {
		if (Build.VERSION.SDK_INT >= 23) {
			List<String> permissionsNeeded = new ArrayList<String>();
			final List<String> permissionsList = new ArrayList<String>();
			if (!addPermission(BaseActivity.this, permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
				permissionsNeeded.add("获取相册信息");
			if (!addPermission(BaseActivity.this, permissionsList,Manifest.permission.WRITE_EXTERNAL_STORAGE))
				permissionsNeeded.add("获得读写照片权限");
			if (!addPermission(BaseActivity.this, permissionsList,Manifest.permission.CAMERA))
				permissionsNeeded.add("获取相机权限");
			if (permissionsList.size() > 0) {
				requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), 111);
				return;
			}
		}
	}
	@TargetApi(23)
	private boolean addPermission(Context Ctx, List<String> permissionsList,
								  String permission) {
		if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
			permissionsList.add(permission);
			// Check for Rationale Option
			if (shouldShowRequestPermissionRationale(permission))
				return false;
		}
		return true;
	}
	@TargetApi(23)
	@Override
	public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
		switch (requestCode) {
			case 111:
				for (int i = 0; i < permissions.length; i++) {
					if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
						if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permissions[i])) {
							// 缺少数据卡读写权限 （必须）
							Toast.makeText(getApplicationContext(), "选取照片必须要数据卡写权限！", Toast.LENGTH_LONG).show();
							return;
						}
						if (Manifest.permission.CAMERA.equals(permissions[i])) {
							// 缺少读取手机信息权限（可选）
							Toast.makeText(getApplicationContext(), "拍照必须获取相机权限！", Toast.LENGTH_LONG).show();
							return;
						}
						if (Manifest.permission.READ_EXTERNAL_STORAGE.equals(permissions[i]) ) {
							Toast.makeText(getApplicationContext(), "选取照片必须要数据卡读权限！", Toast.LENGTH_LONG).show();
							return;
						}
					}
				}
				break;
			default:
				super.onRequestPermissionsResult(requestCode, permissions,grantResults);
		}
	}
}
