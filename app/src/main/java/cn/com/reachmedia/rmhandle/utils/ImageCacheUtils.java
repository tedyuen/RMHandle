package cn.com.reachmedia.rmhandle.utils;

import java.util.ArrayList;
import java.util.List;

import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.ui.bean.ImageCacheResBean;

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
            instance.imageCacheResBeens = new ArrayList<>();
        }
        return instance;
    }

    //首页需要查询的community id
    private List<String> communityIds;
    private List<String> communityIdsA;
    private List<String> communityIdsB;
    //需要下载的所有图片信息
    private List<ImageCacheResBean> imageCacheResBeens;

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

    public List<ImageCacheResBean> getImageCacheResBeens() {
        return imageCacheResBeens;
    }

    public void setImageCacheResBeens(List<ImageCacheResBean> imageCacheResBeens) {
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
