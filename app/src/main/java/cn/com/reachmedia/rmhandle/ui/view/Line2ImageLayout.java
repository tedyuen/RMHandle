package cn.com.reachmedia.rmhandle.ui.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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


    List<ImageAllBean> commImageDatas;//小区放大的资料
    NewPointDetailFragment fragment;

    PhotoPickManger pickManger;
    PictureBean resultDatas;
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
        // 这里
        commImageDatas = new ArrayList<>();
        if (!StringUtils.isEmpty(pointListModel.getCGatePics())) {
            String[] gatePath = pointListModel.getCGatePics().split("@&");
            String[] gatePath2 = pointListModel.getCGatePic().split("@&");
            if(!StringUtils.isEmpty(gatePath[0])){
                ImageAllBean allBean = new ImageAllBean(gatePath2[0],ImageAllBean.URL_IMG);
                commImageDatas.add(allBean);
                ImageCacheUtils.getInstance().displayLocalOrUrl(getContext(),gatePath[0],ivCommPhoto1);
            }else if(gatePath.length>1 && !StringUtils.isEmpty(gatePath[1])){
                ImageAllBean allBean = new ImageAllBean(gatePath2[1],ImageAllBean.URL_IMG);
                commImageDatas.add(allBean);
                ImageCacheUtils.getInstance().displayLocalOrUrl(getContext(),gatePath[1],ivCommPhoto1);
            }
        }

        if (!StringUtils.isEmpty(pointListModel.getCPestPics())) {
            String[] pestPath = pointListModel.getCPestPics().split("@&");
            String[] pestPath2 = pointListModel.getCPestPic().split("@&");
            if(!StringUtils.isEmpty(pestPath[0])){
                ImageAllBean allBean = new ImageAllBean(pestPath2[0],ImageAllBean.URL_IMG);
                commImageDatas.add(allBean);
                ImageCacheUtils.getInstance().displayLocalOrUrl(getContext(),pestPath[0],ivCommPhoto3);
            }else if(pestPath.length>1 && !StringUtils.isEmpty(pestPath[1])){
                ImageAllBean allBean = new ImageAllBean(pestPath2[1],ImageAllBean.URL_IMG);
                commImageDatas.add(allBean);
                ImageCacheUtils.getInstance().displayLocalOrUrl(getContext(),pestPath[1],ivCommPhoto3);
            }
        }
    }

    @OnClick(R.id.rl_comm_photo_1)
    public void goViewCommPhoto1(){
        ViewHelper.getAllImagePager(getContext(), commImageDatas, 0);
    }
    @OnClick(R.id.rl_comm_photo_3)
    public void goViewCommPhoto2(){
        ViewHelper.getAllImagePager(getContext(), commImageDatas, 1);
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
//                StringBuffer buffer = new StringBuffer();
                for(File file:list){
                    String fileId = ImageUtils.getPointPicId(fragment.workId, fragment.pointId, "door", fragment.bean.getUserId());
                    String filePath = ImageUtils.getPointPicPath(fileId, LineImageLayout.photo_path);
                    PictureBean tempBean = new PictureBean(file, PictureBean.PictureType.TYPE_4,fileId,filePath);
                    resultDatas = tempBean;
//                    buffer.append("" + file.getAbsolutePath() + " " + file.length()+"\n");
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
