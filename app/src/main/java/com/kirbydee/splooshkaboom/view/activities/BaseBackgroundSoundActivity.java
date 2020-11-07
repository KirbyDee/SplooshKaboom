package com.kirbydee.splooshkaboom.view.activities;

import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

public abstract class BaseBackgroundSoundActivity extends BaseActivity {

    private static final String TAG = BaseBackgroundSoundActivity.class.getName();

    private MediaPlayer player;

    private final Handler playerHandler = new Handler();

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();

        startMusic();
    }

    private void startMusic() {
        Log.i(TAG, "startMusic");
        stopMusic();
        int soundId = getBackgroundSoundId();
        long delay = getBackgroundSoundDelay();

        playerHandler.postDelayed(() -> {
            this.player = MediaPlayer.create(this, soundId);
            this.player.setLooping(true);
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

    protected abstract int getBackgroundSoundId();

    protected long getBackgroundSoundDelay() {
        return 0;
    }
}