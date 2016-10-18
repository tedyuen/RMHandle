package cn.com.reachmedia.rmhandle.ui.bean;

import android.view.View;
import android.widget.ImageView;

import java.io.File;

import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.ui.view.imagepager.ImageAllBean;
import cn.com.reachmedia.rmhandle.utils.ImageCacheUtils;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import cn.com.reachmedia.rmhandle.utils.pictureutils.utils.SimpleImageLoader;

/**
 * 点位图片提交bean
 * Created by tedyuen on 16-9-21.
 */
public class PictureBean {
    private String fileId;       // fileId提交用
    private String mainPath;     // 入库用路径 url s
    private String subPath;      // 显示用路径 url b
    private boolean isDeleted;   // 是否被删除
    private PictureType type;    // 图片类型
    private File file;           // 图片文件

    public PictureBean(){

    }



    /**
     * 初始化新建图片
     * @param file
     * @param type
     * @param fileId
     */
    public PictureBean(File file,PictureType type,String fileId,String mainPath){
        this.file = file;
        this.type = type;
        this.fileId = fileId;
        this.mainPath = mainPath;
        this.subPath = file.getAbsolutePath();
        this.isDeleted = false;
    }

    public enum PictureType{
        TYPE_1,    // id和图片都未提交

        TYPE_2,    // id已经提交，图片未提交

        TYPE_3,    // id和图片都提交

        TYPE_4,    // 新增图片
    }

    public void displayImage(ImageView imageView){
        displayImage(imageView,false);
    }


    public void displayImage(ImageView imageView,boolean isLarge){
        System.out.println("显示图片: "+type+":"+mainPath);

        switch (type){
            case TYPE_1:
                imageView.setVisibility(View.VISIBLE);
                SimpleImageLoader.displayImage(new File(mainPath), imageView);
                break;
            case TYPE_2:
                imageView.setVisibility(View.VISIBLE);
                SimpleImageLoader.displayImage(new File(mainPath), imageView);
                break;
            case TYPE_3:
                imageView.setVisibility(View.VISIBLE);
                if(isLarge){
                    ImageCacheUtils.displayLocalOrUrl(App.getContext(),subPath,imageView,1080,1920);
                }else{
                    ImageCacheUtils.displayLocalOrUrl(App.getContext(),mainPath,imageView);
                }
                break;
            case TYPE_4:
                if(file!=null && file.exists()){
                    SimpleImageLoader.displayImage(file, imageView);
                    imageView.setVisibility(View.VISIBLE);
                }else if(!StringUtils.isEmpty(mainPath)){
                    SimpleImageLoader.displayImage(new File(mainPath), imageView);
                    imageView.setVisibility(View.VISIBLE);
                }
                break;
        }

    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public PictureType getType() {
        return type;
    }

    public void setType(PictureType type) {
        this.type = type;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getMainPath() {
        return mainPath;
    }

    public void setMainPath(String mainPath) {
        this.mainPath = mainPath;
    }

    public String getSubPath() {
        return subPath;
    }

    public void setSubPath(String subPath) {
        this.subPath = subPath;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
