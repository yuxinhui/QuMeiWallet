package com.gdyd.qmwallet.home;

import android.content.Context;
import android.content.Intent;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.home.model.MemberInfoBean;
import com.gdyd.qmwallet.home.presenter.UpgradePresenter;
import com.gdyd.qmwallet.home.view.IUpgradeView;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ManageMentActivity extends BaseActivity implements View.OnClickListener,IUpgradeView {

    private PercentRelativeLayout left_return;
    private PullToRefreshListView listView;
    private int [] icon={R.drawable.icon_jp,R.drawable.icon_zs,R.drawable.icon_zz,R.drawable.icon_dl,R.drawable.icon_hhr,R.drawable.icon_gd};
    private String merchantNo;
    private UpgradePresenter upgradePresenter=new UpgradePresenter(this);
    private TextView Merchant_sum;
    private TextView trans_money;
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
    private String lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
    private ArrayList<MemberInfoBean.DataBean.RecomMemberLevelBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_ment);
        merchantNo = getIntent().getStringExtra(APPConfig.MERCHANTNO);
        initData();
        upgradePresenter.getUpgradeInfo(merchantNo);
    }

    /**
     * 初始化刷新状态
     */
    private void initPull() {

        ILoadingLayout layoutProxy = listView.getLoadingLayoutProxy(true,false);
        layoutProxy.setLastUpdatedLabel("上次刷新时间:" + lastUpdateTime);
        layoutProxy.setPullLabel("下拉刷新");
        layoutProxy.setRefreshingLabel("正在加载数据");
        layoutProxy.setReleaseLabel("手指释放刷新数据");
        ILoadingLayout endLayout  = listView.getLoadingLayoutProxy(false,true);
        endLayout.setLastUpdatedLabel("上次刷新时间:" + lastUpdateTime);
        endLayout.setPullLabel("上拉刷新");
        endLayout.setRefreshingLabel("正在加载数据");
        endLayout.setReleaseLabel("手指释放刷新数据");
    }

    /**
     * 初始化布局信息
     */
    private void initData() {
        left_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        left_return.setOnClickListener(this);
        listView = ((PullToRefreshListView) findViewById(R.id.listView));
        Merchant_sum = ((TextView) findViewById(R.id.Merchant_sum));
        trans_money = ((TextView) findViewById(R.id.trans_money));
        // String[] stringArray = getResources().getStringArray(R.array.name);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
                initPull();
                upgradePresenter.getUpgradeInfo(merchantNo);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //item点击事件
            @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MemberInfoBean.DataBean.RecomMemberLevelBean recomMemberLevelBean = list.get(position - 1);
            startActivity(new Intent(ManageMentActivity.this,MemberDetailsActivity.class).
                    putExtra("Level", recomMemberLevelBean.getLevelValue()).
                    putExtra(APPConfig.MERCHANTNO,merchantNo)
                    .putExtra("title", recomMemberLevelBean.getName()));
        }
    });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_return:
                finish();
                break;
        }
    }

    /**
     * 升级规则返回
     * @param memberInfoBean
     */
    @Override
    public void UpgradeInfo(MemberInfoBean memberInfoBean) {
        listView.onRefreshComplete();
        if (memberInfoBean!=null&&memberInfoBean.getnResul()==1){
            MemberInfoBean.DataBean dataBean = memberInfoBean.getDataBean();
            double transMoney = dataBean.getTransMoney();
            int merchantCount = dataBean.getMerchantCount();
            Merchant_sum.setText(merchantCount+"");
            trans_money.setText(String .format("%.2f",transMoney)+"");
            list = memberInfoBean.getDataBean().getList();
            ManageAdapter manageAdapter = new ManageAdapter(this, list, icon);
            listView.setAdapter(manageAdapter);
        }
    }

    /**
     * 会员管理适配器
     */
    class ManageAdapter extends BaseAdapter{
         private Context context;
        private  ArrayList<MemberInfoBean.DataBean.RecomMemberLevelBean>  list;
        private int [] typeface;
        private LayoutInflater inflater;

        public ManageAdapter(Context context, ArrayList<MemberInfoBean.DataBean.RecomMemberLevelBean> name, int[] face) {
            this.context = context;
            this.list = name;
            this.typeface = face;
            inflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView==null){
                convertView=inflater.inflate(R.layout.manage_ment_item,parent,false);
                holder=new ViewHolder();
                holder.face= (ImageView) convertView.findViewById(R.id.face);
                holder.name= (TextView) convertView.findViewById(R.id.name);
                holder.jj=((TextView) convertView.findViewById(R.id.jj));
              holder.zj=  ((TextView) convertView.findViewById(R.id.zj));
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            MemberInfoBean.DataBean.RecomMemberLevelBean bean = list.get(position);
            holder.name.setText(bean.getName());
            holder.jj.setText("间接"+bean.getSecondCount()+"户");
            holder.zj.setText("直属"+bean.getFirstCount()+"户");
            holder.face.setImageResource(typeface[position]);
            return convertView;
        }
        class ViewHolder{
            TextView name,jj,zj;
            ImageView face;
        }
    }
}
