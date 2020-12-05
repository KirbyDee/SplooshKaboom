package com.kirbydee.splooshkaboom.view.activities;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.kirbydee.splooshkaboom.controller.HandlerController;
import com.kirbydee.splooshkaboom.controller.media.VideoController;
import com.kirbydee.splooshkaboom.model.anim.ActivityTransitionAnimation;
import com.kirbydee.splooshkaboom.controller.media.SoundController;
import com.kirbydee.splooshkaboom.utils.Storage;

import static com.kirbydee.splooshkaboom.model.anim.ActivityTransitionAnimation.NORMAL_FADE;
import static com.kirbydee.splooshkaboom.model.anim.ActivityTransitionAnimation.NO_SPECIAL;

public abstract class BaseActivity extends Activity {

    private static final String TAG = BaseActivity.class.getName();

    private HandlerController handlerController;

    protected SoundController soundController;

    protected VideoController videoController;

    protected Storage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        // Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // override transitions
        overrideActivityTransition();

        // init
        init();
    }

    @Override
    public void finish() {
        super.finish();

        // override transitions
        overrideActivityTransition();
    }

    private void overrideActivityTransition() {
        ActivityTransitionAnimation anim = getActivityTransitionAnimation();
        if (anim != NO_SPECIAL) {
            overridePendingTransition(anim.enterAnim, anim.exitAnim);
        }
    }

    protected ActivityTransitionAnimation getActivityTransitionAnimation() {
        return NORMAL_FADE;
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();

        // setup
        setUpViews();
        setUpListeners();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        cleanUp();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        cleanUp();
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        cleanUp();
        super.onPause();
    }

    protected void cleanUp() {
        Log.i(TAG, "cleanUp");
        this.handlerController.clear();
        this.soundController.stopAll();
    }

    protected void runAfterDelay(Runnable r, long delay) {
        this.handlerController.postDelayed(r, delay);
    }

    protected <A extends Activity> void changeActivity(final Class<A> activity) {
        Log.i(TAG, "changeActivity");
        Intent intent = new Intent(this, activity);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        finish();
    }

    protected <A extends Activity> void changeActivityAfterDelay(final Class<A> activity, long delay) {
        runAfterDelay(() -> changeActivity(activity), delay);
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed");
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    protected void init() {
        Handler handler = new Handler();
        this.handlerController = new HandlerController(handler);
        this.soundController = new SoundController(this, this.handlerController);
        this.videoController = new VideoController(this);
        this.storage = new Storage(this);
    }

    protected void setUpViews() {
        // Override
    }

    protected void setUpListeners() {
        // Override
    }
}