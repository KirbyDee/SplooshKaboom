package com.kirbydee.splooshkaboom.view.layoutviews.counter;

import android.content.Context;
import android.util.AttributeSet;

import com.kirbydee.splooshkaboom.R;

import androidx.annotation.Nullable;

public class CounterView extends CounterAbstractView {

    public interface Listener {

        void onCreate(CounterView view);
    }

    public CounterView(Context context) {
        super(context);
    }

    public CounterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int getResourceColorId() {
        return R.color.white;
    }

    @Override
    protected void init(Context context) {
        if (context instanceof CounterView.Listener) {
            Listener listener = (Listener) context;
            listener.onCreate(this);
        }
    }

    @Override
    public String toString() {
        return "CounterView{" + super.toString() + "}";
    }
}
