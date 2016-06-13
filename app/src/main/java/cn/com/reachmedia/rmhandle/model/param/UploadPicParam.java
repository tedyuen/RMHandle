package cn.com.reachmedia.rmhandle.model.param;

import com.google.gson.Gson;


import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.bean.PointWorkBean;
import cn.com.reachmedia.rmhandle.db.utils.PointWorkBeanDbUtil;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import cn.com.reachmedia.rmhandle.utils.TimeUtils;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/23 下午5:49
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/23          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class UploadPicParam extends TokenParam {

    public String workId;
    public String pointId;
    public String workTime;
    public String onlineTime;
//    public String file1Id;
//    public String file2Id;
//    public String file3Id;
//    public String communityDoorId;
//    public String communityDoorXY;
//    public String communityDoorTime;


    public UploadPicParam(){
        method = AppApiContact.InterfaceMethod.UPLOAD_PIC_METHOD;
    }

    public UploadPicParam(PointWorkBean bean){
        method = AppApiContact.InterfaceMethod.UPLOAD_PIC_METHOD;
        this.workId = bean.getWorkId();
        this.pointId = bean.getPointId();
        this.workTime = TimeUtils.dateAddByDateForString(bean.getWorkTime(),"yyyy-MM-dd HH:mm:ss",0);
        this.onlineTime = bean.getOnlineTime();

    }

    public String toJson(){
        return new Gson().toJson(this);
    }

}
