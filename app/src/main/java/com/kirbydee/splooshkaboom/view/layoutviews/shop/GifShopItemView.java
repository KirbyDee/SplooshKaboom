package com.kirbydee.splooshkaboom.view.layoutviews.shop;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;

public class GifShopItemView extends ShopItemView {

    private static final String TAG = GifShopItemView.class.getName();

    private AnimationDrawable idleAnimation;

    public GifShopItemView(Context context) {
        super(context);
    }

    public GifShopItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GifShopItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context, @Nullable AttributeSet attrs) {
        super.init(context, attrs);
        Log.i(TAG, "init");

        if (this.idleAnimation == null) {
            Drawable drawable = getBackground();
            if (drawable instanceof AnimationDrawable) {
                this.idleAnimation = (AnimationDrawable) drawable;
            }
        }
    }

    @Override
    protected void postStartIdleAnimation() {
        Log.i(TAG, "postStartIdleAnimation");
        if (this.idleAnimation != null) {
            this.idleAnimation.start();
        }
    }

    @Override
    protected void postStopIdleAnimation() {
        Log.i(TAG, "postStopIdleAnimation");
        if (this.idleAnimation != null) {
            this.idleAnimation.stop();
        }
    }
}
