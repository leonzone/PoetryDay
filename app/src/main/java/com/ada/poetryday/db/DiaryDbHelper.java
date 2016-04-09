package com.ada.poetryday.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wwjun.wang on 2015/8/19.
 */
public class DiaryDbHelper extends SQLiteOpenHelper {
    public DiaryDbHelper(Context context, int version) {
        super(context, "save.db", null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists DiaryList (id INTEGER primary key autoincrement,date INTEGER unique,diary text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
