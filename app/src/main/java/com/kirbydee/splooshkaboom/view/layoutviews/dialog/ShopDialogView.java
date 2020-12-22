package com.kirbydee.splooshkaboom.view.layoutviews.dialog;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import static com.kirbydee.splooshkaboom.utils.Consts.SHOP_DIALOG_VIEW_FADE_DURATION;

public class ShopDialogView extends RelativeLayout {

    private static final String TAG = ShopDialogView.class.getName();

    public interface Listener {

        void onCreate(ShopDialogView view);
    }

    public ShopDialogView(Context context) {
        super(context);
    }

    public ShopDialogView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ShopDialogView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public ShopDialogView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        Log.i(TAG, "init");
        if (context instanceof Listener) {
            Listener listener = (Listener) context;
            listener.onCreate(this);
        }
    }

    public void fadeIn() {
        Log.i(TAG, "fadeIn");
        animate()
                .alpha(1.0f)
                .setDuration(SHOP_DIALOG_VIEW_FADE_DURATION)
                .start();
    }

    public void fadeOut() {
        Log.i(TAG, "fadeOut");
        animate()
                .alpha(0.0f)
                .setDuration(SHOP_DIALOG_VIEW_FADE_DURATION)
                .start();
    }
}
