package com.kirbydee.splooshkaboom.view.layoutviews.grid.state;

import android.content.Context;
import android.util.AttributeSet;

import com.kirbydee.splooshkaboom.view.layoutviews.grid.GridView;

import androidx.annotation.Nullable;

public abstract class StateGridView extends GridView {

    public StateGridView(Context context) {
        super(context);
    }

    public StateGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StateGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public StateGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
