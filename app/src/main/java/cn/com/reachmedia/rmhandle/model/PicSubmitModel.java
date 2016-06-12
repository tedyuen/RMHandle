package cn.com.reachmedia.rmhandle.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/12 下午6:37
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/12          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PicSubmitModel extends BaseModel implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public PicSubmitModel(){}

    private PicSubmitModel(Parcel in){
    }

    public static final Creator<PicSubmitModel> CREATOR = new Creator<PicSubmitModel>() {
        public PicSubmitModel createFromParcel(Parcel source) {
            return new PicSubmitModel(source);
        }

        public PicSubmitModel[] newArray(int size) {
            return new PicSubmitModel[size];
        }
    };
    
}
