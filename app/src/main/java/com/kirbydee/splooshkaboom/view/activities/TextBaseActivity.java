package com.kirbydee.splooshkaboom.view.activities;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import com.kirbydee.splooshkaboom.contract.TextContract;
import com.kirbydee.splooshkaboom.model.anim.ActivityTransitionAnimation;
import com.kirbydee.splooshkaboom.model.counter.Rupees;
import com.kirbydee.splooshkaboom.model.media.Video;
import com.kirbydee.splooshkaboom.utils.Storage;
import com.kirbydee.splooshkaboom.view.layoutviews.TextBox;
import com.kirbydee.splooshkaboom.view.layoutviews.TextBoxNext;
import com.kirbydee.splooshkaboom.view.layoutviews.counter.RupeesView;

import static com.kirbydee.splooshkaboom.model.anim.ActivityTransitionAnimation.NORMAL_FADE;
import static com.kirbydee.splooshkaboom.model.media.Sound.TEXT_BUTTON_SOUND;

public abstract class TextBaseActivity<P extends TextContract.Presenter> extends MediaBaseActivity
        implements TextBoxNext.Listener, TextBox.Listener, RupeesView.Listener,
        TextContract.View {

    private static final String TAG = TextBaseActivity.class.getName();

    // presenter
    protected P presenter;

    // screen view
    private View screenView;

    // menu text
    private TextBox textBox;
    private TextBoxNext textBoxNext;

    @Override
    protected void setUpViews() {
        super.setUpViews();
        Log.i(TAG, "setUpViews");

        // views
        this.screenView = findViewById(getScreenViewId());
        this.textBox = findViewById(getTextBoxId());
        this.textBoxNext = findViewById(getTextBoxNextId());

        // view states
        showTextBox(false);
    }

    protected abstract int getScreenViewId();

    protected abstract int getTextBoxId();

    protected abstract int getTextBoxNextId();

    @Override
    @SuppressLint("ClickableViewAccessibility")
    protected void setUpListeners() {
        Log.i(TAG, "setUpListeners");
        super.setUpListeners();

        this.screenView.setOnClickListener(this::onClickScreen);
        enableScreenClick(true);
    }

    @Override
    public void onTextFinished(TextBox textBox) {
        Log.i(TAG, "onTextFinished: " + textBox);
        this.textBoxNext.show();
    }

    protected void onClickScreen(View v) {
        Log.i(TAG, "onClickScreen");
        this.presenter.onClickScreen();
    }

    @Override
    public void onAnimationFinished(TextBoxNext textBoxNext) {
        Log.i(TAG, "onAnimationFinished: " + textBoxNext);
        // do nothing
    }

    @Override
    public void showTextBox(boolean show) {
        Log.i(TAG, "showTextBox: " + show);
        int visibility = show ? View.VISIBLE : View.GONE;
        this.textBox.setVisibility(visibility);
    }

    @Override
    public boolean isTextBoxFinished() {
        Log.i(TAG, "isTextBoxFinished");
        return this.textBox.isFinished();
    }

    @Override
    public void playVideo(Video video) {
        Log.i(TAG, "playVideo (" + video + ")");
        play(video);
    }

    @Override
    public void removeTextBoxNext() {
        Log.i(TAG, "removeTextBoxNext");
        this.textBoxNext.unShow();
        play(TEXT_BUTTON_SOUND);
    }

    @Override
    public void enableScreenClick(boolean enable) {
        Log.i(TAG, "enableScreenClick (" + enable + ")");
        this.textBox.setClickable(enable);
    }

    @Override
    public void forceFinishText() {
        Log.i(TAG, "forceFinishText");
        this.textBox.forceFinish();
    }

    @Override
    public void showNextText(int resId) {
        Log.i(TAG, "showNextText: (" + resId + ")");
        this.textBox.reset();
        this.textBox.animateText(getString(resId));
    }

    @Override
    public void onCreate(RupeesView view) {
        Log.i(TAG, "onCreate (" + view + ")");
        Rupees rupees = fetch(Storage::getRupees);
        view.update(rupees);
    }

    @Override
    protected void init() {
        super.init();
        Log.i(TAG, "init");

        this.presenter = getPresenter();
    }

    protected abstract P getPresenter();

    @Override
    protected ActivityTransitionAnimation getActivityTransitionAnimation() {
        Log.i(TAG, "getActivityTransitionAnimation");
        return NORMAL_FADE;
    }
}