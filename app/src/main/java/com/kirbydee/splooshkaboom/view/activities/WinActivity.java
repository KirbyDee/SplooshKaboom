package com.kirbydee.splooshkaboom.view.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.contract.WinContract;
import com.kirbydee.splooshkaboom.model.anim.ActivityTransitionAnimation;
import com.kirbydee.splooshkaboom.model.media.Sound;
import com.kirbydee.splooshkaboom.model.media.Video;
import com.kirbydee.splooshkaboom.presenter.WinPresenter;

import static com.kirbydee.splooshkaboom.model.anim.ActivityTransitionAnimation.LONG_FADE;
import static com.kirbydee.splooshkaboom.model.media.Sound.HURRAY;
import static com.kirbydee.splooshkaboom.utils.Consts.WIN_ACTIVITY_BACKGROUND_SOUND_DELAY;

public class WinActivity extends TextBaseActivity<WinContract.Presenter> implements WinContract.View {

    private static final String TAG = WinActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        setContentView(R.layout.win);
    }

    @Override
    protected void setUpViews() {
        super.setUpViews();
        Log.i(TAG, "setUpViews");
    }

    @Override
    protected void onBackgroundSoundFinished(MediaPlayer mp) {
        play(Video.CHEST_OPEN);
        play(Sound.CHEST_OPEN);
    }

    @Override
    protected int getScreenViewId() {
        Log.i(TAG, "getScreenViewId");
        return R.id.winScreen;
    }

    @Override
    protected int getTextBoxId() {
        Log.i(TAG, "getTextBoxId");
        return R.id.winTextView;
    }

    @Override
    protected int getTextBoxNextId() {
        Log.i(TAG, "getTextBoxNextId");
        return R.id.winTextNext;
    }

    @Override
    protected int getVideoViewId() {
        Log.i(TAG, "getVideoViewId");
        return R.id.winVideoView;
    }

    @Override
    protected void setUpListeners() {
        super.setUpListeners();
        Log.i(TAG, "setUpListeners");
    }

    @Override
    protected WinContract.Presenter getPresenter() {
        Log.i(TAG, "getPresenter");
        return new WinPresenter(this, getStorage());
    }

    @Override
    protected Sound getBackgroundSound() {
        Log.i(TAG, "getBackgroundSound");
        return HURRAY;
    }

    @Override
    protected long getBackgroundSoundDelay() {
        return WIN_ACTIVITY_BACKGROUND_SOUND_DELAY;
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed");
        this.presenter.onBackPressed();
    }

    @Override
    protected ActivityTransitionAnimation getActivityTransitionAnimation() {
        Log.i(TAG, "getActivityTransitionAnimation");
        return LONG_FADE;
    }

    @Override
    public void backToMenu() {
        Log.i(TAG, "backToMenu");
        changeActivity(MenuActivity.class);
    }
}