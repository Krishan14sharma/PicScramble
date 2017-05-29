package com.scramble.pic.krishan;

import android.app.Application;

import com.scramble.pic.krishan.di.AppComponent;
import com.scramble.pic.krishan.di.ApplicationModule;
import com.scramble.pic.krishan.di.DaggerAppComponent;
import com.scramble.pic.krishan.di.NetworkModule;

/**
 * Created by krishan on 28/05/2017.
 */

public class GameApplication extends Application {
    private AppComponent mAppComponent;

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initDi();
    }

    private void initDi() {
        mAppComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule())
                .build();
    }
}
