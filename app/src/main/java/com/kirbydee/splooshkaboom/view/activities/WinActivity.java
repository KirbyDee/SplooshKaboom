package com.kirbydee.splooshkaboom.view.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.contract.WinContract;
import com.kirbydee.splooshkaboom.model.anim.ActivityTransitionAnimation;
import com.kirbydee.splooshkaboom.model.counter.Counter;
import com.kirbydee.splooshkaboom.model.counter.Rupee;
import com.kirbydee.splooshkaboom.model.media.Sound;
import com.kirbydee.splooshkaboom.model.media.Video;
import com.kirbydee.splooshkaboom.presenter.WinPresenter;
import com.kirbydee.splooshkaboom.view.layoutviews.gifs.TreasureRupeeView;
import com.kirbydee.splooshkaboom.view.layoutviews.textbox.WinTextFormatter;

import static com.kirbydee.splooshkaboom.model.anim.ActivityTransitionAnimation.LONG_FADE;
import static com.kirbydee.splooshkaboom.model.media.Sound.HURRAY;
import static com.kirbydee.splooshkaboom.utils.Consts.WIN_ACTIVITY_BACKGROUND_SOUND_DELAY;

public class WinActivity extends TextBaseActivity<WinContract.Presenter> implements WinContract.View, TreasureRupeeView.Listener {

    private static final String TAG = WinActivity.class.getName();

    private TreasureRupeeView treasureRupeeView;

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
        this.presenter.onIntro();

        // set text box configs
        setTextBoxFormatter(new WinTextFormatter(this.presenter));
    }

    @Override
    protected void onBackgroundSoundFinished(MediaPlayer mp) {
        play(Video.CHEST_OPEN, nothing -> this.presenter.onChestOpenVideoFinished());
        play(Sound.CHEST_OPEN, nothing -> this.presenter.onChestOpenSoundFinished());
    }

    @Override
    protected int getScreenViewId() {
        Log.i(TAG, "getScreenViewId");
        return R.id.winScreen;
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

        // get win counter
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Counter counter = (Counter) bundle.getSerializable("counter");
        return new WinPresenter(this, getStorage(), counter);
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
    public void showRupee(Rupee rupee) {
        Log.i(TAG, "showRupee (" + rupee + ")");
        this.treasureRupeeView.show(rupee);
    }

    @Override
    public void backToMenu() {
        Log.i(TAG, "backToMenu");
        changeActivity(MenuActivity.class);
    }

    @Override
    public void onCreate(TreasureRupeeView treasureRupeeView) {
        Log.i(TAG, "onCreate (" + treasureRupeeView + ")");
        this.treasureRupeeView = treasureRupeeView;
    }
}