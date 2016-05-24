package cn.com.reachmedia.rmhandle.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;
import java.util.List;

import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.PointWorkBean;
import cn.com.reachmedia.rmhandle.db.utils.PointWorkBeanDbUtil;
import cn.com.reachmedia.rmhandle.model.UploadPicModel;
import cn.com.reachmedia.rmhandle.model.param.UploadPicParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.UploadPicController;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/24 上午11:06
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/24          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PointPicService extends Service {

    private PointWorkBeanDbUtil pointWorkBeanDbUtil;
    @Override
    public void onCreate() {
        super.onCreate();
        pointWorkBeanDbUtil = PointWorkBeanDbUtil.getIns();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("start PointPicService!");
        String userId = SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID);
        if(userId!=null){
            List<PointWorkBean> listBean = pointWorkBeanDbUtil.getUpload(userId,"1");
            for(PointWorkBean bean:listBean){
                System.out.println("==>  "+bean.getWorkId()+":"+bean.getPointId()+":"+bean.getNativeState());
                UploadPicParam uploadPicParam = new UploadPicParam(bean);
                UploadPicController uploadPicController = new UploadPicController(new UiDisplayListener<UploadPicModel>() {
                    @Override
                    public void onSuccessDisplay(UploadPicModel data) {

                    }

                    @Override
                    public void onFailDisplay(String errorMsg) {

                    }
                });
                String[] filePaths = bean.getFilePathData().split(PointWorkBeanDbUtil.FILE_SPLIT);
                File file1 = new File(filePaths[0]);
                File file2 = null;
                File file3 = null;
                File doorFile= new File(bean.getDoorpic());
                if(!file1.exists()){
                    file1 = null;
                }
                if(!doorFile.exists()){
                    doorFile = null;
                }
                if(filePaths.length>1){
                    file2 = new File(filePaths[1]);
                    if(!file2.exists()){
                        file2 = null;
                    }
                }
                if(filePaths.length>2){
                    file3 = new File(filePaths[2]);
                    if(!file3.exists()){
                        file3 = null;
                    }
                }
                uploadPicController.uploadPic(uploadPicParam,file1,file2,file3,doorFile);
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
