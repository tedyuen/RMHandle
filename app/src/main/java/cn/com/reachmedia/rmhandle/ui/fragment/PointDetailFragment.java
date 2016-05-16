package cn.com.reachmedia.rmhandle.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppParamContact;
import cn.com.reachmedia.rmhandle.bean.PointBean;
import cn.com.reachmedia.rmhandle.db.utils.PointBeanDbUtil;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.ui.base.BaseToolbarFragment;
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

    public static PointDetailFragment newInstance(){
        PointDetailFragment fragment = new PointDetailFragment();
        Bundle args = new Bundle();
//        args.putParcelable(AppParamContact.PARAM_KEY_MODEL, model);
        fragment.setArguments(args);
        return fragment;
    }
    public PointDetailFragment(){}

    private ApartmentPointUtils apartmentPointUtils;
    private PointBeanDbUtil pointBeanDbUtil;
    private PointBean bean;
    private PointListModel pointListModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apartmentPointUtils = ApartmentPointUtils.getIns();
        pointBeanDbUtil = PointBeanDbUtil.getIns();
        bean = pointBeanDbUtil.getPointBeanByWPID(apartmentPointUtils.workId,apartmentPointUtils.pointId);
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
        try{
            int tempName = Integer.parseInt(bean.getDoor());
            setTitle(tempName+"号楼点位");
        }catch (Exception e){
            setTitle(bean.getDoor()+"点位");
        }

    }



    @Override
    protected int getLayoutResId() {
        return 0;
    }
}
