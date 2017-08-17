package com.bibinet.biunion.project.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.mvp.presenter.CompanyInfoPresenter;
import com.bibinet.biunion.mvp.view.CompanyInfoView;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.application.Configs;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.CompanyUpImageBean;
import com.bibinet.biunion.project.bean.LoginResultBean;
import com.bibinet.biunion.project.bean.UpLoadDataResultBean;
import com.bibinet.biunion.project.builder.MyCallBack;
import com.bibinet.biunion.project.ui.dialog.ShowCompanyInfoSelectDialog;
import com.bibinet.biunion.project.utils.ConvertUtils;
import com.bibinet.biunion.project.utils.DialogUtils;
import com.bibinet.biunion.project.utils.ImageUtils;
import com.bibinet.biunion.project.utils.PhoneNumberUtils;
import com.bibinet.biunion.project.utils.SharedPresUtils;
import com.bibinet.biunion.project.utils.SoftKeyboardUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-3.
 */

public class CompanyInfoActivity extends BaseActivity implements CompanyInfoView, View.OnClickListener {

    private static final String PHOTO_COMPANYPIC = "businesspic.jpg";//营业执照
    private static final int REQUESTCODE_PICK = 1;//图库
    private static final int PHOTO_REQUEST_CAMERA = 3;//相机
    private static final int REQUESTCODE_CUTTING = 2;
    private static final String PHOTO_BUSINESS = "business.jpg";//营业执照
    private final String imagePath = Configs.cachePathImage + "/" + PHOTO_BUSINESS;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_imageright)
    ImageView titleImageright;
    @BindView(R.id.title_imageleft)
    ImageView titleImageleft;
    @BindView(R.id.inputCompanyName)
    EditText inputCompanyName;
    @BindView(R.id.inputCreditCode)
    EditText inputCreditCode;
    @BindView(R.id.inputLegalName)
    EditText inputLegalName;
    @BindView(R.id.inputLegalIdentityCode)
    EditText inputLegalIdentityCode;
    @BindView(R.id.inputIndustry)
    TextView inputIndustry;
    @BindView(R.id.inputArea)
    TextView inputArea;
    @BindView(R.id.inputDetailAddress)
    EditText inputDetailAddress;
    @BindView(R.id.inputContactPerson)
    EditText inputContactPerson;
    @BindView(R.id.inputPhoneNumber)
    EditText inputPhoneNumber;
    @BindView(R.id.act_company_info_business_add)
    ImageView businessImage;
    @BindView(R.id.saveTenderBook)
    Button saveTenderBook;
    private DialogUtils dialogUtils;
    private CompanyInfoPresenter companyInfoPresenter;
    private int thumbnailFileInfoId = -1;
    private int orignalId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title.setText("企业资料");
        titleImageleft.setVisibility(View.VISIBLE);
        companyInfoPresenter = new CompanyInfoPresenter(this);

        if (Constants.loginresultInfo != null && Constants.loginresultInfo.getUser()!=null && Constants.loginresultInfo.getUser().getEnterprise()!=null) {
            //初始化数据
            LoginResultBean.UserBean.EnterpriseBean bean = Constants.loginresultInfo.getUser().getEnterprise();
            //企业名称
            inputCompanyName.setText(Constants.loginresultInfo.getUser().getEnterprise().getEnterpriseName());
            //征信码
            inputCreditCode.setText(Constants.loginresultInfo.getUser().getEnterprise().getUSCCode());
            //法人姓名
            inputLegalName.setText(bean.getBusinessLicenseName());
            //法人身份证号
            inputLegalIdentityCode.setText(bean.getBusinessLicenseCardNo());
            //所属行业
            inputIndustry.setText(bean.getIndustry());
            if(!bean.getIndustry().equals("")){
                inputIndustry.setTextColor(Color.parseColor("#333333"));
            }
            //地址 弹框选择 禁止默认添加
            inputArea.setText(bean.getRegion());
            if(!bean.getRegion().equals("")){
                inputArea.setTextColor(Color.parseColor("#333333"));
            }
            //详细地址
            inputDetailAddress.setText(Constants.loginresultInfo.getUser().getEnterprise().getAddr());
            //联系人
            inputContactPerson.setText(Constants.loginresultInfo.getUser().getEnterprise().getContactName());
            //联系电话
            inputPhoneNumber.setText(Constants.loginresultInfo.getUser().getEnterprise().getContactCellphone());
            //营业执照
            String URL = Constants.loginresultInfo.getUser().getEnterprise().getTradingCertificateURL();
            if(!URL.equals("")){
                ImageOptions imageOptions = new ImageOptions.Builder().setFailureDrawableId(R.mipmap.img_company_info_default_add).setImageScaleType(ImageView.ScaleType.CENTER_CROP).build();
                x.image().bind(businessImage, URL, imageOptions, new Callback.CommonCallback<Drawable>() {
                    @Override
                    public void onSuccess(Drawable result) {
                        thumbnailFileInfoId = 0;
                        orignalId = 0;
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        } else {
            return;
        }
    }

    @OnClick({R.id.title_imageleft, R.id.act_company_info_business_add, R.id.saveTenderBook})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_imageleft:
                SoftKeyboardUtils.hintKbTwo(this);
                finish();
                break;
            case R.id.act_company_info_business_add:
                upLoadImage();
                break;
            case R.id.saveTenderBook:
                saveUserInfo();
                break;
        }
    }

    //上传用户资料
    private void saveUserInfo() {
        String companyName = inputCompanyName.getText().toString().trim();
        String creditCode = inputCreditCode.getText().toString().trim();
        String legalName = inputLegalName.getText().toString().trim();
        String leagalidentityCode = inputLegalIdentityCode.getText().toString().trim();
        String industry = inputIndustry.getText().toString().trim();
        String area = inputArea.getText().toString().trim();
        String detailAddress = inputDetailAddress.getText().toString().trim();
        String contactName = inputContactPerson.getText().toString().trim();
        String contactPhone = inputPhoneNumber.getText().toString().trim();
        if (Constants.loginresultInfo != null) {
            if (TextUtils.isEmpty(companyName) || TextUtils.isEmpty(creditCode) || TextUtils.isEmpty(legalName) || TextUtils.isEmpty(leagalidentityCode) || industry.startsWith("请选择")
                    || area.startsWith("请选择") || TextUtils.isEmpty(detailAddress) || TextUtils.isEmpty(contactName) || TextUtils.isEmpty(contactPhone)) {
                Toast.makeText(this, "请确保您要提交的内容不为空", Toast.LENGTH_SHORT).show();
            } else {
                //验证身份证
                if(!PhoneNumberUtils.isLegalId(leagalidentityCode)){
                    Toast.makeText(this, "身份证格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                //联系电话验证
                if(!PhoneNumberUtils.isMobileNumber(contactPhone)){
                    Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i("TAG", Constants.loginresultInfo.getUser().getEnterprise().getEnterpriseCode() + "企业吗");
                String in = new ConvertUtils().industryConvert(industry);
                int ar = new ConvertUtils().areaConvert(area);

                if(orignalId==-1 && thumbnailFileInfoId==1){
                    Toast.makeText(this, "您还未上传营业执照", Toast.LENGTH_SHORT).show();
                    return;
                }
                companyInfoPresenter.upLoadData(Constants.loginresultInfo.getUser().getEnterprise().getEnterpriseCode(),companyName,creditCode,legalName,leagalidentityCode,in,String.valueOf(ar),detailAddress,contactName,contactPhone,orignalId,thumbnailFileInfoId, Constants.loginresultInfo.getUser().getUserId());
            }
        } else {
            Toast.makeText(this, "您还没有登录呢", Toast.LENGTH_SHORT).show();
        }
    }

    private void upLoadImage() {
        dialogUtils = new DialogUtils();
        dialogUtils.diloagShow(this, R.layout.item_select_business_photo);
        View itemview = dialogUtils.getView();
        TextView camera = (TextView) itemview.findViewById(R.id.camera);
        TextView picstorage = (TextView) itemview.findViewById(R.id.picstorage);
        camera.setOnClickListener(this);
        picstorage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dialogUtils.dialogDismiss();
        switch (v.getId()) {
            case R.id.camera:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //摄像头权限
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 111);
                    } else {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File businesspic = new File(imagePath);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(businesspic));
                        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
                    }
                }
                dialogUtils.dialogDismiss();
                break;
            case R.id.picstorage:
                Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 图片的存储路径
                startActivityForResult(intent1, REQUESTCODE_PICK);
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.inputArea, R.id.inputIndustry})
    void showDialog(View v) {
        switch (v.getId()) {
            //行业选择
            case R.id.inputIndustry:
                new ShowCompanyInfoSelectDialog(this, 0, new ShowCompanyInfoSelectDialog.OnShowCompanyInfoSelectDialogListener() {
                    @Override
                    public void onSelect(String s) {
                        inputIndustry.setText(s);
                        inputIndustry.setTextColor(Color.parseColor("#333333"));
                    }
                }).show();
                break;
            //地区选择
            case R.id.inputArea:
                new ShowCompanyInfoSelectDialog(this, 1, new ShowCompanyInfoSelectDialog.OnShowCompanyInfoSelectDialogListener() {
                    @Override
                    public void onSelect(String s) {
                        inputArea.setText(s);
                        inputArea.setTextColor(Color.parseColor("#333333"));
                    }
                }).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //从图库选择
            case REQUESTCODE_PICK:
                if (data == null || data.getData() == null) {
                    return;
                }
                Uri uri = data.getData();
//                startPhotoZoom(uri);
                // 查询选择图片
                Cursor cursor = getContentResolver().query(uri,
                        new String[]{MediaStore.Images.Media.DATA}, null,
                        null, null);
                // 光标移动至开头 获取图片路径
                cursor.moveToFirst();
                String pathImage = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                Bitmap bitmap = BitmapFactory.decodeFile(pathImage);
                //保存图片到本地目录下
                ImageUtils.saveBitmap(bitmap, imagePath);
                businessImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                businessImage.setImageBitmap(bitmap);
                updateImage();
                break;
            //拍照
            case PHOTO_REQUEST_CAMERA:
                if(resultCode == Activity.RESULT_OK){
                    if (hasSdcard()) {
                        Bitmap b = BitmapFactory.decodeFile(imagePath);
                        businessImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        businessImage.setImageBitmap(b);
                        updateImage();
                    } else {
                        Toast.makeText(CompanyInfoActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            //裁剪图片
            case REQUESTCODE_CUTTING:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Log.i("TAG", "相片裁剪");
                        Bitmap photo = extras.getParcelable("data");
                        Log.i("TAG", photo.toString() + "phto");
                        businessImage.setImageBitmap(photo);
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateImage() {
        Bitmap b = getSmallBitmap();
        saveBitmap(b);
        companyInfoPresenter.upLoadPic(new File(imagePath));
    }

    public boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    // 开始裁剪相片
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置宽高比例
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 16);
        intent.putExtra("aspectY", 9);
        // 设置裁剪图片宽高
        intent.putExtra("outputX", 1600);
        intent.putExtra("outputY", 900);
        // 广播刷新相册
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }

    @Override
    public void onUpLoadDataSucess(LoginResultBean updataInfo, String loginString) {
        String resultCode = updataInfo.getResCode();
        switch (Integer.parseInt(resultCode)) {
            case 0000:
                Toast.makeText(this, "资料提交成功", Toast.LENGTH_SHORT).show();
                //刷新登录信息
                Constants.loginresultInfo = updataInfo;
                SharedPresUtils sharedPresUtils = SharedPresUtils.getsSharedPresUtils(this);
                sharedPresUtils.putString("loginResultData", loginString);
//                Log.e("e-x-", Constants.loginresultInfo.getUser().getEnterprise().getThumbnailFileInfoId()+"-"+Constants.loginresultInfo.getUser().getEnterprise().getOriginalFileInfoId());
                finish();
                break;
            case 9999:
                Toast.makeText(this, "系统繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
                break;
            case 1111:
                Toast.makeText(this, "数据异常，请稍后再试", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onUpLoadDataFailed(String s) {
        Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpCompanyPicViewSucess(CompanyUpImageBean upImageInfo) {
        switch (Integer.parseInt(upImageInfo.getResCode())) {
            case 0000:
                orignalId = upImageInfo.getOriginalFileInfoId();
                thumbnailFileInfoId = upImageInfo.getThumbnailFileInfoId();
                Log.e("x-x--", orignalId+"-"+thumbnailFileInfoId);
                Toast.makeText(this, "上传营业执照成功", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, upImageInfo.getResMessage(), Toast.LENGTH_SHORT).show();
//                thumbnailFileInfoId = 0;
//                orignalId = 0;
                break;
        }
        Log.e("x-x--", "=-=");
    }

    @Override
    public void onUpCompanyPicViewFailed(String s) {
        Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
//        thumbnailFileInfoId = 0;
//        orignalId = 0;
    }

    /**
     * @param
     * @return
     * @author 吴昊
     * @time 2017-5-4 14:57
     * 压缩图片
     */
    public Bitmap getSmallBitmap() {
        // 设置参数
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
        BitmapFactory.decodeFile(imagePath, options);
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
        Bitmap bm = BitmapFactory.decodeFile(imagePath, options); // 解码文件
//        LogUtils.e("TAG", "size: " + bm.getByteCount() + " width: " + bm.getWidth() + " heigth:" + bm.getHeight()); // 输出图像数据
        return bm;
    }


    /**
     * 保存方法
     */
    public void saveBitmap(Bitmap bitmap) {
        File f = new File(imagePath);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
