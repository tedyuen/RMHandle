package cn.com.reachmedia.rmhandle.model.param;

import com.google.gson.Gson;

import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/25 下午6:03
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/25          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class TokenParam extends BaseParam {
    public String usertoken;

    public TokenParam(){
//        usertoken = SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_TOKEN);
        usertoken = "7637E52C131BC6E693287205DD2D629D8DE599547B3C4940EA6DFB68DC416776";
    }

    public String toJson(){
        return new Gson().toJson(this);
    }
}
