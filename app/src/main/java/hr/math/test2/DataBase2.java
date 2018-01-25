package hr.math.test2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by admin on 25/01/2018.
 */

public class DataBase2 {

    static final String KEY_ROWID = "_id";
    static final String KEY_USERNAME = "name";
    static final String KEY_PASSWORD = "password";
    static final String KEY_YEARS = "years";
    static final String KEY_ISMARRIED = "is_married";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "IvosDB";
    static final String DATABASE_TABLE = "IvosTable";

    static final String DATABASE_TABLE2 = "AnasTable";

    static final int DATABASE_VERSION = 4;

    static final String DATABASE_CREATE =
            "create table IvosTable (_id integer primary key autoincrement, "
                    + "name text not null, password text not null, years integer, is_married integer);";

    static final String DATABASE_CREATE2 =
            "create table AnasTable (_id integer primary key autoincrement, "
                    + "name text not null, password text not null);";


    final Context context;

    DataBase2.DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DataBase2(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DataBase2.DatabaseHelper(context);
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
                db.execSQL(DATABASE_CREATE2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading db from" + oldVersion + "to"
                    + newVersion );
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    //---opens the database---
    public DataBase2 open() throws SQLException
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
    public long insertContact(String name, String password, int years, int is_married)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_USERNAME, name);
        initialValues.put(KEY_PASSWORD, password);
        initialValues.put(KEY_YEARS, years);
        initialValues.put(KEY_ISMARRIED, is_married);

        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public long insertContact2(String name, String password)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_USERNAME, name);
        initialValues.put(KEY_PASSWORD, password);

        return db.insert(DATABASE_TABLE2, null, initialValues);
    }


    //---deletes a particular contact---
    public boolean deleteContact(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the contacts---
    public Cursor getAllContacts()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_USERNAME,
                KEY_PASSWORD, KEY_YEARS , KEY_ISMARRIED}, null, null, null, null, null);
    }

    //---retrieves a particular contact---
    public Cursor getContact(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_USERNAME, KEY_PASSWORD,
                                KEY_YEARS, KEY_ISMARRIED}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getContactByName(String name) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_USERNAME, KEY_PASSWORD,
                                KEY_YEARS, KEY_ISMARRIED}, KEY_USERNAME + "=" + name, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a contact---
    public boolean updateContact(long rowId, String name, int years, int is_married)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_USERNAME, name);
        args.put(KEY_YEARS, years);
        args.put(KEY_ISMARRIED, is_married);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}