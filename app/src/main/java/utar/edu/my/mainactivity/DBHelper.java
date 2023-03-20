package utar.edu.my.mainactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table UserScore(name TEXT primary key, score TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists UserScore");
    }

    public Boolean insertuserdata(String name, int score) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("score", score);
        long result = DB.insert("UserScore", null, cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public Boolean checkname(String name,int score) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("score", score);
        Cursor cursor = DB.rawQuery("Select * from UserScore where name=?", new String[]{name});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Cursor getdata() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query("UserScore", new String[] {"name", "score"},
                null, null, null, null, "score" + " DESC");
    }
    public Integer deleteData(){
        SQLiteDatabase db= this.getWritableDatabase();
        return db.delete("UserScore",null,null);
    }
}





