package cn.com.reachmedia.rmhandle.ui;

import android.support.v4.app.Fragment;

import cn.com.reachmedia.rmhandle.ui.base.BaseAbstractActionBarActivity;
import cn.com.reachmedia.rmhandle.ui.fragment.UnUploadPointListFragment;

/**
 * Created by tedyuen on 16-12-14.
 * 未上传点位列表
 */
public class UnUploadPointListActivity extends BaseAbstractActionBarActivity {

    @Override
    public Fragment getFragment() {
        return UnUploadPointListFragment.newInstance();
    }
}
