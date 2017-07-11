package com.gdyd.qmwallet.home;

import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.home.view.VideoFragment;

public class VideoListActivity extends BaseActivity implements View.OnClickListener {
    private int[] color = {R.color.colorWhite, R.color.textgray};
    private RadioGroup fg;
    private PercentRelativeLayout left_return;
    private Fragment fragment;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        fg = ((RadioGroup) findViewById(R.id.rg));
        left_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        left_return.setOnClickListener(this);
        fg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < 3; i++) {
                    RadioButton childAt = (RadioButton) fg.getChildAt(i);
                    childAt.setTextColor(getResources().getColor(color[1]));
                    childAt.setBackground(null);
                }
                switch (checkedId) {
                    case R.id.xc:
                        fragment= VideoFragment.getInstance(1);
                        RadioButton childAt = (RadioButton) fg.getChildAt(0);
                        childAt.setTextColor(getResources().getColor(color[0]));
                        childAt.setBackground(getResources().getDrawable(R.drawable.rectangle_blue));
                        break;
                    case R.id.jx:
                        fragment= VideoFragment.getInstance(2);
                        RadioButton childAt1 = (RadioButton) fg.getChildAt(1);
                        childAt1.setTextColor(getResources().getColor(color[0]));
                        childAt1.setBackground(getResources().getDrawable(R.drawable.rectangle_blue));
                        break;
                    case R.id.js:
                        fragment= VideoFragment.getInstance(3);
                        RadioButton childAt2 = (RadioButton) fg.getChildAt(2);
                        childAt2.setTextColor(getResources().getColor(color[0]));
                        childAt2.setBackground(getResources().getDrawable(R.drawable.rectangle_blue));
                        break;
                }
                //切换Fragment方式一：replace方法每次会将当前的Fragment销毁掉，然后创建新的Fragment实例添加进来
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.ll, fragment);
//                transaction.commit();
                //切换Fragment方式二：add、show、hide
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断Fragment是否已经添加到事务中
                if (fragment.isAdded()) {
                    //隐藏当前的Fragment
                    transaction.hide(currentFragment)
                            //显示所点击RadioButton对应的Fragment
                            .show(fragment).commit();
                } else {
                    transaction.hide(currentFragment).add(R.id.ll, fragment).commit();
                }
                currentFragment = fragment;
            }
        });
        //初始状态下显示聊天Fragment
        currentFragment =VideoFragment.getInstance(1);
        getSupportFragmentManager().beginTransaction().replace(R.id.ll, currentFragment).commit();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_return:
                finish();
                break;

        }
    }
}
