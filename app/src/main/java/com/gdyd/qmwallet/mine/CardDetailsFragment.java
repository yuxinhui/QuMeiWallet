package com.gdyd.qmwallet.mine;

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
import android.support.percent.PercentRelativeLayout;
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

import com.gdyd.qmwallet.BaseFragment;
import com.gdyd.qmwallet.Other.view.LoginActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.home.MemberDetailsActivity;
import com.gdyd.qmwallet.home.model.MemberDetailsBean;
import com.gdyd.qmwallet.home.presenter.ManageMentPresenter;
import com.gdyd.qmwallet.home.view.IManageMentView;
import com.gdyd.qmwallet.home.view.MemberDetailsFragment;
import com.gdyd.qmwallet.mine.model.CardBean;
import com.gdyd.qmwallet.mine.model.PhotoBean;
import com.gdyd.qmwallet.mine.model.PlaceBean;
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

/**
 * Created by zanzq on 2017/6/1.
 */

public class CardDetailsFragment extends BaseFragment implements IManageMentView {
    private int page=1;
    private final int pageSize = 30;
    private View view;
    private PullToRefreshListView listview;
    private ManageMentPresenter presenter=new ManageMentPresenter(this);
    private MemberAdapter memberAdapter;
    private ArrayList<CardBean.DataBean> beanArrayList=new ArrayList<>();
    private  ArrayList<MemberDetailsBean.DataBean.MerchantBean> BFArrayList=new ArrayList<>();
    private String merchantNo;
    private int level;
    private int checkState;
    private TextView count;
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
    private String lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
    private String phoneNumber;
    private boolean selectEditFlag;


    public static CardDetailsFragment getInstance(int level,int checkState) {
        CardDetailsFragment fragment = new CardDetailsFragment();
        Bundle args = new Bundle();
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
        level = arguments.getInt("Level");
        checkState = arguments.getInt("CheckState");
        selectEditFlag = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null){
            view = inflater.inflate(R.layout.fm_puto_list, container, false);
        }
        count = ((TextView) view.findViewById(R.id.count));
        count.setVisibility(View.GONE);
        listview = ((PullToRefreshListView) view.findViewById(R.id.listView));
        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);// 两端刷新
//        listview.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("上拉刷新");
//        listview.getLoadingLayoutProxy(false, true).setLastUpdatedLabel("松开加载更多");
        initPull();
        View inflate = inflater.inflate(R.layout.add_item_footer,null);

        TextView card_type = (TextView) inflate.findViewById(R.id.card_type);
//        if (checkState==2){
//            card_type.setText("添加新的信用卡");
//        }else{
//            card_type.setText("添加新的储蓄卡");
//        }
        listview.getRefreshableView().addFooterView(inflate);
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
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.d("zanZQ", "onItemClick: "+position);
                if ((position-1)==beanArrayList.size()){
                   // Toast.makeText(getActivity(), "最后一个"+position, Toast.LENGTH_SHORT).show();

                 startActivity( new Intent(getActivity(),AddCardActivity.class).putExtra(APPConfig.LOGIN_INFO,BaseFragment.bean)
                 .putExtra("type",checkState));
                    return;
                }else{
                    final int isDefaultCard = beanArrayList.get(position - 1).getIsDefault();
                    if (selectEditFlag){
                        if (checkState==1){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
                            builder.setTitle("提示");
                            //builder.setIcon(R.drawable.tg2);

                            if (isDefaultCard==1){
                                return;
                            }
                            final String cardNo = beanArrayList.get(position - 1).getCardNo();
                            builder.setMessage("\t\t你确定要将尾号为"+cardNo.substring(cardNo.length()-4,cardNo.length())+"的储蓄卡更改为结算卡吗？");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    long transTyp = EncryptionHelper.getDate();
                                    String transTy="1068"+transTyp+"";
                                    String transKe = EncryptionHelper.md5(transTy);

                                    presenter.setcard(new PhotoBean(cardNo,bean.getUserData().getMerchant().getID(),
                                            transTyp,transKe,"1068"));
                                }
                            });
                            builder.setNegativeButton("取消",null);
                            builder.show();
                        }
                    }else {
                        if (isDefaultCard==1){
                            Toast.makeText(getActivity(), "此卡被绑定使用，无法删除", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
                        builder.setTitle("提示");
                        final int bankCardId = beanArrayList.get(position - 1).getId();
                        final String cardNo = beanArrayList.get(position - 1).getCardNo();
                        builder.setMessage("\t\t你确定要将尾号为"+cardNo.substring(cardNo.length()-4,cardNo.length())+"的银行卡解除绑定吗？");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                long transTyp = EncryptionHelper.getDate();
                                String transTy="1078"+transTyp+"";
                                String transKe = EncryptionHelper.md5(transTy);
                                ArrayList<String> mstring = new ArrayList<String>();
                                presenter.deleteCard(new PhotoBean("1078",transKe,bankCardId,mstring,transTyp));
                                //      context.startService(new Intent(context, AppService.class).putExtra("UpdateUrl",UpdateUrl));
                            }
                        });
                        builder.setNegativeButton("取消",null);
                        builder.show();
                    }

                }

            }
        });

        return view;
    }

    private void InitQuer() {
        long transTyp = EncryptionHelper.getDate();
        String transTy="1067"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);
        presenter.getcardInfo(new PhotoBean("1067",transKe,transTyp,level));
    }

    public void Refresh(){
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

//    //查询接口回调
//    public void initSearch(String name,String phone,int type){
//        switch (type){
//            case  APPConfig.phone:  //根据手机号码搜索
//                beanArrayList.clear();
//                for (int i = 0; i < BFArrayList.size(); i++) {
//                    String number = BFArrayList.get(i).get_m().getPhoneNumber();
//                    if (number.contains(phone)){
//                        beanArrayList.add(BFArrayList.get(i));
//                    }
//                }
//                if (Is.isNoEmptyAll(memberAdapter)){
//                    memberAdapter.notifyDataSetChanged();
//                }
//                if (beanArrayList==null||beanArrayList.size()==0){
//                    Toast.makeText(getActivity(), "没有符合的商户", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case  APPConfig.name://根据名字搜索 当前页
//                beanArrayList.clear();
//                for (int i = 0; i < BFArrayList.size(); i++) {
//                    String number = BFArrayList.get(i).get_m().getName();
//                    if (number.contains(name)){
//                        beanArrayList.add(BFArrayList.get(i));
//                    }
//                }
//                if (Is.isNoEmptyAll(memberAdapter)){
//                    memberAdapter.notifyDataSetChanged();
//                }
//                if (beanArrayList==null||beanArrayList.size()==0){
//                    Toast.makeText(getActivity(), "没有符合的商户", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case  APPConfig.nameANDphone: //根据手机号码和名字联合搜索
//                beanArrayList.clear();
//                for (int i = 0; i < BFArrayList.size(); i++) {
//                    MemberDetailsBean.DataBean.MerchantBean merchantBean = BFArrayList.get(i);
//                    String number = merchantBean.get_m().getPhoneNumber();
//                    String merchantBeanName = merchantBean.get_m().getName();
//                    if (number.contains(phone)&&merchantBeanName.contains(name)){
//                        beanArrayList.add(BFArrayList.get(i));
//                    }
//
//                }
//                if (Is.isNoEmptyAll(memberAdapter)){
//                    memberAdapter.notifyDataSetChanged();
//                }
//                if (beanArrayList==null||beanArrayList.size()==0){
//                    Toast.makeText(getActivity(), "没有符合的商户", Toast.LENGTH_SHORT).show();
//                }
//                break;
//
//        }
//    }
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

    }

    @Override
    public void cardInfo(CardBean cardBean ) {
        if (!isAdded()){
            return;
        }
        listview.onRefreshComplete();
        if (cardBean!=null&&cardBean.getNResul()==1){
            ArrayList<CardBean.DataBean> list =  cardBean.getDataBeen();
            if (list!=null&&list.size()>0){
                beanArrayList.clear();
                for (int i = 0; i < list.size(); i++) {
                    CardBean.DataBean e = list.get(i);
                    if (e.getCardType()==checkState){
                        beanArrayList.add(e);
                    }
                }

               //beanArrayList.addAll(list);
                if (!Is.isNoEmptyAll(memberAdapter)){
                    memberAdapter = new MemberAdapter(beanArrayList, getActivity());
                    listview.setAdapter(memberAdapter);
                } else{
                    memberAdapter.notifyDataSetChanged();
                }
            }else{
                beanArrayList.clear();
                BFArrayList.clear();
                if (Is.isNoEmptyAll(memberAdapter)){
                    memberAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "没有查询到相关信息", Toast.LENGTH_SHORT).show();
                }else{
                    memberAdapter = new MemberAdapter(beanArrayList, getActivity());
                    listview.setAdapter(memberAdapter);
                }
            }
        }else{
            Toast.makeText(getActivity(), "没有更多了..", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setcardInfo(String info) {
        Log.d("zanZQ", "setcardInfo: "+info);
        if (info==null||info.trim().equals("")){
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(info);
          //  {"Data":null,"nResul":1,"sMessage":"设置成功"}
            int nResul = jsonObject.getInt("nResul");
            String sMessage = jsonObject.getString("sMessage");
            Toast.makeText(getActivity(), sMessage, Toast.LENGTH_SHORT).show();
            if (nResul==1){
                InitQuer();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delectBankCard(String info){
        Log.d("zanZQ", "setcardInfo: "+info);
        if (info==null||info.trim().equals("")){
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(info);
            //  {"Data":null,"nResul":1,"sMessage":"设置成功"}
            int nResul = jsonObject.getInt("nResul");
            String sMessage = jsonObject.getString("sMessage");
            Toast.makeText(getActivity(), sMessage, Toast.LENGTH_SHORT).show();
            if (nResul==1){
                InitQuer();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class MemberAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<CardBean.DataBean> list;
        private LayoutInflater inflater;
        private String [] bankCode;
        private String [] bankName;
        private int [] icon={R.drawable.gs_card,R.drawable.ny_card,R.drawable.js_card,R.drawable.jt_card,R.drawable.zs_card,
                R.drawable.gd_card,R.drawable.hx_card,
                R.drawable.pf_card,R.drawable.ms_card,R.drawable.gf_card,
        R.drawable.pa_card,R.drawable.zg_card};

        public MemberAdapter(ArrayList<CardBean.DataBean> list, Context context) {
            this.list = list;
            this.context = context;
            inflater=LayoutInflater.from(context);
            bankCode=context.getResources().getStringArray(R.array.bankcode);
            bankName=context.getResources().getStringArray(R.array.bankname);

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
            MemberAdapter.ViedHolder holder;
            if (convertView==null){
                convertView=inflater.inflate(R.layout.card_item,parent,false);
                holder=new ViedHolder();
                holder.card_no= (TextView) convertView.findViewById(R.id.card_no);
                holder.face= (ImageView) convertView.findViewById(R.id.face3);
                holder.isjj= (ImageView) convertView.findViewById(R.id.is_jj);
                convertView.setTag(holder);
            }else{
                holder= (MemberAdapter.ViedHolder) convertView.getTag();
            }
            CardBean.DataBean dataBean = list.get(position);
            String cardNo = dataBean.getCardNo();
            if (cardNo!=null&&cardNo.length()>4){
                holder.card_no.setText("**** **** **** "+cardNo.substring(cardNo.length()-4,cardNo.length()));


            }
            if (dataBean.getIsDefault()==1&&checkState==1){
                if (selectEditFlag){
                    holder.isjj.setVisibility(View.VISIBLE);
                    holder.isjj.setImageResource(R.drawable.isdefault);
                }else {
                    holder.isjj.setVisibility(View.VISIBLE);
                    holder.isjj.setImageResource(R.drawable.icon_bank_delete);
                }

            }else{
                if (selectEditFlag){
                    holder.isjj.setVisibility(View.GONE);
                }else {
                    holder.isjj.setVisibility(View.VISIBLE);
                    holder.isjj.setImageResource(R.drawable.icon_bank_delete);
                }

            }





            String cardType = dataBean.getBankName();
//            for (int i = 0; i < bankCode.length; i++) {
//                if (cardType.equals(bankCode[i])){
//                    holder.card_type.setText(bankName[i]);
//                    holder.face.setImageResource(icon[i]);
//                }
//            }
            if (Is.isNoEmpty(cardType)){if (cardType.contains("招商银行")){
                holder.face.setImageResource(checkState==1? R.drawable.zs_cx:R.drawable.zs_xy);
            }else if (cardType.contains("中国银行")){
                holder.face.setImageResource(checkState==1? R.drawable.zg_cx:R.drawable.zg_xy);
            }else if (cardType.contains("光大银行")){
                holder.face.setImageResource(checkState==1? R.drawable.gd_cx:R.drawable.gd_xy);
            }else if (cardType.contains("广发银行")){
                holder.face.setImageResource(checkState==1? R.drawable.gf_cx: R.drawable.gf_xy);
            }else if (cardType.contains("工商银行")){
                holder.face.setImageResource(checkState==1?R.drawable.gs_cx: R.drawable.gs_xy);
            }else if (cardType.contains("华夏银行")){
                holder.face.setImageResource(checkState==1?R.drawable.hx_cx:R.drawable.hx_xy);
            }else if (cardType.contains("建设银行")){
                holder.face.setImageResource(checkState==1?R.drawable.js_cx:R.drawable.js_xy);
            }else if (cardType.contains("交通银行")){
                holder.face.setImageResource(checkState==1?R.drawable.jt_cx:R.drawable.jt_xy);
            }else if (cardType.contains("民生银行")){
                holder.face.setImageResource(checkState==1?R.drawable.ms_cx:R.drawable.ms_xy);
            }else if (cardType.contains("农业银行")){
                holder.face.setImageResource(checkState==1?R.drawable.ny_cx:R.drawable.ny_xy);
            }else if (cardType.contains("平安银行")){
                holder.face.setImageResource(checkState==1?R.drawable.pa_cx:R.drawable.pa_xy);
            }else if (cardType.contains("浦发银行")){
                holder.face.setImageResource(checkState==1?R.drawable.pf_cx:R.drawable.pf_xy);
            }else if (cardType.contains("广州银行")){
                holder.face.setImageResource(checkState==1?R.drawable.gz_cx:R.drawable.gz_xy);
            }else if (cardType.contains("兴业银行")){
                holder.face.setImageResource(checkState==1?R.drawable.xy_cx:R.drawable.xy_xy);
            }else if (cardType.contains("中信银行")){
                holder.face.setImageResource(checkState==1?R.drawable.zx_cx:R.drawable.zx_xy);
            }
            }

            return convertView;
        }
        class ViedHolder{
            ImageView face,isjj;
            TextView card_no,card_type;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        T.cancelCurrentToast();
    }

    public void  showDelete(boolean isEdit){
            selectEditFlag =isEdit;
            memberAdapter.notifyDataSetChanged();
    }
}
