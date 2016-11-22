package cn.com.reachmedia.rmhandle.ui.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.CommDoorPicBean;
import cn.com.reachmedia.rmhandle.bean.PointBean;
import cn.com.reachmedia.rmhandle.bean.PointWorkBean;
import cn.com.reachmedia.rmhandle.db.utils.PointWorkBeanDbUtil;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.ui.bean.FileDb;
import cn.com.reachmedia.rmhandle.ui.bean.PictureBean;
import cn.com.reachmedia.rmhandle.ui.fragment.NewPointDetailFragment;
import cn.com.reachmedia.rmhandle.ui.view.imagepager.ImageAllBean;
import cn.com.reachmedia.rmhandle.utils.FileUtils;
import cn.com.reachmedia.rmhandle.utils.ImageCacheUtils;
import cn.com.reachmedia.rmhandle.utils.ImageUtils;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import cn.com.reachmedia.rmhandle.utils.TimeUtils;
import cn.com.reachmedia.rmhandle.utils.ToastHelper;
import cn.com.reachmedia.rmhandle.utils.ViewHelper;
import cn.com.reachmedia.rmhandle.utils.pictureutils.camera.PhotoPickManger;

/**
 * Created by tedyuen on 16-9-20.
 */
public class Line2ImageLayout extends FrameLayout implements PointDetailLine{

    @Bind(R.id.iv_comm_photo_1)
    ImageView ivCommPhoto1;
    @Bind(R.id.rl_comm_photo_1)
    RelativeLayout rlCommPhoto1;
    @Bind(R.id.iv_comm_photo_3)
    ImageView ivCommPhoto3;
    @Bind(R.id.rl_comm_photo_3)
    RelativeLayout rlCommPhoto3;
    @Bind(R.id.iv_comm_photo_2)
    ImageView ivCommPhoto2;
    @Bind(R.id.rl_comm_photo_2)
    RelativeLayout rlCommPhoto2;

    NewPointDetailFragment fragment;

    PhotoPickManger pickManger;
    PictureBean resultDatas;
    PictureBean resultDatas1;
    PictureBean resultDatas2;
    Activity activity;

    public Line2ImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.line2_image_layout, this);
        ButterKnife.bind(this);
    }

    /**
     * 设置门洞照片
     * @param bean
     */
    public void setDoorPic(ImageAllBean bean) {
        //TODO 这里还需要设置原有照片到 resultDatas
        bean.doPicasso(getContext(),ivCommPhoto2,ImageAllBean.THUMBNAIL_WIDTH,ImageAllBean.THUMBNAIL_HEIGHT);
    }

    /**
     * 设置小区照片
     */
    public void setCommunityPhoto(PointListModel pointListModel, CommDoorPicBean commBean){

        String[] preGate;
        if(pointListModel.getCGatePics()!=null ){
            preGate = pointListModel.getCGatePics().split("@&");
        }else{
            preGate = pointListModel.getCGatePic().split("@&");
        }
        if(!StringUtils.isEmpty(preGate[0])){
            resultDatas1 = new PictureBean();
            resultDatas1.setFileId("");
            resultDatas1.setMainPath(preGate[0]);
            resultDatas1.setSubPath(preGate[0].replace("t_","s_"));
            resultDatas1.setType(PictureBean.PictureType.TYPE_3);
        }else if(preGate.length>1 && !StringUtils.isEmpty(preGate[1])){
            resultDatas1 = new PictureBean();
            resultDatas1.setFileId("");
            resultDatas1.setMainPath(preGate[1]);
            resultDatas1.setSubPath(preGate[1].replace("t_","s_"));
            resultDatas1.setType(PictureBean.PictureType.TYPE_3);
        }

        String[] prePest;
        if(pointListModel.getCPestPics()!=null ){
            prePest = pointListModel.getCPestPics().split("@&");
        }else{
            prePest = pointListModel.getCPestPic().split("@&");
        }
        if(!StringUtils.isEmpty(prePest[0])){
            resultDatas2 = new PictureBean();
            resultDatas2.setFileId("");
            resultDatas2.setMainPath(prePest[0]);
            resultDatas2.setSubPath(prePest[0].replace("t_","s_"));
            resultDatas2.setType(PictureBean.PictureType.TYPE_3);
        }else if(prePest.length>1 && !StringUtils.isEmpty(prePest[1])){
            resultDatas2 = new PictureBean();
            resultDatas2.setFileId("");
            resultDatas2.setMainPath(prePest[1]);
            resultDatas2.setSubPath(prePest[1].replace("t_","s_"));
            resultDatas2.setType(PictureBean.PictureType.TYPE_3);
        }

        if(!fragment.commInsertOrUpdate){//有本地未提交数据
            if(commBean!=null){
                if(!StringUtils.isEmpty(commBean.getCommunityFile1())){
                    resultDatas1 = new PictureBean();
                    resultDatas1.setMainPath(commBean.getCommunityFile1());
                    resultDatas1.setFileId(commBean.getCommunityFileId1());
                    resultDatas1.setType(PictureBean.PictureType.TYPE_1);
                }else if(!StringUtils.isEmpty(commBean.getCommunityFile2())){
                    resultDatas1 = new PictureBean();
                    resultDatas1.setMainPath(commBean.getCommunityFile2());
                    resultDatas1.setFileId(commBean.getCommunityFileId2());
                    resultDatas1.setType(PictureBean.PictureType.TYPE_1);
                }

                if(!StringUtils.isEmpty(commBean.getCommunitySpace1())){
                    resultDatas2 = new PictureBean();
                    resultDatas2.setMainPath(commBean.getCommunitySpace1());
                    resultDatas2.setFileId(commBean.getCommunitySpaceId1());
                    resultDatas2.setType(PictureBean.PictureType.TYPE_1);
                }else if(!StringUtils.isEmpty(commBean.getCommunitySpace2())){
                    resultDatas2 = new PictureBean();
                    resultDatas2.setMainPath(commBean.getCommunitySpace2());
                    resultDatas2.setFileId(commBean.getCommunitySpaceId2());
                    resultDatas2.setType(PictureBean.PictureType.TYPE_1);
                }
            }
        }

        if(resultDatas1!=null){
            resultDatas1.displayImage(ivCommPhoto1);
        }
        if(resultDatas2!=null){
            resultDatas2.displayImage(ivCommPhoto3);
        }

    }

    public void goViewDoorPhoto(int index){
        int tempIndex = -1;
        List<PictureBean> imageDatas = new ArrayList<>();
        if(resultDatas1!=null){

            imageDatas.add(resultDatas1);
            tempIndex = index;
        }
        if(resultDatas2!=null){
            imageDatas.add(resultDatas2);
            if(tempIndex!=-1){
                tempIndex = index;
            }else{
                tempIndex = 0;
            }
        }
        if(tempIndex!=-1){
            ViewHelper.getPictureImagePager(getContext(), imageDatas, tempIndex);
            return;
        }
    }

    @OnClick(R.id.rl_comm_photo_1)
    public void goViewCommPhoto1(){
        goViewDoorPhoto(0);
//        ViewHelper.getAllImagePager(getContext(), commImageDatas, 0);
    }
    @OnClick(R.id.rl_comm_photo_3)
    public void goViewCommPhoto2(){
        goViewDoorPhoto(1);
//        ViewHelper.getAllImagePager(getContext(), commImageDatas, 1);
    }


    //以下是门洞拍照逻辑
    /**
     * 放大门洞图
     */
    public void goViewDoorPhoto(){
        if(resultDatas!=null){
            List<PictureBean> imageDatas = new ArrayList<>();
            imageDatas.add(resultDatas);
            ViewHelper.getPictureImagePager(getContext(), imageDatas, 1);
            return;
        }
        ToastHelper.showAlert(activity,"暂无图片,上传图片请点击修改进行操作。");
    }

    public void updateAddPhotosClickState(Activity activity, Bundle savedInstanceState){
        this.activity = activity;
        pickManger = new PhotoPickManger("pickDoor",activity, savedInstanceState,new PhotoPickManger.OnPhotoPickFinsh() {
            @Override
            public void onPhotoPick(List<File> list) {
                for(File file:list){
                    String fileId = ImageUtils.getPointPicId(fragment.workId, fragment.pointId, "door", fragment.bean.getUserId());
                    String filePath = ImageUtils.getPointPicPath(fileId, LineImageLayout.photo_path);
                    PictureBean tempBean = new PictureBean(file, PictureBean.PictureType.TYPE_4,fileId,filePath);
                    tempBean.setWaterMark(true);
                    resultDatas = tempBean;
                }
                refreshAllImage();
            }
        });
        pickManger.flushBundle();
    }


    /**
     * 刷新所有图片
     */
    public void refreshAllImage(){
        if(resultDatas!=null){
            resultDatas.displayImage(ivCommPhoto2);
        }
    }

    @OnClick(R.id.rl_comm_photo_2)
    public void clickDoor(){
        if(resultDatas==null){//直接拍照
            startAlbum();
        }else{
            if(!fragment.checkChangeEditMode()){
                goViewDoorPhoto();
            }else{
                new MaterialDialog.Builder(getContext())
                        .title(R.string.dialog_title_add_photo)
                        .items(R.array.new_dialog_add_photo_big)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                if (which == 0) {//拍照
                                    startAlbum();
                                }else if (which == 1) {// 查看大图
                                    goViewDoorPhoto();
                                }
                            }
                        })
                        .show();
            }
        }
    }

    private void startAlbum(){
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(fragment.getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, new PermissionsResultAction() {

                    @Override
                    public void onGranted() {
                        pickManger.setReturnFileCount(1);
                        pickManger.start(PhotoPickManger.Mode.AS_WEIXIN_IMGCAPTRUE);
                    }

                    @Override
                    public void onDenied(String permission) {
                        if(fragment.getActivity()!=null){
                            ToastHelper.showAlert(fragment.getActivity(),fragment.getString(R.string.sdcard_denied));
                        }
                    }
                }
        );
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        pickManger.onActivityResult(requestCode,resultCode,data);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        pickManger.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public void init(NewPointDetailFragment fragment) {
        this.fragment = fragment;
        PointBean bean = fragment.bean;
        PointWorkBean pointWorkBean = fragment.pointWorkBean;
        if(bean!=null){
            if(!StringUtils.isEmpty(bean.getDoorId()) && !StringUtils.isEmpty(bean.getCDoorPic())){
                resultDatas = new PictureBean();
                resultDatas.setFileId(bean.getDoorId());
                resultDatas.setMainPath(bean.getCDoorPic());
                resultDatas.setSubPath(bean.getCDoorPic().replace("t_","s_"));
                resultDatas.setType(PictureBean.PictureType.TYPE_3);
            }
        }

        if(pointWorkBean!=null) {
            if(!StringUtils.isEmpty(pointWorkBean.getDoorpicid()) && !StringUtils.isEmpty(pointWorkBean.getDoorpic())){
                if(resultDatas==null || !resultDatas.getFileId().equals(pointWorkBean.getDoorpicid())){
                    resultDatas = new PictureBean();
                    resultDatas.setMainPath(pointWorkBean.getDoorpic());
                    resultDatas.setFileId(pointWorkBean.getDoorpicid());
                    resultDatas.setType(PictureBean.PictureType.TYPE_1);
                }else{
                    resultDatas.setMainPath(pointWorkBean.getDoorpic());
                    resultDatas.setType(PictureBean.PictureType.TYPE_2);
                }
            }
        }
        refreshAllImage();
    }

    @Override
    public void changeEditMode(boolean flag) {
        if(resultDatas==null){
            if (flag) {
                if (StringUtils.isEmpty(fragment.bean.getCDoorPic())) {
                    ivCommPhoto2.setImageDrawable(getResources().getDrawable(R.mipmap.picture_add_icon));
                }
            } else {
                if (StringUtils.isEmpty(fragment.bean.getCDoorPic())) {
                    ivCommPhoto2.setImageDrawable(getResources().getDrawable(R.drawable.abc));
                }
            }
        }
    }


    public FileDb getFileDB(){
        if(resultDatas!=null && !resultDatas.isDeleted()){
            FileDb db = new FileDb();
            db.setFileIds(resultDatas.getFileId());
            db.setFilePaths(resultDatas.getMainPath());
            db.setFileXY(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_LATITUDE)+ PointWorkBeanDbUtil.FILE_SPLIT3+SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_LONGITUDE));
            db.setFileTime(TimeUtils.getNowStr());
            db.getPictureBeen().add(resultDatas);
            return db;
        }else{
            return null;
        }
    }
}
