package com.kirbydee.splooshkaboom.view.layoutviews.shop;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

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

    // TODO: that's ugly ..
    // TODO: also check inventory
    @Override
    protected void startIdleAnimation() {
        Log.i(TAG, "startIdleAnimation");
        if (this.idleAnimation == null) {
            Drawable drawable = getBackground();
            if (drawable instanceof AnimationDrawable) {
                this.idleAnimation = (AnimationDrawable) drawable;
            }
        }
        if (this.idleAnimation != null) {
            post(() -> this.idleAnimation.start());
        }
    }

    @Override
    protected void stopIdleAnimation() {
        Log.i(TAG, "stopIdleAnimation");
        if (this.idleAnimation != null) {
            post(() -> this.idleAnimation.stop());
        }
    }
}
