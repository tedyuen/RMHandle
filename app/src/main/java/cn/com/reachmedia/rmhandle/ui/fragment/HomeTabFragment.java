package cn.com.reachmedia.rmhandle.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.model.TaskIndexModel;
import cn.com.reachmedia.rmhandle.model.param.TaskIndexParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.TaskIndexController;
import cn.com.reachmedia.rmhandle.ui.HomeActivity;
import cn.com.reachmedia.rmhandle.ui.adapter.HomeTabFragmentAdapter;
import cn.com.reachmedia.rmhandle.ui.interf.HomeUiDataUpdate;
import cn.com.reachmedia.rmhandle.ui.view.PageListView;
import cn.com.reachmedia.rmhandle.utils.HomeFilterUtil;
import cn.com.reachmedia.rmhandle.utils.ImageCacheUtils;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import cn.com.reachmedia.rmhandle.utils.ToastHelper;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/18 下午3:30
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/18          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class HomeTabFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,PageListView.OnLoadNextListener,UiDisplayListener<TaskIndexModel> {

    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";
    public static final String LIST_TYPE = "list_type";
    private int listType = AppSpContact.SP_KEY_UNDONE;//默认未完成

    private HomeTabFragmentAdapter mAdapter;

    private TaskIndexController taskIndexController;
    private TaskIndexParam param;

    private HomeUiDataUpdate updateListener;

    HomeFilterUtil homeFilterUtil = HomeFilterUtil.getIns();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskIndexController = new TaskIndexController();
        param = new TaskIndexParam();

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

        Bundle args = getArguments();

        if (args != null ) {
            listType = args.getInt(LIST_TYPE);
        }
        taskIndexController.setUiDisplayListener(this);
        setUpViewComponent();

        return rootView;
    }



    private void setUpViewComponent() {
        mAdapter = new HomeTabFragmentAdapter((HomeActivity) getActivity());
        mPageListView.setAdapter(mAdapter);
        mPageListView.setLoadMoreEnable(false);
//        mPageListView.setLoadNextListener(this);
        mAdapter.notifyDataSetChanged();

        mSwipeContainer.setRefreshing(true);
        onRefresh();
    }


    @Override
    public void onSuccessDisplay(TaskIndexModel data) {
        if(mSwipeContainer==null) return;
        mSwipeContainer.setRefreshing(false);
        mPageListView.setState(PageListView.PageListViewState.Idle);
        if(data!=null){
            if (AppApiContact.ErrorCode.SUCCESS.equals(data.rescode)) {
                if(updateListener!=null){
//                    System.out.println("==>updateListener:  "+data.getFinish()+":"+data.getOngoing());
                    updateListener.updateTabCount(data.getOngoing(),data.getFinish());
                }

                if(listType==AppSpContact.SP_KEY_UNDONE){
                    if(ImageCacheUtils.getInstance().getCommunityIdsA()!=null){
                        ImageCacheUtils.getInstance().getCommunityIdsA().clear();
                    }
                }else if(listType==AppSpContact.SP_KEY_DONE){
                    if(ImageCacheUtils.getInstance().getCommunityIdsB()!=null) {
                        ImageCacheUtils.getInstance().getCommunityIdsB().clear();
                    }
                }
                for(TaskIndexModel.PListBean pListBean:data.getPList()){
                    ImageCacheUtils.getInstance().addCommunityIds(pListBean.getCommunityid(),listType);
                    for(TaskIndexModel.PListBean.CListBean cListBean:pListBean.getCList()){
                        homeFilterUtil.customers.add(cListBean.getCname());
                        homeFilterUtil.customersMap.put(cListBean.getCname(),cListBean.getCid()+"");
                        homeFilterUtil.initCacheCustomer();
                    }
                }
                mAdapter.updateData(data.getPList());
                mAdapter.notifyDataSetChanged();
            }else if(AppApiContact.ErrorCode.ERROR_LON.equals(data.rescode)){
                ToastHelper.showAlert(getActivity(),"没有定位权限无法使用，请前往设置");
            }
        }
    }

    @Override
    public void onFailDisplay(String errorMsg) {
        if(mSwipeContainer==null) return;
        mSwipeContainer.setRefreshing(false);
        mPageListView.setState(PageListView.PageListViewState.Idle);
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    public void onRefresh() {
        if(taskIndexController!=null){
            mPageListView.setState(PageListView.PageListViewState.Loading);
            param.state = listType;
            param.startime = homeFilterUtil.startTime;
            param.endtime = homeFilterUtil.endTime;
            param.space = homeFilterUtil.getAreaId();
            param.lon = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LONGITUDE);
            param.lat = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LATITUDE);
            param.customer = homeFilterUtil.getCustomerId();
            taskIndexController.getTaskIndex(param);
        }
    }
    @Override
    public void onLoadNext() {

    }

    public void setUiUpdateListener(HomeUiDataUpdate updateListener){
        this.updateListener = updateListener;
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }
}
