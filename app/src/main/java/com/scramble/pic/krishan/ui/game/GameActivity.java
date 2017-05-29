package com.scramble.pic.krishan.ui.game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.scramble.krishan.picscramble.R;
import com.scramble.pic.krishan.GameApplication;
import com.scramble.pic.krishan.ui.game.di.DaggerGameComponent;
import com.scramble.pic.krishan.ui.game.di.GameComponent;
import com.scramble.pic.krishan.ui.game.di.GameModule;
import com.scramble.pic.krishan.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class GameActivity extends AppCompatActivity {

    @Inject
    GamePresenter presenter;

    private GameFragment gameFragment;
    private GameComponent gameComponent;

    public static Intent createIntent(Context context) {
        return new Intent(context, GameActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameFragment = (GameFragment) getSupportFragmentManager()
                .findFragmentById(R.id.container);

        if (gameFragment == null) {
            gameFragment = GameFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, gameFragment)
                    .commit();
        }
        initDi();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }

    private void initDi() {
        gameComponent = DaggerGameComponent.builder()
                .appComponent(((GameApplication) getApplication()).getAppComponent())
                .gameModule(new GameModule(gameFragment))
                .build();

        gameComponent.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameComponent = null;
    }

    public static class GameFragment extends Fragment
            implements GameContract.Screen,
            GameAdapter.AdapterEventListener {

        @BindView (R.id.recyclerView)
        RecyclerView mRecyclerView;

        @BindView (R.id.progressBar)
        ProgressBar mProgressBar;

        @BindView (R.id.timer)
        TextView mTimer;

        @BindView (R.id.findImage)
        ImageView mFindImage;

        @BindView (R.id.completeMsg)
        TextView mCompletedTextView;

        @BindView (R.id.startAgain)
        Button startAgain;

        public static final int SPAN_COUNT = 3;

        private GameAdapter mRecyclerViewGameAdapter;

        private GameContract.Presenter mPresenter;
        private Unbinder binder;
        private CountDownTimer countDownTimer;

        public static GameFragment newInstance() {
            return new GameFragment();
        }

        @Override
        public void setPresenter(GameContract.Presenter presenter) {
            mPresenter = presenter;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_game, container, false);
            binder = ButterKnife.bind(this, rootView);
            return rootView;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            mRecyclerViewGameAdapter = new GameAdapter(new ArrayList<>(),
                    this);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT));
            mRecyclerView.setAdapter(mRecyclerViewGameAdapter);
            mPresenter.getFeedImages();
        }

        @Override
        public void showProgressBar() {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void hideProgressBar() {
            mProgressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        public void startTimer() {
            countDownTimer = new CountDownTimer(16000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    showHidePuzzleImageView(false);
                    showHideTimerTextView(true);
                    mTimer.setText(String.valueOf(millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    mTimer.setText("");
                    showHideTimerTextView(false);
                    mRecyclerViewGameAdapter.setShowPlaceHolder(true);
                    mRecyclerViewGameAdapter.notifyDataSetChanged();

                    // Shuffle the list
                    mPresenter.shuffleImages();

                    showHidePuzzleImageView(true);
                    loadImageView();
                }
            };
            countDownTimer.start();
        }

        private void showHidePuzzleImageView(boolean isToBeShown) {
            if (isToBeShown) {
                mFindImage.setVisibility(View.VISIBLE);
                return;
            }

            mFindImage.setVisibility(View.GONE);
        }

        private void showHideTimerTextView(boolean isToBeShown) {
            if (isToBeShown) {
                mTimer.setVisibility(View.VISIBLE);
                return;
            }

            mTimer.setVisibility(View.GONE);
        }

        @Override
        public void loadImageView() {
            String imageUrl = mPresenter.getItem();
            Picasso.with(getContext())
                    .load(imageUrl)
                    .transform(new CircleTransform())
                    .into(mFindImage);
        }

        @OnClick (R.id.startAgain)
        public void onStartAgainButtonClick() {
            mPresenter.startAgain();
        }

        @Override
        public void gameCompleted() {
            mTimer.setVisibility(View.GONE);
            mFindImage.setVisibility(View.GONE);
            startAgain.setVisibility(View.VISIBLE);
            mCompletedTextView.setVisibility(View.VISIBLE);
        }

        @Override
        public void launchGameActivity() {
            getActivity().finish();
            startActivity(GameActivity.createIntent(getActivity()));
        }

        @Override
        public void showImageGrid(List<String> items) {
            mRecyclerViewGameAdapter.setShowPlaceHolder(false);
            mRecyclerViewGameAdapter.notifyChange(items);
        }

        @Override
        public void error(String message) {

        }


        @Override
        public void correctAnswer(int position) {
            mRecyclerViewGameAdapter.setShowPlaceHolder(false);
            mRecyclerViewGameAdapter.notifyItemChanged(position);
        }

        @Override
        public void wrongAnswer() {
            Toast.makeText(getContext(), R.string.try_again_text,
                    Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onItemClicked(int pos) {
            mPresenter.onImageSelected(pos);
        }

        @Override
        public void imageLoaded() {
            mPresenter.imageLoaded();
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binder.unbind();
            mPresenter.stop();
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
        }

    }

}
