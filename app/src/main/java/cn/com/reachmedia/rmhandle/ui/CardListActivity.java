package cn.com.reachmedia.rmhandle.ui;

import android.support.v4.app.Fragment;

import cn.com.reachmedia.rmhandle.ui.base.BaseAbstractActionBarActivity;
import cn.com.reachmedia.rmhandle.ui.fragment.CardListFragment;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/12 下午6:26
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/12          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class CardListActivity extends BaseAbstractActionBarActivity {

    @Override
    public Fragment getFragment() {
        return new CardListFragment().newInstance();
    }
}
