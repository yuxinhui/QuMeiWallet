package com.gdyd.qmwallet.mine;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseActivity;
import com.gdyd.qmwallet.BaseFragment;
import com.gdyd.qmwallet.MainActivity;
import com.gdyd.qmwallet.Other.model.ILoginData;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.Other.presenter.PhotoPresenter;
import com.gdyd.qmwallet.Other.view.IPhotoView;
import com.gdyd.qmwallet.Other.view.LoginActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.mine.model.PhotoBean;
import com.gdyd.qmwallet.myview.kankan.wheel.widget.WheelView;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.ReadImgToBinary2;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RealNameActivity extends BaseActivity implements IPhotoView {
     private static  final int Z=1;
     private static  final int F=2;
    private static final  int N=3;
    private static final  int Y=4;
    private static final  int size=400;
    private static final int TAKE_PHOTO_REQUEST_CODE = 1;
    private PopupWindow pw;
    private File file1;
    private File file2;
    private File file3;
    private File file4;
    private ImageView img_z;
    private ImageView img_f;
    private ImageView img_n;
    private ImageView img_y;
    private PercentRelativeLayout image_return;
    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private Bitmap bitmap3;
    private Bitmap bitmap4;
    private PhotoPresenter presenter=new PhotoPresenter(this);
    private String phone="1111";
    private ArrayList<String>  list=new ArrayList<>();
    private int id;
    private String merchantNo;
    private LoginInfoBean bean;
  private int requs=0;
    private PercentRelativeLayout tip_ddsq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name);
        image_return = ((PercentRelativeLayout) findViewById(R.id.left_return));
        image_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button submit = (Button) findViewById(R.id.submit);
        tip_ddsq = ((PercentRelativeLayout) findViewById(R.id.tip_ddsq));
        bean = ((LoginInfoBean) getIntent().getSerializableExtra(APPConfig.LOGIN_INFO));
        list=getIntent().getStringArrayListExtra("list");
       phone=bean.getUserData().getMerchant().getPhoneNumber();
       //  list = bean.getUserData().getImagelistUrl();
        id = bean.getUserData().getMerchant().getID();
        merchantNo = bean.getUserData().getMerchant().getMerchantNo();
        Log.d("zanZQ", "onCreate: "+id);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.getUserData().getMerchant().getCheckState()==2||bean.getUserData().getMerchant().getCheckState()==1){
                    finish();
                    return;
                }
              //  list.clear();
                try {
                    bitmap1= ((BitmapDrawable) img_z.getDrawable()).getBitmap();
                    bitmap2= ((BitmapDrawable) img_f.getDrawable()).getBitmap();
                    bitmap3= ((BitmapDrawable) img_n.getDrawable()).getBitmap();
                    bitmap4= ((BitmapDrawable) img_y.getDrawable()).getBitmap();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (bitmap1!=null&&img_z.getContentDescription().equals("z")){
                    Toast.makeText(RealNameActivity.this, "请上传身份证正面照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (bitmap2!=null&&img_f.getContentDescription().equals("f")){
                    Toast.makeText(RealNameActivity.this, "请上传身份证反面照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (bitmap3!=null&&img_n.getContentDescription().equals("n")){
                    Toast.makeText(RealNameActivity.this, "请上传手拿身份证照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (bitmap4!=null&&img_y.getContentDescription().equals("y")){
                    Toast.makeText(RealNameActivity.this, "请上传银行卡正面照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                tip_ddsq.setVisibility(View.VISIBLE);
                backgroundAlpha(0.7f);
                long transTyp = EncryptionHelper.getDate();
                String transTy="1009"+transTyp+"";
                String transKe = EncryptionHelper.md5(transTy);
                if (file1!=null){
                    presenter.SubmitPhoto(new PhotoBean("1009",transKe, ReadImgToBinary2.imgToBase64(file1.getAbsolutePath(), 400,400,bitmap1, null),"1",transTyp,merchantNo),Z);
              return;
                };
                if (file2!=null){
                    presenter.SubmitPhoto(new PhotoBean("1009",transKe, ReadImgToBinary2.imgToBase64(file2.getAbsolutePath(),size,size, bitmap2, null),"2",transTyp,merchantNo),F);
                    return;
                };
                if (file3!=null){

                    presenter.SubmitPhoto(new PhotoBean("1009",transKe, ReadImgToBinary2.imgToBase64(file3.getAbsolutePath(),size,size, bitmap3, null),"3",transTyp,merchantNo),N);
                    return;
                };
                if (file4!=null){
                    presenter.SubmitPhoto(new PhotoBean("1009",transKe, ReadImgToBinary2.imgToBase64(file4.getAbsolutePath(),size,size, bitmap4, null),"4",transTyp,merchantNo),Y);
                    return;
                };
                if (list.size()==4){
                    long transTyp2 = EncryptionHelper.getDate();
                    String transTy2="1047"+transTyp2+"";
                    String transKe2 = EncryptionHelper.md5(transTy2);
                    presenter.SubmitPhotoPath(new PhotoBean("1047",transKe2,id,list,transTyp2));
                    return;
                }
                finish();

           //    String imageBuf = ReadImgToBinary2.imgToBase64(file1.getAbsolutePath(), null, null);
              //  String imageBuf2 = ReadImgToBinary2.imgToBase64(null, bitmap1, null);
             //   Log.d("zanZQ", "onClick: "+imageBuf);
             //   Bitmap bitmap = ReadImgToBinary2.base64ToBitmap(imageBuf, "1.jpg", null);
              //  img_y.setImageBitmap(bitmap);
           }
        });
        img_z = ((ImageView) findViewById(R.id.img_z));
        img_f = ((ImageView) findViewById(R.id.img_f));
        img_n = ((ImageView) findViewById(R.id.img_n));
        img_y = ((ImageView) findViewById(R.id.img_y));
        img_z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPw(getString(R.string.pw_id_z),R.drawable.tip_z,Z);
            }
        });
        img_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPw(getString(R.string.pw_id_f),R.drawable.tip_f,F);
            }
        });
        img_y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPw(getString(R.string.pw_blank_z),R.drawable.tip_y,Y);
            }
        });
        img_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPw(getString(R.string.pw_id_n),R.drawable.tip_n,N);
            }
        });
        if (list!=null&&list.size()>0){
            try {
                check();
           //     presenter.SubmitPhotoPath(new PhotoBean("1047","9A1ADE54071C4B95657AF264E260A18A",id,list));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (ContextCompat.checkSelfPermission(RealNameActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(RealNameActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        N);

            }
        }

    }



    private void check() {
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            String path= UrlConfig.PHOTO_URI+s;
            Log.d("zanZQ", "check: "+path);
            String substring = s.substring(s.length() - 5, s.length() - 4);
            for (int j = 0; j < list.size(); j++) {
               if (i!=j){
                   String a = list.get(j);
                   String sub = a.substring(a.length() - 5, a.length() - 4);
                   if (sub.equals(substring)){
                       list.remove(j);
                   }
               }
            }

           switch (substring){
               case Z+"":
                   Picasso.with(this).load(path).resize(400,520).into(img_z);
                    img_z.setContentDescription("is");
             //     bitmap1= ((BitmapDrawable) img_z.getDrawable()).getBitmap();
                   break;
               case F+"":
                   Picasso.with(this).load(path).resize(400,520).into(img_f);
                   img_f.setContentDescription("is");
                //;   bitmap2= ((BitmapDrawable) img_f.getDrawable()).getBitmap();
                   break;
               case N+"":
                   Picasso.with(this).load(path).resize(400,520).into(img_n);
                   img_n.setContentDescription("is");
               //    bitmap3= ((BitmapDrawable) img_n.getDrawable()).getBitmap();
                   break;
               case Y+"":
                   Picasso.with(this).load(path).resize(400,520).into(img_y);
                   img_y.setContentDescription("is");
              //     bitmap4= ((BitmapDrawable) img_y.getDrawable()).getBitmap();
                   break;
           }
        }
    }

    private void createPw(String title, final int photo, final int requestCode) {
        requs=requestCode;
        View view = LayoutInflater.from(RealNameActivity.this).inflate(R.layout.pw_tip, null);
        TextView tip_title = (TextView) view.findViewById(R.id.tip_title);
        tip_title.setText(title);
        ImageView tip_photo = (ImageView) view.findViewById(R.id.tip_photo);
        tip_photo.setImageResource(photo);
        Button submit = (Button) view.findViewById(R.id.submit);
        //构造一个popupWindow对象，
        //1.pw的View
        //2、3表示pw的宽和高
        //4.表示pw是否具有抢焦点的能力，效果同setFocusable
        pw = new PopupWindow(view, getResources().getDisplayMetrics().widthPixels*5/7, getResources().getDisplayMetrics().heightPixels/2, true);
        //点击外部区域时关闭popupWindow，必须设置setBackgroundDrawable才有效
        pw.setOutsideTouchable(false);
        pw.setBackgroundDrawable(new BitmapDrawable());
        //设置当pw打开时点击后退按钮时关闭pw
//        pw.setFocusable(true);
        //showAsDropDown表示pw在某一个控件的下方显示
        //代码中的尺寸都为px
        //第2、3个参数表示pw在x、y轴上的偏移量
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(RealNameActivity.this, Manifest.permission.CAMERA)
                              != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ActivityCompat.requestPermissions(RealNameActivity.this, new String[]{Manifest.permission.CAMERA},
                                TAKE_PHOTO_REQUEST_CODE    );
                    }
                } else if (requestCode==1){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    file1 = new File(Environment.getExternalStorageDirectory(), R.drawable.tip_z+ "1.jpg");
//                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//                        ContentValues contentValues = new ContentValues(1);
//                        contentValues.put(MediaStore.Images.Media.DATA, file1.getAbsolutePath());
//                        Uri uri = RealNameActivity.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                        startActivityForResult(intent, requestCode);
//                    }else{
                    Uri imageUri;
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                        imageUri = FileProvider.getUriForFile(RealNameActivity.this, "com.gdyd.qmwallet.fileprovider", file1);
                    }else {
                        imageUri=Uri.fromFile(file1);
                    }
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
//                    Uri imageUri = FileProvider.getUriForFile(RealNameActivity.this, "com.gdyd.qmwallet.fileprovider", file1);
                    //通过FileProvider创建一个content类型的Uri
                       intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, requestCode);
            //        }

                }else if (requestCode==2){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    file2 = new File(Environment.getExternalStorageDirectory(),  R.drawable.tip_z + "2.jpg");
//                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//                        ContentValues contentValues = new ContentValues(1);
//                        contentValues.put(MediaStore.Images.Media.DATA, file2.getAbsolutePath());
//                        Uri uri = RealNameActivity.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                        startActivityForResult(intent, requestCode);
//                    }else{
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
//                    Uri imageUri = FileProvider.getUriForFile(RealNameActivity.this, "com.gdyd.qmwallet.fileprovider", file2);
                    Uri imageUri;
                    if (Build.VERSION.SDK_INT >Build.VERSION_CODES.KITKAT) {
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                        imageUri = FileProvider.getUriForFile(RealNameActivity.this, "com.gdyd.qmwallet.fileprovider", file2);
                    }else {
                        imageUri=Uri.fromFile(file2);
                    }
                    //通过FileProvider创建一个content类型的Uri
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, requestCode);
              //      }

                }else if (requestCode==3){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    file3 = new File(Environment.getExternalStorageDirectory(), R.drawable.tip_z + "3.jpg");
//                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//                        ContentValues contentValues = new ContentValues(1);
//                        contentValues.put(MediaStore.Images.Media.DATA, file3.getAbsolutePath());
//                        Uri uri = RealNameActivity.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                        startActivityForResult(intent, requestCode);
//                    }else{
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
//                    Uri imageUri = FileProvider.getUriForFile(RealNameActivity.this, "com.gdyd.qmwallet.fileprovider", file3);
                    Uri imageUri;
                    if (Build.VERSION.SDK_INT >Build.VERSION_CODES.KITKAT) {
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                        imageUri = FileProvider.getUriForFile(RealNameActivity.this, "com.gdyd.qmwallet.fileprovider", file3);
                    }else {
                        imageUri=Uri.fromFile(file3);
                    }
                    //通过FileProvider创建一个content类型的Uri
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, requestCode);
               //     }
                }else if (requestCode==4){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    file4 = new File(Environment.getExternalStorageDirectory(),  R.drawable.tip_z + "4.jpg");
//                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//                        ContentValues contentValues = new ContentValues(1);
//                        contentValues.put(MediaStore.Images.Media.DATA, file4.getAbsolutePath());
//                        Uri uri = RealNameActivity.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                        startActivityForResult(intent, requestCode);
//                    }else{
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
//                    Uri imageUri = FileProvider.getUriForFile(RealNameActivity.this, "com.gdyd.qmwallet.fileprovider", file4);
                    Uri imageUri;
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                        imageUri = FileProvider.getUriForFile(RealNameActivity.this, "com.gdyd.qmwallet.fileprovider", file4);
                    }else {
                        imageUri=Uri.fromFile(file4);
                    }
                    //通过FileProvider创建一个content类型的Uri
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, requestCode);
              //      }
                }
                pw.dismiss();
            }
        });
        backgroundAlpha(0.7f);
        pw.showAtLocation(view,Gravity.CENTER,0,0);
        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == TAKE_PHOTO_REQUEST_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (requs==1){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    file1 = new File(Environment.getExternalStorageDirectory(), R.drawable.tip_z+ "1.jpg");
//                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//                        ContentValues contentValues = new ContentValues(1);
//                        contentValues.put(MediaStore.Images.Media.DATA, file1.getAbsolutePath());
//                        Uri uri = RealNameActivity.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                        startActivityForResult(intent, requestCode);
//                    }else{
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
//                    Uri imageUri = FileProvider.getUriForFile(RealNameActivity.this, "com.gdyd.qmwallet.fileprovider", file1);
                    Uri imageUri;
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                        imageUri = FileProvider.getUriForFile(RealNameActivity.this, "com.gdyd.qmwallet.fileprovider", file1);
                    }else {
                        imageUri=Uri.fromFile(file1);
                    }
                    //通过FileProvider创建一个content类型的Uri
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, requestCode);
                //    }

                }else if (requs==2){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    file2 = new File(Environment.getExternalStorageDirectory(),  R.drawable.tip_z + "2.jpg");
//                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//                        ContentValues contentValues = new ContentValues(1);
//                        contentValues.put(MediaStore.Images.Media.DATA, file2.getAbsolutePath());
//                        Uri uri = RealNameActivity.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                        startActivityForResult(intent, requestCode);
//                    }else{
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
//                    Uri imageUri = FileProvider.getUriForFile(RealNameActivity.this, "com.gdyd.qmwallet.fileprovider", file2);
                    Uri imageUri;
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                        imageUri = FileProvider.getUriForFile(RealNameActivity.this, "com.gdyd.qmwallet.fileprovider", file2);
                    }else {
                        imageUri=Uri.fromFile(file2);
                    }
                    //通过FileProvider创建一个content类型的Uri
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, requestCode);
                //    }

                }else if (requs==3){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    file3 = new File(Environment.getExternalStorageDirectory(), R.drawable.tip_z + "3.jpg");
//                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//                        ContentValues contentValues = new ContentValues(1);
//                        contentValues.put(MediaStore.Images.Media.DATA, file3.getAbsolutePath());
//                        Uri uri = RealNameActivity.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                        startActivityForResult(intent, requestCode);
//                    }else{
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
//                    Uri imageUri = FileProvider.getUriForFile(RealNameActivity.this, "com.gdyd.qmwallet.fileprovider", file3);
                    Uri imageUri;
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                        imageUri = FileProvider.getUriForFile(RealNameActivity.this, "com.gdyd.qmwallet.fileprovider", file3);
                    }else {
                        imageUri=Uri.fromFile(file3);
                    }
                    //通过FileProvider创建一个content类型的Uri
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, requestCode);
                  //  }
                }else if (requs==4){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    file4 = new File(Environment.getExternalStorageDirectory(),  R.drawable.tip_z + "4.jpg");
//                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//                        ContentValues contentValues = new ContentValues(1);
//                        contentValues.put(MediaStore.Images.Media.DATA, file4.getAbsolutePath());
//                        Uri uri = RealNameActivity.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                        startActivityForResult(intent, requestCode);
//                    }else{
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
//                    Uri imageUri = FileProvider.getUriForFile(RealNameActivity.this, "com.gdyd.qmwallet.fileprovider", file4);
                    Uri imageUri;
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                        imageUri = FileProvider.getUriForFile(RealNameActivity.this, "com.gdyd.qmwallet.fileprovider", file4);
                    }else {
                        imageUri=Uri.fromFile(file4);
                    }
                    //通过FileProvider创建一个content类型的Uri
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, requestCode);
                 //   }
                }
            } else {
                // Permission Denied
                Toast.makeText(RealNameActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == N){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,"相机已获得授权！",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"相机未获得授权！",Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Z && resultCode == RESULT_OK){
          //  String stringExtra = data.getStringExtra(MediaStore.EXTRA_OUTPUT);
            //  bitmap1 = BitmapFactory.decodeFile(file.getAbsolutePath());

            if (file1==null){
                   file1=new File(Environment.getExternalStorageDirectory(), R.drawable.tip_z + "1.jpg");
                try {
                    boolean newFile = file1.createNewFile();
                    if (newFile){
                        String path = file1.getAbsolutePath().toString();
                        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                        bitmap1 = ReadImgToBinary2.getBitmap(path, img_z.getWidth(), img_z.getHeight());
                        img_z.setImageBitmap(bitmap1);
                        img_z.setContentDescription("is");
                    }else{
                        img_z.setContentDescription("z");
                        Toast.makeText(this, "保存失败！请重新拍照", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return;
            }else {
                String path = file1.getAbsolutePath().toString();
               // Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                bitmap1 = ReadImgToBinary2.getBitmap(path, img_z.getWidth(), img_z.getHeight());
                img_z.setImageBitmap(bitmap1);
                img_z.setContentDescription("is");
            }

        }else if (requestCode==F && resultCode == RESULT_OK){
          //  bitmap2 = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (file2==null){
                file2=new File(Environment.getExternalStorageDirectory(), R.drawable.tip_z + "2.jpg");
                try {
                    boolean newFile = file2.createNewFile();
                    if (newFile){
                        String path = file2.getAbsolutePath().toString();
                   //     Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                        bitmap2 = ReadImgToBinary2.getBitmap(path, img_f.getWidth(), img_f.getHeight());
                        img_f.setImageBitmap(bitmap2);
                        img_f.setContentDescription("is");
                    }else{
                        img_f.setContentDescription("f");
                        Toast.makeText(this, "保存失败！请重新拍照", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;
            }else {
            bitmap2 = ReadImgToBinary2.getBitmap(file2.getAbsolutePath().toString(), img_f.getWidth(), img_f.getHeight());
            img_f.setImageBitmap(bitmap2);
            img_f.setContentDescription("is");
            }
        }else if (requestCode==N && resultCode == RESULT_OK){
          //  bitmap3 = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (file3==null){
                file3=new File(Environment.getExternalStorageDirectory(), R.drawable.tip_z + "3.jpg");
                try {
                    boolean newFile = file3.createNewFile();
                    if (newFile){
                        String path = file3.getAbsolutePath().toString();
                  //      Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                        bitmap3 = ReadImgToBinary2.getBitmap(path, img_n.getWidth(), img_n.getHeight());
                        img_n.setImageBitmap(bitmap3);
                        img_n.setContentDescription("is");
                    }else{
                        img_n.setContentDescription("n");
                        Toast.makeText(this, "保存失败！请重新拍照", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;
            }else {
            bitmap3 = ReadImgToBinary2.getBitmap(file3.getAbsolutePath().toString(), img_n.getWidth(), img_n.getHeight());
            img_n.setImageBitmap(bitmap3);
            img_n.setContentDescription("is");
            }
        } else if (requestCode==Y && resultCode == RESULT_OK){
            if (file4==null){
                file4=new File(Environment.getExternalStorageDirectory(), R.drawable.tip_z + "4.jpg");
                try {
                    boolean newFile = file4.createNewFile();
                    if (newFile){
                        String path = file4.getAbsolutePath().toString();
                     //   Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                        bitmap4 = ReadImgToBinary2.getBitmap(path, img_y.getWidth(), img_y.getHeight());
                        img_y.setImageBitmap(bitmap4);
                        img_y.setContentDescription("is");
                    }else{
                        img_y.setContentDescription("y");
                        Toast.makeText(this, "保存失败！请重新拍照", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;
            }else {
            bitmap4 = ReadImgToBinary2.getBitmap(file4.getAbsolutePath().toString(), img_y.getWidth(), img_y.getHeight());
          //  bitmap4 = BitmapFactory.decodeFile(file.getAbsolutePath());
            img_y.setContentDescription("is");
            img_y.setImageBitmap(bitmap4);
            }
        }
        if (list!=null&&list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                String substring = s.substring(s.length() - 5, s.length() - 4);
                // String path= UrlConfig.PHOTO_URI+s;
                if (substring.equals(requestCode+"")){
                    list.remove(i);
                   // return;
                }
            }
        }
    }
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 0f-1f,1f为不透明
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0 1f为不透明
        getWindow().setAttributes(lp);
    }

    @Override
    public void UpPhoto(String info,int type) {
        try {
            if (info==null||info.equals("")){
                Toast.makeText(this, "上传图片失败，请重新上传", Toast.LENGTH_SHORT).show();
                //list.clear();
                delete();
                return;
            }
            JSONObject object = new JSONObject(info);
            int nResul = object.getInt("nResul");
            if (nResul==1){
                list.add(object.getString("Data"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        long transTyp = EncryptionHelper.getDate();
        String transTy="1009"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);
        long transTyp2 = EncryptionHelper.getDate();
        String transTy2="1047"+transTyp2+"";
        String transKe2 = EncryptionHelper.md5(transTy2);
        switch (type){
            case Z:
                Log.d("zanZQ", "UpPhoto:1 ");
                if (file2!=null){
                    presenter.SubmitPhoto(new PhotoBean("1009",transKe, ReadImgToBinary2.imgToBase64(file2.getAbsolutePath(),size,size, bitmap2, null),"2",transTyp,merchantNo),F);
                }else if (file3!=null){
                    presenter.SubmitPhoto(new PhotoBean("1009",transKe, ReadImgToBinary2.imgToBase64(file3.getAbsolutePath(),size,size, bitmap3, null),"3",transTyp,merchantNo),N);
                }else if (file4!=null){
                    presenter.SubmitPhoto(new PhotoBean("1009",transKe, ReadImgToBinary2.imgToBase64(file4.getAbsolutePath(),size,size, bitmap4, null),"4",transTyp,merchantNo),Y);
                }else{
                    if (list.size()==4){

                        presenter.SubmitPhotoPath(new PhotoBean("1047",transKe2,id,list,transTyp2));
                    }else{
                       // list.clear();
                        delete();
                        tip_ddsq.setVisibility(View.GONE);
                        backgroundAlpha(1f);
                        Toast.makeText(this, "请补充完信息", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case F:
                Log.d("zanZQ", "UpPhoto: 2");
                if (file3!=null){
                    presenter.SubmitPhoto(new PhotoBean("1009",transKe, ReadImgToBinary2.imgToBase64(file3.getAbsolutePath(),size,size, bitmap3, null),"3",transTyp,merchantNo),N);
                }else if (file4!=null){
                    presenter.SubmitPhoto(new PhotoBean("1009",transKe, ReadImgToBinary2.imgToBase64(file4.getAbsolutePath(),size,size, bitmap4, null),"4",transTyp,merchantNo),Y);
                }else {
                    if (list.size()==4){
                        presenter.SubmitPhotoPath(new PhotoBean("1047",transKe2,id,list,transTyp2));
                    }else{
                      //  list.clear();
                        delete();
                        tip_ddsq.setVisibility(View.GONE);
                        backgroundAlpha(1f);
                        Toast.makeText(this, "请补充完信息", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case N:
                Log.d("zanZQ", "UpPhoto:3 ");
                if (file4!=null){
                    presenter.SubmitPhoto(new PhotoBean("1009",transKe, ReadImgToBinary2.imgToBase64(file4.getAbsolutePath(),size,size, bitmap4, null),"4",transTyp,merchantNo),Y);
                }else{
                    if (list.size()==4){
                        presenter.SubmitPhotoPath(new PhotoBean("1047",transKe2,id,list,transTyp2));
                    }else{
                     //   list.clear();
                        delete();
                        tip_ddsq.setVisibility(View.GONE);
                        backgroundAlpha(1f);
                        Toast.makeText(this, "请补充完信息", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case Y:
                if (list.size()==4){
                    presenter.SubmitPhotoPath(new PhotoBean("1047",transKe2,id,list,transTyp2));
                }else{
                   // list.clear();
                    delete();
                    tip_ddsq.setVisibility(View.GONE);
                    backgroundAlpha(1f);
                    Toast.makeText(this, "请补充完信息", Toast.LENGTH_SHORT).show();
                }
                Log.d("zanZQ", "UpPhoto: 4"+id);
               break;
            default:
                break;
        }
        Log.d("zanZQ", "UpPhoto: "+info);
    }

    @Override
    public void UpPhotoPath(String info) {
        Log.d("zanZQ", "UpPhotoPath: "+info);
        tip_ddsq.setVisibility(View.GONE);
        backgroundAlpha(1f);
        if (info==null||info.equals("")){
            Toast.makeText(this, "上传图片失败，请重新上传", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(info);
            int nResul = jsonObject.getInt("nResul");
            if (nResul==1){
                Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show();
                bean.getUserData().getMerchant().setCheckState(1);
                BaseFragment.bean=bean;
                startActivity(new Intent(RealNameActivity.this, MainActivity.class).putExtra(APPConfig.LOGIN_INFO,bean));
                finish();
            }else{
                Toast.makeText(this, "上传失败", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("zanZQ", "UpPhotoPath: 上传成功");
    }

    @Override
    public void UpPhotoHead(String info) {

    }

    public void image_return(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("zanZQ", "onDestroy: 拍照");
        //
        if (file1!=null){
            Log.d("zanZQ", "onDestroy: "+file1.getAbsolutePath().toString());
        }
//        if (file1!=null&&file1.exists()){
//            file1.delete();
//        }
//        if (file2!=null&&file2.exists()){
//            file2.delete();
//        }
//        if (file3!=null&&file3.exists()){
//            file3.delete();
//        }  if (file4!=null&&file4.exists()){
//            file4.delete();
//        }


    }
    private void delete(){
        if (file4!=null){
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                String substring = s.substring(s.length() - 5, s.length() - 4);
                if (substring.equals("4")){
                    list.remove(i);
                }
            }

        }
        if (file3!=null){
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                String substring = s.substring(s.length() - 5, s.length() - 4);
                if (substring.equals("4")){
                    list.remove(i);
                }
            }
        }
        if (file2!=null){
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                String substring = s.substring(s.length() - 5, s.length() - 4);
                if (substring.equals("4")){
                    list.remove(i);
                }
            }
        }
        if (file1!=null){
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                String substring = s.substring(s.length() - 5, s.length() - 4);
                if (substring.equals("4")){
                    list.remove(i);
                }
            }
        }
    }
}
