package com.gdyd.qmwallet.home;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.myview.MyVideoView;

import java.net.URI;
import java.net.URL;

/**
 * 视频播放
 */
public class PlayActivity extends AppCompatActivity {

    private boolean fullscreen=false;
    private MyVideoView videoView;
    private TextView qie;
    private String url;
   private String text="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        videoView = (MyVideoView) findViewById(R.id.video);
        qie = ((TextView) findViewById(R.id.qie));
        url = getIntent().getStringExtra("url");
        if (savedInstanceState != null) {
            url = savedInstanceState.getString("url");
            text=savedInstanceState.getString("name");
            if (text.equals("切换竖屏")){
                RelativeLayout.LayoutParams layoutParams=
                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                videoView.setLayoutParams(layoutParams);
                text="切换竖屏";
                qie.setText(text);
                Log.d("zanZQ", "qiehuan: 全屏" );
                fullscreen = true;//改变全屏/窗口的标记
            }
            qie.setText(text);
        }

        Uri uri= Uri.parse(url);
        //设置视频控制器
        videoView.setMediaController(new MediaController(this));

        //播放完成回调
        videoView.setOnCompletionListener( new MyPlayerOnCompletionListener());

        //设置视频路径
        videoView.setVideoURI(uri);
        //开始播放视频
        videoView.start();

    }

    /**
     * 全/半屏切换
     * @param view
     */
    public void qiehuan(View view) {
        if(!fullscreen){//设置RelativeLayout的全屏模式
            RelativeLayout.LayoutParams layoutParams=
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            videoView.setLayoutParams(layoutParams);
            text="切换竖屏";
            qie.setText(text);

            Log.d("zanZQ", "qiehuan: 全屏" );
            fullscreen = true;//改变全屏/窗口的标记
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else{//设置RelativeLayout的窗口模式
            Log.d("zanZQ", "qiehuan: 窗口");
            RelativeLayout.LayoutParams lp=new  RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics()));
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            videoView.setLayoutParams(lp);
            text="切换横屏";
            qie.setText(text);
            fullscreen = false;//改变全屏/窗口的标记
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
    }
    @Override

    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);


        //切换为竖屏

        if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "竖屏", Toast.LENGTH_LONG).show();
            Log.d("zanZQ", "onConfigurationChanged: 竖屏");
        }

//切换为横屏

        else if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "横屏", Toast.LENGTH_LONG).show();
            Log.d("zanZQ", "onConfigurationChanged: 横屏");
        }
    }

    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText( PlayActivity.this, "播放完成了", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save data :url
        outState.putString("url", url);
        outState.putString("name",text);
    }


}
