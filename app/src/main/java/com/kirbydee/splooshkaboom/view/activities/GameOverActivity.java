package com.kirbydee.splooshkaboom.view.activities;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.model.anim.ActivityTransitionAnimation;
import com.kirbydee.splooshkaboom.utils.sound.Sounds;

import static com.kirbydee.splooshkaboom.model.anim.ActivityTransitionAnimation.GAME_OVER;
import static com.kirbydee.splooshkaboom.utils.Consts.GAME_OVER_ACTIVITY_BACKGROUND_SOUND_DELAY;
import static com.kirbydee.splooshkaboom.utils.Consts.GAME_OVER_ACTIVITY_CHANGE_ACTIVITY_DELAY;
import static com.kirbydee.splooshkaboom.utils.Consts.GAME_OVER_ACTIVITY_FADE_IN_BUTTONS_DELAY;
import static com.kirbydee.splooshkaboom.utils.Consts.GAME_OVER_ACTIVITY_FADE_IN_BUTTONS_DURATION;
import static com.kirbydee.splooshkaboom.utils.sound.MonoVolume.MAX;
import static com.kirbydee.splooshkaboom.utils.sound.Sounds.GAME_OVER_BUTTON_CLICK;

public class GameOverActivity extends BaseBackgroundSoundActivity {

    private static final String TAG = GameOverActivity.class.getName();

    private Button continueButton;

    private Button quitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
        runAfterDelay(this::fadeInButtons, GAME_OVER_ACTIVITY_FADE_IN_BUTTONS_DELAY);
    }

    private void fadeInButtons() {
        Log.i(TAG, "fadeInButtons");
        fadeInButton(this.continueButton);
        fadeInButton(this.quitButton);
    }

    private void fadeInButton(Button button) {
        Log.i(TAG, "fadeInButton (" + button + ")");
        button.animate()
                .alpha(1.0f)
                .setDuration(GAME_OVER_ACTIVITY_FADE_IN_BUTTONS_DURATION)
                .setListener(getAnimatorListener(button))
                .start();
    }

    private Animator.AnimatorListener getAnimatorListener(final Button button) {
        Log.i(TAG, "getAnimatorListener (" + button + ")");
        return new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // do nothing
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                button.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // do nothing
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // do nothing
            }
        };
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
        onButtonClick(v, StartActivity.class);
    }

    private <A extends Activity> void onButtonClick(View view, final Class<A> activity) {
        Log.i(TAG, "onButtonClick (" + view + ", " + activity + ")");
        view.setClickable(false);
        this.sound.play(GAME_OVER_BUTTON_CLICK, MAX);
        changeActivityAfterDelay(activity, GAME_OVER_ACTIVITY_CHANGE_ACTIVITY_DELAY);
    }

    @Override
    protected Sounds getBackgroundSound() {
        Log.i(TAG, "getBackgroundSound");
        return Sounds.GAME_OVER;
    }

    @Override
    protected long getBackgroundSoundDelay() {
        Log.i(TAG, "getBackgroundSoundDelay");
        return GAME_OVER_ACTIVITY_BACKGROUND_SOUND_DELAY;
    }
}