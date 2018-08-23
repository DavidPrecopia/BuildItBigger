package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.support.multidex.MultiDex;

import timber.log.Timber;

public final class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Fixes MultiDex issue pre-API 21
        MultiDex.install(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
