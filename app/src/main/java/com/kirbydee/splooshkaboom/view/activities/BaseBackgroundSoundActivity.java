package com.kirbydee.splooshkaboom.view.activities;

import android.media.MediaPlayer;
import android.util.Log;

import com.kirbydee.splooshkaboom.utils.Sounds;

public abstract class BaseBackgroundSoundActivity extends BaseActivity {

    private static final String TAG = BaseBackgroundSoundActivity.class.getName();

    private MediaPlayer player;

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();

        startMusic();
    }

    protected void restartMusic() {
        Log.i(TAG, "restart");
        startMusic();
    }

    private void startMusic() {
        Log.i(TAG, "startMusic");
        stopMusic();
        Sounds sound = getBackgroundSound();
        long delay = getBackgroundSoundDelay();

        runAfterDelay(() -> {
            this.player = MediaPlayer.create(this, sound.getRes());
            this.player.setLooping(loopMusic());
            this.player.start();
        }, delay);
    }

    private void stopMusic() {
        Log.i(TAG, "stopMusic");
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        stopMusic();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        stopMusic();
        super.onPause();
    }

    protected abstract Sounds getBackgroundSound();

    protected long getBackgroundSoundDelay() {
        return 0;
    }

    protected boolean loopMusic() {
        return true;
    }
}