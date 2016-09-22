package cn.com.reachmedia.rmhandle.db.helper;


import java.util.List;

import cn.com.reachmedia.rmhandle.bean.PointBean;
import cn.com.reachmedia.rmhandle.dao.PointBeanDao;
import cn.com.reachmedia.rmhandle.db.DaoHelperInterface;
import cn.com.reachmedia.rmhandle.db.DatabaseLoader;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/10 下午4:36
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/10          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PointBeanDaoHelper implements DaoHelperInterface {

    private static PointBeanDaoHelper instance;
    private PointBeanDao pointBeanDao;

    private PointBeanDaoHelper() {
        try {
            pointBeanDao = DatabaseLoader.getDaoSession().getPointBeanDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PointBeanDaoHelper getInstance() {
        if(instance == null) {
            instance = new PointBeanDaoHelper();
        }

        return instance;
    }

    @Override
    public <T> void addData(T t) {
        if(pointBeanDao != null && t != null) {
            pointBeanDao.insertOrReplace((PointBean) t);
        }
    }

    @Override
    public void deleteData(long id) {
        if(pointBeanDao != null ) {
            pointBeanDao.deleteByKey(id);
        }
    }

    @Override
    public PointBean getDataById(long id) {
        if(pointBeanDao != null) {
            return pointBeanDao.load(id);
        }
        return null;
    }

    @Override
    public List getAllData() {
        if(pointBeanDao != null) {
            return pointBeanDao.loadAll();
        }
        return null;
    }

    @Override
    public boolean hasKey(long id) {
        if(pointBeanDao == null) {
            return false;
        }

        QueryBuilder<PointBean> qb = pointBeanDao.queryBuilder();
        qb.where(PointBeanDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if(pointBeanDao == null) {
            return 0;
        }

        QueryBuilder<PointBean> qb = pointBeanDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if(pointBeanDao != null) {
            pointBeanDao.deleteAll();
        }
    }

    public <T> void addDataTx(Iterable<T> entities, boolean setPrimaryKey){
        if(entities!=null){
            pointBeanDao.insertInTx((Iterable<PointBean>) entities,setPrimaryKey);
        }
    }

    @Override
    public PointBeanDao getDao() {
        return pointBeanDao;
    }
}
