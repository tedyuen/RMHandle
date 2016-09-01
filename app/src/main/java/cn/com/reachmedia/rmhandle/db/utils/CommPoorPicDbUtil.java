package cn.com.reachmedia.rmhandle.db.utils;

import java.util.List;

import cn.com.reachmedia.rmhandle.bean.CommDoorPicBean;
import cn.com.reachmedia.rmhandle.dao.CommDoorPicBeanDao;
import cn.com.reachmedia.rmhandle.db.helper.CommDoorPicDaoHelper;
import cn.com.reachmedia.rmhandle.utils.TimeUtils;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/8/8 上午11:42
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/8/8          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class CommPoorPicDbUtil {

    private static CommPoorPicDbUtil commPoorPicDbUtil;
    private  CommDoorPicDaoHelper commDoorPicDaoHelper;
    private CommPoorPicDbUtil(){
        commDoorPicDaoHelper = CommDoorPicDaoHelper.getInstance();
    }

    public static CommPoorPicDbUtil getIns(){
        if(commPoorPicDbUtil==null){
            commPoorPicDbUtil = new CommPoorPicDbUtil();

        }
        return commPoorPicDbUtil;
    }

    public void updateOneData(CommDoorPicBean commDoorPicBean){
        commDoorPicDaoHelper.updateData(commDoorPicBean);
    }

    public CommDoorPicBean getBeanByCommId(String communityId,String nativeState){
        return commDoorPicDaoHelper.getDao().queryBuilder()
                .where(CommDoorPicBeanDao.Properties.CommunityId.eq(communityId),
                        CommDoorPicBeanDao.Properties.NativeState.eq(nativeState))
                .unique();
    }

    public void insertOneData(CommDoorPicBean commDoorPicBean){
        commDoorPicDaoHelper.addData(commDoorPicBean);
    }

    /**
     * 获取状态未提交的数据
     * @param nativeState
     */
    public List<CommDoorPicBean> getUpload(String nativeState){
        List<CommDoorPicBean> list = commDoorPicDaoHelper.getDao().queryBuilder()
                .where(CommDoorPicBeanDao.Properties.NativeState.eq(nativeState))
                .list();
        return list;
    }

    public CommDoorPicBean getSingleCommDoorPicBean(String nativeState){
        CommDoorPicBean commDoorPicBean = commDoorPicDaoHelper.getDao().queryBuilder()
                .where(CommDoorPicBeanDao.Properties.NativeState.eq(nativeState))
                .limit(1)
                .unique();
        return commDoorPicBean;
    }

    public void changeNativeState(String communityId){
        if(communityId==null){
            return;
        }
        CommDoorPicBean bean = commDoorPicDaoHelper.getDao().queryBuilder()
                .where(CommDoorPicBeanDao.Properties.CommunityId.eq(communityId)
                        ,CommDoorPicBeanDao.Properties.NativeState.eq("0"))
                .unique();
        if(bean!=null){
            bean.setNativeState("1");
            bean.setWorkTime(TimeUtils.getNowDate());
            commDoorPicDaoHelper.getDao().update(bean);
        }
    }

}
