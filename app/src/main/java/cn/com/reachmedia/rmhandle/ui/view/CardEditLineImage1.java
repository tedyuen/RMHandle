package cn.com.reachmedia.rmhandle.ui.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.ui.bean.PictureBean;
import cn.com.reachmedia.rmhandle.ui.fragment.NewCardEditFragment;
import cn.com.reachmedia.rmhandle.utils.FileUtils;
import cn.com.reachmedia.rmhandle.utils.ImageCacheUtils;
import cn.com.reachmedia.rmhandle.utils.ImageUtils;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import cn.com.reachmedia.rmhandle.utils.ToastHelper;
import cn.com.reachmedia.rmhandle.utils.pictureutils.camera.PhotoPickManger;

/**
 * Created by tedyuen on 16-11-9.
 */
public class CardEditLineImage1 extends FrameLayout {

    @Bind(R.id.iv_gate_photo_1)
    ProportionImageView ivGatePhoto1;
    @Bind(R.id.iv_gate_photo_2)
    ProportionImageView ivGatePhoto2;

    public CardEditLineImage1(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.card_edit_line_layout_1, this);
        ButterKnife.bind(this);

    }

    PhotoPickManger pickManger1;
    PhotoPickManger pickManger2;
    NewCardEditFragment fragment;

    PictureBean resultDatas1;
    PictureBean resultDatas2;




    private final static String path = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + "RMHandle/";
    public final static String photo_path = path + "point/";

    public void init(NewCardEditFragment fragment){
        this.fragment = fragment;
        PointListModel pointListModel = fragment.pointListModel;

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
        }

        if(!StringUtils.isEmpty(preGate[1])){
            resultDatas2 = new PictureBean();
            resultDatas2.setFileId("");
            resultDatas2.setMainPath(preGate[1]);
            resultDatas2.setSubPath(preGate[1].replace("t_","s_"));
            resultDatas2.setType(PictureBean.PictureType.TYPE_3);
        }

        if(!fragment.insertOrUpdate){//有本地未提交数据

        }



        refreshAllImage();
    }


    /**
     * 更新添加图片点击按钮事件
     */
    public boolean updateAddPhotosClickState(Activity activity, Bundle savedInstanceState) {
        if (!FileUtils.mkdirAndCheck(photo_path)) {
            return false;
        }
        pickManger1 = new PhotoPickManger("cardedit1", activity, savedInstanceState, new PhotoPickManger.OnPhotoPickFinsh() {
            @Override
            public void onPhotoPick(List<File> list) {
                for (File file : list) {
                    String fileId = ImageUtils.getGatePicId(fragment.pointListModel.getCommunityid(),fragment.userId,1);
                    String filePath = ImageUtils.getPointPicPath(fileId, LineImageLayout.photo_path);
                    PictureBean tempBean = new PictureBean(file, PictureBean.PictureType.TYPE_4,fileId,filePath);
                    resultDatas1 = tempBean;
                }
                refreshAllImage();
            }
        });
        pickManger1.flushBundle();

        pickManger2 = new PhotoPickManger("cardedit2", activity, savedInstanceState, new PhotoPickManger.OnPhotoPickFinsh() {
            @Override
            public void onPhotoPick(List<File> list) {
                for (File file : list) {
                    String fileId = ImageUtils.getGatePicId(fragment.pointListModel.getCommunityid(),fragment.userId,2);
                    String filePath = ImageUtils.getPointPicPath(fileId, LineImageLayout.photo_path);
                    PictureBean tempBean = new PictureBean(file, PictureBean.PictureType.TYPE_4,fileId,filePath);
                    resultDatas2 = tempBean;
                }
                refreshAllImage();
            }
        });
        pickManger2.flushBundle();
        setAddPhotosClickEvent(1, 1);
        return true;
    }

    public void refreshAllImage(){
        if(resultDatas1!=null){
            resultDatas1.displayImage(ivGatePhoto1);
        }
        if(resultDatas2!=null){
            resultDatas2.displayImage(ivGatePhoto2);
        }
        setAddPhotosClickEvent(1, 1);
    }

    public void setAddPhotosClickEvent(final int index1,final int index2) {
        ivGatePhoto1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(resultDatas1==null){
                    takePhoto1(index1);
                }else{
                    new MaterialDialog.Builder(fragment.getActivity())
                            .title(R.string.dialog_title_add_photo)
                            .items(R.array.new_dialog_add_photo_big)
                            .itemsCallback(new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                    if (which == 0) {
                                        takePhoto1(index1);
                                    }
                                    else if (which == 1) {
                                        //查看大图
                                    }
                                }
                            })
                            .show();
                }

            }
        });

        ivGatePhoto2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(resultDatas2==null){
                    takePhoto2(index2);
                }else{
                    new MaterialDialog.Builder(fragment.getActivity())
                            .title(R.string.dialog_title_add_photo)
                            .items(R.array.new_dialog_add_photo_big)
                            .itemsCallback(new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                    if (which == 0) {
                                        takePhoto2(index2);
                                    }
                                    else if (which == 1) {
                                        //查看大图
                                    }
                                }
                            })
                            .show();
                }

            }
        });

    }

    private void takePhoto1(final int index){
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(fragment.getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, new PermissionsResultAction() {

                    @Override
                    public void onGranted() {
                        pickManger1.setReturnFileCount(index);
                        pickManger1.start(PhotoPickManger.Mode.AS_WEIXIN_IMGCAPTRUE);
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

    private void takePhoto2(final int index){
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(fragment.getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, new PermissionsResultAction() {

                    @Override
                    public void onGranted() {
                        pickManger2.setReturnFileCount(index);
                        pickManger2.start(PhotoPickManger.Mode.AS_WEIXIN_IMGCAPTRUE);
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
        pickManger1.onActivityResult(requestCode,resultCode,data);
        pickManger2.onActivityResult(requestCode,resultCode,data);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        pickManger1.onSaveInstanceState(savedInstanceState);
        pickManger2.onSaveInstanceState(savedInstanceState);
    }

}
