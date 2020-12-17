package com.kirbydee.splooshkaboom.view.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.model.media.Sound;
import com.kirbydee.splooshkaboom.view.layoutviews.TextBox;
import com.kirbydee.splooshkaboom.view.layoutviews.TextBoxNext;

import java.util.concurrent.ThreadLocalRandom;

import static com.kirbydee.splooshkaboom.model.media.Sound.BEEDLE_OHH;
import static com.kirbydee.splooshkaboom.model.media.Sound.SHOP_BACKGROUND;
import static com.kirbydee.splooshkaboom.model.media.Video.SHOP_IDLE;
import static com.kirbydee.splooshkaboom.utils.Consts.SHOP_ACTIVITY_MAX_BEEDLE_SOUND_DELAY;
import static com.kirbydee.splooshkaboom.utils.Consts.SHOP_ACTIVITY_MIN_BEEDLE_SOUND_DELAY;

public class ShopActivity extends MediaBaseActivity {

    private static final String TAG = ShopActivity.class.getName();

    // controller
    //private ShopContract.Presenter shopPresenter;

    // screen view
    private View shopScreen;

    // menu text
    private TextBox shopTextView;
    private TextBoxNext shopTextNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        setContentView(R.layout.shop);
    }

    @Override
    protected void setUpViews() {
        super.setUpViews();
        Log.i(TAG, "setUpViews");

        // views
        this.shopScreen = findViewById(R.id.shopScreen);
        this.shopTextView = findViewById(R.id.shopTextView);
        this.shopTextNext = findViewById(R.id.shopTextNext);

        // view states
        //shopMenuText(false);
        //shopMenu(false);

        // start background video
        play(SHOP_IDLE);
        //this.menuPresenter.onIntro();

        playBeedleOhh(0);
    }

    private void playBeedleOhh(long delay) {
        Log.i(TAG, "playBeedleOhh (" + delay + ")");
        play(BEEDLE_OHH, mp -> playBeedleOhh(
                ThreadLocalRandom
                        .current()
                        .nextLong(SHOP_ACTIVITY_MIN_BEEDLE_SOUND_DELAY, SHOP_ACTIVITY_MAX_BEEDLE_SOUND_DELAY)
        ), delay);
    }

    @Override
    protected int getVideoViewId() {
        Log.i(TAG, "getVideoViewId");
        return R.id.shopVideoView;
    }

    @Override
    protected void setUpListeners() {
        super.setUpListeners();
        Log.i(TAG, "setUpListeners");
    }

    @Override
    protected Sound getBackgroundSound() {
        Log.i(TAG, "getBackgroundSound");
        return SHOP_BACKGROUND;
    }
}