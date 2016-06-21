package cn.com.reachmedia.rmhandle.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/21 上午9:56
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/21          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class IntentUtils {
    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 跳转到第三方地图App
     */
    public static void goMapApp(Context context,String Longitude,String Latitude,String Address){
        try {
            //先检测本地安装有百度地图app
            //com.baidu.BaiduMap
///*          if (isAvilible(context, "com.baidu.BaiduMap")) {//传入指定应用包名
//                Uri uri = Uri.parse("geo:" + Latitude + "," + Longitude + "?q="
//                        + Address);
//                Intent it = new Intent(Intent.ACTION_VIEW, uri);
//                it.setPackage("com.baidu.BaiduMap");
//                context.startActivity(it);
//            } else  if (isAvilible(context, "com.autonavi.minimap")) {
//                Uri uri = Uri.parse("geo:" + Latitude + "," + Longitude + "?q="
//                        + Address);
//                Intent it = new Intent(Intent.ACTION_VIEW, uri);
//                it.setPackage("com.autonavi.minimap");
//                context.startActivity(it);
//            }  else {*/
            Uri uri = Uri.parse("geo:" + Latitude + "," + Longitude + "?q="
                    + Address);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(it);
            // }
        } catch (Exception e) {
            ToastHelper.showAlert((Activity)context,"您没有安装百度地图,请先下载百度地图");
            // Toast.makeText(context, "手机没有安装任何地图软件", Toast.LENGTH_SHORT).show();
        }
    }
}
