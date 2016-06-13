package cn.com.reachmedia.rmhandle.ui.bean;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/13 上午11:34
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/13          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class SynchronzieBean {

    private String communityName;
    private String cname;
    private String pointTime;
    private String photoTime;
    private int pointCount;
    private int photoCount;

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getPointTime() {
        return pointTime;
    }

    public void setPointTime(String pointTime) {
        this.pointTime = pointTime;
    }

    public String getPhotoTime() {
        return photoTime;
    }

    public void setPhotoTime(String photoTime) {
        this.photoTime = photoTime;
    }

    public int getPointCount() {
        return pointCount;
    }

    public void setPointCount(int pointCount) {
        this.pointCount = pointCount;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }
}
