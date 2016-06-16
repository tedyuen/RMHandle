package cn.com.reachmedia.rmhandle.ui;

import android.support.v4.app.Fragment;

import cn.com.reachmedia.rmhandle.ui.base.BaseAbstractActionBarActivity;
import cn.com.reachmedia.rmhandle.ui.fragment.TaskInfoAllFragment;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/16 下午3:55
 * Description: 小区纵览
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/16          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class TaskInfoAllActivity extends BaseAbstractActionBarActivity {

    @Override
    public Fragment getFragment() {
        return new TaskInfoAllFragment().newInstance();
    }
}
