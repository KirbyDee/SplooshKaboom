package com.kirbydee.splooshkaboom.view.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.kirbydee.splooshkaboom.R;

import java.util.Random;

import androidx.annotation.Nullable;

public class GridCellView extends View {

    private static final String TAG = GridCellView.class.getName();

    private int rowIndex;
    private int columnIndex;

    public GridCellView(Context context) {
        super(context);
    }

    public GridCellView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        populateIndexes(context, attrs);
    }

    public GridCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        populateIndexes(context, attrs);
    }

    public GridCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        populateIndexes(context, attrs);
    }

    int[] colors = {Color.GREEN, Color.BLUE, Color.RED, Color.WHITE, Color.YELLOW, Color.BLACK};
    Random rnd = new Random();

    private void populateIndexes(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GridCellView, 0, 0);
        try {
            this.rowIndex = typedArray.getInt(R.styleable.GridCellView_rowIndex, -1);
            this.columnIndex = typedArray.getInt(R.styleable.GridCellView_columnIndex, -1);
        } finally {
            typedArray.recycle();
        }

        setBackgroundColor(colors[rnd.nextInt(6)]);

        setOnTouchListener(this::onTouch);
    }

    private boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG, "onTouch: " + event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "(" + rowIndex + ", " + columnIndex + ")");
                break;
            case MotionEvent.ACTION_UP:
                v.performClick();
                break;
            default:
                break;
        }
        return true;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }
}
