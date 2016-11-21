package cn.com.reachmedia.rmhandle.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;
import java.util.List;

import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.CompImageBean;
import cn.com.reachmedia.rmhandle.db.utils.CompImageDbUtil;
import cn.com.reachmedia.rmhandle.ui.view.LineImageLayout;
import cn.com.reachmedia.rmhandle.utils.ImageUtils;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;
import cn.com.reachmedia.rmhandle.utils.TimeUtils;

/**
 * Created by tedyuen on 16-11-21.
 */
public class CompImageService extends Service{

    private CompImageDbUtil compImageDbUtil;

    @Override
    public void onCreate(){
        super.onCreate();
        compImageDbUtil = CompImageDbUtil.getIns();
    }

    String userId;

    List<CompImageBean> dataList;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("start CompImageService!");
        new AsyncTask<String,Integer,Integer>(){

            @Override
            protected Integer doInBackground(String... strings) {
                userId = SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID);
                if(userId!=null){
                    dataList = compImageDbUtil.getUpload(userId,0);
                    compressImage();
                }
                return 0;
            }
        }.execute();

        return super.onStartCommand(intent, flags, startId);
    }

    private void compressImage(){
        if(dataList!=null && dataList.size()>0) {
            try{
                CompImageBean bean = dataList.get(0);
                long lastModifyTime = System.currentTimeMillis();
                mergeImage(bean,lastModifyTime);
                bean.setState(1);
                compImageDbUtil.updateOneData(bean);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(dataList!=null && dataList.size()>0){
                    dataList.remove(0);
                    compressImage();
                }
            }
        }
    }


    /**
     * 生成水印图片
     * @param bean
     * @param lastModifyTime
     */
    public void mergeImage(CompImageBean bean,long lastModifyTime){
        long time1 = System.currentTimeMillis();
        //是否需要水印 true:是
        if(bean.getWater_mask()){
            File sourceFile = new File(bean.getSource_path());
            if(sourceFile.exists()){
                lastModifyTime = sourceFile.lastModified();
            }

            Bitmap source = null;
            long time2 = System.currentTimeMillis();
            int b = ImageUtils.getBitmapDegree(bean.getTarget_path());
            try{
                if (b != 0) {
                    source = ImageUtils.rotateBitMap(ImageUtils.getBitmapByPath(bean.getTarget_path()), b);
                } else {
                    source = ImageUtils.getBitmapByPath(bean.getTarget_path());
                    long time32 = System.currentTimeMillis();
                    System.out.println("time2:  "+(time32-time2));
                }
            }catch (Exception e){
                e.printStackTrace();
                if (b != 0) {
                    source = ImageUtils.rotateBitMap(ImageUtils.getBitmapByPath(bean.getTarget_path()), b);
                } else {
                    source = ImageUtils.getBitmapByPath(bean.getTarget_path());
                }
            }catch (Error error){
                error.printStackTrace();
                if (b != 0) {
                    source = ImageUtils.rotateBitMap(ImageUtils.getBitmapByPath(bean.getTarget_path()), b);
                } else {
                    source = ImageUtils.getBitmapByPath(bean.getTarget_path());
                }
            }
            long time3 = System.currentTimeMillis();

            if(source!=null){
                Bitmap target = null;
                try{
//                    source = ImageUtils.compLocal(source);
                    String leftText = TimeUtils.getWaterMarkDate(lastModifyTime,0);
                    String rightText = TimeUtils.getWaterMarkDate(lastModifyTime,1);
                    target = ImageUtils.drawTextToRightBottom(getApplicationContext(),source,leftText,rightText,16, Color.WHITE,10,20);
                    if(target!=null){
                        ImageUtils.saveCompressPicPath(target,bean.getTarget_path(), LineImageLayout.photo_path);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(source!=null){
                        source.recycle();
                    }
                    if(target!=null){
                        target.recycle();
                    }
                }
            }
            long time4 = System.currentTimeMillis();
            System.out.println("time3:  "+(time4-time3));
        }else{//没有水印
            Bitmap source = null;
            int b = ImageUtils.getBitmapDegree(bean.getTarget_path());
            long time2 = System.currentTimeMillis();
            System.out.println("time1:  "+(time2-time1));

            try{
                if (b != 0) {
                    source = ImageUtils.rotateBitMap(ImageUtils.getBitmapByPath(bean.getTarget_path()), b);
                } else {
                    source = ImageUtils.getBitmapByPath(bean.getTarget_path());
                }
            }catch (Exception e){
                e.printStackTrace();
                if (b != 0) {
                    source = ImageUtils.rotateBitMap(ImageUtils.getBitmapByPath(bean.getTarget_path()), b);
                } else {
                    source = ImageUtils.getBitmapByPath(bean.getTarget_path());
                }
            }catch (Error error){
                error.printStackTrace();
                if (b != 0) {
                    source = ImageUtils.rotateBitMap(ImageUtils.getBitmapByPath(bean.getTarget_path()), b);
                } else {
                    source = ImageUtils.getBitmapByPath(bean.getTarget_path());
                }
            }
            long time3 = System.currentTimeMillis();
            System.out.println("time2:  "+(time3-time2));
            if(source!=null){
                try{
//                    source = ImageUtils.compLocal(source);
                    ImageUtils.saveCompressPicPath(source,bean.getTarget_path(),LineImageLayout.photo_path);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(source!=null){
                        source.recycle();
                    }
                }
            }
            long time4 = System.currentTimeMillis();
            System.out.println("time3:  "+(time4-time3));

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
