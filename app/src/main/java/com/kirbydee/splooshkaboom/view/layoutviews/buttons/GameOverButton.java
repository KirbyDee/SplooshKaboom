package com.kirbydee.splooshkaboom.view.layoutviews.buttons;

import android.animation.Animator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatButton;

import static com.kirbydee.splooshkaboom.utils.Consts.GAME_OVER_ACTIVITY_FADE_IN_BUTTONS_DURATION;

public class GameOverButton extends AppCompatButton {

    private static final String TAG = GameOverButton.class.getName();

    public interface Listener {

        void onAnimationEnd(GameOverButton button);
    }

    private final Handler handler = new Handler();

    private Listener listener;

    public GameOverButton(Context context) {
        super(context);
        init(context);
    }

    public GameOverButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        Log.i(TAG, "init");
        if (context instanceof Listener) {
            this.listener = (Listener) context;
        }
    }

    public void fadeIn() {
        Log.i(TAG, "show");
        animate()
                .alpha(1.0f)
                .setDuration(GAME_OVER_ACTIVITY_FADE_IN_BUTTONS_DURATION)
                .setListener(getAnimatorListener())
                .start();
    }

    private Animator.AnimatorListener getAnimatorListener() {
        Log.i(TAG, "getAnimatorListener");
        return new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // do nothing
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setClickable(true);
                if (GameOverButton.this.listener != null) {
                    GameOverButton.this.listener.onAnimationEnd(GameOverButton.this);
                }
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
}