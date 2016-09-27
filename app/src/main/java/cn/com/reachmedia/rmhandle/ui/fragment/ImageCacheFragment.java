package cn.com.reachmedia.rmhandle.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.model.param.PointListParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.PointListController;
import cn.com.reachmedia.rmhandle.ui.base.BaseToolbarFragment;
import cn.com.reachmedia.rmhandle.ui.bean.ImageCacheResBean;
import cn.com.reachmedia.rmhandle.utils.ImageCacheUtils;
import cn.com.reachmedia.rmhandle.utils.StringUtils;

/**
 * Created by tedyuen on 16-9-26.
 */
public class ImageCacheFragment extends BaseToolbarFragment {

    @Bind(R.id.tv_detail)
    TextView tvDetail;
    @Bind(R.id.tv_community_count)
    TextView tvCommunityCount;
    @Bind(R.id.rl_right_text)
    RelativeLayout rlRightText;
    @Bind(R.id.iv_info)
    ImageView ivInfo;
    @Bind(R.id.rl_right_img)
    RelativeLayout rlRightImg;
    @Bind(R.id.iv_top_shadow)
    ImageView ivTopShadow;
    @Bind(R.id.tv_detail2)
    TextView tvDetail2;
    @Bind(R.id.tv_image_count)
    TextView tvImageCount;
    private ImageCacheUtils imageCacheUtils;

    public static ImageCacheFragment newInstance() {
        ImageCacheFragment fragment = new ImageCacheFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ImageCacheFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.image_cache_fragment, container, false);
        ButterKnife.bind(this, rootView);
        needTitle();
        imageCacheUtils = ImageCacheUtils.getInstance();
        imageCacheUtils.mergeCommunityAB();//合并小区数据
        totalCommunityCount = imageCacheUtils.getCommunityIds().size();
        if (imageCacheUtils.getCommunityIds() != null && imageCacheUtils.getCommunityIds().size() > 0) {
            imageCacheUtils.getImageCacheResBeens().clear();//清除图片数据
            getPointData();
        }
        return rootView;
    }

    private int totalCommunityCount;

    private void setCommunityCount() {
        int tempCount = totalCommunityCount - imageCacheUtils.getCommunityIds().size();
        tvCommunityCount.setText("小区: " + tempCount + "/" + totalCommunityCount);
    }

    public void getPointData() {
        if (getActivity() == null) return;
        setCommunityCount();
        if (imageCacheUtils.getCommunityIds() != null && imageCacheUtils.getCommunityIds().size() > 0) {

            String communityId = imageCacheUtils.getCommunityIds().get(0);
            final String[] initData = new String[]{mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_INDEX_STARTTIME), communityId};

            PointListController pointListController = new PointListController(new UiDisplayListener<PointListModel>() {
                @Override
                public void onSuccessDisplay(PointListModel data) {
                    if (data != null) {
                        if (AppApiContact.ErrorCode.SUCCESS.equals(data.rescode)) {
                            String[] gate = data.getCGatePics().split("@&");
                            String[] pest = data.getCPestPics().split("@&");
                            for (int i = 0; i < gate.length; i++) {
                                if (!StringUtils.isEmpty(gate[i])) {
                                    ImageCacheResBean tempBean = new ImageCacheResBean(initData);
                                    tempBean.setUrl(gate[i]);
                                    imageCacheUtils.addPointBean(tempBean);
                                }
                            }
                            for (int i = 0; i < pest.length; i++) {
                                if (!StringUtils.isEmpty(pest[i])) {
                                    ImageCacheResBean tempBean = new ImageCacheResBean(initData);
                                    tempBean.setUrl(pest[i]);
                                    imageCacheUtils.addPointBean(tempBean);
                                }
                            }
                            List<PointListModel.NewListBean> tempList = data.getNewList();
                            for (PointListModel.NewListBean tempBean : tempList) {
                                if (!StringUtils.isEmpty(tempBean.getCDoorPic())) {
                                    ImageCacheResBean tempBean2 = new ImageCacheResBean(initData);
                                    tempBean2.setUrl(tempBean.getCDoorPic());
                                    imageCacheUtils.addPointBean(tempBean2);
                                }
                                List<PointListModel.NewListBean.PFileListBean> fileBeans = tempBean.getpFileList();
                                for (PointListModel.NewListBean.PFileListBean fileBean : fileBeans) {
                                    if (!StringUtils.isEmpty(fileBean.getFileUrlS())) {
                                        ImageCacheResBean fileBean2 = new ImageCacheResBean(initData);
                                        fileBean2.setUrl(fileBean.getFileUrlS());
                                        imageCacheUtils.addPointBean(fileBean2);
                                    }
                                }
                            }
                        }
                    }
                    if (imageCacheUtils.getCommunityIds() != null && imageCacheUtils.getCommunityIds().size() > 0) {
                        imageCacheUtils.getCommunityIds().remove(0);
                    }
                    getPointData();
                }

                @Override
                public void onFailDisplay(String errorMsg) {
                    if (imageCacheUtils.getCommunityIds() != null && imageCacheUtils.getCommunityIds().size() > 0) {
                        imageCacheUtils.getCommunityIds().remove(0);
                    }
                    getPointData();
                }
            });
            PointListParam param = new PointListParam();
            param.communityid = communityId;
            param.startime = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_INDEX_STARTTIME);
            param.endtime = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_INDEX_ENDTIME);
            param.space = "";
            param.customer = "";
            pointListController.getTaskIndex(param);

        } else {//点位数据加载完毕
            tvDetail2.setText("小区数据加载完毕,总共" + imageCacheUtils.getImageCacheResBeens().size() + "张图片需要缓存");
            tvDetail.setText("正在下载图片...");
            tvImageCount.setVisibility(View.VISIBLE);



        }


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
}
