package cn.com.reachmedia.rmhandle.ui;

import android.os.PowerManager;
import android.support.v4.app.Fragment;

import cn.com.reachmedia.rmhandle.ui.base.BaseAbstractActionBarActivity;
import cn.com.reachmedia.rmhandle.ui.fragment.UserInfoFragment;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/18 下午4:01
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/18          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class UserInfoActivity extends BaseAbstractActionBarActivity {

    @Override
    public Fragment getFragment() {
        return new UserInfoFragment().newInstance();
    }

    private PowerManager pManager;
    private PowerManager.WakeLock mWakeLock;

    @Override
    protected void onResume() {
        super.onResume();
        pManager = ((PowerManager) getSystemService(POWER_SERVICE));
        mWakeLock = pManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                | PowerManager.ON_AFTER_RELEASE, TAG);
        mWakeLock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(null != mWakeLock){
            mWakeLock.release();
        }
    }
}
