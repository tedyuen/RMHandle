package cn.com.reachmedia.rmhandle.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/17 下午5:34
 * Description: 图片保存
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/17          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PhotoSavePathUtil {

    private final static String TAG = "PhotoSavePathUtil";
    /**
     * 检验SDcard状态
     *
     * @return boolean
     */
    public static boolean checkSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 保存文件文件到目录
     *
     * @param context
     * @return 文件保存的目录
     */
    public static String setMkdir(Context context) {
        String filePath;
        if (checkSDCard()) {
            // 有sd卡时，存放在sd卡路径下SoccerBaby文件中
            filePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "RMHandle";
        } else {
            // 没有sd卡时，存放在缓存路径中
            filePath = context.getCacheDir().getAbsolutePath();
        }
        File file = new File(filePath);
        if (!file.exists()) {
            boolean b = file.mkdirs();
            LogUtils.e(TAG,"文件不存在  创建文件    " + b);
            LogUtils.e(TAG,"路径为"+filePath);

        } else {
            LogUtils.i(TAG, "文件存在");
        }
        return filePath;
    }
}
