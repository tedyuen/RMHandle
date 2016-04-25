package cn.com.reachmedia.rmhandle.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/25 下午5:36
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/25          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class BaseModel implements Parcelable{
    public String method;
    public String resstate;
    public String rescode;
    public String resdesc;

    public String toJson() {
        return new Gson().toJson(this);
    }

    public BaseModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.method);
        dest.writeString(this.resstate);
        dest.writeString(this.rescode);
        dest.writeString(this.resdesc);
    }

    private BaseModel(Parcel in) {
        this.method = in.readString();
        this.resstate = in.readString();
        this.rescode = in.readString();
        this.resdesc = in.readString();
    }

    public static final Creator<BaseModel> CREATOR = new Creator<BaseModel>() {
        public BaseModel createFromParcel(Parcel source) {
            return new BaseModel(source);
        }

        public BaseModel[] newArray(int size) {
            return new BaseModel[size];
        }
    };
}
