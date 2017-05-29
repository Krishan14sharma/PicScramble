package com.scramble.pic.krishan.data.source;

import com.scramble.pic.krishan.data.model.FlickrFeed;

import rx.Observable;

/**
 * Created by krishan on 28/05/2017.
 */

public interface ImageDataSource {
    Observable<FlickrFeed> getFeedList();
}
