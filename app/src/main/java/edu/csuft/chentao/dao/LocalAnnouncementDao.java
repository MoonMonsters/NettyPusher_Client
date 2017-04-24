package edu.csuft.chentao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import edu.csuft.chentao.pojo.bean.LocalAnnouncement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LOCAL_ANNOUNCEMENT".
*/
public class LocalAnnouncementDao extends AbstractDao<LocalAnnouncement, Long> {

    public static final String TABLENAME = "LOCAL_ANNOUNCEMENT";

    /**
     * Properties of entity LocalAnnouncement.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property _id = new Property(0, Long.class, "_id", true, "_id");
        public final static Property Serialnumber = new Property(1, String.class, "serialnumber", false, "SERIALNUMBER");
        public final static Property Groupid = new Property(2, int.class, "groupid", false, "GROUPID");
        public final static Property Title = new Property(3, String.class, "title", false, "TITLE");
        public final static Property Content = new Property(4, String.class, "content", false, "CONTENT");
        public final static Property Username = new Property(5, String.class, "username", false, "USERNAME");
        public final static Property Userid = new Property(6, int.class, "userid", false, "USERID");
        public final static Property Time = new Property(7, String.class, "time", false, "TIME");
        public final static Property Isnew = new Property(8, boolean.class, "isnew", false, "ISNEW");
    }


    public LocalAnnouncementDao(DaoConfig config) {
        super(config);
    }
    
    public LocalAnnouncementDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOCAL_ANNOUNCEMENT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: _id
                "\"SERIALNUMBER\" TEXT," + // 1: serialnumber
                "\"GROUPID\" INTEGER NOT NULL ," + // 2: groupid
                "\"TITLE\" TEXT," + // 3: title
                "\"CONTENT\" TEXT," + // 4: content
                "\"USERNAME\" TEXT," + // 5: username
                "\"USERID\" INTEGER NOT NULL ," + // 6: userid
                "\"TIME\" TEXT," + // 7: time
                "\"ISNEW\" INTEGER NOT NULL );"); // 8: isnew
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_LOCAL_ANNOUNCEMENT__id ON LOCAL_ANNOUNCEMENT" +
                " (\"_id\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOCAL_ANNOUNCEMENT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LocalAnnouncement entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String serialnumber = entity.getSerialnumber();
        if (serialnumber != null) {
            stmt.bindString(2, serialnumber);
        }
        stmt.bindLong(3, entity.getGroupid());
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(4, title);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(5, content);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(6, username);
        }
        stmt.bindLong(7, entity.getUserid());
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(8, time);
        }
        stmt.bindLong(9, entity.getIsnew() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LocalAnnouncement entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String serialnumber = entity.getSerialnumber();
        if (serialnumber != null) {
            stmt.bindString(2, serialnumber);
        }
        stmt.bindLong(3, entity.getGroupid());
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(4, title);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(5, content);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(6, username);
        }
        stmt.bindLong(7, entity.getUserid());
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(8, time);
        }
        stmt.bindLong(9, entity.getIsnew() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public LocalAnnouncement readEntity(Cursor cursor, int offset) {
        LocalAnnouncement entity = new LocalAnnouncement( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // _id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // serialnumber
            cursor.getInt(offset + 2), // groupid
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // title
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // content
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // username
            cursor.getInt(offset + 6), // userid
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // time
            cursor.getShort(offset + 8) != 0 // isnew
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LocalAnnouncement entity, int offset) {
        entity.set_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSerialnumber(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setGroupid(cursor.getInt(offset + 2));
        entity.setTitle(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setContent(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUsername(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setUserid(cursor.getInt(offset + 6));
        entity.setTime(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setIsnew(cursor.getShort(offset + 8) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(LocalAnnouncement entity, long rowId) {
        entity.set_id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(LocalAnnouncement entity) {
        if(entity != null) {
            return entity.get_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(LocalAnnouncement entity) {
        return entity.get_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}