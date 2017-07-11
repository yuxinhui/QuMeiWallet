package com.gdyd.qmwallet.trans;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.percent.PercentRelativeLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.Is;
import com.gdyd.qmwallet.utils.ReplaceTools;
import com.gdyd.qmwallet.utils.TimeUtils;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 王松 on 2016/9/18.
 */
public class TransActivity extends BaseActivity implements View.OnClickListener,PullToRefreshBase.OnRefreshListener2 ,ITransView{
    private PopupWindow popupWindow;
    private DatePickerDialog dateDialog;
    private DatePickerDialog enddateDialog;
    private View contentView;
    private PercentRelativeLayout layout_body;
    private PercentRelativeLayout layout_queryorder_search;
    private PercentRelativeLayout layout_queryorder_pay_type_tip;
    private PercentRelativeLayout layout_queryorder_pay_status_tip;
    private PercentRelativeLayout layout_queryorder_start_date_tip;
    private PercentRelativeLayout layout_queryorder_end_date_tip;
    private TextView start_search;
    private Spinner spinner_pay_type;
    private Spinner spinner_pay_status;
    private TextView text_start_date;
    private TextView text_end_date;
    private PercentRelativeLayout layout_jysj_date;
    private TextView transdata_ls;
    private TextView transdata_count_money;
    private PullToRefreshListView layout_jysj_list;
    private TextView transdata_count_money_text;
    private EditText queryorder_search_text;
    private ImageView image_return;
    private ImageView image_search;
    private int page;
    private final int pageSize = 30;
    private String startDate = "";
    private String endDate = "";
    private String type = "";
    private String  StateType="";
    private TransPresenter presenter=new TransPresenter(this);
    private LoginInfoBean bean;
    List<Trans.DataBean.TransRecordsBean> transRecords=new ArrayList<>();
    private String merchantNo;
    private TransAdapter transAdapter;
    private  SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
    private  String lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trans);
        init();
        transdata_ls.setText("("+startDate+")—("+endDate+")");
    }


    private void init() {
        page = 1;
        layout_jysj_date = (PercentRelativeLayout) findViewById(R.id.layout_jysj_date);
        transdata_ls = (TextView) findViewById(R.id.transdata_ls);
     //   transdata_ls.setText("(" + TimeUtils.getShortStandDate() + ")");
        transdata_count_money = (TextView) findViewById(R.id.transdata_count_money);
        transdata_count_money_text = (TextView)findViewById(R.id.transdata_count_money_text);
        layout_jysj_list = (PullToRefreshListView) findViewById(R.id.layout_jysj_list);
        image_return = (ImageView)findViewById(R.id.image_return);
        image_search = (ImageView) findViewById(R.id.image_search);

        layout_jysj_date.setOnClickListener(null);
        image_return.setOnClickListener(this);
        image_search.setOnClickListener(this);

        layout_jysj_list.setMode(PullToRefreshBase.Mode.BOTH);// 两端刷新
        layout_jysj_list.setOnRefreshListener(this);
//        layout_jysj_list.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("上拉刷新");
//        layout_jysj_list.getLoadingLayoutProxy(false, true).setLastUpdatedLabel("松开加载更多");
        initPull();
        initpopupWindowdow();
        getData();
        getMoneyCount(null);
        layout_jysj_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //   Trans Trans = listTrans.get(position - 1);
                Trans.DataBean.TransRecordsBean transRecordsBean = transRecords.get(position - 1);
                startActivity(new Intent(TransActivity.this,TransDetails.class).putExtra("Trans",transRecordsBean).
                        putExtra("name",bean.getUserData().getMerchant().getName()
                        ).putExtra("phone",bean.getUserData().getMerchant().getPhoneNumber()));
            }
        });
        bean = ((LoginInfoBean) getIntent().getSerializableExtra(APPConfig.LOGIN_INFO));
        if (bean!=null){
            merchantNo = bean.getUserData().getMerchant().getMerchantNo();
            long transTyp = EncryptionHelper.getDate();
            String transTy="1012"+transTyp+"";
            String transKe = EncryptionHelper.md5(transTy);
            presenter.getTransData(new PlaceBean("1012",transKe,type,page,pageSize,endDate,startDate,merchantNo,transTyp));

            //     presenter.getTransData(new PlaceBean(type,page,pageSize,endDate,startDate, merchantNo));
        }



    }



    /**
     * 设置总金额及交易笔数
     *
     * @param listTrans
     */
    private void getMoneyCount(List<Trans.DataBean.TransRecordsBean> listTrans) {
        if (Is.isNoEmpty(listTrans)) {
            Double count = 0D;
            for (Trans.DataBean.TransRecordsBean Trans : listTrans) {
              // count += Double.parseDouble(Trans.getTransMoney());
                if (Trans.getOrderState().equals("00000")){
                    count += Trans.getTransMoney();
                }
            }
            transdata_count_money
                    .setText("￥" + String .format("%.2f",count));
            transdata_count_money_text.setText("成功完成收款" + listTrans.size()
                    + "笔");
        } else {
            transdata_count_money.setText("￥0.00");
            transdata_count_money_text.setText("暂无交易");
        }
    }
    /**
     * 初始化高级搜索布局
     */
    private void initpopupWindowdow() {
        layout_body = (PercentRelativeLayout)findViewById(R.id.layout_body);
        contentView = View.inflate(TransActivity.this,
                R.layout.activity_query_order, null);
        layout_queryorder_search = (PercentRelativeLayout) contentView
                .findViewById(R.id.layout_queryorder_search);
        layout_queryorder_pay_type_tip = (PercentRelativeLayout) contentView
                .findViewById(R.id.layout_queryorder_pay_type_tip);
        layout_queryorder_pay_status_tip = (PercentRelativeLayout) contentView
                .findViewById(R.id.layout_queryorder_pay_status_tip);
        layout_queryorder_start_date_tip = (PercentRelativeLayout) contentView
                .findViewById(R.id.layout_queryorder_start_date_tip);
        layout_queryorder_end_date_tip = (PercentRelativeLayout) contentView
                .findViewById(R.id.layout_queryorder_end_date_tip);
        start_search = (TextView) contentView.findViewById(R.id.start_search);
        spinner_pay_status = (Spinner) contentView.findViewById(R.id.spinner_pay_status);
        spinner_pay_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //String[] languages = getResources().getStringArray(R.array.pay_type);
                switch (i){
                    case 0:
                        StateType ="";
                        break;
                    case 1:
                        StateType = getResources().getString(R.string.tip_queryorder_pay_status_s);
                        break;
                    case 2:
                        StateType = getResources().getString(R.string.tip_queryorder_pay_status_c);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_pay_type = (Spinner) contentView.findViewById(R.id.spinner_pay_type);
        spinner_pay_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //String[] languages = getResources().getStringArray(R.array.pay_type);
                switch (i){
                    case 0:
                        type = "";
                        break;
                    case 1:
                        type = APPConfig.WX;
                        break;
                    case 2:
                        type = APPConfig.ZFB;
                        break;
                    case 3:
                        type = APPConfig.KJ;
                        break;
                    case 4:
                        type = APPConfig.QQ;
                        break;
                    case 5:
                        type = APPConfig.JD;
                        break;
                    case 6:
                        type = APPConfig.YL;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        text_start_date = (TextView) contentView
                .findViewById(R.id.text_start_date);
        text_end_date = (TextView) contentView.findViewById(R.id.text_end_date);
        queryorder_search_text = (EditText) contentView
                .findViewById(R.id.queryorder_search_text);

        text_start_date.setText(TimeUtils.getShortStandDate());
        text_end_date.setText(TimeUtils.getShortStandDate());

        layout_queryorder_search.setOnClickListener(this);
        layout_queryorder_pay_type_tip.setOnClickListener(this);
        layout_queryorder_pay_status_tip.setOnClickListener(this);
        layout_queryorder_start_date_tip.setOnClickListener(this);
        layout_queryorder_end_date_tip.setOnClickListener(this);
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
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 0f-1f,1f为不透明
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp =getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0 1f为不透明
      getWindow().setAttributes(lp);
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
    private void getData() {
        dateDialog = new DatePickerDialog(TransActivity.this,
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
                   //     transdata_ls.setText("(" + st + ")");
                        text_start_date.setText(st);
                        page = 1;
                        startDate = st;
                        if (!Is.isNoEmpty(endDate)) {
                            text_end_date.setText(st);
                            endDate = st;
                            long transTyp = EncryptionHelper.getDate();
                            String transTy="1012"+transTyp+"";
                            String transKe = EncryptionHelper.md5(transTy);
                            presenter.getTransData(new PlaceBean("1012",transKe,type,page,pageSize,endDate,startDate,merchantNo,transTyp));

                            //   presenter.getTransData(new PlaceBean(type,page,pageSize,endDate,startDate,merchantNo));
                        }
                        //    initData(pageSize, page, startDate, endDate);



                    }
                }, TimeUtils.getYear(), TimeUtils.getMonth() - 1,
                TimeUtils.getDay());

        enddateDialog = new DatePickerDialog(TransActivity.this,
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
                        //    transdata_ls.setText("(" + st + ")");
                            startDate = st;
                        }
                        //   initData(pageSize, page, startDate, endDate);
                    }
                }, TimeUtils.getYear(), TimeUtils.getMonth() - 1,
                TimeUtils.getDay());

        // 请求网络数据
       String date = text_start_date.getText().toString();
     //   startDate = date.substring(1, date.length() - 1);
    //    endDate = date.substring(1, date.length() - 1);
        startDate =date;
        endDate =date;
        //   initData(pageSize, page, startDate, endDate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.image_return:
                if (dateDialog != null) {
                    dateDialog.cancel();
                }
                dateDialog = null;
              finish();
                break;
            case R.id.image_search:
                openpopupWindowdow();
                break;
            case R.id.start_search:
                // 高级搜索
                long transTyp = EncryptionHelper.getDate();
                String transTy="1012"+transTyp+"";
                String transKe = EncryptionHelper.md5(transTy);
                presenter.getTransData(new PlaceBean("1012",transKe,type,page,pageSize,endDate,startDate,merchantNo,transTyp));
                //    initData(pageSize, page, startDate, endDate);
                dim();
                //   T.showShort(TransData.this, "加载中...");
                break;

        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
           page=1;
        lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
        initPull();
        long transTyp = EncryptionHelper.getDate();
        String transTy="1012"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);
        presenter.getTransData(new PlaceBean("1012",transKe,type,page,pageSize,endDate,startDate,merchantNo,transTyp));

    //    presenter.getTransData(new PlaceBean(type,page,pageSize,endDate,startDate,merchantNo));
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        page++;
        lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
        initPull();
        long transTyp = EncryptionHelper.getDate();
        String transTy="1012"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);
        presenter.getTransData(new PlaceBean("1012",transKe,type,page,pageSize,endDate,startDate,merchantNo,transTyp));

      //  presenter.getTransData(new PlaceBean(type,page,pageSize,endDate,startDate,merchantNo));
    }
    private void initPull() {
        ILoadingLayout layoutProxy = layout_jysj_list.getLoadingLayoutProxy(true,false);
        layoutProxy.setLastUpdatedLabel("上次刷新时间:" + lastUpdateTime);
        layoutProxy.setPullLabel("下拉刷新");
        layoutProxy.setRefreshingLabel("正在加载数据");
        layoutProxy.setReleaseLabel("手指释放刷新数据");
        ILoadingLayout endLayout  = layout_jysj_list.getLoadingLayoutProxy(false,true);
        endLayout.setLastUpdatedLabel("上次刷新时间:" + lastUpdateTime);
        endLayout.setPullLabel("上拉刷新");
        endLayout.setRefreshingLabel("正在加载数据");
        endLayout.setReleaseLabel("手指释放刷新数据");
    }
    @Override
    public void UpdateTransView(Trans trans) {
       // Log.d("zanZQ", "UpdateTransView: "+trans.toString());
        layout_jysj_list.onRefreshComplete();
        transdata_ls.setText("("+startDate+")—("+endDate+")");
        if (trans==null){
            transRecords.clear();
            if (Is.isNoEmptyAll(transAdapter)){
                transAdapter.notifyDataSetChanged();
                Toast.makeText(TransActivity.this, "暂无信息", Toast.LENGTH_SHORT).show();
            }
          //  Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();
            return;
        }
        if (trans.getnResul()==1){
            int nPageCount = trans.getDataBean().getNPageCount();
            if (page==nPageCount){
                Toast.makeText(TransActivity.this, "当前是最后一页", Toast.LENGTH_SHORT).show();
            }else if (page>nPageCount){
                page=nPageCount;
                Toast.makeText(TransActivity.this, "没有查询到信息..", Toast.LENGTH_SHORT).show();
                return;
            }
            List<Trans.DataBean.TransRecordsBean> transRecord = trans.getDataBean().getTransRecords();
            if (transRecord!=null&&transRecord.size()>0) {
                //  transRecords.clear();
                if (StateType.equals("")) {
                    transRecords.clear();
                    transRecords.addAll(transRecord);
                  //  transRecords = transRecord;
                } else {
                    transRecords.clear();
                    for (int i = 0; i < transRecord.size(); i++) {
                        if (StateType.equals(getResources().getString(R.string.tip_queryorder_pay_status_s))) {
                            if (transRecord.get(i).getOrderState().equals("00000")) {
                                transRecords.add(transRecord.get(i));
                            }
                        } else if (StateType.equals(getResources().getString(R.string.tip_queryorder_pay_status_c))) {
                            if (transRecord.get(i).getOrderState().equals("00001")) {
                                transRecords.add(transRecord.get(i));
                            }
                        }

                    }
                }
                if (!Is.isNoEmptyAll(transAdapter)){
                    transAdapter = new TransAdapter(TransActivity.this, transRecords);
                    layout_jysj_list.setAdapter(transAdapter);
                } else{
                    transAdapter.notifyDataSetChanged();
                }

            }else{
                transRecords.clear();
                if (Is.isNoEmptyAll(transAdapter)){
                    transAdapter.notifyDataSetChanged();
                    Toast.makeText(TransActivity.this, "暂无信息", Toast.LENGTH_SHORT).show();
                }


            }

        }
        getMoneyCount(transRecords);
    }
    class TransAdapter extends BaseAdapter{
       private List<Trans.DataBean.TransRecordsBean> list;
       private Context context;
        private LayoutInflater inflater;

        public TransAdapter(Context context, List<Trans.DataBean.TransRecordsBean> list) {
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
           ViewHolder holder;
            if (convertView==null){
                convertView=inflater.inflate(R.layout.view_listview_jyls,parent,false);
                holder=new ViewHolder();


                holder.jyls_img = (ImageView) convertView
                        .findViewById(R.id.jyls_img);
                holder.jyls_out_trade_order = (TextView) convertView
                        .findViewById(R.id.jyls_out_trade_order);
                holder.jyls_pay_time = (TextView) convertView
                        .findViewById(R.id.jyls_pay_time);
                holder.jyls_money = (TextView) convertView
                        .findViewById(R.id.jyls_money);
                holder.jyls_pay_success = (TextView) convertView
                        .findViewById(R.id.jyls_pay_success);
                convertView.setTag(holder);
            }else{
               holder= (ViewHolder) convertView.getTag();
            }
            Trans.DataBean.TransRecordsBean transRecordsBean = list.get(position);
            if (APPConfig.WX.equals(transRecordsBean.getType())) {
                holder.jyls_img.setImageResource(R.drawable.icon_wx_color);
            } else if ( APPConfig.ZFB.equals(transRecordsBean.getType())){
                holder.jyls_img.setImageResource(R.drawable.icon_zfb_color);
            }else if (APPConfig.KJ.equals(transRecordsBean.getType())){
                holder.jyls_img.setImageResource(R.drawable.icon_kj_color);
            }else if (APPConfig.YL.equals(transRecordsBean.getType())){
                holder.jyls_img.setImageResource(R.drawable.icon_kj_color);
            }else if (APPConfig.QQ.equals(transRecordsBean.getType())){
                holder.jyls_img.setImageResource(R.drawable.icon_qq_color);
            }else if (APPConfig.JD.equals(transRecordsBean.getType())){
                holder.jyls_img.setImageResource(R.drawable.icon_jd_color);
            }
            Object payTime = transRecordsBean.getPayTime();
            if (payTime==null||payTime.equals("")){
                holder.jyls_pay_time.setText("");
            }else{
                holder.jyls_pay_time.setText(payTime +"");
            }

            holder.jyls_money.setText("￥" +String .format("%.2f",transRecordsBean.getTransMoney()) );
            holder.jyls_out_trade_order.setText(transRecordsBean.getOrderNo());
            String pay_tip = transRecordsBean.getRoutingTYpe();
            String orderState = transRecordsBean.getOrderState();
            if (orderState.equals("00000")){
                holder.jyls_pay_success.setText("支付成功");
            }else{
                holder.jyls_pay_success.setText("支付中");
            }
            return convertView;
        }
        class ViewHolder{
           ImageView jyls_img;
            TextView jyls_out_trade_order,jyls_pay_time,jyls_money,jyls_pay_success;
        }
    }
}
