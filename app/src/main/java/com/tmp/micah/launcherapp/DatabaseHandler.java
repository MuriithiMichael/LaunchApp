package com.tmp.micah.launcherapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Geofrey on 2/12/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="applist";
    private static final String TABLE_NAME="installed_apps";
    private static final String TABLE_NAME1="user";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table_name="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  package_name VARCHAR(1000)," +
                "  status INTEGER " +
                ")";
        String table_name1="CREATE TABLE IF NOT EXISTS "+TABLE_NAME1+" (" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  user_name VARCHAR(1000)," +
                "  pin VARCHAR(1000) " +
                ")";
        db.execSQL(table_name);
        db.execSQL(table_name1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
