package com.kirbydee.splooshkaboom.activity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.kirbydee.splooshkaboom.R;

import java.util.Random;

public class GameActivity extends BaseBackgroundSoundActivity {

    private static final String TAG = GameActivity.class.getName();

    private MediaPlayer soundPlayer;

    private TableLayout gameGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        playStartSound();
    }
    private void stopSound() {
        Log.i(TAG, "stopSound");
        if (soundPlayer != null) {
            soundPlayer.stop();
            soundPlayer.release();
            soundPlayer = null;
        }
    }

    private void playStartSound() {
        Log.i(TAG, "playStartSound");
        stopSound();
        this.soundPlayer = MediaPlayer.create(this, R.raw.game_intro);
        this.soundPlayer.start();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();


    }

    private int[] colors = {Color.BLACK, Color.WHITE, Color.RED, Color.BLUE, Color.GREEN};

    @Override
    protected void setUpViews() {
        Log.i(TAG, "setUpViews");

        Random rnd = new Random();

        this.gameGrid = findViewById(R.id.gridLayout);

        gameGrid.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                gameGrid.getViewTreeObserver().removeOnGlobalLayoutListener(this);



                int width = GameActivity.this.gameGrid.getWidth();
                int height = GameActivity.this.gameGrid.getHeight();

                for (int j = 0; j < 8; j++) {
                    TableRow row = new TableRow(GameActivity.this);
                    row.setId(View.generateViewId());
                    TableRow.LayoutParams rowParams = new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            height/8
                    );
                    row.setLayoutParams(rowParams);

                    for (int i = 0; i < 8; i++) {
                        View view = new View(GameActivity.this);
                        view.setBackgroundColor(colors[rnd.nextInt(5)]);
                        ViewGroup.LayoutParams cellParams = new ViewGroup.LayoutParams(
                                width/8,
                                ViewGroup.LayoutParams.MATCH_PARENT
                        );
                        row.addView(view);
                    }

                    GameActivity.this.gameGrid.addView(row);
                }
            }
        });
    }

    @Override
    protected void setUpListeners() {
        Log.i(TAG, "setUpListeners");

    }

    @Override
    protected int getBackgroundSoundId() {
        Log.i(TAG, "getBackgroundSoundId");
        return R.raw.game;
    }

    @Override
    protected long getBackgroundSoundDelay() {
        return 1300;
    }
}