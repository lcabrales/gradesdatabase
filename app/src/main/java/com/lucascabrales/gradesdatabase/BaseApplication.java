package com.lucascabrales.gradesdatabase;

import android.app.Application;

import com.lucascabrales.gradesdatabase.data.StorageManager;

/**
 * Created by lucascabrales on 10/16/17.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        StorageManager.setBaseContext(getApplicationContext());
    }
}