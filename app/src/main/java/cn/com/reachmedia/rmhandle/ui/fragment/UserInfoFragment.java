package cn.com.reachmedia.rmhandle.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.app.AppUpdateManager;
import cn.com.reachmedia.rmhandle.db.utils.PointWorkBeanDbUtil;
import cn.com.reachmedia.rmhandle.network.AppNetworkInfo;
import cn.com.reachmedia.rmhandle.ui.HomeActivity;
import cn.com.reachmedia.rmhandle.ui.LoginActivity;
import cn.com.reachmedia.rmhandle.ui.OfflineMapActivity;
import cn.com.reachmedia.rmhandle.ui.SynchronizeActivity;
import cn.com.reachmedia.rmhandle.ui.base.BaseToolbarFragment;
import cn.com.reachmedia.rmhandle.utils.AppVersionHelper;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import de.hdodenhof.circleimageview.CircleImageView;

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
    @Bind(R.id.iv_head_portrait)
    CircleImageView ivHeadPortrait;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.tv_userinfo)
    TextView tvUserinfo;
    @Bind(R.id.tv_shangchuan)
    TextView tvShangchuan;
    @Bind(R.id.tv_offline_map)
    TextView tvOfflineMap;
    @Bind(R.id.tv_gengxin)
    TextView tvGengxin;

    private PointWorkBeanDbUtil pointWorkBeanDbUtil;


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
        pointWorkBeanDbUtil = PointWorkBeanDbUtil.getIns();

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


    private void updateInfo(){
        String avatarUrl = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_PIC_URL);
        System.out.println(avatarUrl);
//        avatarUrl = "http://120.26.65.65:8085/img/res/images/advsales/admin/scheduling/282/s_20160601133823133.jpg";
        if(!StringUtils.isEmpty(avatarUrl)){
            Picasso.with(getContext()).load(avatarUrl).placeholder(R.mipmap.default_avatar).into(ivHeadPortrait);
        }
        tvUsername.setText(mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_USER_NAME));
        tvUserinfo.setText("分部:"+mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_SPACE));

        tvShangchuan.setText(pointWorkBeanDbUtil.getUnSynchronize()+"个未同步");

        try {
            tvGengxin.setText("v"+ AppVersionHelper.getVersionName(getContext())+" ");
        } catch (Exception e) {
            tvGengxin.setText("");
            e.printStackTrace();
        }

    }


    @OnClick(R.id.ll_bottom_1)
    public void goUserInfoActivity() {
        startActivity(new Intent(getActivity(), HomeActivity.class));
        getActivity().overridePendingTransition(0, 0);
    }

    @OnClick(R.id.rl_offline_map)
    public void goOfflineMapActivity() {
        startActivity(new Intent(getActivity(), OfflineMapActivity.class));
    }

    @OnClick(R.id.bt_logout)
    public void logout() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title("确定要退出吗?")
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mSharedPreferencesHelper.remove(AppSpContact.SP_KEY_TOKEN);
                        mSharedPreferencesHelper.remove(AppSpContact.SP_KEY_INDEX_COMMUNITID);
                        mSharedPreferencesHelper.remove(AppSpContact.SP_KEY_INDEX_COMMUNITNAME);
                        mSharedPreferencesHelper.remove(AppSpContact.SP_KEY_INDEX_STARTTIME);
                        mSharedPreferencesHelper.remove(AppSpContact.SP_KEY_INDEX_ENDTIME);
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


    //上传同步列表
    @OnClick(R.id.rl_shangchuan)
    public void synchronizeList(){
        startActivity(new Intent(getActivity(), SynchronizeActivity.class));

    }


    //检查更新
    @OnClick(R.id.rl_gengxin)
    public void checkUpdate(){
        AppUpdateManager mAppUpdateManager = new AppUpdateManager(getActivity());
        mAppUpdateManager.setShowMessage(true);
        mAppUpdateManager.checkUpdateInfo(getActivity());
    }


    @Override
    public void onResume() {
        super.onResume();
        updateInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

//    @OnClick(R.id.iv_head_portrait)
//    public void testWifi(){
//        System.out.println("wifi: "+AppNetworkInfo.isWifi(getContext()));
//        System.out.println("network: "+AppNetworkInfo.isNetworkAvailable(getContext()));
//    }
}
