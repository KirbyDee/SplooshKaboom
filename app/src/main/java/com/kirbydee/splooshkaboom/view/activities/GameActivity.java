package com.kirbydee.splooshkaboom.view.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.controller.GameController;
import com.kirbydee.splooshkaboom.controller.ShakeDetector;
import com.kirbydee.splooshkaboom.model.cellview.BombCellView;
import com.kirbydee.splooshkaboom.model.cellview.BombCellView.BombCellViewListener;
import com.kirbydee.splooshkaboom.model.cellview.GridCellView;
import com.kirbydee.splooshkaboom.model.cellview.GridCellView.GridCellViewListener;
import com.kirbydee.splooshkaboom.model.cellview.SquidCellView;
import com.kirbydee.splooshkaboom.model.cellview.SquidCellView.SquidCellViewListener;
import com.kirbydee.splooshkaboom.model.gridcell.GridCellState;
import com.kirbydee.splooshkaboom.utils.Sound;
import com.kirbydee.splooshkaboom.utils.Sounds;

public class GameActivity extends BaseBackgroundSoundActivity implements
        GridCellViewListener, BombCellViewListener, SquidCellViewListener {

    private static final String TAG = GameActivity.class.getName();

    private GameController gameController = new GameController();

    private View gameGrid;

    // The following are used for the shake detection
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ShakeDetector shakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        Sound.playSound(this, Sounds.INTRO);

        initGameController();
        initShakeDetector();
    }

    private void resetGame() {
        initGameController();
    }

    private void initGameController() {
        Log.i(TAG, "initGameController");
        this.gameController.reset();
    }

    private void initShakeDetector() {
        Log.i(TAG, "initShakeDetector");
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.accelerometer = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.shakeDetector = new ShakeDetector();
        this.shakeDetector.setOnShakeListener(this::handleShakeEvent);
    }

    private void handleShakeEvent(int count) {
        Log.i(TAG, "handleShakeEvent: " + count);

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.reset_popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        popupWindow.showAtLocation(this.gameGrid, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    resetGame();
                    popupWindow.dismiss();
                    return true;
                case MotionEvent.ACTION_UP:
                    v.performClick();
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
        this.sensorManager.registerListener(this.shakeDetector, this.accelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
        this.sensorManager.unregisterListener(this.shakeDetector);
        super.onPause();
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
    public void onClick(GridCellView view) {
        Log.i(TAG, "onShoot (" + view + ")");
        if (this.gameController.gameFinished() || this.gameController.hasBeenShotAlready(view)) {
            Log.i(TAG, "Cannot be clicked");
            return;
        }

        shoot(view);
        this.gameController.detonateBomb();
        this.gameController.checkSquids();
        checkForWinLoss();
    }

    private void shoot(GridCellView view) {
        Log.i(TAG, "shoot");
        GridCellState state = this.gameController.shoot(view);
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
        if (this.gameController.isWin()) {
            Sound.playSound(this, Sounds.HURRAY, 1);
        }
        else if (this.gameController.isLoss()) {
            changeActivity(GameOverActivity.class);
        }
    }

    @Override
    public void onCreate(GridCellView view) {
        // TODO
    }

    @Override
    public void onCreate(BombCellView view) {
        this.gameController.addBomb(view);
    }

    @Override
    public void onCreate(SquidCellView view) {
        this.gameController.addSquid(view);
    }
}