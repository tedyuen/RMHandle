package cn.com.reachmedia.rmhandle.db.utils;

import java.util.ArrayList;
import java.util.List;

import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.PointBean;
import cn.com.reachmedia.rmhandle.bean.PointWorkBean;
import cn.com.reachmedia.rmhandle.dao.PointBeanDao;
import cn.com.reachmedia.rmhandle.db.helper.PointBeanDaoHelper;
import cn.com.reachmedia.rmhandle.db.helper.PointWorkBeanDaoHelper;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;
import cn.com.reachmedia.rmhandle.utils.TimeUtils;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/12 上午11:36
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/12          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PointBeanDbUtil {
    private static PointBeanDbUtil pointBeanDbUtil;
    private PointBeanDbUtil(){
        pointBeanDaoHelper = PointBeanDaoHelper.getInstance();
        pointWorkBeanDaoHelper = PointWorkBeanDaoHelper.getInstance();
    }
    private PointBeanDaoHelper pointBeanDaoHelper;
    private PointWorkBeanDaoHelper pointWorkBeanDaoHelper;

    public static PointBeanDbUtil getIns(){
        if(pointBeanDbUtil==null){
            pointBeanDbUtil = new PointBeanDbUtil();

        }
        return pointBeanDbUtil;
    }


    public void insertData(List<PointListModel.NewListBean> newList,String communityid,String starttime,String endtime){
        pointBeanDaoHelper.deleteAll();
        for(PointListModel.NewListBean tempBean:newList){
            PointBean bean = tempBean.toBean(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID),communityid,starttime,endtime);
            pointBeanDaoHelper.addData(bean);
        }
    }

    public long[] getItemNumber(){
        return new long[]{getNewNumber(),getEndNumber(),getErrorNumber()};
    }

    private long getNewNumber(){
        return pointBeanDaoHelper.getDao().queryBuilder()
                .where(PointBeanDao.Properties.State.eq(0),
                        PointBeanDao.Properties.StateType.eq(0))
                .count();
    }

    private long getEndNumber(){
        return pointBeanDaoHelper.getDao().queryBuilder()
                .where(PointBeanDao.Properties.State.eq(1),
                        PointBeanDao.Properties.StateType.eq(1))
                .count();
    }

    private long getErrorNumber(){
        return pointBeanDaoHelper.getDao().queryBuilder()
                .where(PointBeanDao.Properties.State.eq(1),
                        PointBeanDao.Properties.StateType.notEq(1))
                .count();
    }
//---------------------------------------------
    public List<PointBean> getNewList(String communityid,String starttime){
        List<PointBean> list = pointBeanDaoHelper.getDao().queryBuilder()
                .where(PointBeanDao.Properties.State.eq(0),
                        PointBeanDao.Properties.StateType.eq(0),
                        PointBeanDao.Properties.Communityid.eq(communityid),
                        PointBeanDao.Properties.Starttime.eq(TimeUtils.simpleDateParse(starttime,"yyyy-MM-dd")),
                        PointBeanDao.Properties.UserId.eq(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID)))
                .list();
        List<PointBean> result = new ArrayList<>();
        for(PointBean pointBean:list){
            PointWorkBean workBean = pointWorkBeanDaoHelper.getDataByWPID(pointBean.getWorkId(),pointBean.getPointId(),0,"0");
            if(workBean==null){
                result.add(pointBean);
            }
        }
        return result;
    }

    public List<PointBean> getEndList(String communityid,String starttime){
        List<PointBean> list = pointBeanDaoHelper.getDao().queryBuilder()
                .where(PointBeanDao.Properties.State.eq(1),
                        PointBeanDao.Properties.StateType.eq(1),
                        PointBeanDao.Properties.Communityid.eq(communityid),
                        PointBeanDao.Properties.Starttime.eq(TimeUtils.simpleDateParse(starttime,"yyyy-MM-dd")),
                        PointBeanDao.Properties.UserId.eq(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID)))
                .list();
        List<PointBean> result = new ArrayList<>();
        for(PointBean pointBean:list){
            PointWorkBean workBean = pointWorkBeanDaoHelper.getDataByWPID(pointBean.getWorkId(),pointBean.getPointId(),1,"0");
            if(workBean==null){
                result.add(pointBean);
            }
        }
        return result;
    }

    public List<PointBean> getErrorList(String communityid,String starttime){
        List<PointBean> list = pointBeanDaoHelper.getDao().queryBuilder()
                .where(PointBeanDao.Properties.State.eq(1),
                        PointBeanDao.Properties.StateType.notEq(1),
                        PointBeanDao.Properties.Communityid.eq(communityid),
                        PointBeanDao.Properties.Starttime.eq(TimeUtils.simpleDateParse(starttime,"yyyy-MM-dd")),
                        PointBeanDao.Properties.UserId.eq(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID)))
                .list();
        List<PointBean> result = new ArrayList<>();
        for(PointBean pointBean:list){
            PointWorkBean workBean = pointWorkBeanDaoHelper.getDataByWPIDError(pointBean.getWorkId(),pointBean.getPointId(),1,"0");
            if(workBean==null){
                result.add(pointBean);
            }
        }
        return result;
    }

    public PointBean getPointBeanByWPID(String workId,String pointId){
        return pointBeanDaoHelper.getDao().queryBuilder()
                .where(PointBeanDao.Properties.WorkId.eq(workId),
                        PointBeanDao.Properties.PointId.eq(pointId),
                        PointBeanDao.Properties.UserId.eq(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID)))
                .unique();
    }


}
