package cn.com.reachmedia.rmhandle.ui;

import android.os.PowerManager;
import android.support.v4.app.Fragment;

import cn.com.reachmedia.rmhandle.ui.base.BaseAbstractActionBarActivity;
import cn.com.reachmedia.rmhandle.ui.fragment.FixErrorFragment;

/**
 * Created by tedyuen on 06/03/2017.
 */

public class FixErrorActivity extends BaseAbstractActionBarActivity {

    @Override
    public Fragment getFragment() {
        return new FixErrorFragment().newInstance();
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
