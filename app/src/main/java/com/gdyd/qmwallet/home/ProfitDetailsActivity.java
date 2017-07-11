package com.gdyd.qmwallet.home;

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

public class ProfitDetailsActivity extends BaseActivity implements IProfitView,View.OnClickListener{
   private ProfitPresenter presenter=new ProfitPresenter(this);
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
    private String startDate = "";
    private String endDate = "";
    private TextView countMoney;
    private TextView countProfit;
    private PullToRefreshListView lv;
    private TextView profit_money;
    private PercentRelativeLayout image_return;
    private ImageView left_title;
    ProfitAdapter profitAdapter;
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
        setContentView(R.layout.activity_profit_details);
        bean = ((LoginInfoBean) getIntent().getSerializableExtra(APPConfig.LOGIN_INFO));
        lv = ((PullToRefreshListView) findViewById(R.id.listView));
        profit_money = ((TextView) findViewById(R.id.profit_money));
        date_scope = ((TextView) findViewById(R.id.date_scope));
        image_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        left_title = ((ImageView) findViewById(R.id.left_title));
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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (groupType==1){
                    ProfitBean.DataBean.RecommendProfitViewBean beanData = profitList.get(position-1);
                    String searchTime = beanData.getSettleDate().substring(0,10);
                    Intent intent = new Intent(ProfitDetailsActivity.this, ProfitItemDetailsActivity.class);
                    intent.putExtra("profitDetailSearchTime",searchTime);
                    intent.putExtra("merchantNo",bean.getUserData().getMerchant().getMerchantNo());
                    startActivity(intent);
                }


            }
        });

        image_return.setOnClickListener(this);
        left_title.setOnClickListener(this);
        initpopupWindowdow();
        getData();
        startDate=TimeUtils.getShortStandDate();
        endDate=TimeUtils.getShortStandDate();
        inRequestData();

    }

    private void inRequestData() {
        pageSize=30;
        long transTyp = EncryptionHelper.getDate();
        String transTy="1079"+transTyp;
        String transKe = EncryptionHelper.md5(transTy);
        presenter.getProfitData(new PlaceBean("1079",transKe,bean.getUserData().getMerchant().getMerchantNo(),type,StateType,startDate,endDate,page,pageSize,transTyp));
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
                Toast.makeText(ProfitDetailsActivity.this, "暂无信息", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        groupType = profitBean.getDataBean().getGroupType();

        if (groupType==1){
            profitList.clear();
        }
        if (profitBean.getNResul()==1){
            profit_money.setText("￥" + String .format("%.2f",profitBean.getDataBean().getTransProfit()));
            if (endDate.equals(startDate)&&startDate.equals(dateFormat.format(new Date()))){
                date_scope.setText(getResources().getString(R.string.profit_date));
            }else{
                date_scope.setText("("+startDate+")—("+endDate+")");
            }
            if (profitBean.getDataBean().getGroupType()==2){
                int nPageCount = profitBean.getDataBean().getPageCount();
                if (page==nPageCount){
                    profitList.clear();
                    Toast.makeText(ProfitDetailsActivity.this, "当前是最后一页", Toast.LENGTH_SHORT).show();
                }else if (page>nPageCount){
                    if (nPageCount==0){
                        nPageCount=1;
                    }
                    page=nPageCount;
                    Toast.makeText(ProfitDetailsActivity.this, "没有查询到信息..", Toast.LENGTH_SHORT).show();
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
                    profitAdapter = new ProfitAdapter(ProfitDetailsActivity.this, profitList);
                    lv.getRefreshableView().setAdapter(profitAdapter);
                } else{
                    profitAdapter.notifyDataSetChanged();
                }
            }
        }else{
            profitList.clear();
            if (Is.isNoEmptyAll(profitAdapter)){
                profitAdapter.notifyDataSetChanged();
                Toast.makeText(ProfitDetailsActivity.this, "暂无信息", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void getDataYuDianRecord(YuDianRecordBean yuDianRecordBean){

    }
    /**
     * 初始化高级搜索布局
     */
    private void initpopupWindowdow() {
        layout_body = (PercentRelativeLayout) findViewById(R.id.layout_body);
        contentView = View.inflate(ProfitDetailsActivity.this,
                R.layout.activity_query_profit, null);
        layout_queryorder_start_date_tip = (PercentRelativeLayout) contentView
                .findViewById(R.id.layout_queryorder_start_date_tip);
        layout_queryorder_end_date_tip = (PercentRelativeLayout) contentView
                .findViewById(R.id.layout_queryorder_end_date_tip);
        layout_queryorder_pay_type_tip = (PercentRelativeLayout) contentView
                .findViewById(R.id.layout_queryorder_pay_type_tip);
        layout_queryorder_pay_status_tip = (PercentRelativeLayout) contentView
                .findViewById(R.id.layout_queryorder_pay_status_tip);
        start_search = (TextView) contentView.findViewById(R.id.start_search);
        text_start_date = (TextView) contentView
                .findViewById(R.id.text_start_date);
        text_end_date = (TextView) contentView.findViewById(R.id.text_end_date);
        spinner_pay_status = (Spinner) contentView.findViewById(R.id.spinner_pay_status);
        //分润类型选择
        spinner_pay_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //String[] languages = getResources().getStringArray(R.array.pay_type);
                switch (i){
                    case 0:
                        StateType =0;
                        break;
                    case 1:
                        StateType = 1;
                        break;
                    case 2:
                        StateType = 2;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_pay_type = (Spinner) contentView.findViewById(R.id.spinner_pay_type);
        //交易方式选择
        spinner_pay_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //String[] languages = getResources().getStringArray(R.array.pay_type);
                switch (i){
                    case 0:
                        type = "";
                        break;
                    case 1:
                        type = "Z0";
                        break;
                    case 2:
                        type = "A0";
                        break;
                    case 3:
                        type = "K0";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        queryorder_search_name = (EditText) contentView
//                .findViewById(R.id.queryorder_search_name);
//        queryorder_search_phone = ((EditText) contentView.findViewById(R.id.queryorder_search_phone));
        text_start_date.setText(TimeUtils.getShortStandDate());
        text_end_date.setText(TimeUtils.getShortStandDate());
        layout_queryorder_start_date_tip.setOnClickListener(this);
        layout_queryorder_end_date_tip.setOnClickListener(this);
        layout_queryorder_pay_type_tip.setOnClickListener(this);
        layout_queryorder_pay_status_tip.setOnClickListener(this);
        start_search.setOnClickListener(this);
    }
    /**
     * 打开popupWindowdow
     */
    public void openpopupWindowdow() {
        if (null == popupWindow) {
            // 创建
            createPopupWindowdow();
        }
        // 动画

        popupWindow.setAnimationStyle(R.style.popwindow_anim_style);

        // 设置透明度
        backgroundAlpha(0.7f);
        // 开启弹出框
        popupWindow.showAtLocation(layout_body, Gravity.BOTTOM, 0, 0);
        popupWindow.update();
    }

    /**
     * 为空或没有加载到数据，重新创建PopupWindowdow
     */
    private void createPopupWindowdow() {
        // (popupWindow自定义布局文件,popupWindow宽度,popupWindow高度)(注：若想指定位置则后两个参数必须给定值不能为WRAP_CONTENT)
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // 恢复正常透明度
                backgroundAlpha(1.0f);
            }
        });
        // 点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        // 设置背景
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int[] location = new int[2];
        // contentView.getLocationInWindow(location);
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, location[0],
                location[0]);
        // popupWindow.showAsDropDown(mNameOf4s);
    }
    /**
     * 关闭popupWindowdow
     */
    public void dim() {
        if (popupWindow != null && popupWindow.isShowing()) {// 如果当前正在显示，则将被处理
            // backgroundAlpha(1f);
            popupWindow.dismiss();
            popupWindow = null;
        }
    }
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0 1f为不透明
        getWindow().setAttributes(lp);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dateDialog != null) {
            dateDialog.cancel();
        }
        dateDialog = null;
        if (enddateDialog != null) {
            enddateDialog.cancel();
        }
        enddateDialog = null;
        dim();
    }
    private void getData() {
        dateDialog = new DatePickerDialog(ProfitDetailsActivity.this,
                AlertDialog.THEME_HOLO_LIGHT,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String month = "01";
                        if ((monthOfYear + 1) >= 10) {
                            month = String.valueOf(monthOfYear + 1);
                        } else {
                            month = "0" + String.valueOf(monthOfYear + 1);
                        }
                        String day = "01";
                        if (dayOfMonth >= 10) {
                            day = String.valueOf(dayOfMonth);
                        } else {
                            day = "0" + String.valueOf(dayOfMonth);
                        }
                        StringBuilder sb = new StringBuilder();
                        String st = sb.append(year).append("-").append(month)
                                .append("-").append(day).toString();
                        text_start_date.setText(st);
                        page = 1;
                        startDate = st;
                        if (!Is.isNoEmpty(endDate)) {
                            text_end_date.setText(st);
                            endDate = st;
                        }
                        //    initData(pageSize, page, startDate, endDate);
                    }
                }, TimeUtils.getYear(), TimeUtils.getMonth() - 1,
                TimeUtils.getDay());

        enddateDialog = new DatePickerDialog(ProfitDetailsActivity.this,
                AlertDialog.THEME_HOLO_LIGHT,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String month = "01";
                        if ((monthOfYear + 1) >= 10) {
                            month = String.valueOf(monthOfYear + 1);
                        } else {
                            month = "0" + String.valueOf(monthOfYear + 1);
                        }
                        String day = "01";
                        if (dayOfMonth >= 10) {
                            day = String.valueOf(dayOfMonth);
                        } else {
                            day = "0" + String.valueOf(dayOfMonth);
                        }
                        StringBuilder sb = new StringBuilder();
                        String st = sb.append(year).append("-").append(month)
                                .append("-").append(day).toString();
                        text_end_date.setText(st);
                        page = 1;
                        endDate = st;
                        if (!Is.isNoEmpty(startDate)) {
                            text_start_date.setText(st);
                            startDate = st;
                        }
                        // initData(pageSize, page, startDate, endDate);
                    }
                }, TimeUtils.getYear(), TimeUtils.getMonth() - 1,
                TimeUtils.getDay());

        // 请求网络数据
        String date = "(" + TimeUtils.getShortStandDate() + ")";
        startDate = date.substring(1, date.length() - 1);
        endDate = date.substring(1, date.length() - 1);
      //  initData(pageSize, page, startDate, endDate);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.layout_jysj_date:
            if (!dateDialog.isShowing()) {

                dateDialog.show();
                endDate = "";
            }
            break;
            case R.id.layout_queryorder_pay_type_tip:
                // 支付方式
                spinner_pay_type.performClick();
                break;
            case R.id.layout_queryorder_pay_status_tip:
                spinner_pay_status.performClick();
                break;
            case R.id.layout_queryorder_start_date_tip:
                if (!dateDialog.isShowing()) {
                    dateDialog.show();
                }
                break;
            case R.id.layout_queryorder_end_date_tip:
                if (!enddateDialog.isShowing()) {
                    enddateDialog.show();
                }
                break;
            case R.id.left_return:
                if (checkMain()){
                    Intent intent1 = new Intent(ProfitDetailsActivity.this, MainActivity.class);
                    startActivity(intent1.putExtra(APPConfig.LOGIN_INFO,bean).putExtra("type",0));
                }
                    finish();


                break;
            case R.id.left_title:
                openpopupWindowdow();
                break;
            case R.id.start_search:
                // 高级搜索
                inRequestData();
                //    initData(pageSize, page, startDate, endDate);
                dim();
                //   T.showShort(TransData.this, "加载中...");
                break;
        }
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
        public int getItemViewType(int position) {
           return groupType-1;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           ViewHolder holder=null;
            ViewHolder2 groupViewHolder=null;
            if (convertView==null){
                switch (groupType){
                    case 1:
                        groupViewHolder=new ViewHolder2();
                        convertView=inflater.inflate(R.layout.activity_info_centre_item,parent,false);
                        groupViewHolder.title= ((TextView) convertView.findViewById(R.id.title));
                        groupViewHolder.iv= ((ImageView) convertView.findViewById(R.id.iv));
//                holder.tip_img=convertView.findViewById(R.id.tip_img);
                        convertView.setTag(groupViewHolder);
                        break;
                    case 2:
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
                        break;

                }

            }else{
                switch (groupType){
                    case 1:
                        groupViewHolder = (ViewHolder2) convertView.getTag();
                        break;
                    case 2:
                        holder= (ViewHolder) convertView.getTag();
                        break;
                }

            }

            ProfitBean.DataBean.RecommendProfitViewBean bean = list.get(position);
            switch (groupType){
                case 1:
                    groupViewHolder.title.setText(bean.getSettleDate().substring(0,10)+"\t\t当日累计分润:\t\t"+String .format("%.2f",bean.getSettleProfit())+"元");
                    break;
                case 2:
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
                    break;
            }


            return convertView;
        }

//        @Override
//        public int getGroupCount() {
//            return list != null ? list.size() : 0;
//        }
//
//        @Override
//        public int getChildrenCount(int groupPosition) {
//            return list.get(groupPosition).getArrayList().size();
//        }
//
//        @Override
//        public Object getGroup(int groupPosition) {
//            return list.get(groupPosition);
//        }
//
//        @Override
//        public Object getChild(int groupPosition, int childPosition) {
//            return list.get(groupPosition).getArrayList().get(childPosition);
//        }
//
//        @Override
//        public long getGroupId(int groupPosition) {
//            return groupPosition;
//        }
//
//        @Override
//        public long getChildId(int groupPosition, int childPosition) {
//            return childPosition;
//        }
//
//        @Override
//        public boolean hasStableIds() {
//            return false;
//        }
//
//        @Override
//        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//            GroupViewHolder holder;
//            if (convertView==null){
//                holder=new GroupViewHolder();
//                convertView=inflater.inflate(R.layout.activity_info_centre_item,parent,false);
//                holder.title= ((TextView) convertView.findViewById(R.id.title));
//                holder.iv= ((ImageView) convertView.findViewById(R.id.iv));
////                holder.tip_img=convertView.findViewById(R.id.tip_img);
//                convertView.setTag(holder);
//            }else{
//                holder= (GroupViewHolder) convertView.getTag();
//            }
//            ProfitElvBean profitElvBean = list.get(groupPosition);
//            ArrayList<ProfitBean.DataBean.RecommendProfitViewBean> arrayList = profitElvBean.getArrayList();
//            Double count = 0D;
//            if (Is.isNoEmpty(arrayList)) {
//                for (ProfitBean.DataBean.RecommendProfitViewBean recommendProfitViewBean : arrayList) {
//                   count+=recommendProfitViewBean.getSettleProfit();
//                }
//            }
//            holder.title.setText(profitElvBean.getName()+"\t\t当日累计分润:\t\t"+String .format("%.2f",count));
//            //该分组是否展开
//            if (isExpanded) {
//                holder.iv.setImageResource(R.drawable.rounds_open);
//            } else {
//                holder.iv.setImageResource(R.drawable.rounds_close);
//            }
//            return convertView;
//        }
//
//        @Override
//        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//            ViewHolder holder;
//            if (convertView==null){
//                convertView=inflater.inflate(R.layout.fen_list_item,parent,false);
//                holder=new ViewHolder();
////                convertView.setLayoutParams(new AbsListView.LayoutParams(
////                        AbsListView.LayoutParams.MATCH_PARENT, layout_jysj_list
////                        .getHeight() /5 ));
//                holder.type = (ImageView) convertView
//                        .findViewById(R.id.type);
//                holder.name = (TextView) convertView
//                        .findViewById(R.id.name);
//                holder.time = (TextView) convertView
//                        .findViewById(R.id.time);
//                holder.jy_money = (TextView) convertView
//                        .findViewById(R.id.js_money);
//                holder.jy_profit = (TextView) convertView
//                        .findViewById(R.id.js_profit);
//                holder.js_and_sj= (TextView) convertView.findViewById(R.id.jy_and_sj);
//                convertView.setTag(holder);
//            }else{
//                holder= (ViewHolder) convertView.getTag();
//            }
//            ProfitBean.DataBean.RecommendProfitViewBean bean = list.get(groupPosition).getArrayList().get(childPosition);
//
//
//            String name = bean.getMer2Name();
//            double settleMoney = bean.getSettleMoney();
//            if (bean.getProfitSource()==2){
//                //   name = bean.getMerName();
//                if (settleMoney <1000&& settleMoney !=100){
//                    holder.type.setImageResource(R.drawable.icon_dl);
//                }else{
//                    holder.type.setImageResource(R.drawable.icon_hhr);
//                }
//
//            }else if (bean.getProfitSource()==1){
//                if (getResources().getString(R.string.home_wx_Z0).equals(bean.getType())) {
//                    holder.type.setImageResource(R.drawable.icon_wx_color);
//                } else if ( getResources().getString(R.string.home_zfb_AO).equals(bean.getType())){
//                    holder.type.setImageResource(R.drawable.icon_zfb_color);
//                }else{
//                    holder.type.setImageResource(R.drawable.icon_kj_color);
//                }
//            }
//            String payTime = bean.getSettleDate();
//            if (payTime==null||payTime.equals("")){
//                holder.time.setText("");
//            }else{
//                holder.time.setText(payTime +"");
//            }
//
//            holder.jy_money.setText("￥" +String .format("%.2f", settleMoney));
//            holder.jy_profit.setText("￥"+ String .format("%.2f",bean.getSettleProfit()));
//
//            if (name!=null&&name.length()>0){
//                int length = name.length();
//                if (length ==2){
//                    name=name.substring(0,1)+"*";
//                }
//                else if (length >2){
//                    name=name.substring(0,1)+ Tool.getxing(length-2)+name.substring(length -1, length);
//                }
//            }else{
//                name="";
//            }
//            String a="%s为你产生了一笔%s元分润已到账。";
//            holder.name.setText(String.format(a,name,String .format("%.2f",bean.getSettleProfit())));
//            return convertView;
//        }
//
//        @Override
//        public boolean isChildSelectable(int groupPosition, int childPosition) {
//            return false;
//        }
//
        class ViewHolder{
            ImageView type;
            TextView time,name,jy_money,jy_profit,js_and_sj;
        }
        class ViewHolder2{
            TextView title;
            ImageView iv;
            View tip_img;
        }

    }
    @Override
    public void onBackPressed() {

        if (checkMain()){
            Intent intent1 = new Intent(ProfitDetailsActivity.this, MainActivity.class);
            startActivity(intent1.putExtra(APPConfig.LOGIN_INFO, JGPush.bean).putExtra("type",0));
        }
            super.onBackPressed();

    }
}
