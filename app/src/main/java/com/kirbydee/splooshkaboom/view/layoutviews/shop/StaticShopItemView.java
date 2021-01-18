package com.kirbydee.splooshkaboom.view.layoutviews.shop;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.Nullable;

public class StaticShopItemView extends ShopItemView {

    private static final String TAG = StaticShopItemView.class.getName();

    private ObjectAnimator idleAnimation;

    public StaticShopItemView(Context context) {
        super(context);
    }

    public StaticShopItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StaticShopItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context, @Nullable AttributeSet attrs) {
        super.init(context, attrs);
        Log.i(TAG, "init");
        initIdleAnimation();
    }

    private void initIdleAnimation() {
        Log.i(TAG, "initIdleAnimation");
        this.idleAnimation = ObjectAnimator.ofFloat(this, "translationY", -10);
        this.idleAnimation.setDuration(2000);
        this.idleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        this.idleAnimation.setRepeatCount(ObjectAnimator.INFINITE);
        this.idleAnimation.setRepeatMode(ObjectAnimator.REVERSE);
    }

    @Override
    protected void postStartIdleAnimation() {
        Log.i(TAG, "postStartIdleAnimation");
        this.idleAnimation.start();
    }

    @Override
    protected void postStopIdleAnimation() {
        Log.i(TAG, "postStopIdleAnimation");
        this.idleAnimation.cancel();
    }

    @Override
    public void selectItem() {
        Log.i(TAG, "selectItem");
        stopIdleAnimation();
        super.selectItem();
        startIdleAnimation();
    }

    @Override
    public void deselectItem() {
        Log.i(TAG, "deselectItem");
        stopIdleAnimation();
        super.deselectItem();
        startIdleAnimation();
    }
}
