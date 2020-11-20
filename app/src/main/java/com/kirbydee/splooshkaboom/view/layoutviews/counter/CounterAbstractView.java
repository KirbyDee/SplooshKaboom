package com.kirbydee.splooshkaboom.view.layoutviews.counter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kirbydee.splooshkaboom.model.counter.Counter;

import androidx.annotation.Nullable;

public abstract class CounterAbstractView extends LinearLayout {

    private TextView count;

    public CounterAbstractView(Context context) {
        super(context);
    }

    public CounterAbstractView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CounterAbstractView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CounterAbstractView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {

        this.count = getTextView(context);
        this.addView(this.count);

        init(context);
    }

    private TextView getTextView(Context context) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        );
        ColorStateList colorStateList = context.getResources().getColorStateList(getResourceColorId(), context.getTheme());
        textView.setBackgroundTintList(colorStateList);
        return textView;
    }

    protected abstract int getResourceColorId();

    protected abstract void init(Context context);

    @SuppressLint("SetTextI18n")
    public void update(Counter counter) {
        this.count.setText("" + counter.get());
    }

    @Override
    public String toString() {
        return "CounterAbstractView{" +
                "count=" + count +
                '}';
    }
}
