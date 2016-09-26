package cn.com.reachmedia.rmhandle.utils;

import java.util.ArrayList;
import java.util.List;

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
        }
        return instance;
    }


    private List<String> communityIds;

    public void cleanData(){
        communityIds = new ArrayList<>();


    }


    public List<String> getCommunityIds() {
        return communityIds;
    }

    public void setCommunityIds(List<String> communityIds) {
        this.communityIds = communityIds;
    }
}
