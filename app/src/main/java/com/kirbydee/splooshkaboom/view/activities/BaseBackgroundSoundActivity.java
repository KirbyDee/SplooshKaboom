package com.kirbydee.splooshkaboom.view.activities;

import android.util.Log;

import com.kirbydee.splooshkaboom.utils.sound.Sounds;
import com.kirbydee.splooshkaboom.utils.sound.Volume;

import static com.kirbydee.splooshkaboom.utils.sound.MonoVolume.NORMAL;

public abstract class BaseBackgroundSoundActivity extends BaseActivity {

    private static final String TAG = BaseBackgroundSoundActivity.class.getName();

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
        Volume volume = getBackgroundVolume();
        long delay = getBackgroundSoundDelay();

        if (delay <= 0) {
            this.sound.play(sound, volume);
        }
        else {
            this.sound.play(sound, volume, delay);
        }
    }

    private void stopMusic() {
        Log.i(TAG, "stopMusic");
        this.sound.stop(getBackgroundSound());
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

    protected abstract Sounds getBackgroundSound();

    protected Volume getBackgroundVolume() {
        return NORMAL;
    }

    protected long getBackgroundSoundDelay() {
        return 0;
    }
}