package com.kirbydee.splooshkaboom.view.layoutviews.grid.state;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.kirbydee.splooshkaboom.R;

import androidx.annotation.Nullable;

public class SquidStateGridView extends StateGridView {

    private int squidSize;

    public interface Listener {

        void onCreate(SquidStateGridView view);
    }

    public SquidStateGridView(Context context) {
        super(context);
    }

    public SquidStateGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquidStateGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SquidStateGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SquidCellView, 0, 0);
        try {
            this.squidSize = typedArray.getInt(R.styleable.SquidCellView_squidSize, -1);
        } finally {
            typedArray.recycle();
        }

        if (context instanceof Listener) {
            Listener listener = (Listener) context;
            listener.onCreate(this);
        }
    }

    @Override
    protected int getEnableDrawable() {
        return R.drawable.squid_active;
    }

    @Override
    protected int getDisableDrawable() {
        return R.drawable.squid_nonactive;
    }

    @Override
    public String toString() {
        return "SquidCellView{" +
                "squidSize=" + squidSize +
                '}';
    }

    public int getSquidSize() {
        return squidSize;
    }

    public void setSquidSize(int squidSize) {
        this.squidSize = squidSize;
    }
}
