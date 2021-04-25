package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Users.db";
    public static final String TABLE_NAME = "Users";
    public static final String COLUMN_ID = "userId";
    public static final String COLUMN_FNAME = "firstName";
    public static final String COLUMN_LNAME = "lastName";
    public static final String COLUMN_PHONENUMBER = "phoneNumber";
    public static final String COLUMN_EMAILADDRESS = "emailAddress";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME +"(" +
                        COLUMN_ID + " TEXT PRIMARY KEY, " +
                        COLUMN_FNAME + " TEXT NOT NULL, " +
                        COLUMN_LNAME + " TEXT NOT NULL, " +
                        COLUMN_PHONENUMBER + " TEXT NOT NULL, " +
                        COLUMN_EMAILADDRESS + " TEXT NOT NULL)"
        );
        Log.d("Yasmeen","Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addUser(String id, String fname, String lname, String phone, String email){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_FNAME, fname);
        values.put(COLUMN_LNAME, lname);
        values.put(COLUMN_PHONENUMBER, phone);
        values.put(COLUMN_EMAILADDRESS, email);

        long result = db.insert(TABLE_NAME, null, values);
        if(result == -1) {return false;} else {return true;}
    }

    public Cursor viewUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor x = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return x;
    }

    public Integer deleteUser(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "userId = ?", new String[]{id});
    }

    public void updateUserfName(String id, String fname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_FNAME, fname);
        db.update(TABLE_NAME, values, "userId = ?", new String[]{id});
    }

    public void updateUserlName(String id, String lname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_LNAME, lname);
        db.update(TABLE_NAME, values, "userId = ?", new String[]{id});
    }

    public void updateUserEmail(String id, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_EMAILADDRESS, email);
        db.update(TABLE_NAME, values, "userId = ?", new String[]{id});
    }

    public void updateUserPhone(String id, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_PHONENUMBER, phone);
        db.update(TABLE_NAME, values, "userId = ?", new String[]{id});
    }

    public Cursor findUser(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_FNAME, COLUMN_LNAME, COLUMN_PHONENUMBER, COLUMN_EMAILADDRESS}, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }
}
