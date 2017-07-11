package com.gdyd.qmwallet.friends.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.text.ClipboardManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.friends.FriendDataBean;
import com.gdyd.qmwallet.friends.activity.ImagePagerActivity;
import com.gdyd.qmwallet.friends.bean.CircleBean;
import com.gdyd.qmwallet.friends.selector.NoScrollGridView;
import com.gdyd.qmwallet.friends.utils.ToastUtils;
import com.gdyd.qmwallet.friends.utils.UiUtils;
import com.gdyd.qmwallet.myview.CheckOverSizeTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CircleAdapter extends BaseAdapter {
		 private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		private ArrayList<FriendDataBean.DataBean.GraphicSharing2> mCircleBeans;
		private Context mContext;
		
		public CircleAdapter(ArrayList<FriendDataBean.DataBean.GraphicSharing2> beans, Context context) {
			this.mCircleBeans = beans;
			this.mContext = context;
		}
		
		@Override
		public int getCount() {
			return mCircleBeans.size();
		}

		@Override
		public Object getItem(int position) {
			return mCircleBeans.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if(convertView == null){
				convertView = UiUtils.inflate(R.layout.item_listview_circle);
				holder = new ViewHolder();
				holder.tv_commentCount = (TextView) convertView.findViewById(R.id.tv_commentCount);
				holder.tv_dynamicDesc = (CheckOverSizeTextView) convertView.findViewById(R.id.tv_dynamicDesc);
				holder.tv_releaseTime = (TextView) convertView.findViewById(R.id.tv_releaseTime);
				holder.tv_releaseTime2 = (TextView) convertView.findViewById(R.id.tv_releaseTime2);
				holder.tv_goodCount = (TextView) convertView.findViewById(R.id.tv_goodCount);
				holder.gridView = (NoScrollGridView) convertView.findViewById(R.id.gridView);
				holder.tv_nickname = (TextView) convertView.findViewById(R.id.tv_nickname);
				holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
				holder.zk=((TextView) convertView.findViewById(R.id.zk));
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			final FriendDataBean.DataBean.GraphicSharing2 bean = (FriendDataBean.DataBean.GraphicSharing2) getItem(position);
			if(null != bean){
				// 设置头像
			//	ImageLoader.getInstance().displayImage(bean.iconUri, holder.iv_icon);
				// 设置昵称
			//	holder.tv_nickname.setText(bean.getGraphicSharing().getTitle());
				// 设置说说
				holder.tv_dynamicDesc.setText(bean.getGraphicSharing().getText());
				holder.tv_dynamicDesc.setOnOverLineChangedListener(new CheckOverSizeTextView.OnOverSizeChangedListener() {
					@Override
					public void onChanged(boolean isOverSize) {
						if (isOverSize) {
							holder.zk.setVisibility(View.VISIBLE);
						} else {
							if (holder.zk.getText().toString().equals("收回")){
								holder.zk.setVisibility(View.VISIBLE);
							}else{
								holder.zk.setVisibility(View.GONE);
							}

						//	tv.setBackgroundColor(Color.WHITE);
						}
					}
				});
				holder.zk.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (holder.tv_dynamicDesc.isOverSize()) {
							holder.tv_dynamicDesc.displayAll();
							holder.zk.setText("收回");
						} else {
							holder.tv_dynamicDesc.hide(3);
							holder.zk.setText("更多");
						}
					}
				});
				// 设置发布时间
				String sendTime = bean.getGraphicSharing().getSendTime();
				if (sendTime==null||sendTime.equals("")){
					sendTime=dateFormat.format(new Date());
				}
				String month = sendTime.substring(sendTime.indexOf("-") + 1, sendTime.lastIndexOf("-"));
				String day = sendTime.substring(sendTime.lastIndexOf("-")+1, sendTime.lastIndexOf(" "));
				String time = sendTime.substring(sendTime.indexOf(" "), sendTime.lastIndexOf(":"));
				//holder.tv_releaseTime.setText(day+"日"+month+"月");
				String string = day+month+"月";
				SpannableString builder = new SpannableString(string);
				builder.setSpan(new AbsoluteSizeSpan(40), 2, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				holder.tv_releaseTime.setText(builder);
				holder.tv_releaseTime2.setText(time);
				holder.tv_goodCount.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (bean.getImageList()!=null&&bean.getImageList().size()>0){
							try {
								for (int i = 0; i < bean.getImageList().size(); i++) {
                                    Bitmap bitmap = ((BitmapDrawable) ((ImageView) holder.gridView.getChildAt(i)).getDrawable()).getBitmap();
                                    String save_result = MediaStore.Images.Media.insertImage(mContext.getContentResolver(),bitmap ,"俏美钱包","普通支付");
                                }
								ToastUtils.showToast(mContext,"保存成功");
							} catch (Exception e) {
								e.printStackTrace();
								ToastUtils.showToast(mContext,"保存失败");
							}
						}else{
							ToastUtils.showToast(mContext,"没有图片");
						}
					}
				});
				holder.tv_commentCount.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// 从API11开始android推荐使用android.content.ClipboardManager
						// 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
						ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
						// 将文本内容放到系统剪贴板里。
						cm.setText(bean.getGraphicSharing().getText());
						ToastUtils.showToast(mContext,"复制成功，可以发给朋友们了。");
					}
				});
				// 设置点赞总数
				//holder.tv_goodCount.setText(String.valueOf("200"));
				// 设置评论总数
				//holder.tv_commentCount.setText(String.valueOf("200"));
				// 判断如果用户是否上传图片
				if(bean.getImageList() != null && bean.getImageList().size() > 0){
					// 有：设置Adapter显示图片
					holder.gridView.setVisibility(View.VISIBLE);
					// 图片数组转图片集合
					final String[] urls = bean.getImageList().toArray(new String[bean.getImageList().size()]);
					CircleGridAdapter adapter = new CircleGridAdapter(urls,holder.gridView.getWidth());
					holder.gridView.setAdapter(adapter);
					// 设置点击事件
					holder.gridView.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							enterPhotoDetailed(urls, position);
						}
					});
				}else{
					// 否：隐藏
					holder.gridView.setVisibility(View.GONE);
				}
			}
			return convertView;
		}
		
		/**
		 * 进入图片详情页
		 * @param urls 图片数组
		 * @param position 角标
		 */
		protected void enterPhotoDetailed(String[] urls, int position) {
			Intent intent = new Intent(mContext, ImagePagerActivity.class);
			intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
			intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
			mContext.startActivity(intent);
		}
		private static class ViewHolder{
			ImageView iv_icon;
			TextView tv_nickname;
			CheckOverSizeTextView tv_dynamicDesc;
			TextView tv_releaseTime,tv_releaseTime2;
			TextView tv_goodCount;
			TextView zk;
			TextView tv_commentCount;
			NoScrollGridView gridView;
		}
}