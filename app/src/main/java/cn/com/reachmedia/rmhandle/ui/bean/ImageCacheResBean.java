package cn.com.reachmedia.rmhandle.ui.bean;


import cn.com.reachmedia.rmhandle.bean.ImageCacheBean;
import cn.com.reachmedia.rmhandle.db.helper.ImageCacheDaoHelper;
import cn.com.reachmedia.rmhandle.utils.TimeUtils;

/**
 * Created by tedyuen on 16-9-27.
 */
public class ImageCacheResBean {

    private String url;
    private String path;
    private String startTime;
    private String createTime;
    private String communityId;

    public ImageCacheResBean(){

    }

    public ImageCacheResBean(String[] initData){
        if(initData!=null && initData.length==2){
            startTime = initData[0];
            communityId = initData[1];
        }
    }

    public ImageCacheBean returnBean(ImageCacheDaoHelper imageCacheDaoHelper){
        ImageCacheBean bean = imageCacheDaoHelper.getBeanByUrl(url);
        if(bean==null){
            bean = new ImageCacheBean(url,path,
                    TimeUtils.simpleDateParse(startTime,"yyyy-MM-dd"),
                    TimeUtils.simpleDateParse(createTime,"yyyy-MM-dd"),
                    0l,communityId);
        }
        return bean;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("communityId:");
        buffer.append(communityId);
        buffer.append("\tstartTime:");
        buffer.append(startTime);
        buffer.append("\tcreateTime:");
        buffer.append(createTime);
        buffer.append("\turl:");
        buffer.append(url);
        buffer.append("\tpath:");
        buffer.append(path);

        return buffer.toString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    @Override
    public boolean equals(Object o) {
        return url.equals(((ImageCacheResBean)o).getUrl());
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }
}
