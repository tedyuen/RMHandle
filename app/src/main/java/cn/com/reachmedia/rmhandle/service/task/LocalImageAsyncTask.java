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
public class LocalImageAsyncTask extends AsyncTask<String,Integer,Bitmap[]> {

    private ImageView imageView;
    private Bitmap bitmap;

    public LocalImageAsyncTask(ImageView imageView,Bitmap bitmap) {
        this.imageView = imageView;
        this.bitmap = bitmap;
    }

    @Override
    protected Bitmap[] doInBackground(String... picPath) {
        Bitmap[] result = new Bitmap[2];
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
                Bitmap bitmapTemp2 = ImageUtils.comp(myBitmap4);
                result[0] = bitmapTemp2;
                result[1] = myBitmap4;
            } catch (Exception e) {
                e.printStackTrace();
                return result;
            }
        }
        return result;
    }


    @Override
    protected void onPostExecute(Bitmap[] bitmaps) {
        if(bitmaps[0]!=null){
            imageView.setImageBitmap(bitmaps[0]);
            System.out.println("===>  task has bitmap 11:");

        }
        if(bitmaps[1]!=null){
            bitmap = bitmaps[1];
            System.out.println("===>  task has bitmap 22:");

        }
    }
}
