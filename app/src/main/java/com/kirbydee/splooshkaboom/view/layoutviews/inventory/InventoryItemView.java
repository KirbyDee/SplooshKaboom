package com.kirbydee.splooshkaboom.view.layoutviews.inventory;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;

import com.kirbydee.splooshkaboom.R;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public abstract class InventoryItemView extends AppCompatImageView {

    private static final String TAG = InventoryItemView.class.getName();

    public interface Listener {

        void onCreate(InventoryItemView view);
    }

    private Context context;

    private String itemResourceName;

    private int itemIndex;

    public InventoryItemView(Context context) {
        super(context);
    }

    public InventoryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public InventoryItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        Log.i(TAG, "init");
        this.context = context;
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ShopItemView, 0, 0);
        try {
            this.itemResourceName = typedArray.getString(R.styleable.ShopItemView_itemResource);
            this.itemIndex = typedArray.getInt(R.styleable.ShopItemView_itemIndex, -1);
        } finally {
            typedArray.recycle();
        }

        setBackgroundResource(getItemResourceId());
        if (context instanceof Listener) {
            Listener listener = (Listener) context;
            listener.onCreate(this);
        }
    }

    protected abstract void startSelectedAnimation();

    protected abstract void stopSelectedAnimation();

    protected int getItemResourceId() {
        return this.context.getResources().getIdentifier(this.itemResourceName, "drawable", this.context.getPackageName());
    }

    public void showItem() {
        showItem(true);
    }

    public void showItem(boolean show) {
        Log.i(TAG, "showItem (" + show + ")");
        setVisibility(show ? VISIBLE : GONE);
        if (show) {
            setVisibility(VISIBLE);
            startSelectedAnimation();
        }
        else {
            stopSelectedAnimation();
            setVisibility(GONE);
        }
    }

    @Override
    public String toString() {
        return "ShopItemView{" +
                "itemResourceName='" + itemResourceName + '\'' +
                ", itemIndex=" + itemIndex +
                '}';
    }

    public int getItemIndex() {
        return itemIndex;
    }
}
