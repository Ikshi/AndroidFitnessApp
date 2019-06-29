package com.example.ikshita.supfitness3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Database for map

public class DBManagerMap extends SQLiteOpenHelper {

    public static final String DATABASENAME = "supfitness.db";
    public static final String TABLENAME = "map_table";
   // public static final String COLUSER = "User";
    public static final String COLLONGITUDE = "longitude";
    public static final String COLLATITUDE = "latitude";


    public DBManagerMap(Context context){
        super(context,DATABASENAME,null,1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
       String createTable ="CREATE TABLE "+ TABLENAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "LONGITUDE DOUBLE, LATITUDE DOUBLE)";

       db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("DROP TABLE IF EXISTS "+ TABLENAME);
    }

    //method to insert data into database
    public boolean addData(Double longitude, Double latitude){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COLLATITUDE,latitude);
        contentvalues.put(COLLONGITUDE,longitude);
        //contentvalues.put("DATE", String.valueOf(date));
        long result = db.insert(TABLENAME,null,contentvalues);

        if(result == -1 ){

            return false;
        }else {
            return true;
        }

    }


    public Cursor getContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLENAME,null);
        return data;
    }
}
