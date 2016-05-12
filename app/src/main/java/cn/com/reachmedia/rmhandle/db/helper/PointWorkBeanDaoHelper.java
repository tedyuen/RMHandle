package cn.com.reachmedia.rmhandle.db.helper;

import java.util.List;

import cn.com.reachmedia.rmhandle.bean.PointWorkBean;
import cn.com.reachmedia.rmhandle.dao.PointWorkBeanDao;
import cn.com.reachmedia.rmhandle.db.DaoHelperInterface;
import cn.com.reachmedia.rmhandle.db.DatabaseLoader;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/10 下午5:51
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/10          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PointWorkBeanDaoHelper implements DaoHelperInterface {


    private static PointWorkBeanDaoHelper instance;
    private PointWorkBeanDao pointBeanDao;

    private PointWorkBeanDaoHelper() {
        try {
            pointBeanDao = DatabaseLoader.getDaoSession().getPointWorkBeanDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PointWorkBeanDaoHelper getInstance() {
        if(instance == null) {
            instance = new PointWorkBeanDaoHelper();
        }

        return instance;
    }


    @Override
    public <T> void addData(T t) {
        if(pointBeanDao != null && t != null) {
            pointBeanDao.insertOrReplace((PointWorkBean) t);
        }
    }

    @Override
    public void deleteData(long id) {
        if(pointBeanDao != null ) {
            pointBeanDao.deleteByKey(id);
        }
    }

    @Override
    public PointWorkBean getDataById(long id) {
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

        QueryBuilder<PointWorkBean> qb = pointBeanDao.queryBuilder();
        qb.where(PointWorkBeanDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if(pointBeanDao == null) {
            return 0;
        }

        QueryBuilder<PointWorkBean> qb = pointBeanDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if(pointBeanDao != null) {
            pointBeanDao.deleteAll();
        }
    }
}