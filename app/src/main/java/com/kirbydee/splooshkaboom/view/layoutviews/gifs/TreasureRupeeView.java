package com.kirbydee.splooshkaboom.view.layoutviews.gifs;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;

import com.kirbydee.splooshkaboom.model.counter.Rupee;
import com.kirbydee.splooshkaboom.view.layoutviews.textbox.TextBoxView;

public class TreasureRupeeView extends GifView {

    private static final String TAG = TreasureRupeeView.class.getName();

    public interface Listener {

        void onCreate(TreasureRupeeView treasureRupeeView);
    }

    public TreasureRupeeView(Context context) {
        super(context);
    }

    public TreasureRupeeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TreasureRupeeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context) {
        Log.i(TAG, "init");
        if (context instanceof TextBoxView.Listener) {
            Listener listener = (Listener) context;
            listener.onCreate(this);
        }
    }

    public void show(Rupee rupee) {
        Log.i(TAG, "show");
        setVisibility(VISIBLE);
        setBackgroundResource(rupee.resId);
        post(() -> {
            AnimationDrawable frameAnimation = (AnimationDrawable) getBackground();
            frameAnimation.start();
        });
    }
}
