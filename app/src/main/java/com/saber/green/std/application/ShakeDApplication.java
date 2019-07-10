package com.saber.green.std.application;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

public class ShakeDApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
