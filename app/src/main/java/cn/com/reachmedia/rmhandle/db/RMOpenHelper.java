package cn.com.reachmedia.rmhandle.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cn.com.reachmedia.rmhandle.dao.CommDoorPicBeanDao;
import cn.com.reachmedia.rmhandle.dao.DaoMaster;
import cn.com.reachmedia.rmhandle.dao.PointBeanDao;
import cn.com.reachmedia.rmhandle.dao.PointWorkBeanDao;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/10 下午4:34
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/10          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class RMOpenHelper extends DaoMaster.OpenHelper{

    public RMOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                //创建新表，注意createTable()是静态方法
                PointBeanDao.createTable(db, true);
                PointWorkBeanDao.createTable(db, true);
                CommDoorPicBeanDao.createTable(db, true);

                // 加入新字段
                // db.execSQL("ALTER TABLE 'moments' ADD 'audio_path' TEXT;");

                // TODO
                break;
        }
    }
}