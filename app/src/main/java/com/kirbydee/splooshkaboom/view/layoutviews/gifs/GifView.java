package com.kirbydee.splooshkaboom.view.layoutviews.gifs;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class GifView extends AppCompatImageView {

    public GifView(Context context) {
        super(context);
        init(context);
    }

    public GifView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GifView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        post(() -> {
            AnimationDrawable frameAnimation = (AnimationDrawable) getBackground();
            frameAnimation.start();
        });
    }
}
