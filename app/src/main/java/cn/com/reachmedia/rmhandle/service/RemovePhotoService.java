package cn.com.reachmedia.rmhandle.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;
import java.util.List;

import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.CommDoorPicBean;
import cn.com.reachmedia.rmhandle.bean.PointWorkBean;
import cn.com.reachmedia.rmhandle.db.utils.CommPoorPicDbUtil;
import cn.com.reachmedia.rmhandle.db.utils.PointWorkBeanDbUtil;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;
import cn.com.reachmedia.rmhandle.utils.StringUtils;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/8/12 下午2:23
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/8/12          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class RemovePhotoService extends Service {


    private PointWorkBeanDbUtil pointWorkBeanDbUtil;
    private CommPoorPicDbUtil commPoorPicDbUtil;


    @Override
    public void onCreate() {
        super.onCreate();
        pointWorkBeanDbUtil = PointWorkBeanDbUtil.getIns();
        commPoorPicDbUtil = CommPoorPicDbUtil.getIns();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("start DoorPicService!");

        List<CommDoorPicBean> list = commPoorPicDbUtil.getUpload("1");
        for(CommDoorPicBean bean:list){
            if(bean!=null){
                removeFile(bean.getCommunityFile1());
                removeFile(bean.getCommunityFile2());
                removeFile(bean.getCommunitySpace1());
                removeFile(bean.getCommunitySpace2());
            }
        }

        String userId = SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID);
        List<PointWorkBean> list2 = pointWorkBeanDbUtil.getUpload(userId,"2");
        for(PointWorkBean bean:list2){
            if(bean!=null){
                String[] filePaths = new String[0];
                if(bean.getFilePathData()!=null){
                    filePaths = bean.getFilePathData().split(PointWorkBeanDbUtil.FILE_SPLIT);
                }
                for(int i=0;i<filePaths.length;i++){
                    removeFile(filePaths[i]);
                }
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void removeFile(String path){
        if(!StringUtils.isEmpty(path)){
            deleteFileSelf(new File(path));
        }
    }

    public void deleteFileSelf(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    this.deleteFileSelf(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        } else {
//            Constants.Logdada("文件不存在！"+"\n");
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
