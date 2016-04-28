package cn.com.reachmedia.rmhandle.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.ui.OfflineMapActivity;
import cn.com.reachmedia.rmhandle.ui.TaskInforActivity;
import cn.com.reachmedia.rmhandle.ui.adapter.CustomerPhotoTabAdapter;
import cn.com.reachmedia.rmhandle.ui.view.PageListView;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/28 上午11:41
 * Description: 客户及拍照要求
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/28          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class CustomerPhotoTabFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";

    TaskInforActivity activity;

    @Bind(R.id.swipe_container)
    protected SwipeRefreshLayout mSwipeContainer;
    @Bind(R.id.page_list_view)
    protected PageListView mPageListView;

    CustomerPhotoTabAdapter mAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_customer_photo_list, container, false);
        ButterKnife.bind(this, rootView);
        activity = (TaskInforActivity)getActivity();
        //设置下拉刷新小球的颜色
        mSwipeContainer.setOnRefreshListener(this);
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //设置下拉刷新小球的颜色
        Bundle args = getArguments();

        setUpViewComponent();

        return rootView;
    }

    private void setUpViewComponent() {
        mAdapter = new CustomerPhotoTabAdapter(getActivity());
        mPageListView.setAdapter(mAdapter);
//        mPageListView.setLoadMoreEnable(true);
//        mPageListView.setLoadNextListener(this);
        mAdapter.notifyDataSetChanged();
//        tv_empty_text.setText("订单暂无数据");
    }

    @Override
    public void onRefresh() {

    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }
}
