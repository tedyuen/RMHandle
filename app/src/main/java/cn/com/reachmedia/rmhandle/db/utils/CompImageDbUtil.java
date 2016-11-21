package cn.com.reachmedia.rmhandle.db.utils;

import java.util.List;

import cn.com.reachmedia.rmhandle.bean.CompImageBean;
import cn.com.reachmedia.rmhandle.dao.CompImageBeanDao;
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

    public List<CompImageBean> getUpload(String userId,int nativeState){
        List<CompImageBean> list = compImageDaoHelper.getDao().queryBuilder()
                .where(CompImageBeanDao.Properties.Userid.eq(userId),
                        CompImageBeanDao.Properties.State.eq(nativeState))
                .list();
        return list;
    }
}
