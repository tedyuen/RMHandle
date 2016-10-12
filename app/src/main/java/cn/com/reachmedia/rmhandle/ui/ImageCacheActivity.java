package cn.com.reachmedia.rmhandle.ui;

import android.os.PowerManager;
import android.support.v4.app.Fragment;

import cn.com.reachmedia.rmhandle.ui.base.BaseAbstractActionBarActivity;
import cn.com.reachmedia.rmhandle.ui.fragment.ImageCacheFragment;

/**
 * Created by tedyuen on 16-9-26.
 */
public class ImageCacheActivity extends BaseAbstractActionBarActivity {

    @Override
    public Fragment getFragment() {
        return new ImageCacheFragment().newInstance();
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
