package com.gdyd.qmwallet.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.percent.PercentRelativeLayout;
import android.view.ViewGroup.LayoutParams;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;



import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.utils.DtoB;

import java.io.File;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;

/**
 * Created by hebei on 2017/7/3.
 */

public class ShaiShaiFenActivity extends BaseActivity implements View.OnClickListener {

    private TextView title,text_myyudian_value;
    private PercentRelativeLayout left_return;
    private PercentRelativeLayout layout_share_yudian_selectimage;
    private LoginInfoBean bean;
    private GridView gridView;
    private Button btnShare;
    private GridViewRadioAdapter gridViewAdapter;
    private ImageView imgshare_select_yudian;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shaishai_fen);
        title = ((TextView) findViewById(R.id.title));
        text_myyudian_value = ((TextView) findViewById(R.id.text_myyudian_value));
        title.setText("晒晒分");

        final   int searchTime = getIntent().getExtras().getInt("yudianfen");
        text_myyudian_value.setText(searchTime+"");
        left_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        layout_share_yudian_selectimage = (PercentRelativeLayout)findViewById(R.id.layout_share_yudian_selectimage);
        left_return.setOnClickListener(this);
        gridView = (GridView)findViewById(R.id.grid_choose_pictures);
        btnShare = (Button)findViewById(R.id.btn_share_commit);
        imgshare_select_yudian = (ImageView)findViewById(R.id.img_yudian_share_selectimage);
        btnShare.setOnClickListener(this);
        gridViewAdapter = new GridViewRadioAdapter(this);
        gridViewAdapter.setSmallImges(new String[]{"icon_yd_big_dh","icon_yd_big_sm","icon_yd_big_sl","icon_yd_big_hp","icon_yd_big_cy","icon_yd_big_xk"});  //传入问题的选项
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                gridViewAdapter.setSelection(position);  //传值更新
                gridViewAdapter.notifyDataSetChanged();  //每一次点击通知adapter重新渲染
            }
        });

        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        PercentRelativeLayout.LayoutParams params = (PercentRelativeLayout.LayoutParams)gridView.getLayoutParams();
        params.height = (width-42)*108/107+5;
        gridView.setLayoutParams(params);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_return:
                finish();
                return;
            case R.id.btn_share_commit:

                OnekeyShare oks2 = new OnekeyShare();
                //关闭sso授权
                oks2.disableSSOWhenAuthorize();

                oks2.addHiddenPlatform(TencentWeibo.NAME);
                //  oks.addHiddenPlatform(WechatFavorite.NAME);
                oks2.addHiddenPlatform(QQ.NAME);
                oks2.addHiddenPlatform(SinaWeibo.NAME);
                oks2.addHiddenPlatform(QZone.NAME);
                oks2.setViewToShare(layout_share_yudian_selectimage);
//                File file2= DtoB.saveBitmap(this,bitmap,R.drawable.logo+"");
//                oks2.setImagePath(file2.getAbsolutePath().toString());//确保SDcard下面存在此张图片

                oks2.show(this);
                break;
        }
    }


    public class GridViewRadioAdapter extends BaseAdapter {
        private Context mContext;
        private int lastPosition = 0;            //记录上一次选中的图片位置，默认不选中
        private String[] smallImges = null;              //放问题内容文字的数组

        public GridViewRadioAdapter(Context mContext) {
            this.mContext = mContext;
        }

        public void setSmallImges(String[] smallImges) {
            this.smallImges = smallImges;
        }

        public void setSelection(int position) {   //在activity中GridView的onItemClickListener中调用此方法，来设置选中位置
            lastPosition = position;
        }

        @Override
        public int getCount() {
            return smallImges.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.yudian_share_item, null);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
                viewHolder.imageCheck = (ImageView)convertView.findViewById(R.id.isselected);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
                viewHolder.imageCheck.setImageResource(R.drawable.icon_yudian_selected);
                viewHolder.imageCheck.setVisibility(View.INVISIBLE);
                switch (position){     //选中状态下设置样式
                    case 0:
                        viewHolder.imageView.setBackgroundResource(R.drawable.icon_yd_small_dh);

                        break;
                    case 1:
                        viewHolder.imageView.setBackgroundResource(R.drawable.icon_yd_small_sm);
                        break;
                    case 2:
                        viewHolder.imageView.setBackgroundResource(R.drawable.icon_yd_small_sl);
                        break;
                    case 3:
                        viewHolder.imageView.setBackgroundResource(R.drawable.icon_yd_small_hp);
                        break;
                    case 4:
                        viewHolder.imageView.setBackgroundResource(R.drawable.icon_yd_small_cy);
                        break;
                    case 5:
                        viewHolder.imageView.setBackgroundResource(R.drawable.icon_yd_small_xk);
                        break;
                }

            if (lastPosition == position){
                viewHolder.imageCheck.setVisibility(View.VISIBLE);
                switch (position){     //选中状态下设置样式
                    case 0:
                        imgshare_select_yudian.setImageResource(R.drawable.icon_yd_big_dh);

                        break;
                    case 1:
                        imgshare_select_yudian.setImageResource(R.drawable.icon_yd_big_sm);
                        break;
                    case 2:
                        imgshare_select_yudian.setImageResource(R.drawable.icon_yd_big_sl);
                        break;
                    case 3:
                        imgshare_select_yudian.setImageResource(R.drawable.icon_yd_big_hp);
                        break;
                    case 4:
                        imgshare_select_yudian.setImageResource(R.drawable.icon_yd_big_cy);
                        break;
                    case 5:
                        imgshare_select_yudian.setImageResource(R.drawable.icon_yd_big_xk);
                        break;
                }


            }


                return convertView;
        }

        class ViewHolder{
            private ImageView imageView;
            private ImageView imageCheck;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public static Bitmap convertViewToBitmap(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        return view.getDrawingCache(true);
    }


}




