package com.gdyd.qmwallet.home.view;

import android.content.Context;
import android.content.Intent;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.home.YL_kj;
import com.gdyd.qmwallet.home.model.EarningRecordBean;
import com.gdyd.qmwallet.home.model.GalleryBean;
import com.gdyd.qmwallet.home.model.JyBean;
import com.gdyd.qmwallet.home.presenter.GalleryPresenter;
import com.gdyd.qmwallet.home.presenter.MainFgPresenter;
import com.gdyd.qmwallet.mine.model.GradeBean;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.share.RWebActivity;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.Is;
import com.gdyd.qmwallet.utils.T;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GalleryActivity extends BaseActivity implements View.OnClickListener,IGalleryView, IMainFgView{

    private TextView type;
    private TextView money;
    private PullToRefreshListView listView;
    private Button submit;
    private GalleryPresenter presenter=new GalleryPresenter(this);
    private MainFgPresenter mainFgPresenter=new MainFgPresenter(this);
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
    private String lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
    private String paytype;
    private double paymoney;
    private double maxselectmoney ;
    private double minselectmoney ;

    private String merchantNo;
    private ArrayList<GalleryBean.DataBean> list=new ArrayList<>();
    private MyAdapter adapter;
    private String userName;
    private PercentRelativeLayout tip_ddsq;
    private String payType;
    private String routingType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        PercentRelativeLayout left_return = (PercentRelativeLayout) findViewById(R.id.left_return);
        left_return.setOnClickListener(this);
        type = ((TextView) findViewById(R.id.type));
        money = ((TextView) findViewById(R.id.money));
        tip_ddsq = ((PercentRelativeLayout) findViewById(R.id.tip_ddsq));
        listView = ((PullToRefreshListView) findViewById(R.id.listView));
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);// 两端刷新
        //   listView.getLoadingLayoutProxy(false, true).setLastUpdatedLabel("松开加载更多");
        initPull();
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
                initPull();
                long transTyp = EncryptionHelper.getDate();
                String transTy="1060"+transTyp+"";
                String transKe = EncryptionHelper.md5(transTy);
                presenter.getGalleryInfo(new PlaceBean("1060",transKe,0,paytype,transTyp,APPConfig.AgentID));
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              if (isFirst){
                  GalleryBean.DataBean dataBean = list.get(position - 1);
                  maxselectmoney = dataBean.getMerchantPass().getSingleMaxMoney();
                  minselectmoney = dataBean.getMerchantPass().getSingleMinMoney();
                  paymoney = getIntent().getDoubleExtra("money",0.00);
                  if (paymoney<minselectmoney){
                      Toast.makeText(GalleryActivity.this, "最低交易金额为"+ ((int) minselectmoney)+"元", Toast.LENGTH_SHORT).show();
                      return;
                  }

                  if (paymoney>maxselectmoney){

                      Toast.makeText(GalleryActivity.this, "单笔交易金额为"+ ((int) maxselectmoney)+"元内", Toast.LENGTH_SHORT).show();
                      return;
                  }

                  routingType = dataBean.getMerchantPass().getRoutingType();
                  payType = dataBean.getMerchantPass().getPayType();
                  if (dataBean.getEnable()!=1){

//                      if (payType.equals(getResources().getString(R.string.home_kj_K0))){
////                          mainFgPresenter.KJTrans(new JyBean(paymoney +""
////                                  ,merchantNo,
////                                  Integer.valueOf(dataBean.getMerchantPass().getPayClass()),payType,3,4,"",27
////                          ));
//                          Intent intent = new Intent(GalleryActivity.this, YL_kj.class);
//                          intent.putExtra(APPConfig.MERCHANTNO,merchantNo);
//                          intent.putExtra("money",paymoney+"");
//                      //    intent.putExtra("NO",object.getString("outTradeNo"));
//                          startActivity(intent);
//                      }else{
                          Toast.makeText(GalleryActivity.this, "此通道暂时关闭", Toast.LENGTH_SHORT).show();
               //       }

                      return;
                  }
                  isFirst=false;
                  tip_ddsq.setVisibility(View.VISIBLE);
                                    backgroundAlpha(0.7f);

                  if (payType.equals(getResources().getString(R.string.home_wx_Z0))){
                      mainFgPresenter.WXTrans(new JyBean(paymoney +""
                              ,merchantNo,
                              Integer.valueOf(dataBean.getMerchantPass().getPayClass()),payType,1,4,"",Integer.valueOf(routingType)
                      ));
                  }else if (payType.equals(getResources().getString(R.string.home_zfb_AO))){
                      mainFgPresenter.ZFBTrans(new JyBean(paymoney +""
                              ,merchantNo,
                              Integer.valueOf(dataBean.getMerchantPass().getPayClass()),payType,1,4,"",Integer.valueOf(routingType)));
                  }else if (payType.equals(APPConfig.KJ)){
                    // routingType="27";
                      if (routingType.trim().equals("27")){
//                          Intent intent = new Intent(GalleryActivity.this, YL_kj.class);
//                          intent.putExtra(APPConfig.MERCHANTNO,merchantNo);
//                          intent.putExtra("money",paymoney+"");
//                          startActivity(intent);
                          mainFgPresenter.KJTrans(new JyBean(paymoney +""
                                  ,merchantNo,
                                  Integer.valueOf(dataBean.getMerchantPass().getPayClass()),payType,1,4,"",Integer.valueOf(routingType)
                          ));
                      }else{
                          mainFgPresenter.KJTrans(new JyBean(paymoney +""
                                  ,merchantNo,
                                  Integer.valueOf(dataBean.getMerchantPass().getPayClass()),payType,3,4,"",Integer.valueOf(routingType)
                          ));
                      }

                  }else if (payType.equals(APPConfig.JD)){
                      mainFgPresenter.ZFBTrans(new JyBean(paymoney +""
                              ,merchantNo,
                              Integer.valueOf(dataBean.getMerchantPass().getPayClass()),payType,1,4,"",Integer.valueOf(routingType)));
                  }else if (payType.equals(APPConfig.QQ)){
                      mainFgPresenter.ZFBTrans(new JyBean(paymoney +""
                              ,merchantNo,
                              Integer.valueOf(dataBean.getMerchantPass().getPayClass()),payType,1,4,"",Integer.valueOf(routingType)));
                  }else if (payType.equals(APPConfig.YL)){
                      mainFgPresenter.ZFBTrans(new JyBean(paymoney +""
                              ,merchantNo,
                              Integer.valueOf(dataBean.getMerchantPass().getPayClass()),payType,1,4,"",Integer.valueOf(routingType)));
                  }
              }else{
                  isFirst=true;
              }

            }
        });
        submit = ((Button) findViewById(R.id.submit));
        paytype = getIntent().getStringExtra("type");
        paymoney = getIntent().getDoubleExtra("money",0.00);
        userName = getIntent().getStringExtra("userName");
        merchantNo = getIntent().getStringExtra(APPConfig.MERCHANTNO);
        if (paytype.equals(getResources().getString(R.string.home_wx_Z0))){
            type.setText(getResources().getString(R.string.home_wx));
        }else if (paytype.equals(getResources().getString(R.string.home_zfb_AO))){
            type.setText(getResources().getString(R.string.home_zfb));
        }else if (paytype.equals(APPConfig.KJ)){
            type.setText(getResources().getString(R.string.home_kj));
        }else if (paytype.equals(APPConfig.JD)){
            type.setText(getResources().getString(R.string.home_JD));
        }else if (paytype.equals(APPConfig.QQ)){
            type.setText(getResources().getString(R.string.home_QQ));
        }else if (paytype.equals(APPConfig.YL)){
            type.setText(getResources().getString(R.string.home_YL));
        }
        money.setText(getResources().getString(R.string.CNY)+String .format("%.2f",paymoney) +"");
        long transTyp = EncryptionHelper.getDate();
        String transTy="1060"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);
        presenter.getGalleryInfo(new PlaceBean("1060",transKe,0,paytype,transTyp,APPConfig.AgentID));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                finish();
                break;
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
                list.addAll(gallerys);
                if (!Is.isNoEmptyAll(adapter)){
                    adapter = new MyAdapter(this.list, GalleryActivity.this);
                    listView.setAdapter(adapter);

                }else{
                    adapter.notifyDataSetChanged();
                }
            }else{
                list.clear();
                if (Is.isNoEmptyAll(adapter)){
                    adapter.notifyDataSetChanged();
                    Toast.makeText(GalleryActivity.this, "没有查询到相关信息", Toast.LENGTH_SHORT).show();
                }
            }


        }else{
            Toast.makeText(this, "没有更多了", Toast.LENGTH_SHORT).show();
        }
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
    public void getWXInfo(String info) {  //微信下单回调
        tip_ddsq.setVisibility(View.GONE);
        backgroundAlpha(1f);
        Log.d("zanZQ", "getWXInfo: "+info);
        //  Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        isFirst=true;
        if (info!=null&&!info.equals("")){
            try {
                JSONObject object = new JSONObject(info);
                String code = object.getString("code");

                if (code.equals("00")){
                    String pay_url = object.getString("pay_url");
                    // String format = String.format(UrlConfig.Z_OR_W, userName, moneyInfo, object.getString("pay_url"), "Z0");
                    Toast.makeText(this, "下单成功", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(GalleryActivity.this, PayCodeActivity.class)
                            .putExtra("url", pay_url)
                            .putExtra(APPConfig.TITLE,getResources().getString(R.string.home_wx))
                            .putExtra("name",userName).putExtra("money",String .format("%.2f",paymoney)+""));
                    //  startActivity(new Intent(PayActivity.this, WebViewActivity.class).putExtra("url",format).putExtra(APPConfig.TITLE,getResources().getString(R.string.home_wx)));
                }else{
                    String msg = object.getString("msg");
                    if (msg==null||msg.equals("")){
                        msg="下单失败！";
                    }
                    if (msg.equals("下单成功")){
                        msg="下单失败！";
                    }
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "下单失败！", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "下单失败！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getZFBInfo(String info) { // 支付宝下单回调
        tip_ddsq.setVisibility(View.GONE);
        backgroundAlpha(1f);
        isFirst=true;
        Log.d("zanZQ", "getZFBInfo: "+info);
        if (info!=null&&!info.equals("")){
            try {
                JSONObject object = new JSONObject(info);
                String code = object.getString("code");
                if (code.equals("00")){
                    String pay_url = object.getString("pay_url");
                    //  String format = String.format(UrlConfig.Z_OR_W, userName, moneyInfo, object.getString("pay_url"), "A0");
                    Toast.makeText(this, "下单成功", Toast.LENGTH_SHORT).show();
                    if (payType.equals(APPConfig.ZFB)){
                        startActivity(new Intent(GalleryActivity.this, PayCodeActivity.class)
                                .putExtra("url", pay_url)
                                .putExtra(APPConfig.TITLE,getResources().getString(R.string.home_zfb))
                                .putExtra("name",userName).putExtra("money",String .format("%.2f",paymoney)+""));
                    }else if (payType.equals(APPConfig.QQ)){
                        startActivity(new Intent(GalleryActivity.this, PayCodeActivity.class)
                                .putExtra("url", pay_url)
                                .putExtra(APPConfig.TITLE,getResources().getString(R.string.home_QQ))
                                .putExtra("name",userName).putExtra("money",String .format("%.2f",paymoney)+""));
                    }else if (payType.equals(APPConfig.JD)){
                        startActivity(new Intent(GalleryActivity.this, PayCodeActivity.class)
                                .putExtra("url", pay_url)
                                .putExtra(APPConfig.TITLE,getResources().getString(R.string.home_JD))
                                .putExtra("name",userName).putExtra("money",String .format("%.2f",paymoney)+""));
                    }else if (payType.equals(APPConfig.YL)){
                        startActivity(new Intent(GalleryActivity.this, PayCodeActivity.class)
                                .putExtra("url", pay_url)
                                .putExtra(APPConfig.TITLE,getResources().getString(R.string.home_YL))
                                .putExtra("name",userName).putExtra("money",String .format("%.2f",paymoney)+""));
                    }

                }else{
                    String msg = object.getString("msg");
                    if (msg==null||msg.equals("")){
                        msg="下单失败！";
                    }
                    if (msg.equals("下单成功")){
                        msg="下单失败！";
                    }
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(this, "下单失败！", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this, "下单失败！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getKJInfo(String info) {
        tip_ddsq.setVisibility(View.GONE);
        backgroundAlpha(1f);
        Log.d("zanZQ", "getKJInfo: "+info);
        //  Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        isFirst=true;
        if (info!=null&&!info.equals("")){
            try {
                JSONObject object = new JSONObject(info);
                String code = object.getString("code");
                //  String msg = object.getString("msg");
                //    String pay_url = object.getString("pay_url");
                if (code.equals("00")){
                    Toast.makeText(this, "下单成功", Toast.LENGTH_SHORT).show();
                    if (routingType.equals("27")){
                        Intent intent = new Intent(GalleryActivity.this, YL_kj.class);
                        intent.putExtra(APPConfig.MERCHANTNO,merchantNo);
                        intent.putExtra("money",paymoney+"");
                        intent.putExtra("NO",object.getString("outTradeNo"));
                        startActivity(intent);
                    }else{
                        startActivity(new Intent(GalleryActivity.this, RWebActivity.class).
                                putExtra("url", object.getString("pay_url")).
                                putExtra(APPConfig.TITLE,getResources().getString(R.string.home_kj))
                                .putExtra("type",1) .putExtra("name",userName).putExtra("money",String .format("%.2f",paymoney)+""));

                    }
              }else{
                    String msg = object.getString("msg");
                    if (msg==null||msg.equals("")){
                        msg="下单失败！";
                    }
                    if (msg.equals("下单成功")){
                        msg="下单失败！";
                    }
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "下单失败！", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "下单失败！", Toast.LENGTH_SHORT).show();
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
           ViewHolder holder;
            if (convertView==null){
                convertView=inflater.inflate(R.layout.gallery_item,parent,false);
                holder=new ViewHolder();
                convertView.setLayoutParams(new AbsListView.LayoutParams(
                        AbsListView.LayoutParams.MATCH_PARENT, listView
                        .getHeight() /5 ));
                holder.title= ((TextView) convertView.findViewById(R.id.title));
                holder.monry_size= ((TextView) convertView.findViewById(R.id.money_size));
                holder.face= ((ImageView) convertView.findViewById(R.id.img));
                holder.img_yes_no= ((ImageView) convertView.findViewById(R.id.img_yes_no));
                holder.back= (PercentRelativeLayout) convertView.findViewById(R.id.background);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            GalleryBean.DataBean a = infoBeanList.get(position);
            GalleryBean.DataBean.MerchantPassBean dataBean = a.getMerchantPass();
            if (a.getEnable()!=1){
                holder.back.setBackgroundColor(getResources().getColor(R.color.fen_gray));
            }else{
                holder.back.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            }
            String payType = dataBean.getPayType();
            if (getResources().getString(R.string.home_wx_Z0).equals(payType)) {
                holder.face.setImageResource(R.drawable.icon_wx_color);
            } else if ( getResources().getString(R.string.home_zfb_AO).equals(payType)){
                holder.face.setImageResource(R.drawable.icon_zfb_color);
            }else if (APPConfig.KJ.equals(payType)){
                holder.face.setImageResource(R.drawable.icon_kj_color);
            }else if (APPConfig.QQ.equals(payType)){
                holder.face.setImageResource(R.drawable.icon_qq);
            }else if (APPConfig.JD.equals(payType)){
                holder.face.setImageResource(R.drawable.icon_jd);
            }else if (APPConfig.YL.equals(payType)){
                holder.face.setImageResource(R.drawable.icon_kj_color);
            }
            String remark = dataBean.getRemark();

            holder.title.setText(remark.substring(0,remark.lastIndexOf("|")));
            holder.monry_size.setText(remark.substring(remark.lastIndexOf("|")+1,remark.length()));
            return convertView;
        }
        class ViewHolder {
            ImageView face,img_yes_no;
            TextView title,monry_size;
            PercentRelativeLayout back;
        }
    }
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 0f-1f,1f为不透明
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp =getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0 1f为不透明
        getWindow().setAttributes(lp);
    }
}
