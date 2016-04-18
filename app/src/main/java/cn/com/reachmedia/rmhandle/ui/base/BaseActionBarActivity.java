package cn.com.reachmedia.rmhandle.ui.base;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MenuItem;

import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/18 下午1:39
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/18          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public abstract class BaseActionBarActivity extends AppCompatActivity {

    protected final String TAG = getClass().getSimpleName();
    protected ActionBar mActionBar;
    protected SharedPreferencesHelper mSharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar = getSupportActionBar();
        mSharedPreferencesHelper = SharedPreferencesHelper.getInstance();

        showUpActionBar();
    }

    protected int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    /**
     * 显示ActionBar返回箭头
     */
    protected void showUpActionBar() {
        if (mActionBar != null) {
            mActionBar.setDisplayShowCustomEnabled(false);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public abstract Fragment getFragment();
}
