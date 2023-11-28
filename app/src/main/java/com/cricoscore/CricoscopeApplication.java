package com.cricoscore;

import android.app.Application;
import android.content.Context;

public class CricoscopeApplication extends Application {

    private static Context _Context;

    public static Context getContext() {
        return _Context;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        _Context=this;
    }
}
