package cn.com.reachmedia.rmhandle.ui.bean;


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
}
