package com.bibinet.biunion.project.ui.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.BuildConfig;
import com.bibinet.biunion.R;
import com.bibinet.biunion.mvp.presenter.PrivatePersonDesinPresenter;
import com.bibinet.biunion.mvp.presenter.UpLoadUserPhotoPresenter;
import com.bibinet.biunion.mvp.view.PrivatePersonDesinView;
import com.bibinet.biunion.mvp.view.UpLoadUserPhotoView;
import com.bibinet.biunion.project.application.Configs;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.BaseBean;
import com.bibinet.biunion.project.bean.LoginResultBean;
import com.bibinet.biunion.project.bean.PrivatePersonDesinBean;
import com.bibinet.biunion.project.builder.MyCallBack;
import com.bibinet.biunion.project.ui.activity.AboutUsActivity;
import com.bibinet.biunion.project.ui.activity.CompanyInfoActivity;
import com.bibinet.biunion.project.ui.activity.FoucsMyActivity;
import com.bibinet.biunion.project.ui.activity.LegalStatementActivity;
import com.bibinet.biunion.project.ui.activity.LoginActivity;
import com.bibinet.biunion.project.ui.activity.PrivatePersonActivity;
import com.bibinet.biunion.project.ui.activity.RegistActivity;
import com.bibinet.biunion.project.ui.activity.SettingActivity;
import com.bibinet.biunion.project.ui.activity.TermOfServiceActivity;
import com.bibinet.biunion.project.ui.dialog.WaitDialog;
import com.bibinet.biunion.project.utils.DialogUtils;
import com.bibinet.biunion.project.utils.ImageUtils;
import com.bibinet.biunion.project.utils.SharedPresUtils;
import com.bibinet.biunion.project.utils.cityselectutil.Base64MapUtils;
import com.soundcloud.android.crop.Crop;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_My extends Fragment implements UpLoadUserPhotoView {

    private static final String PHOTO_USERPHOTO = "userphoto.jpg";//用户头像
    private static final int REQUESTCODE_PICK = 1;//图库
    private static final int PHOTO_REQUEST_CAMERA = 3;//相机
    private static final int REQUESTCODE_CUTTING = 2;
    private final String imagePath = Configs.cachePathImage + "/" + PHOTO_USERPHOTO;
    private final String imagePath1 = Configs.cachePathImage + "/" + "file" + PHOTO_USERPHOTO;
    @BindView(R.id.title_imageright)
    ImageView titleImageright;
    @BindView(R.id.title_imageleft)
    ImageView titleImageleft;
    @BindView(R.id.companyName)
    TextView companyName;
    //    @BindView(R.id.userName)
//    TextView userName;
    @BindView(R.id.companyInfo)
    LinearLayout companyInfo;
    @BindView(R.id.privateOdering)
    LinearLayout privateOdering;
    @BindView(R.id.foucsMy)
    LinearLayout foucsMy;
    @BindView(R.id.aboutOur)
    LinearLayout aboutOur;
    @BindView(R.id.serviceTerm)
    LinearLayout serviceTerm;
    @BindView(R.id.legalStatement)
    LinearLayout legalStatement;
    @BindView(R.id.setting)
    LinearLayout setting;
    Unbinder unbinder;
    @BindView(R.id.userPhoto_login)
    ImageView userPhotoLogin;
    @BindView(R.id.logined)
    RelativeLayout logined;
    @BindView(R.id.uerPhoto_noLogin)
    ImageView uerPhotoNoLogin;
    @BindView(R.id.loginBtn)
    Button loginBtn;
    @BindView(R.id.noLogin)
    RelativeLayout noLogin;
    @BindView(R.id.rigestBtn)
    Button rigestBtn;
    private View view;
    private DialogUtils dialogUtils;
    private UpLoadUserPhotoPresenter upLoadUserPhotoPresenter;
    private WaitDialog waitDialog;

    public Fragment_My() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        waitDialog = new WaitDialog(getActivity());
        return view;
    }

    private void initView() {
        upLoadUserPhotoPresenter = new UpLoadUserPhotoPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Constants.loginresultInfo == null) {
            logined.setVisibility(View.GONE);
            noLogin.setVisibility(View.VISIBLE);
        } else {
            logined.setVisibility(View.VISIBLE);
            noLogin.setVisibility(View.GONE);
            companyName.setText(Constants.loginresultInfo.getUser().getName());
            if (TextUtils.isEmpty(Constants.loginresultInfo.getUser().getImage())) {
                userPhotoLogin.setImageResource(R.mipmap.wode_toux);
            } else {
                ImageUtils.generateImage(Constants.loginresultInfo.getUser().getImage(), imagePath);
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                try {
                    Bitmap b = ImageUtils.getRoundBitmap(bitmap, bitmap.getWidth());
                    userPhotoLogin.setImageBitmap(b);
                } catch (Exception e) {

                }
            }
        }
    }

    private boolean checkLogin() {
        if (Constants.loginresultInfo != null && Constants.loginresultInfo.getUser() != null && Constants.loginresultInfo.getUser().getUserId() != null && !Constants.loginresultInfo.getUser().getUserId().equals("")) {
            return true;
        }
        return false;
    }

    @OnClick({R.id.companyInfo, R.id.privateOdering, R.id.foucsMy, R.id.aboutOur, R.id.rigestBtn, R.id.serviceTerm, R.id.legalStatement, R.id.setting, R.id.userPhoto_login, R.id.logined, R.id.loginBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.userPhoto_login:
                selectPhoto();
                break;
            case R.id.logined:
                break;
            case R.id.loginBtn:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.companyInfo:
                if (!checkLogin()) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), CompanyInfoActivity.class));
                break;
            case R.id.privateOdering:
                if (!checkLogin()) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), PrivatePersonActivity.class));
                break;
            case R.id.foucsMy:
                if (!checkLogin()) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), FoucsMyActivity.class));
                break;
            case R.id.aboutOur:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                return;
            case R.id.rigestBtn:
                startActivity(new Intent(getActivity(), RegistActivity.class));
                break;
            case R.id.serviceTerm:
                startActivity(new Intent(getActivity(), TermOfServiceActivity.class));
                break;
            case R.id.legalStatement:
                startActivity(new Intent(getActivity(), LegalStatementActivity.class));
                break;
            case R.id.setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }

    }

    private void selectPhoto() {
        dialogUtils = new DialogUtils();
        dialogUtils.diloagShow(getActivity(), R.layout.item_selectphoto);
        View dialogView = dialogUtils.getView();
        TextView camera = (TextView) dialogView.findViewById(R.id.camera);
        TextView picstorage = (TextView) dialogView.findViewById(R.id.picstorage);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFromCamera();
                dialogUtils.dialogDismiss();
            }
        });
        picstorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFromGallay();
                dialogUtils.dialogDismiss();
            }
        });
    }

    private void selectFromGallay() {
        Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 图片的存储路径
        getActivity().startActivityForResult(intent1, REQUESTCODE_PICK);
    }

    private void selectFromCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //摄像头权限
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 111);
            } else {
                startCream();
            }
        } else {
            startCream();
        }
        dialogUtils.dialogDismiss();
    }

    public boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Log.e("sdfasdfasdf", "asdfasdfxxadfasdfsdf");
            switch (requestCode) {
                //从图库选择
                case REQUESTCODE_PICK:
                    if (data == null || data.getData() == null) {
                        return;
                    }
                    Uri uri = data.getData();
                    // 查询选择图片
                    Cursor cursor = getActivity().getContentResolver().query(uri,
                            new String[]{MediaStore.Images.Media.DATA}, null,
                            null, null);
                    // 光标移动至开头 获取图片路径
                    cursor.moveToFirst();
                    String pathImage = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    new Crop(Uri.fromFile(new File(pathImage))).output(Uri.fromFile(new File(imagePath1))).asSquare().start(getActivity());
                    break;
                //拍照
                case PHOTO_REQUEST_CAMERA:
                    Uri outputUri = Uri.fromFile(new File(imagePath1));
                    new Crop(Uri.fromFile(new File(imagePath1))).output(outputUri).asSquare().start(getActivity());
                    break;
                //裁剪图片
                case Crop.REQUEST_CROP:
                    waitDialog.open();
                    Bitmap bitmap = getSmallBitmap(imagePath1);
                    ImageUtils.saveBitmap(bitmap, imagePath1);
//                    //转码
                    String fileCode = ImageUtils.getImageStr(imagePath1);
                    upLoadUserPhotoPresenter.upLoadUserPhoto(Constants.loginresultInfo.getUser().getUserId(), Constants.loginresultInfo.getUser().getEnterprise().getEnterpriseCode(), fileCode);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * @param
     * @return
     * @author 吴昊
     * @time 2017-5-4 14:57
     * 压缩图片
     */
    public Bitmap getSmallBitmap(String path) {
        // 设置参数
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
        BitmapFactory.decodeFile(path, options);
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 2; // 默认像素压缩比例，压缩为原图的1/2
        int minLen = Math.min(height, width); // 原图的最小边长
        if (minLen > 100) { // 如果原始图像的最小边长大于100dp（此处单位我认为是dp，而非px）
            float ratio = (float) minLen / 100.0f; // 计算像素压缩比例
            inSampleSize = (int) ratio;
        }
        options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
        options.inSampleSize = inSampleSize; // 设置为刚才计算的压缩比例
        Bitmap bm = BitmapFactory.decodeFile(path, options); // 解码文件
//        LogUtils.e("TAG", "size: " + bm.getByteCount() + " width: " + bm.getWidth() + " heigth:" + bm.getHeight()); // 输出图像数据
        return bm;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick()
    public void onViewClicked() {
    }


    //头像上传
    @Override
    public void onUpLoadPhotoSucess(LoginResultBean loginInfo, String loginString) {
        waitDialog.close();
        if (loginInfo.getResCode().equals("0000")) {
            Constants.loginresultInfo = loginInfo;
            SharedPresUtils sharedPresUtils = SharedPresUtils.getsSharedPresUtils(getActivity());
            sharedPresUtils.putString("loginResultData", loginString);
            //更新头像
            ImageUtils.generateImage(Constants.loginresultInfo.getUser().getImage(), imagePath);
            Bitmap photo = BitmapFactory.decodeFile(imagePath);
            Bitmap p = ImageUtils.getRoundBitmap(photo, photo.getWidth());
            userPhotoLogin.setImageBitmap(p);
        } else {
            Toast.makeText(getActivity(), loginInfo.getResMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpLoadPhotoFailed(String s) {
        waitDialog.close();
        Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 111:
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "摄像头权限开启失败", Toast.LENGTH_SHORT).show();
                } else {
                    startCream();
                }
                break;
            default:
                break;
        }
    }

    private void startCream() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File businesspic = new File(imagePath1);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileProvider", businesspic);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            getActivity().startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
        }else{
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(businesspic));
            getActivity().startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
        }
    }

}
