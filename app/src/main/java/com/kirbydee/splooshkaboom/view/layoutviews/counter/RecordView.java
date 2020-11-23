package com.kirbydee.splooshkaboom.view.layoutviews.counter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import com.kirbydee.splooshkaboom.model.counter.Counter;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class RecordView extends AppCompatTextView {

    public interface Listener {

        void onCreate(RecordView view);
    }

    public RecordView(Context context) {
        super(context);
        init(context);
    }

    public RecordView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RecordView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        if (counter.get() > 0) {
            setText("" + counter.get());
        }
        else {
            setText("?");
        }
    }
}
