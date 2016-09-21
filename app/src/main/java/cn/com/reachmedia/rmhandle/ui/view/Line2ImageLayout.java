package cn.com.reachmedia.rmhandle.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.ui.view.imagepager.ImageAllBean;

/**
 * Created by tedyuen on 16-9-20.
 */
public class Line2ImageLayout extends FrameLayout {


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

    public Line2ImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.line2_image_layout, this);
        ButterKnife.bind(this);


    }


    public void setDoorPic(ImageAllBean bean) {
        bean.doPicasso(getContext(),ivCommPhoto2,ImageAllBean.THUMBNAIL_WIDTH,ImageAllBean.THUMBNAIL_HEIGHT);
    }
}
