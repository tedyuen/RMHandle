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

    /**
     * cid : 4
     * cname : 帮宝适
     * descs :
     * pictime : 05月05日
     * showtime : 04月07日至04月27日
     * worktime : 周六
     */

    private List<ClListBean> clList;
    /**
     * cid : 4
     * cname : 帮宝适
     * comcount : 2
     * pointcount : 0
     */

    private List<CrListBean> crList;

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
        private String cid;
        private String cname;
        private String descs;
        private String pictime;
        private String showtime;
        private String worktime;
        /**
         * picurlB : http://120.26.65.65:8085/img/res/images/advsales/admin/scheduling/5/s_20160412173541134_0.png
         * picurlS : http://120.26.65.65:8085/img/res/images/advsales/admin/scheduling/5/s_20160412173541134_0.png
         */

        private List<PicListBean> picList;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getDescs() {
            return descs;
        }

        public void setDescs(String descs) {
            this.descs = descs;
        }

        public String getPictime() {
            return pictime;
        }

        public void setPictime(String pictime) {
            this.pictime = pictime;
        }

        public String getShowtime() {
            return showtime;
        }

        public void setShowtime(String showtime) {
            this.showtime = showtime;
        }

        public String getWorktime() {
            return worktime;
        }

        public void setWorktime(String worktime) {
            this.worktime = worktime;
        }

        public List<PicListBean> getPicList() {
            return picList;
        }

        public void setPicList(List<PicListBean> picList) {
            this.picList = picList;
        }

        public static class PicListBean {
            private String picurlB;
            private String picurlS;

            public String getPicurlB() {
                return picurlB;
            }

            public void setPicurlB(String picurlB) {
                this.picurlB = picurlB;
            }

            public String getPicurlS() {
                return picurlS;
            }

            public void setPicurlS(String picurlS) {
                this.picurlS = picurlS;
            }
        }
    }

    public static class CrListBean {
        private String cid;
        private String cname;
        private int comcount;
        private int pointcount;
        /**
         * communityid : 663
         * communityname : 宝宸怡景苑
         * district : 宝山区
         * pointcount : 0
         * pointing : 0
         */

        private List<ComListBean> comList;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public int getComcount() {
            return comcount;
        }

        public void setComcount(int comcount) {
            this.comcount = comcount;
        }

        public int getPointcount() {
            return pointcount;
        }

        public void setPointcount(int pointcount) {
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
            private int pointcount;
            private int pointing;

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

            public int getPointcount() {
                return pointcount;
            }

            public void setPointcount(int pointcount) {
                this.pointcount = pointcount;
            }

            public int getPointing() {
                return pointing;
            }

            public void setPointing(int pointing) {
                this.pointing = pointing;
            }
        }
    }
}
