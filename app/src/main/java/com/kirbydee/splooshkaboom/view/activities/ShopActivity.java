package com.kirbydee.splooshkaboom.view.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.controller.media.VideoController;
import com.kirbydee.splooshkaboom.model.media.Sound;
import com.kirbydee.splooshkaboom.model.media.Video;

import static com.kirbydee.splooshkaboom.model.media.Sound.SHOP_BACKGROUND;

public class ShopActivity extends BackgroundSoundBaseActivity {

    private static final String TAG = ShopActivity.class.getName();

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);


        // TODO




        videoView = findViewById(R.id.menuVideoView);
        VideoController videoController = new VideoController(this);
        videoController.play(videoView, Video.MENU_TALK, mp -> {
            System.out.println();
        });
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
    protected Sound getBackgroundSound() {
        Log.i(TAG, "getBackgroundSound");
        return SHOP_BACKGROUND;
    }
}