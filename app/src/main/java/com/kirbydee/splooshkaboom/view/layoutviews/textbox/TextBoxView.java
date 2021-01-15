package com.kirbydee.splooshkaboom.view.layoutviews.textbox;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import static com.kirbydee.splooshkaboom.utils.Consts.TEXT_BOX_FADE_IN_DURATION;

public class TextBoxView extends RelativeLayout {

    private static final String TAG = TextBoxView.class.getName();

    public interface Listener {

        void onCreate(TextBoxView textBoxView);

        void onAppear(TextBoxView textBoxView);
    }

    private Listener listener;

    public TextBoxView(Context context) {
        super(context);
        init(context);
    }

    public TextBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        Log.i(TAG, "init");
        if (context instanceof Listener) {
            this.listener = (Listener) context;
            listener.onCreate(this);
        }
    }

    public void show(boolean show) {
        Log.i(TAG, "show (" + show + ")");
        if (show) {
            show();
        }
        else {
            unShow();
        }
    }

    public void show() {
        Log.i(TAG, "show");
        setVisibility(VISIBLE);
        animate()
                .alpha(1.0f)
                .setDuration(TEXT_BOX_FADE_IN_DURATION)
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
                if (TextBoxView.this.listener != null) {
                    TextBoxView.this.listener.onAppear(TextBoxView.this);
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