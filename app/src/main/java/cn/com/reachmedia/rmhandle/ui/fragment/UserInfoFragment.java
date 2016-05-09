package cn.com.reachmedia.rmhandle.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.ui.HomeActivity;
import cn.com.reachmedia.rmhandle.ui.LoginActivity;
import cn.com.reachmedia.rmhandle.ui.OfflineMapActivity;
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
        return 0;
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
        View rootView = inflater.inflate(R.layout.userinfo_fragment, container, false);
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

    @OnClick(R.id.rl_offline_map)
    public void goOfflineMapActivity(){
        startActivity(new Intent(getActivity(),OfflineMapActivity.class));
    }

    @OnClick(R.id.bt_logout)
    public void logout(){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title("确定要退出吗?")
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mSharedPreferencesHelper.remove(AppSpContact.SP_KEY_TOKEN);
                        App.getIns().closeHomeActivity();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        dialog.dismiss();
                        getActivity().finish();
                    }
                })
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });
        MaterialDialog dialog = builder.build();
        dialog.show();

    }


}
