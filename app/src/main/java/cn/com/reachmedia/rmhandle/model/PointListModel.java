package cn.com.reachmedia.rmhandle.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import cn.com.reachmedia.rmhandle.bean.PointBean;
import cn.com.reachmedia.rmhandle.bean.PointWorkBean;
import cn.com.reachmedia.rmhandle.db.utils.PointWorkBeanDbUtil;
import cn.com.reachmedia.rmhandle.utils.TimeUtils;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/26 上午11:40
 * Description: 3.5 进入小区接口
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/26          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PointListModel extends BaseModel implements Parcelable {

    /**
     * communityid : 33
     * community : 黄埔新苑
     * doordesc : 1号楼2121
     * carddesc : 找老袁
     * newNumber : 32
     * endNumber : 100
     * errorNumber : 223
     * telephone : 18017494728_58876837
     * cGatePic : http://aa.ss.s
     * cPestPic : http://aa.ss.s
     * newList : [{"workId":"22","cid":"121","cname":"肯德基","doorId":"109","door":"1号楼","workUp":1,"workDown":0,"workDownPhone":1,"workCheck":0,"pointId":"1233","ground":1,"cDoorPic":"http://aa.ss.s","errorDesc":"破损","isPhoto":1,"state":0,"stateType":0,"updateTime":"2016-05-09 10:00:00","pictime":"6月05日","worktime":"周四","pFileList":[{"fileId":"12","fileUrlB":"http://aa.ss.s","fileUrlS":"http://aa.ss.s"}]}]
     * comList : [{"cid":"12123","cname":"美莱","memo":"上刊要求富文本","picList":[{"picurlB":"http://aa.ss.s","picurlS":"http://aa.ss.s"}]}]
     */

    private String communityid;
    private String community;
    private String doordesc;
    private String carddesc;
    private int newNumber;
    private int endNumber;
    private int errorNumber;
    private String telephone;
    private String cGatePic;
    private String cPestPic;
    /**
     * workId : 22
     * cid : 121
     * cname : 肯德基
     * doorId : 109
     * door : 1号楼
     * workUp : 1
     * workDown : 0
     * workDownPhone : 1
     * workCheck : 0
     * pointId : 1233
     * ground : 1
     * cDoorPic : http://aa.ss.s
     * errorDesc : 破损
     * isPhoto : 1
     * state : 0
     * stateType : 0
     * updateTime : 2016-05-09 10:00:00
     * pictime : 6月05日
     * worktime : 周四
     * pFileList : [{"fileId":"12","fileUrlB":"http://aa.ss.s","fileUrlS":"http://aa.ss.s"}]
     */

    private List<NewListBean> newList;
    /**
     * cid : 12123
     * cname : 美莱
     * memo : 上刊要求富文本
     * picList : [{"picurlB":"http://aa.ss.s","picurlS":"http://aa.ss.s"}]
     */

    private List<ComListBean> comList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(communityid);
        dest.writeString(community);
        dest.writeString(doordesc);
        dest.writeString(carddesc);
        dest.writeInt(newNumber);
        dest.writeInt(endNumber);
        dest.writeInt(errorNumber);
        dest.writeString(telephone);
        dest.writeString(cGatePic);
        dest.writeString(cPestPic);
        dest.writeList(newList);
        dest.writeList(comList);
    }

    public PointListModel(){}

    private PointListModel(Parcel in){
        communityid = in.readString();
        community = in.readString();
        doordesc = in.readString();
        carddesc = in.readString();
        newNumber = in.readInt();
        newNumber = in.readInt();
        errorNumber = in.readInt();
        telephone = in.readString();
        cGatePic = in.readString();
        cPestPic = in.readString();
        newList = in.readArrayList(NewListBean.class.getClassLoader());
        comList = in.readArrayList(ComListBean.class.getClassLoader());
    }

    public static final Creator<PointListModel> CREATOR = new Creator<PointListModel>() {
        public PointListModel createFromParcel(Parcel source) {
            return new PointListModel(source);
        }

        public PointListModel[] newArray(int size) {
            return new PointListModel[size];
        }
    };

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

    public String getDoordesc() {
        return doordesc;
    }

    public void setDoordesc(String doordesc) {
        this.doordesc = doordesc;
    }

    public String getCarddesc() {
        return carddesc;
    }

    public void setCarddesc(String carddesc) {
        this.carddesc = carddesc;
    }

    public int getNewNumber() {
        return newNumber;
    }

    public void setNewNumber(int newNumber) {
        this.newNumber = newNumber;
    }

    public int getEndNumber() {
        return endNumber;
    }

    public void setEndNumber(int endNumber) {
        this.endNumber = endNumber;
    }

    public int getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(int errorNumber) {
        this.errorNumber = errorNumber;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCGatePic() {
        return cGatePic;
    }

    public void setCGatePic(String cGatePic) {
        this.cGatePic = cGatePic;
    }

    public String getCPestPic() {
        return cPestPic;
    }

    public void setCPestPic(String cPestPic) {
        this.cPestPic = cPestPic;
    }

    public List<NewListBean> getNewList() {
        return newList;
    }

    public void setNewList(List<NewListBean> newList) {
        this.newList = newList;
    }

    public List<ComListBean> getComList() {
        return comList;
    }

    public void setComList(List<ComListBean> comList) {
        this.comList = comList;
    }


    public static class NewListBean {
        private String workId;
        private String cid;
        private String cname;
        private String doorId;
        private String door;
        private int workUp;
        private int workUpPhone;
        private int workDown;
        private int workDownPhone;
        private int workCheck;
        private String pointId;
        private int ground;
        private String cDoorPic;
        private String errorDesc;
        private int isPhoto;
        private int state;
        private int stateType;
        private String updateTime;
        private String pictime;
        private String worktime;


        public PointBean toBean(String userId, String communityid, String starttime, String endtime){
            PointBean bean = new PointBean();
            bean.setWorkId(this.workId);
            bean.setCid(this.cid);
            bean.setCname(this.cname);
            bean.setDoorId(this.doorId);
            bean.setDoor(this.door);
            bean.setWorkUp(this.workUp);
            bean.setWorkUpPhone(this.workUpPhone);
            bean.setWorkDown(this.workDown);
            bean.setWorkDownPhone(this.workDownPhone);
            bean.setWorkCheck(this.workCheck);
            bean.setPointId(this.pointId);
            bean.setGround(this.ground);
            bean.setCDoorPic(this.cDoorPic);
            bean.setErrorDesc(this.errorDesc);
            bean.setIsPhoto(this.isPhoto);
            bean.setState(this.state);
            bean.setStateType(this.stateType);
            bean.setUpdateTime(this.updateTime);
            bean.setPictime(this.pictime);
            bean.setWorktime(this.worktime);
            bean.setUserId(userId);
            bean.setCommunityid(communityid);
            bean.setStarttime(TimeUtils.simpleDateParse(starttime,"yyyy-MM-dd"));
            bean.setEndtime(TimeUtils.simpleDateParse(endtime,"yyyy-MM-dd"));

            StringBuffer bFileId = new StringBuffer();
            StringBuffer bFileUrlB = new StringBuffer();
            StringBuffer bFileUrlS = new StringBuffer();

            bean.setFileId("");
            bean.setFileUrlB("");
            bean.setFileUrlS("");
            if(pFileList!=null){
                for(PFileListBean pFileListBean:pFileList){
                    bFileId.append(pFileListBean.fileId);
                    bFileId.append(PointWorkBeanDbUtil.FILE_SPLIT);

                    bFileUrlB.append(pFileListBean.fileUrlB);
                    bFileUrlB.append(PointWorkBeanDbUtil.FILE_SPLIT);

                    bFileUrlS.append(pFileListBean.fileUrlS);
                    bFileUrlS.append(PointWorkBeanDbUtil.FILE_SPLIT);
                }
                if(pFileList.size()!=0){
                    bean.setFileId(bFileId.toString());
                    bean.setFileUrlB(bFileUrlB.toString());
                    bean.setFileUrlS(bFileUrlS.toString());
                }
            }
            return bean;
        }

        public PointWorkBean toWorkBean(String userId){
            PointWorkBean bean = new PointWorkBean();
            bean.setWorkId(this.workId);
            bean.setNativeState("0");
            bean.setPointId(this.pointId);
            bean.setErrorDesc(this.errorDesc);
            bean.setState(this.state==0?1:0);
            bean.setUserId(userId);
            return bean;
        }
        /**
         * fileId : 12
         * fileUrlB : http://aa.ss.s
         * fileUrlS : http://aa.ss.s
         */

        private List<PFileListBean> pFileList;

        public String getWorkId() {
            return workId;
        }

        public void setWorkId(String workId) {
            this.workId = workId;
        }

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

        public String getDoorId() {
            return doorId;
        }

        public void setDoorId(String doorId) {
            this.doorId = doorId;
        }

        public String getDoor() {
            return door;
        }

        public void setDoor(String door) {
            this.door = door;
        }

        public int getWorkUp() {
            return workUp;
        }

        public void setWorkUp(int workUp) {
            this.workUp = workUp;
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

        public int getWorkCheck() {
            return workCheck;
        }

        public void setWorkCheck(int workCheck) {
            this.workCheck = workCheck;
        }

        public String getPointId() {
            return pointId;
        }

        public void setPointId(String pointId) {
            this.pointId = pointId;
        }

        public int getGround() {
            return ground;
        }

        public void setGround(int ground) {
            this.ground = ground;
        }

        public String getCDoorPic() {
            return cDoorPic;
        }

        public void setCDoorPic(String cDoorPic) {
            this.cDoorPic = cDoorPic;
        }

        public String getErrorDesc() {
            return errorDesc;
        }

        public void setErrorDesc(String errorDesc) {
            this.errorDesc = errorDesc;
        }

        public int getIsPhoto() {
            return isPhoto;
        }

        public void setIsPhoto(int isPhoto) {
            this.isPhoto = isPhoto;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getWorkUpPhone() {
            return workUpPhone;
        }

        public void setWorkUpPhone(int workUpPhone) {
            this.workUpPhone = workUpPhone;
        }

        public String getcDoorPic() {
            return cDoorPic;
        }

        public void setcDoorPic(String cDoorPic) {
            this.cDoorPic = cDoorPic;
        }

        public List<PFileListBean> getpFileList() {
            return pFileList;
        }

        public void setpFileList(List<PFileListBean> pFileList) {
            this.pFileList = pFileList;
        }

        public int getStateType() {
            return stateType;
        }

        public void setStateType(int stateType) {
            this.stateType = stateType;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
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

        public List<PFileListBean> getPFileList() {
            return pFileList;
        }

        public void setPFileList(List<PFileListBean> pFileList) {
            this.pFileList = pFileList;
        }

        public static class PFileListBean {
            private String fileId;
            private String fileUrlB;
            private String fileUrlS;

            public String getFileId() {
                return fileId;
            }

            public void setFileId(String fileId) {
                this.fileId = fileId;
            }

            public String getFileUrlB() {
                return fileUrlB;
            }

            public void setFileUrlB(String fileUrlB) {
                this.fileUrlB = fileUrlB;
            }

            public String getFileUrlS() {
                return fileUrlS;
            }

            public void setFileUrlS(String fileUrlS) {
                this.fileUrlS = fileUrlS;
            }
        }
    }

    public static class ComListBean {
        private String cid;
        private String cname;
        private String memo;
        /**
         * picurlB : http://aa.ss.s
         * picurlS : http://aa.ss.s
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

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
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
}
