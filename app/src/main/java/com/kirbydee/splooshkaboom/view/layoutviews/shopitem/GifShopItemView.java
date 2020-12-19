package com.kirbydee.splooshkaboom.view.layoutviews.shopitem;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
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

        this.idleAnimation = (AnimationDrawable) getBackground();
    }

    @Override
    protected void startIdleAnimation() {
        post(() -> this.idleAnimation.start());
    }

    @Override
    protected void stopIdleAnimation() {
        post(() -> this.idleAnimation.stop());
    }
}
