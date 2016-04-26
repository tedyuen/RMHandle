package cn.com.reachmedia.rmhandle.model;

import java.util.List;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/26 上午11:40
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/26          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PointListModel {


    /**
     * communityid : 33
     * community : 黄埔新苑
     * doordesc : 1号楼2121
     * carddesc : 找老袁
     * newNumber : 32
     * endNumber : 100
     * errorNumber : 223
     * telephone : 12333337777
     * newList : [{"workId":"22","cid":"121","cname":"肯德基","doorId":"109","door":"1号楼","workUp":1,"workDown":0,"workDownPhone":1,"workCheck":0,"piontid":1233,"ground":1}]
     * endList : [{"workId":"23","cid":"122","cname":"再生能源","doorId":"190","door":"2号楼","workUp":1,"workDown":0,"workDownPhone":1,"workCheck":0,"piontid":1233,"ground":0,"isPhoto":0}]
     * errorList : [{"workId":"24","cid":"126","cname":"杜威斯","doorId":"419","door":"6号楼","workUp":1,"workDown":0,"workDownPhone":1,"workCheck":0,"piontid":1233,"ground":1,"errorDesc":"刊位破损"}]
     */

    private String communityid;
    private String community;
    private String doordesc;
    private String carddesc;
    private int newNumber;
    private int endNumber;
    private int errorNumber;
    private String telephone;
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
     * piontid : 1233
     * ground : 1
     */

    private List<NewListBean> newList;
    /**
     * workId : 23
     * cid : 122
     * cname : 再生能源
     * doorId : 190
     * door : 2号楼
     * workUp : 1
     * workDown : 0
     * workDownPhone : 1
     * workCheck : 0
     * piontid : 1233
     * ground : 0
     * isPhoto : 0
     */

    private List<EndListBean> endList;
    /**
     * workId : 24
     * cid : 126
     * cname : 杜威斯
     * doorId : 419
     * door : 6号楼
     * workUp : 1
     * workDown : 0
     * workDownPhone : 1
     * workCheck : 0
     * piontid : 1233
     * ground : 1
     * errorDesc : 刊位破损
     */

    private List<ErrorListBean> errorList;

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

    public List<NewListBean> getNewList() {
        return newList;
    }

    public void setNewList(List<NewListBean> newList) {
        this.newList = newList;
    }

    public List<EndListBean> getEndList() {
        return endList;
    }

    public void setEndList(List<EndListBean> endList) {
        this.endList = endList;
    }

    public List<ErrorListBean> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<ErrorListBean> errorList) {
        this.errorList = errorList;
    }

    public static class NewListBean {
        private String workId;
        private String cid;
        private String cname;
        private String doorId;
        private String door;
        private int workUp;
        private int workDown;
        private int workDownPhone;
        private int workCheck;
        private int piontid;
        private int ground;

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

        public int getPiontid() {
            return piontid;
        }

        public void setPiontid(int piontid) {
            this.piontid = piontid;
        }

        public int getGround() {
            return ground;
        }

        public void setGround(int ground) {
            this.ground = ground;
        }
    }

    public static class EndListBean {
        private String workId;
        private String cid;
        private String cname;
        private String doorId;
        private String door;
        private int workUp;
        private int workDown;
        private int workDownPhone;
        private int workCheck;
        private int piontid;
        private int ground;
        private int isPhoto;

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

        public int getPiontid() {
            return piontid;
        }

        public void setPiontid(int piontid) {
            this.piontid = piontid;
        }

        public int getGround() {
            return ground;
        }

        public void setGround(int ground) {
            this.ground = ground;
        }

        public int getIsPhoto() {
            return isPhoto;
        }

        public void setIsPhoto(int isPhoto) {
            this.isPhoto = isPhoto;
        }
    }

    public static class ErrorListBean {
        private String workId;
        private String cid;
        private String cname;
        private String doorId;
        private String door;
        private int workUp;
        private int workDown;
        private int workDownPhone;
        private int workCheck;
        private int piontid;
        private int ground;
        private String errorDesc;

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

        public int getPiontid() {
            return piontid;
        }

        public void setPiontid(int piontid) {
            this.piontid = piontid;
        }

        public int getGround() {
            return ground;
        }

        public void setGround(int ground) {
            this.ground = ground;
        }

        public String getErrorDesc() {
            return errorDesc;
        }

        public void setErrorDesc(String errorDesc) {
            this.errorDesc = errorDesc;
        }
    }
}
