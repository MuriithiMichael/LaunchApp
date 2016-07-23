package com.tmp.micah.launcherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Micah on 17/05/2016.
 */
public class ManagePackages extends DatabaseHandler {

    private  static final String TABLE_MPESA="mpesa_charges";
    private  static final String TABLE_ATM="atm_charges";
    SQLiteDatabase db, db1;


    public ManagePackages(Context context) {
        super(context);
    }


    public boolean addNewPackage(String packageName, int isVisible){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("package_name",packageName);
        values.put("status",isVisible);

        boolean status=db.insert("installed_apps",null,values)>0;
        db.close();

        return status;
    }

    public boolean addNewPackage2(String packageName, int isVisible){
        boolean isExist = select_Apps(packageName);
        boolean status = false;
        if(!isExist){
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("package_name",packageName);
            values.put("status",isVisible);

            status=db.insert("installed_apps",null,values)>0;
            db.close();
        }else{

        }
        return status;
    }

    public boolean select_Apps(String appname){
        db1 = this.getReadableDatabase();
        boolean status=false;
        Cursor cursor = null;
        String sql = "SELECT * FROM installed_apps WHERE package_name=?";
        String [] values = new String[]{appname};
        cursor = db1.rawQuery(sql, values);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            status = true;
        }else{
            status = false;
        }
        cursor.close();
        db1.close();
        return status;
    }

    public boolean addNewUser(String username, String pin){
        boolean isExist = select_User(pin);
        boolean status = false;
        if(!isExist){
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("user_name",username);
            values.put("pin",pin);

            status=db.insert("user",null,values)>0;
            db.close();
        }else{

        }
        return status;
    }

    public boolean select_User(String pin){
        db1 = this.getReadableDatabase();
        boolean status=false;
        Cursor cursor = null;
        String sql = "SELECT * FROM user";
       // String [] values = new String[]{pin};
        cursor = db1.rawQuery(sql, null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            status = true;
        }else{
            status = false;
        }
        cursor.close();
        db1.close();
        return status;
    }

    public boolean select_LoginUser(String pin){
        db1 = this.getReadableDatabase();
        boolean status=false;
        Cursor cursor = null;
        String sql = "SELECT * FROM user where pin=?";
        String [] values = new String[]{pin};
        cursor = db1.rawQuery(sql, values);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            status = true;
        }else{
            status = false;
        }
        cursor.close();
        db1.close();
        return status;
    }

    public boolean updatePackage(int packageID,String packageName,int isVisible){
    db = this.getWritableDatabase();
      ContentValues values = new ContentValues();
        values.put("package_name",packageName);
        values.put("status",isVisible);

        String[] whereArgs = {""+packageID};

       boolean status= db.update("installed_apps",values,"id",whereArgs)>0;

        return status;
    }
    public boolean updatePackageStatus(String packageName,int isVisible){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status",isVisible);

        String[] whereArgs = {packageName};

        boolean status= db.update("installed_apps",values,"package_name=?",whereArgs)>0;

        return status;
    }
    public ArrayList<HashMap<String,String>> getApps(int action){
    db=this.getReadableDatabase();
        Cursor cursor = null;
        String id="",package_name="",status="";
        ArrayList<HashMap<String,String>> arrayPackages = new ArrayList<HashMap<String,String>>();

        db = this.getReadableDatabase();
        if(action==0) {
            String sql = "SELECT id,package_name,status FROM installed_apps";
            cursor = db.rawQuery(sql, null);
        }
        else if(action==1) {
            String sql2 = "SELECT id,package_name,status FROM installed_apps WHERE status=1";
            cursor = db.rawQuery(sql2, null);
        }else if(action==2){
            String sql2 = "SELECT id,package_name,status FROM installed_apps WHERE status=0";
            cursor = db.rawQuery(sql2, null);
        }
        else{

        }
        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> packageDetails = new HashMap<String,String>();
                id=cursor.getString(0);
                package_name=cursor.getString(1);
                status=cursor.getString(2);

                packageDetails.put("id",id);
                packageDetails.put("package_name",package_name);
                packageDetails.put("status",status);
                arrayPackages.add(packageDetails);

            } while (cursor.moveToNext());
        }


        return arrayPackages;
    }

    public boolean deletePackage(int packageID){
        db = this.getWritableDatabase();
      String[] whereArgs={""+packageID};
       boolean status= db.delete("installed_apps" , "id",whereArgs)>0;

        return status;
    }

    public HashMap<String,String> packageDetails(int packageID){
        HashMap<String,String> packageData = new HashMap<String, String>();
        String package_name="",status="";
        db = this.getReadableDatabase();
        String sql="SELECT id,package_name,status FROM installed_apps WHERE id='"+packageID+"'";

        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                package_name=cursor.getString(1);
                status=cursor.getString(2);

                packageData.put("package_name",package_name);
                packageData.put("status",status);
            } while (cursor.moveToNext());
        }

        return packageData;
    }
}
