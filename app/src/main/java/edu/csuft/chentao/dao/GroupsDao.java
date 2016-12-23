package edu.csuft.chentao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import edu.csuft.chentao.pojo.bean.Groups;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "GROUPS".
*/
public class GroupsDao extends AbstractDao<Groups, Long> {

    public static final String TABLENAME = "GROUPS";

    /**
     * Properties of entity Groups.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property _id = new Property(0, Long.class, "_id", true, "_id");
        public final static Property Groupname = new Property(1, String.class, "groupname", false, "GROUPNAME");
        public final static Property Image = new Property(2, byte[].class, "image", false, "IMAGE");
        public final static Property Groupid = new Property(3, int.class, "groupid", false, "GROUPID");
    }


    public GroupsDao(DaoConfig config) {
        super(config);
    }
    
    public GroupsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"GROUPS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: _id
                "\"GROUPNAME\" TEXT," + // 1: groupname
                "\"IMAGE\" BLOB," + // 2: image
                "\"GROUPID\" INTEGER NOT NULL );"); // 3: groupid
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_GROUPS__id ON GROUPS" +
                " (\"_id\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"GROUPS\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Groups entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String groupname = entity.getGroupname();
        if (groupname != null) {
            stmt.bindString(2, groupname);
        }
 
        byte[] image = entity.getImage();
        if (image != null) {
            stmt.bindBlob(3, image);
        }
        stmt.bindLong(4, entity.getGroupid());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Groups entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String groupname = entity.getGroupname();
        if (groupname != null) {
            stmt.bindString(2, groupname);
        }
 
        byte[] image = entity.getImage();
        if (image != null) {
            stmt.bindBlob(3, image);
        }
        stmt.bindLong(4, entity.getGroupid());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Groups readEntity(Cursor cursor, int offset) {
        Groups entity = new Groups( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // _id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // groupname
            cursor.isNull(offset + 2) ? null : cursor.getBlob(offset + 2), // image
            cursor.getInt(offset + 3) // groupid
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Groups entity, int offset) {
        entity.set_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setGroupname(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setImage(cursor.isNull(offset + 2) ? null : cursor.getBlob(offset + 2));
        entity.setGroupid(cursor.getInt(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Groups entity, long rowId) {
        entity.set_id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Groups entity) {
        if(entity != null) {
            return entity.get_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Groups entity) {
        return entity.get_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
