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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(comNumber);
        dest.writeInt(comCount);
        dest.writeInt(piontNumber);
        dest.writeInt(piontCount);
        dest.writeList(cList);
    }

    public TaskMapModel(){}

    private TaskMapModel(Parcel in){
        comNumber = in.readInt();
        comCount = in.readInt();
        piontNumber = in.readInt();
        piontCount = in.readInt();
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

    /**
     * comNumber : 32
     * comCount : 100
     * piontNumber : 223
     * piontCount : 345
     * cList : [{"cid":"121","community":"黄埔新苑","lon":"21.33212","lat":"34.33212","status":1,"pionts":18,"pionte":5}]
     */

    private int comNumber;
    private int comCount;
    private int piontNumber;
    private int piontCount;
    /**
     * cid : 121
     * community : 黄埔新苑
     * lon : 21.33212
     * lat : 34.33212
     * status : 1
     * pionts : 18
     * pionte : 5
     */

    private List<CListBean> cList;

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

    public int getPiontNumber() {
        return piontNumber;
    }

    public void setPiontNumber(int piontNumber) {
        this.piontNumber = piontNumber;
    }

    public int getPiontCount() {
        return piontCount;
    }

    public void setPiontCount(int piontCount) {
        this.piontCount = piontCount;
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
        private int pionts;
        private int pionte;

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

        public int getPionts() {
            return pionts;
        }

        public void setPionts(int pionts) {
            this.pionts = pionts;
        }

        public int getPionte() {
            return pionte;
        }

        public void setPionte(int pionte) {
            this.pionte = pionte;
        }
    }
}
