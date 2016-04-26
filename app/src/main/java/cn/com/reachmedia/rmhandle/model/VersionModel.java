package cn.com.reachmedia.rmhandle.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/26 上午11:49
 * Description: 4.1 版本检测接口
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/26          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class VersionModel extends BaseModel implements Parcelable{

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(isalert);
        dest.writeString(isupdate);
        dest.writeString(newversion);
        dest.writeString(gourl);
        dest.writeString(content);
    }

    public VersionModel(){}

    private VersionModel(Parcel in){
        isalert = in.readString();
        isupdate = in.readString();
        newversion = in.readString();
        gourl = in.readString();
        content = in.readString();
    }

    public static final Creator<VersionModel> CREATOR = new Creator<VersionModel>() {
        public VersionModel createFromParcel(Parcel source) {
            return new VersionModel(source);
        }

        public VersionModel[] newArray(int size) {
            return new VersionModel[size];
        }
    };

    /**
     * isalert : 1
     * isupdate : 1
     * newversion : 2.0
     * gourl : http://www.XXX.XXX
     * content : 请尽快更新!
     */

    private String isalert;
    private String isupdate;
    private String newversion;
    private String gourl;
    private String content;

    public String getIsalert() {
        return isalert;
    }

    public void setIsalert(String isalert) {
        this.isalert = isalert;
    }

    public String getIsupdate() {
        return isupdate;
    }

    public void setIsupdate(String isupdate) {
        this.isupdate = isupdate;
    }

    public String getNewversion() {
        return newversion;
    }

    public void setNewversion(String newversion) {
        this.newversion = newversion;
    }

    public String getGourl() {
        return gourl;
    }

    public void setGourl(String gourl) {
        this.gourl = gourl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
