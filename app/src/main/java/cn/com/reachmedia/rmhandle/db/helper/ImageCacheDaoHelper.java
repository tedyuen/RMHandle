package cn.com.reachmedia.rmhandle.db.helper;

import java.util.List;

import cn.com.reachmedia.rmhandle.bean.ImageCacheBean;
import cn.com.reachmedia.rmhandle.dao.CommDoorPicBeanDao;
import cn.com.reachmedia.rmhandle.dao.ImageCacheBeanDao;
import cn.com.reachmedia.rmhandle.db.DaoHelperInterface;
import cn.com.reachmedia.rmhandle.db.DatabaseLoader;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by tedyuen on 16-9-26.
 */
public class ImageCacheDaoHelper implements DaoHelperInterface {

    private static ImageCacheDaoHelper instance;
    private ImageCacheBeanDao imageCacheBeanDao;

    private ImageCacheDaoHelper(){
        try{
            imageCacheBeanDao = DatabaseLoader.getDaoSession().getImageCacheBeanDao();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ImageCacheDaoHelper getInstance(){
        if(instance == null){
            instance = new ImageCacheDaoHelper();
        }
        return instance;
    }


    @Override
    public <T> void addData(T t) {
        if(imageCacheBeanDao != null && t != null) {
            imageCacheBeanDao.insertOrReplace((ImageCacheBean) t);
        }
    }

    @Override
    public void deleteData(long id) {
        if(imageCacheBeanDao != null ) {
            imageCacheBeanDao.deleteByKey(id);
        }
    }

    @Override
    public ImageCacheBean getDataById(long id) {
        if(imageCacheBeanDao != null) {
            return imageCacheBeanDao.load(id);
        }
        return null;
    }

    @Override
    public List getAllData() {
        if(imageCacheBeanDao != null) {
            return imageCacheBeanDao.loadAll();
        }
        return null;
    }

    @Override
    public boolean hasKey(long id) {
        if(imageCacheBeanDao == null) {
            return false;
        }
        QueryBuilder<ImageCacheBean> qb = imageCacheBeanDao.queryBuilder();
        qb.where(CommDoorPicBeanDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if(imageCacheBeanDao == null) {
            return 0;
        }
        QueryBuilder<ImageCacheBean> qb = imageCacheBeanDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if(imageCacheBeanDao != null) {
            imageCacheBeanDao.deleteAll();
        }
    }

    @Override
    public ImageCacheBeanDao getDao() {
        return imageCacheBeanDao;
    }

    public <T> void updateData(T t){
        if(imageCacheBeanDao != null && t != null) {
            imageCacheBeanDao.update((ImageCacheBean) t);
        }
    }

    public ImageCacheBean getBeanByUrl(String url){
        ImageCacheBean bean = imageCacheBeanDao.queryBuilder()
                .where(ImageCacheBeanDao.Properties.Url.eq(url))
                .limit(1)
                .unique();
        return bean;
    }

}
