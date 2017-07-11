package com.gdyd.qmwallet.home.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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

import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.home.MemberDetailsActivity;
import com.gdyd.qmwallet.home.model.MemberDetailsBean;
import com.gdyd.qmwallet.home.presenter.ManageMentPresenter;
import com.gdyd.qmwallet.mine.model.CardBean;
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

/**
 * Created by zanzq on 2017/3/6.
 * 会员管理详情fragment
 */

public class MemberDetailsFragment extends Fragment implements IManageMentView{
    private int page=1;
    private final int pageSize = 30;
    private View view;
    private PullToRefreshListView listview;
  private ManageMentPresenter presenter=new ManageMentPresenter(this);
    private MemberAdapter memberAdapter;
    private  ArrayList<MemberDetailsBean.DataBean.MerchantBean> beanArrayList=new ArrayList<>();
    private  ArrayList<MemberDetailsBean.DataBean.MerchantBean> BFArrayList=new ArrayList<>();
    private String merchantNo;
    private int level;
    private int checkState;
    private TextView count;
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
    private String lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
    private String phoneNumber;


    public static MemberDetailsFragment getInstance(String merchantNo,int level,int checkState) {
        MemberDetailsFragment fragment = new MemberDetailsFragment();
        Bundle args = new Bundle();
        args.putString("MerchantNo", merchantNo);
        args.putInt("Level",level);
        args.putInt("CheckState",checkState);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        //  String name = arguments.getString("name");
        merchantNo = arguments.getString("MerchantNo");
        level = arguments.getInt("Level");
        checkState = arguments.getInt("CheckState");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       if (view==null){
           view = inflater.inflate(R.layout.fm_puto_list, container, false);
       }
        listview = ((PullToRefreshListView) view.findViewById(R.id.listView));
        listview.setMode(PullToRefreshBase.Mode.BOTH);// 两端刷新
//        listview.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("上拉刷新");
//        listview.getLoadingLayoutProxy(false, true).setLastUpdatedLabel("松开加载更多");
        initPull();
        count = ((TextView) view.findViewById(R.id.count));
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page=1;
                lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
                initPull();
                InitQuer();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
                initPull();
                InitQuer();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                phoneNumber = beanArrayList.get(position - 1).get_m().getPhoneNumber();
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
                builder2.setTitle("提示");
                //builder.setIcon(R.drawable.tg2);
                builder2.setMessage("\t\t你要拨打联系人电话吗？");
                builder2.setPositiveButton("马上拨打", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 113);
                            }
                        }else{
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            Uri data = Uri.parse("tel:" + phoneNumber);
                            intent.setData(data);
                            startActivity(intent);
                        }
                        //      context.startService(new Intent(context, AppService.class).putExtra("UpdateUrl",UpdateUrl));
                    }
                });
                builder2.setNegativeButton("取消",null);
                builder2.show();
            }
        });

      return view;
    }

    private void InitQuer() {
        long transTyp = EncryptionHelper.getDate();
        String transTy="1054"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);
        presenter.getManageMentInfo(new PlaceBean("1054",transKe, merchantNo, level, checkState,page,pageSize, MemberDetailsActivity.mearchType,transTyp,MemberDetailsActivity.search_merchant_by_name,MemberDetailsActivity.search_merchant_by_phone));
    }

    public void Refresh(){
        page=1;
        InitQuer();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      if (requestCode==113){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + phoneNumber);
                intent.setData(data);
                startActivity(intent);
            } else
            {
                // Permission Denied
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

    //查询接口回调
     public void initSearch(String name,String phone,int type){
          switch (type){
              case  APPConfig.phone:  //根据手机号码搜索
                  beanArrayList.clear();
                  for (int i = 0; i < BFArrayList.size(); i++) {
                      String number = BFArrayList.get(i).get_m().getPhoneNumber();
                      if (number.contains(phone)){
                          beanArrayList.add(BFArrayList.get(i));
                      }
                  }
                  if (Is.isNoEmptyAll(memberAdapter)){
                      memberAdapter.notifyDataSetChanged();
                  }
                  if (beanArrayList==null||beanArrayList.size()==0){
                      Toast.makeText(getActivity(), "没有符合的商户", Toast.LENGTH_SHORT).show();
                  }
              break;
              case  APPConfig.name://根据名字搜索 当前页
                  beanArrayList.clear();
                  for (int i = 0; i < BFArrayList.size(); i++) {
                      String number = BFArrayList.get(i).get_m().getName();
                      if (!Is.isNoEmpty(number)){
                          number="";
                      }
                      if (number.contains(name)){
                          beanArrayList.add(BFArrayList.get(i));
                      }
                  }
                  if (Is.isNoEmptyAll(memberAdapter)){
                      memberAdapter.notifyDataSetChanged();
                  }
                  if (beanArrayList==null||beanArrayList.size()==0){
                      Toast.makeText(getActivity(), "没有符合的商户", Toast.LENGTH_SHORT).show();
                  }
                  break;
              case  APPConfig.nameANDphone: //根据手机号码和名字联合搜索
                  beanArrayList.clear();
                  for (int i = 0; i < BFArrayList.size(); i++) {
                      MemberDetailsBean.DataBean.MerchantBean merchantBean = BFArrayList.get(i);
                      String number = merchantBean.get_m().getPhoneNumber();
                      String merchantBeanName = merchantBean.get_m().getName();
                      if (!Is.isNoEmpty(merchantBeanName)){
                          merchantBeanName="";
                      }
                      if (number.contains(phone)&&merchantBeanName.contains(name)){
                          beanArrayList.add(BFArrayList.get(i));
                      }

                  }
                  if (Is.isNoEmptyAll(memberAdapter)){
                      memberAdapter.notifyDataSetChanged();
                  }
                  if (beanArrayList==null||beanArrayList.size()==0){
                      Toast.makeText(getActivity(), "没有符合的商户", Toast.LENGTH_SHORT).show();
                  }
                  break;

          }
     }
    private void initData() {
     //   presenter.getManageMentInfo(new PlaceBean("1054","BD6D0831B58D4C76EA087AE5CBF2D40E", merchantNo, level, checkState,page,pageSize,direct));
        InitQuer();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void ManageMentInfo(MemberDetailsBean info) {
        if (!isAdded()){
            return;
        }
        listview.onRefreshComplete();
        if (info!=null&&info.getNResul()==1){
            ArrayList<MemberDetailsBean.DataBean.MerchantBean> list =  info.getDataBean().getList();
            int nPageCount = info.getDataBean().getPageCount();
            int totalCount = info.getDataBean().getTotalCount();
            count.setText("你当前拥有:"+totalCount+"商户");
            if (page==nPageCount){
                T.showMessage(getActivity(), "当前是最后一页",2000);
             //   Toast.makeText(getActivity(), "当前是最后一页", Toast.LENGTH_SHORT).show();
            }else if (page>nPageCount){
                if (nPageCount==0){
                    nPageCount=1;
                }
                page=nPageCount;
//                beanArrayList.clear();
//                BFArrayList.clear();
                if (Is.isNoEmptyAll(memberAdapter)){
//                    memberAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "没有查询到相关信息", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            if (list!=null&&list.size()>0){
                if (page==1){
                    beanArrayList.clear();
                    BFArrayList.clear();
                }
                BFArrayList.addAll(list);
                beanArrayList.addAll(list);
                if (!Is.isNoEmptyAll(memberAdapter)){
                    memberAdapter = new MemberAdapter(beanArrayList, getActivity());
                    listview.setAdapter(memberAdapter);
                } else{
                    memberAdapter.notifyDataSetChanged();
                }
            }else{
//                beanArrayList.clear();
//                BFArrayList.clear();
                if (Is.isNoEmptyAll(memberAdapter)){
//                    memberAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "没有查询到相关信息", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Toast.makeText(getActivity(), "没有更多了..", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void cardInfo(CardBean cardBean) {

    }

    @Override
    public void setcardInfo(String info) {

    }

    @Override
    public void delectBankCard(String info){

    }


    class MemberAdapter extends BaseAdapter{
   private Context context;
        private ArrayList<MemberDetailsBean.DataBean.MerchantBean> list;
        private LayoutInflater inflater;
        private int [] icon={R.drawable.icon_jp,R.drawable.icon_zs,R.drawable.icon_zz,R.drawable.icon_dl,R.drawable.icon_hhr,R.drawable.icon_gd};

        public MemberAdapter(ArrayList<MemberDetailsBean.DataBean.MerchantBean> list, Context context) {
            this.list = list;
            this.context = context;
            inflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size()>0?list.size():0;
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
            ViedHolder holder;
            if (convertView==null){
                convertView=inflater.inflate(R.layout.member_details_list_item,parent,false);
                holder=new ViedHolder();
                holder.name= (TextView) convertView.findViewById(R.id.name);
                holder.face= (ImageView) convertView.findViewById(R.id.face);
                holder.time= (TextView) convertView.findViewById(R.id.time);
                holder.zl= (TextView) convertView.findViewById(R.id.ziliao);
                holder.phone= (TextView) convertView.findViewById(R.id.phone);
                holder.z1= (TextView) convertView.findViewById(R.id.ziliao1);
                holder.z2= (TextView) convertView.findViewById(R.id.ziliao2);
                convertView.setTag(holder);
            }else{
                holder= (ViedHolder) convertView.getTag();
            }
            MemberDetailsBean.DataBean.MerchantBean bean = list.get(position);
            MemberDetailsBean.DataBean.MerchantBean.MBean m = bean.get_m();
            String phoneNumber = m.getPhoneNumber();

            String phone= phoneNumber.substring(0,3)+"****"+ phoneNumber.substring(phoneNumber.length() -4, phoneNumber.length());
            holder.phone.setText(phone);
            String addTime = m.getAddTime();
            if (addTime!=null&&!addTime.equals("")){
                holder.time.setText(addTime.substring(0,10));
            }else {
                holder.time.setText(addTime);
            }

            String name = m.getName();
            if (name!=null&&name.length()>0){
                int length = name.length();
                if (length ==2){
                    name=name.substring(0,1)+"*";
                }
                else if (length >2){
                    name=name.substring(0,1)+"*"+name.substring(length -1, length);
                }
            }else{
                name="未进行实名认证";
            }
            holder.name.setText(name);
            int i = m.getLevelValue() - 1;
            if (i<0){
             i=0;
            }
            holder.face.setImageResource(icon[i]);
            if (m.getCheckState()!=2){
                holder.zl.setText("资料未完善");
                holder.zl.setTextColor(getResources().getColor(R.color.red));
            }else{
                holder.zl.setText("资料已认证");
                holder.zl.setTextColor(getResources().getColor(R.color.green));
            }
            holder.z1.setText("当前等级："+m.getLevelValue());

            if ( bean.getDirect()==1) {
                holder.z2.setText("直接");
            }else if (bean.getDirect()==2){
                holder.z2.setText("间接");
            }

            return convertView;
        }
        class ViedHolder{
            ImageView face;
            TextView name,phone,time,zl,z1,z2;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        T.cancelCurrentToast();
    }
}
