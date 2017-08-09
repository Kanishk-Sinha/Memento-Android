package com.kanishk.code.bloop.data;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by kanishk on 19/7/17.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
