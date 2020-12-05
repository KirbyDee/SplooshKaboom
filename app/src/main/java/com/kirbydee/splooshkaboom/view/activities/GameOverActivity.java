package com.kirbydee.splooshkaboom.view.activities;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.model.anim.ActivityTransitionAnimation;
import com.kirbydee.splooshkaboom.model.media.Sound;
import com.kirbydee.splooshkaboom.view.layoutviews.buttons.GameOverButton;

import static com.kirbydee.splooshkaboom.model.anim.ActivityTransitionAnimation.GAME_OVER;
import static com.kirbydee.splooshkaboom.model.media.MonoVolume.MAX;
import static com.kirbydee.splooshkaboom.model.media.Sound.GAME_OVER_BUTTON_CLICK;
import static com.kirbydee.splooshkaboom.utils.Consts.GAME_OVER_ACTIVITY_BACKGROUND_SOUND_DELAY;
import static com.kirbydee.splooshkaboom.utils.Consts.GAME_OVER_ACTIVITY_CHANGE_ACTIVITY_DELAY;

public class GameOverActivity extends BackgroundSoundBaseActivity
        implements GameOverButton.Listener {

    private static final String TAG = GameOverActivity.class.getName();

    private GameOverButton continueButton;

    private GameOverButton quitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
    }

    private void fadeInButtons() {
        Log.i(TAG, "fadeInButtons");
        fadeInButton(this.continueButton);
        fadeInButton(this.quitButton);
    }

    private void fadeInButton(GameOverButton button) {
        Log.i(TAG, "fadeInButton (" + button + ")");
        button.fadeIn();
    }

    @Override
    protected ActivityTransitionAnimation getActivityTransitionAnimation() {
        Log.i(TAG, "getActivityTransitionAnimation");
        return GAME_OVER;
    }

    @Override
    protected void setUpViews() {
        Log.i(TAG, "setUpViews");
        this.continueButton = findViewById(R.id.gameOverContinue);
        this.quitButton = findViewById(R.id.gameOverQuit);
    }

    @Override
    protected void setUpListeners() {
        Log.i(TAG, "setUpListeners");
        this.continueButton.setOnClickListener(this::onContinueClick);
        this.quitButton.setOnClickListener(this::onQuitClick);
    }

    private void onContinueClick(View v) {
        Log.i(TAG, "onContinueClick (" + v + ")");
        onButtonClick(v, GameActivity.class);
    }

    private void onQuitClick(View v) {
        Log.i(TAG, "onQuitClick (" + v + ")");
        onButtonClick(v, MenuActivity.class);
    }

    private <A extends Activity> void onButtonClick(View view, final Class<A> activity) {
        Log.i(TAG, "onButtonClick (" + view + ", " + activity + ")");
        view.setClickable(false);
        this.soundController.play(GAME_OVER_BUTTON_CLICK, MAX);
        changeActivityAfterDelay(activity, GAME_OVER_ACTIVITY_CHANGE_ACTIVITY_DELAY);
    }

    @Override
    protected Sound getBackgroundSound() {
        Log.i(TAG, "getBackgroundSound");
        return Sound.GAME_OVER;
    }

    @Override
    protected long getBackgroundSoundDelay() {
        Log.i(TAG, "getBackgroundSoundDelay");
        return GAME_OVER_ACTIVITY_BACKGROUND_SOUND_DELAY;
    }

    @Override
    protected MediaPlayer.OnCompletionListener getOnCompletionListener() {
        return mp -> fadeInButtons();
    }

    @Override
    public void onAnimationEnd(GameOverButton button) {

    }
}