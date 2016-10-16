package cn.com.reachmedia.rmhandle.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

import cn.com.reachmedia.rmhandle.ui.base.BaseAbstractActionBarActivity;
import cn.com.reachmedia.rmhandle.ui.fragment.NewPointDetailFragment;
import cn.com.reachmedia.rmhandle.utils.pictureutils.utils.SimpleImageLoader;

/**
 * Created by tedyuen on 16-9-19.
 */
public class NewPointDetailActivity extends BaseAbstractActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleImageLoader.init(this.getApplicationContext());
    }

    @Override
    public Fragment getFragment() {
        return new NewPointDetailFragment().newInstance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**将事件传递给Fragment*/
        FragmentManager fm = getSupportFragmentManager();
        handlerFragmentOnActivityResult(fm, requestCode, resultCode, data);
    }

    public void handlerFragmentOnActivityResult(FragmentManager fragmentManager, int requestCode, int resultCode, Intent data) {
        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> fragments = fm.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if(fragment!=null){
                    fragment.onActivityResult(requestCode, resultCode, data);
                    FragmentManager childFragmentManager = fragment.getChildFragmentManager();
                    List<Fragment> childFragments = childFragmentManager.getFragments();
                    if (childFragmentManager != null) {

                    }
                }
            }
        }
    }
}
