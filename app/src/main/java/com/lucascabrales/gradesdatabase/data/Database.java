package com.lucascabrales.gradesdatabase.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lucascabrales.gradesdatabase.models.Grade;

/**
 * Created by lucascabrales on 10/16/17.
 */

public class Database extends SQLiteOpenHelper {

    private static final String TAG = "DATA_BASE";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DataBase.db";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Grade.TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}