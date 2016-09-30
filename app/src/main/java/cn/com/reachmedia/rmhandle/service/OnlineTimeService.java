package cn.com.reachmedia.rmhandle.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;

/**
 * Created by tedyuen on 16-9-27.
 */
public class OnlineTimeService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        new UpdateLocation().location();
        Timer timer = new Timer();
        timer.schedule(new LocationWork(),0, 5000);
//        timer.schedule(new LocationWork(),0, 10000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
