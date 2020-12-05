package com.kirbydee.splooshkaboom.view.layoutviews;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatImageView;

import static com.kirbydee.splooshkaboom.utils.Consts.TEXT_BOX_NEXT_FADE_IN_DURATION;

public class TextBoxNext extends AppCompatImageView {

    private static final String TAG = TextBoxNext.class.getName();

    public interface Listener {

        void onAnimationFinished(TextBoxNext view);
    }

    private Listener listener;

    public TextBoxNext(Context context) {
        super(context);
        init(context);
    }

    public TextBoxNext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        Log.i(TAG, "init");
        unShow();

        if (context instanceof Listener) {
            this.listener = (Listener) context;
        }
    }

    public void show() {
        Log.i(TAG, "show");
        setVisibility(VISIBLE);
        animate()
                .alpha(1.0f)
                .setDuration(TEXT_BOX_NEXT_FADE_IN_DURATION)
                .setListener(getAnimatorListener())
                .start();
    }

    public void unShow() {
        Log.i(TAG, "unShow");
        setAlpha(0.0f);
        setVisibility(GONE);
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
                if (TextBoxNext.this.listener != null) {
                    listener.onAnimationFinished(TextBoxNext.this);
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