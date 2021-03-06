package cn.com.reachmedia.rmhandle.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import cn.com.reachmedia.rmhandle.bean.CommDoorPicBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table COMM_DOOR_PIC_BEAN.
*/
public class CommDoorPicBeanDao extends AbstractDao<CommDoorPicBean, Long> {

    public static final String TABLENAME = "COMM_DOOR_PIC_BEAN";

    /**
     * Properties of entity CommDoorPicBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property UserId = new Property(1, String.class, "userId", false, "USER_ID");
        public final static Property CommunityId = new Property(2, String.class, "communityId", false, "COMMUNITY_ID");
        public final static Property CommunityFile1 = new Property(3, String.class, "communityFile1", false, "COMMUNITY_FILE1");
        public final static Property CommunityFileId1 = new Property(4, String.class, "communityFileId1", false, "COMMUNITY_FILE_ID1");
        public final static Property CommunitySpace1 = new Property(5, String.class, "communitySpace1", false, "COMMUNITY_SPACE1");
        public final static Property CommunitySpaceId1 = new Property(6, String.class, "communitySpaceId1", false, "COMMUNITY_SPACE_ID1");
        public final static Property CommunityFile2 = new Property(7, String.class, "communityFile2", false, "COMMUNITY_FILE2");
        public final static Property CommunityFileId2 = new Property(8, String.class, "communityFileId2", false, "COMMUNITY_FILE_ID2");
        public final static Property CommunitySpace2 = new Property(9, String.class, "communitySpace2", false, "COMMUNITY_SPACE2");
        public final static Property CommunitySpaceId2 = new Property(10, String.class, "communitySpaceId2", false, "COMMUNITY_SPACE_ID2");
        public final static Property WorkTime = new Property(11, java.util.Date.class, "workTime", false, "WORK_TIME");
        public final static Property NativeState = new Property(12, String.class, "nativeState", false, "NATIVE_STATE");
    };


    public CommDoorPicBeanDao(DaoConfig config) {
        super(config);
    }
    
    public CommDoorPicBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'COMM_DOOR_PIC_BEAN' (" + //
                "'ID' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'USER_ID' TEXT," + // 1: userId
                "'COMMUNITY_ID' TEXT," + // 2: communityId
                "'COMMUNITY_FILE1' TEXT," + // 3: communityFile1
                "'COMMUNITY_FILE_ID1' TEXT," + // 4: communityFileId1
                "'COMMUNITY_SPACE1' TEXT," + // 5: communitySpace1
                "'COMMUNITY_SPACE_ID1' TEXT," + // 6: communitySpaceId1
                "'COMMUNITY_FILE2' TEXT," + // 7: communityFile2
                "'COMMUNITY_FILE_ID2' TEXT," + // 8: communityFileId2
                "'COMMUNITY_SPACE2' TEXT," + // 9: communitySpace2
                "'COMMUNITY_SPACE_ID2' TEXT," + // 10: communitySpaceId2
                "'WORK_TIME' INTEGER," + // 11: workTime
                "'NATIVE_STATE' TEXT);"); // 12: nativeState
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_COMM_DOOR_PIC_BEAN_ID ON COMM_DOOR_PIC_BEAN" +
                " (ID);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'COMM_DOOR_PIC_BEAN'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, CommDoorPicBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
 
        String communityId = entity.getCommunityId();
        if (communityId != null) {
            stmt.bindString(3, communityId);
        }
 
        String communityFile1 = entity.getCommunityFile1();
        if (communityFile1 != null) {
            stmt.bindString(4, communityFile1);
        }
 
        String communityFileId1 = entity.getCommunityFileId1();
        if (communityFileId1 != null) {
            stmt.bindString(5, communityFileId1);
        }
 
        String communitySpace1 = entity.getCommunitySpace1();
        if (communitySpace1 != null) {
            stmt.bindString(6, communitySpace1);
        }
 
        String communitySpaceId1 = entity.getCommunitySpaceId1();
        if (communitySpaceId1 != null) {
            stmt.bindString(7, communitySpaceId1);
        }
 
        String communityFile2 = entity.getCommunityFile2();
        if (communityFile2 != null) {
            stmt.bindString(8, communityFile2);
        }
 
        String communityFileId2 = entity.getCommunityFileId2();
        if (communityFileId2 != null) {
            stmt.bindString(9, communityFileId2);
        }
 
        String communitySpace2 = entity.getCommunitySpace2();
        if (communitySpace2 != null) {
            stmt.bindString(10, communitySpace2);
        }
 
        String communitySpaceId2 = entity.getCommunitySpaceId2();
        if (communitySpaceId2 != null) {
            stmt.bindString(11, communitySpaceId2);
        }
 
        java.util.Date workTime = entity.getWorkTime();
        if (workTime != null) {
            stmt.bindLong(12, workTime.getTime());
        }
 
        String nativeState = entity.getNativeState();
        if (nativeState != null) {
            stmt.bindString(13, nativeState);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public CommDoorPicBean readEntity(Cursor cursor, int offset) {
        CommDoorPicBean entity = new CommDoorPicBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // communityId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // communityFile1
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // communityFileId1
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // communitySpace1
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // communitySpaceId1
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // communityFile2
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // communityFileId2
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // communitySpace2
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // communitySpaceId2
            cursor.isNull(offset + 11) ? null : new java.util.Date(cursor.getLong(offset + 11)), // workTime
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12) // nativeState
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, CommDoorPicBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCommunityId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCommunityFile1(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCommunityFileId1(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCommunitySpace1(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCommunitySpaceId1(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCommunityFile2(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setCommunityFileId2(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setCommunitySpace2(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setCommunitySpaceId2(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setWorkTime(cursor.isNull(offset + 11) ? null : new java.util.Date(cursor.getLong(offset + 11)));
        entity.setNativeState(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(CommDoorPicBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(CommDoorPicBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
