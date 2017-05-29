package com.scramble.pic.krishan.ui.game.di;

import com.scramble.pic.krishan.di.scope.ActivityScope;
import com.scramble.pic.krishan.ui.game.GameContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by krishan on 23/05/17.
 */

@Module
public class GameModule {
    private final GameContract.Screen mView;

    public GameModule(GameContract.Screen view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    GameContract.Screen provideGameView() {
        return mView;
    }
}
