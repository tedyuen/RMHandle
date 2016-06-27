package cn.com.reachmedia.rmhandle.model.param;

import com.google.gson.Gson;

import cn.com.reachmedia.rmhandle.app.AppApiContact;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/27 下午2:08
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/27          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class GetVersionParam extends TokenParam{

    public String version;

    public int build;

    public GetVersionParam(){
        method = AppApiContact.InterfaceMethod.VERSION_METHOD;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }
}
