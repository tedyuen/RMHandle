package cn.com.reachmedia.rmhandle.ui.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tedyuen on 16-10-16.
 */
public class FileDb {
    private String deleteIds;
    private String fileIds;
    private String filePaths;
    private String fileXY;
    private String fileTime;
    private int fileCount;
    private int newCount;//新建数量
    private boolean isAllDelete;//是否都删除了
    private List<PictureBean> pictureBeen;

    public FileDb(){
        pictureBeen = new ArrayList<>();
    }

    public List<PictureBean> copyFile(){
//            StringBuffer buffer = new StringBuffer();
//            buffer.append("========== copy file ==========\n");
//            for(PictureBean bean:pictureBeen){
//                System.out.println(bean.getFileId());
//                System.out.println(bean.getMainPath());
//                System.out.println(bean.getSubPath());
//                System.out.println("-----------------------");
//            }
//            buffer.append("=====================\n");
        return pictureBeen;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("========== insert db ==========\n");
        buffer.append("filedIds: ");
        buffer.append(fileIds);
        buffer.append("\n");
        buffer.append("filePaths: ");
        buffer.append(filePaths);
        buffer.append("\n");
        buffer.append("fileXY: ");
        buffer.append(fileXY);
        buffer.append("\n");
        buffer.append("fileTime: ");
        buffer.append(fileTime);
        buffer.append("\n");
        buffer.append("deleteIds: ");
        buffer.append(deleteIds);
        buffer.append("\n");
        buffer.append("fileCount: ");
        buffer.append(fileCount);
        buffer.append("\n");
        buffer.append("=====================\n");
        return buffer.toString();
    }

    public List<PictureBean> getPictureBeen() {
        return pictureBeen;
    }

    public void setPictureBeen(List<PictureBean> pictureBeen) {
        this.pictureBeen = pictureBeen;
    }

    public String getDeleteIds() {
        return deleteIds;
    }

    public void setDeleteIds(String deleteIds) {
        this.deleteIds = deleteIds;
    }

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public String getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(String filePaths) {
        this.filePaths = filePaths;
    }

    public String getFileXY() {
        return fileXY;
    }

    public void setFileXY(String fileXY) {
        this.fileXY = fileXY;
    }

    public String getFileTime() {
        return fileTime;
    }

    public void setFileTime(String fileTime) {
        this.fileTime = fileTime;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public int getNewCount() {
        return newCount;
    }

    public void setNewCount(int newCount) {
        this.newCount = newCount;
    }

    public boolean isAllDelete() {
        return isAllDelete;
    }

    public void setAllDelete(boolean allDelete) {
        isAllDelete = allDelete;
    }
}
