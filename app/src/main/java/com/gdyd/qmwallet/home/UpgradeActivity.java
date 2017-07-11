package com.gdyd.qmwallet.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.Other.view.LoginActivity;
import com.gdyd.qmwallet.Other.view.WebViewActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.home.HomeVpAdapter;
import com.gdyd.qmwallet.home.model.JyBean;
import com.gdyd.qmwallet.home.model.MemberInfoBean;
import com.gdyd.qmwallet.home.model.VpAdapter;
import com.gdyd.qmwallet.home.presenter.MainFgPresenter;
import com.gdyd.qmwallet.home.presenter.UpgradePresenter;
import com.gdyd.qmwallet.home.view.IMainFgView;
import com.gdyd.qmwallet.home.view.IUpgradeView;
import com.gdyd.qmwallet.home.view.PayActivity;
import com.gdyd.qmwallet.home.view.PayCodeActivity;
import com.gdyd.qmwallet.mine.model.GradeBean;
import com.gdyd.qmwallet.mine.model.RVAdapter;
import com.gdyd.qmwallet.share.RWebActivity;
import com.gdyd.qmwallet.utils.NetUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpgradeActivity extends BaseActivity implements IUpgradeView ,IMainFgView,VpAdapter.OnUpgradeClick {
    private int prePosition = 1;
    private ViewPager viewpager;
   private List<GradeBean> list;
    private List<ImageView> imageViews;
    private TextView tv;
    private LinearLayout dotLayout;
    private int [] icon={R.drawable.icon_jp,R.drawable.icon_zs,R.drawable.icon_zz,R.drawable.icon_dl,R.drawable.icon_hhr,R.drawable.icon_gd};
   private UpgradePresenter presenter=new UpgradePresenter(this);
    private MainFgPresenter mainFgPresenter=new MainFgPresenter(this);
    private PercentRelativeLayout left_return;
    private TextView left_title;
    private VpAdapter adapter;
    private ArrayList<MemberInfoBean.DataBean.RecomMemberLevelBean> dataLists;
    private String merchantNo;
    private int selectgrade=-1;
    private String[] stringArray;
    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);
        viewpager = ((ViewPager) findViewById(R.id.viewpager));
        dotLayout = ((LinearLayout)findViewById(R.id.dot_layout));
        left_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        left_title = ((TextView) findViewById(R.id.left_title));
        left_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv = ((TextView)findViewById(R.id.tv));
        merchantNo =getIntent().getStringExtra(APPConfig.MERCHANTNO);
        level = getIntent().getIntExtra(APPConfig.LEVEL, 0);
        presenter.getUpgradeInfo();

    }

    private void initData(MemberInfoBean memberInfoBean) {
        list=new ArrayList<>();
     //   stringArray = getResources().getStringArray(R.array.name);
        String[] array = getResources().getStringArray(R.array.text);
        String[] array2 = getResources().getStringArray(R.array.text2);
        dataLists = memberInfoBean.getDataBean().getList();
        for (int i = 0; i < 6; i++) {
            GradeBean gradeBean;
            MemberInfoBean.DataBean.RecomMemberLevelBean list = dataLists.get(i);
            String content = array[i];
            String s = array2[i];
            s=s.replace("a",list.getLevelStandard()+"");
            content= content.replace("b", ((int) (list.getRateZ0() * 100000))+"").replace("c", ((int) (list.getRateK0() * 100000))+"").replace("d", ((int) (list.getRateQ0() * 100000))+"");
            if (list.getLevelType()==1){
                if (i==0){
                    gradeBean = new GradeBean(list.getName(),icon[i],true, content,"无需升级",list.getLevelValue(),level,list.getLevelType(),s);
                }else{
                  // content= String.format(content,list.getLevelStandard(),list.getRate()*10000000,list.getRate()*10000000);
                    gradeBean = new GradeBean(list.getName(),icon[i],true, content,"无需升级",list.getLevelValue(),level,list.getLevelType(),s);
                }

            }else if (i==5){
             //   content= content.replace("b",list.getRateZ0()*100000+"").replace("c",list.getRateK0()*100000+"");
                 gradeBean = new GradeBean(list.getName(),icon[i],false, content,"在线咨询客服",list.getLevelValue(),level,list.getLevelType(),s);
            }else{
                //content= content.replace("b",list.getRateZ0()*100000+"").replace("c",list.getRateK0()*100000+"");
                gradeBean = new GradeBean(list.getName(),icon[i],false, content,"我要升级",list.getLevelValue(),level,list.getLevelType(),s);
            }
            this.list.add(gradeBean);
        }
    }
    private void initData2() {
        imageViews = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            //创建一个新的ImageView
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
          //  iv.setImageResource(imgs[i]);
            imageViews.add(iv);
            View view = new View(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
            lp.leftMargin = 20;
            //设置view的宽高左边距等参数
            view.setLayoutParams(lp);
            //默认情况下所有设置View的所有属性为false
            view.setEnabled(false);
            //给view设置背景选择器，enable属性为true时背景为红色，否则为白色
            view.setBackgroundResource(R.drawable.dot_bg);
            //将View添加到容器中
            dotLayout.addView(view);
        }
        //设置第一个View的enable为true，则该View  背景为红色
        dotLayout.getChildAt(1).setEnabled(true);
    }
    private void initVp() {
        //设置预加载页数，最小值为1，默认为1
        adapter = new VpAdapter(this,list);
        adapter.setOnUpgradeClick(this);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(1);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //页面滑动过程中回调该方法
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //页面滑动结束时回调该方法
            @Override
            public void onPageSelected(int position) {
                position = position % imageViews.size();
                tv.setText(list.get(position).getName());
                dotLayout.getChildAt(prePosition).setEnabled(false);
                dotLayout.getChildAt(position+1).setEnabled(true);
                prePosition = position+1;
            }

            //页面状态发生改变时回调
            //state三种取值：
            //0 表示页面静止
            //1 表示手指在ViewPager上拖动
            //2 表示手指离开ViewPager，页面自由滑动
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        int item = level - 1;
        if (item<0){
            item=0;
        }
        viewpager.setCurrentItem(item);

    }

    @Override
    public void UpgradeInfo(MemberInfoBean memberInfoBean) {
        if (memberInfoBean!=null){
            if (memberInfoBean.getnResul()==1){
                initData( memberInfoBean);
                initData2();
                initVp();
            }
        }


    }

    public void image_return(View view) {
        finish();
    }

    public void ProfitModel(View view) {
        startActivity(new Intent(UpgradeActivity.this, RWebActivity.class).
                putExtra("url", UrlConfig.ylms).
                putExtra(APPConfig.TITLE,getResources().getString(R.string.ylms))
                .putExtra("type",3) );
    }

    @Override
    public void getWXInfo(String info) {
        Log.d("zanZQ", "getWXInfo: "+info);
        if (info!=null&&!info.equals("")){
            if (selectgrade==6){
                checkBean();
                return;
            }
            try {
                JSONObject object = new JSONObject(info);
                String code = object.getString("code");

                if (code.equals("00")){
              //      String format = String.format(UrlConfig.Z_OR_W, "广东赢鼎信息技术有限公司", dataLists.get(selectgrade-1).getLevelStandard(), object.getString("pay_url"), "Z0");
                    Toast.makeText(this, "下单成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpgradeActivity.this, PayCodeActivity.class).putExtra("url",object.getString("pay_url")).putExtra(APPConfig.TITLE,getResources().getString(R.string.home_wx))
                            .putExtra("name","俏美科技有限公司").putExtra("money",dataLists.get(selectgrade-1).getLevelStandard()+""));
                }else{
                    String msg = object.getString("msg");
                    if (msg==null||msg.equals("")){
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
    public void getZFBInfo(String info) {
        Log.d("zanZQ", "getZFBInfo: "+info);
       // Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        if (info!=null&&!info.equals("")){
            if (selectgrade==6){
                checkBean();
                return;
            }
            try {
                JSONObject object = new JSONObject(info);
                String code = object.getString("code");

                if (code.equals("00")){

                 //   String format = String.format(UrlConfig.Z_OR_W, "广东赢鼎信息技术有限公司", dataLists.get(selectgrade-1).getLevelStandard(), object.getString("codeUrl"), "A0");
                    Toast.makeText(this, "下单成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpgradeActivity.this, PayCodeActivity.class).putExtra("url",object.getString("pay_url"))
                            .putExtra(APPConfig.TITLE,getResources().getString(R.string.home_zfb)).putExtra("name","俏美科技有限公司").putExtra("money",dataLists.get(selectgrade-1).getLevelStandard()+""));
                }else{
                    String msg = object.getString("msg");
                    if (msg==null||msg.equals("")){
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
        Log.d("zanZQ", "getKJInfo: "+info);
        if (info!=null&&!info.equals("")){
            if (selectgrade==6){
                checkBean();
                return;
            }
            try {
                JSONObject object = new JSONObject(info);
                String code = object.getString("code");

                if (code.equals("00")){
                    Toast.makeText(this, "下单成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpgradeActivity.this, RWebActivity.class).
                            putExtra("url",object.getString("pay_url")).
                            putExtra(APPConfig.TITLE,getResources().getString(R.string.home_kj))
                    .putExtra("type",1))
                    ;
                }else{
                    String msg = object.getString("msg");
                    if (msg==null||msg.equals("")){
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
    protected void onResume() {
        super.onResume();
        if (!NetUtil.isConnectionNet(UpgradeActivity.this)){
            Toast.makeText(this, "当前无网络连接", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(final int grade) {
        this.selectgrade=grade;
        int levelStandard=0;
        for (int i = 0; i < dataLists.size(); i++) {
            if (dataLists.get(i).getLevelValue()==grade){
                levelStandard = dataLists.get(i).getLevelStandard();
            }
        }
        if (grade==6){
            mainFgPresenter.WXTrans(new JyBean(levelStandard +""
                    , "QMC0000000008",
                    1,"Z0",1,4,""
                    ,merchantNo,selectgrade));
            return;
        }
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            //  alertDialog.setOnCancelListener(new ReOnCancelListener());
            alertDialog.setTitle(R.string.options);
        final int finalLevelStandard = levelStandard;
        alertDialog.setItems(R.array.options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

//                            if (which == 0) {
//                                mainFgPresenter.KJTrans(new JyBean(finalLevelStandard +""
//                                        , "QMC0000000008",
//                                        1,"K0",3,4,"",merchantNo,selectgrade
//                                ));
//                            } else
                               if (which==0){
                                mainFgPresenter.WXTrans(new JyBean(finalLevelStandard +""
                                        , "QMC0000000008",
                                       1,"Z0",1,4,""
                                       ,merchantNo,selectgrade));
                            }else{
                                mainFgPresenter.ZFBTrans(new JyBean(finalLevelStandard +""
                                        ,"QMC0000000008",
                                        1,"A0",1,4,"",merchantNo,selectgrade));
                            }
                        }
                    }
            );
            alertDialog.show();

    }
    public void checkBean() {

        AlertDialog.Builder builder = new AlertDialog.Builder(UpgradeActivity.this, AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle("提示");
        //builder.setIcon(R.drawable.tg2);
        builder.setMessage("\t\t已为你登记,短时间内客服会通知你，请保持联络。。");
        builder.setPositiveButton("确定", null);
        builder.setNegativeButton("取消",null);
        builder.show();

    }
}
