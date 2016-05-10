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
    private String file1;
    private String file2;
    private String file3;

    public PointWorkBean() {
    }

    public PointWorkBean(Long id) {
        this.id = id;
    }

    public PointWorkBean(Long id, Long lastId, String userId, String workId, String pointId, Integer state, Integer repairType, String repairDesc, Integer errorType, String errorDesc, String lon, String lat, String workTime, String onlineTime, String nativeState, Integer fileCount, String file1, String file2, String file3) {
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
        this.file1 = file1;
        this.file2 = file2;
        this.file3 = file3;
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

    public String getFile1() {
        return file1;
    }

    public void setFile1(String file1) {
        this.file1 = file1;
    }

    public String getFile2() {
        return file2;
    }

    public void setFile2(String file2) {
        this.file2 = file2;
    }

    public String getFile3() {
        return file3;
    }

    public void setFile3(String file3) {
        this.file3 = file3;
    }

}
