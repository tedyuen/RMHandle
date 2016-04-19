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
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.ui.adapter.HomeTabFragmentAdapter;
import cn.com.reachmedia.rmhandle.ui.view.PageListView;

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
public class HomeTabFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,PageListView.OnLoadNextListener{

    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";
    public static final String LIST_TYPE = "list_type";
    private int listType = AppSpContact.SP_KEY_UNDONE;//默认未完成

    private HomeTabFragmentAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        controller = new MyOrderListController(this);
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
        setUpViewComponent();

        return rootView;
    }



    private void setUpViewComponent() {
        mAdapter = new HomeTabFragmentAdapter(getActivity());
        mPageListView.setAdapter(mAdapter);
//        mPageListView.setLoadMoreEnable(true);
//        mPageListView.setLoadNextListener(this);
        mAdapter.notifyDataSetChanged();
//        tv_empty_text.setText("订单暂无数据");
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    public void onRefresh() {

    }
    @Override
    public void onLoadNext() {

    }
}
