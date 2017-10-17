package com.lucascabrales.gradesdatabase.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.lucascabrales.gradesdatabase.MainActivity;

/**
 * Created by lucascabrales on 10/16/17.
 */

public enum StorageManager {
    INSTANCE;
    private static final String DEFAULT_PREFS = "SP_DEFAULT";
    private static final String ORDER_BY = "SP_ORDER_BY";

    private Context mContext;
    private SharedPreferences mSettings;
    public SQLiteDatabase db;

    StorageManager() {
    }

    public static void setBaseContext(Context ctx) {
        INSTANCE.setContext(ctx);
        Database dataBase = new Database(ctx);
        INSTANCE.db = dataBase.getWritableDatabase();
    }


    private void setContext(Context ctx) {
        mContext = ctx;
    }
    public static Context getBaseContext() {
        return INSTANCE.mContext;
    }

    private SharedPreferences getSetting() {
        if (mSettings == null)
            mSettings = mContext.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE);
        return mSettings;
    }

    public static SQLiteDatabase getDb() {
        SQLiteDatabase db = INSTANCE.db;
        if (!db.isOpen()) {
            return INSTANCE.db = new Database(getBaseContext()).getWritableDatabase();
        }
        return db;
    }

    public static void clearAll() {
        SharedPreferences.Editor editor = INSTANCE.getSetting().edit();
        editor.clear();
        editor.apply();
    }

    public static void setOrderBy(String value) {
        SharedPreferences.Editor editor = INSTANCE.getSetting().edit();
        editor.putString(ORDER_BY, value);
        editor.apply();
    }

    public static String getOrderBy() {
        return INSTANCE.getSetting().getString(ORDER_BY, MainActivity.SUBJECT);
    }
}