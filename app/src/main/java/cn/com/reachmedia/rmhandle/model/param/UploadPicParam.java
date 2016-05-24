package cn.com.reachmedia.rmhandle.model.param;

import com.google.gson.Gson;


import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.bean.PointWorkBean;
import cn.com.reachmedia.rmhandle.db.utils.PointWorkBeanDbUtil;
import cn.com.reachmedia.rmhandle.utils.StringUtils;

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
    public String file1Id;
    public String file2Id;
    public String file3Id;
    public String communityDoorId;
    public String communityDoorXY;
    public String communityDoorTime;


    public UploadPicParam(){
        method = AppApiContact.InterfaceMethod.UPLOAD_PIC_METHOD;
    }

    public UploadPicParam(PointWorkBean bean){
        method = AppApiContact.InterfaceMethod.UPLOAD_PIC_METHOD;
        this.workId = bean.getWorkId();
        this.pointId = bean.getPointId();
        this.workTime = bean.getWorkTime();
        this.onlineTime = bean.getOnlineTime();
        String[] fileIds = bean.getFileIdData().split(PointWorkBeanDbUtil.FILE_SPLIT);

        this.file1Id = fileIds[0];
        if(fileIds.length>1 && !StringUtils.isEmpty(fileIds[1])){
            this.file2Id = bean.getOnlineTime();
        }else{
            this.file2Id = "";
        }
        if(fileIds.length>2 && !StringUtils.isEmpty(fileIds[2])){
            this.file3Id = bean.getOnlineTime();
        }else{
            this.file3Id = "";
        }

        this.communityDoorId = bean.getDoorpicid();
        this.communityDoorXY = bean.getDoorpicXY();
        this.communityDoorTime = bean.getDoorpicTime();
    }

    public String toJson(){
        return new Gson().toJson(this);
    }

}
