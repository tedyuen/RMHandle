package cn.com.reachmedia.rmhandle.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.utils.pictureutils.camera.PhotoPickManger;

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


    public LineImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.line_image_layout, this);
        ButterKnife.bind(this);
        addPhotos = new ImageView[]{ivPointPhoto1,ivPointPhoto2,ivPointPhoto3};
        deletes = new ImageView[]{ivDelete1,ivDelete2,ivDelete3};

    }


    /**
     *
     */
    public void updateAddPhotosClickState(){

    }


}
