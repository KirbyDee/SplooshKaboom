package com.kirbydee.splooshkaboom.view.activities;

import android.media.MediaPlayer;
import android.util.Log;
import android.widget.VideoView;

import com.kirbydee.splooshkaboom.controller.media.SoundController;
import com.kirbydee.splooshkaboom.controller.media.VideoController;
import com.kirbydee.splooshkaboom.model.media.Sound;
import com.kirbydee.splooshkaboom.model.media.Video;
import com.kirbydee.splooshkaboom.model.media.Volume;

import static com.kirbydee.splooshkaboom.model.media.MonoVolume.NORMAL;

public abstract class MediaBaseActivity extends BaseActivity {

    private static final String TAG = MediaBaseActivity.class.getName();

    // controllers
    private SoundController soundController;
    private VideoController videoController;

    // video (possible)
    private VideoView videoView;

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
        startMusic();
    }

    protected void restartMusic() {
        Log.i(TAG, "restartMusic");
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
    protected void onPause() {
        Log.i(TAG, "onPause");
        stopMusic();

        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        stopMusic();

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        stopMusic();

        super.onDestroy();
    }

    @Override
    protected void cleanUp() {
        super.cleanUp();
        Log.i(TAG, "cleanUp");

        this.soundController.stopAll();
        if (this.videoView != null) {
            this.videoController.stop(this.videoView);
        }
    }

    @Override
    protected void setUpViews() {
        super.setUpViews();
        Log.i(TAG, "setUpViews");

        int videoViewId = getVideoViewId();
        this.videoView = videoViewId > 0 ? findViewById(videoViewId) : null;
    }

    protected void init() {
        super.init();
        Log.i(TAG, "init");

        this.soundController = initWithHandlerController(handlerController -> new SoundController(this, handlerController));
        this.videoController = new VideoController(this);
    }

    protected void stop(final Sound sound) {
        this.soundController.stop(sound);
    }

    protected void changeVolume(final Sound sound, Volume volume) {
        this.soundController.changeVolume(sound, volume);
    }

    public void play(final Sound sound) {
        this.soundController.play(sound);
    }

    protected void play(final Sound sound, final Volume volume) {
        this.soundController.play(sound, volume);
    }

    protected void play(final Sound sound, final MediaPlayer.OnCompletionListener listener) {
        this.soundController.play(sound, listener);
    }

    protected void play(final Sound sound, final Volume volume, final MediaPlayer.OnCompletionListener listener) {
        this.soundController.play(sound, volume, listener);
    }

    protected void play(final Sound sound, long delay) {
        this.soundController.play(sound, delay);
    }

    protected void play(final Sound sound, final Volume volume, long delay) {
        this.soundController.play(sound, volume, delay);
    }

    protected void play(final Sound sound, final MediaPlayer.OnCompletionListener listener, long delay) {
        this.soundController.play(sound, listener, delay);
    }

    protected void play(final Sound sound, final Volume volume, final MediaPlayer.OnCompletionListener listener, long delay) {
        this.soundController.play(sound, volume, listener, delay);
    }

    protected void stop() {
        Log.i(TAG, "stop video");
        this.videoView.stopPlayback();
    }

    protected void play(final Video video) {
        this.videoController.play(this.videoView, video);
    }

    protected void play(final Video video, final Volume volume) {
        this.videoController.play(this.videoView, video, volume);
    }

    protected void play(final Video video, final MediaPlayer.OnCompletionListener listener) {
        this.videoController.play(this.videoView, video, listener);
    }

    protected void play(final Video video, final Volume volume, final MediaPlayer.OnCompletionListener listener) {
        this.videoController.play(this.videoView, video, volume, listener);
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

    protected int getVideoViewId() {
        return -1;
    }
}