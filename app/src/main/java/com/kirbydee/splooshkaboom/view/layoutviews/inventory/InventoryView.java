package com.kirbydee.splooshkaboom.view.layoutviews.inventory;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.kirbydee.splooshkaboom.R;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class InventoryView extends ConstraintLayout {

    private static final String TAG = InventoryView.class.getName();

    public interface Listener {

        void onCreate(InventoryView view);

        void onSelected(InventoryView view);

        void onDeselected(InventoryView view);
    }

    private Listener listener;

    private int itemIndex;

    public InventoryView(Context context) {
        super(context);
    }

    public InventoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public InventoryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        Log.i(TAG, "init");

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ShopItemView, 0, 0);
        try {
            this.itemIndex = typedArray.getInt(R.styleable.ShopItemView_itemIndex, -1);
        } finally {
            typedArray.recycle();
        }

        setOnClickListener(this::onClick);
        if (context instanceof Listener) {
            this.listener = (Listener) context;
            this.listener.onCreate(this);
        }
    }

    private void onClick(View view) {
        Log.i(TAG, "onClick");
        select();
    }

    public void select() {
        Log.i(TAG, "select");
        setBackgroundResource(R.drawable.inventory_item_selected);
        if (this.listener != null) {
            this.listener.onSelected(this);
        }
    }

    public void deselect() {
        Log.i(TAG, "deselect");
        setBackgroundResource(R.drawable.inventory_item_deselected);
        if (this.listener != null) {
            this.listener.onDeselected(this);
        }
    }

    @Override
    public String toString() {
        return "InventoryView{" +
                "itemIndex=" + itemIndex +
                '}';
    }

    public int getItemIndex() {
        return itemIndex;
    }
}
