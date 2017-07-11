package com.gdyd.qmwallet.friends.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.friends.base.BaseActivity;
import com.gdyd.qmwallet.friends.selector.Bimp;
import com.gdyd.qmwallet.friends.selector.FileUtils;
import com.gdyd.qmwallet.friends.selector.NoScrollGridView;
import com.gdyd.qmwallet.friends.selector.PhotoActivity;
import com.gdyd.qmwallet.friends.selector.TestPicActivity;
import com.gdyd.qmwallet.friends.utils.MyAlertDialog;
import com.gdyd.qmwallet.friends.utils.MyPopupWindow;
import com.gdyd.qmwallet.friends.utils.SystemUtils;
import com.gdyd.qmwallet.friends.utils.ToastUtils;
import com.gdyd.qmwallet.home.presenter.UpgradePresenter;
import com.gdyd.qmwallet.home.view.UPPhotoIntentService;
import com.gdyd.qmwallet.mine.RealNameActivity;
import com.gdyd.qmwallet.mine.model.PhotoBean;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.NetUtil;
import com.gdyd.qmwallet.utils.ReadImgToBinary2;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

;import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 发布圈子
 * @author zhouyou
 */
public class PublishCircleActivity extends BaseActivity implements TextWatcher, OnClickListener {
	private Gson gson = new Gson();
	private static final int MAX_INPUT_LENGTH = 140;// 最大输入长度 
	private static final int TAKE_PICTURE = 0x000000;
	private NoScrollGridView mGridView;
	private EditText mEditText;
	private TextView mHintView;
	private Button mSendCircle;
	private GridAdapter mAdapter;
	private String mImageFileName;
	public static String mImagePath;
	private Handler handle=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what==0){
				ToastUtils.showToast(PublishCircleActivity.this, ((String) msg.obj));
			}
		}
	};
	private int id;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_publish_circle);
		id = getIntent().getIntExtra("ID",0);
		initView();
		addListener();
		requestNeedPermissions();
	}

	private void initView() {
		setTitle("发布圈子");
		setTitleLeftImg(R.drawable.ico_back);
		mSendCircle = (Button) findViewById(R.id.btn_send_circle);
		mEditText = (EditText) findViewById(R.id.et_input);
		mHintView = (TextView) findViewById(R.id.tv_hint);
		mGridView = (NoScrollGridView) findViewById(R.id.gv_gridview);
		mEditText.addTextChangedListener(this);
		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		mAdapter = new GridAdapter(this);
		mAdapter.update();
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (arg2 == Bimp.mBmps.size()) {
					showPopupWindow();
				} else {
					Intent intent = new Intent(PublishCircleActivity.this, PhotoActivity.class);
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
	}
	
	private void showPopupWindow() {
		isKeyboardShownToHideKeyboard();
		MyPopupWindow popupWindow = new MyPopupWindow(this);
		String[] str = {"拍照","从相册中选择"};
		popupWindow.showPopupWindowForFoot(str, new MyPopupWindow.Callback() {
			@Override
			public void callback(String text, int position) {
				switch (position) {
				case 0:
					photo();
					break;
				case 1:
					Intent intent = new Intent(PublishCircleActivity.this, TestPicActivity.class);
					startActivity(intent);
					break;
				}
			}
		});
	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater; // 视图容器
		private int selectedPosition = -1;// 选中的位置
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			return (Bimp.mBmps.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.mBmps.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused));
				if (position == APPConfig.maxPhotopage) {
					holder.image.setVisibility(View.GONE);
				}
			}else {
				holder.image.setImageBitmap(Bimp.mBmps.get(position));
			}
			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					mAdapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.mMax == Bimp.mDrr.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							try {
								if(Bimp.mDrr.size() <= 0)
									return;
								String path = Bimp.mDrr.get(Bimp.mMax);
								Bitmap bm = Bimp.revitionImageSize(path);
								Bimp.mBmps.add(bm);
								String newStr = path.substring(path.lastIndexOf("/") + 1,path.lastIndexOf("."));
								FileUtils.saveBitmap(bm, "" + newStr);
								Bimp.mMax += 1;
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		mAdapter.update();
		super.onRestart();
	}

	/** 拍照 */
	public void photo() {
		// 随机缓存照片名
		mImageFileName = FileUtils.getFileNameForSystemTime(".jpg");
		// 首先判断SD卡是否存在
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			Uri imageUri;
			File file = new File(Environment.getExternalStorageDirectory(), mImageFileName);
			if (Build.VERSION.SDK_INT >Build.VERSION_CODES.KITKAT) {
				intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
				imageUri = FileProvider.getUriForFile(PublishCircleActivity.this, "com.gdyd.qmwallet.fileprovider", file);
			}else {
				imageUri=Uri.fromFile(file);
			}
			intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
			startActivityForResult(intent, TAKE_PICTURE);



		}else{
			ToastUtils.showToast(this, "内存卡不存在");
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			// 拍照
			if(requestCode == TAKE_PICTURE){
				if (Bimp.mDrr.size() < APPConfig.maxPhotopage && resultCode == -1) {
					File file = new File(Environment.getExternalStorageDirectory() + "/" + mImageFileName);
					mImagePath = file.getPath();
					Bimp.mDrr.add(mImagePath); 
				}
			}
		}
	}
	
	private void addListener() {
		mSendCircle.setOnClickListener(this);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,int after) {}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		int length = mEditText.getText().toString().length();
		mHintView.setVisibility(length >= 1 ? View.VISIBLE : View.GONE);
		mHintView.setTextColor(length < MAX_INPUT_LENGTH ? Color.BLACK : Color.RED);
		mHintView.setText(String.valueOf(MAX_INPUT_LENGTH - length));
	}

	@Override
	public void afterTextChanged(Editable s) {}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send_circle:// 发送圈子
			sendCircle();
			break;
		}
	}

	private void sendCircle() {
		List<String> list = new ArrayList<String>();				
		for (int i = 0; i < Bimp.mDrr.size(); i++) {
			String Str = Bimp.mDrr.get(i).substring(Bimp.mDrr.get(i).lastIndexOf("/") + 1, Bimp.mDrr.get(i).lastIndexOf("."));
			String e = FileUtils.SDPATH + Str + ".JPEG";
			Log.d("zanZQ", "sendCircle: "+e+",id:"+i);
			list.add(e);
			Bimp.list.add(ReadImgToBinary2.imgToBase64(e,400,400, null, null));
			//startService(new Intent(PublishCircleActivity.this, UPPhotoIntentService.class).putExtra("type",0).putExtra("photobean",new PhotoBean("1009","779255CEC3D03F353DD58996BF76DDFE", ReadImgToBinary2.imgToBase64(FileUtils.SDPATH + Str+".JPEG",200,200, null, null),i+"")));
			}
	//	startService(new Intent(PublishCircleActivity.this, UPPhotoIntentService.class).putExtra("type",1).putExtra("photo",new PlaceBean("1064","8A0A58671138F84CD22F932EF1B5BB6C",Bimp.list,new PlaceBean.GraphicSharingbean(2,"",mEditText.getText().toString()))));
new Thread(new Runnable() {
	@Override
	public void run() {

			OkHttpClient client = NetUtil.getClient();
		long transTyp = EncryptionHelper.getDate();
		String transTy="1064"+transTyp+"";
		String transKe = EncryptionHelper.md5(transTy);
			String value = gson.toJson(new PlaceBean("1064",transKe,Bimp.list,new PlaceBean.GraphicSharingbean(id,"",mEditText.getText().toString()),transTyp,APPConfig.AgentID));
			Log.d("zanZQ", "UpPhotoDatapath: "+value);
		try {
			String key = EncryptionHelper.key;
			Log.d("zanZQ", "getBannerData:"+key);
			value = EncryptionHelper.aesEncrypt(value, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
			RequestBody body = new FormBody.Builder()
					//添加键值对
					.add("sPostParam", value)
					.build();
			Request request=new Request.Builder().url(UrlConfig.URI).post(body).build();
			client.newCall(request).enqueue(new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {
				}

				@Override
				public void onResponse(Call call, Response response) throws IOException {
					if (response.isSuccessful()){
						String string = response.body().string();
						try {
							string = EncryptionHelper.aesDecrypt(string, EncryptionHelper.key);
							Log.d("zanZQ", "onResponse:bande "+string);
						} catch (Exception e) {
							e.printStackTrace();
						}
						Log.d("zanZQ", "onResponse:上传图片地址 "+string);
						try {
							JSONObject object = new JSONObject(string);
							int nResul = object.getInt("nResul");
							//if (nResul==1){
//								String data = object.getString("sMessage");
//								    ToastUtils.showToast(PublishCircleActivity.this,data);

						//	}else{
								Message mess = Message.obtain();
								String data = object.getString("sMessage");
								mess.what=0;
								mess.obj=data;
							handle.sendMessage(mess);
							Bimp.list.clear();
//							}
						} catch (JSONException e) {
							e.printStackTrace();

						}

					}
				}
			});
		}

}).start();
		// 高清的压缩图片全部就在  list 路径里面了
		// 高清的压缩过的 bmp 对象  都在 Bimp.mBmps里面
		// 完成上传服务器后删除缓存
		FileUtils.deleteDir();
		list.clear();
		Bimp.mBmps.clear();
		Bimp.mDrr.clear();
		Bimp.mMax = 0;
	//	ToastUtils.showToast(this, "发送成功");
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			exitHint();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onLeftImageViewClick(View view) {
		exitHint();
	}

	/** 退出提示  */
	private void exitHint() {
		// 判断是否有改动
		if(mEditText.getText().toString().length() > 0 || Bimp.mDrr.size() > 0 || !TextUtils.isEmpty(mImagePath)){
			isKeyboardShownToHideKeyboard();
			// 弹出提示框提示是否确认退出
			MyAlertDialog.showDialogForIOS(this, "提示", "确认要舍弃内容并退出当前页面吗？", new MyAlertDialog.OnCallbackListener() {
				@Override
				public void onConfrimClick(AlertDialog dialog) {
					dialog.dismiss();
					FileUtils.deleteDir();
					Bimp.mBmps.clear();
					Bimp.mDrr.clear();
					Bimp.mMax = 0;
					PublishCircleActivity.this.finish();
				}

				@Override
				public void onCancelClick(AlertDialog dialog) {
					dialog.dismiss();
				}
			});
		}else{
			finish();
		}
	}
	
	/** 判断软键盘是否弹起如弹起则隐藏 */
	private void isKeyboardShownToHideKeyboard(){
		if(SystemUtils.isKeyboardShown(mEditText.getRootView())){
			SystemUtils.hideKeyboard(this, mEditText.getApplicationWindowToken());
		}
	}
}
