package com.example.mcs_productstockmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Products.db";
    public static final Integer DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "product_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "DESCRIPTION";
    public static final String COL_4 = "QUANTITY";
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String createTable =  "CREATE TABLE " + TABLE_NAME + " (" +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT NOT NULL, " +
                COL_3 + " TEXT NOT NULL, " +
                COL_4 + " INTEGER NOT NULL" + ");";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String name, String desc, String qty){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, name);
        cv.put(COL_3, desc);
        cv.put(COL_4, qty);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }

    public Cursor getSpecificData( String pos){
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL_1 + " LIKE ?";
        String[] selArgs = {pos};
        Cursor res = db.query(TABLE_NAME, null, selection, selArgs, null, null, null);
        return res;
    }

    public boolean editData(String id, String name, String desc, String qty){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, desc);
        contentValues.put(COL_4, qty);
        db.update(TABLE_NAME, contentValues, "id = ?", new String[]{id});
        return true;
    }
}