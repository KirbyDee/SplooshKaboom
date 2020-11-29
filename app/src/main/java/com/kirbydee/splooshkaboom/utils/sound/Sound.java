package com.kirbydee.splooshkaboom.utils.sound;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.kirbydee.splooshkaboom.controller.HandlerController;
import com.kirbydee.splooshkaboom.view.activities.GameActivity;

import java.util.HashMap;
import java.util.Map;

import static com.kirbydee.splooshkaboom.utils.sound.MonoVolume.NORMAL;

public class Sound {

    private static final String TAG = GameActivity.class.getName();

    private final Context context;

    private final HandlerController handlerController;

    private final Map<Sounds, MediaPlayer> mediaPlayerMap;

    public Sound(Context context, HandlerController handlerController) {
        this.context = context;
        this.handlerController = handlerController;
        this.mediaPlayerMap = new HashMap<>();
    }

    public MediaPlayer stop(final Sounds sound) {
        Log.i(TAG, "stopSound (" + sound + ")");
        MediaPlayer mediaPlayer = this.mediaPlayerMap.get(sound);
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this.context, sound.getRes());
        this.mediaPlayerMap.put(sound, mediaPlayer);
        return mediaPlayer;
    }

    public void play(final Sounds sound) {
        Log.i(TAG, "play Sound (" + sound + ")");
        play(sound, NORMAL);
    }

    public void play(final Sounds sound, final Volume volume) {
        Log.i(TAG, "play Sound (" + sound + ", " + volume + ")");
        MediaPlayer mediaPlayer = stop(sound);
        mediaPlayer.setVolume(volume.getLeft(), volume.getRight());
        mediaPlayer.setLooping(sound.isLoop());
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(MediaPlayer::stop);
    }

    public void play(final Sounds sound, long delay) {
        Log.i(TAG, "play Sound (" + sound + ", " + delay + "ms)");
        play(sound, NORMAL, delay);
    }

    public void play(final Sounds sound, final Volume volume, long delay) {
        Log.i(TAG, "play Sound (" + sound + ", " + volume + ", " + delay + "ms)");
        this.handlerController.postDelayed(() -> play(sound, volume), delay);
    }
}
