package com.scramble.pic.krishan.ui.game;

import com.scramble.pic.krishan.di.scope.ActivityScope;
import com.scramble.pic.krishan.domain.GetFeedImagesUseCase;
import com.scramble.pic.krishan.utils.RxUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by krishan on 28/05/2017.
 */

@ActivityScope
public class GamePresenter implements GameContract.Presenter {

    private GameContract.Screen mView;
    private CompositeSubscription mCompositeSubscription;
    private GetFeedImagesUseCase imagesUseCase;

    private static final int NO_OF_IMAGES = 9;

    private List<Integer> imagePositionList;
    private int current_index = 0;
    private List<String> mImageList = new ArrayList<>();
    private int imageCounter;


    @Inject
    public GamePresenter(GameContract.Screen view,
                         GetFeedImagesUseCase imagesUseCase) {
        mView = view;
        mView.setPresenter(this);
        mCompositeSubscription = new CompositeSubscription();
        this.imagesUseCase = imagesUseCase;
        initImagePositions();
    }

    private void initImagePositions() {
        imagePositionList = new ArrayList<>();
        int i = 0;
        while (i < NO_OF_IMAGES) {
            imagePositionList.add(i++);
        }
    }


    private void correctAnswer() {
        mView.correctAnswer(imagePositionList.get(current_index));
        ++current_index;
        if (current_index < 9) {
            mView.loadImageView();
        }
        else {
            mView.gameCompleted();
        }

    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        RxUtils.clear(mCompositeSubscription);
    }

    @Override
    public void getFeedImages() {
        mView.showProgressBar();
        RxUtils.addToCompositeSubscription(mCompositeSubscription,
                imagesUseCase.getImageUrlList(NO_OF_IMAGES)
                        .compose(RxUtils.applyIOScheduler())
                        .subscribe(imageUrl -> mImageList.add(imageUrl), throwable -> {
                            mView.error(throwable.getMessage());
                        }, () -> {
                            mView.showImageGrid(mImageList);
                        }));
    }

    @Override
    public void onImageSelected(int clickedPos) {
        if (current_index > imagePositionList.size() - 1) {
            return;
        }
        if (clickedPos == imagePositionList.get(current_index)) {
            correctAnswer();
        }
        else {
            mView.wrongAnswer();
        }
    }

    @Override
    public void shuffleImages() {
        Collections.shuffle(imagePositionList);
    }

    @Override
    public void startAgain() {
        mView.launchGameActivity();
    }

    @Override
    public void imageLoaded() {
        imageCounter++;
        if (imageCounter == 9) {
            mView.hideProgressBar();
            mView.startTimer();
        }
    }

    @Override
    public String getItem() {
        return mImageList.get(imagePositionList.get(current_index));
    }
}
