package com.kirbydee.splooshkaboom.view.layoutviews.inventory.display;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;

public class TriforceView extends InventoryDisplayView {

    private static final String TAG = TriforceView.class.getName();

    public interface Listener {

        void onCreate(TriforceView view);
    }

    public TriforceView(Context context) {
        super(context);
    }

    public TriforceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TriforceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context, @Nullable AttributeSet attrs) {
        Log.i(TAG, "init");
        if (context instanceof Listener) {
            Listener listener = (Listener) context;
            listener.onCreate(this);
        }
    }
}
