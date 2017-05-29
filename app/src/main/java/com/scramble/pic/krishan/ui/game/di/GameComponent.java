package com.scramble.pic.krishan.ui.game.di;

import com.scramble.pic.krishan.di.AppComponent;
import com.scramble.pic.krishan.di.scope.ActivityScope;
import com.scramble.pic.krishan.ui.game.GameContract;
import com.scramble.pic.krishan.ui.game.GameActivity;

import dagger.Component;

/**
 * Created by krishan on 23/05/17.
 */

@ActivityScope
@Component (dependencies = {AppComponent.class}, modules = {GameModule.class})
public interface GameComponent {
    GameContract.Screen getView();

    void inject(GameActivity homeActivity);
}
