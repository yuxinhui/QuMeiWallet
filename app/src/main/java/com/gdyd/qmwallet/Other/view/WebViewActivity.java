package com.gdyd.qmwallet.Other.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.service.AppService;
import com.gdyd.qmwallet.utils.IntentUtils;
import com.gdyd.qmwallet.utils.Is;
import com.gdyd.qmwallet.utils.NetUtil;

import java.io.File;
import java.io.IOException;

public class WebViewActivity extends BaseActivity {
    private WebView web;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE=1;
    private final  static  int REQ_CHOOSE=2;
    private PercentRelativeLayout image_view;
    private String startUrl;
    public static boolean isUp=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        startUrl = getIntent().getStringExtra("url");
      //  TextView title = (TextView) findViewById(R.id.title);
      //  title.setText(getIntent().getStringExtra(APPConfig.TITLE));
          isUp=false;
        web = (WebView) findViewById(R.id.webView);
        image_view = ((PercentRelativeLayout) findViewById(R.id.left_return));
        image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (web.canGoBack()){
                    if(web.getUrl().equals(startUrl)){
                       onBackPressed();
                    }else{
                        web.goBack();
                    }
                }else{
                    onBackPressed();
                }
            }
        });

        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setBuiltInZoomControls(true);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setSavePassword(true);
        s.setSaveFormData(true);
        s.setCacheMode(WebSettings.LOAD_NO_CACHE);
        s.setJavaScriptEnabled(true);     // enable navigator.geolocation
        s.setGeolocationEnabled(true);
        s.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");     // enable Web Storage: localStorage, sessionStorage
        s.setDomStorageEnabled(true);
        web.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");
        s.setJavaScriptCanOpenWindowsAutomatically(true);
        web.requestFocus();
        //  web.setWebChromeClient(new WebChromeClient());
        //设置 缓存模式
        web.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
// 开启 DOM storage API 功能
        web.getSettings().setDomStorageEnabled(true);

        if (Is.isNoEmpty(startUrl)) {
            if (!NetUtil.isConnectionNet(WebViewActivity.this)) {
                Toast.makeText(this, "当前没有可用网络", Toast.LENGTH_SHORT).show();
            } else {
                web.loadUrl(startUrl);
                web.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        Log.d("zanZQ", "shouldOverrideUrlLoading: "+url);
                        if (url.contains("http://wx.gdydit.cn/onlinepay/paySuccess.jsp")){
                            finish();
                        }else if (url.contains("http://wx.gdydit.cn:8088/register/inreview.html")){
                            WebViewActivity.this.setResult(2, null);
                            finish();
                            //   startActivity(new Intent(WebViewActivity.this, Login.class));
                        }else{
                            web.loadUrl(url);
                        }
                        return true;
                    }

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        Log.d("zanZQ", "onPageStarted: ");
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        Log.d("zanZQ", "onPageFinished: ");
                       // 编写 javaScript方法
//                        String javascript =  "javascript:function hideOther() {" +
//                                "document.getElementsByTagName('body')[0].innerHTML;" +
//                                "document.getElementsByTagName('div')[0].style.display='none';" +
//                                "document.getElementsByTagName('div')[3].style.display='none';" +
//                                "document.getElementsByClassName('dropdown')[0].style.display='none';" +
//                                "document.getElementsByClassName('min')[0].remove();" +
//                                "var divs = document.getElementsByTagName('div');" +
//                                "var lastDiv = divs[divs.length-1];" +
//                                "lastDiv.remove();" +
//                                "document.getElementsByClassName('showme')[0].remove();" +
//                                "document.getElementsByClassName('nei-t3')[1].remove();}";
//
//                        //创建方法
//                        view.loadUrl(javascript);
//
//                        //加载方法
//                        view.loadUrl("javascript:hideOther();");
                    }
                });
            }

        }


        web.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Log.d("zanZQ", "onDownloadStart: "+url);
                isUp=true;
                startService(new Intent(WebViewActivity.this, AppService.class).putExtra("UpdateUrl",url));
                Toast.makeText(WebViewActivity.this, "正在下载...", Toast.LENGTH_SHORT).show();
//                Uri uri=Uri.parse(url);
//                Intent intent=new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
                finish();
            }
        });
        web.setWebChromeClient(new MyWebClient());

        // 我们这里选择好图片后，还要进行处理：

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        Log.d("zanZQ", "onKeyDown: "+keyCode+","+event.getKeyCode());
        if (keyCode == KeyEvent.KEYCODE_BACK&&web.canGoBack()) {
            if (web.canGoBack()) {
                web.goBack();//返回上一页面
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyWebView();
    }
    private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
        mUploadCallbackAboveL = uploadMsg;
        dispatchTakePictureIntent();
    }

    //5.0以下的掉用
    private void openFileChooserImpl(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        dispatchTakePictureIntent();
    }

    //拍照
    private void dispatchTakePictureIntent() {
        selectImgDialog();

    }

    private void selectImgDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        //  alertDialog.setOnCancelListener(new ReOnCancelListener());
        alertDialog.setTitle(R.string.options);
        alertDialog.setItems(R.array.options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            chosePic();
                        } else {
                            takePhoto();
                        }
                    }
                }
        );
        alertDialog.show();
    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(WebViewActivity.this.getPackageManager()) != null) {
            Uri imageUri = null;
            try {
                imageUri = Uri.fromFile(createImageFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(takePictureIntent, FILECHOOSER_RESULTCODE);
        }
    }

    /**
     * 209.
     * 本地相册选择图片
     * 210.
     */
    private void chosePic() {
        Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        String IMAGE_UNSPECIFIED = "image/*";
        innerIntent.setType(IMAGE_UNSPECIFIED); // 查看类型
        Intent wrapperIntent = Intent.createChooser(innerIntent, null);
        startActivityForResult(wrapperIntent, REQ_CHOOSE);
    }


    String mCurrentPhotoPath = null;
    String FileName = "forum";

    //创建文件夹包装图片
    private File createImageFile() throws IOException {
        File storageDir = new File(IntentUtils.getAppPath(WebViewActivity.this) + FileName);

        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        storageDir = new File(IntentUtils.getAppPath(WebViewActivity.this) + FileName + "/", System.currentTimeMillis() + ".jpg");
        //保存当前图片路径
        mCurrentPhotoPath = storageDir.getAbsolutePath();
        return storageDir;
    }

    //onActivityResult回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILECHOOSER_RESULTCODE || requestCode == REQ_CHOOSE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, data);
            } else if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }

    }

    //5.0以上版本，由于api不一样，要单独处理
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, Intent data) {

        if (mUploadCallbackAboveL == null) {
            return;
        }
        Uri result = null;
        if (requestCode == FILECHOOSER_RESULTCODE) {
            File file = new File(mCurrentPhotoPath);
            Uri localUri = Uri.fromFile(file);
            Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
            WebViewActivity.this.sendBroadcast(localIntent);
            result = Uri.fromFile(file);

        } else if (requestCode == REQ_CHOOSE) {
            result = data.getData();
        }
        mUploadCallbackAboveL.onReceiveValue(new Uri[]{result});
        mUploadCallbackAboveL = null;
        return;
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        web.saveState(outState);
    }

    public void destroyWebView() {
        if (web != null) {
            //     web.clearHistory();
            //   web.clearCache(true);
            //    web.loadUrl("about:blank"); // clearView() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
            //    web.freeMemory();
            //  web.pauseTimers();
            web = null; // Note that mWebView.destroy() and mWebView = null do the exact same thing
        }
    }
    private class MyWebClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String mTitle) {
            super.onReceivedTitle(view, mTitle);
            //这里可以用来处理我们的页面标题

        }
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//处理网页面加载进度
        }



        // For Android  < 3.0

        public void openFileChooser(ValueCallback<Uri> filePathCallback) {
            openFileChooserImpl(filePathCallback);
        }

        // For Android  > 4.1.1

        public void openFileChooser(ValueCallback filePathCallback, String acceptType) {
            openFileChooserImpl(filePathCallback);
        }

        // For Android 3.0+

        public void openFileChooser(ValueCallback<Uri> filePathCallback, String acceptType, String capture) {
            openFileChooserImpl(filePathCallback);
        }

        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                         WebChromeClient.FileChooserParams fileChooserParams) {
            openFileChooserImplForAndroid5(filePathCallback);
            return true;
        }


    }
    public class JavascriptInterface {

        private Context context;

        public JavascriptInterface(Context context) {
            this.context = context;
        }
        @android.webkit.JavascriptInterface
        public void openImage(String img) {
            System.out.println(img);
            //
            Intent intent = new Intent();
            intent.putExtra("image", img);
            intent.setClass(context, WebViewActivity.class);
            context.startActivity(intent);
            System.out.println(img);
        }
    }

    @Override
    public void onBackPressed() {

        if (web.canGoBack()){
            if(web.getUrl().equals(startUrl)){
                super.onBackPressed();
            }else{
                web.goBack();
            }
        }else{
            super.onBackPressed();
        }
    }


}
