package cn.com.reachmedia.rmhandle.db.utils;

import java.util.List;

import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.PointWorkBean;
import cn.com.reachmedia.rmhandle.dao.PointWorkBeanDao;
import cn.com.reachmedia.rmhandle.db.helper.PointWorkBeanDaoHelper;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/17 下午3:57
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/17          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PointWorkBeanDbUtil {

    public final static String FILE_SPLIT = "@#@";

    private static PointWorkBeanDbUtil pointWorkBeanDbUtil;
    private PointWorkBeanDbUtil(){
        pointWorkBeanDaoHelper = PointWorkBeanDaoHelper.getInstance();
    }
    private PointWorkBeanDaoHelper pointWorkBeanDaoHelper;

    public static PointWorkBeanDbUtil getIns(){
        if(pointWorkBeanDbUtil==null){
            pointWorkBeanDbUtil = new PointWorkBeanDbUtil();

        }
        return pointWorkBeanDbUtil;
    }

    public void insertData(List<PointListModel.NewListBean> newList){
        pointWorkBeanDaoHelper.deleteAll();
        for(PointListModel.NewListBean tempBean:newList){
            PointWorkBean bean = tempBean.toWorkBean(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID));
            pointWorkBeanDaoHelper.addData(bean);
        }
    }

    public void insertOneData(PointWorkBean pointWorkBean){
//        pointWorkBeanDaoHelper.deleteAll();
        pointWorkBeanDaoHelper.addData(pointWorkBean);
    }

    public void updateOneData(PointWorkBean pointWorkBean){
//        pointWorkBeanDaoHelper.deleteAll();
        pointWorkBeanDaoHelper.updateData(pointWorkBean);
    }



    /**
     * 获取状态未提交的数据
     * @param userId
     */
    public List<PointWorkBean> getUpload(String userId,String nativeState){
        List<PointWorkBean> list = pointWorkBeanDaoHelper.getDao().queryBuilder()
                .where(PointWorkBeanDao.Properties.UserId.eq(userId),
                        PointWorkBeanDao.Properties.NativeState.eq(nativeState))
                .list();
        return list;
    }

    public void changeNativeState(String workId,String pointId,String preState,String nativeState){
        PointWorkBean bean = pointWorkBeanDaoHelper.getDao().queryBuilder()
                .where(PointWorkBeanDao.Properties.WorkId.eq(workId),
                        PointWorkBeanDao.Properties.PointId.eq(pointId),
                        PointWorkBeanDao.Properties.NativeState.eq(preState))
                .unique();
        bean.setNativeState(nativeState);
        pointWorkBeanDaoHelper.getDao().update(bean);
    }

    public PointWorkBean getPointWorkBeanByWPID(String workId,String pointId){
        return pointWorkBeanDaoHelper.getDao().queryBuilder()
                .where(PointWorkBeanDao.Properties.WorkId.eq(workId),
                        PointWorkBeanDao.Properties.PointId.eq(pointId),
                        PointWorkBeanDao.Properties.NativeState.notEq("2"),
                        PointWorkBeanDao.Properties.UserId.eq(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID)))
                .unique();
    }
}
