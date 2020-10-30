package com.kirbydee.splooshkaboom;

import android.app.Application;
import android.content.Context;

public class SplooshKaboomApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        SplooshKaboomApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return SplooshKaboomApplication.context;
    }
}
