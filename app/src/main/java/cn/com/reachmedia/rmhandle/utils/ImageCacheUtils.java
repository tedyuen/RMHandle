package cn.com.reachmedia.rmhandle.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.ImageCacheBean;
import cn.com.reachmedia.rmhandle.db.helper.ImageCacheDaoHelper;
import cn.com.reachmedia.rmhandle.ui.bean.ImageCacheResBean;
import cn.com.reachmedia.rmhandle.utils.pictureutils.utils.SimpleImageLoader;

/**
 * Created by tedyuen on 16-9-26.
 */
public class ImageCacheUtils {

    private static ImageCacheUtils instance;
    private ImageCacheUtils(){

    }

    public static ImageCacheUtils getInstance(){
        if(instance == null){
            instance = new ImageCacheUtils();
            instance.communityIds = new ArrayList<>();
            instance.communityIdsA = new ArrayList<>();
            instance.communityIdsB = new ArrayList<>();
            instance.imageCacheResBeens = new LinkedHashSet<>();
        }
        return instance;
    }

    //首页需要查询的community id
    private List<String> communityIds;
    private List<String> communityIdsA;
    private List<String> communityIdsB;
    //需要下载的所有图片信息
    private LinkedHashSet<ImageCacheResBean> imageCacheResBeens;

    //清空查询条件
    public void cleanData(){
        communityIds.clear();
        communityIdsA.clear();
        communityIdsB.clear();
        imageCacheResBeens.clear();
    }

    public void mergeCommunityAB(){
        communityIds.clear();
        communityIds.addAll(communityIdsA);
        communityIds.addAll(communityIdsB);
    }

    public static boolean displayImage(String url, ImageView imageView){
//        System.out.println("local path 3: "+url);
        ImageCacheBean bean = ImageCacheDaoHelper.getInstance().getBeanByUrl(url);
        if(bean!=null && !StringUtils.isEmpty(bean.getPath())){
            File file = new File(bean.getPath());
//            System.out.println("file path: "+bean.getPath());
            if(file.exists()){
                SimpleImageLoader.displayImage(file,imageView);
//                System.out.println("local path 2: "+url);
                return true;
            }
        }
        return false;
    }

    public static void displayLocalOrUrl(Context context,String url, ImageView imageView){
        displayLocalOrUrl(context,url,imageView,300,261);
    }

    public static void displayLocalOrUrl(Context context,String url, ImageView imageView,int targetWidth, int targetHeight){
        boolean tempBoolean = ImageCacheUtils.getInstance().displayImage(url,imageView);
        if(!tempBoolean) {
            Picasso.with(context).load(url).placeholder(R.drawable.abc).resize(targetWidth, targetHeight).centerCrop().into(imageView);
        }
    }





    public void addCommunityIds(String cId,int type){
        if(type== AppSpContact.SP_KEY_UNDONE){
            this.communityIdsA.add(cId);
        }else if(type==AppSpContact.SP_KEY_DONE){
            this.communityIdsB.add(cId);
        }
    }

    public void addPointBean(ImageCacheResBean bean){
        imageCacheResBeens.add(bean);
    }

    public LinkedHashSet<ImageCacheResBean> getImageCacheResBeens() {
        return imageCacheResBeens;
    }

    public void setImageCacheResBeens(LinkedHashSet<ImageCacheResBean> imageCacheResBeens) {
        this.imageCacheResBeens = imageCacheResBeens;
    }

    public List<String> getCommunityIdsA() {
        return communityIdsA;
    }

    public void setCommunityIdsA(List<String> communityIdsA) {
        this.communityIdsA = communityIdsA;
    }

    public List<String> getCommunityIdsB() {
        return communityIdsB;
    }

    public void setCommunityIdsB(List<String> communityIdsB) {
        this.communityIdsB = communityIdsB;
    }

    public List<String> getCommunityIds() {
        return communityIds;
    }

    public void setCommunityIds(List<String> communityIds) {
        this.communityIds = communityIds;
    }
}
