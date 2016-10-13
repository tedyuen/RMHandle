package cn.com.reachmedia.rmhandle.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.PointBean;
import cn.com.reachmedia.rmhandle.db.utils.PointBeanDbUtil;
import cn.com.reachmedia.rmhandle.db.utils.PointWorkBeanDbUtil;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.ui.base.BaseToolbarFragment;
import cn.com.reachmedia.rmhandle.ui.view.Line2ImageLayout;
import cn.com.reachmedia.rmhandle.ui.view.Line3ImageLayout;
import cn.com.reachmedia.rmhandle.ui.view.LineImageLayout;
import cn.com.reachmedia.rmhandle.ui.view.imagepager.ImageAllBean;
import cn.com.reachmedia.rmhandle.utils.StringUtils;

/**
 * Created by tedyuen on 16-9-19.
 */
public class NewPointDetailFragment extends BaseToolbarFragment {


    @Bind(R.id.line_image_1)
    LineImageLayout lineImage1;


    @Bind(R.id.line_image_2)
    Line2ImageLayout lineImage2;


    @Bind(R.id.tv_action_time)
    TextView tvActionTime;
    @Bind(R.id.tv_cname)
    TextView tvCname;
    @Bind(R.id.wb_memo)
    WebView wbMemo;
    @Bind(R.id.ll_cust_photo)
    Line3ImageLayout ll_cust_photo;


    @Bind(R.id.rb_check_1)
    RadioButton rbCheck1;
    @Bind(R.id.rb_check_2)
    RadioButton rbCheck2;


    public static NewPointDetailFragment newInstance() {
        NewPointDetailFragment fragment = new NewPointDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NewPointDetailFragment() {
    }


    private PointBeanDbUtil pointBeanDbUtil;
    private PointWorkBeanDbUtil pointWorkBeanDbUtil;
    private PointListModel pointListModel;//缓存列表数据
    private PointBean bean;//网络数据


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pointBeanDbUtil = PointBeanDbUtil.getIns();
        pointWorkBeanDbUtil = PointWorkBeanDbUtil.getIns();
        String communityId = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_INDEX_COMMUNITID);
        String tempStartTime = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_INDEX_STARTTIME);
        String dataJson = mSharedPreferencesHelper.getString(communityId+"_"+tempStartTime);
        String workId = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_WORK_ID);
        String pointId = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_POINT_ID);
        if(!StringUtils.isEmpty(dataJson)) {
            Gson gson = new Gson();
            try {
                pointListModel = gson.fromJson(dataJson, PointListModel.class);
            } catch (Exception e) {
                e.printStackTrace();
                getActivity().finish();
            }
        }else {
            getActivity().finish();
        }
        bean = pointBeanDbUtil.getPointBeanByWPID(workId, pointId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_fragment_point_detail, container, false);
        ButterKnife.bind(this, rootView);
        needTitle();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //初始化小区门口及环境照


        //设置页面标题
        try {
            int tempName = Integer.parseInt(bean.getDoor());
            setTitle(tempName + "号楼" + (bean.getGround() == 0 ? "地下" : "地上") + "点位");
        } catch (Exception e) {
            setTitle(bean.getDoor() + (bean.getGround() == 0 ? "地下" : "地上") + "点位");
        }
        tvActionTime.setText("到画时间：" + bean.getPictime() + "    上画时间：" + bean.getWorktime());
        //客户文字和照片
        String cid = bean.getCid().trim();
        for (PointListModel.ComListBean comBean : pointListModel.getComList()) {
            if (comBean.getCid().trim().equals(cid)) {
                tvCname.setText(comBean.getCname());
                wbMemo.getSettings().setDefaultTextEncodingName("utf-8");
                wbMemo.loadDataWithBaseURL("", comBean.getMemo(), "text/html", "utf-8", "");
                if (comBean.getPicList() == null || comBean.getPicList().size() == 0) {
                    ll_cust_photo.setVisibility(View.GONE);
                } else {
                    if(comBean.getPicList()!=null){
                        ll_cust_photo.setCusImg(comBean.getPicList());
                    }
                }
                break;
            }
        }

        //巡检状态
        if(bean.getCheckState()==1){
            rbCheck1.setChecked(true);
            rbCheck1.setVisibility(View.VISIBLE);
            rbCheck2.setVisibility(View.GONE);
        }else {
            rbCheck2.setChecked(true);
            rbCheck2.setVisibility(View.VISIBLE);
            rbCheck1.setVisibility(View.GONE);
        }

        //门洞照显示
        if (!StringUtils.isEmpty(bean.getCDoorPic())) {
            ImageAllBean doorPicBean = new ImageAllBean(bean.getCDoorPic(),ImageAllBean.URL_IMG);
            lineImage2.setDoorPic(doorPicBean);
        }

        if(pointListModel!=null){
            lineImage2.setCommunityPhoto(pointListModel);
        }

        lineImage1.updateAddPhotosClickState(getActivity(),savedInstanceState);

    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        lineImage1.onActivityResult(requestCode,resultCode,data);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        lineImage1.onSaveInstanceState(savedInstanceState);
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
