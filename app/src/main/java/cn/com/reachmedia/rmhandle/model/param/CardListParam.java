package cn.com.reachmedia.rmhandle.model.param;

import com.google.gson.Gson;

import cn.com.reachmedia.rmhandle.app.AppApiContact;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/20 上午10:52
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/20          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class CardListParam extends TokenParam {


    public String communityId;

    public CardListParam(){
        method = AppApiContact.InterfaceMethod.CARD_LIST;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }
}
