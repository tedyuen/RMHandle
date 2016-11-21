package cn.com.reachmedia.rmhandle.service;

import android.content.Context;
import android.content.Intent;

import cn.com.reachmedia.rmhandle.service.onlinetime.OnlineTimeService;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/18 下午6:20
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/18          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class ServiceHelper {
    private static ServiceHelper serviceHelper;
    private ServiceHelper(){}
    public static ServiceHelper getIns(){
        if(serviceHelper==null){
            serviceHelper = new ServiceHelper();
        }
        return serviceHelper;
    }

    Intent locationIntent;
    Intent onlineTimeIntent;

    /**
     * 没有wifi也传图片
     * @param context
     */
    public void startPointWorkWOwifiService(Context context){
        context.startService(new Intent(context,PointWorkNoPicService.class));
    }

    public void startPointWorkWithPicService(Context context){
        context.startService(new Intent(context,PointWorkService.class));
    }

    public void startCompImageService(Context context){
        context.startService(new Intent(context,CompImageService.class));
    }





    public void startPointPicService(Context context){
        context.startService(new Intent(context,PointPicService.class));
    }
    public void startPointPicWOwifiService(Context context){
        context.startService(new Intent(context,PointPicWOwifiService.class));
    }

    public void startRemoveDoneFileService(Context context){
        context.startService(new Intent(context,RemovePhotoService.class));
    }


    public void startCommDoorPicService(Context context,boolean wifiFlag){
        if(wifiFlag){//with out wifi
            context.startService(new Intent(context,DoorPicWOwifiService.class));
        }else{
            context.startService(new Intent(context,DoorPicService.class));
        }
    }



    public void startLocationWorkService(Context context){
        if(locationIntent==null)
            locationIntent = new Intent(context,LocationService.class);
        context.startService(locationIntent);
    }

    public void startOnlineTimeWorkService(Context context){
        if(onlineTimeIntent==null)
            onlineTimeIntent = new Intent(context,OnlineTimeService.class);
        context.startService(onlineTimeIntent);
    }

    public void stopLocationWorkService(Context context){
        context.stopService(locationIntent);
    }

    public void stopOnlineTimeService(Context context){
        if(onlineTimeIntent!=null){
            context.stopService(onlineTimeIntent);
        }
    }

    public void startUploadErrorLogService(Context context){
        context.startService(new Intent(context,UploadErrorLogService.class));
    }
}
