package com.gdyd.qmwallet.mine;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gdyd.qmwallet.BaseFragment;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.Other.presenter.PhotoPresenter;
import com.gdyd.qmwallet.Other.view.IPhotoView;
import com.gdyd.qmwallet.Other.view.LoginActivity;
import com.gdyd.qmwallet.Other.view.ModifyPwdActivity;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.mine.model.BlankBean;
import com.gdyd.qmwallet.mine.model.BranchBankBean;
import com.gdyd.qmwallet.mine.model.PhotoBean;
import com.gdyd.qmwallet.mine.model.RateBean;
import com.gdyd.qmwallet.mine.presenter.BankPresenter;
import com.gdyd.qmwallet.mine.presenter.CheckPresenter;
import com.gdyd.qmwallet.mine.view.IBankInfoView;
import com.gdyd.qmwallet.mine.view.ICheckUpdateView;
import com.gdyd.qmwallet.myview.XCRoundRectImageView;
import com.gdyd.qmwallet.share.RWebActivity;
import com.gdyd.qmwallet.utils.AppUtils;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.IntentUtils;
import com.gdyd.qmwallet.utils.ReadImgToBinary2;
import com.gdyd.qmwallet.utils.SharePreUtil;
import com.gdyd.qmwallet.utils.Tool;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.jpush.android.api.JPushInterface;

import static android.app.Activity.RESULT_OK;

public class MineFragment extends BaseFragment implements View.OnClickListener, ICheckUpdateView, IBankInfoView, IPhotoView {
    private View view;
    private PercentRelativeLayout layout_user;
    private RelativeLayout layout_approve;
    private RelativeLayout layout_main_pwd;
    private RelativeLayout layout_direct_shareholder;
    private TextView sh_state;
    private int checkState = -1;
    //  private LoginInfoBean bean;
    private TextView my_name;
    private TextView login_type;
    private XCRoundRectImageView my_portrait;
    private LoginInfoBean.UserData.MerchantBean merchant;
    private Button login_exit;
    private RelativeLayout layout_main_service;
    private CheckPresenter presenter = new CheckPresenter(this);
    private BankPresenter bankPresenter = new BankPresenter(this);
    private PhotoPresenter photoPresenter = new PhotoPresenter(this);
    private RelativeLayout layout_main_chack;
    private TextView dj;
    private TextView text_tjr_info;
    private TextView text_shareholder_info;//直属股东
    private RelativeLayout layout_main_tjr;
    private PullToRefreshScrollView pull_to_refresh_scr;
    private TextView app_version;
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
    private String lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
    private String phoneNumber;
    private RelativeLayout layout_main_rate;
    private static final int REQUEST_CODE = 100;
    private File file;
    private Bitmap bitmap;
    private static final int CODE_GALLERY_REQUEST = 200;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fm_main_mine, container, false);
        }
        initView();
        //    initData();
        if (bean != null && bean.getUserData() != null && bean.getUserData().getMerchant() != null) {
            String headImage = bean.getUserData().getMerchant().getHeadImage();
            if (headImage == null || headImage.trim().equals("")) {
                my_portrait.setImageDrawable(getResources().getDrawable(R.drawable.icon_tourist));
            } else {
                Picasso.with(getActivity()).load(UrlConfig.PHOTO_URI + headImage).resize(400, 400).centerCrop().into(my_portrait);
            }
        }
        return view;
    }

    private void initView() {
        bean = ((LoginInfoBean) getActivity().getIntent().getSerializableExtra(APPConfig.LOGIN_INFO));
        my_name = ((TextView) view.findViewById(R.id.my_name));
        login_type = ((TextView) view.findViewById(R.id.login_type));
        text_tjr_info = ((TextView) view.findViewById(R.id.text_tjr_info));
        text_shareholder_info = ((TextView) view.findViewById(R.id.text_shareholder_info));
        view.findViewById(R.id.layout_main_about).setOnClickListener(this);
        view.findViewById(R.id.layout_main_help).setOnClickListener(this);
        pull_to_refresh_scr = ((PullToRefreshScrollView) view.findViewById(R.id.pull_to_refresh_scr));
        pull_to_refresh_scr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);// 两端刷新
        app_version = ((TextView) view.findViewById(R.id.app_version));

//        pull_to_refresh_scr.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("下拉刷新");
//        pull_to_refresh_scr.getLoadingLayoutProxy(false, true).setLastUpdatedLabel("松开加载更多");
        initPull();
        pull_to_refresh_scr.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
                initPull();
                if (bean != null) {
                    bankPresenter.getUserInfo(bean.getUserData().getMerchant().getMerchantNo());
                } else {
                    pull_to_refresh_scr.onRefreshComplete();
                }

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

            }
        });
        my_portrait = ((XCRoundRectImageView) view.findViewById(R.id.my_portrait));
        my_portrait.setOnClickListener(this);
        dj = ((TextView) view.findViewById(R.id.my_name_type));
        layout_main_tjr = ((RelativeLayout) view.findViewById(R.id.layout_main_tjr));
        layout_main_tjr.setOnClickListener(this);
        layout_main_chack = ((RelativeLayout) view.findViewById(R.id.layout_main_chack));

        layout_user = ((PercentRelativeLayout) view.findViewById(R.id.layout_main_user));

        layout_main_pwd = ((RelativeLayout) view.findViewById(R.id.layout_main_pwd));
        sh_state = ((TextView) view.findViewById(R.id.sh_state));
        layout_main_rate = ((RelativeLayout) view.findViewById(R.id.layout_main_rate));
        ((RelativeLayout) view.findViewById(R.id.layout_main_help2)).setOnClickListener(this);
        layout_main_rate.setOnClickListener(this);
        layout_main_pwd.setOnClickListener(this);

        layout_user.setOnClickListener(this);
        layout_main_chack.setOnClickListener(this);
        layout_approve = ((RelativeLayout) view.findViewById(R.id.layout_main_approve));
        layout_approve.setOnClickListener(this);
        login_exit = ((Button) view.findViewById(R.id.login_exit));
        login_exit.setOnClickListener(this);
        layout_main_service = ((RelativeLayout) view.findViewById(R.id.layout_main_service));
        layout_main_service.setOnClickListener(this);

        layout_direct_shareholder = ((RelativeLayout) view.findViewById(R.id.layout_direct_shareholder));
        layout_direct_shareholder.setOnClickListener(this);

        ToggleButton mTogBtn = (ToggleButton) view.findViewById(R.id.mTogBtn); // 获取到控件
        SharedPreferences shared = getActivity().getSharedPreferences("main",
                Activity.MODE_PRIVATE);
        boolean yuyin = shared.getBoolean("yuyin", false);
        if (yuyin) {
            mTogBtn.setChecked(true);
        } else {
            mTogBtn.setChecked(false);
        }
        mTogBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                SharedPreferences shared = getActivity().getSharedPreferences("main",
                        Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                if (isChecked) {
                    //选中
                    editor.putBoolean("yuyin", true);
                    //  Toast.makeText(getActivity(), "语音播报已打开", Toast.LENGTH_SHORT).show();
                } else {
                    //未选中
                    editor.putBoolean("yuyin", false);
                    //   Toast.makeText(getActivity(), "语音播报已关闭", Toast.LENGTH_SHORT).show();
                }
                editor.commit();
            }
        });// 添加监听事件

    }

    private void initPull() {
        ILoadingLayout layoutProxy = pull_to_refresh_scr.getLoadingLayoutProxy(true, false);
        layoutProxy.setLastUpdatedLabel("上次刷新时间:" + lastUpdateTime);
        layoutProxy.setPullLabel("下拉刷新");
        layoutProxy.setRefreshingLabel("正在加载数据");
        layoutProxy.setReleaseLabel("手指释放刷新数据");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_portrait:
                if (bean == null) {
                    checkBean();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
                builder.setTitle("选择方式");
                builder.setItems(R.array.photo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 1:
                                // 选择
                                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE);
                                    }
                                } else {
                                    Intent intent = new Intent(Intent.ACTION_PICK,
                                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//调用android的图库
                                    intent.setType("image/*");

//                                    file = new File(Environment.getExternalStorageDirectory(), R.drawable.back+ "head2.jpg");
//                                    Uri imageUri;
//                                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
//                                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
//                                        imageUri = FileProvider.getUriForFile(getActivity(), "com.gdyd.qmwallet.fileprovider", file);
//                                    }else {
//                                        imageUri=Uri.fromFile(file);
//                                    }
//
//                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                    startActivityForResult(intent, CODE_GALLERY_REQUEST);

                                }
                                break;

                            case 0: // 照相
                                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                                                REQUEST_CODE);
                                    }
                                } else {
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    file = new File(Environment.getExternalStorageDirectory(), R.drawable.back + "head.jpg");

                                    Uri imageUri;
                                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                                        imageUri = FileProvider.getUriForFile(getActivity(), "com.gdyd.qmwallet.fileprovider", file);
                                    } else {
                                        imageUri = Uri.fromFile(file);
                                    }

                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                    startActivityForResult(intent, REQUEST_CODE);

                                }
                                break;
                        }
                    }
                });

                builder.show();


                break;
            case R.id.layout_main_tjr:
                // 拨打电话
                if (bean == null) {
                    checkBean();
                    return;
                }
                String s = text_tjr_info.getText().toString();
                if (s == null || s.equals("") || s.equals("未有推荐人")) {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
                builder2.setTitle("提示");
                //builder.setIcon(R.drawable.tg2);
                builder2.setMessage("\t\t你要拨打联系人电话吗？");
                builder2.setPositiveButton("马上拨打", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 112);
                            }
                        } else {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            Uri data = Uri.parse("tel:" + phoneNumber);
                            intent.setData(data);
                            startActivity(intent);
                        }
                        //      context.startService(new Intent(context, AppService.class).putExtra("UpdateUrl",UpdateUrl));
                    }
                });
                builder2.setNegativeButton("取消", null);
                builder2.show();
                break;


            case R.id.layout_direct_shareholder:
                if (bean == null) {
                    checkBean();
                    return;
                }
                startActivity(new Intent(getActivity(), DirectShareHolderActivity.class).putExtra(APPConfig.LOGIN_INFO, bean));
                break;


            case R.id.layout_main_approve:
                if (bean == null) {
                    checkBean();
                    return;
                }
                startActivity(new Intent(getActivity(), UserInfoActivity.class).putExtra(APPConfig.LOGIN_INFO, bean));
                break;
            case R.id.layout_main_user:
                if (my_name.getContentDescription().equals(getResources().getString(R.string.tip_login_type))) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                } else {
                    //  Toast.makeText(getActivity(), "个人中心正在开发中。敬请期待", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.layout_main_pwd:
                if (bean == null) {
                    checkBean();
                    return;
                }
                startActivity(new Intent(getActivity(), ModifyPwdActivity.class).putExtra("phone", bean.getUserData().getMerchant().getPhoneNumber()));
                break;
            case R.id.layout_main_rate:
                if (bean == null) {
                    checkBean();
                    return;
                } else {
                    if (bean.getUserData().getMerchant().getCheckState() != 2) {
                        checkSMRZBean();
                        return;
                    }
                }

                startActivity(new Intent(getActivity(), RateActivity.class).putExtra(APPConfig.MERCHANTNO, bean.getUserData().getMerchant().getMerchantNo()));
                break;
            case R.id.layout_main_help2:
                if (bean == null) {
                    checkBean();
                    return;
                } else {
                    if (bean.getUserData().getMerchant().getCheckState() != 2) {
                        checkSMRZBean();
                        return;
                    }
                }
                startActivity(new Intent(getActivity(), CardManageActivity.class)
                        .putExtra(APPConfig.LOGIN_INFO, bean)
                        .putExtra(APPConfig.LEVEL, 1));
                break;
            case R.id.layout_main_help:
                startActivity(new Intent(getActivity(), RWebActivity.class).
                        putExtra("url", UrlConfig.help).
                        putExtra(APPConfig.TITLE, getResources().getString(R.string.tip_my_help))
                        .putExtra("type", 3));
                //   Toast.makeText(getActivity(), "使用帮助正在开发中", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_main_about:
                startActivity(new Intent(getActivity(), RWebActivity.class).
                        putExtra("url", UrlConfig.about).
                        putExtra(APPConfig.TITLE, "关于我们")
                        .putExtra("type", 3));
                //    Toast.makeText(getActivity(), "关于我们正在开发中", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_main_service:
                AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
                builder3.setTitle("联系客服");
                builder3.setItems(R.array.lianxi_type, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // 拨打电话
                                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 111);
                                    }
                                } else {
                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    Uri data = Uri.parse("tel:" + getResources().getString(R.string.tip_my_service_tel));
                                    intent.setData(data);
                                    startActivity(intent);
                                }
                                break;

                            case 1:
                                try {
                                    final String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=1619482101&version=1";
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
                                } catch (Exception e) {
                                    // 未安装手Q或安装的版本不支持
                                    Toast.makeText(getActivity(), "未安装QQ或安装的版本不支持", Toast.LENGTH_SHORT).show();
                                }
                                break;
//                            case 2:
//                                Intent intent = new Intent(Intent.ACTION_VIEW);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                intent.setDataAndType(Uri.withAppendedPath(
//                                        ContactsContract.Data.CONTENT_URI, String.valueOf(123)),
//                                        WEIXIN_SNS_MIMETYPE);
//                                getActivity().startActivity(intent);
//                                break;
                        }
                    }
                });
//                builder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        // 拨打电话
//                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 111);
//                            }
//
//                            }else{
//                            Intent intent = new Intent(Intent.ACTION_CALL);
//                            Uri data = Uri.parse("tel:" + getResources().getString(R.string.tip_my_service_tel));
//                            intent.setData(data);
//                            startActivity(intent);
//                            final String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=1154403058&version=1";
//                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
//                        }
//
//
//
//
//                    }
//                });
//                builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
                builder3.show();

                break;
            case R.id.login_exit:
                JPushInterface.setAlias(getActivity(), null, null);
                startActivity(new Intent(getActivity(), LoginActivity.class));
                SharedPreferences shared = getActivity().getSharedPreferences("main",
                        Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                boolean isRemember = shared.getBoolean("isRemember", false);
                if (!isRemember) {
                    editor.remove("pwd");
                }
                editor.remove("mchtNo");
                editor.remove("mchtName");
                editor.remove("ID");
                editor.commit();
                SharePreUtil.saveStringData(getActivity(), "token", "");
                getActivity().finish();
                break;
            case R.id.layout_main_chack:
                long transTyp = EncryptionHelper.getDate();
                String transTy = "1022" + transTyp + "";
                String transKe = EncryptionHelper.md5(transTy);
                presenter.getCheckUpdate(new PhotoBean("1022", transKe, 2, AppUtils.getVersionName(getActivity()), transTyp));
                //  presenter.getCheckUpdate(new PhotoBean("1022","56F9FC48252A2ECFAD13B5E964B63D32",2, AppUtils.getVersionName(getActivity())));
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 111) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + getResources().getString(R.string.tip_my_service_tel));
                intent.setData(data);
                startActivity(intent);
            } else {
                // Permission Denied
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 112) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + phoneNumber);
                intent.setData(data);
                startActivity(intent);
            } else {
                // Permission Denied
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = new File(Environment.getExternalStorageDirectory(), R.drawable.back + "head.jpg");
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
                    imageUri = FileProvider.getUriForFile(getActivity(), "com.gdyd.qmwallet.fileprovider", file);
                } else {
                    imageUri = Uri.fromFile(file);
                }
                //通过FileProvider创建一个content类型的Uri
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, REQUEST_CODE);
                //    }

            }
        } else if (requestCode == CODE_GALLERY_REQUEST) {
            Intent pickIntent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(pickIntent, CODE_GALLERY_REQUEST);
        }

    }

    @Override
    public void CheckUpdateBack(String a) {
        Log.d("zanZQ", "CheckUpdateBack: " + a);
        if (a == null || a.equals("")) {
            return;
        }
        try {
            JSONObject object = new JSONObject(a);
            int nResul = object.getInt("nResul");
            if (nResul > 1) {
                JSONObject data = new JSONObject(object.getString("Data"));
                IntentUtils.tipAppUpdate(getActivity(), data);
            } else {
                Toast.makeText(getActivity(), object.getString("sMessage"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getRateInfo(RateBean rateBean) {

    }

    @Override
    public void ISumBankInfoView(BlankBean blankBean) {

    }

    @Override
    public void IBranchBankInfoView(BranchBankBean list) {

    }

    @Override
    public void ISubmitInfoBack(String backInfo) {

    }

    @Override
    public void IUserInfoView(LoginInfoBean merchantBean) {
        Log.d("zanZQ", "IUserInfoView: 刷新");
        pull_to_refresh_scr.onRefreshComplete();
        if (merchantBean == null || merchantBean.getNResul() != 1 || getActivity() ==null) {
            return;
        }
        LoginInfoBean.UserData.MerchantBean merchant = merchantBean.getUserData().getMerchant();
        String name = merchant.getName();
        if (bean.getUserData().getMerchant().getMerchantNo().equals(merchant.getMerchantNo())) {
            bean = merchantBean;
            SharedPreferences shared = getActivity().getSharedPreferences("main",
                    Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("mchtName", merchantBean.getUserData().getMerchant().getName());
            editor.commit();
            initData();
            if (bean != null && bean.getUserData() != null && bean.getUserData().getMerchant() != null) {
                String headImage = bean.getUserData().getMerchant().getHeadImage();
                if (headImage == null || headImage.trim().equals("")) {
                    my_portrait.setImageDrawable(getResources().getDrawable(R.drawable.icon_tourist));
                } else {
                    Picasso.with(getActivity()).load(UrlConfig.PHOTO_URI + headImage).resize(400, 400).centerCrop().into(my_portrait);
                }
            }
            String shareHolderName = "";
            if (merchantBean != null && merchantBean.getUserData() != null && merchantBean.getUserData().getShareholderInfo() != null) {
                shareHolderName = merchantBean.getUserData().getShareholderInfo().getName();

            }

            if (shareHolderName != null && shareHolderName.length() > 0) {
                int length = shareHolderName.length();
                if (length == 2) {
                    shareHolderName = shareHolderName.substring(0, 1) + "*";
                } else if (length > 2) {
                    shareHolderName = shareHolderName.substring(0, 1) + Tool.getxing(length - 2) + shareHolderName.substring(length - 1, length);
                }
            } else {
                shareHolderName = "";
            }
            try {
                String shareHolderPhone = merchantBean.getUserData().getShareholderInfo().getPhoneNumber();
                int j = shareHolderPhone.length();
                if (j > 10) {
                    String phone1 = phoneNumber.substring(0, 3) + Tool.getxing(j - 7) + phoneNumber.substring(j - 4, j);
                    text_shareholder_info.setText(shareHolderName + "\t\t" + phone1);
                }
            } catch (Exception E) {

            }


            return;
        }

        if (name != null && name.length() > 0) {
            int length = name.length();
            if (length == 2) {
                name = name.substring(0, 1) + "*";
            } else if (length > 2) {
                name = name.substring(0, 1) + Tool.getxing(length - 2) + name.substring(length - 1, length);
            }
        } else {
            name = "";
        }

        phoneNumber = merchant.getPhoneNumber();

        int i = phoneNumber.length();
        String phone = phoneNumber.substring(0, 3) + Tool.getxing(i - 7) + phoneNumber.substring(i - 4, i);
        text_tjr_info.setText(name + "\t\t" + phone);

        if (bean.getUserData().getShareholderInfo() == null) {

            bankPresenter.getUserInfo(bean.getUserData().getMerchant().getMerchantNo());
        } else {
            String shareHolderName = "";

            if (bean != null && bean.getUserData() != null && bean.getUserData().getShareholderInfo() != null) {
                shareHolderName = bean.getUserData().getShareholderInfo().getName();
            }
            if (shareHolderName != null && shareHolderName.length() > 0) {
                int length = shareHolderName.length();
                if (length == 2) {
                    shareHolderName = shareHolderName.substring(0, 1) + "*";
                } else if (length > 2) {
                    shareHolderName = shareHolderName.substring(0, 1) + Tool.getxing(length - 2) + shareHolderName.substring(length - 1, length);
                }
            } else {
                shareHolderName = "";
            }
            try {
                String shareHolderPhone = bean.getUserData().getShareholderInfo().getPhoneNumber();
                int j = shareHolderPhone.length();
                if (j > 10) {
                    String phone1 = shareHolderPhone.substring(0, 3) + Tool.getxing(j - 7) + shareHolderPhone.substring(j - 4, j);
                    text_shareholder_info.setText(shareHolderName + "\t\t" + phone1);
                }
            } catch (Exception e){

            }

        }


    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        app_version.setText("当前版本: " + AppUtils.getVersionName(getActivity()));
        if (bean != null) {
            checkState = bean.getUserData().getMerchant().getCheckState();
            merchant = bean.getUserData().getMerchant();
            String recommendNo = merchant.getRecommendNo();
            // recommendNo="QMC0000005637";
            if (recommendNo == null || recommendNo.equals("")) {
                text_tjr_info.setText("未有推荐人");
            } else {
                bankPresenter.getUserInfo(recommendNo);
            }

            String name = merchant.getName();

            if (name == null || name.trim().equals("")) {
                my_name.setText("游客");
            } else {
                my_name.setText(name);
            }
            login_type.setText(bean.getUserData().getMerchant().getPhoneNumber());
            my_name.setContentDescription(getResources().getString(R.string.tip_login_hou));
            String[] stringArray = getResources().getStringArray(R.array.name);
            int levelValue = merchant.getLevelValue();
//            if (levelValue>1){
            dj.setText("等级:" + stringArray[levelValue - 1]);
//            }else {
//                dj.setText("等级:普通会员");
//            }

        }
        if (checkState == 1) {
            sh_state.setText("审核中");
            sh_state.setTextColor(getResources().getColor(R.color.red));
        } else if (checkState == 2) {
            sh_state.setText("审核通过");
            sh_state.setTextColor(getResources().getColor(R.color.green));
        } else if (checkState == 3 || checkState == 5) {
            sh_state.setText("审核失败");
            sh_state.setTextColor(getResources().getColor(R.color.red));
        } else if (checkState == 0) {
            sh_state.setText("未提交");
            sh_state.setTextColor(getResources().getColor(R.color.red));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            //  String stringExtra = data.getStringExtra(MediaStore.EXTRA_OUTPUT);
            //  bitmap1 = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (file == null) {
                file = new File(Environment.getExternalStorageDirectory(), R.drawable.back + "head.jpg");
                try {
                    boolean newFile = file.createNewFile();
                    if (newFile) {
                        String path = file.getAbsolutePath().toString();
                        bitmap = ReadImgToBinary2.getBitmap(path, my_portrait.getWidth() * 2, my_portrait.getWidth() * 2);
                        my_portrait.setImageBitmap(bitmap);
                        my_portrait.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        long transTyp = EncryptionHelper.getDate();
                        String transTy = "1063" + transTyp;
                        String transKe = EncryptionHelper.md5(transTy);
                        photoPresenter.SubmitHeandPhoto(new PhotoBean("1063", transKe, merchant.getMerchantNo(), transTyp), ReadImgToBinary2.imgToBase64(file.getAbsolutePath(), my_portrait.getWidth(), my_portrait.getWidth(), bitmap, null));

                        //   photoPresenter.SubmitHeandPhoto(new PhotoBean("1063","C8190D04CBBC7F544131FC5CA3672AA5",merchant.getMerchantNo()),ReadImgToBinary2.imgToBase64(file.getAbsolutePath(),my_portrait.getWidth()*2,my_portrait.getHeight()*2, bitmap, null));

                    } else {
                        Toast.makeText(getActivity(), "保存失败！请重新拍照", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return;
            } else {
                String path = file.getAbsolutePath().toString();
                // Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                bitmap = ReadImgToBinary2.getBitmap(path, my_portrait.getWidth() * 2, my_portrait.getHeight() * 2);
                my_portrait.setImageBitmap(bitmap);
                my_portrait.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                long transTyp = EncryptionHelper.getDate();
                String transTy = "1063" + transTyp;
                String transKe = EncryptionHelper.md5(transTy);
                photoPresenter.SubmitHeandPhoto(new PhotoBean("1063", transKe, merchant.getMerchantNo(), transTyp), ReadImgToBinary2.imgToBase64(file.getAbsolutePath(), my_portrait.getWidth(), my_portrait.getWidth(), bitmap, null));

                //      photoPresenter.SubmitHeandPhoto(new PhotoBean("1063","C8190D04CBBC7F544131FC5CA3672AA5",merchant.getMerchantNo()),ReadImgToBinary2.imgToBase64(file.getAbsolutePath(),my_portrait.getWidth()*2,my_portrait.getWidth()*2, bitmap, null));

            }

        } else if (requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            try {
                Uri selectedImage = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = getContext().getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String imagePath = c.getString(columnIndex);
                bitmap = ReadImgToBinary2.getBitmap(imagePath, my_portrait.getWidth() * 2, my_portrait.getWidth() * 2);
                my_portrait.setImageBitmap(bitmap);
                my_portrait.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                long transTyp = EncryptionHelper.getDate();
                String transTy = "1063" + transTyp;
                String transKe = EncryptionHelper.md5(transTy);
                photoPresenter.SubmitHeandPhoto(new PhotoBean("1063", transKe, merchant.getMerchantNo(), transTyp), ReadImgToBinary2.imgToBase64(imagePath, my_portrait.getWidth(), my_portrait.getWidth(), bitmap, null));

                // photoPresenter.SubmitHeandPhoto(new PhotoBean("1063","C8190D04CBBC7F544131FC5CA3672AA5",merchant.getMerchantNo()),ReadImgToBinary2.imgToBase64(imagePath,my_portrait.getWidth()*2,my_portrait.getWidth()*2, bitmap, null));

                c.close();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    String path = data.getData().getPath();
                    bitmap = ReadImgToBinary2.getBitmap(path, my_portrait.getWidth() * 2, my_portrait.getWidth() * 2);
                    long transTyp = EncryptionHelper.getDate();
                    String transTy = "1063" + transTyp;
                    String transKe = EncryptionHelper.md5(transTy);
                    photoPresenter.SubmitHeandPhoto(new PhotoBean("1063", transKe, merchant.getMerchantNo(), transTyp), ReadImgToBinary2.imgToBase64(path, my_portrait.getWidth(), my_portrait.getWidth(), bitmap, null));
                    my_portrait.setImageBitmap(bitmap);
                    my_portrait.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    Toast.makeText(getActivity(), "选择图片失败", Toast.LENGTH_SHORT).show();
                }

            }

        }

    }

    @Override
    public void UpPhoto(String info, int type) {

    }

    @Override
    public void UpPhotoPath(String info) {

    }

    @Override
    public void UpPhotoHead(String info) {
        Log.d("zanZQ", "UpPhotoHead: " + info);
        if (info != null) {
            try {
                JSONObject jsonObject = new JSONObject(info);
                int nResul = jsonObject.getInt("nResul");
                if (nResul == 1) {
                    Toast.makeText(getActivity(), jsonObject.getString("sMessage"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void checkUpdateNewNotice(String notice) {


    }
}


