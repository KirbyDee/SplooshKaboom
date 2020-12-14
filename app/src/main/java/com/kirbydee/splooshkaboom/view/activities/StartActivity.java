package com.kirbydee.splooshkaboom.view.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.model.media.Sound;

import static com.kirbydee.splooshkaboom.model.media.Sound.INTRO_BACKGROUND;

public class StartActivity extends BackgroundSoundBaseActivity {

    private static final String TAG = StartActivity.class.getName();

    private View screenView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
    }

    @Override
    protected void setUpViews() {
        Log.i(TAG, "setUpViews");
        this.screenView = findViewById(R.id.screenView);
    }

    @Override
    protected void setUpListeners() {
        Log.i(TAG, "setUpListeners");
        this.screenView.setOnTouchListener(this::onTouch);
    }

    private boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG, "onTouch: " + event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onScreenTouch();
                break;
            case MotionEvent.ACTION_UP:
                v.performClick();
                break;
            default:
                break;
        }
        return true;
    }

    private void onScreenTouch() {
        this.screenView.setClickable(false);
        this.soundController.play(Sound.INTRO_START, mp -> changeActivity(MenuActivity.class));
    }

    @Override
    protected Sound getBackgroundSound() {
        Log.i(TAG, "getBackgroundSound");
        return INTRO_BACKGROUND;
    }
}