package cn.com.reachmedia.rmhandle.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.model.ErrorLogModel;
import cn.com.reachmedia.rmhandle.model.param.ErrorLogParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.ErrorLogController;
import cn.com.reachmedia.rmhandle.utils.CrashHandler;
import cn.com.reachmedia.rmhandle.utils.FileUtils;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import cn.com.reachmedia.rmhandle.utils.TimeUtils;

/**
 * Created by tedyuen on 16-10-26.
 */
public class UploadErrorLogService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();


    }

    List<String> fileNameList;

    private void uploadErrorLog(){
        if(fileNameList!=null && fileNameList.size()>0){
            String fileName = fileNameList.get(0);
            if(fileName!=null){
                File file = new File(fileName);
                if(file.exists()){
                    try{
                        long time = file.lastModified();
                        String errorTime = TimeUtils.getStrByLong(time);
                        String fileId = file.getName();
                        ErrorLogParam errorLogParam = new ErrorLogParam(errorTime,"","");
                        ErrorLogController errorLogController = new ErrorLogController(new UiDisplayListener<ErrorLogModel>() {
                            @Override
                            public void onSuccessDisplay(ErrorLogModel data) {
                                if (data != null) {
                                    if (AppApiContact.ErrorCode.SUCCESS.equals(data.rescode)) {
//                                        System.out.println("upload success:"+data.getFileId());
                                        removeFile(CrashHandler.path+data.getFileId());
                                    }

                                    if(fileNameList!=null && fileNameList.size()>0){
                                        fileNameList.remove(0);
                                    }
                                    uploadErrorLog();
                                }
                            }

                            @Override
                            public void onFailDisplay(String errorMsg) {

                            }
                        });
                        errorLogController.uploadErrorLog(errorLogParam,file,fileId);
                    }catch (Exception e){
                        e.printStackTrace();
                        if(fileNameList!=null && fileNameList.size()>0){
                            fileNameList.remove(0);
                        }
                        uploadErrorLog();
                    }
                }
            }
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("start UploadErrorLogService!");
        fileNameList = new ArrayList<>();
        fileNameList = FileUtils.getAllFileNameList(CrashHandler.path);
        uploadErrorLog();
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
}
