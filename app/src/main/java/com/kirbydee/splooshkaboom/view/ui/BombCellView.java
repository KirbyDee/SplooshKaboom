package com.kirbydee.splooshkaboom.view.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.kirbydee.splooshkaboom.R;

import androidx.annotation.Nullable;

public class BombCellView extends View {

    private static final String TAG = BombCellView.class.getName();

    private int bombIndex;

    public interface BombCellViewListener {

        void initDone(BombCellView view);
    }

    public BombCellView(Context context) {
        super(context);
    }

    public BombCellView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BombCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public BombCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BombCellView, 0, 0);
        try {
            this.bombIndex = typedArray.getInt(R.styleable.BombCellView_bombIndex, -1);
        } finally {
            typedArray.recycle();
        }

        if (context instanceof BombCellViewListener) {
            BombCellViewListener listener = (BombCellViewListener) context;
            listener.initDone(this);
        }
    }

    @Override
    public String toString() {
        return "BombCellView{" +
                "bombIndex=" + bombIndex +
                '}';
    }

    public int getBombIndex() {
        return bombIndex;
    }

    public void setBombIndex(int bombIndex) {
        this.bombIndex = bombIndex;
    }
}
