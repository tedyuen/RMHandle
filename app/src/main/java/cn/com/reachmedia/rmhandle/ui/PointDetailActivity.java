package cn.com.reachmedia.rmhandle.ui;

import android.support.v4.app.Fragment;

import cn.com.reachmedia.rmhandle.ui.base.BaseAbstractActionBarActivity;
import cn.com.reachmedia.rmhandle.ui.fragment.PointDetailFragment;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/29 上午10:55
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/29          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PointDetailActivity extends BaseAbstractActionBarActivity {

    @Override
    public Fragment getFragment() {
        return new PointDetailFragment().newInstance();
    }
}
