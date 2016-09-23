package cn.com.reachmedia.rmhandle.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.com.reachmedia.rmhandle.ui.bean.PictureBean;
import cn.com.reachmedia.rmhandle.ui.view.imagepager.ImageAllBean;
import cn.com.reachmedia.rmhandle.ui.view.imagepager.ImageAllPagerActivity;
import cn.com.reachmedia.rmhandle.ui.view.imagepager.ImageMergePagerActivity;
import cn.com.reachmedia.rmhandle.ui.view.imagepager.ImagePagerActivity;
import cn.com.reachmedia.rmhandle.ui.view.imagepager.NewImageAllPagerActivity;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/9 下午6:58
 * Description: View 帮助类
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/9          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class ViewHelper {

    /**
     * 设置View 是否显示
     *
     * @param view   需要设置的View对象
     * @param isGone 是否隐藏
     * @param <V>    V
     * @return V 当前View
     */
    public static <V extends View> V setGone(V view, boolean isGone) {
        if (view != null) {
            if (isGone) {
                if (View.GONE != view.getVisibility())
                    view.setVisibility(View.GONE);
            } else {
                if (View.VISIBLE != view.getVisibility())
                    view.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }

    /**
     * 点击放大图片
     * @param context
     * @param urlList
     * @param index
     */
    public static void getImagePager(Context context, List<String> urlList, final int index,boolean merge,int localSize){
        Intent intent = new Intent();
        intent.setClass(context.getApplicationContext(),ImagePagerActivity.class);
        String[] temp = new String[urlList.size()];
        for(int i=0;i<urlList.size();i++){
            temp[i] = urlList.get(i);
        }
        intent.putExtra("images", temp);
        intent.putExtra("image_index", index);
        intent.putExtra("merge", merge);
        intent.putExtra("local_size", localSize);
        context.startActivity(intent);
    }


    public static void getAllImagePager(Context context, List<ImageAllBean> imageDatas,int index){
        Intent intent = new Intent();
        intent.setClass(context.getApplicationContext(), ImageAllPagerActivity.class);
        intent.putExtra("image_index", index);

        ImageAllPagerActivity.imageData = imageDatas;

        context.startActivity(intent);
    }

    public static void getNewAllImagePager(Context context,List<PictureBean> resultDatas,int index,int maxCount){
        Intent intent = new Intent();
        intent.setClass(context.getApplicationContext(), NewImageAllPagerActivity.class);
        intent.putExtra("image_index", index);
        List<PictureBean> result = new ArrayList<>();
        for(int i=0;i<Math.min(maxCount,resultDatas.size());i++){
            if(!resultDatas.get(0).isDeleted()){
                result.add(resultDatas.get(0));
            }
        }
        NewImageAllPagerActivity.resultDatas = result;
        context.startActivity(intent);
    }

    public static void getNewImagePager(Context context, List<String> urlList, List<Boolean> imageFlag, List<Bitmap> imageLocal,List<String> imagePaths,int index){
        Intent intent = new Intent();
        intent.setClass(context.getApplicationContext(),ImageMergePagerActivity.class);
        String[] temp = new String[urlList.size()];
        for(int i=0;i<urlList.size();i++){
            temp[i] = urlList.get(i);
        }
        boolean[] tempImageFlag = new boolean[imageFlag.size()];
        for(int i=0;i<imageFlag.size();i++){
            tempImageFlag[i] = imageFlag.get(i);
        }

        intent.putExtra("images", temp);
        intent.putExtra("images_merge_flag", tempImageFlag);
        intent.putExtra("image_index", index);

        ImageMergePagerActivity.imageLocal = imageLocal;
        ImageMergePagerActivity.imagePaths = imagePaths;

        context.startActivity(intent);
    }

    /**
     * 点击放大本地图片
     * @param context
     * @param index
     */
    public static void getImagePagerLocal(Context context,final int index){
        Intent intent = new Intent();
        intent.setClass(context.getApplicationContext(),ImagePagerActivity.class);
//        intent.putExtra("imagesLocal", uriArray);
        intent.putExtra("image_index", index);
        context.startActivity(intent);
    }

}
