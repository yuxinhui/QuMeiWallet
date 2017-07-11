package com.gdyd.qmwallet.friends.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;



import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.friends.FriendDataBean;
import com.gdyd.qmwallet.friends.FriendPresenter;
import com.gdyd.qmwallet.friends.ILodeFriendsView;
import com.gdyd.qmwallet.friends.adapter.CircleAdapter;
import com.gdyd.qmwallet.friends.base.BaseActivity;
import com.gdyd.qmwallet.friends.utils.ToastUtils;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.Is;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends BaseActivity implements OnClickListener,ILodeFriendsView {
	private ArrayList<FriendDataBean.DataBean.GraphicSharing2> mCircleBeans=new ArrayList<>();
	private PullToRefreshListView mListView;
	private List<String> mImgList;
	private  SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
	private  String lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
	private int id;
	private int page=1;
	private final int pageSize = 20;
   private FriendPresenter friendPresenter=new FriendPresenter(this);
	private CircleAdapter adapter;
	private int Permissions;
	private View iv_publish_circle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_friend);
		id = getIntent().getIntExtra("ID",0);
		Permissions = getIntent().getIntExtra("Permissions",0);
		//initData();
		initView();
		if (Permissions ==1){
			iv_publish_circle.setVisibility(View.VISIBLE);
		}
		requestNeedPermissions();
		long transTyp = EncryptionHelper.getDate();
		String transTy="1065"+transTyp+"";
		String transKe = EncryptionHelper.md5(transTy);
		friendPresenter.getInfo(new PlaceBean("1065",transKe,pageSize,page,transTyp,APPConfig.AgentID));
	}

	// 初始化数据
//	private void initData() {
//		// 模拟请求网络获取到数据
//		mImgList = new ArrayList<>();
//		mImgList.add("http://imgsrc.baidu.com/forum/w%3D580/sign=7eb05e9eddf9d72a17641015e428282a/3e87194c510fd9f9b3d66fc8212dd42a2a34a4c9.jpg");
//		mImgList.add("http://imgsrc.baidu.com/forum/w%3D580/sign=f09bb261cfbf6c81f7372ce08c3eb1d7/c213c895d143ad4bbd0a10c981025aafa40f06b6.jpg");
//		mImgList.add("http://img5.ph.126.net/PpGsC74VUQWuERP1gdwKGQ==/580964351947981315.jpg");
//		mImgList.add("http://img1.imgtn.bdimg.com/it/u=3749455878,661128293&fm=21&gp=0.jpg");
//		mImgList.add("http://ww2.sinaimg.cn/mw600/a1957912gw1e8aj508g1mj20go0b40uh.jpg");
//		mImgList.add("http://img7.aili.com/201511/11/1447231729_95100300.jpg");
//		mImgList.add("http://ww2.sinaimg.cn/large/83dcc5dbjw1dtpbjzipdaj.jpg");
//		mImgList.add("http://p18.qhimg.com/t0144d6a0802f22be4f.jpg");
//		mImgList.add("http://img2.3lian.com/2014/f5/158/d/90.jpg");
//		mCircleBeans = new ArrayList<>();
//		for (int i = 0; i < 10; i++) {
//			CircleBean bean = new CircleBean();
//			bean.iconUri = "http://img2.3lian.com/2014/f5/36/d/9.jpg";
//			bean.nickName = "昵称" + i;
//			bean.dynamicDesc = "这几天北京的大雾霾，朋友圈被各种照片刷爆。两句话让我印象深刻。“我们面对如此的恶劣，为何不是反抗，而是自嘲。”“也许，这一天，让很多人重新思考他们的人生。”后来。真的有朋友发了很长的文字。说自己即将离开北京的打算。底下很多人发表着自己的意见。更多的观点其实也像是自我的安慰表达——过几年我们也一定会离开的。 用黑乎乎的照片发了一条微博。感慨了一小句。这是一座用年轻梦想健康金钱作为代价才能好......";
//			bean.releaseTime = "2016-12-" + i;
//			ArrayList<String> a=new ArrayList<>();
//
//			a.addAll(mImgList);
//			bean.imgList = a;
//			bean.commentCount = 11 + i;
//			bean.goodCount = 72 + i;
//			mCircleBeans.add(bean);
//			if (mImgList.size()!=0){
//				String remove = mImgList.remove(0);
//			}
//
//		}
//	}

	// 初始化View
	private void initView() {
		mListView = (PullToRefreshListView) findViewById(R.id.listview);
		iv_publish_circle = findViewById(R.id.iv_publish_circle);
		iv_publish_circle.setOnClickListener(this);
		findViewById(R.id.left_return).setOnClickListener(this);
		mListView.setMode(PullToRefreshBase.Mode.BOTH);

		initPull();
		mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				page=1;
				lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
				initPull();
				long transTyp = EncryptionHelper.getDate();
				String transTy="1065"+transTyp+"";
				String transKe = EncryptionHelper.md5(transTy);
				friendPresenter.getInfo(new PlaceBean("1065",transKe,pageSize,page,transTyp, APPConfig.AgentID));
				//friendPresenter.getInfo(new PlaceBean("1065","242FE52516D5B7A559C6B86A82D916D6",pageSize,page));
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				page++;
				lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
				initPull();
				Log.d("zanZQ", "onPullUpToRefresh: "+page);
				long transTyp = EncryptionHelper.getDate();
				String transTy="1065"+transTyp+"";
				String transKe = EncryptionHelper.md5(transTy);
				friendPresenter.getInfo(new PlaceBean("1065",transKe,pageSize,page,transTyp,APPConfig.AgentID));
			//	friendPresenter.getInfo(new PlaceBean("1065","242FE52516D5B7A559C6B86A82D916D6",pageSize,page));
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_publish_circle:
			startActivity(new Intent(this, PublishCircleActivity.class).putExtra("ID",id));
			break;
			case R.id.left_return:
				finish();
				break;
		}
	}
	private void initPull() {
		ILoadingLayout layoutProxy = mListView.getLoadingLayoutProxy(true,false);
		layoutProxy.setLastUpdatedLabel("上次刷新时间:" + lastUpdateTime);
		layoutProxy.setPullLabel("下拉刷新");
		layoutProxy.setRefreshingLabel("正在加载数据");
		layoutProxy.setReleaseLabel("手指释放刷新数据");
		ILoadingLayout endLayout  = mListView.getLoadingLayoutProxy(false,true);
		endLayout.setLastUpdatedLabel("上次刷新时间:" + lastUpdateTime);
		endLayout.setPullLabel("上拉刷新");
		endLayout.setRefreshingLabel("正在加载数据");
		endLayout.setReleaseLabel("手指释放刷新数据");
	}

	@Override
	public void getFriendData(FriendDataBean friendDataBean) {
		mListView.onRefreshComplete(); // 停止刷新动画
		if (friendDataBean!=null&&friendDataBean.getnResul()==1){
			int pageCount = friendDataBean.getDataBean().getPageCount();
			//    list=recordBeanList;
			Log.d("zanZQ", "getRecordBack: "+page+":size"+pageCount);
			if (page==pageCount){ //如果总页数 等于当前请求页数 则是最后一页
				if (adapter!=null){
					ToastUtils.showToast(MainActivity.this, "当前是最后一页");
				}

				//   Toast.makeText(getActivity(), "当前是最后一页", Toast.LENGTH_SHORT).show();
			}else if (page>pageCount){ //如果 请求页大于总页数 则直接返回
				if (pageCount==0){
					pageCount=1;
				}
				page=pageCount;
			//	mCircleBeans.clear();
				if (Is.isNoEmptyAll(adapter)){
					adapter.notifyDataSetChanged();
					Toast.makeText(MainActivity.this, "没有更多了", Toast.LENGTH_SHORT).show();
				}
				return;
			}
			ArrayList<FriendDataBean.DataBean.GraphicSharing2> graphicSharing2 = friendDataBean.getDataBean().getGraphicSharing2();
			if (graphicSharing2!=null&&graphicSharing2.size()>0){
				mCircleBeans.clear();
				mCircleBeans.addAll(graphicSharing2);
				if (!Is.isNoEmptyAll(adapter)){
					adapter = new CircleAdapter(mCircleBeans, this);
					mListView.setAdapter(adapter);

				}else{
					adapter.notifyDataSetChanged();

				}
			}else{
				//mCircleBeans.clear();
				if (Is.isNoEmptyAll(adapter)){
					adapter.notifyDataSetChanged();
					Toast.makeText(MainActivity.this, "没有更多了", Toast.LENGTH_SHORT).show();
				}
			}


		}else{
			Toast.makeText(this, "没有更多了", Toast.LENGTH_SHORT).show();
		}
	}
}
