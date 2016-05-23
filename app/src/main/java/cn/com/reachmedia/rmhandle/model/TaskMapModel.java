package cn.com.reachmedia.rmhandle.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/26 上午11:12
 * Description: 3.4 首页任务地图
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/26          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class TaskMapModel extends BaseModel implements Parcelable{

    /**
     * comNumber : 32
     * comCount : 100
     * pointNumber : 223
     * pointCount : 345
     * cList : [{"cid":"121","community":"黄埔新苑","lon":"21.33212","lat":"34.33212","status":1,"points":18,"pointe":5}]
     */

    private int comNumber;
    private int comCount;
    private int pointNumber;
    private int pointCount;
    /**
     * cid : 121
     * community : 黄埔新苑
     * lon : 21.33212
     * lat : 34.33212
     * status : 1
     * points : 18
     * pointe : 5
     */

    private List<CListBean> cList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(comNumber);
        dest.writeInt(comCount);
        dest.writeInt(pointNumber);
        dest.writeInt(pointCount);
        dest.writeList(cList);
    }

    public TaskMapModel(){}

    private TaskMapModel(Parcel in){
        comNumber = in.readInt();
        comCount = in.readInt();
        pointNumber = in.readInt();
        pointCount = in.readInt();
        cList = in.readArrayList(CListBean.class.getClassLoader());
    }

    public static final Creator<TaskMapModel> CREATOR = new Creator<TaskMapModel>() {
        public TaskMapModel createFromParcel(Parcel source) {
            return new TaskMapModel(source);
        }

        public TaskMapModel[] newArray(int size) {
            return new TaskMapModel[size];
        }
    };

    public int getComNumber() {
        return comNumber;
    }

    public void setComNumber(int comNumber) {
        this.comNumber = comNumber;
    }

    public int getComCount() {
        return comCount;
    }

    public void setComCount(int comCount) {
        this.comCount = comCount;
    }

    public int getPointNumber() {
        return pointNumber;
    }

    public void setPointNumber(int pointNumber) {
        this.pointNumber = pointNumber;
    }

    public int getPointCount() {
        return pointCount;
    }

    public void setPointCount(int pointCount) {
        this.pointCount = pointCount;
    }

    public List<CListBean> getCList() {
        return cList;
    }

    public void setCList(List<CListBean> cList) {
        this.cList = cList;
    }


    public static class CListBean {
        private String cid;
        private String community;
        private String lon;
        private String lat;
        private int status;
        private int points;
        private int pointe;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public int getPointe() {
            return pointe;
        }

        public void setPointe(int pointe) {
            this.pointe = pointe;
        }
    }
}
