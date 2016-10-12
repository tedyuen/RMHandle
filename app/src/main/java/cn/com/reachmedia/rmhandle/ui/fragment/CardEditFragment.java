package cn.com.reachmedia.rmhandle.ui.fragment;

import android.Manifest;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.app.Constant;
import cn.com.reachmedia.rmhandle.bean.CommDoorPicBean;
import cn.com.reachmedia.rmhandle.db.helper.CommDoorPicDaoHelper;
import cn.com.reachmedia.rmhandle.db.utils.CommPoorPicDbUtil;
import cn.com.reachmedia.rmhandle.model.CardSubmitModel;
import cn.com.reachmedia.rmhandle.model.PicSubmitModel;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.model.param.CardSubmitParam;
import cn.com.reachmedia.rmhandle.model.param.PicSubmitParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.CardSubmitController;
import cn.com.reachmedia.rmhandle.network.controller.PicSubmitController;
import cn.com.reachmedia.rmhandle.service.ServiceHelper;
import cn.com.reachmedia.rmhandle.service.task.LocalImageAsyncTask;
import cn.com.reachmedia.rmhandle.ui.CardListActivity;
import cn.com.reachmedia.rmhandle.ui.base.BaseToolbarFragment;
import cn.com.reachmedia.rmhandle.ui.view.ProportionImageView;
import cn.com.reachmedia.rmhandle.ui.view.imagepager.ImageAllBean;
import cn.com.reachmedia.rmhandle.utils.ApartmentPointUtils;
import cn.com.reachmedia.rmhandle.utils.CropImageUtils;
import cn.com.reachmedia.rmhandle.utils.ImageCacheUtils;
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

    CommDoorPicBean commBean=null;
    boolean insertOrUpdate;

    CommPoorPicDbUtil commPoorPicDbUtil = CommPoorPicDbUtil.getIns();


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
            initBean();

            initPhoto();
            if(model.getCGatePics()!=null && model.getCPestPics()!=null){
//                System.out.println("s==> "+model.getCGatePics()+":"+model.getCPestPics());
                setCardPhoto(model.getCGatePics(),model.getCPestPics());
            }else{
//                System.out.println("b==> "+model.getCGatePic()+":"+model.getCPestPic());
                setCardPhoto(model.getCGatePic(),model.getCPestPic());
            }
//            setCardPhoto(model.getCGatePic(),model.getCPestPic());

        }
    }

    String[] preGate,prePest;
    private void setCardPhoto(String cGatePic,String cPestPic){
        preGate = cGatePic.split("@&");
        prePest = cPestPic.split("@&");

        for(int i=0;i<preGate.length;i++){
            if(i<gatePhotos.length){
                if(!StringUtils.isEmpty(preGate[i])){
                    boolean tempBoolean = ImageCacheUtils.getInstance().displayImage(preGate[i],gatePhotos[i]);
                    if(!tempBoolean) {
                        Picasso.with(getContext()).load(preGate[i]).placeholder(R.drawable.abc).resize(300, 261).centerCrop().into(gatePhotos[i]);
                    }
//                    System.out.println("本地环境图片path 1: "+i+":"+preGate[i]);

                }
            }
        }
        for(int i=0;i<prePest.length;i++){
            if(i<pestPhotos.length){
                if(!StringUtils.isEmpty(prePest[i])){
//                    System.out.println("本地环境图片path 2: "+i+":"+preGate[i]);
                    boolean tempBoolean = ImageCacheUtils.getInstance().displayImage(prePest[i],pestPhotos[i]);
                    if(!tempBoolean) {
                        Picasso.with(getContext()).load(prePest[i]).placeholder(R.drawable.abc).resize(300, 261).centerCrop().into(pestPhotos[i]);
                    }
                }
            }
        }

        if(!insertOrUpdate){//有本地未提交数据
            showLocalPic(commBean.getCommunityFile1(),gatePhotos[0]);
            showLocalPic(commBean.getCommunityFile2(),gatePhotos[1]);
            showLocalPic(commBean.getCommunitySpace1(),pestPhotos[0]);
            showLocalPic(commBean.getCommunitySpace2(),pestPhotos[1]);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            },200);

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
//    private Bitmap[] photoCacheBitmap;
    private String[] photoCachePath;

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
//        photoCacheBitmap = new Bitmap[size];
        photoCachePath = new String[size];
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
                super.onActivityResult(requestCode, resultCode, data);
                setImageUri(origUri[index],null);
                break;
            case Constant.KITKAT_LESS://门洞照
            case Constant.KITKAT_ABOVE://门洞照       System.out.println("4.4以上，选择好图片了");
                setImageUri(null,data);
                break;

        }
    }


    private void setImageUri(Uri uri,Intent data){
        Uri originalUri = null;
        File file = null;
        // 先将这个uri转换为path，然后再转换为uri
        try {
            if(uri!=null){
                originalUri = uri;
                photoCachePath[index] = ImageUtils.getPath(getActivity(), originalUri);
                file = new File(photoCachePath[index]);
            }else if (null != data && data.getData() != null) {
                originalUri = data.getData();
                photoCachePath[index] = ImageUtils.getPath(getActivity(), originalUri);
                file = new File(photoCachePath[index]);
            }
            Bitmap photoBmp = ImageUtils.getBitmapFormUri(getActivity(), Uri.fromFile(file));
            int degree = ImageUtils.getBitmapDegree(file.getAbsolutePath());
            /**
             * 把图片旋转为正的方向
             */
            Bitmap newbitmap = ImageUtils.rotateBitmapByDegree(photoBmp, degree);
            if(uri==null) {
                photo_ids[index] = ImageUtils.getGatePicId(model.getCommunityid(), userId, index);
                photo_paths[index] = ImageUtils.saveCompressPicPath(newbitmap, ImageUtils.getPointPicPath(photo_ids[index], photo_path), photo_path);
            }
            allPhotos[index].setImageBitmap(newbitmap);

//            photoCacheBitmap[index] = newbitmap;
//            photoBmp.recycle();
        }catch(Exception e){
            e.printStackTrace();
            photo_ids[index] = null;
        }
    }


    @OnClick(R.id.iv_gate_photo_1)
    public void iv_gate_photo_1(){
        index = 0;
        showAddPhotoDialog(photoCachePath[0]!=null || preGate.length>0 && !StringUtils.isEmpty(preGate[0]));
    }

    @OnClick(R.id.iv_gate_photo_2)
    public void iv_gate_photo_2(){
        index = 1;
        showAddPhotoDialog(photoCachePath[1]!=null || preGate.length>1);
    }

    @OnClick(R.id.iv_pest_photo_1)
    public void iv_pest_photo_1(){
        index = 2;
        showAddPhotoDialog(photoCachePath[2]!=null || prePest.length>0  && !StringUtils.isEmpty(prePest[0]));
    }
    @OnClick(R.id.iv_pest_photo_2)
    public void iv_pest_photo_2(){
        index = 3;
        showAddPhotoDialog(photoCachePath[3]!=null || prePest.length>1 );
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
                            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(getActivity(),
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionsResultAction() {

                                        @Override
                                        public void onGranted() {
                                            startImagePick();
                                        }

                                        @Override
                                        public void onDenied(String permission) {
                                            if(getActivity()!=null){
                                                ToastHelper.showAlert(getActivity(),getString(R.string.sdcard_denied));
                                            }
                                        }
                                    }
                            );

                        }
                        else if (which ==2){
                            List<ImageAllBean> imageData = new ArrayList<>();


                            if(!insertOrUpdate && !StringUtils.isEmpty(commBean.getCommunityFile1())){
                                ImageAllBean bean = new ImageAllBean(commBean.getCommunityFile1(),ImageAllBean.LOCAL_PATH_IMG);
                                imageData.add(bean);
                            }else if(photoCachePath[0]!=null){
                                ImageAllBean bean = new ImageAllBean(photoCachePath[0],ImageAllBean.LOCAL_PATH_IMG);
                                imageData.add(bean);
                            }else{
                                if(preGate.length>0 && !StringUtils.isEmpty(preGate[0])){
                                    ImageAllBean bean = new ImageAllBean(preGate[0],ImageAllBean.URL_IMG);
                                    imageData.add(bean);
                                }
                            }


                            if(!insertOrUpdate && !StringUtils.isEmpty(commBean.getCommunityFile2())){
                                ImageAllBean bean = new ImageAllBean(commBean.getCommunityFile2(),ImageAllBean.LOCAL_PATH_IMG);
                                imageData.add(bean);
                            }else if(photoCachePath[1]!=null){
                                ImageAllBean bean = new ImageAllBean(photoCachePath[1],ImageAllBean.LOCAL_PATH_IMG);
                                imageData.add(bean);
                            }else{
                                if(preGate.length>1 && !StringUtils.isEmpty(preGate[1])){
                                    ImageAllBean bean = new ImageAllBean(preGate[1],ImageAllBean.URL_IMG);
                                    imageData.add(bean);
                                }
                            }

                            if(!insertOrUpdate && !StringUtils.isEmpty(commBean.getCommunitySpace1())){
                                ImageAllBean bean = new ImageAllBean(commBean.getCommunitySpace1(),ImageAllBean.LOCAL_PATH_IMG);
                                imageData.add(bean);
                            }else if(photoCachePath[2]!=null){
                                ImageAllBean bean = new ImageAllBean(photoCachePath[2],ImageAllBean.LOCAL_PATH_IMG);
                                imageData.add(bean);
                            }else{
                                if(prePest.length>0 && !StringUtils.isEmpty(prePest[0])){
                                    ImageAllBean bean = new ImageAllBean(prePest[0],ImageAllBean.URL_IMG);
                                    imageData.add(bean);
                                }
                            }

                            if(!insertOrUpdate && !StringUtils.isEmpty(commBean.getCommunitySpace2())){
                                ImageAllBean bean = new ImageAllBean(commBean.getCommunitySpace2(),ImageAllBean.LOCAL_PATH_IMG);
                                imageData.add(bean);
                            }else if(photoCachePath[3]!=null){
                                ImageAllBean bean = new ImageAllBean(photoCachePath[2],ImageAllBean.LOCAL_PATH_IMG);
                                imageData.add(bean);
                            }else{
                                if(prePest.length>1 && !StringUtils.isEmpty(prePest[1])){
                                    ImageAllBean bean = new ImageAllBean(prePest[1],ImageAllBean.URL_IMG);
                                    imageData.add(bean);
                                }
                            }
                            ViewHelper.getAllImagePager(getActivity(), imageData,index);

                        }
                    }
                })
                .show();
    }

    /**
     * 相机拍照
     */
    private void startActionGatePhoto() {
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                new String[]{Manifest.permission.CAMERA}, new PermissionsResultAction() {

                    @Override
                    public void onGranted() {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, getCameraTempFileDoor());
                        startActivityForResult(intent, REQUEST_CODE_GETIMAGE_BYCAMERA);
                    }

                    @Override
                    public void onDenied(String permission) {
                        ToastHelper.showAlert(getActivity(),getString(R.string.camera_denied));
                    }
                }
        );


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
                        CommDoorPicBean bean = getCommBean();
                        if (bean == null) {
                            dialog.dismiss();
                            ToastHelper.showInfo(getActivity(), "请添加图片!");
                        } else {
                            if(insertOrUpdate){
                                commPoorPicDbUtil.insertOneData(bean);
                            }else{
                                commPoorPicDbUtil.updateOneData(bean);
                            }
                            //service
                            ServiceHelper.getIns().startCommDoorPicService(getContext(),false);
                            ToastHelper.showInfo(getActivity(), "提交成功!");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getActivity().finish();
                                }
                            }, 1000);
                        }


//                        showProgressDialog();
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "Activity-onRequestPermissionsResult() PermissionsManager.notifyPermissionsChange()");
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    public void initBean(){
        if (!StringUtils.isEmpty(model.getCommunityid())) {
            commBean = commPoorPicDbUtil.getBeanByCommId(model.getCommunityid(),"0");
        }
        insertOrUpdate = commBean==null;
    }

    private void showLocalPic(String picPath,ImageView imageView){
        LocalImageAsyncTask task = new LocalImageAsyncTask(imageView,true);
        task.execute(picPath);
//        Picasso.with(getContext()).load(new File(picPath)).resize(300,261).centerCrop().into(imageView);
    }

    public CommDoorPicBean getCommBean(){
        if(insertOrUpdate){
            commBean = new CommDoorPicBean();
            if (!StringUtils.isEmpty(model.getCommunityid())) {
                commBean.setCommunityId(model.getCommunityid());
            }else{
                return null;//没有小区id，异常
            }
        }
        commBean.setNativeState("0");
        int allSize = gatePhotoSize+pestPhotoSize;
        String[] resultFilePath = new String[allSize];
        String[] resultId = new String[allSize];

        for(int i=0;i<allSize;i++){
            if(!StringUtils.isEmpty(photo_paths[i])){
                resultFilePath[i] = photo_paths[i];
                if(!(new File(photo_paths[i]).exists())){
                    resultFilePath[i] = null;
                }
            }
            if(!StringUtils.isEmpty(photo_ids[i])){
                resultId[i] = photo_ids[i];
            }else{
                resultId[i] = "";
            }
        }

        boolean picFlag = false;

        if(!StringUtils.isEmpty(resultFilePath[0])){
            commBean.setCommunityFile1(resultFilePath[0]);
            picFlag = true;
        }
        if(!StringUtils.isEmpty(resultFilePath[1])){
            commBean.setCommunityFile2(resultFilePath[1]);
            picFlag = true;
        }
        if(!StringUtils.isEmpty(resultFilePath[2])){
            commBean.setCommunitySpace1(resultFilePath[2]);
            picFlag = true;
        }
        if(!StringUtils.isEmpty(resultFilePath[3])){
            commBean.setCommunitySpace2(resultFilePath[3]);
            picFlag = true;
        }

        if(!StringUtils.isEmpty(resultId[0])){
            commBean.setCommunityFileId1(resultId[0]);
            picFlag = true;
        }
        if(!StringUtils.isEmpty(resultId[1])){
            commBean.setCommunityFileId2(resultId[1]);
            picFlag = true;
        }
        if(!StringUtils.isEmpty(resultId[2])){
            commBean.setCommunitySpaceId1(resultId[2]);
            picFlag = true;
        }
        if(!StringUtils.isEmpty(resultId[3])){
            commBean.setCommunitySpaceId2(resultId[3]);
            picFlag = true;
        }

        if(!picFlag){
            return  null;
        }else{
            return commBean;
        }
    }
}
