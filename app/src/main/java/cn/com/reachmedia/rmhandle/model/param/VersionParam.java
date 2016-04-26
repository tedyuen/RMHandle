package cn.com.reachmedia.rmhandle.model.param;

import com.google.gson.Gson;

import cn.com.reachmedia.rmhandle.app.AppApiContact;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/26 上午11:46
 * Description: 4.1 版本检测接口
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/26          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class VersionParam extends TokenParam {

    public int build;
    public String version;

    public VersionParam(){
        method = AppApiContact.InterfaceMethod.VERSION_METHOD;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }

}
