package cn.com.reachmedia.rmhandle.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/26 上午10:36
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/26          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class CardSubmitModel extends BaseModel implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public CardSubmitModel(){}

    private CardSubmitModel(Parcel in){
    }

    public static final Creator<CardSubmitModel> CREATOR = new Creator<CardSubmitModel>() {
        public CardSubmitModel createFromParcel(Parcel source) {
            return new CardSubmitModel(source);
        }

        public CardSubmitModel[] newArray(int size) {
            return new CardSubmitModel[size];
        }
    };

}
