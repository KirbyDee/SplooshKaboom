package com.kirbydee.splooshkaboom.controller.media;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.kirbydee.splooshkaboom.model.media.Video;
import com.kirbydee.splooshkaboom.model.media.Volume;

import static com.kirbydee.splooshkaboom.model.media.MonoVolume.NORMAL;

public class VideoController extends MediaController {

    private static final String TAG = VideoController.class.getName();

    private final Context context;

    public VideoController(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void show(int timeout) {
        // do nothing (remove controlling part)
    }

    public void stop(final VideoView view) {
        Log.i(TAG, "stop (" + view + ")");
        view.stopPlayback();
    }

    public void play(final VideoView view, final Video video) {
        play(view, video, NORMAL);
    }

    public void play(final VideoView view, final Video video, final Volume volume) {
        play(view, video, volume, null);
    }

    public void play(final VideoView view, final Video video, final MediaPlayer.OnCompletionListener listener) {
        play(view, video, NORMAL, listener);
    }

    public void play(final VideoView view, final Video video, final Volume volume, final MediaPlayer.OnCompletionListener listener) {
        Log.i(TAG, "play Video (" + view + ", " + video + ", " + volume + ")");
        // stop the current view (if there is a video)
        stop(view);

        // set anchor of this controller
        setAnchorView(view);
        view.setMediaController(this);

        // load video
        String videoPath = "android.resource://" + this.context.getPackageName() + "/" + video.getRes();
        view.setVideoURI(Uri.parse(videoPath));
        view.requestFocus();
        view.start();

        // add listeners
        view.setOnPreparedListener(mp -> {
            mp.setLooping(video.isLoop());
            mp.setVolume(volume.getLeft(), volume.getRight());
        });
        view.setOnCompletionListener(mp -> {
            view.stopPlayback();
            if (listener != null) {
                listener.onCompletion(mp);
            }
        });
    }
}
