package cn.com.reachmedia.rmhandle.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;
import java.util.List;

import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.PointWorkBean;
import cn.com.reachmedia.rmhandle.db.utils.PointWorkBeanDbUtil;
import cn.com.reachmedia.rmhandle.model.UploadPicModel;
import cn.com.reachmedia.rmhandle.model.param.UploadPicParam;
import cn.com.reachmedia.rmhandle.network.AppNetworkInfo;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.UploadPicController;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import de.greenrobot.dao.DaoException;

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
public class PointPicWOwifiService extends Service {

    private PointWorkBeanDbUtil pointWorkBeanDbUtil;
    @Override
    public void onCreate() {
        super.onCreate();
        pointWorkBeanDbUtil = PointWorkBeanDbUtil.getIns();
    }

    String userId;
    List<PointWorkBean> dataList;


    private void uploadSingle(){
        if(dataList!=null && dataList.size()>0) {
            PointWorkBean bean = dataList.get(0);
            if(bean!=null){
                System.out.println("==>single  "+bean.getWorkId()+":"+bean.getPointId()+":"+bean.getNativeState());
                UploadPicParam uploadPicParam = new UploadPicParam(bean);
                UploadPicController uploadPicController = new UploadPicController(new UiDisplayListener<UploadPicModel>() {
                    @Override
                    public void onSuccessDisplay(UploadPicModel data) {
                        if (data != null) {
                            if (AppApiContact.ErrorCode.SUCCESS.equals(data.rescode)) {
                                System.out.println("workId: "+data.getWorkId()+"\tpointId: "+data.getPoint());
                                if(dataList!=null && dataList.size()>0){
                                    dataList.remove(0);
                                }
                                try{
                                    pointWorkBeanDbUtil.changeNativeState(data.getWorkId(),data.getPoint(),"1","2");
                                    sendPointFinishMsg();
                                }catch (DaoException e) {
                                    e.printStackTrace();
//                                    pointWorkBeanDbUtil.changeNativeStateUnunique(data.getWorkId(), data.getPoint(), "1", "2");
                                }
                                uploadSingle();
                            }else if (AppApiContact.ErrorCode.ERROR_LESS_FILE.equals(data.rescode)) {
                                //文件未上传
                                if(dataList!=null && dataList.size()>0){
                                    dataList.remove(0);
                                }
                                uploadSingle();
                            }
                        }
                    }

                    @Override
                    public void onFailDisplay(String errorMsg) {

                    }
                });

                String[] filePaths = new String[0];
                if(bean.getFilePathData()!=null){
                    filePaths = bean.getFilePathData().split(PointWorkBeanDbUtil.FILE_SPLIT);
                }

                for(int i=0;i<filePaths.length;i++){
                    System.out.println("filepath==> "+filePaths[i]);
                }
                File file1 = null;
                if(filePaths.length>0){
                    file1 = new File(filePaths[0]);
                }
                File file2 = null;
                File file3 = null;
                File doorFile= new File(bean.getDoorpic()==null?"":bean.getDoorpic());
                if(file1!=null && !file1.exists()){
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


                String file1Id,file2Id,file3Id,communityDoorId,communityDoorXY,communityDoorTime;
                String[] fileIds = new String[0];
                if(bean.getFileIdData()!=null){
                    fileIds = bean.getFileIdData().split(PointWorkBeanDbUtil.FILE_SPLIT);
                }
                if(fileIds.length>0 && !StringUtils.isEmpty(fileIds[0])){
                    file1Id = fileIds[0];
                }else{
                    file1Id = "";
                }
                if(fileIds.length>1 && !StringUtils.isEmpty(fileIds[1])){
                    file2Id = fileIds[1];
                }else{
                    file2Id = "";
                }
                if(fileIds.length>2 && !StringUtils.isEmpty(fileIds[2])){
                    file3Id = fileIds[2];
                }else{
                    file3Id = "";
                }

                communityDoorId = bean.getDoorpicid()==null?"":bean.getDoorpicid();
                communityDoorXY = bean.getDoorpicXY()==null?"":bean.getDoorpicXY();
                communityDoorTime = bean.getDoorpicTime()==null?"":bean.getDoorpicTime();

                uploadPicController.uploadPic(uploadPicParam,file1,file2,file3,doorFile,file1Id,file2Id,file3Id,communityDoorId,communityDoorXY,communityDoorTime);
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("start PointPicWOService!");
        userId = SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID);
        if(userId!=null){
            dataList = pointWorkBeanDbUtil.getUpload(userId,"1");
            uploadSingle();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void sendPointFinishMsg(){
        Intent intent = new Intent("POINT_FINISHED_MSG");
        intent.putExtra("msg","finished");
        sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
