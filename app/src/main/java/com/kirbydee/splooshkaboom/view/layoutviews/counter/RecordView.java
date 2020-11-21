package com.kirbydee.splooshkaboom.view.layoutviews.counter;

import android.content.Context;
import android.util.AttributeSet;

import com.kirbydee.splooshkaboom.R;

import androidx.annotation.Nullable;

public class RecordView extends CounterAbstractView {

    public interface Listener {

        void onCreate(RecordView view);
    }

    public RecordView(Context context) {
        super(context);
    }

    public RecordView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecordView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RecordView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int getResourceColorId() { // TODO does this work?
        return R.color.black;
    }

    @Override
    protected void init(Context context) {
        if (context instanceof Listener) {
            Listener listener = (Listener) context;
            listener.onCreate(this);
        }
    }

    @Override
    public String toString() {
        return "RecordView{" + super.toString() + "}";
    }
}
