package com.scramble.pic.krishan.di;

import android.app.Application;

import com.scramble.pic.krishan.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by krishan on 28/05/2017.
 */

@Module
public class ApplicationModule {

    private Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationScope
    Application providesApplication() {
        return mApplication;
    }

}
