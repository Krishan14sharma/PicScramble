package com.scramble.pic.krishan.ui.game;

import com.scramble.pic.krishan.core.BasePresenter;
import com.scramble.pic.krishan.core.BaseScreen;

import java.util.List;

/**
 * Created by krishan on 23/05/17.
 */

public class GameContract {
    public interface Screen extends BaseScreen<Presenter> {

        void correctAnswer(int position);

        void wrongAnswer();

        void loadImageView();

        void gameCompleted();

        void showImageGrid(List<String> items);

        void error(String message);

        void launchGameActivity();

        void showProgressBar();

        void hideProgressBar();

        void startTimer();

    }

    public interface Presenter extends BasePresenter {

        void getFeedImages();

        void onImageSelected(int pos);

        void shuffleImages();

        String getItem();

        void startAgain();

        void imageLoaded();
    }
}
