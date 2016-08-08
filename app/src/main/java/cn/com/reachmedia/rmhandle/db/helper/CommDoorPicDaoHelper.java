package cn.com.reachmedia.rmhandle.db.helper;

import java.util.List;

import cn.com.reachmedia.rmhandle.bean.CommDoorPicBean;
import cn.com.reachmedia.rmhandle.dao.CommDoorPicBeanDao;
import cn.com.reachmedia.rmhandle.db.DaoHelperInterface;
import cn.com.reachmedia.rmhandle.db.DatabaseLoader;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/8/8 上午10:56
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/8/8          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class CommDoorPicDaoHelper implements DaoHelperInterface {

    private static CommDoorPicDaoHelper instance;
    private CommDoorPicBeanDao commDoorPicBeanDao;

    private CommDoorPicDaoHelper() {
        try {
            commDoorPicBeanDao = DatabaseLoader.getDaoSession().getCommDoorPicBeanDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CommDoorPicDaoHelper getInstance() {
        if(instance == null) {
            instance = new CommDoorPicDaoHelper();
        }

        return instance;
    }

    @Override
    public <T> void addData(T t) {
        if(commDoorPicBeanDao != null && t != null) {
            commDoorPicBeanDao.insertOrReplace((CommDoorPicBean) t);
        }
    }

    @Override
    public void deleteData(long id) {
        if(commDoorPicBeanDao != null ) {
            commDoorPicBeanDao.deleteByKey(id);
        }
    }

    @Override
    public CommDoorPicBean getDataById(long id) {
        if(commDoorPicBeanDao != null) {
            return commDoorPicBeanDao.load(id);
        }
        return null;
    }

    @Override
    public List getAllData() {
        if(commDoorPicBeanDao != null) {
            return commDoorPicBeanDao.loadAll();
        }
        return null;
    }

    @Override
    public boolean hasKey(long id) {
        if(commDoorPicBeanDao == null) {
            return false;
        }

        QueryBuilder<CommDoorPicBean> qb = commDoorPicBeanDao.queryBuilder();
        qb.where(CommDoorPicBeanDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if(commDoorPicBeanDao == null) {
            return 0;
        }

        QueryBuilder<CommDoorPicBean> qb = commDoorPicBeanDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if(commDoorPicBeanDao != null) {
            commDoorPicBeanDao.deleteAll();
        }
    }

    @Override
    public CommDoorPicBeanDao getDao() {
        return commDoorPicBeanDao;
    }

    public <T> void updateData(T t){
        if(commDoorPicBeanDao != null && t != null) {
            commDoorPicBeanDao.update((CommDoorPicBean) t);
        }
    }
}
