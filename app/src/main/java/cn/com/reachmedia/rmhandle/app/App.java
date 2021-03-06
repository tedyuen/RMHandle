package cn.com.reachmedia.rmhandle.app;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.squareup.okhttp.OkHttpClient;
//import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.com.reachmedia.rmhandle.db.DatabaseLoader;
import cn.com.reachmedia.rmhandle.network.cookie.PersistentCookieStore;
import cn.com.reachmedia.rmhandle.network.http.AppApiService;
import cn.com.reachmedia.rmhandle.service.ServiceHelper;
import cn.com.reachmedia.rmhandle.ui.HomeActivity;
import cn.com.reachmedia.rmhandle.utils.AppVersionHelper;
import cn.com.reachmedia.rmhandle.utils.CrashHandler;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;
import cn.com.reachmedia.rmhandle.utils.TimeUtils;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/18 上午11:19
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/18          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class App extends Application {

    private static Context sContext;

    private static AppApiService sAppApiService;//API 请求Service对象

    public static App app = null;

    public static App getIns() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        app = this;
//        CrashReport.initCrashReport(getApplicationContext(), "900046108", true);
        CrashHandler handler = CrashHandler.getInstance();
        handler.init(getApplicationContext());
        Thread.setDefaultUncaughtExceptionHandler(handler);
        setUpSharedPreferencesHelper(getApplicationContext());//初始化SharedPreferences
        SharedPreferencesHelper mSharedPreferencesHelper = SharedPreferencesHelper.getInstance();
        initVersionInfo(mSharedPreferencesHelper);
        initPhoneInfo(mSharedPreferencesHelper);
        ServiceHelper.getIns().startUploadErrorLogService(this);
        setUpApiService();//初始化APP API
//        EventBus.getDefault().register(this);//注册接收定位信息事件
        DatabaseLoader.init(getApplicationContext());
//        initJPush();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        ServiceHelper.getIns().startLocationWorkService(this);
        if(mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LONGITUDE)==null){
            mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LONGITUDE,"123");
        }
        if(mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LATITUDE)==null){
            mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LATITUDE,"456");
        }
        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_ON_LINE_TIME, TimeUtils.getNowStr());
        ServiceHelper.getIns().startOnlineTimeWorkService(this);
    }

    public void initPhoneInfo(SharedPreferencesHelper mSharedPreferencesHelper){
        WifiManager wifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_MAC_ADDRESS, info.getMacAddress());

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if("MODEL".equals(field.getName())){
                    mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_PHONE_MODEL, field.get(null).toString());
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initVersionInfo(SharedPreferencesHelper mSharedPreferencesHelper){
        try{
            String versionName = AppVersionHelper.getVersionName(getContext());
            String versionCode = String.valueOf(AppVersionHelper.getVersionCode(getContext()));
            mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_VERSION_CODE,versionCode);
            mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_VERSION_NAME,versionName);
        }catch (Exception e){
            e.printStackTrace();
            mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_VERSION_CODE,"-1");
            mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_VERSION_NAME,"0.1");
        }
    }

    public void setUpApiService() {
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateTypeAdapter()).create();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(15, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(15, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(15, TimeUnit.SECONDS);
        okHttpClient.setCookieHandler(new CookieManager(
                new PersistentCookieStore(getApplicationContext()),
                CookiePolicy.ACCEPT_ALL));

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(AppApiContact.getApiHost())
                .setConverter(new GsonConverter(gson))
                .setClient(new OkClient(okHttpClient))
                .build();
        sAppApiService = restAdapter.create(AppApiService.class);
    }

    /**
     * 初始化SharedPreferences
     *
     * @param context 上下文
     */
    private void setUpSharedPreferencesHelper(Context context) {
        SharedPreferencesHelper sph = SharedPreferencesHelper.getInstance();
        sph.Builder(context);

    }

    private HomeActivity homeActivity;
    public void addHomeActivity(HomeActivity activity){
        this.homeActivity = activity;
    }
    public void closeHomeActivity(){
        if(this.homeActivity!=null){
            this.homeActivity.finish();
        }
    }


    //启动相册的activity
    private List<Activity> albumActivityList = new ArrayList<>();
    public void addAlbumActivity(Activity activity){
        albumActivityList.add(activity);
    }
    public void finishAlbumActivity(){
        for(Activity activity:albumActivityList){
            if(activity!=null){
                activity.finish();
            }
        }
        albumActivityList.clear();
    }



    public void exit() {
//        EventBus.getDefault().unregister(this);
        ServiceHelper.getIns().stopLocationWorkService(this);
        System.exit(0);
    }

    @Override
    public boolean stopService(Intent name) {
        ServiceHelper.getIns().stopLocationWorkService(this);
        ServiceHelper.getIns().stopOnlineTimeService(this);
        return super.stopService(name);
    }

    public static Context getContext() {
        return sContext;
    }

    public static AppApiService getAppApiService() {
        return sAppApiService;
    }
}
