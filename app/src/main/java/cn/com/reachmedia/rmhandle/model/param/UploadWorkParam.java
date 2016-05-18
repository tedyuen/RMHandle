package cn.com.reachmedia.rmhandle.model.param;

import com.google.gson.Gson;

import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.bean.PointWorkBean;

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
    public String pointId;
    public int state;
    public int repairType;
    public String repairDesc;
    public int errorType;
    public String errorDesc;
    public String lon;
    public String lat;
    public String workTime;
    public String onlineTime;


    public UploadWorkParam(){
        method = AppApiContact.InterfaceMethod.UPLOAD_WORK_METHOD;
    }

    public UploadWorkParam(PointWorkBean bean){
        method = AppApiContact.InterfaceMethod.UPLOAD_WORK_METHOD;
        this.workId = bean.getWorkId();
        this.pointId = bean.getPointId();
        this.state = bean.getState();
        this.repairType = bean.getRepairType();
        this.repairDesc = bean.getRepairDesc();
        this.errorType = bean.getErrorType();
        this.errorDesc = bean.getErrorDesc();
        this.lon = bean.getLon();
        this.lat = bean.getLat();
        this.workTime = bean.getWorkTime();
        this.onlineTime = bean.getOnlineTime();

    }

    public String toJson(){
        return new Gson().toJson(this);
    }
}
