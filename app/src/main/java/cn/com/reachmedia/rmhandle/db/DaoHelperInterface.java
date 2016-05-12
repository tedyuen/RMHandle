package cn.com.reachmedia.rmhandle.db;

import java.util.List;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/10 下午4:32
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/10          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public interface DaoHelperInterface {
    public <T> void addData(T t);
    public void deleteData(long id);
    public <T> T getDataById(long id);
    public List getAllData();
    public boolean hasKey(long id);
    public long getTotalCount();
    public void deleteAll();
    <T> T getDao();
}
