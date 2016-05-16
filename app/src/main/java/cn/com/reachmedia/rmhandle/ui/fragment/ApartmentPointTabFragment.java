package cn.com.reachmedia.rmhandle.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppParamContact;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.ui.ApartmentPointActivity;
import cn.com.reachmedia.rmhandle.ui.adapter.ApartmentPointTabBaseAdapter;
import cn.com.reachmedia.rmhandle.ui.adapter.ApartmentPointTabFragmentAdapter;
import cn.com.reachmedia.rmhandle.ui.adapter.ApartmentPointTabFragmentAdapter2;
import cn.com.reachmedia.rmhandle.ui.adapter.ApartmentPointTabFragmentAdapter3;
import cn.com.reachmedia.rmhandle.ui.view.PageListView;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/20 下午5:37
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/20          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class ApartmentPointTabFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";
    public static final String LIST_TYPE = "list_type";
    private int listType = AppSpContact.SP_KEY_APAET_POINT_UNDONE;//默认未完成

    private String communityid;

    private ApartmentPointTabBaseAdapter mAdapter;

    private ApartmentPointActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        activity = (ApartmentPointActivity)getActivity();
        if (args != null ) {
            int temp = args.getInt(ApartmentPointTabFragment.LIST_TYPE);
            if(temp!=0){
                listType = args.getInt(ApartmentPointTabFragment.LIST_TYPE);
            }
            String tempCommunityid = args.getString(AppParamContact.PARAM_KEY_ID);
            if(tempCommunityid!=null){
                communityid = tempCommunityid;
            }
            System.out.println("=====>!!  "+listType);
            System.out.println("=====>!!  "+tempCommunityid);
        }
    }

    @Bind(R.id.swipe_container)
    protected SwipeRefreshLayout mSwipeContainer;
    @Bind(R.id.page_list_view)
    protected PageListView mPageListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_tab, container, false);
        ButterKnife.bind(this, rootView);
        //设置下拉刷新小球的颜色
        mSwipeContainer.setOnRefreshListener(this);
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //设置下拉刷新小球的颜色

//        Bundle args = getArguments();
//
//        if (args != null ) {
//            listType = args.getInt(ApartmentPointTabFragment.LIST_TYPE);
//        }
        setUpViewComponent();

        return rootView;
    }



    private void setUpViewComponent() {
        System.out.println("listType:  "+listType);
        switch (listType){
            case 1:
                mAdapter = new ApartmentPointTabFragmentAdapter(getActivity());

                break;
            case 2:
                mAdapter = new ApartmentPointTabFragmentAdapter2(getActivity());

                break;
            case 3:
                mAdapter = new ApartmentPointTabFragmentAdapter3(getActivity());

                break;
        }
        mPageListView.setAdapter(mAdapter);
//        mPageListView.setLoadMoreEnable(true);
//        mPageListView.setLoadNextListener(this);
        mAdapter.notifyDataSetChanged();
//        tv_empty_text.setText("订单暂无数据");
    }



    @Override
    public void onRefresh() {
        activity.onRefresh();
    }

    public void onUpdateData(String workId,String pointId){

    }



    @Override
    protected int getLayoutResId() {
        return 0;
    }
}
