package cn.com.reachmedia.rmhandle.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.PointWorkBean;
import cn.com.reachmedia.rmhandle.db.utils.PointWorkBeanDbUtil;
import cn.com.reachmedia.rmhandle.ui.UnUploadPointListActivity;
import cn.com.reachmedia.rmhandle.ui.adapter.UnUploadPointListAdapter;
import cn.com.reachmedia.rmhandle.ui.view.PageListView;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;
import cn.com.reachmedia.rmhandle.utils.StringUtils;

/**
 * Created by tedyuen on 16-12-14.
 */
public class UnUploadPointListFragment extends BaseFragment {


    UnUploadPointListActivity activity;
    @Bind(R.id.page_list_view)
    PageListView pageListView;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;

    private String communityId;
    private String communityName;
    private PointWorkBeanDbUtil pointWorkBeanDbUtil;

    private UnUploadPointListAdapter mAdapter;

    public static UnUploadPointListFragment newInstance() {
        return new UnUploadPointListFragment();
    }

    public UnUploadPointListFragment() {
        activity = (UnUploadPointListActivity) getActivity();
        communityId = SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_UNUP_COMID);
        communityName = SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_UNUP_COMNAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_unupload_point_list, container, false);
        ButterKnife.bind(this, rootView);
        toolbarTitle.setText(communityName);
        if (!StringUtils.isEmpty(communityId)) {
            pointWorkBeanDbUtil = PointWorkBeanDbUtil.getIns();
            List<PointWorkBean> list = pointWorkBeanDbUtil.getUnUploadDateByComid(communityId);
            mAdapter = new UnUploadPointListAdapter(getActivity(),list);
            pageListView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }

        return rootView;
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
