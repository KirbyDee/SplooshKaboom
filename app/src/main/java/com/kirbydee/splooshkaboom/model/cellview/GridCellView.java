package com.kirbydee.splooshkaboom.model.cellview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.kirbydee.splooshkaboom.R;

import androidx.annotation.Nullable;

public class GridCellView extends CellView {

    private static final String TAG = GridCellView.class.getName();

    private GridCellViewListener listener;

    private int rowIndex;
    private int columnIndex;

    public interface GridCellViewListener {

        void onCreate(GridCellView view);

        void onClick(GridCellView view);
    }

    public GridCellView(Context context) {
        super(context);
    }

    public GridCellView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GridCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GridCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GridCellView, 0, 0);
        try {
            this.rowIndex = typedArray.getInt(R.styleable.GridCellView_rowIndex, -1);
            this.columnIndex = typedArray.getInt(R.styleable.GridCellView_columnIndex, -1);
        } finally {
            typedArray.recycle();
        }

        setOnTouchListener(this::onTouch);
        if (context instanceof GridCellViewListener) {
            this.listener = (GridCellViewListener) context;
            this.listener.onCreate(this);
        }
    }

    @Override
    protected int getEnableDrawable() {
        return R.drawable.sploosh;
    }

    @Override
    protected int getDisableDrawable() {
        return R.drawable.kaboom;
    }

    private boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG, "onTouch: " + event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "ACTION_DOWN: (" + rowIndex + ", " + columnIndex + ")");
                if (this.listener != null) {
                    this.listener.onClick(this);
                }
                break;
            case MotionEvent.ACTION_UP:
                v.performClick();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GridCellView{" +
                "rowIndex=" + rowIndex +
                ", columnIndex=" + columnIndex +
                '}';
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
