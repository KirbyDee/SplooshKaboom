package com.kirbydee.splooshkaboom.view.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.contract.MenuContract;
import com.kirbydee.splooshkaboom.model.anim.ActivityTransitionAnimation;
import com.kirbydee.splooshkaboom.model.counter.Rupees;
import com.kirbydee.splooshkaboom.model.media.Sound;
import com.kirbydee.splooshkaboom.model.media.Video;
import com.kirbydee.splooshkaboom.presenter.MenuPresenter;
import com.kirbydee.splooshkaboom.utils.Storage;
import com.kirbydee.splooshkaboom.view.layoutviews.TextBox;
import com.kirbydee.splooshkaboom.view.layoutviews.TextBoxNext;
import com.kirbydee.splooshkaboom.view.layoutviews.counter.RupeesView;

import static com.kirbydee.splooshkaboom.model.anim.ActivityTransitionAnimation.NORMAL_FADE;
import static com.kirbydee.splooshkaboom.model.media.Sound.MENU_BACKGROUND;
import static com.kirbydee.splooshkaboom.model.media.Sound.TEXT_BUTTON_SOUND;
import static com.kirbydee.splooshkaboom.model.media.Video.MENU_INTRO;
import static com.kirbydee.splooshkaboom.model.media.Video.MENU_TALK;

public class MenuActivity extends MediaBaseActivity
        implements TextBoxNext.Listener, TextBox.Listener,
        MenuContract.View, RupeesView.Listener {

    private static final String TAG = MenuActivity.class.getName();

    // controller
    private MenuContract.Presenter menuPresenter;

    // screen view
    private View menuScreen;

    // menu text
    private TextBox menuTextView;
    private TextBoxNext menuTextNext;

    // menu items
    private TextView menuStart;
    private TextView menuShop;
    private TextView menuInventory;
    private TextView menuQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        setContentView(R.layout.menu);
    }

    @Override
    protected void setUpViews() {
        super.setUpViews();
        Log.i(TAG, "setUpViews");

        // views
        this.menuScreen = findViewById(R.id.menuScreen);
        this.menuTextView = findViewById(R.id.menuTextView);
        this.menuTextNext = findViewById(R.id.menuTextNext);
        this.menuStart = findViewById(R.id.menuStart);
        this.menuShop = findViewById(R.id.menuShop);
        this.menuInventory = findViewById(R.id.menuInventory);
        this.menuQuit = findViewById(R.id.menuQuit);

        // view states
        showMenuText(false);
        showMenu(false);

        // start background video
        play(MENU_INTRO, mp -> {
            play(MENU_TALK);
            this.menuPresenter.onIntro();
        });
    }

    @Override
    protected int getVideoViewId() {
        Log.i(TAG, "getVideoViewId");
        return R.id.menuVideoView;
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    protected void setUpListeners() {
        Log.i(TAG, "setUpListeners");
        super.setUpListeners();

        this.menuScreen.setOnClickListener(this::onClickScreen);
        enableScreenClick();

        this.menuStart.setOnClickListener(this::onClickStart);
        this.menuShop.setOnClickListener(this::onClickShop);
        this.menuInventory.setOnClickListener(this::onClickInventory);
        this.menuQuit.setOnClickListener(this::onClickQuit);
    }

    private void onClickScreen(View v) {
        Log.i(TAG, "onClickScreen");
        this.menuPresenter.onClickScreen();
    }

    private void onClickStart(View v) {
        Log.i(TAG, "onClickStart");
        play(TEXT_BUTTON_SOUND);
        this.menuPresenter.onStart();
    }

    private void onClickShop(View v) {
        Log.i(TAG, "onClickShop");
        play(TEXT_BUTTON_SOUND);
        changeActivity(ShopActivity.class);
    }

    private void onClickInventory(View v) {
        Log.i(TAG, "onClickInventory");
        play(TEXT_BUTTON_SOUND);
        changeActivity(StartActivity.class); // TODO
    }

    private void onClickQuit(View v) {
        Log.i(TAG, "onClickQuit");
        finish();
    }

    @Override
    public void onTextFinished(TextBox textBox) {
        Log.i(TAG, "onTextFinished: " + textBox);
        this.menuTextNext.show();
    }

    @Override
    public void onAnimationFinished(TextBoxNext view) {
        Log.i(TAG, "onAnimationFinished: " + view);
        // do nothing
    }

    @Override
    public void showMenuText(boolean show) {
        Log.i(TAG, "showMenuText: " + show);
        int visibility = show ? View.VISIBLE : View.GONE;
        this.menuTextView.setVisibility(visibility);
    }

    @Override
    protected void init() {
        super.init();
        Log.i(TAG, "init");

        this.menuPresenter = new MenuPresenter(this);
    }

    @Override
    protected Sound getBackgroundSound() {
        Log.i(TAG, "getBackgroundSound");
        return MENU_BACKGROUND;
    }

    @Override
    public boolean isTextBoxFinished() {
        Log.i(TAG, "isTextBoxFinished");
        return this.menuTextView.isFinished();
    }

    @Override
    public void playVideo(Video video) {
        play(video);
    }

    @Override
    public void removeNextText() {
        Log.i(TAG, "removeNextText");
        this.menuTextNext.unShow();
        play(TEXT_BUTTON_SOUND);
    }

    @Override
    public void disableScreenClick() {
        Log.i(TAG, "disableScreenClick");
        this.menuScreen.setClickable(false);
    }

    @Override
    public void enableScreenClick() {
        Log.i(TAG, "enableScreenClick");
        this.menuScreen.setClickable(true);
    }

    @Override
    public void startGame() {
        Log.i(TAG, "startGame");
        changeActivity(GameActivity.class);
    }

    @Override
    public void forceFinishText() {
        Log.i(TAG, "forceFinishText");
        this.menuTextView.forceFinish();
    }

    @Override
    public void showNextText(int resId) {
        Log.i(TAG, "showNextText: (" + resId + ")");
        this.menuTextView.reset();
        this.menuTextView.animateText(getString(resId));
    }

    @Override
    public void showMenu(boolean show) {
        Log.i(TAG, "showMenu: (" + show + ")");
        int visibility = show ? View.VISIBLE : View.GONE;

        // visibility
        this.menuStart.setVisibility(visibility);
        this.menuShop.setVisibility(visibility);
        this.menuInventory.setVisibility(visibility);
        this.menuQuit.setVisibility(visibility);

        // enable click
        this.menuStart.setClickable(show);
        this.menuShop.setClickable(show);
        this.menuInventory.setClickable(show);
        this.menuQuit.setClickable(show);
    }

    @Override
    public void onCreate(RupeesView view) {
        Log.i(TAG, "onCreate (" + view + ")");
        Rupees rupees = fetch(Storage::getRupees);
        view.update(rupees);
    }

    @Override
    protected ActivityTransitionAnimation getActivityTransitionAnimation() {
        Log.i(TAG, "getActivityTransitionAnimation");
        return NORMAL_FADE;
    }
}