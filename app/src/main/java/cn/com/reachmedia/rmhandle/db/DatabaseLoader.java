package cn.com.reachmedia.rmhandle.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cn.com.reachmedia.rmhandle.dao.DaoMaster;
import cn.com.reachmedia.rmhandle.dao.DaoSession;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/10 下午4:33
 * Description: db 加载器
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/10          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class DatabaseLoader {


    private static RMOpenHelper helper;

    public static void init(Context context){
        helper = new RMOpenHelper(context, "rm-db.db", null);


    }

    public static SQLiteDatabase getDatabase(){
        return helper.getWritableDatabase();
    }


    /**
     * 获取dao session
     * @return
     */
    public static DaoSession getDaoSession(){
        SQLiteDatabase db = getDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        return daoSession;
    }
}
