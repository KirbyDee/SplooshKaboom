package com.kirbydee.splooshkaboom.view.layoutviews.inventory.display;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import static com.kirbydee.splooshkaboom.utils.Consts.INVENTORY_DISPLAY_FADE_IN_DURATION;

public abstract class InventoryDisplayView extends AppCompatTextView {

    private static final String TAG = InventoryDisplayView.class.getName();

    public InventoryDisplayView(Context context) {
        super(context);
    }

    public InventoryDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public InventoryDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    protected abstract void init(Context context, @Nullable AttributeSet attrs);

    public void show(boolean show) {
        if (show) {
            show();
        }
        else {
            unShow();
        }
    }

    private void show() {
        Log.i(TAG, "show");
        setVisibility(VISIBLE);
        animate()
                .alpha(1.0f)
                .setDuration(INVENTORY_DISPLAY_FADE_IN_DURATION)
                .start();
    }

    public void unShow() {
        Log.i(TAG, "unShow");
        setAlpha(0.0f);
        setVisibility(GONE);
    }
}
