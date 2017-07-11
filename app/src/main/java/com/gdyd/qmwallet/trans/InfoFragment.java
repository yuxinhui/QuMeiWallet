package com.gdyd.qmwallet.trans;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseFragment;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.home.model.InfoBean;
import com.gdyd.qmwallet.home.model.MemberDetailsBean;
import com.gdyd.qmwallet.home.view.MemberDetailsFragment;
import com.gdyd.qmwallet.mine.model.PhotoBean;
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

/**
 * Created by zanzq on 2017/3/13.
 */

public class InfoFragment extends BaseFragment implements IInfoView{
    private View view;
    private InfoPresenter infoPresenter=new InfoPresenter(this);
  //  private LoginInfoBean bean;
    private int page=1;
    private final int pageSize = 10;
    private PullToRefreshListView listview;
    private List<InfoBean.DataBean.SmsPushDetailBean> beanArrayList=new ArrayList<>();
    private InfoAdapter infoAdapter;
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
    private String lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_info, container, false);
        }
        listview = ((PullToRefreshListView) view.findViewById(R.id.info_lv));
        bean = ((LoginInfoBean) getActivity().getIntent().getSerializableExtra(APPConfig.LOGIN_INFO));
//        if (bean==null){
//            checkBean();
//        }else{
//            initData();
//        }
        listview.setMode(PullToRefreshBase.Mode.BOTH);// 两端刷新
//        listview.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("上拉刷新");
//        listview.getLoadingLayoutProxy(false, true).setLastUpdatedLabel("松开加载更多");
        initPull();
      listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page=1;
                lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
                initPull();
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
                initPull();
                initData();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InfoBean.DataBean.SmsPushDetailBean smsPushDetailBean = beanArrayList.get(position - 1);
                Intent intent = new Intent(getActivity(),InfoDetails.class);
                intent.putExtra("infoBean",smsPushDetailBean);
                startActivity(intent);
                if (smsPushDetailBean.getIsNotifyAll()==1){
                    long transTyp = EncryptionHelper.getDate();
                    String transTy="1069"+transTyp+"";
                    String transKe = EncryptionHelper.md5(transTy);
                    infoPresenter.getInfoSign(new PhotoBean("1069",transKe,transTyp,smsPushDetailBean.getSMSPushID()+""));
                }

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bean==null){
            checkBean();
        }else{
            initData();
        }
    }

    private void initData() {
        long transTyp = EncryptionHelper.getDate();
        String transTy="1028"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);
        infoPresenter.getInfoData(new PlaceBean("1028",transKe,bean.getUserData().getMerchant().getMerchantNo(),page,pageSize,transTyp, APPConfig.AgentID));
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

    @Override
    public void UpDataInfoView(InfoBean info) {
        if (!isAdded()){
            return;
        }
        listview.onRefreshComplete();
        if (info!=null&&info.getNResul()==1){
            List<InfoBean.DataBean.SmsPushDetailBean> list = info.getDataBean().getSmsPushDetail();
            int nPageCount = info.getDataBean().getNPageCount();
            if (page==nPageCount){
                T.showMessage(getActivity(), "当前是最后一页",2000);
                //   Toast.makeText(getActivity(), "当前是最后一页", Toast.LENGTH_SHORT).show();
            }else if (page>nPageCount){
                if (nPageCount==0){
                    nPageCount=1;
                }
                page=nPageCount;
                beanArrayList.clear();
                if (Is.isNoEmptyAll(infoAdapter)){
                    infoAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "没有查询到相关信息", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            if (list!=null&&list.size()>0){
                beanArrayList.clear();
                beanArrayList.addAll(list);
                if (!Is.isNoEmptyAll(infoAdapter)){
                    infoAdapter = new InfoAdapter(beanArrayList, getActivity());
                    listview.setAdapter(infoAdapter);
                } else{
                    infoAdapter.notifyDataSetChanged();
                }
            }else{
                beanArrayList.clear();
                if (Is.isNoEmptyAll(infoAdapter)){
                    infoAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "没有查询到相关信息", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Toast.makeText(getActivity(), "获取信息失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void SignInfoView(String info) {
        Log.d("zanZQ", "SignInfoView++++++++++++: "+info);
    }

    public class InfoAdapter extends BaseAdapter {
        private List<InfoBean.DataBean.SmsPushDetailBean> infoBeanList;
        private Context context;
        private LayoutInflater inflater;

        public InfoAdapter(List<InfoBean.DataBean.SmsPushDetailBean> infoBeanList, Context context) {
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
                convertView=inflater.inflate(R.layout.activity_info_centre_item,parent,false);
                holder=new ViewHolder();
                holder.title= ((TextView) convertView.findViewById(R.id.title));
                holder.iv= ((ImageView) convertView.findViewById(R.id.iv));
                holder.tip_img=convertView.findViewById(R.id.tip_img);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            InfoBean.DataBean.SmsPushDetailBean smsPushDetailBean = infoBeanList.get(position);
            holder.title.setText(""+ smsPushDetailBean.getMessTitle().trim());
            if (smsPushDetailBean.getIsNotifyAll()==1){
                holder.tip_img.setVisibility(View.VISIBLE);
            }else{
                holder.tip_img.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }
        class ViewHolder {
            TextView title;
            ImageView iv;
            View tip_img;
        }
    }

}
