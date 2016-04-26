package cn.com.reachmedia.rmhandle.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/26 上午10:43
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/26          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class TaskDetailModel extends BaseModel implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(clList);
        dest.writeList(crList);
    }

    public TaskDetailModel(){}

    private TaskDetailModel(Parcel in){
        clList = in.readArrayList(ClListBean.class.getClassLoader());
        crList = in.readArrayList(CrListBean.class.getClassLoader());
    }

    public static final Creator<TaskDetailModel> CREATOR = new Creator<TaskDetailModel>() {
        public TaskDetailModel createFromParcel(Parcel source) {
            return new TaskDetailModel(source);
        }

        public TaskDetailModel[] newArray(int size) {
            return new TaskDetailModel[size];
        }
    };


    /**
     * cid : 121
     * cname : 阿波罗男子医院
     * showtime : 6月20日至7月1日
     * pictime : 6月05日
     * worktime : 周四
     * descs :
     */

    private List<ClListBean> clList;
    /**
     * cid : 121
     * cname : 阿波罗男子医院
     * comcount : 59
     * pointcount : 998
     * comList : [{"communityid":"12","communityname":"南开大厦","district":"卢湾区","pointing":"10","pointcount":"15"}]
     */

    private List<CrListBean> crList;

    public List<ClListBean> getClList() {
        return clList;
    }

    public void setClList(List<ClListBean> clList) {
        this.clList = clList;
    }

    public List<CrListBean> getCrList() {
        return crList;
    }

    public void setCrList(List<CrListBean> crList) {
        this.crList = crList;
    }

    public static class ClListBean {
        private int cid;
        private String cname;
        private String showtime;
        private String pictime;
        private String worktime;
        private String descs;

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

        public String getShowtime() {
            return showtime;
        }

        public void setShowtime(String showtime) {
            this.showtime = showtime;
        }

        public String getPictime() {
            return pictime;
        }

        public void setPictime(String pictime) {
            this.pictime = pictime;
        }

        public String getWorktime() {
            return worktime;
        }

        public void setWorktime(String worktime) {
            this.worktime = worktime;
        }

        public String getDescs() {
            return descs;
        }

        public void setDescs(String descs) {
            this.descs = descs;
        }
    }

    public static class CrListBean {
        private int cid;
        private String cname;
        private String comcount;
        private String pointcount;
        /**
         * communityid : 12
         * communityname : 南开大厦
         * district : 卢湾区
         * pointing : 10
         * pointcount : 15
         */

        private List<ComListBean> comList;

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

        public String getComcount() {
            return comcount;
        }

        public void setComcount(String comcount) {
            this.comcount = comcount;
        }

        public String getPointcount() {
            return pointcount;
        }

        public void setPointcount(String pointcount) {
            this.pointcount = pointcount;
        }

        public List<ComListBean> getComList() {
            return comList;
        }

        public void setComList(List<ComListBean> comList) {
            this.comList = comList;
        }

        public static class ComListBean {
            private String communityid;
            private String communityname;
            private String district;
            private String pointing;
            private String pointcount;

            public String getCommunityid() {
                return communityid;
            }

            public void setCommunityid(String communityid) {
                this.communityid = communityid;
            }

            public String getCommunityname() {
                return communityname;
            }

            public void setCommunityname(String communityname) {
                this.communityname = communityname;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getPointing() {
                return pointing;
            }

            public void setPointing(String pointing) {
                this.pointing = pointing;
            }

            public String getPointcount() {
                return pointcount;
            }

            public void setPointcount(String pointcount) {
                this.pointcount = pointcount;
            }
        }
    }
}
