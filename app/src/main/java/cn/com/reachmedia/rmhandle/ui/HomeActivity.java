package cn.com.reachmedia.rmhandle.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.github.ksoichiro.android.observablescrollview.CacheFragmentStatePagerAdapter;
import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.ui.base.BaseActionBarActivity;
import cn.com.reachmedia.rmhandle.ui.fragment.HomeTabFragment;

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
public class HomeActivity extends BaseActionBarActivity {

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

    Map<Integer,HomeTabFragment> fragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);
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
}
