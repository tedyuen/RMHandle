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

    public void startPointWorkService(Context context){
//        context.startService(new Intent(context,PointWorkService.class));

    }
}
