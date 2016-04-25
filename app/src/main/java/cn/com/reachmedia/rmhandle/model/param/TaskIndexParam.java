package cn.com.reachmedia.rmhandle.model.param;

import com.google.gson.Gson;

import cn.com.reachmedia.rmhandle.app.AppApiContact;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/25 下午6:02
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/25          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class TaskIndexParam extends TokenParam {

    public String starttime;

    public String endtime;

    public String space;

    public String lon;

    public String lat;

    public String customer;

    public TaskIndexParam(){
        method = AppApiContact.InterfaceMethod.TASK_INDEX_METHOD;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }

}
