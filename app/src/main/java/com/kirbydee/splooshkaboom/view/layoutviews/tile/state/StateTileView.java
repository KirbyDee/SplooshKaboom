package com.kirbydee.splooshkaboom.view.layoutviews.tile.state;

import android.content.Context;
import android.util.AttributeSet;

import com.kirbydee.splooshkaboom.view.layoutviews.tile.TileView;

import androidx.annotation.Nullable;

public abstract class StateTileView extends TileView {

    public StateTileView(Context context) {
        super(context);
    }

    public StateTileView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StateTileView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public StateTileView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected abstract int getEnableDrawable();

    protected abstract int getDisableDrawable();

    public void enable() {
        setBackgroundResource(getEnableDrawable());
    }

    public void disable() {
        setBackgroundResource(getDisableDrawable());
    }

    @Override
    public void reset() {
        enable();
    }
}
