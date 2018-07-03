package com.jim.green.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.jim.recorder.model.Cell;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CELL".
*/
public class CellDao extends AbstractDao<Cell, Long> {

    public static final String TABLENAME = "CELL";

    /**
     * Properties of entity Cell.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Time = new Property(0, Long.class, "time", true, "_id");
        public final static Property HowLong = new Property(1, int.class, "howLong", false, "HOW_LONG");
        public final static Property Unit = new Property(2, Long.class, "unit", false, "UNIT");
        public final static Property TypeId = new Property(3, Long.class, "typeId", false, "TYPE_ID");
    }


    public CellDao(DaoConfig config) {
        super(config);
    }
    
    public CellDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CELL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: time
                "\"HOW_LONG\" INTEGER NOT NULL ," + // 1: howLong
                "\"UNIT\" INTEGER," + // 2: unit
                "\"TYPE_ID\" INTEGER);"); // 3: typeId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CELL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Cell entity) {
        stmt.clearBindings();
 
        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(1, time);
        }
        stmt.bindLong(2, entity.getHowLong());
 
        Long unit = entity.getUnit();
        if (unit != null) {
            stmt.bindLong(3, unit);
        }
 
        Long typeId = entity.getTypeId();
        if (typeId != null) {
            stmt.bindLong(4, typeId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Cell entity) {
        stmt.clearBindings();
 
        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(1, time);
        }
        stmt.bindLong(2, entity.getHowLong());
 
        Long unit = entity.getUnit();
        if (unit != null) {
            stmt.bindLong(3, unit);
        }
 
        Long typeId = entity.getTypeId();
        if (typeId != null) {
            stmt.bindLong(4, typeId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Cell readEntity(Cursor cursor, int offset) {
        Cell entity = new Cell( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // time
            cursor.getInt(offset + 1), // howLong
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // unit
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3) // typeId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Cell entity, int offset) {
        entity.setTime(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setHowLong(cursor.getInt(offset + 1));
        entity.setUnit(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setTypeId(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Cell entity, long rowId) {
        entity.setTime(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Cell entity) {
        if(entity != null) {
            return entity.getTime();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Cell entity) {
        return entity.getTime() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}