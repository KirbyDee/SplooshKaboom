package com.kirbydee.splooshkaboom.view.layoutviews.counter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import com.kirbydee.splooshkaboom.model.counter.Counter;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class CounterView extends AppCompatTextView {

    public interface Listener {

        void onCreate(CounterView view);
    }

    public CounterView(Context context) {
        super(context);
        init(context);
    }

    public CounterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void init(Context context) {
        if (context instanceof Listener) {
            Listener listener = (Listener) context;
            listener.onCreate(this);
        }
    }

    @SuppressLint("SetTextI18n")
    public void update(Counter counter) {
        setText("" + counter.get());
    }
}
