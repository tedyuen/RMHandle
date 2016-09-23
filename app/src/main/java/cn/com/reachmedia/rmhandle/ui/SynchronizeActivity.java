package cn.com.reachmedia.rmhandle.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.ksoichiro.android.observablescrollview.CacheFragmentStatePagerAdapter;
import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.network.AppNetworkInfo;
import cn.com.reachmedia.rmhandle.service.ServiceHelper;
import cn.com.reachmedia.rmhandle.ui.base.BaseActionBarTabActivity;
import cn.com.reachmedia.rmhandle.ui.fragment.BaseFragment;
import cn.com.reachmedia.rmhandle.ui.fragment.SynchronizeTabFragment;
import cn.com.reachmedia.rmhandle.ui.fragment.SynchronizeTabFragment2;
import cn.com.reachmedia.rmhandle.utils.ToastHelper;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/13 上午11:02
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/13          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class SynchronizeActivity extends BaseActionBarTabActivity {


    @Bind(R.id.rl_right_text)
    RelativeLayout rlLeftText;
    private NavigationAdapter mPagerAdapter;

    Map<Integer, BaseFragment> fragmentMap;

    SynchronizeActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_map_tab);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        needTitle();
        activity = this;
        fragmentMap = new HashMap<>();
        ViewCompat.setElevation(mHeaderView, getResources().getDimension(R.dimen.toolbar_elevation));
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

        rlLeftText.setVisibility(View.VISIBLE);

//        showProgressDialog();
    }

    @OnClick(R.id.rl_right_text)
    public void goSynchronize(){
        if(AppNetworkInfo.isWifi(this)){
//            ServiceHelper.getIns().startPointWorkWithPicService(getApplicationContext());
            ServiceHelper.getIns().startPointWorkWOwifiService(getApplicationContext());
            ServiceHelper.getIns().startCommDoorPicService(getApplicationContext(),false);
            ToastHelper.showInfo(activity,"开始上传,请稍后刷新状态!");
        }else{
            new MaterialDialog.Builder(this)
                    .title(R.string.dialog_title_synchronize)
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
                            ServiceHelper.getIns().startPointWorkWOwifiService(getApplicationContext());
                            ServiceHelper.getIns().startCommDoorPicService(getApplicationContext(),true);
                            ToastHelper.showInfo(activity,"开始上传,请稍后刷新状态!");
                            dialog.dismiss();
                        }
                    })
                    .show();
        }


    }


    private static class NavigationAdapter extends CacheFragmentStatePagerAdapter {

        private int mScrollY;

        private SynchronizeActivity activity;


        public NavigationAdapter(FragmentManager fm, SynchronizeActivity activity) {
            super(fm);
            this.activity = activity;
        }

        public void setScrollY(int scrollY) {
            mScrollY = scrollY;
        }

        @Override
        protected Fragment createItem(int position) {
            BaseFragment f = null;
            Bundle args = new Bundle();

            switch (position) {
                case 0:
                    f = new SynchronizeTabFragment();
                    if (0 < mScrollY) {
                        args.putInt(SynchronizeTabFragment.ARG_INITIAL_POSITION, 1);
                        args.putInt(SynchronizeTabFragment.LIST_TYPE, 0);
                    }
                    f.setArguments(args);
                    activity.fragmentMap.put(position, f);
                    break;
                case 1:
                    f = new SynchronizeTabFragment2();
                    if (0 < mScrollY) {
                        args.putInt(SynchronizeTabFragment.ARG_INITIAL_POSITION, 1);
                        args.putInt(SynchronizeTabFragment.LIST_TYPE, 1);
                    }
                    f.setArguments(args);
                    activity.fragmentMap.put(position, f);
                    break;
            }
            return f;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "待上传";
                case 1:
                    return "最近已上传";
                default:
                    return "error";
            }
        }
    }

    @Override
    public Fragment getFragment() {
        return null;
    }

    private PowerManager pManager;
    private PowerManager.WakeLock mWakeLock;

    @Override
    protected void onResume() {
        super.onResume();
        pManager = ((PowerManager) getSystemService(POWER_SERVICE));
        mWakeLock = pManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                | PowerManager.ON_AFTER_RELEASE, TAG);
        mWakeLock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(null != mWakeLock){
            mWakeLock.release();
        }
    }
}
