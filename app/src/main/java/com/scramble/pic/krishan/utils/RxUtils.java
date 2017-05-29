package com.scramble.pic.krishan.utils;

import android.support.annotation.Nullable;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by krishan on 23/05/17.
 */

public final class RxUtils {
    private RxUtils() {
        throw new AssertionError("No instances for utility class");
    }

    public static void clear(@Nullable CompositeSubscription subscription) {
        if (subscription != null) {
            subscription.clear();
        }
    }


    public static void addToCompositeSubscription(CompositeSubscription compositeSubscription, Subscription subscription) {
        if (compositeSubscription != null && subscription != null) {
            compositeSubscription.add(subscription);
        }
    }

    public static <T> Observable.Transformer<T, T> applyIOScheduler() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
