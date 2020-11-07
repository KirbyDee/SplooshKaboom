package com.kirbydee.splooshkaboom.model.cellview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.kirbydee.splooshkaboom.R;

import androidx.annotation.Nullable;

public class SquidCellView extends CellView {

    private int squidSize;

    public interface SquidCellViewListener {

        void onCreate(SquidCellView view);
    }

    public SquidCellView(Context context) {
        super(context);
    }

    public SquidCellView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquidCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SquidCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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


        if (context instanceof SquidCellViewListener) {
            SquidCellViewListener listener = (SquidCellViewListener) context;
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
