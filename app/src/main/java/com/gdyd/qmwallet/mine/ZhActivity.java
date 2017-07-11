package com.gdyd.qmwallet.mine;

import android.content.Context;
import android.content.Intent;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.config.HandleConfig;
import com.gdyd.qmwallet.home.model.EarningRecordBean;
import com.gdyd.qmwallet.home.view.EarningRecordActivity;
import com.gdyd.qmwallet.mine.model.BlankBean;
import com.gdyd.qmwallet.mine.model.BranchBankBean;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.mine.presenter.BankPresenter;
import com.gdyd.qmwallet.mine.view.IBankInfoView;
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

public class ZhActivity extends BaseActivity implements IBankInfoView {

    private PercentRelativeLayout left_return;
    private Button button_search;
    private EditText write_psw;
    private PullToRefreshListView listview;
    private int page=1;
    private BankPresenter bankPresenter =new BankPresenter(this);
    private int pageSize=30;
    private String bankCode="";
    private String cityName="";
    private String aeeaName="";
    private String keyword="";
   private   ArrayList<BranchBankBean.DataBean.Bank_stlnoBean> list=new ArrayList<>();
    private MyAdapter adapter;
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
    private String lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zh);
        initView();
        bankCode = getIntent().getStringExtra("BankCode");
        cityName = getIntent().getStringExtra("CityName");
        aeeaName = getIntent().getStringExtra("AeeaName");
        String substring = cityName.substring(0, cityName.length() - 1);
        Log.d("zanZQ", "onCreate: "+substring);
        long transTyp = EncryptionHelper.getDate();
        String transTy="1044"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);
        bankPresenter.getBranchBank(new PlaceBean("1044",transKe,pageSize,page
          , cityName, bankCode, aeeaName, substring,transTyp));
    }
    private void initPull() {
        ILoadingLayout layoutProxy = listview.getLoadingLayoutProxy(true,false);
        layoutProxy.setLastUpdatedLabel("上次刷新时间:" + lastUpdateTime);
        layoutProxy.setPullLabel("下拉刷新");
        layoutProxy.setRefreshingLabel("正在加载数据");
        layoutProxy.setReleaseLabel("手指释放刷新数据");
        ILoadingLayout endLayout  = listview.getLoadingLayoutProxy(false,true);
        endLayout.setLastUpdatedLabel("上次刷新时间:" + lastUpdateTime);
        endLayout.setPullLabel("上拉刷新");
        endLayout.setRefreshingLabel("正在加载数据");
        endLayout.setReleaseLabel("手指释放刷新数据");
    }
    private void initView() {
        left_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        button_search = ((Button) findViewById(R.id.button_search));
        write_psw = ((EditText) findViewById(R.id.write_psw));
        listview = ((PullToRefreshListView) findViewById(R.id.listview));
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        initPull();
        left_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword=write_psw.getText().toString();
                long transTyp = EncryptionHelper.getDate();
                String transTy="1044"+transTyp+"";
                String transKe = EncryptionHelper.md5(transTy);
                bankPresenter.getBranchBank(new PlaceBean("1044",transKe,pageSize,page
                        , cityName, bankCode, aeeaName, keyword,transTyp));
//                bankPresenter.getBranchBank(new PlaceBean("1044","9DE22DEC7801A115E4C532B22C5F8551",pageSize,page
//                        ,cityName,bankCode,aeeaName,keyword));
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent data=new Intent();
                BranchBankBean.DataBean.Bank_stlnoBean bank_stlnoBean = list.get(position - 1);
                data.putExtra("name", bank_stlnoBean.getBANK_NAME());
                data.putExtra("OPEN_STLNO",bank_stlnoBean.getOPEN_STLNO());
                setResult(HandleConfig.SUCCESS,data);
                finish();
            }
        });
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                page=1;
                lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
                initPull();
                long transTyp = EncryptionHelper.getDate();
                String transTy="1044"+transTyp+"";
                String transKe = EncryptionHelper.md5(transTy);
                bankPresenter.getBranchBank(new PlaceBean("1044",transKe,pageSize,page
                        , cityName, bankCode, aeeaName, keyword,transTyp));
//                bankPresenter.getBranchBank(new PlaceBean("1044","9DE22DEC7801A115E4C532B22C5F8551",pageSize,page
//                        ,cityName,bankCode,aeeaName,keyword));
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
                initPull();
                long transTyp = EncryptionHelper.getDate();
                String transTy="1044"+transTyp+"";
                String transKe = EncryptionHelper.md5(transTy);
                bankPresenter.getBranchBank(new PlaceBean("1044",transKe,pageSize,page
                        , cityName, bankCode, aeeaName, keyword,transTyp));
//                bankPresenter.getBranchBank(new PlaceBean("1044","9DE22DEC7801A115E4C532B22C5F8551",pageSize,page
//                        ,cityName,bankCode,aeeaName,keyword));
            }
        });
    }

    @Override
    public void ISumBankInfoView(BlankBean blankBean) {

    }

    @Override
    public void IBranchBankInfoView(BranchBankBean branchBankBean) {
        listview.onRefreshComplete();
        if (branchBankBean!=null&&branchBankBean.getNResul()==1){
            int pageCount = branchBankBean.getDataBean().getPageCount();
            //    list=recordBeanList;
            Log.d("zanZQ", "getRecordBack: "+page+":size"+pageCount);
            if (page==pageCount){ //如果总页数 等于当前请求页数 则是最后一页
                T.showMessage(ZhActivity.this, "当前是最后一页",2000);
                //   Toast.makeText(getActivity(), "当前是最后一页", Toast.LENGTH_SHORT).show();
            }else if (page>pageCount){ //如果 请求页大于总页数 则直接返回
                if (pageCount==0){
                    pageCount=1;
                }
                page=pageCount;
                list.clear();
                if (Is.isNoEmptyAll(adapter)){
                    adapter.notifyDataSetChanged();
                    Toast.makeText(ZhActivity.this, "没有查询到相关信息", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            ArrayList<BranchBankBean.DataBean.Bank_stlnoBean> arrayList = branchBankBean.getDataBean().getList();
            if (arrayList!=null&&arrayList.size()>0){
                list.clear();
                list.addAll(arrayList);
                if (!Is.isNoEmptyAll(adapter)){
                    adapter = new MyAdapter(this.list, ZhActivity.this);
                    listview.setAdapter(adapter);

                }else{
                    adapter.notifyDataSetChanged();

                }
            }else{
                list.clear();
                if (Is.isNoEmptyAll(adapter)){
                    adapter.notifyDataSetChanged();
                    Toast.makeText(ZhActivity.this, "没有查询到相关信息", Toast.LENGTH_SHORT).show();
                }
            }


        }else{
            Toast.makeText(this, "没有更多了", Toast.LENGTH_SHORT).show();
        }
//        if (list==null||list.size()==0){
//            Toast.makeText(this, "该区没有此支行", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        ArrayList<String> a=new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            a.add(list.get(i).getBANK_NAME().trim());
//        }
    }

    @Override
    public void ISubmitInfoBack(String backInfo) {

    }

    @Override
    public void IUserInfoView(LoginInfoBean merchantBean) {

    }
    class MyAdapter extends BaseAdapter {  //提现记录适配器
        private List<BranchBankBean.DataBean.Bank_stlnoBean> infoBeanList;
        private Context context;
        private LayoutInflater inflater;

        public MyAdapter(List<BranchBankBean.DataBean.Bank_stlnoBean> infoBeanList, Context context) {
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
                convertView=inflater.inflate(R.layout.item,parent,false);
                holder=new ViewHolder();
                holder.fh= ((TextView) convertView.findViewById(R.id.fh_name));
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }

            BranchBankBean.DataBean.Bank_stlnoBean bank_stlnoBean = infoBeanList.get(position);
            holder.fh.setText(bank_stlnoBean.getBANK_NAME());
            return convertView;
        }
        class ViewHolder {
            TextView fh;

        }
    }
}
