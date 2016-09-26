package cn.com.reachmedia.rmhandle.ui;

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
}
