package com.gdyd.qmwallet.home;

import android.content.Context;
import android.content.Intent;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.home.model.GalleryBean;
import com.gdyd.qmwallet.home.presenter.GalleryPresenter;
import com.gdyd.qmwallet.home.view.GalleryActivity;
import com.gdyd.qmwallet.home.view.IGalleryView;
import com.gdyd.qmwallet.home.view.PayActivity;
import com.gdyd.qmwallet.mine.model.GradeBean;
import com.gdyd.qmwallet.mine.model.PhotoBean;
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

public class PayTypeActivity extends BaseActivity implements View.OnClickListener ,IGalleryView {

    private TextView title;
    private PercentRelativeLayout left_return;
    private PullToRefreshListView listView;
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
    private String lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
    private GalleryPresenter presenter=new GalleryPresenter(this);
    private ArrayList<GalleryBean.DataBean> list=new ArrayList<>();
    private MyAdapter adapter;
    private LoginInfoBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earning_record);
        title = ((TextView) findViewById(R.id.title));
        title.setText("选择支付类型");
        left_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        left_return.setOnClickListener(this);
        bean = ((LoginInfoBean) getIntent().getSerializableExtra(APPConfig.LOGIN_INFO));
        listView = ((PullToRefreshListView) findViewById(R.id.listView));
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);// 两端刷新
        //  lv.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("上拉刷新");
        //   lv.getLoadingLayoutProxy(false, true).setLastUpdatedLabel("松开加载更多");
        initPull();


        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
                initPull();
                long transTyp = EncryptionHelper.getDate();
                String transTy="1060"+transTyp;
                String transKe = EncryptionHelper.md5(transTy);
                presenter.getGalleryInfo(new PlaceBean("1060",transKe,0,"",transTyp,APPConfig.AgentID));

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GalleryBean.DataBean dataBean = list.get(position - 1);
                String paytype = dataBean.getMerchantPass().getPayType();
                String remark = dataBean.getMerchantPass().getRemark();
                String min= "10";
                try {
                    String substring = remark.substring(remark.lastIndexOf("|"), remark.length());
                    min = "10";
                    if (substring.contains("-")){
                         min = substring.substring(substring.indexOf("单笔")+2, substring.lastIndexOf("-"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                startActivity(new Intent(PayTypeActivity.this, PayActivity.class).putExtra(APPConfig.LOGIN_INFO,bean)
                        .putExtra(APPConfig.TRANS_TYPE,paytype).putExtra(APPConfig.max,dataBean.getMerchantPass().getSingleMaxMoney()+"")
                .putExtra(APPConfig.min,min));
//
//                if (getResources().getString(R.string.home_wx_Z0).equals(paytype)) {
////                    holder.face.setImageResource(R.drawable.icon_wx_big);
////                    holder.title.setText(getResources().getString(R.string.home_wx));
//                    startActivity(new Intent(PayTypeActivity.this, PayActivity.class).putExtra(APPConfig.LOGIN_INFO,bean).putExtra(APPConfig.TRANS_TYPE,APPConfig.WX));
//                } else if ( getResources().getString(R.string.home_zfb_AO).equals(paytype)){
////                    holder.face.setImageResource(R.drawable.icon_zfb_big);
////                    holder.title.setText(getResources().getString(R.string.home_zfb));
//                      startActivity(new Intent(PayTypeActivity.this, PayActivity.class).putExtra(APPConfig.LOGIN_INFO,bean).putExtra(APPConfig.TRANS_TYPE,APPConfig.ZFB));
//
//                }else if (getResources().getString(R.string.home_qq).equals(paytype)){
////                    holder.face.setImageResource(R.drawable.icon_qq);
////                    holder.title.setText(getResources().getString(R.string.home_QQ));
//                    startActivity(new Intent(PayTypeActivity.this, PayActivity.class).putExtra(APPConfig.LOGIN_INFO,bean).putExtra(APPConfig.TRANS_TYPE,APPConfig.QQ));
//
//                    Toast.makeText(PayTypeActivity.this, "暂未开通", Toast.LENGTH_SHORT).show();
//                }else if (getResources().getString(R.string.home_jd).equals(paytype)){
////                    holder.face.setImageResource(R.drawable.icon_jd);
////                    holder.title.setText(getResources().getString(R.string.home_JD));
//                    startActivity(new Intent(PayTypeActivity.this, PayActivity.class).putExtra(APPConfig.LOGIN_INFO,bean).putExtra(APPConfig.TRANS_TYPE,APPConfig.JD));
//
//                    Toast.makeText(PayTypeActivity.this, "暂未开通", Toast.LENGTH_SHORT).show();
//                }else{
//                    startActivity(new Intent(PayTypeActivity.this, PayActivity.class).putExtra(APPConfig.LOGIN_INFO,bean).putExtra(APPConfig.TRANS_TYPE,APPConfig.YL));
//                    Toast.makeText(PayTypeActivity.this, "暂未开通", Toast.LENGTH_SHORT).show();
//                }
            }
        });
        long transTyp = EncryptionHelper.getDate();
        String transTy="1060"+transTyp;
        String transKe = EncryptionHelper.md5(transTy);
        presenter.getGalleryInfo(new PlaceBean("1060",transKe,0,"",transTyp,APPConfig.AgentID));
       // presenter.getGalleryInfo(new PlaceBean("1060","322A42135A90FDCC709F93EEE00551D5",0,""));
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
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_return:
                finish();
                return;
        }
    }

    @Override
    public void UpdateGalleryInfo(GalleryBean galleryBean) {
        listView.onRefreshComplete(); // 停止刷新动画
        if (galleryBean!=null&&galleryBean.getnResul()==1){
            ArrayList<GalleryBean.DataBean> gallerys = galleryBean.getGallerys();
            if (gallerys!=null&&gallerys.size()>0){
                list.clear();
                for (int i = 0; i < gallerys.size(); i++) {
                  if (gallerys.get(i).getMerchantPass().getPayClass().equals("1")){
                      boolean a=true;
                      GalleryBean.DataBean dataBean = gallerys.get(i);
                      String payType = dataBean.getMerchantPass().getPayType();
                      if (list.size()==0){
                          list.add(dataBean);

                      }else{
                          for (int j = 0; j < list.size(); j++) {
                              if (list.get(j).getMerchantPass().getPayType().equals(payType)){
                                  a=false;
                                  break;
                              }


                          }
                          if (a){
                              list.add(dataBean);
                          }
                      }
                  }
                }
                for (int i = 0; i < gallerys.size(); i++) {
                    if (gallerys.get(i).getMerchantPass().getPayClass().equals("2")){
                        boolean a=true;
                        GalleryBean.DataBean dataBean = gallerys.get(i);
                        String payType = dataBean.getMerchantPass().getPayType();
                        if (list.size()==0){
                            list.add(dataBean);

                        }else{
                            for (int j = 0; j < list.size(); j++) {
                                if (list.get(j).getMerchantPass().getPayType().equals(payType)){
                                    a=false;
                                    break;
                                }


                            }
                            if (a){
                                list.add(dataBean);
                            }
                        }
                    }
                }
                if (!Is.isNoEmptyAll(adapter)){
                    adapter = new MyAdapter(this.list, PayTypeActivity.this);
                    listView.setAdapter(adapter);

                }else{
                    adapter.notifyDataSetChanged();
                }
            }else{
                list.clear();
                if (Is.isNoEmptyAll(adapter)){
                    adapter.notifyDataSetChanged();
                    Toast.makeText(PayTypeActivity.this, "没有查询到相关信息", Toast.LENGTH_SHORT).show();
                }
            }


        }else{
            Toast.makeText(this, "没有更多了", Toast.LENGTH_SHORT).show();
        }
    }
    class MyAdapter extends BaseAdapter {  //提现记录适配器
        private List<GalleryBean.DataBean> infoBeanList;
        private Context context;
        private LayoutInflater inflater;

        public MyAdapter(List<GalleryBean.DataBean> infoBeanList, Context context) {
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
            MyAdapter.ViewHolder holder;
            if (convertView==null){
                convertView=inflater.inflate(R.layout.paytype_item,parent,false);
                holder=new MyAdapter.ViewHolder();

                holder.face= (ImageView) convertView.findViewById(R.id.face);
                holder.title= (TextView) convertView.findViewById(R.id.title);
                convertView.setTag(holder);
            }else{
                holder= (MyAdapter.ViewHolder) convertView.getTag();
            }
            String paytype = list.get(position).getMerchantPass().getPayType();
            if (getResources().getString(R.string.home_wx_Z0).equals(paytype)) {
                holder.face.setImageResource(R.drawable.icon_wx_big);
                holder.title.setText(getResources().getString(R.string.home_wx));
            } else if ( getResources().getString(R.string.home_zfb_AO).equals(paytype)){
                holder.face.setImageResource(R.drawable.icon_zfb_big);
                holder.title.setText(getResources().getString(R.string.home_zfb));
            }else if (getResources().getString(R.string.home_qq).equals(paytype)){
                holder.face.setImageResource(R.drawable.icon_qq);
                holder.title.setText(getResources().getString(R.string.home_QQ));
            }else if (getResources().getString(R.string.home_jd).equals(paytype)){
                holder.face.setImageResource(R.drawable.icon_jd);
                holder.title.setText(getResources().getString(R.string.home_JD));
            }else if(getResources().getString(R.string.home_yl).equals(paytype)){
                holder.face.setImageResource(R.drawable.icon_kj_big);
                holder.title.setText(getResources().getString(R.string.home_YL));
            }

            return convertView;
        }
        class ViewHolder {
            ImageView face;
            TextView title;

        }
    }
}
