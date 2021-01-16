package com.kirbydee.splooshkaboom.view.layoutviews.inventory;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.Nullable;

public class StaticInventoryItemView extends InventoryItemView {

    private static final String TAG = StaticInventoryItemView.class.getName();

    private ObjectAnimator selectedAnimation;

    public StaticInventoryItemView(Context context) {
        super(context);
    }

    public StaticInventoryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StaticInventoryItemView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        this.selectedAnimation = ObjectAnimator.ofFloat(this, "translationY", -10);
        this.selectedAnimation.setDuration(2000);
        this.selectedAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        this.selectedAnimation.setRepeatCount(ObjectAnimator.INFINITE);
        this.selectedAnimation.setRepeatMode(ObjectAnimator.REVERSE);
    }

    @Override
    protected void startSelectedAnimation() {
        Log.i(TAG, "startSelectedAnimation");
        post(() -> this.selectedAnimation.start());
    }

    @Override
    protected void stopSelectedAnimation() {
        Log.i(TAG, "stopSelectedAnimation");
        post(() -> this.selectedAnimation.cancel());
    }
}
