package cn.com.reachmedia.rmhandle.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import cn.com.reachmedia.rmhandle.service.ForegroundService;
import cn.com.reachmedia.rmhandle.ui.base.BaseAbstractActionBarActivity;
import cn.com.reachmedia.rmhandle.ui.fragment.CardEditFragment;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/12 下午1:31
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/12          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class CardEditActivity extends BaseAbstractActionBarActivity {

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceIntent = new Intent(this, ForegroundService.class);
        startService(serviceIntent);
        System.out.println("===>start service ForegroundService");
    }

    @Override
    public Fragment getFragment() {
        return new CardEditFragment().newInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(serviceIntent!=null){
            stopService(serviceIntent);
            System.out.println("===>stop service ForegroundService");
        }
    }
}
