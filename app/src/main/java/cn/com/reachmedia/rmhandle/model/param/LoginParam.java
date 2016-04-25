package cn.com.reachmedia.rmhandle.model.param;

import com.google.gson.Gson;

import cn.com.reachmedia.rmhandle.app.AppApiContact;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/25 下午5:34
 * Description: 3.1用户登陆
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/25          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class LoginParam extends BaseParam {
    public String username;

    public String password;

    public LoginParam(){
        method = AppApiContact.InterfaceMethod.LOGIN_METHOD;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }
}
