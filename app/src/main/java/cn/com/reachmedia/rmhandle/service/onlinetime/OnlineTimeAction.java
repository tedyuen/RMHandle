package cn.com.reachmedia.rmhandle.service.onlinetime;

import android.os.AsyncTask;

import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;
import cn.com.reachmedia.rmhandle.utils.TimeUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tedyuen on 16-10-17.
 */
public class OnlineTimeAction {


    public void getOnlineTime(){

        App.getAppApiService().getOnlineTime(new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                try{
                    Long result = Long.valueOf(s);
                    String now = TimeUtils.getStrByLong(result);
//                    System.out.println(result+":"+now);
                    SharedPreferencesHelper.getInstance().putString(AppSpContact.SP_KEY_ON_LINE_TIME, now);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }
}
