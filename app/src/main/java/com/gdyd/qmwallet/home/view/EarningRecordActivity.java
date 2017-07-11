package com.gdyd.qmwallet.home.view;

import android.content.Context;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.home.model.EarningRecordBean;
import com.gdyd.qmwallet.home.presenter.RecordPresenter;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.Is;
import com.gdyd.qmwallet.utils.T;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarningRecordActivity extends BaseActivity implements IRecordView {
    private int page=1;
    private final int pageSize = 30;
    private String machNo;
    private RecordPresenter presenter=new RecordPresenter(this);
    private MyAdapter adapter;
    List<EarningRecordBean.DataBean.RecommendPostCashBean> list=new ArrayList<>();
    private PullToRefreshListView listView;
    private PercentRelativeLayout left_return;
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
    private String lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earning_record);
        machNo = getIntent().getStringExtra(APPConfig.LOGIN_MACHNO);
        listView = ((PullToRefreshListView) findViewById(R.id.listView));
        left_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        left_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        left_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setMode(PullToRefreshBase.Mode.BOTH);// 两端刷新
     //   listView.getLoadingLayoutProxy(false, true).setLastUpdatedLabel("松开加载更多");
        initPull();
        long transTyp = EncryptionHelper.getDate();
        String transTy="1040"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);
        presenter.getRecordData(new PlaceBean("1040",transKe,machNo,page,pageSize,transTyp));
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page=1;
                lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
                initPull();
                long transTyp = EncryptionHelper.getDate();
                String transTy="1040"+transTyp+"";
                String transKe = EncryptionHelper.md5(transTy);
                presenter.getRecordData(new PlaceBean("1040",transKe,machNo,page,pageSize,transTyp));
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
                initPull();
                Log.d("zanZQ", "onPullUpToRefresh: "+page);
                long transTyp = EncryptionHelper.getDate();
                String transTy="1040"+transTyp+"";
                String transKe = EncryptionHelper.md5(transTy);
                presenter.getRecordData(new PlaceBean("1040",transKe,machNo,page,pageSize,transTyp));
            }
        });
    }

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

    //获取提现记录
    @Override
    public void getRecordBack(EarningRecordBean earningRecordBean) {
        listView.onRefreshComplete(); // 停止刷新动画
            if (earningRecordBean!=null&&earningRecordBean.getnResul()==1){
                int pageCount = earningRecordBean.getDataBean().getPageCount();
            //    list=recordBeanList;
                Log.d("zanZQ", "getRecordBack: "+page+":size"+pageCount);
                if (page==pageCount){ //如果总页数 等于当前请求页数 则是最后一页
                    T.showMessage(EarningRecordActivity.this, "当前是最后一页",2000);
                    //   Toast.makeText(getActivity(), "当前是最后一页", Toast.LENGTH_SHORT).show();
                }else if (page>pageCount){ //如果 请求页大于总页数 则直接返回
                    if (pageCount==0){
                        pageCount=1;
                    }
                    page=pageCount;
                    list.clear();
                    if (Is.isNoEmptyAll(adapter)){
                        adapter.notifyDataSetChanged();
                        Toast.makeText(EarningRecordActivity.this, "没有查询到相关信息", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                ArrayList<EarningRecordBean.DataBean.RecommendPostCashBean> arrayList = earningRecordBean.getDataBean().getList();
                if (arrayList!=null&&arrayList.size()>0){
                    list.clear();
                    list.addAll(arrayList);
                    if (!Is.isNoEmptyAll(adapter)){
                        adapter = new MyAdapter(this.list, EarningRecordActivity.this);
                        listView.setAdapter(adapter);

                    }else{
                        adapter.notifyDataSetChanged();

                    }
                }else{
                    list.clear();
                    if (Is.isNoEmptyAll(adapter)){
                        adapter.notifyDataSetChanged();
                        Toast.makeText(EarningRecordActivity.this, "没有查询到相关信息", Toast.LENGTH_SHORT).show();
                    }
                }


            }else{
                Toast.makeText(this, "没有更多了", Toast.LENGTH_SHORT).show();
            }
    }



    class MyAdapter extends BaseAdapter {  //提现记录适配器
        private List<EarningRecordBean.DataBean.RecommendPostCashBean> infoBeanList;
        private Context context;
        private LayoutInflater inflater;

        public MyAdapter(List<EarningRecordBean.DataBean.RecommendPostCashBean> infoBeanList, Context context) {
            this.infoBeanList = infoBeanList;
            this.context = context;
            inflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return infoBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return infoBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView==null){
                convertView=inflater.inflate(R.layout.record_item,parent,false);
                holder=new ViewHolder();
                holder.tx_money= ((TextView) convertView.findViewById(R.id.tx_money));
                holder.js_money= ((TextView) convertView.findViewById(R.id.js_money));
                holder.time= ((TextView) convertView.findViewById(R.id.time));
                holder.state= ((TextView) convertView.findViewById(R.id.state));
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }

            EarningRecordBean.DataBean.RecommendPostCashBean earningRecordBean = infoBeanList.get(position);
            holder.tx_money.setText(String .format("%.2f",earningRecordBean.getPostMoney())+"");
            double js_money = earningRecordBean.getPayMoney();
            if (js_money==0){
                holder.js_money.setText("未结算");
            }else{
                holder.js_money.setText(String .format("%.2f",js_money)+"");
            }


            int state = earningRecordBean.getSettleState();
            if (state==1){
                holder.state.setText("未支付");
                holder.state.setTextColor(getResources().getColor(R.color.colorGray));
                holder.time.setText(earningRecordBean.getPostDate());
            }else if (state==2){
                holder.state.setText("支付成功");
                holder.state.setTextColor(getResources().getColor(R.color.green));
                holder.time.setText(earningRecordBean.getSettleTime());
            }else if (state==3){
                holder.state.setText("提现中");
                holder.state.setTextColor(getResources().getColor(R.color.red));
                holder.time.setText(earningRecordBean.getPostDate());
            }else if (state==4){
                holder.state.setText("汇款中");
                holder.state.setTextColor(getResources().getColor(R.color.red));
                holder.time.setText(earningRecordBean.getPostDate());
            }


            return convertView;
        }
        class ViewHolder {
            TextView tx_money,js_money,time,state;

        }
    }
}
