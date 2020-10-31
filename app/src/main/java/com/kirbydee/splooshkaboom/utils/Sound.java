package com.kirbydee.splooshkaboom.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.kirbydee.splooshkaboom.view.activities.GameActivity;

import java.util.HashMap;
import java.util.Map;

public class Sound {

    private static final String TAG = GameActivity.class.getName();

    private static final Map<Sounds, MediaPlayer> mediaPlayerMap = new HashMap<>();

    public static void stopSound(Context context, Sounds sound) {
        Log.i(TAG, "stopSound");
        MediaPlayer mediaPlayer = mediaPlayerMap.get(sound);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(context, sound.getRes());
        mediaPlayerMap.put(sound, mediaPlayer);
    }

    public static void playSound(Context context, Sounds sound) {
        Log.i(TAG, "play Sound: " + sound);
        stopSound(context, sound);
        mediaPlayerMap.get(sound).start();
    }
}
