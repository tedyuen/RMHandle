package cn.com.reachmedia.rmhandle.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.github.ksoichiro.android.observablescrollview.CacheFragmentStatePagerAdapter;
import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.ui.base.BaseActionBarActivity;
import cn.com.reachmedia.rmhandle.ui.fragment.BaseFragment;
import cn.com.reachmedia.rmhandle.ui.fragment.OfflineMapListFragment;
import cn.com.reachmedia.rmhandle.ui.fragment.OfflineMapManageFragment;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/21 上午10:49
 * Description: 离线地图管理
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/21          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class OfflineMapActivity extends BaseActionBarActivity implements MKOfflineMapListener{

    private final ThreadLocal<View> mToolbarView = new ThreadLocal<>();
    SlidingTabLayout slidingTabLayout;
    private NavigationAdapter mPagerAdapter;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.header)
    View mHeaderView;
    @Bind(R.id.pager)
    ViewPager mPager;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;

    Map<Integer,BaseFragment> fragmentMap;
    public MKOfflineMap mOffline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map_tab);
        ButterKnife.bind(this);
        initOfflineMap();
        setSupportActionBar(mToolbar);
        needTitle();
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

    }


    private static class NavigationAdapter extends CacheFragmentStatePagerAdapter {

        private int mScrollY;

        private OfflineMapActivity activity;


        public NavigationAdapter(FragmentManager fm, OfflineMapActivity activity) {
            super(fm);
            this.activity = activity;
        }

        public void setScrollY(int scrollY) {
            mScrollY = scrollY;
        }

        @Override
        protected Fragment createItem(int position) {
            BaseFragment f=null;
            Bundle args = new Bundle();

            switch (position){
                case 0:
                    f = new OfflineMapListFragment();
                    activity.fragmentMap.put(position,f);
                    if (0 < mScrollY) {
                        args.putInt(OfflineMapListFragment.ARG_INITIAL_POSITION, 1);
                    }
                    f.setArguments(args);
                    break;
                case 1:
                    f = new OfflineMapManageFragment();
                    activity.fragmentMap.put(position,f);
                    if (0 < mScrollY) {
                        args.putInt(OfflineMapManageFragment.ARG_INITIAL_POSITION, 1);
                    }
                    f.setArguments(args);
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
            switch (position){
                case 0:
                    return "城市列表";
                case 1:
                    return "下载管理";
                default:
                    return "error";
            }
        }
    }

    public void needTitle(){
        toolbarTitle.setText(getTitle());
    }

    @OnClick(R.id.iv_back)
    public void goBack(){
        finish();
    }

    @Override
    public Fragment getFragment() {
        return null;
    }

    private void initOfflineMap(){
        mOffline = new MKOfflineMap();
        mOffline.init(this);
    }

    @Override
    public void onGetOfflineMapState(int type, int state) {
        switch (type) {
            case MKOfflineMap.TYPE_DOWNLOAD_UPDATE: {
                MKOLUpdateElement update = mOffline.getUpdateInfo(state);
                // 处理下载进度更新提示
                if (update != null) {
//                    stateView.setText(String.format("%s : %d%%", update.cityName,
//                            update.ratio));
//                    updateView();
                    OfflineMapManageFragment fragment = (OfflineMapManageFragment)fragmentMap.get(1);
                    if(fragment!=null){
                        fragment.updateView();
                    }
                }
            }
            break;
            case MKOfflineMap.TYPE_NEW_OFFLINE:
                // 有新离线地图安装
                Log.d("OfflineDemo", String.format("add offlinemap num:%d", state));
                break;
            case MKOfflineMap.TYPE_VER_UPDATE:
                // 版本更新提示
                // MKOLUpdateElement e = mOffline.getUpdateInfo(state);

                break;
            default:
                break;
        }
    }

    public String formatDataSize(int size) {
        String ret = "";
        if (size < (1024 * 1024)) {
            ret = String.format("%dK", size / 1024);
        } else {
            ret = String.format("%.1fM", size / (1024 * 1024.0));
        }
        return ret;
    }

    public void startDownloadMap(int cityid){
        OfflineMapManageFragment fragment = (OfflineMapManageFragment)fragmentMap.get(1);
        if(fragment!=null){
            fragment.start(cityid);
            switchOfflineMapManage();
        }

    }

    public void switchOfflineMapManage(){
        mPager.setCurrentItem(1);
    }


    @Override
    protected void onDestroy() {
        /**
         * 退出时，销毁离线地图模块
         */
        mOffline.destroy();
        super.onDestroy();
    }

}
