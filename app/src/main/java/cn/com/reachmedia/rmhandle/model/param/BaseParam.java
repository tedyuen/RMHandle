package cn.com.reachmedia.rmhandle.model.param;

import com.google.gson.Gson;

import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.utils.MD5;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/25 下午5:32
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/25          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class BaseParam {
    public String method;

    public String clienttype;

    public String clientversion;

    public String infversion;

    public String modelversion;

    public String imeino;

    public String timestamp;

    public String md5s;

    public String macaddress;

    public String model;

    public BaseParam(){
        clienttype = "ad_ph";
        clientversion = SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_VERSION_NAME);
        infversion = "1";
        imeino = SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_DEVICE_ID);
        macaddress = SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_MAC_ADDRESS);
        model = SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_PHONE_MODEL);
        timestamp = System.currentTimeMillis()+"";
        md5s = MD5.getMD5KeyStr(timestamp).toUpperCase();
        modelversion = android.os.Build.VERSION.RELEASE+":"+android.os.Build.VERSION.SDK_INT;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }
}
