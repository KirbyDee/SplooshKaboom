package com.kirbydee.splooshkaboom.view.layoutviews.shopitem;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.kirbydee.splooshkaboom.R;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public abstract class ShopItemView extends AppCompatImageView {

    private static final String TAG = ShopItemView.class.getName();

    public interface Listener {

        void onCreate(ShopItemView view);

        void onClick(ShopItemView view);
    }

    private AnimatorSet selectAnimations;

    private AnimatorSet deselectAnimations;

    private Context context;

    private String itemResourceName;

    private Listener listener;

    public ShopItemView(Context context) {
        super(context);
    }

    public ShopItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ShopItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        Log.i(TAG, "init");
        this.context = context;
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ShopItemView, 0, 0);
        try {
            this.itemResourceName = typedArray.getString(R.styleable.ShopItemView_itemResource);
        } finally {
            typedArray.recycle();
        }
        initSelectAnimation();

        showItem();
        startIdleAnimation();
        setOnClickListener(this::onClick);
        if (context instanceof Listener) {
            this.listener = (Listener) context;
            this.listener.onCreate(this);
        }
    }

    private void initSelectAnimation() {
        Log.i(TAG, "initSelectAnimation");

        post(() -> {
            DisplayMetrics metrics = getResources().getDisplayMetrics();

            // view location on screen
            int[] point = new int[2];
            getLocationOnScreen(point);
            int idleLocationX = point[0];
            int idleLocationY = point[1];

            // translation
            ObjectAnimator translationY = ObjectAnimator.ofFloat(this, "translationY", (float) metrics.heightPixels / 3 - idleLocationY);
            ObjectAnimator translationX = ObjectAnimator.ofFloat(this, "translationX", (float) metrics.widthPixels / 2 - idleLocationX);
            ObjectAnimator reverseTranslationY = ObjectAnimator.ofFloat(this, "translationY", 0);
            ObjectAnimator reverseTranslationX = ObjectAnimator.ofFloat(this, "translationX", 0);

            // scale
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1f, 2.5f);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1f, 2.5f);
            ObjectAnimator reverseScaleY = ObjectAnimator.ofFloat(this, "scaleY", 2.5f, 1f);
            ObjectAnimator reverseScaleX = ObjectAnimator.ofFloat(this, "scaleX", 2.5f, 1f);

            // select
            this.selectAnimations = new AnimatorSet();
            this.selectAnimations.playTogether(scaleX, scaleY, translationX, translationY);
            this.selectAnimations.setDuration(1000);
            this.selectAnimations.setInterpolator(new AccelerateDecelerateInterpolator());

            // de-select
            this.deselectAnimations = new AnimatorSet();
            this.deselectAnimations.playTogether(reverseScaleX, reverseScaleY, reverseTranslationX, reverseTranslationY);
            this.deselectAnimations.setDuration(500);
            this.deselectAnimations.setInterpolator(new AccelerateDecelerateInterpolator());
        });
    }

    public void deselectItem() {
        Log.i(TAG, "deselectItem");
        post(() -> this.deselectAnimations.start());
        setClickable(true);
    }

    public void selectItem() {
        Log.i(TAG, "selectItem");
        post(() -> this.selectAnimations.start());
        setClickable(false);
    }

    public void itemSold() {
        Log.i(TAG, "itemSold");
        stopIdleAnimation();
        showSoldSign();
        setClickable(false);
    }

    protected abstract void startIdleAnimation();

    protected abstract void stopIdleAnimation();

    protected int getItemResourceId() {
        return this.context.getResources().getIdentifier(this.itemResourceName, "drawable", context.getPackageName());
    }

    private void showItem() {
        Log.i(TAG, "showItem");
        setBackgroundResource(getItemResourceId());
    }

    private void showSoldSign() {
        Log.i(TAG, "showSoldSign");
        setBackgroundResource(R.drawable.shop_sold);
    }

    private void onClick(View view) {
        Log.i(TAG, "onClick");
        if (this.listener != null && view instanceof ShopItemView) {
            this.listener.onClick((ShopItemView) view);
        }
    }
}
