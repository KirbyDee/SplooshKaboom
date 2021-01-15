package com.kirbydee.splooshkaboom.view.activities;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import com.kirbydee.splooshkaboom.contract.TextContract;
import com.kirbydee.splooshkaboom.model.anim.ActivityTransitionAnimation;
import com.kirbydee.splooshkaboom.model.counter.Rupees;
import com.kirbydee.splooshkaboom.model.media.Sound;
import com.kirbydee.splooshkaboom.model.media.Video;
import com.kirbydee.splooshkaboom.view.layoutviews.counter.RupeesView;
import com.kirbydee.splooshkaboom.view.layoutviews.textbox.TextBoxNext;
import com.kirbydee.splooshkaboom.view.layoutviews.textbox.TextBoxText;
import com.kirbydee.splooshkaboom.view.layoutviews.textbox.TextBoxView;
import com.kirbydee.splooshkaboom.view.layoutviews.textbox.TextFormatter;
import com.kirbydee.splooshkaboom.view.layoutviews.textbox.TextSpeed;

import static com.kirbydee.splooshkaboom.model.anim.ActivityTransitionAnimation.NORMAL_FADE;
import static com.kirbydee.splooshkaboom.model.media.Sound.TEXT_BUTTON_SOUND;

public abstract class TextBaseActivity<P extends TextContract.Presenter> extends MediaBaseActivity
        implements TextBoxNext.Listener, TextBoxText.Listener, RupeesView.Listener,
        TextContract.View, TextBoxView.Listener {

    private static final String TAG = TextBaseActivity.class.getName();

    // presenter
    protected P presenter;

    // screen view
    private View screenView;

    // menu text
    private TextBoxView textBoxView;
    private TextBoxText textBoxText;
    private TextBoxNext textBoxNext;

    // rupee view
    private RupeesView rupeeView;

    @Override
    protected void setUpViews() {
        super.setUpViews();
        Log.i(TAG, "setUpViews");

        // views
        this.screenView = findViewById(getScreenViewId());

        // view states
        showTextBoxText(false);
    }

    protected abstract int getScreenViewId();

    @Override
    @SuppressLint("ClickableViewAccessibility")
    protected void setUpListeners() {
        Log.i(TAG, "setUpListeners");
        super.setUpListeners();

        this.screenView.setOnClickListener(this::onClickScreen);
        enableScreenClick(true);
    }

    @Override
    public void onTextFinished(TextBoxText textBoxText) {
        Log.i(TAG, "onTextFinished (" + textBoxText + ")");
        this.presenter.onTextFinished();
    }

    @Override
    public void showTextBox(boolean show) {
        Log.i(TAG, "showTextBox (" + show + ")");
        this.textBoxView.show(show);
    }

    @Override
    public void showTextBoxNext(boolean show) {
        Log.i(TAG, "showTextBoxNext (" + show + ")");
        this.textBoxNext.show(show);
    }

    protected void onClickScreen(View v) {
        Log.i(TAG, "onClickScreen");
        this.presenter.onClickScreen();
    }

    @Override
    public void showTextBoxText(boolean show) {
        Log.i(TAG, "showTextBox: " + show);
        int visibility = show ? View.VISIBLE : View.GONE;
        this.textBoxText.setVisibility(visibility);
    }

    @Override
    public boolean isTextBoxFinished() {
        Log.i(TAG, "isTextBoxFinished");
        return this.textBoxText.isFinished();
    }

    @Override
    public void playSound(Sound sound) {
        Log.i(TAG, "playSound (" + sound + ")");
        play(sound);
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
        this.screenView.setClickable(enable);
    }

    @Override
    public void forceFinishText() {
        Log.i(TAG, "forceFinishText");
        this.textBoxText.forceFinish();
    }

    @Override
    public void showNextText(int resId) {
        Log.i(TAG, "showNextText: (" + resId + ")");
        this.textBoxText.reset();
        this.textBoxText.animateText(getString(resId));
    }

    @Override
    public void onCreate(RupeesView view) {
        Log.i(TAG, "onCreate (" + view + ")");
        this.rupeeView = view;
        Rupees rupees = getStorage().getRupees();
        this.rupeeView.set(rupees);
    }

    @Override
    public void updateRupees(Rupees rupees) {
        Log.i(TAG, "updateRupees (" + rupees + ")");
        this.rupeeView.update(rupees);
    }

    protected void setTextBoxFormatter(TextFormatter formatter) {
        Log.i(TAG, "setTextBoxFormatter (" + formatter + ")");
        this.textBoxText.setFormatter(formatter);
    }

    protected void setTextSpeed(TextSpeed textSpeed) {
        Log.i(TAG, "setTextSpeed (" + textSpeed + ")");
        this.textBoxText.setTextSpeed(textSpeed);
    }

    protected TextFormatter getTextBoxFormatter() {
        Log.i(TAG, "getTextBoxFormatter");
        return this.textBoxText.getFormatter();
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

    @Override
    public void onCreate(TextBoxText textBoxText) {
        Log.i(TAG, "onCreate (" + textBoxText + ")");
        this.textBoxText = textBoxText;
    }

    @Override
    public void onCreate(TextBoxNext textBoxNext) {
        Log.i(TAG, "onCreate (" + textBoxNext + ")");
        this.textBoxNext = textBoxNext;
    }

    @Override
    public void onCreate(TextBoxView textBoxView) {
        Log.i(TAG, "onCreate (" + textBoxView + ")");
        this.textBoxView = textBoxView;
    }

    @Override
    public void onAppear(TextBoxView textBoxView) {
        Log.i(TAG, "onAppear: " + textBoxView);
        // override
    }

    @Override
    public void onAppear(TextBoxNext textBoxNext) {
        Log.i(TAG, "onAppear: " + textBoxNext);
        // override
    }
}