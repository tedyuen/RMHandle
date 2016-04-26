package cn.com.reachmedia.rmhandle.model.param;

import com.google.gson.Gson;

import cn.com.reachmedia.rmhandle.app.AppApiContact;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/26 上午11:27
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/26          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PointListParam extends TokenParam{

    public String startime;
    public String endtime;
    public String space;
    public String lon;
    public String lat;
    public String communityid;
    public String customer;

    public PointListParam(){
        method = AppApiContact.InterfaceMethod.POINT_LIST_METHOD;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }


}
