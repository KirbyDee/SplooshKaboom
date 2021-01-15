package com.kirbydee.splooshkaboom.view.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.model.counter.Counter;
import com.kirbydee.splooshkaboom.model.media.Sound;

import static com.kirbydee.splooshkaboom.model.media.MonoVolume.LOUD;
import static com.kirbydee.splooshkaboom.model.media.Sound.INTRO_BACKGROUND;
import static com.kirbydee.splooshkaboom.model.media.Sound.INTRO_START;

public class StartActivity extends MediaBaseActivity {

    private static final String TAG = StartActivity.class.getName();

    private View screenView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        setContentView(R.layout.start);
    }

    @Override
    protected void setUpViews() {
        super.setUpViews();
        Log.i(TAG, "setUpViews");

        this.screenView = findViewById(R.id.screenView);
        this.screenView.setClickable(true);
    }

    @Override
    protected void setUpListeners() {
        super.setUpListeners();
        Log.i(TAG, "setUpListeners");

        this.screenView.setOnClickListener(this::onClick);
    }

    private void onClick(View v) {
        Log.i(TAG, "onClick");
        this.screenView.setClickable(false);
        play(INTRO_START, LOUD, mp -> changeActivity(WinActivity.class, () -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("counter", Counter.of(15));
            return bundle;
        })); // TODO
    }

    @Override
    protected Sound getBackgroundSound() {
        Log.i(TAG, "getBackgroundSound");
        return INTRO_BACKGROUND;
    }
}