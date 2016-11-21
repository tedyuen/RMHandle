package cn.com.reachmedia.rmhandle.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.app.AppUpdateManager;
import cn.com.reachmedia.rmhandle.db.helper.ImageCacheDaoHelper;
import cn.com.reachmedia.rmhandle.db.utils.PointWorkBeanDbUtil;
import cn.com.reachmedia.rmhandle.network.AppNetworkInfo;
import cn.com.reachmedia.rmhandle.service.ServiceHelper;
import cn.com.reachmedia.rmhandle.ui.HomeActivity;
import cn.com.reachmedia.rmhandle.ui.LoginActivity;
import cn.com.reachmedia.rmhandle.ui.OfflineMapActivity;
import cn.com.reachmedia.rmhandle.ui.SynchronizeActivity;
import cn.com.reachmedia.rmhandle.ui.base.BaseToolbarFragment;
import cn.com.reachmedia.rmhandle.utils.AppVersionHelper;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import cn.com.reachmedia.rmhandle.utils.ToastHelper;
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
    @Bind(R.id.tv_clear_image_cache)
    TextView tvClearImageCache;

    @Bind(R.id.tv_offline_map)
    TextView tvOfflineMap;
    @Bind(R.id.tv_gengxin)
    TextView tvGengxin;

    @Bind(R.id.tb_water_mark_switch)
    ToggleButton tbWaterMarkSwitch;

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

        IntentFilter filter = new IntentFilter();
        filter.addAction("POINT_FINISHED_MSG");
        getActivity().registerReceiver(pointSynchronizeReceiver,filter);
        tbWaterMarkSwitch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mSharedPreferencesHelper.putBoolean(AppSpContact.SP_KEY_WATER_MARK_SWITCH,tbWaterMarkSwitch.isChecked());
            }
        });
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

        tbWaterMarkSwitch.setChecked(mSharedPreferencesHelper.getBoolean(AppSpContact.SP_KEY_WATER_MARK_SWITCH,false));

        getShangchuan();

        try {
            tvGengxin.setText("v"+ AppVersionHelper.getVersionName(getContext())+" ");
        } catch (Exception e) {
            tvGengxin.setText("");
            e.printStackTrace();
        }

    }

    public void getShangchuan(){
        tvShangchuan.setText(pointWorkBeanDbUtil.getUnSynchronize()+"个未同步");
    }

    private BroadcastReceiver pointSynchronizeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("POINT_FINISHED_MSG")){
                String msg = intent.getStringExtra("msg");
                System.out.println("point synchronize receiver:"+msg);
                getShangchuan();
            }
        }
    };


    @OnClick(R.id.ll_bottom_1)
    public void goUserInfoActivity() {
        startActivity(new Intent(getActivity(), HomeActivity.class));
        getActivity().overridePendingTransition(0, 0);
    }

    @OnClick(R.id.rl_offline_map)
    public void goOfflineMapActivity() {
        startActivity(new Intent(getActivity(), OfflineMapActivity.class));
    }

    @OnClick(R.id.rl_clear_point_image)
    public void clearPointImage(){
        new MaterialDialog.Builder(getContext())
                .title(R.string.dialog_title_synchronize_clean)
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .positiveText("确定清空")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ServiceHelper.getIns().startRemoveDoneFileService(getContext());
                        ToastHelper.showInfo(getActivity(), "清空图片任务正在后台运行...");
                        dialog.dismiss();
                    }
                })
                .show();
    }


    public String allCacheSize="0Kb";
    public int allFileCount;

    @OnClick(R.id.rl_clear_image_cache)
    public void clearImageCache(){
        new MaterialDialog.Builder(getContext())
                .title(R.string.dialog_title_clear_cache)
                .content("所有图片缓存已经占用"+allCacheSize+"空间。\n共"+allFileCount+"个图片文件。")
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ClearCacheTask clearCacheTask = new ClearCacheTask();
                        clearCacheTask.execute();
                        dialog.dismiss();
                    }
                })
                .show();

    }

    public class ClearCacheTask extends AsyncTask<Void,Integer,Boolean> {

        private ImageCacheDaoHelper imageCacheDaoHelper;

        @Override
        protected void onPreExecute() {
            imageCacheDaoHelper = ImageCacheDaoHelper.getInstance();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                imageCacheDaoHelper.deleteAll();
                deleteFile(new File(ImageCacheFragment.path));
                return true;
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean && getActivity()!=null){
                getAllCacheSize();
                ToastHelper.showInfo(getActivity(),"缓存清空成功");
//                tv_clear_cache_result.setText("缓存清空成功");
            }else{
                ToastHelper.showAlert(getActivity(),"缓存清空失败");
//                tv_clear_cache_result.setText("缓存清空失败");
            }
        }
    }

    public String getAllCacheSize(){
        allFileCount = 0;
        allCacheSize = ImageCacheFragment.getDataContent(getDirSize(new File(ImageCacheFragment.path)));
        tvClearImageCache.setText(allCacheSize);
        return allCacheSize;
    }

    private double getDirSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                for (File f : children)
                    size += getDirSize(f);
                return size;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                allFileCount++;
                double size = (double) file.length();
                return size;
            }
        } else {
//            System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
            return 0.0;
        }
    }

    private void deleteFile(File file) {
        if (file.exists()) {//判断文件是否存在
            if (file.isFile()) {//判断是否是文件
                file.delete();//删除文件
            } else if (file.isDirectory()) {//否则如果它是一个目录
                File[] files = file.listFiles();//声明目录下所有的文件 files[];
                for (int i = 0;i < files.length;i ++) {//遍历目录下所有的文件
                    this.deleteFile(files[i]);//把每个文件用这个方法进行迭代
                }
                file.delete();//删除文件夹
            }
        } else {
            System.out.println("所删除的文件不存在");
        }
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

//        CrashReport.testJavaCrash();
    }


    @Override
    public void onResume() {
        super.onResume();
        updateInfo();

        getAllCacheSize();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if(pointSynchronizeReceiver!=null){
            getActivity().unregisterReceiver(pointSynchronizeReceiver);
        }
    }

//    @OnClick(R.id.iv_head_portrait)
//    public void testWifi(){
//        System.out.println("wifi: "+AppNetworkInfo.isWifi(getContext()));
//        System.out.println("network: "+AppNetworkInfo.isNetworkAvailable(getContext()));
//    }
}
