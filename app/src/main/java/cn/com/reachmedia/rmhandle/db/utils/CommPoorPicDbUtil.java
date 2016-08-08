package cn.com.reachmedia.rmhandle.db.utils;

import cn.com.reachmedia.rmhandle.bean.CommDoorPicBean;
import cn.com.reachmedia.rmhandle.dao.CommDoorPicBeanDao;
import cn.com.reachmedia.rmhandle.db.helper.CommDoorPicDaoHelper;

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



}
