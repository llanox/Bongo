package info.llanox.mobile.data;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBRepository {
	
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NOMBRE = "nombre_cancion";
    public static final String KEY_FECHA_REPRODUCCION = "fecha_reproduccion";
    
    private static final String TAG = DBRepository.class.getSimpleName();
    private static final String DATABASE_NAME = "db_reproductor";
    private static final String DATABASE_TABLE = "reproducciones";
    private static final int    DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "create table "+DATABASE_TABLE+" "+ 
    "("+KEY_ROWID+" integer primary key autoincrement, "
        + ""+KEY_NOMBRE+" text not null UNIQUE, "+KEY_FECHA_REPRODUCCION+" text not null);";

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    
    public DBRepository(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version" + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);
        }
    }
    //---opens the database---
    public DBRepository open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }
    
    //---insert a contact into the database---
    public long insertReproduccion(String name, Date fechaReproduccion)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NOMBRE, name);
        initialValues.put(KEY_FECHA_REPRODUCCION, fechaReproduccion.toString());      
        
        
        return db.insert(DATABASE_TABLE, null, initialValues);
    }
    //---deletes a particular contact---
    public boolean deleteContact(long rowId)
    {
    	
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
    //---retrieves all the contacts---
    public Cursor getAllContacts()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NOMBRE,
                KEY_FECHA_REPRODUCCION}, null, null, null, null, null);
    }
    //---retrieves a particular contact---
    public Cursor getContact(long rowId) throws SQLException
    {
        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,KEY_NOMBRE, KEY_FECHA_REPRODUCCION}, KEY_ROWID + "=" + rowId, null,null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //---updates a contact---
    public boolean updateContact(long rowId, String name, Date fechaReproduccion)

    {
        ContentValues args = new ContentValues();
        args.put(KEY_NOMBRE, name);
        args.put(KEY_FECHA_REPRODUCCION, fechaReproduccion.toString());
        return db.update(DATABASE_TABLE, args, KEY_NOMBRE + " like %" + name+"%", null) >0;
    }
}

