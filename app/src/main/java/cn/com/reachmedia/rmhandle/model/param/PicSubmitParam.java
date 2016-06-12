package cn.com.reachmedia.rmhandle.model.param;

import cn.com.reachmedia.rmhandle.app.AppApiContact;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/12 下午6:36
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/12          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PicSubmitParam extends TokenParam {

    public String communityId;

    public PicSubmitParam(){
        method = AppApiContact.InterfaceMethod.CPIC_SUBMIT;
    }

//    public UploadPicParam(PointWorkBean bean){
//        method = AppApiContact.InterfaceMethod.UPLOAD_PIC_METHOD;
//        this.workId = bean.getWorkId();
//        this.pointId = bean.getPointId();
//        this.workTime = bean.getWorkTime();
//        this.onlineTime = bean.getOnlineTime();
//
//    }
}
