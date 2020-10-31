package com.kirbydee.splooshkaboom.view.activities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TableLayout;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.model.GameState;
import com.kirbydee.splooshkaboom.model.gridcell.GridCellState;
import com.kirbydee.splooshkaboom.utils.Sound;
import com.kirbydee.splooshkaboom.utils.Sounds;
import com.kirbydee.splooshkaboom.view.ui.BombCellView;
import com.kirbydee.splooshkaboom.view.ui.GridCellView;
import com.kirbydee.splooshkaboom.view.ui.SquidCellView;

// TOOD: shake screen: reset ?

public class GameActivity extends BaseBackgroundSoundActivity implements
        GridCellView.GridCellViewListener,
        BombCellView.BombCellViewListener,
        SquidCellView.SquidCellViewListener {

    private static final String TAG = GameActivity.class.getName();

    private TableLayout gameGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        Sound.playSound(this, Sounds.INTRO);
        GameState.reset();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void setUpViews() {
        Log.i(TAG, "setUpViews");
        this.gameGrid = findViewById(R.id.gameGrid);
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

    @Override
    public void onShoot(GridCellView view) {
        Log.i(TAG, "onShoot (" + view + ")");
        shoot(view);
        GameState.detonateBomb();
        GameState.checkSquids();
        checkForWinLoss();
    }

    private void shoot(GridCellView view) {
        Log.i(TAG, "shoot");
        GridCellState state = GameState.shoot(view.getRowIndex(), view.getColumnIndex());
        switch (state) {
            case SPLOOSH:
                Log.i(TAG, "GridCellState: " + state);

                // SPLOOSH graphic
                view.setBackgroundResource(R.drawable.sploosh);

                // SPLOOSH sound
                Sound.playSound(this, Sounds.SPLOOSH);
                break;
            case KABOOM:
                Log.i(TAG, "GridCellState: " + state);

                // KABOOM graphic
                view.setBackgroundResource(R.drawable.kaboom);

                // KABOOM sound
                Sound.playSound(this, Sounds.KABOOM);

                // vibrate phone
                vibrate(100);
                break;
            default:
                Log.i(TAG, "unsupported GridCellState: " + state);
                // do nothing
        }
    }

    public void vibrate(long millis) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(millis, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(millis);
        }
    }

    private void checkForWinLoss() {
        Log.i(TAG, "checkForWinLoss");
        if (GameState.isWin()) {
            Sound.playSound(this, Sounds.HURRAY);
        }
    }

    @Override
    public void initDone(BombCellView view) {
        GameState.addBomb(view);
    }

    @Override
    public void initDone(SquidCellView view) {
        GameState.addSquid(view);
    }
}