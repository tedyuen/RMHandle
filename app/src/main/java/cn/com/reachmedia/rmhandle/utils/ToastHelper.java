package cn.com.reachmedia.rmhandle.utils;

import android.app.Activity;
import android.view.Gravity;

import com.devspark.appmsg.AppMsg;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/9 上午10:57
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/9          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class ToastHelper {
    public static void showInfo(Activity context, String msg) {
        try {
            AppMsg.makeText(context, msg, AppMsg.STYLE_INFO).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void showInfo(Activity context, int resId) {
        try {
            AppMsg.makeText(context, resId, AppMsg.STYLE_INFO).show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void showBottomInfo(Activity context, String msg) {
        AppMsg.makeText(context, msg, AppMsg.STYLE_INFO).setLayoutGravity(Gravity.BOTTOM).show();
    }

    public static void showBottomInfo(Activity context, int resId) {
        AppMsg.makeText(context, resId, AppMsg.STYLE_INFO).setLayoutGravity(Gravity.BOTTOM).show();
    }

    public static void showAlert(Activity context, String msg) {
        try {
            AppMsg appMsg = AppMsg.makeText(context, msg, AppMsg.STYLE_ALERT);
            appMsg.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void showAlert(Activity context, int resId) {
        try {
            AppMsg.makeText(context, resId, AppMsg.STYLE_ALERT).show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void showBottomAlert(Activity context, String msg) {
        AppMsg.makeText(context, msg, AppMsg.STYLE_ALERT).setLayoutGravity(Gravity.BOTTOM).show();
    }

    public static void showBottomAlert(Activity context, int resId) {
        AppMsg.makeText(context, resId, AppMsg.STYLE_ALERT).setLayoutGravity(Gravity.BOTTOM).show();
    }

    public static void showConfirm(Activity context, String msg) {
        AppMsg.makeText(context, msg, AppMsg.STYLE_CONFIRM).show();
    }

    public static void showConfirm(Activity context, int resId) {
        AppMsg.makeText(context, resId, AppMsg.STYLE_CONFIRM).show();
    }

    public static void showBottomConfirm(Activity context, String msg) {
        AppMsg.makeText(context, msg, AppMsg.STYLE_CONFIRM).setLayoutGravity(Gravity.BOTTOM).show();
    }

    public static void showBottomConfirm(Activity context, int resId) {
        AppMsg.makeText(context, resId, AppMsg.STYLE_CONFIRM).setLayoutGravity(Gravity.BOTTOM).show();
    }


    public static void showMsg(Activity context, String msg, AppMsg.Style style) {
        AppMsg.makeText(context, msg, style).show();
    }

    public static void showMsg(Activity context, int resId, AppMsg.Style style) {
        AppMsg.makeText(context, resId, style).show();
    }

    public static void showMsg(Activity context, String msg, AppMsg.Style style, int gravity) {
        AppMsg.makeText(context, msg, style).setLayoutGravity(gravity).show();
    }

    public static void showMsg(Activity context, int resId, AppMsg.Style style, int gravity) {
        AppMsg.makeText(context, resId, style).setLayoutGravity(gravity).show();
    }
}
