package com.kirbydee.splooshkaboom.view.layoutviews.inventory;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.kirbydee.splooshkaboom.model.inventory.InventoryItem;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class InventoryDisplayView extends ConstraintLayout {

    private static final String TAG = InventoryDisplayView.class.getName();

    public interface Listener {

        void onCreate(InventoryDisplayView view);
    }

    private Context context;

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

    protected void init(Context context, @Nullable AttributeSet attrs) {
        Log.i(TAG, "init");
        this.context = context;
        if (context instanceof Listener) {
            Listener listener = (Listener) context;
            listener.onCreate(this);
        }
    }

    public void setInventoryItem(InventoryItem item) {
        Log.i(TAG, "setInventoryItem");
        setBackgroundResource(this.context.getResources().getIdentifier(item.res, "drawable", this.context.getPackageName()));
    }

    public void resetInventoryItem() {
        Log.i(TAG, "resetInventoryItem");
        setBackgroundResource(0);
    }
}
