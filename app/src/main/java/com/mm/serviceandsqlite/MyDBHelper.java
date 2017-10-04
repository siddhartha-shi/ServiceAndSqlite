package com.mm.serviceandsqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by o on 4/10/2017.
 * sid
 */
class MyDBHelper extends SQLiteOpenHelper{

    private static final String TAG              = "MyDBHelper";
    private static final String DATABASE_NAME    = "test.db";
    private static final int    DATABASE_VERSION = 1;
    MyDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS TESTING (id INTEGER PRIMARY KEY AUTOINCREMENT, INFO_ID TEXT,"
                + " INFO_TEXT TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    static boolean isAnyInfoAvailable(Context ctx){
        boolean result = false;
        MyDBHelper dbh = null;
        SQLiteDatabase db = null;
        try {
            dbh = new MyDBHelper(ctx);
            db = dbh.getWritableDatabase();
            result = MyDBHelper.is_any_info_available(db);
        } catch (Throwable e) {
            Log.e(TAG, "isAnyInfoAvailable(): Caught - " + e.getClass().getName(), e);
        } finally {
            if (null != db)
                db.close();
            if (null != dbh)
                dbh.close();
        }
        return result;
    }

    private static boolean is_any_info_available(SQLiteDatabase db){
        boolean result = false;

        Cursor cInfo = db.rawQuery(
                "select INFO_ID from TESTING", null);
        if(cInfo != null)
        {
            if(cInfo.moveToFirst())
            {
                result = true;
            }
        }
        if(cInfo != null)
            cInfo.close();
        return result;
    }

    static void insertIntoDatabase(SQLiteDatabase db, String infoId, String infoText){
        //String sql = "INSERT INTO TESTING (INFO_ID, INFO_TEXT) VALUES ('?', '?');";
        //db.execSQL(sql, new String[]{"1", "Hello Sid"});
        String sql = "INSERT INTO TESTING (INFO_ID, INFO_TEXT) VALUES ('"+infoId+"', '"+infoText+"');";
        db.execSQL(sql);
        db.close();
    }
    static String show_available_data(SQLiteDatabase db){
        String result = "";

        Cursor cInfo = db.rawQuery(
                "select * from TESTING", null);
        if(cInfo != null)
        {
            if (cInfo.moveToFirst()) {
                do {
                    result += cInfo.getString(1)+" "+cInfo.getString(2)+"\n";
                } while (cInfo.moveToNext());
            }
        }
        if(cInfo != null)
            cInfo.close();
        return result;
    }
}