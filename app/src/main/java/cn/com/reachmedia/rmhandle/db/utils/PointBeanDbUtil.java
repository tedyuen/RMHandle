package cn.com.reachmedia.rmhandle.db.utils;

import java.util.ArrayList;
import java.util.List;

import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.PointBean;
import cn.com.reachmedia.rmhandle.bean.PointWorkBean;
import cn.com.reachmedia.rmhandle.dao.PointBeanDao;
import cn.com.reachmedia.rmhandle.dao.PointWorkBeanDao;
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


    public void insertData(final List<PointListModel.NewListBean> newList,final String communityid,final String starttime,final String endtime,final String communityName){
        pointBeanDaoHelper.deleteAll();
        List<PointBean> datas = new ArrayList<>();
        for(PointListModel.NewListBean tempBean:newList){
            PointBean bean = tempBean.toBean(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID),communityid,starttime,endtime,communityName);
            datas.add(bean);
        }

        pointBeanDaoHelper.addDataTx(datas,false);

//        for(PointListModel.NewListBean tempBean:newList){
//            PointBean bean = tempBean.toBean(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID),communityid,starttime,endtime,communityName);
//            pointBeanDaoHelper.addData(bean);
//        }
    }

    public long[] getItemNumber(String communityid,String starttime){
        return new long[]{getNewNumber(communityid,starttime),getEndNumber(communityid,starttime),getErrorNumber(communityid,starttime)};
    }

    /**
     * 0 |未完成 |1 正常完成 |2 报修 | 3 报错
     * @param communityid
     * @param starttime
     * @return
     */
    private long getNewNumber(String communityid,String starttime){
        long count = pointBeanDaoHelper.getDao().queryBuilder()
                .where(PointBeanDao.Properties.State.eq(0),
                        PointBeanDao.Properties.StateType.eq(0),
                        PointBeanDao.Properties.Communityid.eq(communityid),
                        PointBeanDao.Properties.Starttime.eq(TimeUtils.simpleDateParse(starttime,"yyyy-MM-dd")),
                        PointBeanDao.Properties.UserId.eq(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID)))
                .count();
        long count2 = pointWorkBeanDaoHelper.getDao().queryBuilder()
                .where(PointWorkBeanDao.Properties.State.ge(1),//1,2,3
                        PointWorkBeanDao.Properties.NativeState.notEq("2"),//未上传
                        PointWorkBeanDao.Properties.Communityid.eq(communityid),
                        PointWorkBeanDao.Properties.Starttime.eq(TimeUtils.simpleDateParse(starttime,"yyyy-MM-dd")),
                        PointWorkBeanDao.Properties.UserId.eq(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID)))
                .count();

        System.out.println("getNewNumber:"+count+":"+count2);
        return count-count2;
    }

    private long getEndNumber(String communityid,String starttime){
        long count = pointBeanDaoHelper.getDao().queryBuilder()
                .where(PointBeanDao.Properties.State.eq(1),
                        PointBeanDao.Properties.StateType.eq(1),
                        PointBeanDao.Properties.Communityid.eq(communityid),
                        PointBeanDao.Properties.Starttime.eq(TimeUtils.simpleDateParse(starttime,"yyyy-MM-dd")),
                        PointBeanDao.Properties.UserId.eq(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID)))
                .count();
        long count2 = pointWorkBeanDaoHelper.getDao().queryBuilder()
                .where(PointWorkBeanDao.Properties.State.eq(1),
                        PointWorkBeanDao.Properties.NativeState.notEq("2"),//未上传
                        PointWorkBeanDao.Properties.Communityid.eq(communityid),
                        PointWorkBeanDao.Properties.Starttime.eq(TimeUtils.simpleDateParse(starttime,"yyyy-MM-dd")),
                        PointWorkBeanDao.Properties.UserId.eq(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID)))
                .count();
        System.out.println("getEndNumber:"+count+":"+count2);
        return count+count2;
    }

    private long getErrorNumber(String communityid,String starttime){
        long count = pointBeanDaoHelper.getDao().queryBuilder()
                .where(PointBeanDao.Properties.State.eq(1),
                        PointBeanDao.Properties.StateType.notEq(1),
                        PointBeanDao.Properties.Communityid.eq(communityid),
                        PointBeanDao.Properties.Starttime.eq(TimeUtils.simpleDateParse(starttime,"yyyy-MM-dd")),
                        PointBeanDao.Properties.UserId.eq(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID)))
                .count();
        long count2 = pointWorkBeanDaoHelper.getDao().queryBuilder()
                .where(PointWorkBeanDao.Properties.State.ge(2),
                        PointWorkBeanDao.Properties.NativeState.notEq("2"),//未上传
                        PointWorkBeanDao.Properties.Communityid.eq(communityid),
                        PointWorkBeanDao.Properties.Starttime.eq(TimeUtils.simpleDateParse(starttime,"yyyy-MM-dd")),
                        PointWorkBeanDao.Properties.UserId.eq(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID)))
                .count();
        System.out.println("getEndNumber:"+count+":"+count2);
        return count+count2;
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
            PointWorkBean workBean = pointWorkBeanDaoHelper.getDataByWPIDError(pointBean.getWorkId(),pointBean.getPointId(),0,"2");
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
        List<PointBean> list2 = pointBeanDaoHelper.getDao().queryBuilder()
                .where(PointBeanDao.Properties.State.eq(0),
                        PointBeanDao.Properties.Communityid.eq(communityid),
                        PointBeanDao.Properties.Starttime.eq(TimeUtils.simpleDateParse(starttime,"yyyy-MM-dd")),
                        PointBeanDao.Properties.UserId.eq(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID)))
                .list();
        List<PointBean> result = new ArrayList<>();
        for(PointBean bean:list2){
            PointWorkBean pointWorkBean = pointWorkBeanDaoHelper.getDataByWPID(bean.getWorkId(),bean.getPointId(),1,"2");
            if(pointWorkBean!=null){
                result.add(bean);
            }
        }
        result.addAll(list);
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
        System.out.println("error list size 1:  "+list.size());
        List<PointBean> list2 = pointBeanDaoHelper.getDao().queryBuilder()
                .where(PointBeanDao.Properties.State.eq(0),
                        PointBeanDao.Properties.Communityid.eq(communityid),
                        PointBeanDao.Properties.Starttime.eq(TimeUtils.simpleDateParse(starttime,"yyyy-MM-dd")),
                        PointBeanDao.Properties.UserId.eq(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID)))
                .list();
        System.out.println("error list size 2:  "+list2.size());

        List<PointBean> result = new ArrayList<>();
        for(PointBean pointBean:list2){
            PointWorkBean workBean = pointWorkBeanDaoHelper.getDataByWPID(pointBean.getWorkId(),pointBean.getPointId(),3,"2");
            if(workBean!=null){
                result.add(pointBean);
            }
        }
        System.out.println("error list size 3:  "+result.size());

        result.addAll(list);
        return result;
    }

    public PointBean getPointBeanByWPID(String workId,String pointId){
//        try{
        System.out.println("===>  "+workId+":"+pointId+":"+SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID));
            return pointBeanDaoHelper.getDao().queryBuilder()
                    .where(PointBeanDao.Properties.WorkId.eq(workId),
                            PointBeanDao.Properties.PointId.eq(pointId),
                            PointBeanDao.Properties.UserId.eq(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID)))
                    .limit(1)
                    .unique();
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
    }


}
