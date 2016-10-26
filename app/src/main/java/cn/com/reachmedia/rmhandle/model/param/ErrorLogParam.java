package cn.com.reachmedia.rmhandle.model.param;

import com.google.gson.Gson;

import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;

/**
 * Created by tedyuen on 16-10-26.
 */
public class ErrorLogParam extends TokenParam {

    public String buildV;
    public String errorTime;
    public String errorType;
    public String log;


    public ErrorLogParam(){
        method = AppApiContact.InterfaceMethod.ERROR_LOG;
        buildV = SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_VERSION_CODE);
    }

    public ErrorLogParam(String errorTime,String errorType,String log){
        method = AppApiContact.InterfaceMethod.ERROR_LOG;
        buildV = SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_VERSION_CODE);
        this.errorTime = errorTime;
        this.errorType = errorType;
        this.log = log;
    }


    public String toJson(){
        return new Gson().toJson(this);
    }

}
