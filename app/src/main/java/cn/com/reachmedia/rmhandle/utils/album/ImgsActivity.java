package cn.com.reachmedia.rmhandle.utils.album;

import android.support.v4.app.Fragment;

import cn.com.reachmedia.rmhandle.ui.base.BaseAbstractActionBarActivity;
import cn.com.reachmedia.rmhandle.utils.album.fragment.ImgsFragment;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/17 下午5:40
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/17          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class ImgsActivity extends BaseAbstractActionBarActivity{
    @Override
    public Fragment getFragment() {
        return new ImgsFragment().newInstance((FileTraversal)getIntent().getExtras().getParcelable("data"),
                (int)getIntent().getExtras().get("count"));
    }
}
