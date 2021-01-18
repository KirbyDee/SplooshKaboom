package com.kirbydee.splooshkaboom.view.layoutviews.inventory;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;

public class GifInventoryItemView extends InventoryItemView {

    private static final String TAG = GifInventoryItemView.class.getName();

    private AnimationDrawable selectedAnimation;

    public GifInventoryItemView(Context context) {
        super(context);
    }

    public GifInventoryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GifInventoryItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context, @Nullable AttributeSet attrs) {
        super.init(context, attrs);
        Log.i(TAG, "init");
        Drawable drawable = getBackground();
        if (drawable instanceof AnimationDrawable) {
            this.selectedAnimation = (AnimationDrawable) drawable;
        }
    }

    @Override
    protected void startSelectedAnimation() {
        Log.i(TAG, "startSelectedAnimation");
        post(() -> this.selectedAnimation.start());
    }

    @Override
    protected void stopSelectedAnimation() {
        Log.i(TAG, "stopSelectedAnimation");
        post(() -> this.selectedAnimation.stop());
    }
}
