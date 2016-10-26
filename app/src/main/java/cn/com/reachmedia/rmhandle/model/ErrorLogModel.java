package cn.com.reachmedia.rmhandle.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tedyuen on 16-10-26.
 */
public class ErrorLogModel extends BaseModel implements Parcelable {

    private String fileId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileId);
    }

    public ErrorLogModel(){}

    private ErrorLogModel(Parcel in){
        fileId = in.readString();
    }

    public static final Creator<ErrorLogModel> CREATOR = new Creator<ErrorLogModel>() {
        public ErrorLogModel createFromParcel(Parcel source) {
            return new ErrorLogModel(source);
        }

        public ErrorLogModel[] newArray(int size) {
            return new ErrorLogModel[size];
        }
    };

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
