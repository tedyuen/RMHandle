package cn.com.reachmedia.rmhandle.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.List;

import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.PointWorkBean;
import cn.com.reachmedia.rmhandle.db.utils.PointWorkBeanDbUtil;
import cn.com.reachmedia.rmhandle.model.UploadWorkModel;
import cn.com.reachmedia.rmhandle.model.param.UploadWorkParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.UploadWorkController;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/18 下午5:56
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/18          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PointWorkService extends Service {

    private PointWorkBeanDbUtil pointWorkBeanDbUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        pointWorkBeanDbUtil = PointWorkBeanDbUtil.getIns();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("start service!");
        String userId = SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID);
        if(userId!=null){
            List<PointWorkBean> listBean = pointWorkBeanDbUtil.getUpload1(userId);
            for(PointWorkBean bean:listBean){
                UploadWorkParam param = new UploadWorkParam(bean);
                UploadWorkController controller = new UploadWorkController(new UiDisplayListener<UploadWorkModel>() {
                    @Override
                    public void onSuccessDisplay(UploadWorkModel data) {
                        if (data != null) {
                            if (AppApiContact.ErrorCode.SUCCESS.equals(data.rescode)) {
                                System.out.println("workId: "+data.getWorkId()+"\tpointId: "+data.getPoint());
                            }
                        }
                    }

                    @Override
                    public void onFailDisplay(String errorMsg) {

                    }
                });
                controller.getTaskIndex(param);


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
