package com.kirbydee.splooshkaboom.view.layoutviews.tile.state;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.kirbydee.splooshkaboom.R;

import androidx.annotation.Nullable;

public class BombView extends StateTileView {

    private int bombIndex;

    public interface Listener {

        void onCreate(BombView view);
    }

    public BombView(Context context) {
        super(context);
    }

    public BombView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BombView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BombView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BombCellView, 0, 0);
        try {
            this.bombIndex = typedArray.getInt(R.styleable.BombCellView_bombIndex, -1);
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
        return R.drawable.bomb_active;
    }
    @Override
    protected int getDisableDrawable() {
        return R.drawable.bomb_nonactive;
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
