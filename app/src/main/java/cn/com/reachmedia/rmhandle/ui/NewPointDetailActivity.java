package cn.com.reachmedia.rmhandle.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import cn.com.reachmedia.rmhandle.ui.base.BaseAbstractActionBarActivity;
import cn.com.reachmedia.rmhandle.ui.fragment.NewPointDetailFragment;

/**
 * Created by tedyuen on 16-9-19.
 */
public class NewPointDetailActivity extends BaseAbstractActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Fragment getFragment() {
        return new NewPointDetailFragment().newInstance();
    }
}
