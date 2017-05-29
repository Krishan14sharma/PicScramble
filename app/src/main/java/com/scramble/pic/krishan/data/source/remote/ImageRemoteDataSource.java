package com.scramble.pic.krishan.data.source.remote;

import com.scramble.pic.krishan.data.model.FlickrFeed;
import com.scramble.pic.krishan.data.source.ImageDataSource;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by krishan on 28/05/2017.
 */

public class ImageRemoteDataSource implements ImageDataSource {

    private FlickrApi mFlickrApi;

    @Inject
    ImageRemoteDataSource(FlickrApi flickrApi) {
        mFlickrApi = flickrApi;
    }

    @Override
    public Observable<FlickrFeed> getFeedList() {
        return mFlickrApi.getFeedList();
    }
}
