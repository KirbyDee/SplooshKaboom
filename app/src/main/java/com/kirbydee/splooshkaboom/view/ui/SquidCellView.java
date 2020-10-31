package com.kirbydee.splooshkaboom.view.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.kirbydee.splooshkaboom.R;

import androidx.annotation.Nullable;

public class SquidCellView extends View {

    private static final String TAG = SquidCellView.class.getName();

    private int squidSize;

    public interface SquidCellViewListener {

        void initDone(SquidCellView view);
    }

    public SquidCellView(Context context) {
        super(context);
    }

    public SquidCellView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SquidCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public SquidCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SquidCellView, 0, 0);
        try {
            this.squidSize = typedArray.getInt(R.styleable.SquidCellView_squidSize, -1);
        } finally {
            typedArray.recycle();
        }

        if (context instanceof SquidCellViewListener) {
            SquidCellViewListener listener = (SquidCellViewListener) context;
            listener.initDone(this);
        }
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
