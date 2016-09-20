package cn.com.reachmedia.rmhandle.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.ui.view.imagepager.ImageAllBean;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import cn.com.reachmedia.rmhandle.utils.ViewHelper;

/**
 * Created by tedyuen on 16-9-20.
 */
public class Line3ImageLayout extends FrameLayout {


    @Bind(R.id.iv_cust_photo_1)
    ImageView ivCustPhoto1;
    @Bind(R.id.iv_cust_photo_2)
    ImageView ivCustPhoto2;
    @Bind(R.id.iv_cust_photo_3)
    ImageView ivCustPhoto3;

    private ImageView[] custPhotos;
    List<ImageAllBean> imageDatas;//图片放大的资料
    public Line3ImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.line3_image_layout, this);
        ButterKnife.bind(this);
        custPhotos = new ImageView[]{ivCustPhoto1,ivCustPhoto2,ivCustPhoto3};

    }



    /**
     * 设置客户图片
     * @param beanList
     */
    public void setCusImg(List<PointListModel.ComListBean.PicListBean> beanList) {
        imageDatas = new ArrayList<>();
        for (int i = 0; i < beanList.size(); i++) {
            if (i >= 3) break;
            PointListModel.ComListBean.PicListBean picBean = beanList.get(i);
            //添加点击事件
            ImageAllBean allBean = new ImageAllBean(picBean.getPicurlB(),ImageAllBean.URL_IMG);
            imageDatas.add(allBean);
            if (!StringUtils.isEmpty(picBean.getPicurlS())) {
                custPhotos[i].setVisibility(View.VISIBLE);
                Picasso.with(getContext()).load(picBean.getPicurlS()).placeholder(R.drawable.abc).resize(300,261).centerCrop().into(custPhotos[i]);
            }
        }

    }

    @OnClick(R.id.iv_cust_photo_1)
    public void goViewCustomerPhoto1() {
        ViewHelper.getAllImagePager(getContext(), imageDatas, 0);
    }

    @OnClick(R.id.iv_cust_photo_2)
    public void goViewCustomerPhoto2() {
        ViewHelper.getAllImagePager(getContext(), imageDatas, 1);
    }

    @OnClick(R.id.iv_cust_photo_3)
    public void goViewCustomerPhoto3() {
        ViewHelper.getAllImagePager(getContext(), imageDatas, 2);
    }

}
