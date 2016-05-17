package cn.com.reachmedia.rmhandle.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.bean.PointBean;
import cn.com.reachmedia.rmhandle.db.utils.PointBeanDbUtil;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.ui.base.BaseToolbarFragment;
import cn.com.reachmedia.rmhandle.ui.view.ProportionImageView;
import cn.com.reachmedia.rmhandle.utils.ApartmentPointUtils;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/29 上午10:56
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/29          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PointDetailFragment extends BaseToolbarFragment {


    @Bind(R.id.iv_point_photo_1)
    ProportionImageView ivPointPhoto1;
    @Bind(R.id.iv_point_photo_2)
    ProportionImageView ivPointPhoto2;
    @Bind(R.id.iv_point_photo_3)
    ProportionImageView ivPointPhoto3;
    @Bind(R.id.iv_comm_photo_1)
    ProportionImageView ivCommPhoto1;
    @Bind(R.id.rl_comm_photo_1)
    RelativeLayout rlCommPhoto1;
    @Bind(R.id.iv_comm_photo_2)
    ProportionImageView ivCommPhoto2;
    @Bind(R.id.rl_comm_photo_2)
    RelativeLayout rlCommPhoto2;
    @Bind(R.id.iv_comm_photo_3)
    ProportionImageView ivCommPhoto3;
    @Bind(R.id.rl_comm_photo_3)
    RelativeLayout rlCommPhoto3;
    @Bind(R.id.tv_cname)
    TextView tvCname;
    @Bind(R.id.iv_cust_photo_1)
    ProportionImageView ivCustPhoto1;
    @Bind(R.id.iv_cust_photo_2)
    ProportionImageView ivCustPhoto2;
    @Bind(R.id.iv_cust_photo_3)
    ProportionImageView ivCustPhoto3;
    @Bind(R.id.tv_action_time)
    TextView tvActionTime;
    @Bind(R.id.wb_memo)
    WebView wbMemo;
    @Bind(R.id.bt_done)
    Button btDone;
    @Bind(R.id.bt_cant_enter)
    Button btCantEnter;
    @Bind(R.id.bt_report_question)
    Button btReportQuestion;
    @Bind(R.id.iv_left_text)
    TextView ivLeftText;
    @Bind(R.id.rl_left_text)
    RelativeLayout rlLeftText;

    public static PointDetailFragment newInstance() {
        PointDetailFragment fragment = new PointDetailFragment();
        Bundle args = new Bundle();
//        args.putParcelable(AppParamContact.PARAM_KEY_MODEL, model);
        fragment.setArguments(args);
        return fragment;
    }

    public PointDetailFragment() {
    }

    private ApartmentPointUtils apartmentPointUtils;
    private PointBeanDbUtil pointBeanDbUtil;
    private PointBean bean;
    private PointListModel pointListModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apartmentPointUtils = ApartmentPointUtils.getIns();
        pointBeanDbUtil = PointBeanDbUtil.getIns();
        bean = pointBeanDbUtil.getPointBeanByWPID(apartmentPointUtils.workId, apartmentPointUtils.pointId);
        pointListModel = apartmentPointUtils.pointListModel;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_point_detail, container, false);
        ButterKnife.bind(this, rootView);
        needTitle();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            int tempName = Integer.parseInt(bean.getDoor());
            setTitle(tempName + "号楼" + (bean.getGround() == 0 ? "地下" : "地上") + "点位");
        } catch (Exception e) {
            setTitle(bean.getDoor() + (bean.getGround() == 0 ? "地下" : "地上") + "点位");
        }

        String cid = bean.getCid();


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
