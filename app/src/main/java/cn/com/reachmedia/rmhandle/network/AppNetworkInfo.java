package cn.com.reachmedia.rmhandle.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      15/10/20 上午10:34
 * Description: 手机网络资源工具类
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 15/10/20          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class AppNetworkInfo {
    /**
     * WIFI是否连接
     *
     * @param context 上下文
     * @return true 是 false 否
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return networkInfo.isConnected();
    }

    /**
     * 网络是否可用
     *
     * @param context 上下文
     * @return true 可用 false 不可用
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
