package cn.com.reachmedia.rmhandle.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/25 下午6:19
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/25          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class TaskIndexModel extends BaseModel implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ongoing);
        dest.writeInt(finish);
        dest.writeList(pList);
    }

    public TaskIndexModel(){}

    private TaskIndexModel(Parcel in){
        ongoing = in.readInt();
        finish = in.readInt();
        pList = in.readArrayList(PListBean.class.getClassLoader());
    }

    public static final Creator<TaskIndexModel> CREATOR = new Creator<TaskIndexModel>() {
        public TaskIndexModel createFromParcel(Parcel source) {
            return new TaskIndexModel(source);
        }

        public TaskIndexModel[] newArray(int size) {
            return new TaskIndexModel[size];
        }
    };

    /**
     * ongoing : 10
     * finish : 15
     * pList : [{"communityid":"12","community":"南开大厦","address":"陆家浜路1332号","worktime":"03-10","distance":"300米","lon":"1","lat":"1","locA":10,"locS":15,"isCard":1,"isPswd":1,"workUp":1,"workUpPhone":1,"workDown":0,"workDownPhone":0,"tips":"物业公司袁经理","cList":[{"cid":121,"cname":"阿波罗男子医院"}]}]
     */

    private int ongoing;
    private int finish;
    /**
     * communityid : 12
     * community : 南开大厦
     * address : 陆家浜路1332号
     * worktime : 03-10
     * distance : 300米
     * lon : 1
     * lat : 1
     * locA : 10
     * locS : 15
     * isCard : 1
     * isPswd : 1
     * workUp : 1
     * workUpPhone : 1
     * workDown : 0
     * workDownPhone : 0
     * tips : 物业公司袁经理
     * cList : [{"cid":121,"cname":"阿波罗男子医院"}]
     */

    private List<PListBean> pList;

    public int getOngoing() {
        return ongoing;
    }

    public void setOngoing(int ongoing) {
        this.ongoing = ongoing;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public List<PListBean> getPList() {
        return pList;
    }

    public void setPList(List<PListBean> pList) {
        this.pList = pList;
    }

    public static class PListBean {
        private String communityid;
        private String community;
        private String address;
        private String worktime;
        private String distance;
        private String lon;
        private String lat;
        private int locA;
        private int locS;
        private int isCard;
        private int isPswd;
        private int workUp;
        private int workUpPhone;
        private int workDown;
        private int workDownPhone;
        private String tips;
        /**
         * cid : 121
         * cname : 阿波罗男子医院
         */

        private List<CListBean> cList;

        public String getCommunityid() {
            return communityid;
        }

        public void setCommunityid(String communityid) {
            this.communityid = communityid;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getWorktime() {
            return worktime;
        }

        public void setWorktime(String worktime) {
            this.worktime = worktime;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
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

        public int getLocA() {
            return locA;
        }

        public void setLocA(int locA) {
            this.locA = locA;
        }

        public int getLocS() {
            return locS;
        }

        public void setLocS(int locS) {
            this.locS = locS;
        }

        public int getIsCard() {
            return isCard;
        }

        public void setIsCard(int isCard) {
            this.isCard = isCard;
        }

        public int getIsPswd() {
            return isPswd;
        }

        public void setIsPswd(int isPswd) {
            this.isPswd = isPswd;
        }

        public int getWorkUp() {
            return workUp;
        }

        public void setWorkUp(int workUp) {
            this.workUp = workUp;
        }

        public int getWorkUpPhone() {
            return workUpPhone;
        }

        public void setWorkUpPhone(int workUpPhone) {
            this.workUpPhone = workUpPhone;
        }

        public int getWorkDown() {
            return workDown;
        }

        public void setWorkDown(int workDown) {
            this.workDown = workDown;
        }

        public int getWorkDownPhone() {
            return workDownPhone;
        }

        public void setWorkDownPhone(int workDownPhone) {
            this.workDownPhone = workDownPhone;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public List<CListBean> getCList() {
            return cList;
        }

        public void setCList(List<CListBean> cList) {
            this.cList = cList;
        }

        public static class CListBean {
            private int cid;
            private String cname;

            public int getCid() {
                return cid;
            }

            public void setCid(int cid) {
                this.cid = cid;
            }

            public String getCname() {
                return cname;
            }

            public void setCname(String cname) {
                this.cname = cname;
            }
        }
    }
}
