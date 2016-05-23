package cn.com.reachmedia.rmhandle.bean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table POINT_WORK_BEAN.
 */
public class PointWorkBean {

    private Long id;
    private Long lastId;
    private String userId;
    private String workId;
    private String pointId;
    private Integer state;
    private Integer repairType;
    private String repairDesc;
    private Integer errorType;
    private String errorDesc;
    private String lon;
    private String lat;
    private String workTime;
    private String onlineTime;
    private String nativeState;
    private Integer fileCount;
    private String filedelete;
    private String fileXY;
    private String fileTime;
    private String fileIdData;
    private String filePathData;
    private String doorpicid;
    private String doorpic;

    public PointWorkBean() {
    }

    public PointWorkBean(Long id) {
        this.id = id;
    }

    public PointWorkBean(Long id, Long lastId, String userId, String workId, String pointId, Integer state, Integer repairType, String repairDesc, Integer errorType, String errorDesc, String lon, String lat, String workTime, String onlineTime, String nativeState, Integer fileCount, String filedelete, String fileXY, String fileTime, String fileIdData, String filePathData, String doorpicid, String doorpic) {
        this.id = id;
        this.lastId = lastId;
        this.userId = userId;
        this.workId = workId;
        this.pointId = pointId;
        this.state = state;
        this.repairType = repairType;
        this.repairDesc = repairDesc;
        this.errorType = errorType;
        this.errorDesc = errorDesc;
        this.lon = lon;
        this.lat = lat;
        this.workTime = workTime;
        this.onlineTime = onlineTime;
        this.nativeState = nativeState;
        this.fileCount = fileCount;
        this.filedelete = filedelete;
        this.fileXY = fileXY;
        this.fileTime = fileTime;
        this.fileIdData = fileIdData;
        this.filePathData = filePathData;
        this.doorpicid = doorpicid;
        this.doorpic = doorpic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLastId() {
        return lastId;
    }

    public void setLastId(Long lastId) {
        this.lastId = lastId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getRepairType() {
        return repairType;
    }

    public void setRepairType(Integer repairType) {
        this.repairType = repairType;
    }

    public String getRepairDesc() {
        return repairDesc;
    }

    public void setRepairDesc(String repairDesc) {
        this.repairDesc = repairDesc;
    }

    public Integer getErrorType() {
        return errorType;
    }

    public void setErrorType(Integer errorType) {
        this.errorType = errorType;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
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

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getNativeState() {
        return nativeState;
    }

    public void setNativeState(String nativeState) {
        this.nativeState = nativeState;
    }

    public Integer getFileCount() {
        return fileCount;
    }

    public void setFileCount(Integer fileCount) {
        this.fileCount = fileCount;
    }

    public String getFiledelete() {
        return filedelete;
    }

    public void setFiledelete(String filedelete) {
        this.filedelete = filedelete;
    }

    public String getFileXY() {
        return fileXY;
    }

    public void setFileXY(String fileXY) {
        this.fileXY = fileXY;
    }

    public String getFileTime() {
        return fileTime;
    }

    public void setFileTime(String fileTime) {
        this.fileTime = fileTime;
    }

    public String getFileIdData() {
        return fileIdData;
    }

    public void setFileIdData(String fileIdData) {
        this.fileIdData = fileIdData;
    }

    public String getFilePathData() {
        return filePathData;
    }

    public void setFilePathData(String filePathData) {
        this.filePathData = filePathData;
    }

    public String getDoorpicid() {
        return doorpicid;
    }

    public void setDoorpicid(String doorpicid) {
        this.doorpicid = doorpicid;
    }

    public String getDoorpic() {
        return doorpic;
    }

    public void setDoorpic(String doorpic) {
        this.doorpic = doorpic;
    }

}
