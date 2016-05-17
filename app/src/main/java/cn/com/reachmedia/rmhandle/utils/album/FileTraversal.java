package cn.com.reachmedia.rmhandle.utils.album;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/17 下午5:36
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/17          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class FileTraversal implements Parcelable{

    public String filename;//所属图片的文件名称
    public List<String[]> filecontent=new ArrayList<>();

    public void sort(){
        Collections.sort(this.filecontent, new Comparator<String[]>() {
            public int compare(String[] arg0, String[] arg1) {
                int result = 0;
                if(arg1[1]==null || arg0[1]==null){
                    return 0;
                }
                try{
                    result = Long.valueOf(arg1[1]).compareTo(Long.valueOf(arg0[1]));
                }catch (Exception e){
                    e.printStackTrace();
                    return result;
                }
                return result;
            }
        });
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(filename);
        dest.writeList(filecontent);
    }

    public static final Parcelable.Creator<FileTraversal> CREATOR=new Parcelable.Creator<FileTraversal>() {

        @Override
        public FileTraversal[] newArray(int size) {
            return null;
        }

        @Override
        public FileTraversal createFromParcel(Parcel source) {
            FileTraversal ft=new FileTraversal();
            ft.filename= source.readString();
            ft.filecontent= source.readArrayList(FileTraversal.class.getClassLoader());

            return ft;
        }


    };
}
