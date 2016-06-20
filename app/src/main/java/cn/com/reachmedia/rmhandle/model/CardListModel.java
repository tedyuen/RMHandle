package cn.com.reachmedia.rmhandle.model;

import android.os.Parcel;

import java.util.List;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/20 上午10:54
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/20          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class CardListModel extends BaseModel {


    /**
     * communityName : 黄埔新苑
     * pswdList : [{"name":"庞威","uTime":"06-01","content":"门卡密码是123"}]
     * cardList : [{"name":"庞威","uTime":"06-02","content":"门卡找章老师拿"}]
     */

    private String communityName;
    /**
     * name : 庞威
     * uTime : 06-01
     * content : 门卡密码是123
     */

    private List<PswdListBean> pswdList;
    /**
     * name : 庞威
     * uTime : 06-02
     * content : 门卡找章老师拿
     */

    private List<CardListBean> cardList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(communityName);
        dest.writeList(pswdList);
        dest.writeList(cardList);
    }

    public CardListModel(){}

    private CardListModel(Parcel in){
        communityName = in.readString();
        pswdList = in.readArrayList(PswdListBean.class.getClassLoader());
        cardList = in.readArrayList(CardListBean.class.getClassLoader());
    }

    public static final Creator<CardListModel> CREATOR = new Creator<CardListModel>() {
        public CardListModel createFromParcel(Parcel source) {
            return new CardListModel(source);
        }

        public CardListModel[] newArray(int size) {
            return new CardListModel[size];
        }
    };

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public List<PswdListBean> getPswdList() {
        return pswdList;
    }

    public void setPswdList(List<PswdListBean> pswdList) {
        this.pswdList = pswdList;
    }

    public List<CardListBean> getCardList() {
        return cardList;
    }

    public void setCardList(List<CardListBean> cardList) {
        this.cardList = cardList;
    }

    public static class PswdListBean {
        private String name;
        private String uTime;
        private String content;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUTime() {
            return uTime;
        }

        public void setUTime(String uTime) {
            this.uTime = uTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class CardListBean {
        private String name;
        private String uTime;
        private String content;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUTime() {
            return uTime;
        }

        public void setUTime(String uTime) {
            this.uTime = uTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
