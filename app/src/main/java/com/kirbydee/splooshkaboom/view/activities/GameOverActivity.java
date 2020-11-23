package com.kirbydee.splooshkaboom.view.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.kirbydee.splooshkaboom.R;

import static com.kirbydee.splooshkaboom.utils.Consts.GAME_OVER_ACTIVITY_BACKGROUND_SOUND_DELAY;

public class GameOverActivity extends BaseBackgroundSoundActivity {

    private static final String TAG = GameOverActivity.class.getName();

    private View screenView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
    }

    @Override
    protected void setUpViews() {
        Log.i(TAG, "setUpViews");
        this.screenView = findViewById(R.id.gameOverView);
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
    protected int getBackgroundSoundId() {
        Log.i(TAG, "getBackgroundSoundId");
        return R.raw.game_over;
    }

    @Override
    protected long getBackgroundSoundDelay() {
        Log.i(TAG, "getBackgroundSoundDelay");
        return GAME_OVER_ACTIVITY_BACKGROUND_SOUND_DELAY;
    }

    @Override
    protected boolean loopMusic() {
        Log.i(TAG, "loopMusic");
        return false;
    }
}