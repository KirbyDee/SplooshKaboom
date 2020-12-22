package com.kirbydee.splooshkaboom.view.layoutviews.counter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.kirbydee.splooshkaboom.model.counter.Rupees;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import static com.kirbydee.splooshkaboom.utils.Consts.RUPEE_COUNT_MAX_TIME;
import static com.kirbydee.splooshkaboom.utils.Consts.UI_DELAY;

public class RupeesView extends AppCompatTextView {

    private static final String TAG = RupeesView.class.getName();

    private int desiredRupees;

    private int rupeeAmountChange;

    public interface Listener {

        void onCreate(RupeesView view);
    }

    public RupeesView(Context context) {
        super(context);
        init(context);
    }

    public RupeesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RupeesView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void init(Context context) {
        Log.i(TAG, "init");
        if (context instanceof Listener) {
            Listener listener = (Listener) context;
            listener.onCreate(this);
        }
    }

    @SuppressLint("SetTextI18n")
    private final Runnable rupeeChanger = () -> {
        Log.i(TAG, "rupeeChanger");

        // get current rupees
        int currentRupees = getCurrentRupees();

        // get new rupee count
        int newRupees;
        if (currentRupees == this.desiredRupees) {
            return;
        }
        else if (Math.abs(currentRupees - this.desiredRupees) < this.rupeeAmountChange) {
            newRupees = this.desiredRupees;
        }
        else if (currentRupees < this.desiredRupees) {
            newRupees = currentRupees + this.rupeeAmountChange;
        }
        else {
            newRupees = currentRupees - this.rupeeAmountChange;
        }
        setText("" + newRupees);

        // post delayed, so it looks fluent
        postDelayed(RupeesView.this.rupeeChanger, UI_DELAY);
    };

    private int getCurrentRupees() {
        int currentRupees = this.desiredRupees;
        try {
            currentRupees = Integer.parseInt(getText().toString());
        } catch (NumberFormatException e) {
            Log.w(TAG, "Cannot convert to int: " + getText().toString(), e);
        }
        return currentRupees;
    }

    public void update(Rupees rupees) {
        Log.i(TAG, "update " + rupees + ")");
        this.desiredRupees = rupees.get();
        int currentRupees = getCurrentRupees();
        long rupeeChangeTime = Math.abs(this.desiredRupees - currentRupees) * UI_DELAY;
        this.rupeeAmountChange = rupeeChangeTime > RUPEE_COUNT_MAX_TIME ?
                Math.toIntExact(Math.floorDiv(rupeeChangeTime, RUPEE_COUNT_MAX_TIME) + 1) :
                1;

        // animate
        removeCallbacks(this.rupeeChanger);
        postDelayed(this.rupeeChanger, UI_DELAY);
    }

    @SuppressLint("SetTextI18n")
    public void set(Rupees rupees) {
        setText("" + rupees.get());
    }
}
