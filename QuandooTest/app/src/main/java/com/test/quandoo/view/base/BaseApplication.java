package com.test.quandoo.view.base;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Base application for the app,
 * Used to access the application context within the application classes.
 */
public class BaseApplication extends Application
{
    private static Context context = null; // application Context
    private static BaseApplication application = null;

    @Override
    public void onCreate(){
        super.onCreate();

        context = getApplicationContext();

        application = this;
    }

    public static Context getContext(){
        return context;
    }

    static public BaseApplication getApplication(){
        return application;
    }
}
