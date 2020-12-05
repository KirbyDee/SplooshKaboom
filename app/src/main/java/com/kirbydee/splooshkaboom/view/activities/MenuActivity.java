package com.kirbydee.splooshkaboom.view.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.controller.MenuController;
import com.kirbydee.splooshkaboom.model.counter.Rupees;
import com.kirbydee.splooshkaboom.model.media.Sound;
import com.kirbydee.splooshkaboom.model.media.Video;
import com.kirbydee.splooshkaboom.utils.VoidFunction;
import com.kirbydee.splooshkaboom.view.layoutviews.TextBox;
import com.kirbydee.splooshkaboom.view.layoutviews.TextBoxNext;
import com.kirbydee.splooshkaboom.view.layoutviews.counter.RupeesView;

import static com.kirbydee.splooshkaboom.model.media.Sound.MENU_BACKGROUND;
import static com.kirbydee.splooshkaboom.model.media.Sound.TEXT_BUTTON_SOUND;
import static com.kirbydee.splooshkaboom.model.media.Video.MENU_INTRO;
import static com.kirbydee.splooshkaboom.model.media.Video.MENU_TALK;

public class MenuActivity extends BackgroundSoundBaseActivity
        implements TextBoxNext.Listener, TextBox.Listener,
        MenuController.Listener, RupeesView.Listener {

    private static final String TAG = MenuActivity.class.getName();

    // controller
    private MenuController menuController;

    // screen view
    private View menuScreen;

    // video
    private VideoView videoView;

    // menu text
    private TextBox menuTextView;
    private TextBoxNext textNext;

    // menu items
    private TextView menuStart;
    private TextView menuShop;
    private TextView menuInventory;
    private TextView menuQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
    }

    @Override
    protected void setUpViews() {
        Log.i(TAG, "setUpViews");

        // views
        this.menuScreen = findViewById(R.id.menuScreen);
        this.videoView = findViewById(R.id.menuVideoView);
        this.menuTextView = findViewById(R.id.menuTextView);
        this.textNext = findViewById(R.id.textNext);
        this.menuStart = findViewById(R.id.menuStart);
        this.menuShop = findViewById(R.id.menuShop);
        this.menuInventory = findViewById(R.id.menuInventory);
        this.menuQuit = findViewById(R.id.menuQuit);

        // view states
        showMenuText(false);
        showMenu(false);

        // start background video
        this.videoController.play(this.videoView, MENU_INTRO, mp -> {
            playVideo(MENU_TALK);
            this.menuController.onIntro();
        });
    }

    @Override
    public void playVideo(Video video) {
        this.videoController.play(this.videoView, video);
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    protected void setUpListeners() {
        Log.i(TAG, "setUpListeners");
        enableScreenTouch();
        this.menuStart.setOnTouchListener(this::onTouchStart);
        this.menuShop.setOnTouchListener(this::onTouchShop);
        this.menuInventory.setOnTouchListener(this::onTouchInventory);
        this.menuQuit.setOnTouchListener(this::onTouchQuit);
    }

    private boolean onTouchScreen(View v, MotionEvent event) {
        Log.i(TAG, "onTouchVideo: " + event);
        return onTouch(v, event, this.menuController::onTouchScreen);
    }

    private boolean onTouchStart(View v, MotionEvent event) {
        Log.i(TAG, "onTouchStart: " + event);
        return onTouch(v, event, () -> {
            this.soundController.play(TEXT_BUTTON_SOUND);
            this.menuController.onStart();
        });
    }

    private boolean onTouchShop(View v, MotionEvent event) {
        Log.i(TAG, "onTouchStart: " + event);
        return onTouch(v, event, () -> {
            this.soundController.play(TEXT_BUTTON_SOUND);
            changeActivity(ShopActivity.class);
        });
    }

    private boolean onTouchInventory(View v, MotionEvent event) {
        Log.i(TAG, "onTouchStart: " + event);
        return onTouch(v, event, () -> {
            this.soundController.play(TEXT_BUTTON_SOUND);
            changeActivity(StartActivity.class); // TODO
        });
    }

    private boolean onTouchQuit(View v, MotionEvent event) {
        Log.i(TAG, "onTouchStart: " + event);
        return onTouch(v, event, this::finish);
    }

    private boolean onTouch(View v, MotionEvent event, VoidFunction onTouchFunction) {
        Log.i(TAG, "onTouchStart: " + event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onTouchFunction.run();
                break;
            case MotionEvent.ACTION_UP:
                v.performClick();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onTextFinished(TextBox textBox) {
        Log.i(TAG, "onTextFinished: " + textBox);
        this.textNext.show();
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
        Log.i(TAG, "init");
        super.init();

        this.menuController = new MenuController(this);
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
    public void removeNextText() {
        Log.i(TAG, "removeNextText");
        this.textNext.unShow();
        this.soundController.play(TEXT_BUTTON_SOUND);
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public void disableScreenTouch() {
        Log.i(TAG, "disableScreenTouch");
        this.menuScreen.setOnTouchListener((v, event) -> false);
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public void enableScreenTouch() {
        Log.i(TAG, "enableScreenTouch");
        this.menuScreen.setOnTouchListener(this::onTouchScreen);
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
    protected void cleanUp() {
        Log.i(TAG, "cleanUp");
        super.cleanUp();
        this.videoController.stop(this.videoView);
    }

    @Override
    public void onCreate(RupeesView view) {
        Log.i(TAG, "onCreate (" + view + ")");
        Rupees rupees = this.storage.getRupees();
        view.update(rupees);
    }
}