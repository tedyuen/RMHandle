package cn.com.reachmedia.rmhandle.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import java.util.List;

import cn.com.reachmedia.rmhandle.ui.view.imagepager.ImagePagerActivity;

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
