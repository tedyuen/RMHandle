package cn.com.reachmedia.rmhandle.model.param;

import com.google.gson.Gson;

import cn.com.reachmedia.rmhandle.app.AppApiContact;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/26 上午10:30
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/26          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class CardSubmitParam extends TokenParam {

    public String doordesc;
    public String carddesc;
    public String communityId;


    public CardSubmitParam(){
        method = AppApiContact.InterfaceMethod.CARD_SUBMIT;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }
}
