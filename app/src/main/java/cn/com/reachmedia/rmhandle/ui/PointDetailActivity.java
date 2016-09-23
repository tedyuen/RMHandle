package cn.com.reachmedia.rmhandle.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import cn.com.reachmedia.rmhandle.service.ForegroundService;
import cn.com.reachmedia.rmhandle.ui.base.BaseAbstractActionBarActivity;
import cn.com.reachmedia.rmhandle.ui.fragment.PointDetailFragment;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/29 上午10:55
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/29          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PointDetailActivity extends BaseAbstractActionBarActivity {

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceIntent = new Intent(this, ForegroundService.class);
//        startService(serviceIntent);
        System.out.println("===>start service ForegroundService");
    }

    @Override
    public Fragment getFragment() {
        return new PointDetailFragment().newInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(serviceIntent!=null){
//            stopService(serviceIntent);
//            System.out.println("===>stop service ForegroundService");
//        }
    }
}
