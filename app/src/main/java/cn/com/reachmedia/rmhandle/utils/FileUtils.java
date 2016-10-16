package cn.com.reachmedia.rmhandle.utils;

import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import cn.com.reachmedia.rmhandle.R;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/8/17 下午4:46
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/8/17          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class FileUtils {

    public static List<String> getAllFileNameList(String dir){
        List<String> result = new ArrayList<>();

        File file=new File(dir);
        File[] tempList = file.listFiles();
        if(tempList!=null){
            System.out.println("该目录下对象个数："+tempList.length);

            for (int i = 0; i < tempList.length; i++) {
                if (tempList[i].isFile()) {
                    //读取某个文件夹下的所有文件
                    System.out.println("文件："+tempList[i].getAbsolutePath());
                    result.add(tempList[i].getAbsolutePath());
                }
                if (tempList[i].isDirectory()) {
                    //读取某个文件夹下的所有文件夹
                    System.out.println("文件夹："+tempList[i].getAbsolutePath());
                }
            }
        }
        return  result;
    }

    public static boolean copyFile(String sourceStr,String destStr) throws IOException {
        File source = new File(sourceStr);
        File dest = new File(destStr);
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        if(source.exists()){
            if(dest.exists()){
                dest.delete();
            }
            try {
                inputChannel = new FileInputStream(source).getChannel();
                outputChannel = new FileOutputStream(dest).getChannel();
                outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
                return true;
            } finally {
                inputChannel.close();
                outputChannel.close();
            }
        }
        return false;
    }

    public static boolean mkdirAndCheck(String dir){
        if (PhotoSavePathUtil.checkSDCard()) {
            File savedir = new File(dir);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
            return true;
        } else {

            return false;
        }
    }
}
