package com.gdyd.qmwallet.mine;

import android.content.Context;
import android.graphics.Color;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.home.model.EarningRecordBean;
import com.gdyd.qmwallet.home.view.EarningRecordActivity;
import com.gdyd.qmwallet.mine.model.MapBean;
import com.gdyd.qmwallet.mine.model.PhotoBean;
import com.gdyd.qmwallet.mine.model.RateBean;
import com.gdyd.qmwallet.mine.presenter.CheckPresenter;
import com.gdyd.qmwallet.mine.view.ICheckUpdateView;
import com.gdyd.qmwallet.trans.MsgBean;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.NumberChangeToChinese;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RateActivity extends BaseActivity implements View.OnClickListener,ICheckUpdateView {

    private TextView title;
    private PercentRelativeLayout left_return;
    private CheckPresenter presenter=new CheckPresenter(this);
    private String machNo;
    private PullToRefreshListView listView;
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
    private String lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earning_record);
        title = ((TextView) findViewById(R.id.title));
        title.setText("交易费率");
        left_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        left_return.setOnClickListener(this);
        machNo = getIntent().getStringExtra(APPConfig.MERCHANTNO);
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
                String transTy="1062"+transTyp+"";
                String transKe = EncryptionHelper.md5(transTy);
                presenter.getRateInfo(new PhotoBean("1062",transKe,machNo,transTyp,APPConfig.AgentID));
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        long transTyp = EncryptionHelper.getDate();
        String transTy="1062"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);
        presenter.getRateInfo(new PhotoBean("1062",transKe,machNo,transTyp,APPConfig.AgentID));
   //     presenter.getRateInfo(new PhotoBean("1062","99336FD109D703CA249504284D5940E2",machNo));
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
    public void CheckUpdateBack(String a) {

    }

    @Override
    public void getRateInfo(RateBean rateBean) {
        listView.onRefreshComplete();
     if (rateBean!=null&&rateBean.getNResul()==1){
         ArrayList<RateBean.DataBean> list = rateBean.getList();
         if (list.size()>0){
           ArrayList<MapBean > mapBeen=new ArrayList<>();
             for (int i = 0; i < list.size(); i++) {
                 RateBean.DataBean bean = list.get(i);
                     boolean a=true;
                     if (mapBeen.size()==0){
                         MapBean mapBean = new MapBean();
                         ArrayList<MapBean.RateDetails> detailsArrayList=new ArrayList<>();
                         mapBean.setName(bean.getPayType());
                         detailsArrayList.add(new MapBean.RateDetails(bean.getPayClass(),bean));
                         mapBean.setList(detailsArrayList);
//                         HashMap<String, RateBean.DataBean> hashMap = new HashMap<>();
//                         hashMap.put(bean.getPayClass(),bean);
                    //     mapBean.setHashMap(hashMap);
                         mapBeen.add(mapBean);
                     }else{
                         for (int j = 0; j < mapBeen.size(); j++) {
                             if (mapBeen.get(j).getName().equals(bean.getPayType())){
                             //    HashMap<String, RateBean.DataBean> hashMap = mapBeen.get(j).getHashMap();
                                 ArrayList<MapBean.RateDetails> rateDetailses = mapBeen.get(j).getList();
                                 rateDetailses.add(new MapBean.RateDetails(bean.getPayClass(),bean));
                            //     hashMap.put(bean.getPayClass(),bean);
                                 a=false;
                                 continue;
                             }
                         }
                         if (a){
                             MapBean mapBean = new MapBean();
                             mapBean.setName(bean.getPayType());
//                             HashMap<String, RateBean.DataBean> hashMap = new HashMap<>();
//                             hashMap.put(bean.getPayClass(),bean);
//                             mapBean.setHashMap(hashMap);
                             ArrayList<MapBean.RateDetails> detailsArrayList=new ArrayList<>();
                             mapBean.setName(bean.getPayType());
                             detailsArrayList.add(new MapBean.RateDetails(bean.getPayClass(),bean));
                             mapBean.setList(detailsArrayList);
                             mapBeen.add(mapBean);
                         }

                     }



             }
            listView.setAdapter(new MyAdapter(mapBeen,RateActivity.this));
         }
     }
    }
    class MyAdapter extends BaseAdapter {  //提现记录适配器
        private    ArrayList<MapBean > mapBeen;
        private Context context;
        private LayoutInflater inflater;

        public MyAdapter(    ArrayList<MapBean > mapBeen, Context context) {
            this.mapBeen = mapBeen;
            this.context = context;
            inflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mapBeen.size();
        }

        @Override
        public Object getItem(int position) {
            return mapBeen.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           ViewHolder holder;
            if (convertView==null){
                convertView=inflater.inflate(R.layout.rate_item,parent,false);
                holder=new MyAdapter.ViewHolder();
                holder.title= ((TextView) convertView.findViewById(R.id.title));
//                holder.type0= ((TextView) convertView.findViewById(R.id.type0));
//                holder.type1= ((TextView) convertView.findViewById(R.id.type1));
//                holder.rate0= ((TextView) convertView.findViewById(R.id.rate0));
//                holder.rate1= ((TextView) convertView.findViewById(R.id.rate1));
//                holder.js0= ((TextView) convertView.findViewById(R.id.js0));
//                holder.js1= ((TextView) convertView.findViewById(R.id.js1));
//                holder.money_size0= ((TextView) convertView.findViewById(R.id.money_size0));
//                holder.money_size1= ((TextView) convertView.findViewById(R.id.money_size1));
                holder.layout_2=((LinearLayout) convertView.findViewById(R.id.layout_2));
             //   holder.layout_3=((LinearLayout) convertView.findViewById(R.id.layout_3));
                holder.face= (ImageView) convertView.findViewById(R.id.face);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            MapBean mapBean = mapBeen.get(position);
        //    HashMap<String, RateBean.DataBean> hashMap = mapBean.getHashMap();
            if (getResources().getString(R.string.home_wx_Z0).equals(mapBean.getName())) {
                holder.face.setImageResource(R.drawable.icon_wx_color);
                holder.title.setText(getResources().getString(R.string.home_wx));
            } else if ( getResources().getString(R.string.home_zfb_AO).equals(mapBean.getName())){
                holder.face.setImageResource(R.drawable.icon_zfb_color);
                holder.title.setText(getResources().getString(R.string.home_zfb));
            }else if (getResources().getString(R.string.home_qq).equals(mapBean.getName())){
                holder.face.setImageResource(R.drawable.icon_qq_color);
                holder.title.setText(getResources().getString(R.string.home_QQ));
            }else if (getResources().getString(R.string.home_jd).equals(mapBean.getName())){
                holder.face.setImageResource(R.drawable.icon_jd_color);
                holder.title.setText(getResources().getString(R.string.home_JD));
            }else if (getResources().getString(R.string.home_kj_K0).equals(mapBean.getName())){
                holder.face.setImageResource(R.drawable.icon_kj_color);
                holder.title.setText(getResources().getString(R.string.home_kj));
            }else if(getResources().getString(R.string.home_yl).equals(mapBean.getName())){
                holder.face.setImageResource(R.drawable.icon_kj_color);
                holder.title.setText(getResources().getString(R.string.home_YL));
            }
            ArrayList<MapBean.RateDetails> list = mapBean.getList();
            holder.layout_2.removeAllViews();
            for (int i = 0; i < list.size(); i++) {
                //创建一个新的ImageView
//            ImageView iv = new ImageView(getActivity());
//            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            iv.setImageResource(imgs[i]);
//            imageViews.add(iv);
                View inflate = inflater.inflate(R.layout.item_item, null);
                TextView type = (TextView) inflate.findViewById(R.id.type1);
                TextView rate = (TextView) inflate.findViewById(R.id.rate1);
                TextView js = (TextView) inflate.findViewById(R.id.js1);
                TextView money_size = (TextView) inflate.findViewById(R.id.money_size1);
                MapBean.RateDetails rateDetails = list.get(i);
                if (rateDetails.getType().equals("1")){
                    type.setText("D+0");
                }else {
                    type.setText("T+1");
                }

                rate.setText(rateDetails.getDataBean().getChannel_Discount()+"%");
                                        String endTime = rateDetails.getDataBean().getEndTime();
                        String startTime = rateDetails.getDataBean().getStartTime();
                        endTime =endTime.substring(endTime.length()-8,endTime.length()-3);
                        startTime =  startTime.substring(startTime.length()-8,startTime.length()-3);

                js.setText(startTime+"-"+endTime);
                String min= "10";
                        try {
                            String remark = rateDetails.getDataBean().getRemark();
                            String substring = remark.substring(remark.lastIndexOf("|"), remark.length());
                            min = "10";
                            if (substring.contains("-")){
                                min = substring.substring(substring.indexOf("单笔")+2, substring.lastIndexOf("-"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                money_size.setText(min+"-"+(int) rateDetails.getDataBean().getSingleMaxMoney());
//                LinearLayout view = new LinearLayout(context);
//                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()));
//                lp.leftMargin = 2;
//                view.setOrientation(LinearLayout.HORIZONTAL);
//                //设置view的宽高左边距等参数
//                view.setLayoutParams(lp);
//            //    holder.layout_2.addView(view);
//                for (int j = 0; j < 4; j++) {
//                    TextView textView = new TextView(context);
//                    ViewGroup.LayoutParams lp2 = new ViewGroup.LayoutParams(
//                            view.getWidth()/4,
//                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()));
//                    textView.setLayoutParams(lp2);
//                    textView.setGravity(Gravity.CENTER);
//                    textView.setTextColor(getResources().getColor(R.color.black));
//                    textView.setTextSize(14);
//                    if (j==0){
//                        textView.setText("D+0");
//                    }else if (j==1){
//                        textView.setText(rateDetails.getDataBean().getChannel_Discount()+"%");
//                    }else  if (j==2){
//
//                        String endTime = rateDetails.getDataBean().getEndTime();
//                        String startTime = rateDetails.getDataBean().getStartTime();
//                        endTime =endTime.substring(endTime.length()-8,endTime.length()-3);
//                        startTime =  startTime.substring(startTime.length()-8,startTime.length()-3);
//                        textView.setText(startTime+"-"+endTime);
//                    }else  if (j==3){
//                        String min= "10";
//                        try {
//                            String remark = rateDetails.getDataBean().getRemark();
//                            String substring = remark.substring(remark.lastIndexOf("|"), remark.length());
//                            min = "10";
//                            if (substring.contains("-")){
//                                min = substring.substring(substring.indexOf("单笔")+2, substring.lastIndexOf("-"));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                        textView.setText(min+"-"+(int) rateDetails.getDataBean().getSingleMaxMoney());
//                    }
//                    view.addView(textView);
//                }
                holder.layout_2.addView(inflate);
            }
//            holder.layout_2.setVisibility(View.GONE);
//            holder.layout_3.setVisibility(View.GONE);
//         if (hashMap.containsKey("1")){
//             holder.layout_2.setVisibility(View.VISIBLE);
//             RateBean.DataBean dataBean = hashMap.get("1");
//             String remark = dataBean.getRemark();
//             String endTime = dataBean.getEndTime();
//             String startTime = dataBean.getStartTime();
//             endTime =endTime.substring(endTime.length()-8,endTime.length()-3);
//             startTime =  startTime.substring(startTime.length()-8,startTime.length()-3);
//             holder.type0.setText("D+0");
//             holder.rate0.setText(dataBean.getChannel_Discount()+"%");
//             holder.js0.setText(startTime+"-"+endTime);
//             String min= "10";
//             try {
//                 String substring = remark.substring(remark.lastIndexOf("|"), remark.length());
//                 min = "10";
//                 if (substring.contains("-")){
//                     min = substring.substring(substring.indexOf("单笔")+2, substring.lastIndexOf("-"));
//                 }
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }
//
//             holder.money_size0.setText(min+"-"+(int) dataBean.getSingleMaxMoney());
//         }
//            if (hashMap.containsKey("2")){
//                RateBean.DataBean dataBean1 = hashMap.get("2");
//                String endTime = dataBean1.getEndTime();
//                String startTime = dataBean1.getStartTime();
//                String remark = dataBean1.getRemark();
//                endTime =endTime.substring(endTime.length()-8,endTime.length()-3);
//                startTime =  startTime.substring(startTime.length()-8,startTime.length()-3);
//                holder.layout_3.setVisibility(View.VISIBLE);
//                holder.type1.setText("T+1");
//                holder.rate1.setText(dataBean1.getChannel_Discount()+"%");
//                holder.js1.setText(startTime+"-"+endTime);
//                String min= "10";
//                try {
//                    String substring = remark.substring(remark.lastIndexOf("|"), remark.length());
//                    min = "10";
//                    if (substring.contains("-")){
//                        min = substring.substring(substring.indexOf("单笔")+2, substring.lastIndexOf("-"));
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                holder.money_size1.setText(min+"-"+(int) dataBean1.getSingleMaxMoney());
//            }


            return convertView;
        }
        class ViewHolder {
            TextView type0,type1,title,rate0,rate1,js0,js1,money_size0,money_size1;
            ImageView face;
          LinearLayout layout_2,layout_3;
        }
    }

    @Override
    public void checkUpdateNewNotice(String notice){


    }
}
