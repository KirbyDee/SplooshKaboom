package com.kirbydee.splooshkaboom.view.layoutviews.tile.game;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.view.layoutviews.tile.TileView;

import androidx.annotation.Nullable;

public class GameTileView extends TileView {

    private static final String TAG = GameTileView.class.getName();

    private Listener listener;

    private int rowIndex;

    private int columnIndex;

    public interface Listener {

        void onCreate(GameTileView view);

        void onClick(GameTileView view);
    }

    public GameTileView(Context context) {
        super(context);
    }

    public GameTileView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GameTileView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GameTileView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

        setOnClickListener(this::onClick);
        if (context instanceof Listener) {
            this.listener = (Listener) context;
            this.listener.onCreate(this);
        }
    }

    private void onClick(View v) {
        Log.i(TAG, "onClick: (" + rowIndex + ", " + columnIndex + ")");
        if (this.listener != null) {
            this.listener.onClick(this);
        }
    }

    public void sploosh() {
        setBackgroundResource(R.drawable.sploosh);
    }

    public void kaboom() {
        setBackgroundResource(R.drawable.kaboom);
    }

    @Override
    public void reset() {
        setBackgroundResource(0);
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
