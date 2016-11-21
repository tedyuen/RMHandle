package cn.com.reachmedia.rmhandle.db.helper;

import java.util.List;

import cn.com.reachmedia.rmhandle.bean.CompImageBean;
import cn.com.reachmedia.rmhandle.dao.CompImageBeanDao;
import cn.com.reachmedia.rmhandle.db.DaoHelperInterface;
import cn.com.reachmedia.rmhandle.db.DatabaseLoader;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by tedyuen on 16-11-21.
 */
public class CompImageDaoHelper implements DaoHelperInterface {

    private static CompImageDaoHelper instance;
    private CompImageBeanDao compImageBeanDao;

    private CompImageDaoHelper(){
        try{
            compImageBeanDao = DatabaseLoader.getDaoSession().getCompImageBeanDao();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static CompImageDaoHelper getInstance(){
        if(instance==null){
            instance = new CompImageDaoHelper();
        }
        return  instance;
    }

    @Override
    public <T> void addData(T t) {
        if(compImageBeanDao !=null && t!=null){
            compImageBeanDao.insertOrReplace((CompImageBean)t);
        }
    }

    @Override
    public void deleteData(long id) {
        if(compImageBeanDao != null ) {
            compImageBeanDao.deleteByKey(id);
        }
    }

    @Override
    public CompImageBean getDataById(long id) {
        if(compImageBeanDao != null) {
            return compImageBeanDao.load(id);
        }
        return null;
    }

    @Override
    public List getAllData() {
        if(compImageBeanDao != null) {
            return compImageBeanDao.loadAll();
        }
        return null;
    }

    @Override
    public boolean hasKey(long id) {
        if(compImageBeanDao == null) {
            return false;
        }
        QueryBuilder<CompImageBean> qb = compImageBeanDao.queryBuilder();
        qb.where(CompImageBeanDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if(compImageBeanDao == null) {
            return 0;
        }

        QueryBuilder<CompImageBean> qb = compImageBeanDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if(compImageBeanDao != null) {
            compImageBeanDao.deleteAll();
        }
    }

    @Override
    public CompImageBeanDao getDao() {
        return compImageBeanDao;
    }

    public <T> void updateData(T t){
        if(compImageBeanDao != null && t != null) {
            compImageBeanDao.update((CompImageBean) t);
        }
    }
}
