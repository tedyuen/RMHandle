package cn.com.reachmedia.rmhandle.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.ksoichiro.android.observablescrollview.CacheFragmentStatePagerAdapter;
import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.service.ServiceHelper;
import cn.com.reachmedia.rmhandle.ui.base.BaseActionBarActivity;
import cn.com.reachmedia.rmhandle.ui.fragment.HomeTabFragment;
import cn.com.reachmedia.rmhandle.ui.interf.HomeUiDataUpdate;
import cn.com.reachmedia.rmhandle.utils.HomeFilterUtil;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/18 下午1:37
 * Description: 首页
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/18          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class HomeActivity extends BaseActionBarActivity implements HomeUiDataUpdate {

    private final ThreadLocal<View> mToolbarView = new ThreadLocal<>();
    SlidingTabLayout slidingTabLayout;
    private NavigationAdapter mPagerAdapter;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.header)
    View mHeaderView;
    @Bind(R.id.pager)
    ViewPager mPager;
    @Bind(R.id.iv_bottom_2)
    ImageView mIvBottom2;
    @Bind(R.id.ll_filter_frame)
    LinearLayout mLlFilterFrame;
    @Bind(R.id.tv_start_time)
    TextView tv_start_time;
    @Bind(R.id.tv_end_time)
    TextView tv_end_time;
    @Bind(R.id.tv_area)
    TextView tv_area;
    @Bind(R.id.tv_custom)
    TextView tv_custom;


    Map<Integer,HomeTabFragment> fragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);
        initFilter();
        setSupportActionBar(mToolbar);
        fragmentMap = new HashMap<>();
        ViewCompat.setElevation(mHeaderView, getResources().getDimension(R.dimen.toolbar_elevation));
        mToolbarView.set(mToolbar);
        mPagerAdapter = new NavigationAdapter(getSupportFragmentManager(),this);
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
        mIvBottom2.setImageLevel(2);
        App.getIns().addHomeActivity(this);
//        setPage(0);
    }

    private static class NavigationAdapter extends CacheFragmentStatePagerAdapter {

        private int mScrollY;

        private HomeActivity activity;


        public NavigationAdapter(FragmentManager fm, HomeActivity activity) {
            super(fm);
            this.activity = activity;
        }

        public void setScrollY(int scrollY) {
            mScrollY = scrollY;
        }

        @Override
        protected Fragment createItem(int position) {
            HomeTabFragment f = new HomeTabFragment();
            f.setUiUpdateListener(activity);
            activity.fragmentMap.put(position,f);
            Bundle args = new Bundle();
            if (0 < mScrollY) {
                args.putInt(HomeTabFragment.ARG_INITIAL_POSITION, 1);
            }
            switch (position){
                case 0:
                    args.putInt(HomeTabFragment.LIST_TYPE, AppSpContact.SP_KEY_UNDONE);
                    break;
                case 1:
                    args.putInt(HomeTabFragment.LIST_TYPE, AppSpContact.SP_KEY_DONE);
                    break;
            }
            f.setArguments(args);

            return f;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "未完成";
                case 1:
                    return "已完成";
                default:
                    return "error";
            }
        }
    }

    @Override
    public Fragment getFragment() {
        return null;
    }

    @OnClick(R.id.ll_bottom_2)
    public void goUserInfoActivity(){
        startActivity(new Intent(this,UserInfoActivity.class));
        overridePendingTransition(0, 0);
    }

    @OnClick(R.id.rl_map)
    public void goMyMap(){
        startActivity(new Intent(this,TaskMapActivity.class));

    }

    @OnClick(R.id.rl_info)
    public void goTaskInfo(){
        startActivity(new Intent(this,TaskInforActivity.class));
    }

    @OnClick(R.id.rl_filter)
    public void alertFilter(){
        triggleFilter();
//        HomeFilterDialogFragment dialog = new HomeFilterDialogFragment();
//        dialog.show(getSupportFragmentManager(), "Write Comments");
    }
    @OnClick(R.id.v_close_filter)
    public void closeFilter(){
        triggleFilter();
    }
    @OnClick(R.id.ll_filter_child_frame)
    public void blankFilterFrame(){

    }


    private void triggleFilter(){

        int vi = mLlFilterFrame.getVisibility();
        if(vi == View.VISIBLE){
            mLlFilterFrame.setVisibility(View.GONE);
            slidingTabLayout.setVisibility(View.VISIBLE);
        }else if(vi == View.GONE){
            mLlFilterFrame.setVisibility(View.VISIBLE);
            slidingTabLayout.setVisibility(View.GONE);
            tv_start_time.setText(homeFilterUtil.startTime);
            tv_end_time.setText(homeFilterUtil.endTime);
            tv_area.setText(homeFilterUtil.currentArea);
            tv_custom.setText(homeFilterUtil.currentCustomer);
        }
    }

    private boolean isFilterShowing(){
        int vi = mLlFilterFrame.getVisibility();
        if(vi==View.VISIBLE){
            return true;
        }else{
            return false;
        }
    }




    //home key
    private int mExitFlag = 0;
    private long mExitLong = System.currentTimeMillis();
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getRepeatCount() == 0) {
                if(isFilterShowing()) {
                    triggleFilter();
                    return true;
                }
                if (mExitFlag == 0) {
                    mExitFlag = 1;
                    mExitLong = System.currentTimeMillis();
                    Toast.makeText(this, R.string.quit_redo, Toast.LENGTH_SHORT).show();
                } else if (mExitFlag == 1) {
                    if ((System.currentTimeMillis() - mExitLong) <= 2000) {
                        //清除登录的key
//                    LoginState.getInstance().clean();
//                        App.app.exit();
                        finish();
                    } else {
                        mExitLong = System.currentTimeMillis();
                        Toast.makeText(this, R.string.quit_redo, Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        }

        return super.dispatchKeyEvent(event);
    }
    //home key


    @Override
    public void updateTabCount(int ongoing,int finish) {
        resetTitle(ongoing,finish);
    }

    public void resetTitle(int ongoing,int finish){
        slidingTabLayout.resetTitle("未完成 ("+ongoing+")",
                "已完成 ("+finish+")"
        );
    }


    private HomeFilterUtil homeFilterUtil;

    public void initFilter(){
        homeFilterUtil = HomeFilterUtil.getIns();
        tv_start_time.setText(homeFilterUtil.startTime);
        tv_end_time.setText(homeFilterUtil.endTime);
    }

    @OnClick(R.id.rl_start_time)
    public void selectStartTime(){
        new MaterialDialog.Builder(this)
                .items(homeFilterUtil.getStartTimes())
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        tv_start_time.setText(text);
                        tv_end_time.setText(homeFilterUtil.getNextWednesdayNoSet(text.toString()));
                        tv_custom.setText(homeFilterUtil.defaultC);
                        tv_area.setText(homeFilterUtil.defaultArea);

                    }
                })
                .show();
    }

    @OnClick(R.id.rl_area)
    public void selectArea(){
        new MaterialDialog.Builder(this)
                .items(homeFilterUtil.getResultData())
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        tv_area.setText(text);

                    }
                })
                .show();
    }

    @OnClick(R.id.rl_custom)
    public void selectCustomer(){
        new MaterialDialog.Builder(this)
                .items(homeFilterUtil.getCustomers())
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        homeFilterUtil.currentCustomer = text.toString();
                        tv_custom.setText(text);
                    }
                })
                .show();
    }

    @OnClick(R.id.bt_logout)
    public void sendRefresh(){
        if(!homeFilterUtil.startTime.equals(tv_start_time.getText().toString())){// 时间改变了
            if(tv_custom.getText().toString().equals(homeFilterUtil.defaultC)){
                homeFilterUtil.currentCustomer = homeFilterUtil.defaultC;
            }else{
                homeFilterUtil.currentCustomer = tv_custom.getText().toString();
            }
            if(tv_area.getText().toString().equals(homeFilterUtil.defaultArea)){
                homeFilterUtil.currentArea = homeFilterUtil.defaultArea;
            }else{
                homeFilterUtil.currentArea = tv_area.getText().toString();
            }
        }else{
            homeFilterUtil.currentCustomer = tv_custom.getText().toString();
            homeFilterUtil.currentArea = tv_area.getText().toString();
        }
        homeFilterUtil.setStartTime(tv_start_time.getText().toString());//设置时间

        for(Integer key:fragmentMap.keySet()){
            fragmentMap.get(key).onRefresh();
        }

        triggleFilter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ServiceHelper.getIns().startPointWorkService(this);
    }
}
