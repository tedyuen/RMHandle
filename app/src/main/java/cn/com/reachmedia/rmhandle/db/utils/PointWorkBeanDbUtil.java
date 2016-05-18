package cn.com.reachmedia.rmhandle.db.utils;

import java.util.List;

import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.PointWorkBean;
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
        pointWorkBeanDaoHelper.deleteAll();
        pointWorkBeanDaoHelper.addData(pointWorkBean);
    }
}
