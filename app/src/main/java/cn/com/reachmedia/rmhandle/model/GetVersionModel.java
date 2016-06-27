package cn.com.reachmedia.rmhandle.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/27 下午2:07
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/27          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class GetVersionModel extends BaseModel{
    public String newversion;
    public int versioncode;

    public int isalert;
    public int isupdate;
    @SerializedName("gourl")
    public String downUrl;

    public String content;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public GetVersionModel(){}

    private GetVersionModel(Parcel in){
    }

    public static final Creator<GetVersionModel> CREATOR = new Creator<GetVersionModel>() {
        public GetVersionModel createFromParcel(Parcel source) {
            return new GetVersionModel(source);
        }

        public GetVersionModel[] newArray(int size) {
            return new GetVersionModel[size];
        }
    };
}
