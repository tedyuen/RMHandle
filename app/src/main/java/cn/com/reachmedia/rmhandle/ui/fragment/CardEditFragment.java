package cn.com.reachmedia.rmhandle.ui.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.app.Constant;
import cn.com.reachmedia.rmhandle.model.CardSubmitModel;
import cn.com.reachmedia.rmhandle.model.PicSubmitModel;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.model.param.CardSubmitParam;
import cn.com.reachmedia.rmhandle.model.param.PicSubmitParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.CardSubmitController;
import cn.com.reachmedia.rmhandle.network.controller.PicSubmitController;
import cn.com.reachmedia.rmhandle.service.ServiceHelper;
import cn.com.reachmedia.rmhandle.ui.CardListActivity;
import cn.com.reachmedia.rmhandle.ui.base.BaseToolbarFragment;
import cn.com.reachmedia.rmhandle.ui.view.ProportionImageView;
import cn.com.reachmedia.rmhandle.utils.ApartmentPointUtils;
import cn.com.reachmedia.rmhandle.utils.CropImageUtils;
import cn.com.reachmedia.rmhandle.utils.ImageUtils;
import cn.com.reachmedia.rmhandle.utils.PhotoSavePathUtil;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import cn.com.reachmedia.rmhandle.utils.ToastHelper;
import cn.com.reachmedia.rmhandle.utils.ViewHelper;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/12 下午1:33
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/12          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class CardEditFragment extends BaseToolbarFragment {

    @Bind(R.id.iv_gate_photo_1)
    ImageView ivGatePhoto1;
    @Bind(R.id.iv_gate_photo_2)
    ImageView ivGatePhoto2;
    @Bind(R.id.iv_pest_photo_1)
    ImageView ivPestPhoto1;
    @Bind(R.id.iv_pest_photo_2)
    ImageView ivPestPhoto2;
    ImageView[] gatePhotos;
    ImageView[] pestPhotos;
    ImageView[] allPhotos;
    @Bind(R.id.et_gate_card)
    EditText etGateCard;
    @Bind(R.id.et_password)
    EditText etPassword;
    private PointListModel model;

    public static CardEditFragment newInstance() {
        CardEditFragment fragment = new CardEditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public CardEditFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card_edit, container, false);
        ButterKnife.bind(this, rootView);
        gatePhotos = new ImageView[]{ivGatePhoto1,ivGatePhoto2};
        pestPhotos = new ImageView[]{ivPestPhoto1,ivPestPhoto2};
        allPhotos = new ImageView[]{ivGatePhoto1,ivGatePhoto2,ivPestPhoto1,ivPestPhoto2};
        needTitle();
        model = ApartmentPointUtils.getIns().pointListModel;
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (model != null) {
            if (!StringUtils.isEmpty(model.getCommunity())) {
                setTitle(model.getCommunity());
            }

            etGateCard.setText(model.getCarddesc());
            etPassword.setText(model.getDoordesc());

            setCardPhoto(model.getCGatePic(),model.getCPestPic());

            initPhoto();

        }
    }
    String[] preGate,prePest;
    private void setCardPhoto(String cGatePic,String cPestPic){
        preGate = cGatePic.split("@&");
        prePest = cPestPic.split("@&");

        for(int i=0;i<preGate.length;i++){
            if(i<gatePhotos.length){
                if(!StringUtils.isEmpty(preGate[i])){
                    Picasso.with(getContext()).load(preGate[i]).placeholder(R.drawable.abc).into(gatePhotos[i]);
                }
            }
        }
        for(int i=0;i<prePest.length;i++){
            if(i<pestPhotos.length){
                if(!StringUtils.isEmpty(prePest[i])){
                    Picasso.with(getContext()).load(prePest[i]).placeholder(R.drawable.abc).into(pestPhotos[i]);
                }
            }
        }

    }

    @OnClick({R.id.bt_edit_save_text_1,R.id.bt_edit_save_text_2})
    public void submitCard(){
        new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_title_submit_card)
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        CardSubmitController cardSubmitController = new CardSubmitController(new UiDisplayListener<CardSubmitModel>() {
                            @Override
                            public void onSuccessDisplay(CardSubmitModel data) {
                                closeProgressDialog();
                                if (data != null) {
                                    if (AppApiContact.ErrorCode.SUCCESS.equals(data.rescode)) {
                                        ToastHelper.showInfo(getActivity(),"修改成功!");

                                    }
                                }
                            }

                            @Override
                            public void onFailDisplay(String errorMsg) {
                                closeProgressDialog();

                            }
                        });
                        CardSubmitParam cardSubmitParam = new CardSubmitParam();
                        cardSubmitParam.communityId = model.getCommunityid();
                        cardSubmitParam.doordesc = etPassword.getText().toString();
                        cardSubmitParam.carddesc = etGateCard.getText().toString();
                        cardSubmitController.cardSubmit(cardSubmitParam);
                        showProgressDialog();
                    }
                })
                .show();
    }


    @OnClick({R.id.bt_edit_history_text_1,R.id.bt_edit_history_text_2})
    public void goCardList(){
        Intent intent = new Intent(getActivity(),CardListActivity.class);
        intent.putExtra("communityId",model.getCommunityid());
        intent.putExtra("communityName",model.getCommunity());
        startActivity(intent);
    }

    //-----以下是图片逻辑

    /**
     * 小区门1
     */
    public static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 1;

    // 保存路径为 RMHandle/人员ID/photo
    private final static String path = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + "RMHandle/";
    private String photo_path;

    private String[] photo_ids,photo_paths;
    private Bitmap[] photoCacheBitmap;

    private File[] photoFile;
    private Uri[] origUri,cropUri;

    private int gatePhotoSize=2;
    private int pestPhotoSize=2;

    private int index;

    private String userId;

    private void initPhoto() {
        // 保存路径为 WoJiaWang/人员ID/portrait
        photo_path = path + "card/";
        int size = gatePhotoSize + pestPhotoSize;
        photo_ids = new String[size];
        photoCacheBitmap = new Bitmap[size];
        photo_paths = new String[size];
        photoFile = new File[size];
        origUri = new Uri[size];
        cropUri = new Uri[size];

        index = 0;

        userId = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_USER_ID);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver resolver = getActivity().getContentResolver();
        if (resultCode != getActivity().RESULT_OK)
            return;

        switch (requestCode) {
            case REQUEST_CODE_GETIMAGE_BYCAMERA://门洞照
                Bitmap myBitmapDoor = null;
                try {
                    super.onActivityResult(requestCode, resultCode, data);
                    byte[] mContent=ImageUtils.readStream(resolver.openInputStream(origUri[index]));
                    //图片旋转
                    int a = ImageUtils.getExifOrientation(ImageUtils.getPath(getActivity(), origUri[index]));
                    if(a!=0){
                        myBitmapDoor = ImageUtils.rotateBitMap(ImageUtils.getPicFromBytes(mContent, ImageUtils.getBitmapOption()),a);
                    }else{
                        myBitmapDoor = ImageUtils.getPicFromBytes(mContent, ImageUtils.getBitmapOption());
                    }
                    //将字节数组转换为ImageView可调用的Bitmap对象

                    //把得到的图片绑定在控件上显示
                    Bitmap bitmapTemp = ImageUtils.comp(myBitmapDoor);
                    photo_ids[index] = ImageUtils.getGatePicId(model.getCommunityid(),userId,index);
                    photo_paths[index] = ImageUtils.saveCompressPicPath(bitmapTemp,ImageUtils.getPointPicPath(photo_ids[index],photo_path),photo_path);
                    allPhotos[index].setImageBitmap(bitmapTemp);
                    photoCacheBitmap[index] = bitmapTemp;
//                    saveCompressPic(myBitmap);
                    myBitmapDoor.recycle();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                break;
            case Constant.KITKAT_LESS://门洞照
                Bitmap myBitmap3 = null;
                Uri uri = data.getData();
//                System.out.println("4.4以下，选择好图片了:  " + uri);
                try {
                    byte[] mContent3 = ImageUtils.readStream(resolver.openInputStream(uri));
                    //将字节数组转换为ImageView可调用的Bitmap对象
                    int b = ImageUtils.getExifOrientation(ImageUtils.getPath(getActivity(), uri));
                    if(b!=0){
                        myBitmap3 = ImageUtils.rotateBitMap(ImageUtils.getPicFromBytes(mContent3, ImageUtils.getBitmapOption()),b);
                    }else {
                        myBitmap3 = ImageUtils.getPicFromBytes(mContent3, ImageUtils.getBitmapOption());
                    }
                    //把得到的图片绑定在控件上显示
                    Bitmap bitmapTemp2 = ImageUtils.comp(myBitmap3);
                    photo_ids[index] = ImageUtils.getGatePicId(model.getCommunityid(),userId,index);
                    photo_paths[index] = ImageUtils.saveCompressPicPath(bitmapTemp2,ImageUtils.getPointPicPath(photo_ids[index],photo_path),photo_path);
                    allPhotos[index].setImageBitmap(bitmapTemp2);
                    photoCacheBitmap[index] = bitmapTemp2;
                    myBitmap3.recycle();
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            case Constant.KITKAT_ABOVE://门洞照
                Bitmap myBitmap4 = null;
                Uri uri3 = data.getData();
                // 先将这个uri转换为path，然后再转换为uri
//                System.out.println("4.4以上，选择好图片了");
                try {
                    byte[] mContent4 = ImageUtils.readStream(resolver.openInputStream(uri3));
                    //将字节数组转换为ImageView可调用的Bitmap对象
                    int c = ImageUtils.getExifOrientation(ImageUtils.getPath(getActivity(), uri3));
                    if(c!=0){
                        myBitmap4 = ImageUtils.rotateBitMap(ImageUtils.getPicFromBytes(mContent4, ImageUtils.getBitmapOption()),c);
                    }else {
                        myBitmap4 = ImageUtils.getPicFromBytes(mContent4, ImageUtils.getBitmapOption());
                    }
                    //把得到的图片绑定在控件上显示
                    Bitmap bitmapTemp3 = ImageUtils.comp(myBitmap4);
                    photo_ids[index] = ImageUtils.getGatePicId(model.getCommunityid(),userId,index);
                    photo_paths[index] = ImageUtils.saveCompressPicPath(bitmapTemp3,ImageUtils.getPointPicPath(photo_ids[index],photo_path),photo_path);
                    allPhotos[index].setImageBitmap(bitmapTemp3);
                    photoCacheBitmap[index] = bitmapTemp3;
                    myBitmap4.recycle();
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;

        }
    }


    @OnClick(R.id.iv_gate_photo_1)
    public void iv_gate_photo_1(){
        index = 0;
        showAddPhotoDialog(preGate.length>0 || !StringUtils.isEmpty(photo_ids[0]));
    }

    @OnClick(R.id.iv_gate_photo_2)
    public void iv_gate_photo_2(){
        index = 1;
        showAddPhotoDialog(preGate.length>1 || !StringUtils.isEmpty(photo_ids[1]));
    }

    @OnClick(R.id.iv_pest_photo_1)
    public void iv_pest_photo_1(){
        index = 2;
        showAddPhotoDialog(prePest.length>0 || !StringUtils.isEmpty(photo_ids[2]));
    }
    @OnClick(R.id.iv_pest_photo_2)
    public void iv_pest_photo_2(){
        index = 3;
        showAddPhotoDialog(prePest.length>1 || !StringUtils.isEmpty(photo_ids[3]));
    }

    /**
     *
     * @param flag true 查看大图  false 无
     */
    private void showAddPhotoDialog(boolean flag){
        new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_title_add_photo)
                .items(flag?R.array.dialog_add_photo_big:R.array.dialog_add_photo)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (which == 0) {
                            startActionGatePhoto();

                        }
                        // 相册选图
                        else if (which == 1) {
                            startImagePick();
                        }
                        else if (which ==2){
                            List<String> url = new ArrayList<>();
                            List<Boolean> imageFlag = new ArrayList<>();
                            List<Bitmap> imageLocal = new ArrayList<>();
                            boolean[] indexFlag = new boolean[4];

                            if(!StringUtils.isEmpty(photo_ids[0])){
                                imageLocal.add(photoCacheBitmap[0]);
                                url.add("");
                                indexFlag[0] = true;
                                imageFlag.add(false);
                            }else{
                                if(preGate.length>0 && !StringUtils.isEmpty(preGate[0])){
                                    imageLocal.add(null);
                                    url.add(preGate[0]);
                                    indexFlag[0] = true;
                                    imageFlag.add(true);

                                }
                            }


                            if(!StringUtils.isEmpty(photo_ids[1])){
                                imageLocal.add(photoCacheBitmap[1]);
                                url.add("");
                                indexFlag[1] = true;
                                imageFlag.add(false);

                            }else{
                                if(preGate.length>1 && !StringUtils.isEmpty(preGate[1])){
                                    imageLocal.add(null);
                                    url.add(preGate[1]);
                                    indexFlag[1] = true;
                                    imageFlag.add(true);

                                }
                            }

                            if(!StringUtils.isEmpty(photo_ids[2])){
                                imageLocal.add(photoCacheBitmap[2]);
                                url.add("");
                                indexFlag[2] = true;
                                imageFlag.add(false);

                            }else{
                                if(prePest.length>0 && !StringUtils.isEmpty(prePest[0])){
                                    imageLocal.add(null);
                                    url.add(prePest[0]);
                                    indexFlag[2] = true;
                                    imageFlag.add(true);

                                }
                            }

                            if(!StringUtils.isEmpty(photo_ids[3])){
                                imageLocal.add(photoCacheBitmap[3]);
                                url.add("");
                                indexFlag[3] = true;
                                imageFlag.add(false);

                            }else{
                                if(prePest.length>1 && !StringUtils.isEmpty(prePest[1])){
                                    imageLocal.add(null);
                                    url.add(prePest[1]);
                                    indexFlag[3] = true;
                                    imageFlag.add(true);
                                }
                            }

                            int tempIndex = 0;
                            for(int i=0;i<index;i++){
                                if(indexFlag[i]){
                                    tempIndex++;
                                }

                            }


                            ViewHelper.getNewImagePager(getActivity(), url, imageFlag, imageLocal,tempIndex);

                        }
                    }
                })
                .show();
    }

    /**
     * 相机拍照
     */
    private void startActionGatePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getCameraTempFileDoor());
        startActivityForResult(intent, REQUEST_CODE_GETIMAGE_BYCAMERA);
    }

    private Uri getCameraTempFileDoor() {
        if (PhotoSavePathUtil.checkSDCard()) {
            File savedir = new File(photo_path);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        } else {
            Toast.makeText(getActivity(), getString(R.string.toast_sdcard_error),
                    Toast.LENGTH_SHORT).show();
            return null;
        }
        // 照片命名
        // 裁剪头像的绝对路径
        photo_ids[index] = ImageUtils.getGatePicId(model.getCommunityid(),userId,index);
        photo_paths[index] = ImageUtils.getPointPicPath(photo_ids[index],photo_path);

        photoFile[index] = new File(photo_paths[index]);
        cropUri[index] = Uri.fromFile(photoFile[index]);
        this.origUri[index] = this.cropUri[index];
        return this.cropUri[index];
    }


    //    -------------- 选择本地图片
    private void startImagePick(){
        if (!ImageUtils.isKitKat) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            //由于startActivityForResult()的第二个参数"requestCode"为常量，
            //个人喜好把常量用一个类全部装起来，不知道各位大神对这种做法有异议没？
//            System.out.println("<4.4");
            startActivityForResult(Intent.createChooser(intent, "选择图片"), Constant.KITKAT_LESS);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            //由于Intent.ACTION_OPEN_DOCUMENT的版本是4.4以上的内容
            //所以注意这个方法的最上面添加了@SuppressLint("InlinedApi")
            //如果客户使用的不是4.4以上的版本，因为前面有判断，所以根本不会走else，
            //也就不会出现任何因为这句代码引发的错误
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            System.out.println(">=4.4");
            startActivityForResult(Intent.createChooser(intent, "选择图片"), Constant.KITKAT_ABOVE);
        }

    }

    //-----以上是图片逻辑

    @OnClick(R.id.bt_edit_save_photo)
    public void picSubmit(){
        new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_title_submit_gate_photo)
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        PicSubmitController picSubmitController = new PicSubmitController(new UiDisplayListener<PicSubmitModel>() {
                            @Override
                            public void onSuccessDisplay(PicSubmitModel data) {
                                closeProgressDialog();
                                if (data != null) {
                                    if (AppApiContact.ErrorCode.SUCCESS.equals(data.rescode)) {
                                        ToastHelper.showInfo(getActivity(),"修改照片成功!");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                getActivity().finish();
                                            }
                                        }, 1000);
                                    }
                                }
                            }

                            @Override
                            public void onFailDisplay(String errorMsg) {
                                closeProgressDialog();

                            }
                        });

                        PicSubmitParam picSubmitParam = new PicSubmitParam();
                        picSubmitParam.communityId = model.getCommunityid();
                        int allSize = gatePhotoSize+pestPhotoSize;
                        File[] resultFile = new File[allSize];
                        String[] resultId = new String[allSize];

                        for(int i=0;i<allSize;i++){
                            if(!StringUtils.isEmpty(photo_paths[i])){
                                resultFile[i] = new File(photo_paths[i]);
                                if(!resultFile[i].exists()){
                                    resultFile[i] = null;
                                }
                            }
                            if(!StringUtils.isEmpty(resultId[i])){
                                resultId[i] = photo_ids[1];
                            }else{
                                resultId[i] = "";
                            }
                        }

                        picSubmitController.picSubmit(picSubmitParam,resultFile[0],resultFile[1],resultFile[2],resultFile[3],resultId[0],resultId[1],resultId[2],resultId[3]);
                        showProgressDialog();
                    }
                })
                .show();
    }



    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
