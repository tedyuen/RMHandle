package cn.com.reachmedia.rmhandle.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;

import com.github.ksoichiro.android.observablescrollview.CacheFragmentStatePagerAdapter;
import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.model.CardListModel;
import cn.com.reachmedia.rmhandle.model.param.CardListParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.CardListController;
import cn.com.reachmedia.rmhandle.ui.base.BaseAbstractActionBarActivity;
import cn.com.reachmedia.rmhandle.ui.base.BaseActionBarTabActivity;
import cn.com.reachmedia.rmhandle.ui.fragment.CardListAbFragment;
import cn.com.reachmedia.rmhandle.ui.fragment.CardListFragment;
import cn.com.reachmedia.rmhandle.ui.fragment.PswdListFragment;
import cn.com.reachmedia.rmhandle.ui.fragment.TaskInfoBaseFragment;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import cn.com.reachmedia.rmhandle.utils.ToastHelper;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/12 下午6:26
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/12          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class CardListActivity extends BaseActionBarTabActivity implements UiDisplayListener<CardListModel> {

    private NavigationAdapter mPagerAdapter;
    Map<Integer,CardListAbFragment> fragmentMap;

    CardListController cardListController;

    private String communityId;
    private String communityname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_map_tab);
        ButterKnife.bind(this);
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
        cardListController = new CardListController(this);
        Intent intent = getIntent();
        if(intent!=null){
            communityId = intent.getStringExtra("communityId");
            communityname = intent.getStringExtra("communityName");
        }
        needTitle();
        if (!StringUtils.isEmpty(communityname)) {
            setTitle(communityname);
        }
        if(!StringUtils.isEmpty(communityId)){
            onRefresh();
            showProgressDialog();
        }

    }

    public void onRefresh(){
        CardListParam cardListParam = new CardListParam();
        cardListParam.communityId = this.communityId;
        cardListController.getCardList(cardListParam);
    }

    @Override
    public void onSuccessDisplay(CardListModel data) {
        closeProgressDialog();
        if (data != null) {
            if (AppApiContact.ErrorCode.SUCCESS.equals(data.rescode)) {
                for(int key:fragmentMap.keySet()){
                    fragmentMap.get(key).onDataRefresh(data);
                }
            }
        }
    }

    @Override
    public void onFailDisplay(String errorMsg) {
        closeProgressDialog();
        ToastHelper.showAlert(this, getResources().getString(R.string.no_network));
    }

    private static class NavigationAdapter extends CacheFragmentStatePagerAdapter {

        private int mScrollY;

        private CardListActivity activity;


        public NavigationAdapter(FragmentManager fm, CardListActivity activity) {
            super(fm);
            this.activity = activity;
        }

        public void setScrollY(int scrollY) {
            mScrollY = scrollY;
        }

        @Override
        protected Fragment createItem(int position) {
            CardListAbFragment f=null;
            Bundle args = new Bundle();

            switch (position){
                case 0:
                    f = new CardListFragment();
                    activity.fragmentMap.put(position,f);
                    if (0 < mScrollY) {
                        args.putInt(CardListFragment.ARG_INITIAL_POSITION, 1);
                    }
                    f.setArguments(args);
                    break;
                case 1:
                    f = new PswdListFragment();
                    activity.fragmentMap.put(position,f);
                    if (0 < mScrollY) {
                        args.putInt(PswdListFragment.ARG_INITIAL_POSITION, 1);
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
                    return "门卡备注修改";
                case 1:
                    return "密码备注修改";
                default:
                    return "error";
            }
        }
    }


    @Override
    public Fragment getFragment() {
        return null;
    }
}
