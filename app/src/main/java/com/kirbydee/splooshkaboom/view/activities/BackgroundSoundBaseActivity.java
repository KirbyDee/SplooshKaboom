package com.kirbydee.splooshkaboom.view.activities;

import android.media.MediaPlayer;
import android.util.Log;

import com.kirbydee.splooshkaboom.model.media.Sound;
import com.kirbydee.splooshkaboom.model.media.Volume;

import static com.kirbydee.splooshkaboom.model.media.MonoVolume.NORMAL;

public abstract class BackgroundSoundBaseActivity extends BaseActivity {

    private static final String TAG = BackgroundSoundBaseActivity.class.getName();

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
        Sound sound = getBackgroundSound();
        Volume volume = getBackgroundVolume();
        long delay = getBackgroundSoundDelay();

        if (delay <= 0) {
            this.soundController.play(sound, volume, getOnCompletionListener());
        }
        else {
            this.soundController.play(sound, volume, getOnCompletionListener(), delay);
        }
    }

    private void stopMusic() {
        Log.i(TAG, "stopMusic");
        this.soundController.stop(getBackgroundSound());
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        stopMusic();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        stopMusic();
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        stopMusic();
        super.onPause();
    }

    protected abstract Sound getBackgroundSound();

    protected Volume getBackgroundVolume() {
        return NORMAL;
    }

    protected long getBackgroundSoundDelay() {
        return 0;
    }

    protected MediaPlayer.OnCompletionListener getOnCompletionListener() {
        return null;
    }
}