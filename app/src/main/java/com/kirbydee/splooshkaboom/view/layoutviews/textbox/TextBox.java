package com.kirbydee.splooshkaboom.view.layoutviews.textbox;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatTextView;

import static com.kirbydee.splooshkaboom.view.layoutviews.textbox.TextSpeed.DEFAULT;

public class TextBox extends AppCompatTextView {

    private static final String TAG = TextBox.class.getName();

    public interface Listener {

        void onTextFinished(TextBox textBox);
    }

    private TextSpeed textSpeed = DEFAULT;

    private TextFormatter formatter;

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
        reset();
        if (context instanceof Listener) {
            this.listener = (Listener) context;
        }
    }

    private final Runnable characterAdder = () -> {
        Log.i(TAG, "characterAdder");
        setText(TextBox.this.text.subSequence(0, TextBox.this.index++));

        if (TextBox.this.index <= TextBox.this.text.length()) {
            postDelayed(TextBox.this.characterAdder, this.textSpeed.time);
        }
        else {
            this.isFinished = true;
            if (this.listener != null) {
                this.listener.onTextFinished(this);
            }
        }
    };

    public void reset() {
        Log.i(TAG, "reset");
        this.isFinished = false;
        this.index = 0;
        setText("");
    }

    public void animateText(CharSequence text) {
        Log.i(TAG, "animateText (" + text + ")");

        // reset
        reset();
        this.text = format(text);

        // animate
        removeCallbacks(this.characterAdder);
        post(this.characterAdder);
    }

    private CharSequence format(CharSequence text) {
        Log.i(TAG, "format (" + text + ")");
        if (this.formatter != null) {
            return this.formatter.format(text.toString());
        }
        return text;
    }

    public void forceFinish() {
        Log.i(TAG, "forceFinish");

        removeCallbacks(this.characterAdder);
        setText(this.text);
        finish();
    }

    private void finish() {
        Log.i(TAG, "finish");
        this.isFinished = true;
        if (this.listener != null) {
            this.listener.onTextFinished(this);
        }
    }

    public boolean isFinished() {
        return isFinished;
    }

    public TextFormatter getFormatter() {
        return formatter;
    }

    public void setFormatter(TextFormatter formatter) {
        this.formatter = formatter;
    }

    public TextSpeed getTextSpeed() {
        return textSpeed;
    }

    public void setTextSpeed(TextSpeed textSpeed) {
        this.textSpeed = textSpeed;
    }

    @Override
    public String toString() {
        return "TextBox{" +
                "textSpeed=" + textSpeed +
                ", isFinished=" + isFinished +
                ", text=" + text +
                ", index=" + index +
                '}';
    }
}