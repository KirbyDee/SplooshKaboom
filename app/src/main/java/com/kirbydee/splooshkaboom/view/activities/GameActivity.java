package com.kirbydee.splooshkaboom.view.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.controller.GameController;
import com.kirbydee.splooshkaboom.controller.ShakeDetector;
import com.kirbydee.splooshkaboom.model.counter.Counter;
import com.kirbydee.splooshkaboom.model.tile.game.GameTile;
import com.kirbydee.splooshkaboom.model.tile.state.Bomb;
import com.kirbydee.splooshkaboom.model.tile.state.Squid;
import com.kirbydee.splooshkaboom.utils.Storage;
import com.kirbydee.splooshkaboom.utils.Vibrator;
import com.kirbydee.splooshkaboom.utils.sound.Sounds;
import com.kirbydee.splooshkaboom.view.dialog.RestartDialog;
import com.kirbydee.splooshkaboom.view.layoutviews.ResetView;
import com.kirbydee.splooshkaboom.view.layoutviews.counter.CounterView;
import com.kirbydee.splooshkaboom.view.layoutviews.counter.RecordView;
import com.kirbydee.splooshkaboom.view.layoutviews.tile.TileView;
import com.kirbydee.splooshkaboom.view.layoutviews.tile.game.GameTileView;
import com.kirbydee.splooshkaboom.view.layoutviews.tile.state.BombView;
import com.kirbydee.splooshkaboom.view.layoutviews.tile.state.SquidView;

import java.util.ArrayList;
import java.util.List;

import static com.kirbydee.splooshkaboom.utils.Consts.GAME_ACTIVITY_BACKGROUND_SOUND_DELAY;
import static com.kirbydee.splooshkaboom.utils.sound.Sounds.GAME_BACKGROUND;
import static com.kirbydee.splooshkaboom.utils.sound.Sounds.GAME_START;
import static com.kirbydee.splooshkaboom.utils.sound.Sounds.HURRAY;
import static com.kirbydee.splooshkaboom.utils.sound.Sounds.KABOOM;
import static com.kirbydee.splooshkaboom.utils.sound.Sounds.SPLOOSH;

public class GameActivity extends BaseBackgroundSoundActivity implements
        GameTileView.Listener, BombView.Listener, SquidView.Listener,
        CounterView.Listener, RecordView.Listener, GameController.Listener,
        ShakeDetector.Listener, RestartDialog.Listener, ResetView.Listener {

    private static final String TAG = GameActivity.class.getName();

    // Storage
    private Storage storage;

    // Controller
    private GameController gameController;

    // Views
    private CounterView counterView;
    private RecordView recordView;
    private List<GameTileView> gameTileViews;
    private List<SquidView> squidViews;
    private List<BombView> bombViews;

    // The following are used for the shake detection
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ShakeDetector shakeDetector;
    private Vibrator vibrator;

    // Reset Dialog
    private RestartDialog restartDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        // reset game
        resetGame();
    }

    private void resetGame() {
        // reset controllers and views
        resetGameController();
        resetGridViews();

        // play start sound
        this.sound.play(GAME_START);
    }

    private void resetGameController() {
        Log.i(TAG, "resetGameController");
        this.gameController.reset();
    }

    private void resetGridViews() {
        Log.i(TAG, "resetGridViews");
        this.gameTileViews.forEach(TileView::reset);
        this.squidViews.forEach(TileView::reset);
        this.bombViews.forEach(TileView::reset);
    }

    @Override
    public void onShake(int count) {
        Log.i(TAG, "onShake (" + count + ")");
        this.restartDialog.show();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
        this.sound.play(GAME_START);
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
        this.sensorManager.unregisterListener(this.shakeDetector);
        super.onPause();
    }

    @Override
    protected void init() {
        Log.i(TAG, "init");
        super.init();
        this.storage = new Storage(this);
        this.gameController = new GameController(this);
        this.restartDialog = new RestartDialog(this);
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.accelerometer = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.shakeDetector = new ShakeDetector(this);
        this.vibrator = new Vibrator(this);
        this.gameTileViews = new ArrayList<>();
        this.squidViews = new ArrayList<>();
        this.bombViews = new ArrayList<>();
    }

    @Override
    protected void setUpViews() {
        Log.i(TAG, "setUpViews");
    }

    @Override
    protected void setUpListeners() {
        Log.i(TAG, "setUpListeners");
        this.sensorManager.registerListener(this.shakeDetector, this.accelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected Sounds getBackgroundSound() {
        Log.i(TAG, "getBackgroundSound");
        return GAME_BACKGROUND;
    }

    @Override
    protected long getBackgroundSoundDelay() {
        Log.i(TAG, "getBackgroundSoundDelay");
        return GAME_ACTIVITY_BACKGROUND_SOUND_DELAY;
    }

    @Override
    public void onClick(GameTileView view) {
        Log.i(TAG, "onClick (" + view + ")");
        this.gameController.onShoot(view.getRowIndex(), view.getColumnIndex());
    }

    @Override
    public void onCreate(GameTileView view) {
        Log.i(TAG, "onCreate (" + view + ")");
        view.reset();
        this.gameTileViews.add(view);
    }

    @Override
    public void onCreate(BombView view) {
        Log.i(TAG, "onCreate (" + view + ")");
        view.reset();
        this.bombViews.add(view);
    }

    @Override
    public void onCreate(SquidView view) {
        Log.i(TAG, "onCreate (" + view + ")");
        view.reset();
        this.squidViews.add(view);
    }

    @Override
    public void onCreate(CounterView view) {
        Log.i(TAG, "onCreate (" + view + ")");
        this.counterView = view;
    }

    @Override
    public void onCreate(RecordView view) {
        Log.i(TAG, "onCreate (" + view + ")");
        this.recordView = view;
    }

    @Override
    public void onCounterChange(Counter counter) {
        Log.i(TAG, "onCounterChange (" + counter + ")");
        this.counterView.update(counter);
    }

    @Override
    public void onRecordChange(Counter record) {
        Log.i(TAG, "onRecordChange (" + record + ")");
        this.recordView.update(record);
    }

    @Override
    public void onSploosh(GameTile gameTile) {
        Log.i(TAG, "onSploosh (" + gameTile + ")");
        this.sound.play(SPLOOSH);
        this.gameTileViews.stream()
                .filter(gameTile::isCorrectTile)
                .forEach(GameTileView::sploosh);
    }

    @Override
    public void onKaboom(GameTile gameTile) {
        Log.i(TAG, "onKaboom (" + gameTile + ")");
        this.sound.play(KABOOM);
        vibrate();
        this.gameTileViews.stream()
                .filter(gameTile::isCorrectTile)
                .forEach(GameTileView::kaboom);
    }

    @Override
    public void onDetonateSquid(Squid squid) {
        Log.i(TAG, "onDetonateSquid (" + squid + ")");
        this.squidViews.stream()
                .filter(squid::isCorrectSquid)
                .forEach(SquidView::disable);
    }

    @Override
    public void onDetonateBomb(Bomb bomb) {
        Log.i(TAG, "onDetonateBomb (" + bomb + ")");
        this.bombViews.stream()
                .filter(bomb::isCorrectBomb)
                .forEach(BombView::disable);
    }

    private void vibrate() {
        Log.i(TAG, "vibrate");
        this.vibrator.vibrate();
    }

    @Override
    public void onWin(Counter counter) {
        Log.i(TAG, "onWin (" + counter + ")");
        this.storage.storeRecord(counter);
        this.sound.play(HURRAY, 200);
        resetGame();
    }

    @Override
    public void onLoss() {
        Log.i(TAG, "onLoss");
        changeActivity(GameOverActivity.class);
    }

    @Override
    public void onPositive(DialogInterface dialog) {
        Log.i(TAG, "onPositive (" + dialog + ")");
        resetGame();
    }

    @Override
    public void onNegative(DialogInterface dialog) {
        Log.i(TAG, "onNegative (" + dialog + ")");
        // do nothing
    }

    @Override
    public void onCreate(ResetView view) {
        Log.i(TAG, "onCreate (" + view + ")");
        // do nothing
    }

    @Override
    public void onClick(ResetView view) {
        Log.i(TAG, "onClick (" + view + ")");
        this.restartDialog.show();
    }
}