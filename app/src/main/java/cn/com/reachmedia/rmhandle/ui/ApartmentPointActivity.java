package cn.com.reachmedia.rmhandle.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.CacheFragmentStatePagerAdapter;
import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;

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
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.model.param.PointListParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.PointListController;
import cn.com.reachmedia.rmhandle.ui.base.BaseActionBarActivity;
import cn.com.reachmedia.rmhandle.ui.base.BaseActionBarTabActivity;
import cn.com.reachmedia.rmhandle.ui.dialog.ApartmentPhoneDialogFragment;
import cn.com.reachmedia.rmhandle.ui.fragment.ApartmentPointTabFragment;
import cn.com.reachmedia.rmhandle.utils.ApartmentPointUtils;

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
    @Bind(R.id.tv_doordesc)
    TextView tv_doordesc;


    private NavigationAdapter mPagerAdapter;

    @Bind(R.id.rl_right_img)
    RelativeLayout mRlRightImg;

    Map<Integer, ApartmentPointTabFragment> fragmentMap;
    PointListController pointListController;

    private String communityId;
    private String starttime;
    private String endtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_point);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        Intent intent = getIntent();
        if(intent!=null){
            communityId = intent.getStringExtra(AppParamContact.PARAM_KEY_ID);
            communityId = "663";
            starttime = "2016-05-05";
            endtime = "2016-05-11";
            setTitle(intent.getStringExtra(AppParamContact.PARAM_KEY_TITLE));
        }
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
        onRefresh();
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
            System.out.println("position:===>  "+position);
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
        ApartmentPhoneDialogFragment dialog = new ApartmentPhoneDialogFragment();
        dialog.show(getSupportFragmentManager(), "Write Comments");
    }

    @OnClick(R.id.iv_back)
    public void goBack(){
        finish();
    }


    //------------ 以下是获取数据
    public void onRefresh(){
        PointListParam param = new PointListParam();
        param.communityid = communityId;
        param.startime = starttime;
        param.endtime = endtime;
        param.space = "";
        param.customer = "";
        pointListController.getTaskIndex(param);
    }

    @Override
    public void onSuccessDisplay(PointListModel data) {
        if (data != null) {
            if (AppApiContact.ErrorCode.SUCCESS.equals(data.rescode)) {
                tv_carddesc.setText("密码："+data.getCarddesc());
                tv_doordesc.setText("门卡备注："+data.getDoordesc());
                ApartmentPointUtils.getIns().pointListModel = data;
                List<PointListModel.NewListBean> newList = data.getNewList();
                PointBeanDbUtil util = PointBeanDbUtil.getIns();
                util.insertData(newList,communityId,starttime,endtime);
                resetTitle(util.getItemNumber());
                for(Integer key:fragmentMap.keySet()){
                    fragmentMap.get(key).onSuccessDisplay(data);
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
}
