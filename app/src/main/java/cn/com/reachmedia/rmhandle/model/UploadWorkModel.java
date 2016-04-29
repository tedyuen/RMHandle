package cn.com.reachmedia.rmhandle.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/29 上午10:15
 * Description: 3.7 提交工作工作项接口
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/29          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class UploadWorkModel extends BaseModel implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(workId);
    }

    public UploadWorkModel(){}

    private UploadWorkModel(Parcel in){
        workId = in.readString();
    }

    public static final Creator<UploadWorkModel> CREATOR = new Creator<UploadWorkModel>() {
        public UploadWorkModel createFromParcel(Parcel source) {
            return new UploadWorkModel(source);
        }

        public UploadWorkModel[] newArray(int size) {
            return new UploadWorkModel[size];
        }
    };

    /**
     * workId : 222
     */

    private String workId;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }
}
