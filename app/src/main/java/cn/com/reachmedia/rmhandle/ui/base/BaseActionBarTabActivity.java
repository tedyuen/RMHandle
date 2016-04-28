package cn.com.reachmedia.rmhandle.ui.base;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;

import butterknife.Bind;
import cn.com.reachmedia.rmhandle.R;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/28 上午11:33
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/28          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public abstract class BaseActionBarTabActivity extends BaseActionBarActivity {

    public final ThreadLocal<View> mToolbarView = new ThreadLocal<>();
    public SlidingTabLayout slidingTabLayout;

    @Bind(R.id.toolbar)
    public Toolbar mToolbar;
    @Bind(R.id.header)
    public View mHeaderView;
    @Bind(R.id.pager)
    public ViewPager mPager;
    @Bind(R.id.toolbar_title)
    public TextView toolbarTitle;



    public void needTitle(){
        toolbarTitle.setText(getTitle());
    }
}
