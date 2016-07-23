package com.tmp.micah.launcherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by micah-pc on 15/02/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME="atmpesa.db";
    public static final String TABLE_NAME="tblmpesa";
    public static final String COL_1="id";
    public static final String COL_2="maxamount";
    public static final String COL_3="minamount";
    public static final String COL_4="regcharge";
    public static final String COL_5="unregcharge";
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,DATABASE_NAME,null,3);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_NAME + " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_2 + " TEXT NOT NULL, " +
                    COL_3 + " TEXT NOT NULL, " +
                    COL_4 + " TEXT NOT NULL, " +
                    COL_5 + " TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String maxamount, String minamount, String regcharge, String unregcharge){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,maxamount);
        contentValues.put(COL_3,minamount);
        contentValues.put(COL_4,regcharge);
        contentValues.put(COL_5,unregcharge);
        long result =db.insert(TABLE_NAME,null,contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
}
