package cn.com.reachmedia.rmhandle.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.ui.view.imagepager.ImageAllBean;
import cn.com.reachmedia.rmhandle.utils.ImageCacheUtils;
import cn.com.reachmedia.rmhandle.utils.StringUtils;

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


    public void setCommunityPhoto(PointListModel pointListModel){
        if (!StringUtils.isEmpty(pointListModel.getCGatePics())) {
            String[] gatePath = pointListModel.getCGatePics().split("@&");
            String[] gatePath2 = pointListModel.getCGatePic().split("@&");
            if(!StringUtils.isEmpty(gatePath[0])){
                System.out.println("====>  "+gatePath[0]);
                ImageCacheUtils.getInstance().displayLocalOrUrl(getContext(),gatePath[0],ivCommPhoto1);
//                commImgList.add(gatePath2[0]);
            }else if(gatePath.length>1 && !StringUtils.isEmpty(gatePath[1])){
//                commImgList.add(gatePath2[1]);
                ImageCacheUtils.getInstance().displayLocalOrUrl(getContext(),gatePath[1],ivCommPhoto1);
            }else{
//                commImgList.add("");
            }
        }else{
//            commImgList.add("");
        }

        if (!StringUtils.isEmpty(pointListModel.getCPestPics())) {
            String[] pestPath = pointListModel.getCPestPics().split("@&");
            String[] pestPath2 = pointListModel.getCPestPic().split("@&");
            if(!StringUtils.isEmpty(pestPath[0])){
//                commImgList.add(pestPath2[0]);
                ImageCacheUtils.getInstance().displayLocalOrUrl(getContext(),pestPath[0],ivCommPhoto3);
            }else if(pestPath.length>1 && !StringUtils.isEmpty(pestPath[1])){
//                commImgList.add(pestPath2[1]);
                ImageCacheUtils.getInstance().displayLocalOrUrl(getContext(),pestPath[1],ivCommPhoto3);
            }else{
//                commImgList.add("");
            }
        }else{
//            commImgList.add("");
        }
    }
}
