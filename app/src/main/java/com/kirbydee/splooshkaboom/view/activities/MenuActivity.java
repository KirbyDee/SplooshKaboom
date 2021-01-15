package com.kirbydee.splooshkaboom.view.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.contract.MenuContract;
import com.kirbydee.splooshkaboom.model.anim.ActivityTransitionAnimation;
import com.kirbydee.splooshkaboom.model.media.Sound;
import com.kirbydee.splooshkaboom.presenter.MenuPresenter;

import static com.kirbydee.splooshkaboom.model.anim.ActivityTransitionAnimation.NORMAL_FADE;
import static com.kirbydee.splooshkaboom.model.media.Sound.MENU_BACKGROUND;
import static com.kirbydee.splooshkaboom.model.media.Sound.TEXT_BUTTON_SOUND;
import static com.kirbydee.splooshkaboom.model.media.Video.MENU_INTRO;
import static com.kirbydee.splooshkaboom.model.media.Video.MENU_TALK;

public class MenuActivity extends TextBaseActivity<MenuContract.Presenter> implements MenuContract.View {

    private static final String TAG = MenuActivity.class.getName();

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
        this.menuStart = findViewById(R.id.menuStart);
        this.menuShop = findViewById(R.id.menuShop);
        this.menuInventory = findViewById(R.id.menuInventory);
        this.menuQuit = findViewById(R.id.menuQuit);

        // view states
        showMenu(false);

        // start background video
        play(MENU_INTRO, mp -> {
            play(MENU_TALK);
            this.presenter.onIntro();
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

        this.menuStart.setOnClickListener(this::onClickStart);
        this.menuShop.setOnClickListener(this::onClickShop);
        this.menuInventory.setOnClickListener(this::onClickInventory);
        this.menuQuit.setOnClickListener(this::onClickQuit);
    }

    @Override
    protected int getScreenViewId() {
        Log.i(TAG, "getScreenViewId");
        return R.id.menuScreen;
    }

    private void onClickStart(View v) {
        Log.i(TAG, "onClickStart");
        play(TEXT_BUTTON_SOUND);
        this.presenter.onStart();
    }

    private void onClickShop(View v) {
        Log.i(TAG, "onClickShop");
        play(TEXT_BUTTON_SOUND);
        changeActivity(ShopActivity.class);
    }

    private void onClickInventory(View v) {
        Log.i(TAG, "onClickInventory");
        play(TEXT_BUTTON_SOUND);
        changeActivity(InventoryActivity.class);
    }

    private void onClickQuit(View v) {
        Log.i(TAG, "onClickQuit");
        finish();
    }

    @Override
    protected MenuContract.Presenter getPresenter() {
        Log.i(TAG, "getPresenter");
        return new MenuPresenter(this, getStorage());
    }

    @Override
    protected Sound getBackgroundSound() {
        Log.i(TAG, "getBackgroundSound");
        return MENU_BACKGROUND;
    }

    @Override
    public void startGame() {
        Log.i(TAG, "startGame");
        changeActivity(GameActivity.class);
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
        // TODO: better visuals for those buttons
        this.menuStart.setClickable(show);
        this.menuShop.setClickable(show);
        this.menuInventory.setClickable(show);
        this.menuQuit.setClickable(show);
    }

    @Override
    protected ActivityTransitionAnimation getActivityTransitionAnimation() {
        Log.i(TAG, "getActivityTransitionAnimation");
        return NORMAL_FADE;
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed");
        this.presenter.onBackPressed();
    }
}