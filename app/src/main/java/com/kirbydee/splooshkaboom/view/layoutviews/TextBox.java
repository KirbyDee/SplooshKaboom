package com.kirbydee.splooshkaboom.view.layoutviews;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatTextView;

import static com.kirbydee.splooshkaboom.utils.Consts.UI_DELAY;

public class TextBox extends AppCompatTextView {

    private static final String TAG = TextBox.class.getName();

    public interface Listener {

        void onTextFinished(TextBox textBox);
    }

    private Handler handler;

    private Listener listener;

    private boolean isFinished;

    private CharSequence text;

    private int index;

    public TextBox(Context context) {
        super(context);
        init(context);
    }

    public TextBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        Log.i(TAG, "init");
        this.handler = new Handler();
        reset();
        if (context instanceof Listener) {
            this.listener = (Listener) context;
        }
    }

    private final Runnable characterAdder = () -> {
        Log.i(TAG, "characterAdder");
        setText(TextBox.this.text.subSequence(0, TextBox.this.index++));

        if (TextBox.this.index <= TextBox.this.text.length()) {
            TextBox.this.handler.postDelayed(TextBox.this.characterAdder, UI_DELAY);
        }
        else {
            this.isFinished = true;
            if (this.listener != null) {
                this.listener.onTextFinished(this);
            }
        }
    };

    public void reset() {
        this.isFinished = false;
        this.index = 0;
        setText("");
    }

    public void animateText(CharSequence text) {
        Log.i(TAG, "animateText (" + text + ")");

        // reset
        reset();
        this.text = text;

        // animate
        this.handler.removeCallbacks(this.characterAdder);
        this.handler.postDelayed(this.characterAdder, UI_DELAY);
    }

    public void forceFinish() {
        Log.i(TAG, "forceFinish");

        this.handler.removeCallbacks(this.characterAdder);
        setText(this.text);
        finish();
    }

    private void finish() {
        this.isFinished = true;
        if (this.listener != null) {
            this.listener.onTextFinished(this);
        }
    }

    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public String toString() {
        return "TextBox{" +
                "isFinished=" + isFinished +
                ", text=" + text +
                ", index=" + index +
                '}';
    }
}