package cn.com.reachmedia.rmhandle.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
}
