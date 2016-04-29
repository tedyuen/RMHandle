package cn.com.reachmedia.rmhandle.model.param;

import com.google.gson.Gson;

import cn.com.reachmedia.rmhandle.app.AppApiContact;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/29 上午10:13
 * Description: 3.7 提交工作工作项接口
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/29          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class UploadWorkParam extends TokenParam{

    public String workId;
    public int state;
    public int errorType;
    public String errorDesc;
    public String lon;
    public String lat;


    public UploadWorkParam(){
        method = AppApiContact.InterfaceMethod.UPLOAD_WORK_METHOD;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }
}
