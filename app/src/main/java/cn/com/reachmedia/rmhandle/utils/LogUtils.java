package cn.com.reachmedia.rmhandle.utils;

import com.orhanobut.logger.Logger;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/18 上午11:54
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/18          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class LogUtils {
    private static final boolean DEBUG_V = true;
    private static final boolean DEBUG_D = true;
    private static final boolean DEBUG_I = true;
    private static final boolean DEBUG_W = true;
    private static final boolean DEBUG_E = true;

    public static void d(String tag, String msg) {
        if (DEBUG_D) {
            Logger.init(tag);
            Logger.d(msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (DEBUG_E) {
            Logger.init(tag);
            Logger.e(tr, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG_E) {
            Logger.init(tag);
            Logger.e(msg);
        }
    }

    public static void json(String tag,String json){
        if (DEBUG_D) {
            Logger.init(tag);
            Logger.json(json);
        }
    }

    public static void i(String tag, String msg) {
        if(DEBUG_I){
            Logger.init(tag);
            Logger.i(msg);
        }
    }
}
