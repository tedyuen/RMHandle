package cn.com.reachmedia.rmhandle.ui;

import android.support.v4.app.Fragment;

import cn.com.reachmedia.rmhandle.ui.base.BaseAbstractActionBarActivity;
import cn.com.reachmedia.rmhandle.ui.fragment.MyMapTabFragment;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/20 下午4:13
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/20          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class TaskMapActivity extends BaseAbstractActionBarActivity {
    @Override
    public Fragment getFragment() {
        return new MyMapTabFragment().newInstance();
    }
}
