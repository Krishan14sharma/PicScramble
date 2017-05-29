package com.scramble.pic.krishan.di;

import com.scramble.pic.krishan.data.source.remote.FlickrApi;
import com.scramble.pic.krishan.di.scope.ApplicationScope;

import dagger.Component;

/**
 * Created by krishan on 28/05/2017.
 */

@ApplicationScope
@Component (modules = {ApplicationModule.class, NetworkModule.class})
public interface AppComponent {
    FlickrApi getApiService();
}
