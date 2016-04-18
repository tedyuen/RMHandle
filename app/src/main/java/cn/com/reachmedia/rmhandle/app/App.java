package cn.com.reachmedia.rmhandle.app;


import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.squareup.okhttp.OkHttpClient;

import org.greenrobot.eventbus.EventBus;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import cn.com.reachmedia.rmhandle.network.cookie.PersistentCookieStore;
import cn.com.reachmedia.rmhandle.network.http.AppApiService;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;
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

        setUpSharedPreferencesHelper(getApplicationContext());//初始化SharedPreferences
        setUpApiService();//初始化APP API
//        EventBus.getDefault().register(this);//注册接收定位信息事件

//        initJPush();

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
//        String deviceId = ((TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
//        sph.putString(AppSpContact.SP_KEY_DEVICE_ID, deviceId);
    }

    public void exit() {
//        EventBus.getDefault().unregister(this);
        System.exit(0);
    }
}
