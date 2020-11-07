package com.kirbydee.splooshkaboom.model.cellview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public abstract class CellView extends View {

    public CellView(Context context) {
        super(context);
    }

    public CellView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    protected abstract void init(Context context, @Nullable AttributeSet attrs);

    public void enable() {
        setBackgroundResource(getEnableDrawable());
    }

    protected abstract int getEnableDrawable();

    public void disable() {
        setBackgroundResource(getDisableDrawable());
    }

    protected abstract int getDisableDrawable();
}
