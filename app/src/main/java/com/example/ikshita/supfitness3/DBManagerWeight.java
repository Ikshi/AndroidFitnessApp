package com.example.ikshita.supfitness3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;


// Database for Weight
public class DBManagerWeight extends SQLiteOpenHelper {

    public static final String DATABASENAME = "supfitness.db";
    public static final String TABLENAME = "supfitness_table";
    public static final String COLWEIGHT = "WEIGHT";
    public static final String COLDATE = "DATE";

    public DBManagerWeight(Context context){
        super(context,DATABASENAME,null,1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
       String createTable ="CREATE TABLE "+ TABLENAME + "UNIQUE(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "WEIGHT DOUBLE, " + "DATE DATETIME default current_timestamp)";

       db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("DROP TABLE IF EXISTS "+ TABLENAME);
    }

    //add data to db
    public boolean addData(Double weight){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COLWEIGHT,weight);
        contentvalues.put(COLDATE, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        long result = db.insert(TABLENAME,null,contentvalues);

        if(result == -1 ){

            return false;
        }else {
            return true;
        }

    }

    //list data from db
    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLENAME,null);
        return data;
    }

  /*  public Cursor getListDates(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dataDate = db.rawQuery("SELECT DATE FROM " + TABLENAME,null);
        return dataDate;
    }*/
}
