package com.gdyd.qmwallet.home;

/**
 * Created by hebei on 2017/6/22.
 */
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.percent.PercentRelativeLayout;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.MainActivity;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.home.model.ProfitBean;
import com.gdyd.qmwallet.home.model.ProfitBean.DataBean.RecommendProfitViewBean;
import com.gdyd.qmwallet.home.model.ProfitElvBean;
import com.gdyd.qmwallet.home.model.YuDianRecordBean;
import com.gdyd.qmwallet.home.presenter.ProfitPresenter;
import com.gdyd.qmwallet.home.view.IProfitView;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.receiver.JGPush;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.Is;
import com.gdyd.qmwallet.utils.TimeUtils;
import com.gdyd.qmwallet.utils.Tool;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProfitItemDetailsActivity extends BaseActivity implements IProfitView,View.OnClickListener{
    private ProfitPresenter presenter=new ProfitPresenter(this);
    private TextView title;
    private PercentRelativeLayout left_return;
    private LoginInfoBean bean;
    private PopupWindow popupWindow;
    private View contentView;
    private int groupType;
    private PercentRelativeLayout layout_body;
    private PercentRelativeLayout layout_queryorder_start_date_tip;
    private PercentRelativeLayout layout_queryorder_end_date_tip;
    private PercentRelativeLayout layout_queryorder_pay_type_tip;
    private PercentRelativeLayout layout_queryorder_pay_status_tip;
    private TextView start_search;
    private TextView text_start_date;
    private TextView text_end_date;
    private EditText queryorder_search_name;
    private EditText queryorder_search_phone;
    private DatePickerDialog dateDialog;
    private DatePickerDialog enddateDialog;
    private int page=1;
    private  int pageSize = 30;
    private String machNo;
    private String startDate = "";
    private String endDate = "";
    private TextView countMoney;
    private TextView countProfit;
    private PullToRefreshListView lv;
    private TextView profit_money;
    private PercentRelativeLayout image_return;
    private ImageView left_title;
    ProfitItemDetailsActivity.ProfitAdapter profitAdapter;
    private Spinner spinner_pay_type;
    private Spinner spinner_pay_status;
    private String type = "";
    private int  StateType=0;
    private  ArrayList<ProfitBean.DataBean.RecommendProfitViewBean> profitList=new ArrayList<>();
    private TextView date_scope;
    private SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
    private String lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
    // private List<QueryBean> listTrans;
    //  private MyElvAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earning_record);
        title = ((TextView) findViewById(R.id.title));
        title.setText("分润明细");
        left_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        left_return.setOnClickListener(this);
        bean = ((LoginInfoBean) getIntent().getSerializableExtra(APPConfig.LOGIN_INFO));
        lv = ((PullToRefreshListView) findViewById(R.id.listView));
        lv.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        lv.setMode(PullToRefreshBase.Mode.BOTH);// 两端刷新


        //  lv.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("上拉刷新");
        //   lv.getLoadingLayoutProxy(false, true).setLastUpdatedLabel("松开加载更多");
        initPull();

        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page=1;
                lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
                initPull();
                inRequestData();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
                initPull();
                inRequestData();
            }
        });

        String searchTime = getIntent().getExtras().getString("profitDetailSearchTime");
        machNo = getIntent().getStringExtra("merchantNo");
        startDate=searchTime;
        endDate=searchTime;
        inRequestData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_return:
                finish();
                return;
        }
    }

    private void inRequestData() {
        pageSize=30;
        long transTyp = EncryptionHelper.getDate();
        String transTy="1079"+transTyp;
        String transKe = EncryptionHelper.md5(transTy);
        presenter.getProfitData(new PlaceBean("1079",transKe,machNo,type,StateType,startDate,endDate,page,pageSize,transTyp));
    }

    private void initPull() {
        ILoadingLayout layoutProxy = lv.getLoadingLayoutProxy(true,false);
        layoutProxy.setLastUpdatedLabel("上次刷新时间:" + lastUpdateTime);
        layoutProxy.setPullLabel("下拉刷新");
        layoutProxy.setRefreshingLabel("正在加载数据");
        layoutProxy.setReleaseLabel("手指释放刷新数据");
        ILoadingLayout endLayout  = lv.getLoadingLayoutProxy(false,true);
        endLayout.setLastUpdatedLabel("上次刷新时间:" + lastUpdateTime);
        endLayout.setPullLabel("上拉刷新");
        endLayout.setRefreshingLabel("正在加载数据");
        endLayout.setReleaseLabel("手指释放刷新数据");
    }

    @Override
    public void UpDataInfo(ProfitBean profitBean) {

        lv.onRefreshComplete();
        if (profitBean==null){
            profitList.clear();
            if (Is.isNoEmptyAll(profitAdapter)){
                profitAdapter.notifyDataSetChanged();
                Toast.makeText(ProfitItemDetailsActivity.this, "暂无信息", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        groupType = profitBean.getDataBean().getGroupType();

        if (groupType==1){
            profitList.clear();
        }
        if (profitBean.getNResul()==1){
            if (profitBean.getDataBean().getGroupType()==2){
                int nPageCount = profitBean.getDataBean().getPageCount();
                if (page==nPageCount){
                    profitList.clear();
                    Toast.makeText(ProfitItemDetailsActivity.this, "当前是最后一页", Toast.LENGTH_SHORT).show();
                }else if (page>nPageCount){
                    if (nPageCount==0){
                        nPageCount=1;
                    }
                    page=nPageCount;
                    Toast.makeText(ProfitItemDetailsActivity.this, "没有查询到信息..", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            ArrayList<ProfitBean.DataBean.RecommendProfitViewBean> list = profitBean.getDataBean().getList();
            if (list!=null&&list.size()>0){

                for (int i = 0; i < list.size(); i++) {
                    ProfitBean.DataBean.RecommendProfitViewBean recommendProfitViewBean = list.get(i);
                    if (recommendProfitViewBean.getSettleProfit()!=0){
                        profitList.add(list.get(i));
                    }
                }
                if (!Is.isNoEmptyAll(profitAdapter)){
                    profitAdapter = new ProfitAdapter(ProfitItemDetailsActivity.this, profitList);
                    lv.getRefreshableView().setAdapter(profitAdapter);
                } else{
                    profitAdapter.notifyDataSetChanged();
                }
            }
        }else{
            profitList.clear();
            if (Is.isNoEmptyAll(profitAdapter)){
                profitAdapter.notifyDataSetChanged();
                Toast.makeText(ProfitItemDetailsActivity.this, "暂无信息", Toast.LENGTH_SHORT).show();
            }
        }
    }

   @Override
   public void getDataYuDianRecord(YuDianRecordBean yuDianRecordBean){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }



    class ProfitAdapter extends BaseAdapter {
        private ArrayList< ProfitBean.DataBean.RecommendProfitViewBean> list;
        private Context context;
        private LayoutInflater inflater;


        public ProfitAdapter(Context context, ArrayList< ProfitBean.DataBean.RecommendProfitViewBean> list) {
            this.context = context;
            this.list = list;
            inflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list != null ? list.size() : 0;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           ViewHolder holder=null;
            if (convertView==null){

                        holder=new ViewHolder();
                        convertView=inflater.inflate(R.layout.fen_list_item,parent,false);
                        holder.type = (ImageView) convertView
                                .findViewById(R.id.type);
                        holder.name = (TextView) convertView
                                .findViewById(R.id.name);
                        holder.time = (TextView) convertView
                                .findViewById(R.id.time);
                        holder.jy_money = (TextView) convertView
                                .findViewById(R.id.js_money);
                        holder.jy_profit = (TextView) convertView
                                .findViewById(R.id.js_profit);
                        holder.js_and_sj= (TextView) convertView.findViewById(R.id.jy_and_sj);
                        convertView.setTag(holder);
            }else{


                        holder= (ViewHolder) convertView.getTag();



            }

            ProfitBean.DataBean.RecommendProfitViewBean bean = list.get(position);

                    String name = bean.getMer2Name();
                    double settleMoney = bean.getSettleMoney();
                    if (bean.getProfitSource()==2){
                        //   name = bean.getMerName();
                        if (settleMoney <1000&& settleMoney !=100){
                            holder.type.setImageResource(R.drawable.icon_dl);
                        }else{
                            holder.type.setImageResource(R.drawable.icon_hhr);
                        }

                    }else if (bean.getProfitSource()==1){
                        if (getResources().getString(R.string.home_wx_Z0).equals(bean.getType())) {
                            holder.type.setImageResource(R.drawable.icon_wx_color);
                        } else if ( getResources().getString(R.string.home_zfb_AO).equals(bean.getType())){
                            holder.type.setImageResource(R.drawable.icon_zfb_color);
                        }else{
                            holder.type.setImageResource(R.drawable.icon_kj_color);
                        }
                    }
                    String payTime = bean.getSettleDate();
                    if (payTime==null||payTime.equals("")){
                        holder.time.setText("");
                    }else{
                        holder.time.setText(payTime +"");
                    }

                    holder.jy_money.setText("￥" +String .format("%.2f", settleMoney));
                    holder.jy_profit.setText("￥"+ String .format("%.2f",bean.getSettleProfit()));

                    if (name!=null&&name.length()>0){
                        int length = name.length();
                        if (length ==2){
                            name=name.substring(0,1)+"*";
                        }
                        else if (length >2){
                            name=name.substring(0,1)+ Tool.getxing(length-2)+name.substring(length -1, length);
                        }
                    }else{
                        name="";
                    }
                    String a="%s为你产生了一笔%s元分润已到账。";
                    holder.name.setText(String.format(a,name,String .format("%.2f",bean.getSettleProfit())));


            return convertView;
        }


        class ViewHolder{
            ImageView type;
            TextView time,name,jy_money,jy_profit,js_and_sj;
        }

    }
    @Override
    public void onBackPressed() {

//        if (checkMain()){
//            Intent intent1 = new Intent(ProfitDetailsActivity.this, MainActivity.class);
//            startActivity(intent1.putExtra(APPConfig.LOGIN_INFO, JGPush.bean).putExtra("type",0));
//        }
        super.onBackPressed();

    }
}
