package com.kirbydee.splooshkaboom.view.layoutviews.inventory.display;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import static com.kirbydee.splooshkaboom.utils.Consts.INVENTORY_DISPLAY_FADE_IN_DURATION;

public class InventoryDisplayBackgroundView extends ConstraintLayout {

    private static final String TAG = TriforceView.class.getName();

    public interface Listener {

        void onCreate(InventoryDisplayBackgroundView view);
    }

    private boolean isShown = false;

    public InventoryDisplayBackgroundView(Context context) {
        super(context);
    }

    public InventoryDisplayBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public InventoryDisplayBackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        Log.i(TAG, "init");
        if (context instanceof Listener) {
            Listener listener = (Listener) context;
            listener.onCreate(this);
        }
    }

    public void show() {
        Log.i(TAG, "show");
        if (this.isShown) {
            return;
        }

        this.isShown = true;
        setAlpha(0.6f);
        animate()
                .alpha(1.0f)
                .setDuration(INVENTORY_DISPLAY_FADE_IN_DURATION)
                .start();
    }

    public void unShow() {
        Log.i(TAG, "unShow");
        if (!this.isShown) {
            return;
        }

        this.isShown = false;
        setAlpha(1.0f);
        animate()
                .alpha(0.6f)
                .setDuration(INVENTORY_DISPLAY_FADE_IN_DURATION)
                .start();
    }
}