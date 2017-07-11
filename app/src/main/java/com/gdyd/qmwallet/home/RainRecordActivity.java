package com.gdyd.qmwallet.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.home.model.ProfitBean;
import com.gdyd.qmwallet.home.model.YuDianRecordBean;
import com.gdyd.qmwallet.home.presenter.ProfitPresenter;
import com.gdyd.qmwallet.home.view.IProfitView;
import com.gdyd.qmwallet.home.view.RainCreditCircle;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.Is;
import com.gdyd.qmwallet.utils.Tool;
import com.gdyd.qmwallet.utils.ZXingUtils;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;


/**
 * Created by hebei on 2017/7/3.
 */

public class RainRecordActivity extends BaseActivity implements IProfitView,View.OnClickListener{

    private TextView title;
    private TextView tv_tip;
    private PercentRelativeLayout left_return;
    private PercentRelativeLayout layout_xuanyao_root,pl_record_score;
    private TextView text_total_count;
    private ProfitAdapter listAdapter;
    private PullToRefreshListView lv;
    private LoginInfoBean loginInfoBean;
    private RainCreditCircle credit_circle;
    private Button btnXuanyao;
    ImageView imgQrcode;
    private ProfitPresenter presenter=new ProfitPresenter(this);
    private ArrayList< YuDianRecordBean.DataBean.RainCreditItem> list = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raincredit_record);
      final   int searchTime = getIntent().getExtras().getInt("yudianfen");
        title = ((TextView) findViewById(R.id.title));
        tv_tip = ((TextView) findViewById(R.id.tv_tip));

        layout_xuanyao_root = ((PercentRelativeLayout) findViewById(R.id.layout_xuanyao_root));
        pl_record_score = ((PercentRelativeLayout) findViewById(R.id.pl_record_score));
        text_total_count =((TextView) findViewById(R.id.text_total_count));
        title.setText("信用记录");
        credit_circle = ((RainCreditCircle)findViewById(R.id.view_crdit_circle));
        credit_circle.setCreditValue(searchTime);
        left_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        left_return.setOnClickListener(this);
        btnXuanyao = ((Button) findViewById(R.id.text_xuanyao_share));
        btnXuanyao.setOnClickListener(this);
        lv = (PullToRefreshListView)findViewById(R.id.listView);
        loginInfoBean  = ((LoginInfoBean) getIntent().getSerializableExtra(APPConfig.LOGIN_INFO));
        String url= "http://qm.qiaomeishidai.com/qiaomeiqianbao/qmShar_personal.jsp?AgentID="+APPConfig.AgentID+"&RecommendNo=%s&Type=1";
        String fullUrl = String.format(url,loginInfoBean.getUserData().getMerchant().getMerchantNo());
        imgQrcode = ((ImageView) findViewById(R.id.yudian_xuanyao_qrcode));
        imgQrcode.setImageBitmap(new ZXingUtils().createQRImage(Is.isNoEmptyAll(fullUrl)? fullUrl :"",
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics()),null));





        DisplayMetrics dm =getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;



        int wdith = (w_screen-Tool.dip2px(this,40f))/5;
        int trans = searchTime*wdith/200;

        Log.e("TAG","wdith="+wdith +"   getWidth=="+tv_tip.getWidth());

        Log.e("TAG","trans="+trans);


        if(searchTime>200){
            tv_tip.setBackground(ContextCompat.getDrawable(RainRecordActivity.this,R.mipmap.icon_tip_yellow));
        }else if(searchTime>400){
            tv_tip.setBackground(ContextCompat.getDrawable(RainRecordActivity.this,R.mipmap.icon_tip_green));
        }else if(searchTime>600){
            tv_tip.setBackground(ContextCompat.getDrawable(RainRecordActivity.this,R.mipmap.icon_tip_bgcoloor));
        }else if(searchTime>800){
            tv_tip.setBackground(ContextCompat.getDrawable(RainRecordActivity.this,R.mipmap.icon_tip_blue));
        }else{
            tv_tip.setBackground(ContextCompat.getDrawable(RainRecordActivity.this,R.mipmap.icon_tip_red));
        }

        tv_tip.setText(searchTime+"");
        TranslateAnimation animation = new TranslateAnimation(0, trans, 0, 0);
        animation.setFillAfter(true);// True:图片停在动画结束位置
        animation.setDuration(0);
        tv_tip.startAnimation(animation);
        sendRequestGetData();
    }

    @Override
    public void onClick(View v) {//activity_yudian_xuanyao
        switch (v.getId()){
            case R.id.left_return:
                finish();
                return;
            case R.id.text_xuanyao_share:
                OnekeyShare oks2 = new OnekeyShare();
                //关闭sso授权
                oks2.disableSSOWhenAuthorize();

                oks2.addHiddenPlatform(TencentWeibo.NAME);
                //  oks.addHiddenPlatform(WechatFavorite.NAME);
                oks2.addHiddenPlatform(QQ.NAME);
                oks2.addHiddenPlatform(SinaWeibo.NAME);
                oks2.addHiddenPlatform(QZone.NAME);
                oks2.setViewToShare(layout_xuanyao_root);
//                File file2= DtoB.saveBitmap(this,bitmap,R.drawable.logo+"");
//                oks2.setImagePath(file2.getAbsolutePath().toString());//确保SDcard下面存在此张图片

                oks2.show(this);
                break;
        }

    }


    class ProfitAdapter extends BaseAdapter {
        private ArrayList< YuDianRecordBean.DataBean.RainCreditItem> list;
        private Context context;
        private LayoutInflater inflater;


        public ProfitAdapter(Context context, ArrayList< YuDianRecordBean.DataBean.RainCreditItem> list) {
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
                convertView=inflater.inflate(R.layout.activity_yudian_record_item,parent,false);

                holder.textViewFen = (TextView) convertView
                        .findViewById(R.id.text_yudian_fen);
                holder.upateTime = (TextView) convertView
                        .findViewById(R.id.text_yudian_record_updatetime);
                convertView.setTag(holder);
            }else{

                holder= (ViewHolder) convertView.getTag();

            }
            YuDianRecordBean.DataBean.RainCreditItem item = list.get(position);
            holder.upateTime.setText(String.format("更新时间：%s",item.getComputerTime().substring(0,10)));
            holder.textViewFen.setText(String.format("%d分",item.getSource()));

            return convertView;
        }


        class ViewHolder{
            TextView textViewFen,upateTime;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
   public void UpDataInfo(ProfitBean profitBean){

    }

    @Override
    public void getDataYuDianRecord(YuDianRecordBean yuDianRecordBean){
        lv.onRefreshComplete();
        if (yuDianRecordBean!=null&&yuDianRecordBean.getnResul()==1){
            YuDianRecordBean.DataBean dataBean = yuDianRecordBean.getDataBean();
            int merchantCount = dataBean.getCount();
            text_total_count.setText(String.format("%d次加分",merchantCount));
            list = yuDianRecordBean.getDataBean().getList();
            ProfitAdapter manageAdapter = new ProfitAdapter(this, list);
            lv.setAdapter(manageAdapter);
        }

    }

    public void sendRequestGetData() {
        long transTyp = EncryptionHelper.getDate();
        String transTy = "1082" + transTyp;
        String transKe = EncryptionHelper.md5(transTy);
        presenter.getYuDianRecord(new PlaceBean("1082", transKe, loginInfoBean.getUserData().getMerchant().getMerchantNo(), 1, 25, transTyp));

    }

}
