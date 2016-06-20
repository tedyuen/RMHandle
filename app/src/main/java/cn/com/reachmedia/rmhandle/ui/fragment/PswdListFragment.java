package cn.com.reachmedia.rmhandle.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.model.CardListModel;
import cn.com.reachmedia.rmhandle.ui.CardListActivity;
import cn.com.reachmedia.rmhandle.ui.adapter.CardListAdapter;
import cn.com.reachmedia.rmhandle.ui.adapter.PswdListAdapter;
import cn.com.reachmedia.rmhandle.ui.view.PageListView;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/20 上午10:59
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/20          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PswdListFragment extends CardListAbFragment implements SwipeRefreshLayout.OnRefreshListener{

    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";

    CardListActivity activity;

    @Bind(R.id.swipe_container)
    protected SwipeRefreshLayout mSwipeContainer;
    @Bind(R.id.page_list_view)
    protected PageListView mPageListView;

    PswdListAdapter mAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_customer_photo_list, container, false);
        ButterKnife.bind(this, rootView);
        activity = (CardListActivity)getActivity();
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
        mAdapter = new PswdListAdapter(getActivity());
        mPageListView.setAdapter(mAdapter);
//        mPageListView.setLoadMoreEnable(true);
//        mPageListView.setLoadNextListener(this);
        mAdapter.notifyDataSetChanged();
//        tv_empty_text.setText("订单暂无数据");
    }


    @Override
    public void onDataRefresh(CardListModel data) {
        if(mSwipeContainer==null) return;
        mSwipeContainer.setRefreshing(false);
        mAdapter.updateData(data.getPswdList());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        activity.onRefresh();
    }
    @Override
    protected int getLayoutResId() {
        return 0;
    }
}
