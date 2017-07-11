package com.gdyd.qmwallet.home;

import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.TabLayout;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.home.model.MemberInfoBean;
import com.gdyd.qmwallet.home.presenter.UpgradePresenter;
import com.gdyd.qmwallet.home.view.IUpgradeView;
import com.gdyd.qmwallet.home.view.MemberDetailsFragment;
import com.gdyd.qmwallet.home.view.MyAdapter;
import com.gdyd.qmwallet.myview.kankan.wheel.widget.WheelView;
import com.gdyd.qmwallet.utils.Is;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *管理详情布局
 */
public class MemberDetailsActivity extends BaseActivity implements View.OnClickListener{

    private TabLayout tab_layout;
    private ViewPager viewpager;
    List<Fragment> fragmentList = new ArrayList<>();
    private MemberDetailsFragment fragment;
    private PercentRelativeLayout left_return;
    private TextView title;
    private String merchantNo;
    private int level;
    private ImageView image_search;
    private PopupWindow pw;
    private TextView start_search;
    private EditText queryorder_search_phone;
    private EditText queryorder_search_name;
    private TextView queryorder_search_comefrom;

    public static  int mearchType=0;
    public static  String search_merchant_by_name="";
    public static  String search_merchant_by_phone="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);
        tab_layout = ((TabLayout) findViewById(R.id.tab_layout));
        viewpager = ((ViewPager) findViewById(R.id.viewpager));
        left_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        title = ((TextView) findViewById(R.id.title));
        left_return.setOnClickListener(this);
        image_search = ((ImageView) findViewById(R.id.image_search));
        image_search.setOnClickListener(this);
        String[] array = getResources().getStringArray(R.array.tab_layout);
        merchantNo = getIntent().getStringExtra(APPConfig.MERCHANTNO);
        level = getIntent().getIntExtra(APPConfig.LEVEL,0);
        String extra = getIntent().getStringExtra("title");
        if (extra.contains("会员")){
            title.setText(extra);
        }else{
            title.setText(extra+"会员");
        }
        for (int i = 0; i <4 ; i++) {
         //   Bundle args = new Bundle();
         //   args.putString("name", array[i]);
            if (i==1){
                 fragment = MemberDetailsFragment.getInstance(merchantNo,level,2);
            }else if (i==2){
                fragment = MemberDetailsFragment.getInstance(merchantNo,level,1);
            }else {
                fragment = MemberDetailsFragment.getInstance(merchantNo,level,i);
            }
            fragmentList.add(fragment);
        }
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), fragmentList, array);
        viewpager.setAdapter(adapter);
        //将TabLayout和ViewPager关联起来
        tab_layout.setupWithViewPager(viewpager);
    }

    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.left_return:
             finish();
             break;
         case R.id.image_search:
             createPw();
             backgroundAlpha(0.7f);
             break;
     }
    }

    /**
     * 创建查询PopupWindow
     */
    private void createPw(){
        View view = LayoutInflater.from(this).inflate(R.layout.pw_search_member, null);
        start_search = ((TextView) view.findViewById(R.id.start_search));
        queryorder_search_phone = ((EditText) view.findViewById(R.id.queryorder_search_phone));
        queryorder_search_name = ((EditText) view.findViewById(R.id.queryorder_search_name));
        queryorder_search_comefrom = ((TextView) view.findViewById(R.id.queryorder_search_comefrom));
        final String[] arrayType = getResources().getStringArray(R.array.search_merchant_type);
        queryorder_search_comefrom.setText(arrayType[mearchType]);
        queryorder_search_comefrom.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MemberDetailsActivity.this);
                alertDialog.setTitle(null);
                alertDialog.setItems(R.array.search_merchant_type, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mearchType =which;
                                queryorder_search_comefrom.setText(arrayType[which]);
                            }
                        }
                );
                alertDialog.show();
            }
        });
        //构造一个popupWindow对象，
        //1.pw的View
        //2、3表示pw的宽和高
        //4.表示pw是否具有抢焦点的能力，效果同setFocusable
        pw = new PopupWindow(view, getResources().getDisplayMetrics().widthPixels*6/7, getResources().getDisplayMetrics().heightPixels*5/12, true);
      //  pw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        //点击外部区域时关闭popupWindow，必须设置setBackgroundDrawable才有效
        pw.setOutsideTouchable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        start_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_merchant_by_phone = queryorder_search_phone.getText().toString();
                search_merchant_by_name = queryorder_search_name.getText().toString();
                if (search_merchant_by_phone==null){
                    search_merchant_by_phone="";
                }

                if (search_merchant_by_name==null){
                    search_merchant_by_name="";
                }

                fragment= (MemberDetailsFragment) fragmentList.get( viewpager.getCurrentItem());
                MemberDetailsFragment memberDetailsFragment = (MemberDetailsFragment) fragmentList.get(viewpager.getCurrentItem());
                 if (memberDetailsFragment !=null){
                     memberDetailsFragment.Refresh();
                 }

                pw.dismiss();
            }
        });
       // pw.showAsDropDown(title, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -16, getResources().getDisplayMetrics()), 20);
        pw.showAtLocation(title, Gravity.CENTER,0,0);
        //设置当pw打开时点击后退按钮时关闭pw
//        pw.setFocusable(true);
        //showAsDropDown表示pw在某一个控件的下方显示
        //代码中的尺寸都为px
        //第2、3个参数表示pw在x、y轴上的偏移量
    }

    /**
     * 设计屏幕亮度
     * @param bgAlpha 
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0 1f为不透明
        getWindow().setAttributes(lp);
    }


}
