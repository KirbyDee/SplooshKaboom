package com.kirbydee.splooshkaboom.view.activities;

import android.os.Bundle;
import android.util.Log;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.model.GameState;
import com.kirbydee.splooshkaboom.utils.Sound;
import com.kirbydee.splooshkaboom.utils.Sounds;

public class GameActivity extends BaseBackgroundSoundActivity {

    private static final String TAG = GameActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();

        Sound.playSound(Sounds.INTRO);
        GameState.reset();
    }

    @Override
    protected void setUpViews() {
        Log.i(TAG, "setUpViews");
    }

    @Override
    protected void setUpListeners() {
        Log.i(TAG, "setUpListeners");
    }

    @Override
    protected int getBackgroundSoundId() {
        Log.i(TAG, "getBackgroundSoundId");
        return R.raw.game;
    }

    @Override
    protected long getBackgroundSoundDelay() {
        return 1300;
    }
}