package cn.com.reachmedia.rmhandle.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.service.task.LocalImageAsyncTask;
import cn.com.reachmedia.rmhandle.ui.bean.PictureBean;
import cn.com.reachmedia.rmhandle.utils.pictureutils.camera.PhotoPickManger;
import cn.com.reachmedia.rmhandle.utils.pictureutils.utils.SimpleImageLoader;

/**
 * Created by tedyuen on 16-9-20.
 */
public class LineImageLayout extends FrameLayout {


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

    PhotoPickManger pickManger;


    List<PictureBean> resultDatas;

    public LineImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.line_image_layout, this);
        ButterKnife.bind(this);
        addPhotos = new ImageView[]{ivPointPhoto1,ivPointPhoto2,ivPointPhoto3};
        deletes = new ImageView[]{ivDelete1,ivDelete2,ivDelete3};
        resultDatas = new ArrayList<>();


    }



    /**
     * 更新添加图片点击按钮事件
     */
    public void updateAddPhotosClickState(Activity activity,Bundle savedInstanceState){
        pickManger = new PhotoPickManger("pick",activity, savedInstanceState,new PhotoPickManger.OnPhotoPickFinsh() {
            @Override
            public void onPhotoPick(List<File> list) {
//                Toast.makeText(getActivity(), "" + list.get(0).getPath() + " " + list.get(0).length(), Toast.LENGTH_SHORT).show();
                StringBuffer buffer = new StringBuffer();
                for(File file:list){
                    PictureBean tempBean = new PictureBean(file, PictureBean.PictrueType.TYPE_4,"");
                    resultDatas.add(tempBean);
                    buffer.append("" + file.getAbsolutePath() + " " + file.length()+"\n");
                }
                System.out.println("filedetail:==> "+buffer.toString());
                refreshAllImage();
            }
        });
        pickManger.flushBundle();
        addPhotos[photoCount].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                pickManger.setReturnFileCount(3 - pickManger.getSelectsPhotos().size());
                pickManger.start(PhotoPickManger.Mode.AS_WEIXIN_IMGCAPTRUE);
            }
        });
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


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        pickManger.onActivityResult(requestCode,resultCode,data);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        pickManger.onSaveInstanceState(savedInstanceState);
    }
}
