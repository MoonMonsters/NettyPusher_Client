package edu.csuft.chentao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import edu.csuft.chentao.pojo.bean.GroupChattingItem;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "GROUP_CHATTING_ITEM".
*/
public class GroupChattingItemDao extends AbstractDao<GroupChattingItem, Long> {

    public static final String TABLENAME = "GROUP_CHATTING_ITEM";

    /**
     * Properties of entity GroupChattingItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property _id = new Property(0, Long.class, "_id", true, "_id");
        public final static Property Groupname = new Property(1, String.class, "groupname", false, "GROUPNAME");
        public final static Property Groupid = new Property(2, int.class, "groupid", false, "GROUPID");
        public final static Property Lastmessage = new Property(3, String.class, "lastmessage", false, "LASTMESSAGE");
        public final static Property Image = new Property(4, byte[].class, "image", false, "IMAGE");
        public final static Property Number = new Property(5, int.class, "number", false, "NUMBER");
    }


    public GroupChattingItemDao(DaoConfig config) {
        super(config);
    }
    
    public GroupChattingItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"GROUP_CHATTING_ITEM\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: _id
                "\"GROUPNAME\" TEXT," + // 1: groupname
                "\"GROUPID\" INTEGER NOT NULL ," + // 2: groupid
                "\"LASTMESSAGE\" TEXT," + // 3: lastmessage
                "\"IMAGE\" BLOB," + // 4: image
                "\"NUMBER\" INTEGER NOT NULL );"); // 5: number
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_GROUP_CHATTING_ITEM__id ON GROUP_CHATTING_ITEM" +
                " (\"_id\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"GROUP_CHATTING_ITEM\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, GroupChattingItem entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String groupname = entity.getGroupname();
        if (groupname != null) {
            stmt.bindString(2, groupname);
        }
        stmt.bindLong(3, entity.getGroupid());
 
        String lastmessage = entity.getLastmessage();
        if (lastmessage != null) {
            stmt.bindString(4, lastmessage);
        }
 
        byte[] image = entity.getImage();
        if (image != null) {
            stmt.bindBlob(5, image);
        }
        stmt.bindLong(6, entity.getNumber());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, GroupChattingItem entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String groupname = entity.getGroupname();
        if (groupname != null) {
            stmt.bindString(2, groupname);
        }
        stmt.bindLong(3, entity.getGroupid());
 
        String lastmessage = entity.getLastmessage();
        if (lastmessage != null) {
            stmt.bindString(4, lastmessage);
        }
 
        byte[] image = entity.getImage();
        if (image != null) {
            stmt.bindBlob(5, image);
        }
        stmt.bindLong(6, entity.getNumber());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public GroupChattingItem readEntity(Cursor cursor, int offset) {
        GroupChattingItem entity = new GroupChattingItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // _id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // groupname
            cursor.getInt(offset + 2), // groupid
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // lastmessage
            cursor.isNull(offset + 4) ? null : cursor.getBlob(offset + 4), // image
            cursor.getInt(offset + 5) // number
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, GroupChattingItem entity, int offset) {
        entity.set_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setGroupname(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setGroupid(cursor.getInt(offset + 2));
        entity.setLastmessage(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setImage(cursor.isNull(offset + 4) ? null : cursor.getBlob(offset + 4));
        entity.setNumber(cursor.getInt(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(GroupChattingItem entity, long rowId) {
        entity.set_id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(GroupChattingItem entity) {
        if(entity != null) {
            return entity.get_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(GroupChattingItem entity) {
        return entity.get_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
