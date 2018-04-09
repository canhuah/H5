package com.canhuah.h5;

import android.app.Application;

/**
 * Created by 黄灿华 on 2018/4/8 14:44
 */

public class AppContext extends Application {

    private static AppContext sInstance;

    public static AppContext getInstance() {
        return sInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

    }

}
