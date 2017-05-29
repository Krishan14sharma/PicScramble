package com.scramble.pic.krishan.ui.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.scramble.krishan.picscramble.R;
import com.scramble.pic.krishan.ui.game.GameActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by krishan on 23/05/17.
 */

public class MainActivity extends AppCompatActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @OnClick (R.id.startGame)
    public void onStartGame() {
        startActivity(GameActivity.createIntent(this));
    }
}
