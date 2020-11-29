package com.kirbydee.splooshkaboom.view.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.utils.sound.Sounds;

import static com.kirbydee.splooshkaboom.utils.sound.Sounds.INTRO_BACKGROUND;

public class StartActivity extends BaseBackgroundSoundActivity {

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
                changeActivity(GameActivity.class);
                break;
            case MotionEvent.ACTION_UP:
                v.performClick();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected Sounds getBackgroundSound() {
        Log.i(TAG, "getBackgroundSound");
        return INTRO_BACKGROUND;
    }
}