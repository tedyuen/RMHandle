package cn.com.reachmedia.rmhandle.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.CacheFragmentStatePagerAdapter;
import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.model.TaskDetailModel;
import cn.com.reachmedia.rmhandle.model.param.TaskDetailParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.TaskDetailController;
import cn.com.reachmedia.rmhandle.ui.base.BaseActionBarActivity;
import cn.com.reachmedia.rmhandle.ui.base.BaseActionBarTabActivity;
import cn.com.reachmedia.rmhandle.ui.fragment.ApartmentInfoTabFragment;
import cn.com.reachmedia.rmhandle.ui.fragment.BaseFragment;
import cn.com.reachmedia.rmhandle.ui.fragment.CustomerPhotoTabFragment;
import cn.com.reachmedia.rmhandle.ui.fragment.TaskInfoBaseFragment;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/28 上午10:45
 * Description: 信息总揽
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/28          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class TaskInforActivity extends BaseActionBarTabActivity implements UiDisplayListener<TaskDetailModel> {

    private NavigationAdapter mPagerAdapter;


    Map<Integer,TaskInfoBaseFragment> fragmentMap;

    TaskDetailController taskDetailController;

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
        taskDetailController = new TaskDetailController(this);
    }

    public void onRefresh(){
        TaskDetailParam taskDetailParam = new TaskDetailParam();
        taskDetailParam.startime = "2016-03-10";
        taskDetailParam.endtime = "2016-05-20";
        taskDetailParam.space = "";
        taskDetailParam.lon = "1";
        taskDetailParam.lat = "1";
        taskDetailController.getTaskDetail(taskDetailParam);
    }


    @Override
    public void onSuccessDisplay(TaskDetailModel data) {
        if(fragmentMap!=null){
            for(Integer key:fragmentMap.keySet()){
                fragmentMap.get(key).updateData(data);
            }
        }
    }

    @Override
    public void onFailDisplay(String errorMsg) {
        if(fragmentMap!=null){
            for(Integer key:fragmentMap.keySet()){
                fragmentMap.get(key).onFailDisplay(errorMsg);
            }
        }
    }

    private static class NavigationAdapter extends CacheFragmentStatePagerAdapter {

        private int mScrollY;

        private TaskInforActivity activity;


        public NavigationAdapter(FragmentManager fm, TaskInforActivity activity) {
            super(fm);
            this.activity = activity;
        }

        public void setScrollY(int scrollY) {
            mScrollY = scrollY;
        }

        @Override
        protected Fragment createItem(int position) {
            TaskInfoBaseFragment f=null;
            Bundle args = new Bundle();

            switch (position){
                case 0:
                    f = new CustomerPhotoTabFragment();
                    activity.fragmentMap.put(position,f);
                    if (0 < mScrollY) {
                        args.putInt(CustomerPhotoTabFragment.ARG_INITIAL_POSITION, 1);
                    }
                    f.setArguments(args);
                    break;
                case 1:
                    f = new ApartmentInfoTabFragment();
                    activity.fragmentMap.put(position,f);
                    if (0 < mScrollY) {
                        args.putInt(ApartmentInfoTabFragment.ARG_INITIAL_POSITION, 1);
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
                    return "客户及拍照要求";
                case 1:
                    return "小区总览列表";
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
