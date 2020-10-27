package com.kirbydee.splooshkaboom.view.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;

import com.kirbydee.splooshkaboom.R;

public class GameActivity extends BaseBackgroundSoundActivity {

    private static final String TAG = GameActivity.class.getName();

    private MediaPlayer soundPlayer;

    private TableLayout gameGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        playStartSound();
    }
    private void stopSound() {
        Log.i(TAG, "stopSound");
        if (soundPlayer != null) {
            soundPlayer.stop();
            soundPlayer.release();
            soundPlayer = null;
        }
    }

    private void playStartSound() {
        Log.i(TAG, "playStartSound");
        stopSound();
        this.soundPlayer = MediaPlayer.create(this, R.raw.game_intro);
        this.soundPlayer.start();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();


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