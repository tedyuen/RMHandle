package cn.com.reachmedia.rmhandle.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.github.ksoichiro.android.observablescrollview.CacheFragmentStatePagerAdapter;
import com.google.gson.Gson;
import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.app.AppParamContact;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.PointBean;
import cn.com.reachmedia.rmhandle.db.helper.PointBeanDaoHelper;
import cn.com.reachmedia.rmhandle.db.utils.PointBeanDbUtil;
import cn.com.reachmedia.rmhandle.db.utils.PointWorkBeanDbUtil;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.model.param.PointListParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.PointListController;
import cn.com.reachmedia.rmhandle.ui.base.BaseActionBarActivity;
import cn.com.reachmedia.rmhandle.ui.base.BaseActionBarTabActivity;
import cn.com.reachmedia.rmhandle.ui.dialog.ApartmentPhoneDialogFragment;
import cn.com.reachmedia.rmhandle.ui.fragment.ApartmentPointTabFragment;
import cn.com.reachmedia.rmhandle.utils.ApartmentPointUtils;
import cn.com.reachmedia.rmhandle.utils.HomeFilterUtil;
import cn.com.reachmedia.rmhandle.utils.LogUtils;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import cn.com.reachmedia.rmhandle.utils.ToastHelper;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/20 下午5:36
 * Description: 小区点位列表
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/20          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class ApartmentPointActivity extends BaseActionBarTabActivity implements UiDisplayListener<PointListModel> {

    private final ThreadLocal<View> mToolbarView = new ThreadLocal<>();
    SlidingTabLayout slidingTabLayout;
    @Bind(R.id.bt_clear)
    Button btClear;
    @Bind(R.id.bt_edit_save)
    Button btEditSave;
    @Bind(R.id.bt_edit_record)
    Button btEditRecord;
    @Bind(R.id.ll_password_frame)
    LinearLayout llPasswordFrame;
    @Bind(R.id.fragment_frame)
    FrameLayout fragmentFrame;
    @Bind(R.id.iv_password_arrow)
    ImageView ivPasswordArrow;
    @Bind(R.id.sliding_tabs)
    SlidingTabLayout slidingTabs;

    @Bind(R.id.tv_carddesc)
    TextView tv_carddesc;
    @Bind(R.id.tv_mima_door)
    TextView tv_mima_door;

    @Bind(R.id.tv_doordesc)
    TextView tv_doordesc;
    @Bind(R.id.iv_apart_photo_1)
    ImageView iv_apart_photo_1;
    @Bind(R.id.iv_apart_photo_2)
    ImageView iv_apart_photo_2;
    @Bind(R.id.iv_apart_photo_3)
    ImageView iv_apart_photo_3;
    @Bind(R.id.iv_apart_photo_4)
    ImageView iv_apart_photo_4;
    ImageView[] gatePhotos;
    ImageView[] pestPhotos;


    private NavigationAdapter mPagerAdapter;

    @Bind(R.id.rl_right_img)
    RelativeLayout mRlRightImg;

    Map<Integer, ApartmentPointTabFragment> fragmentMap;
    PointListController pointListController;

    private String communityId;
    private String starttime;
    private String endtime;

    public PointListModel data;

    HomeFilterUtil homeFilterUtil = HomeFilterUtil.getIns();
    private ApartmentPointActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_point);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        Intent intent = getIntent();
        activity = this;
        if(intent!=null){
            communityId = intent.getStringExtra(AppParamContact.PARAM_KEY_ID);
//            communityId = "663";
//            starttime = "2016-05-05";
//            endtime = "2016-05-11";
            setTitle(intent.getStringExtra(AppParamContact.PARAM_KEY_TITLE));
        }
        gatePhotos = new ImageView[]{iv_apart_photo_1,iv_apart_photo_2};
        pestPhotos = new ImageView[]{iv_apart_photo_3,iv_apart_photo_4};
        mRlRightImg.setVisibility(View.VISIBLE);
        fragmentMap = new HashMap<>();
        ViewCompat.setElevation(mHeaderView, getResources().getDimension(R.dimen.toolbar_elevation));
        pointListController = new PointListController(this);
        mToolbarView.set(mToolbar);
        mPagerAdapter = new NavigationAdapter(getSupportFragmentManager(), this);
        mPager.setOffscreenPageLimit(2);
        mPager.setAdapter(mPagerAdapter);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slidingTabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);
        slidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.colorPrimary));
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(mPager);

        slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        mPager.setCurrentItem(0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        },100);
    }

    private static class NavigationAdapter extends CacheFragmentStatePagerAdapter {

        private int mScrollY;

        private ApartmentPointActivity activity;


        public NavigationAdapter(FragmentManager fm, ApartmentPointActivity activity) {
            super(fm);
            this.activity = activity;
        }

        public void setScrollY(int scrollY) {
            mScrollY = scrollY;
        }

        @Override
        protected Fragment createItem(int position) {
            ApartmentPointTabFragment f = new ApartmentPointTabFragment();
            activity.fragmentMap.put(position, f);
            Bundle args = new Bundle();
            if (0 < mScrollY) {
                args.putInt(ApartmentPointTabFragment.ARG_INITIAL_POSITION, 1);
            }
            switch (position) {
                case 0:
                    args.putInt(ApartmentPointTabFragment.LIST_TYPE, AppSpContact.SP_KEY_APAET_POINT_UNDONE);
                    break;
                case 1:
                    args.putInt(ApartmentPointTabFragment.LIST_TYPE, AppSpContact.SP_KEY_APAET_POINT_DONE);
                    break;
                case 2:
                    args.putInt(ApartmentPointTabFragment.LIST_TYPE, AppSpContact.SP_KEY_APAET_POINT_ERROR);
                    break;

            }
            args.putString(AppParamContact.PARAM_KEY_ID, activity.communityId);
            args.putString(AppParamContact.PARAM_KEY_STARTTIME, activity.starttime);
            args.putString(AppParamContact.PARAM_KEY_ENDTIME, activity.endtime);

            f.setArguments(args);

            return f;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "未上点位";
                case 1:
                    return "完成点位";
                case 2:
                    return "报错";

                default:
                    return "error";
            }
        }
    }

    @Override
    public Fragment getFragment() {
        return null;
    }

    @OnClick(R.id.rl_password)
    public void swithPassword() {
        int vis = llPasswordFrame.getVisibility();
        if (vis == View.VISIBLE) {
            llPasswordFrame.setVisibility(View.GONE);
        } else if (vis == View.GONE) {
            llPasswordFrame.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.rl_right_img)
    public void callPhone(){

        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                new String[]{Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CALL_PHONE},
                new PermissionsResultAction() {
                    @Override
                    public void onGranted() {
                        ApartmentPhoneDialogFragment dialog = new ApartmentPhoneDialogFragment();
                        dialog.show(activity.getSupportFragmentManager(), "Write Comments");
                    }

                    @Override
                    public void onDenied(String permission) {
                        ToastHelper.showAlert(activity,"没有拨打电话权限,请前往设置");
                    }
                });


    }

    @OnClick(R.id.iv_back)
    public void goBack(){
        finish();
    }


    //------------ 以下是获取数据
    public void onRefresh(){
        PointListParam param = new PointListParam();
        param.communityid = communityId;
        param.startime = homeFilterUtil.startTime;
        param.endtime = homeFilterUtil.endTime;
        param.space = homeFilterUtil.getAreaId();
        param.customer = homeFilterUtil.getCustomerId();
        pointListController.getTaskIndex(param);
        if(fragmentMap!=null && fragmentMap.get(0)!=null){

            fragmentMap.get(0).showLoadingCircle();
        }
    }

    @Override
    public void onSuccessDisplay(PointListModel data) {
        if (data != null) {
            if (AppApiContact.ErrorCode.SUCCESS.equals(data.rescode)) {
                mSharedPreferencesHelper.putString(communityId+"_"+homeFilterUtil.startTime,data.toJson());
                updateData(true);
            }
        }
    }


    public void updateData(boolean swipeflag){
        String dataJson = mSharedPreferencesHelper.getString(communityId+"_"+homeFilterUtil.startTime);
        if(!StringUtils.isEmpty(dataJson)){
            Gson gson = new Gson();
            try{
                PointListModel data = gson.fromJson(dataJson,PointListModel.class);
                if(data!=null){
                    this.data = data;
                    boolean colorFlag1,colorFlag2,colorFlag3,colorFlag4;
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("门口照:");
                    if(StringUtils.isEmpty(data.getCGatePic())){
                        buffer.append("未拍;");
                        colorFlag1 = true;
                    }else {
                        buffer.append("已拍;");
                        colorFlag1 = false;
                    }
                    buffer.append("环境照:");
                    if(StringUtils.isEmpty(data.getCPestPic())){
                        buffer.append("未拍;");
                        colorFlag2 = true;
                    }else {
                        buffer.append("已拍;");
                        colorFlag2 = false;
                    }

                    buffer.append("门卡:");
                    if(StringUtils.isEmpty(data.getCarddesc())){
                        buffer.append("未填;");
                        colorFlag3 = true;
                    }else {
                        buffer.append("已填;");
                        colorFlag3 = false;
                    }

                    buffer.append("密码:");
                    if(StringUtils.isEmpty(data.getDoordesc())){
                        buffer.append("未填;");
                        colorFlag4 = true;
                    }else {
                        buffer.append("已填;");
                        colorFlag4 = false;
                    }

                    SpannableStringBuilder builder = new SpannableStringBuilder(buffer.toString());
                    builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.username_color)),0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    if(colorFlag1){
                        builder.setSpan(new ForegroundColorSpan(Color.RED),4,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }else{
                        builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.username_color)),4,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.username_color)),7,11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    if(colorFlag2){
                        builder.setSpan(new ForegroundColorSpan(Color.RED),11,14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }else{
                        builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.username_color)),11,14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.username_color)),14,17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    if(colorFlag3){
                        builder.setSpan(new ForegroundColorSpan(Color.RED),17,20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }else{
                        builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.username_color)),17,20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.username_color)),20,23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    if(colorFlag4){
                        builder.setSpan(new ForegroundColorSpan(Color.RED),23,26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }else{
                        builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.username_color)),23,26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }

                    tv_mima_door.setText(builder);

                    tv_carddesc.setText("密码："+data.getDoordesc());
                    tv_doordesc.setText("门卡备注："+data.getCarddesc());
                    setCardPhoto(data.getCGatePic(),data.getCPestPic());
                    ApartmentPointUtils.getIns().pointListModel = data;
                    List<PointListModel.NewListBean> newList = data.getNewList();
                    PointBeanDbUtil util = PointBeanDbUtil.getIns();
                    util.insertData(newList,communityId,homeFilterUtil.startTime,homeFilterUtil.endTime,data.getCommunity());
                    resetTitle(util.getItemNumber(communityId,homeFilterUtil.startTime));
                    for(Integer key:fragmentMap.keySet()){
                        fragmentMap.get(key).onSuccessDisplay(data,swipeflag);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void setCardPhoto(String cGatePic,String cPestPic){
        String[] gate = cGatePic.split("@&");
        String[] pest = cPestPic.split("@&");

        for(int i=0;i<gate.length;i++){
            if(i<gatePhotos.length){
                if(!StringUtils.isEmpty(gate[i])){
                    Picasso.with(this).load(gate[i]).placeholder(R.drawable.abc).into(gatePhotos[i]);
                }
            }
        }
        for(int i=0;i<pest.length;i++){
            if(i<pestPhotos.length){
                if(!StringUtils.isEmpty(pest[i])){
                    Picasso.with(this).load(pest[i]).placeholder(R.drawable.abc).into(pestPhotos[i]);
                }
            }
        }

    }


    @Override
    public void onFailDisplay(String errorMsg) {
        for(Integer key:fragmentMap.keySet()){
            fragmentMap.get(key).onFailDisplay();
        }
    }


    public void resetTitle(long[] number){
        slidingTabLayout.resetTitle("未上点位 ("+number[0]+")",
                "完成点位 ("+number[1]+")","报错 ("+number[2]+")"
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateData(false);
            }
        },100);

    }

    @OnClick(R.id.bt_edit_save)
    public void editSave(){
        startActivity(new Intent(this,CardEditActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "Activity-onRequestPermissionsResult() PermissionsManager.notifyPermissionsChange()");
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}
