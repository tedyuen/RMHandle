package cn.com.reachmedia.rmhandle.db.utils;

import cn.com.reachmedia.rmhandle.bean.CompImageBean;
import cn.com.reachmedia.rmhandle.db.helper.CompImageDaoHelper;

/**
 * Created by tedyuen on 16-11-21.
 */
public class CompImageDbUtil {

    private static CompImageDbUtil compImageDbUtil;
    private CompImageDaoHelper compImageDaoHelper;
    private CompImageDbUtil(){
        compImageDaoHelper = CompImageDaoHelper.getInstance();
    }

    public static CompImageDbUtil getIns(){
        if(compImageDbUtil==null){
            compImageDbUtil = new CompImageDbUtil();
        }
        return compImageDbUtil;
    }

    public void updateOneData(CompImageBean compImageBean){
        compImageDaoHelper.updateData(compImageBean);
    }

    public void insertOneData(CompImageBean compImageBean){
        compImageDaoHelper.addData(compImageBean);
    }
}
