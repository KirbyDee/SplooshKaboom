package com.kirbydee.splooshkaboom.view.layoutviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class ResetView extends AppCompatImageView {

    private Listener listener;

    public interface Listener {

        void onCreate(ResetView view);

        void onClick(ResetView view);
    }

    public ResetView(Context context) {
        super(context);
    }

    public ResetView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ResetView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        setOnTouchListener(this::onTouch);
        if (context instanceof Listener) {
            this.listener = (Listener) context;
            this.listener.onCreate(this);
        }
    }

    private boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (this.listener != null) {
                    this.listener.onClick(this);
                }
                break;
            case MotionEvent.ACTION_UP:
                v.performClick();
                break;
            default:
                break;
        }
        return true;
    }
}
