package com.kirbydee.splooshkaboom.view.activities;

import android.os.Bundle;
import android.util.Log;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.contract.ShopContract;
import com.kirbydee.splooshkaboom.model.media.Sound;
import com.kirbydee.splooshkaboom.presenter.ShopPresenter;

import java.util.concurrent.ThreadLocalRandom;

import static com.kirbydee.splooshkaboom.model.media.Sound.BEEDLE_OHH;
import static com.kirbydee.splooshkaboom.model.media.Sound.SHOP_BACKGROUND;
import static com.kirbydee.splooshkaboom.model.media.Video.SHOP_IDLE;
import static com.kirbydee.splooshkaboom.utils.Consts.SHOP_ACTIVITY_MAX_BEEDLE_SOUND_DELAY;
import static com.kirbydee.splooshkaboom.utils.Consts.SHOP_ACTIVITY_MIN_BEEDLE_SOUND_DELAY;

public class ShopActivity extends TextBaseActivity<ShopContract.Presenter> implements ShopContract.View {

    private static final String TAG = ShopActivity.class.getName();

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

        // start background video
        play(SHOP_IDLE);
        this.presenter.onStart();
        playBeedleOhh(0);
    }

    @Override
    protected int getScreenViewId() {
        Log.i(TAG, "getScreenViewId");
        return R.id.shopScreen;
    }

    @Override
    protected int getTextBoxId() {
        Log.i(TAG, "getTextBoxId");
        return R.id.shopTextView;
    }

    @Override
    protected int getTextBoxNextId() {
        Log.i(TAG, "getTextBoxNextId");
        return R.id.shopTextNext;
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
    protected ShopContract.Presenter getPresenter() {
        return new ShopPresenter(this);
    }

    @Override
    protected Sound getBackgroundSound() {
        Log.i(TAG, "getBackgroundSound");
        return SHOP_BACKGROUND;
    }
}