package com.scramble.pic.krishan.domain;

import com.scramble.pic.krishan.data.model.FlickrFeed;
import com.scramble.pic.krishan.data.source.ImageDataSource;
import com.scramble.pic.krishan.data.source.remote.ImageRemoteDataSource;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by krishan on 28/05/2017.
 */

public class GetFeedImagesUseCase {
    private ImageDataSource mImageDataSource;

    @Inject
    public GetFeedImagesUseCase(ImageRemoteDataSource imageRemoteDataSource) {
        mImageDataSource = imageRemoteDataSource;
    }

    public Observable<String> getImageUrlList(int noOfImages) {
        return mImageDataSource.getFeedList()
                .flatMap(feedsList -> Observable.from(feedsList.getItems()))
                .map(FlickrFeed.FeedItem:: getImageUrl) // throw imageUrls
                .take(noOfImages); // limit no. of urls thrown
    }
}
