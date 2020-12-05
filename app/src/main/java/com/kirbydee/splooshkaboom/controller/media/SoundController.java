package com.kirbydee.splooshkaboom.controller.media;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.kirbydee.splooshkaboom.controller.HandlerController;
import com.kirbydee.splooshkaboom.model.media.Sound;
import com.kirbydee.splooshkaboom.model.media.Volume;

import java.util.HashMap;
import java.util.Map;

import static com.kirbydee.splooshkaboom.model.media.MonoVolume.NORMAL;

public class SoundController {

    private static final String TAG = SoundController.class.getName();

    private final Context context;

    private final HandlerController handlerController;

    private final Map<Sound, MediaPlayer> mediaPlayerMap;

    public SoundController(Context context, HandlerController handlerController) {
        this.context = context;
        this.handlerController = handlerController;
        this.mediaPlayerMap = new HashMap<>();
    }

    public void stopAll() {
        Log.i(TAG, "stopAll");
        this.mediaPlayerMap
                .keySet()
                .forEach(this::stop);
    }

    public MediaPlayer stop(final Sound sound) {
        Log.i(TAG, "stop (" + sound + ")");
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

    public void play(final Sound sound) {
        play(sound, NORMAL);
    }

    public void play(final Sound sound, final Volume volume) {
        play(sound, volume, null);
    }

    public void play(final Sound sound, final MediaPlayer.OnCompletionListener listener) {
        play(sound, NORMAL, listener);
    }

    public void play(final Sound sound, final Volume volume, final MediaPlayer.OnCompletionListener listener) {
        Log.i(TAG, "play (" + sound + ", " + volume + ")");
        MediaPlayer mediaPlayer = stop(sound);
        mediaPlayer.setVolume(volume.getLeft(), volume.getRight());
        mediaPlayer.setLooping(sound.isLoop());
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(mp -> {
            mp.stop();
            if (listener != null) {
                listener.onCompletion(mp);
            }
        });
    }

    public void play(final Sound sound, long delay) {
        play(sound, NORMAL, delay);
    }

    public void play(final Sound sound, final Volume volume, long delay) {
        play(sound, volume, null, delay);
    }

    public void play(final Sound sound, final MediaPlayer.OnCompletionListener listener, long delay) {
        play(sound, NORMAL, listener, delay);
    }

    public void play(final Sound sound, final Volume volume, final MediaPlayer.OnCompletionListener listener, long delay) {
        Log.i(TAG, "play (" + sound + ", " + volume + ", " + delay + "ms)");
        if (this.handlerController != null) {
            this.handlerController.postDelayed(() -> play(sound, volume, listener), delay);
        }
        else {
            Log.w(TAG, "No HandlerController available, use non-delay");
            play(sound, volume, listener);
        }
    }
}
