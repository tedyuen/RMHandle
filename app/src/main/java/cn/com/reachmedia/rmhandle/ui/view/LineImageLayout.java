package cn.com.reachmedia.rmhandle.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.PointBean;
import cn.com.reachmedia.rmhandle.bean.PointWorkBean;
import cn.com.reachmedia.rmhandle.db.utils.PointWorkBeanDbUtil;
import cn.com.reachmedia.rmhandle.service.task.LocalImageAsyncTask;
import cn.com.reachmedia.rmhandle.ui.bean.PictureBean;
import cn.com.reachmedia.rmhandle.ui.fragment.NewPointDetailFragment;
import cn.com.reachmedia.rmhandle.utils.ImageUtils;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import cn.com.reachmedia.rmhandle.utils.TimeUtils;
import cn.com.reachmedia.rmhandle.utils.ToastHelper;
import cn.com.reachmedia.rmhandle.utils.ViewHelper;
import cn.com.reachmedia.rmhandle.utils.pictureutils.camera.PhotoPickManger;
import cn.com.reachmedia.rmhandle.utils.pictureutils.utils.SimpleImageLoader;

/**
 * Created by tedyuen on 16-9-20.
 */
public class LineImageLayout extends FrameLayout implements PointDetailLine{


    @Bind(R.id.iv_point_photo_1)
    ImageView ivPointPhoto1;
    @Bind(R.id.iv_delete_1)
    ImageView ivDelete1;
    @Bind(R.id.iv_point_photo_2)
    ImageView ivPointPhoto2;
    @Bind(R.id.iv_delete_2)
    ImageView ivDelete2;
    @Bind(R.id.iv_point_photo_3)
    ImageView ivPointPhoto3;
    @Bind(R.id.iv_delete_3)
    ImageView ivDelete3;

    ImageView[] addPhotos;//3个点位图片
    ImageView[] deletes;//3个删除按钮

    private final static int photoMaxCount = 3;
    private int photoCount;//当前图片数量
    private int photoName;

    PhotoPickManger pickManger;
    NewPointDetailFragment fragment;


    List<PictureBean> resultDatas;

    private final static String path = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + "RMHandle/";
    public final static String photo_path = path + "point/";

    public LineImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.line_image_layout, this);
        ButterKnife.bind(this);
        addPhotos = new ImageView[]{ivPointPhoto1,ivPointPhoto2,ivPointPhoto3};
        deletes = new ImageView[]{ivDelete1,ivDelete2,ivDelete3};
        resultDatas = new ArrayList<>();


    }


    @Override
    public void init(NewPointDetailFragment fragment) {
        this.fragment = fragment;
        PointBean bean = fragment.bean;
        PointWorkBean pointWorkBean = fragment.pointWorkBean;
        if(bean!=null){
            //初始化网络图片
            if(!StringUtils.isEmpty(bean.getFileId())){
                String[] photoIds = bean.getFileId().split(PointWorkBeanDbUtil.FILE_SPLIT);
                String[] urlBs = bean.getFileUrlB().split(PointWorkBeanDbUtil.FILE_SPLIT);
                String[] urlSs = bean.getFileUrlS().split(PointWorkBeanDbUtil.FILE_SPLIT);
                for(int i=0;i<photoIds.length;i++){
                    PictureBean pictureBean = new PictureBean();
                    pictureBean.setFileId(photoIds[i]);
                    pictureBean.setType(PictureBean.PictureType.TYPE_3);
                    if(i<urlBs.length){
                        pictureBean.setSubPath(urlBs[i]);
                    }else{
                        pictureBean.setSubPath("");
                    }
                    if(i<urlSs.length){
                        pictureBean.setMainPath(urlSs[i]);
                    }else{
                        pictureBean.setMainPath("");
                    }
                    resultDatas.add(pictureBean);
                }
            }
        }


        if(pointWorkBean!=null){
            String[] cacheFileId, cacheFilePath,deleteIds;
            cacheFileId = pointWorkBean.getFileIdData() == null ? new String[0] : pointWorkBean.getFileIdData().split(PointWorkBeanDbUtil.FILE_SPLIT);
            cacheFilePath = pointWorkBean.getFilePathData() == null ? new String[0] : pointWorkBean.getFilePathData().split(PointWorkBeanDbUtil.FILE_SPLIT);
            deleteIds = pointWorkBean.getFiledelete() == null? new String[0]:pointWorkBean.getFiledelete().split(PointWorkBeanDbUtil.FILE_SPLIT2);
            //未提交图片
            for(int i=0;i<cacheFileId.length;i++){
                for(PictureBean pictureBean:resultDatas){
                    if(cacheFileId[i].equals(pictureBean.getFileId())){
                        pictureBean.setMainPath(cacheFilePath[i]);
                        pictureBean.setType(PictureBean.PictureType.TYPE_2);
                        break;
                    }
                }
            }
            //已经删除图片
            for(int i=0;i<deleteIds.length;i++){
                for(PictureBean pictureBean:resultDatas){
                    if(cacheFileId[i].equals(pictureBean.getFileId())){
                        pictureBean.setDeleted(true);
                        break;
                    }
                }
            }



        }


        refreshAllImage();
    }

    /**
     * 更新添加图片点击按钮事件
     */
    public void updateAddPhotosClickState(Activity activity,Bundle savedInstanceState){
        pickManger = new PhotoPickManger("pick",activity, savedInstanceState,new PhotoPickManger.OnPhotoPickFinsh() {
            @Override
            public void onPhotoPick(List<File> list) {
                StringBuffer buffer = new StringBuffer();
                for(File file:list){
                    String fileId = ImageUtils.getPointPicId(fragment.workId, fragment.pointId, String.valueOf(photoName), fragment.bean.getUserId());
                    String filePath = ImageUtils.getPointPicPath(fileId, photo_path);
                    PictureBean tempBean = new PictureBean(file, PictureBean.PictureType.TYPE_4,fileId,filePath);
                    resultDatas.add(tempBean);
                    photoName++;
                    buffer.append("" + file.getAbsolutePath() + " " + file.length()+"\n");
                }
                System.out.println("filedetail:==> "+buffer.toString());
                refreshAllImage();
            }
        });
        pickManger.flushBundle();
        setAddPhotosClickEvent(photoCount);
    }

    public void setAddPhotosClickEvent(int index){
        if(index<addPhotos.length){
            addPhotos[index].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    pickManger.setReturnFileCount(addPhotos.length - photoCount);
                    pickManger.start(PhotoPickManger.Mode.AS_WEIXIN_IMGCAPTRUE);
                }
            });
        }
        for(int i=0;i<index;i++){
            final int y = i;
            addPhotos[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    goViewDoorPhoto(y);
                }
            });
        }

    }

    public void goViewDoorPhoto(int index){
        if(resultDatas!=null){
            List<PictureBean> imageDatas = new ArrayList<>();
            for(PictureBean temp:resultDatas){
                if(!temp.isDeleted()){
                    imageDatas.add(temp);
                }
            }
            ViewHelper.getPictureImagePager(getContext(), imageDatas, index);
        }
    }

    /**
     * 刷新所有图片
     */
    public void refreshAllImage(){
        int count=0;
        hideAllImage();
        for(PictureBean bean:resultDatas){
            if(count>=addPhotos.length) break;//超过3帐
            if (!bean.isDeleted()) {
                bean.displayImage(addPhotos[count]);
                deletes[count].setVisibility(View.VISIBLE);
                count++;
            }
        }

        photoCount = count;//设置当前图片数量
        if(count<addPhotos.length){
            addPhotos[count].setVisibility(View.VISIBLE);
            addPhotos[count].setImageResource(R.mipmap.picture_add_icon);
        }
        setAddPhotosClickEvent(count);

    }

    /**
     * 赢藏所有图片
     */
    public void hideAllImage(){
        for(int i=0;i<addPhotos.length;i++){
            addPhotos[i].setVisibility(View.INVISIBLE);
            deletes[i].setVisibility(View.INVISIBLE);
        }
    }

    /**
     *
     * @param index
     */
    public void deleteImageData(final int index) {
        new MaterialDialog.Builder(getContext())
                .title(R.string.dialog_title_del_photo)
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
                        int start = 0;
                        for (PictureBean bean : resultDatas) {
                            if(!bean.isDeleted()){
                                if(start==index){
                                    bean.setDeleted(true);
                                    break;
                                }else{
                                    start++;
                                }
                            }
                        }
                        refreshAllImage();
                    }
                }).show();
    }

    /**
     * 计算当前图片数量
     * @return
     */
    public int getCurrentPhotoSize(){
        int count = 0;
        for(PictureBean bean:resultDatas){
            if(count>=addPhotos.length) break;//超过3帐
            if (!bean.isDeleted()) {
                count++;
            }
        }
        photoCount = count;
        return count;
    }

    @OnClick({R.id.iv_delete_1,R.id.iv_delete_2,R.id.iv_delete_3})
    public void initDeleteBtnClickEvent(ImageView button){
        switch (button.getId()){
            case R.id.iv_delete_1:
                deleteImageData(0);
                break;
            case R.id.iv_delete_2:
                deleteImageData(1);
                break;
            case R.id.iv_delete_3:
                deleteImageData(2);
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        pickManger.onActivityResult(requestCode,resultCode,data);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        pickManger.onSaveInstanceState(savedInstanceState);
    }



    @Override
    public void changeEditMode(boolean flag) {
        if (flag) {
            for (int i = 0; i < photoCount; i++) {
                if (i < deletes.length)
                    deletes[i].setVisibility(View.VISIBLE);
            }
            if (photoCount < addPhotos.length) {
                addPhotos[photoCount].setVisibility(View.VISIBLE);
            }
        } else {

            for (int i = 0; i < photoCount; i++) {
                if (i < deletes.length)
                    deletes[i].setVisibility(View.GONE);
            }
            if (photoCount < addPhotos.length) {
                addPhotos[photoCount].setVisibility(View.GONE);
            }
        }
    }


    /**
     * 图片是否为空
     * @return
     */
    public boolean isPhotoEmpty(){
        return false;
    }

    public FileDb getFileDB(boolean insertOrUpdate,PointWorkBean pointWorkBean){
        FileDb db = new FileDb();
        int count =0;
        StringBuffer deleteIds = new StringBuffer();
        StringBuffer fileIds = new StringBuffer();
        StringBuffer filePaths = new StringBuffer();
        StringBuffer fileXY = new StringBuffer();
        StringBuffer fileTime = new StringBuffer();
        if (!insertOrUpdate && !StringUtils.isEmpty(pointWorkBean.getFiledelete())){//整合以前删除的图片id
            deleteIds.append(pointWorkBean.getFiledelete());
        }
        for(PictureBean bean:resultDatas){
            if(count>=addPhotos.length){
                break;
            }
            if(!bean.isDeleted()){
                if(!bean.getType().equals(PictureBean.PictureType.TYPE_3)){//网络图片不要提交
                    appendFileIds(fileIds,bean.getFileId());
                    appendFilePath(filePaths,bean.getMainPath());
                    appendFileXY(fileXY);
                    appendFileTime(fileTime);
//                    switch (bean.getType()){
//                        case TYPE_1:
//                            appendFileIds(fileIds,bean.getFileId());
//                            appendFilePath(filePaths,bean.getMainPath());
//                            appendFileXY(fileXY);
//                            appendFileTime(fileTime);
//                            break;
//                        case TYPE_2:
//                            appendFileIds(fileIds,bean.getFileId());
//                            appendFilePath(filePaths,bean.getMainPath());
//                            appendFileXY(fileXY);
//                            appendFileTime(fileTime);
//                            break;
//                        case TYPE_4:
//                            appendFileIds(fileIds,bean.getFileId());
//                            appendFilePath(filePaths,bean.getMainPath());
//                            appendFileXY(fileXY);
//                            appendFileTime(fileTime);
//                            break;
//                    }
                    count++;
                }
            }else{//删除的图片
                if(deleteIds.length()>0){
                    deleteIds.append(PointWorkBeanDbUtil.FILE_SPLIT2);
                }
                deleteIds.append(bean.getFileId());
            }
        }

        db.setDeleteIds(deleteIds.toString());
        db.setFileIds(fileIds.toString());
        db.setFilePaths(filePaths.toString());
        db.setFileXY(fileXY.toString());
        db.setFileTime(fileTime.toString());
        db.setFileCount(count);
        return db;
    }

    private void appendFileIds(StringBuffer buffer,String id){
        if(buffer.length()>0){
            buffer.append(PointWorkBeanDbUtil.FILE_SPLIT);
        }
        buffer.append(id);
    }

    private void appendFilePath(StringBuffer buffer,String path){
        if(buffer.length()>0){
            buffer.append(PointWorkBeanDbUtil.FILE_SPLIT);
        }
        buffer.append(path);
    }

    private void appendFileXY(StringBuffer buffer){
        if(buffer.length()>0){
            buffer.append(PointWorkBeanDbUtil.FILE_SPLIT2);
        }
        buffer.append(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_LATITUDE)+PointWorkBeanDbUtil.FILE_SPLIT3+SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_LONGITUDE));
    }

    private void appendFileTime(StringBuffer buffer){
        if(buffer.length()>0){
            buffer.append(PointWorkBeanDbUtil.FILE_SPLIT);
        }
        buffer.append(TimeUtils.getNowStr());
    }



    public class FileDb{
        private String deleteIds;
        private String fileIds;
        private String filePaths;
        private String fileXY;
        private String fileTime;
        private int fileCount;

        @Override
        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append("=====================\n");
            buffer.append("filedIds: ");
            buffer.append(fileIds);
            buffer.append("\n");
            buffer.append("filePaths: ");
            buffer.append(filePaths);
            buffer.append("\n");
            buffer.append("fileXY: ");
            buffer.append(fileXY);
            buffer.append("\n");
            buffer.append("fileTime: ");
            buffer.append(fileTime);
            buffer.append("\n");
            buffer.append("deleteIds: ");
            buffer.append(deleteIds);
            buffer.append("\n");
            buffer.append("fileCount: ");
            buffer.append(fileCount);
            buffer.append("\n");
            buffer.append("=====================\n");
            return buffer.toString();
        }

        public String getDeleteIds() {
            return deleteIds;
        }

        public void setDeleteIds(String deleteIds) {
            this.deleteIds = deleteIds;
        }

        public String getFileIds() {
            return fileIds;
        }

        public void setFileIds(String fileIds) {
            this.fileIds = fileIds;
        }

        public String getFilePaths() {
            return filePaths;
        }

        public void setFilePaths(String filePaths) {
            this.filePaths = filePaths;
        }

        public String getFileXY() {
            return fileXY;
        }

        public void setFileXY(String fileXY) {
            this.fileXY = fileXY;
        }

        public String getFileTime() {
            return fileTime;
        }

        public void setFileTime(String fileTime) {
            this.fileTime = fileTime;
        }

        public int getFileCount() {
            return fileCount;
        }

        public void setFileCount(int fileCount) {
            this.fileCount = fileCount;
        }
    }
}
