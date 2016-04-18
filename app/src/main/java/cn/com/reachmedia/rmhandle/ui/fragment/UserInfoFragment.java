package cn.com.reachmedia.rmhandle.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.ui.HomeActivity;
import cn.com.reachmedia.rmhandle.ui.base.BaseToolbarFragment;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/18 下午4:03
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/18          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class UserInfoFragment extends BaseToolbarFragment {

    @Bind(R.id.iv_bottom_1)
    ImageView mIvBottom1;

    public static UserInfoFragment newInstance() {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public UserInfoFragment() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.userinfo_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        setUpViewComponent();
        return rootView;
    }

    /**
     * 设置view
     */
    private void setUpViewComponent() {
        needTitle();
        hideBackBtn();
        mIvBottom1.setImageLevel(2);
    }


    @OnClick(R.id.ll_bottom_1)
    public void goUserInfoActivity(){
        startActivity(new Intent(getActivity(),HomeActivity.class));
        getActivity().overridePendingTransition(0, 0);
    }
}
