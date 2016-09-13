package cn.com.reachmedia.rmhandle.service.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.FileInputStream;

import cn.com.reachmedia.rmhandle.utils.ImageUtils;
import cn.com.reachmedia.rmhandle.utils.StringUtils;

/**
 * Created by tedyuen on 16-9-13.
 */
public class LocalImageAsyncTask extends AsyncTask<String,Integer,Bitmap> {

    private ImageView imageView;
    private boolean compFlag;//true:需要压缩 false:不需要压缩

    public LocalImageAsyncTask(ImageView imageView,boolean compFlag) {
        this.imageView = imageView;
        this.compFlag = compFlag;
    }

    @Override
    protected Bitmap doInBackground(String... picPath) {
        System.out.println("===>  start task:"+picPath[0]);
        if(!StringUtils.isEmpty(picPath[0])){
            try {
                Bitmap myBitmap4 = null;
                byte[] mContent3 = ImageUtils.readStream(new FileInputStream(picPath[0]));
                int b = ImageUtils.getExifOrientation(picPath[0]);
                if (b != 0) {
                    myBitmap4 = ImageUtils.rotateBitMap(ImageUtils.getPicFromBytes(mContent3, ImageUtils.getBitmapOption()), b);
                } else {
                    myBitmap4 = ImageUtils.getPicFromBytes(mContent3, ImageUtils.getBitmapOption());
                }
                if(compFlag){
                    Bitmap bitmapTemp2 = ImageUtils.comp(myBitmap4);
                    myBitmap4.recycle();
                    return bitmapTemp2;
                }else{
                    return myBitmap4;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
        }
    }
}
