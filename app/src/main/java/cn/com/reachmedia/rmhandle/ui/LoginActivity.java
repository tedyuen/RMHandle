package cn.com.reachmedia.rmhandle.ui;

import android.support.v4.app.Fragment;

import cn.com.reachmedia.rmhandle.ui.base.BaseAbstractActionBarActivity;
import cn.com.reachmedia.rmhandle.ui.fragment.LoginFragment;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/9 上午9:53
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/9          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class LoginActivity extends BaseAbstractActionBarActivity {

    @Override
    public Fragment getFragment() {
        return new LoginFragment().newInstance();
    }
}
