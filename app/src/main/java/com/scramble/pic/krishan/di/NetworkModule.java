package com.scramble.pic.krishan.di;

import com.scramble.pic.krishan.data.source.remote.FlickrApi;
import com.scramble.pic.krishan.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by krishan on 28/05/2017.
 */

@Module
public class NetworkModule {

    @Provides
    @ApplicationScope
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .baseUrl(FlickrApi.EndPoints.BASE_URL)
                .build();
    }

    @Provides
    @ApplicationScope
    FlickrApi providesApi(Retrofit retrofit) {
        return retrofit.create(FlickrApi.class);
    }
}
