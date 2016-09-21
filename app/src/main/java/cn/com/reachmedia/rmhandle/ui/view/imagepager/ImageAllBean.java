package cn.com.reachmedia.rmhandle.ui.view.imagepager;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import cn.com.reachmedia.rmhandle.service.task.LocalImageAsyncTask;
import cn.com.reachmedia.rmhandle.utils.StringUtils;

/**
 * Created by tedyuen on 16-9-14.
 */
public class ImageAllBean {

    private String str;
    private int type=URL_IMG;

    public final static int URL_IMG = 1;
    public final static int LOCAL_PATH_IMG = 2;
    public final static int LOCAL_URI_IMG = 3;

    public ImageAllBean(String str,int type){
        this.str = str;
        this.type = type;
    }

    public final static int THUMBNAIL_WIDTH = 300;
    public final static int THUMBNAIL_HEIGHT = 261;

    public void doPicasso(Context context, ImageView imageView,int targetWidth,int targetHeight){
        if(!StringUtils.isEmpty(str)){
            switch (type){
                case URL_IMG:
                    Picasso.with(context).load(str).resize(targetWidth,targetHeight).centerCrop().into(imageView);
                    break;
                case LOCAL_PATH_IMG:
                    Picasso.with(context).load(new File(str)).resize(targetWidth,targetHeight).centerCrop().into(imageView);
                    break;
                case LOCAL_URI_IMG:
                    Picasso.with(context).load(str).resize(targetWidth,targetHeight).centerCrop().into(imageView);
                    break;
            }
        }
    }

    public void doPicasso(Context context, ImageView imageView){
        if(!StringUtils.isEmpty(str)) {
            switch (type) {
                case URL_IMG:
                    Picasso.with(context).load(str).into(imageView);
                    break;
                case LOCAL_PATH_IMG:
//                    Picasso.with(context).load(new File(str)).resize(1080,1920).centerCrop().into(imageView);
                    LocalImageAsyncTask task = new LocalImageAsyncTask(imageView,false);
                    task.execute(str);
                    break;
                case LOCAL_URI_IMG:
                    Picasso.with(context).load(str).into(imageView);
                    break;
            }
        }
    }
}
