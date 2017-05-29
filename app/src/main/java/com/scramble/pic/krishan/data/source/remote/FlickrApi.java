package com.scramble.pic.krishan.data.source.remote;

import com.scramble.pic.krishan.data.model.FlickrFeed;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by krishan on 28/05/2017.
 */

public interface FlickrApi {
    @GET (EndPoints.FEED_END_POINT)
    Observable<FlickrFeed> getFeedList();

    interface EndPoints {
        String BASE_URL = "https://api.flickr.com";
        String FEED_END_POINT = "/services/feeds/photos_public.gne?&lang=en-us&format=json&nojsoncallback=1";
    }
}
