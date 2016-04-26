package cn.com.reachmedia.rmhandle.model.param;

import com.google.gson.Gson;

import cn.com.reachmedia.rmhandle.app.AppApiContact;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/26 上午11:07
 * Description: 3.4 首页任务地图
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/26          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class TaskMapParam extends TokenParam {

    public String starttime;

    public String endtime;

    public String space;

    public String lon;

    public String lat;

    public TaskMapParam(){
        method = AppApiContact.InterfaceMethod.TASK_MAP_METHOD;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }
}
