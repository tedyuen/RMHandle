package cn.com.reachmedia.rmhandle.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;
import java.util.List;

import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.CommDoorPicBean;
import cn.com.reachmedia.rmhandle.db.utils.CommPoorPicDbUtil;
import cn.com.reachmedia.rmhandle.model.PicSubmitModel;
import cn.com.reachmedia.rmhandle.model.param.PicSubmitParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.PicSubmitController;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;
import cn.com.reachmedia.rmhandle.utils.StringUtils;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/8/8 下午5:22
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/8/8          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class DoorPicWOwifiService extends Service {

    private CommPoorPicDbUtil commPoorPicDbUtil;
    @Override
    public void onCreate() {
        super.onCreate();
        commPoorPicDbUtil = CommPoorPicDbUtil.getIns();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("start DoorPicService!");
        String userId = SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID);

        List<CommDoorPicBean> list = commPoorPicDbUtil.getUpload("0");
        for(CommDoorPicBean bean:list){

            PicSubmitController picSubmitController = new PicSubmitController(new UiDisplayListener<PicSubmitModel>() {
                @Override
                public void onSuccessDisplay(PicSubmitModel data) {
                    if (data != null) {
                        if (AppApiContact.ErrorCode.SUCCESS.equals(data.rescode)) {
                            //update native state.
                            commPoorPicDbUtil.changeNativeState(data.getCommunityId());
                        }
                    }
                }

                @Override
                public void onFailDisplay(String errorMsg) {

                }
            });

            PicSubmitParam picSubmitParam = new PicSubmitParam();
            picSubmitParam.communityId = bean.getCommunityId();
            File[] resultFile = new File[4];
            if(!StringUtils.isEmpty(bean.getCommunityFile1())){
                resultFile[0] = new File(bean.getCommunityFile1());
                if(!resultFile[0].exists()){
                    resultFile[0] = null;
                }
            }

            if(!StringUtils.isEmpty(bean.getCommunityFile2())){
                resultFile[1] = new File(bean.getCommunityFile2());
                if(!resultFile[1].exists()){
                    resultFile[1] = null;
                }
            }
            if(!StringUtils.isEmpty(bean.getCommunitySpace1())){
                resultFile[2] = new File(bean.getCommunitySpace1());
                if(!resultFile[2].exists()){
                    resultFile[2] = null;
                }
            }
            if(!StringUtils.isEmpty(bean.getCommunitySpace2())){
                resultFile[3] = new File(bean.getCommunitySpace2());
                if(!resultFile[3].exists()){
                    resultFile[3] = null;
                }
            }

            picSubmitController.picSubmit(picSubmitParam,resultFile[0],resultFile[1],resultFile[2],resultFile[3]
                    ,bean.getCommunityFileId1(),bean.getCommunityFileId2()
                    ,bean.getCommunitySpaceId1(),bean.getCommunitySpaceId2());

        }

        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
