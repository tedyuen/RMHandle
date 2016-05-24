package cn.com.reachmedia.rmhandle.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/23 下午5:48
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/23          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class UploadPicModel extends BaseModel implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(workId);
    }

    public UploadPicModel(){}

    private UploadPicModel(Parcel in){
        workId = in.readString();
    }

    public static final Creator<UploadPicModel> CREATOR = new Creator<UploadPicModel>() {
        public UploadPicModel createFromParcel(Parcel source) {
            return new UploadPicModel(source);
        }

        public UploadPicModel[] newArray(int size) {
            return new UploadPicModel[size];
        }
    };

    /**
     * workId : 222
     */

    private String workId;
    private String point;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
