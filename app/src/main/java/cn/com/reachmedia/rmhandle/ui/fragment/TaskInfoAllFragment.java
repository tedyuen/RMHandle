package cn.com.reachmedia.rmhandle.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.model.TaskDetailModel;
import cn.com.reachmedia.rmhandle.ui.TaskInfoAllActivity;
import cn.com.reachmedia.rmhandle.ui.adapter.ApartmentInfoTabAdapter;
import cn.com.reachmedia.rmhandle.ui.base.BaseToolbarFragment;
import cn.com.reachmedia.rmhandle.ui.view.PageListView;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/16 下午4:23
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/16          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class TaskInfoAllFragment extends BaseToolbarFragment {

    @Bind(R.id.page_list_view)
    protected PageListView mPageListView;

    TaskInfoAllActivity allActivity;

    ApartmentInfoTabAdapter mAdapter;

    public static TaskDetailModel.CrListBean data;

    public static TaskInfoAllFragment newInstance() {
        TaskInfoAllFragment fragment = new TaskInfoAllFragment();
        Bundle args = new Bundle();
//        args.putParcelable(AppParamContact.PARAM_KEY_MODEL, model);
        fragment.setArguments(args);
        return fragment;
    }

    public TaskInfoAllFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.task_info_all_list, container, false);
        ButterKnife.bind(this, rootView);
        ButterKnife.bind(this, rootView);
        allActivity = (TaskInfoAllActivity)getActivity();
        setUpViewComponent();
        needTitle();
        setTitle(data.getCname());
        return rootView;
    }

    private void setUpViewComponent() {
        List<TaskDetailModel.CrListBean> mLists = new ArrayList<>();
        mLists.add(data);
        mAdapter = new ApartmentInfoTabAdapter(getActivity());
        mPageListView.setAdapter(mAdapter);
//        mPageListView.setLoadMoreEnable(true);
//        mPageListView.setLoadNextListener(this);
        mAdapter.updateData(mLists,true);
        mAdapter.notifyDataSetChanged();
//        tv_empty_text.setText("订单暂无数据");
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    @Override
    protected int getLayoutResId() {
        return 0;
    }
}
