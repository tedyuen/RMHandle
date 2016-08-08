package cn.com.reachmedia.rmhandle.service;

import android.content.Context;
import android.content.Intent;

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



    public void startPointPicService(Context context){
        context.startService(new Intent(context,PointPicService.class));
    }
    public void startPointPicWOwifiService(Context context){
        context.startService(new Intent(context,PointPicWOwifiService.class));
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
    public void stopLocationWorkService(Context context){
        context.stopService(locationIntent);
    }

}
