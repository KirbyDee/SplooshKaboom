package com.kirbydee.splooshkaboom.controller;

import android.content.Context;
import android.util.Log;

import com.kirbydee.splooshkaboom.model.counter.Counter;
import com.kirbydee.splooshkaboom.model.tile.game.GameBoard;
import com.kirbydee.splooshkaboom.model.tile.game.GameTile;
import com.kirbydee.splooshkaboom.model.tile.game.GameTileSquidType;
import com.kirbydee.splooshkaboom.model.tile.game.GameTileState;
import com.kirbydee.splooshkaboom.model.tile.state.Bomb;
import com.kirbydee.splooshkaboom.model.tile.state.Bombs;
import com.kirbydee.splooshkaboom.model.tile.state.Squid;
import com.kirbydee.splooshkaboom.model.tile.state.Squids;
import com.kirbydee.splooshkaboom.utils.Storage;

import java.util.Arrays;

import static com.kirbydee.splooshkaboom.utils.Consts.MAX_TURNS;

public class GameController {

    private static final String TAG = GameController.class.getName();

    // Storage
    private final Storage storage;

    // Models
    private GameBoard gameBoard;
    private Squids squids;
    private Bombs bombs;
    private Counter counter;
    private Counter record;

    // Listener
    private Listener listener;

    public interface Listener {

        void onCounterChange(Counter count);

        void onRecordChange(Counter record);

        void onSploosh(GameTile gameTile);

        void onKaboom(GameTile gameTile);

        void onDetonateSquid(Squid squid);

        void onDetonateBomb(Bomb bomb);

        void onWin(Counter counter);

        void onLoss();
    }

    public GameController(Context context) {
        this.storage = new Storage(context);
        if (context instanceof GameController.Listener) {
            this.listener = (GameController.Listener) context;
        }
    }

    public void reset() {
        Log.i(TAG, "reset");
        initGameBoard();
        initBombs();
        initSquids();
        initCounter();
        initRecord();
    }

    private void initGameBoard() {
        Log.i(TAG, "initBombs");
        this.gameBoard = new GameBoard();
    }

    private void initBombs() {
        Log.i(TAG, "initBombs");
        this.bombs = new Bombs();
    }

    private void initSquids() {
        Log.i(TAG, "initSquids");
        this.squids = new Squids();
    }

    private void initCounter() {
        Log.i(TAG, "initCounter");
        this.counter = new Counter(0);
        this.listener.onCounterChange(this.counter);
    }

    private void initRecord() {
        Log.i(TAG, "initRecord");
        this.record = this.storage.getRecord();
        this.listener.onRecordChange(this.record);
    }

    public void onShoot(int rowIndex, int columnIndex) {
        Log.i(TAG, "onShoot (" + rowIndex + ", " + columnIndex + ")");

        // check if game is already finished
        if (this.gameBoard.gameFinished()) {
            Log.i(TAG, "Cannot be shot, game already finished");
            return;
        }

        // try to shot the tile
        this.gameBoard.findTile(rowIndex, columnIndex)
                .filter(GameTile::canBeShot)
                .ifPresent(this::onShoot);
    }

    private void onShoot(GameTile gameTile) {
        Log.i(TAG, "onShoot (" + gameTile + ")");
        shoot(gameTile);
        detonateBomb();
        checkSquids();
        checkForWinLoss();
    }

    private void shoot(GameTile gameTile) {
        Log.i(TAG, "shoot (" + gameTile + ")");
        GameTileState state = gameTile.shoot();

        // check what state the shot grid is now
        switch (state) {
            case SPLOOSH:
                Log.i(TAG, "GameGridState: " + state);
                this.listener.onSploosh(gameTile);
                break;
            case KABOOM:
                Log.i(TAG, "GameGridState: " + state);
                this.listener.onKaboom(gameTile);
                break;
            default:
                Log.i(TAG, "unsupported GameGridState: " + state);
                // do nothing
        }
    }

    private void checkForWinLoss() {
        Log.i(TAG, "checkForWinLoss");
        if (this.gameBoard.isWin()) {
            this.listener.onWin(this.counter);
        }
        else if (this.gameBoard.isLoss()) {
            this.listener.onLoss();
        }
    }

    private void checkSquids() {
        Log.i(TAG, "checkSquids");
        Arrays.stream(GameTileSquidType.values())
                .filter(this.gameBoard::checkIfSquidIsKaboom)
                .forEach(this::detonateSquid);
    }

    private void detonateBomb() {
        Log.i(TAG, "detonateBomb");
        int turnsLeft = this.gameBoard.decreaseTurns();
        this.bombs
                .findBomb(MAX_TURNS - turnsLeft - 1)
                .ifPresent(this.listener::onDetonateBomb);

        this.counter.increase();
        this.listener.onCounterChange(this.counter);
    }

    private void detonateSquid(GameTileSquidType squidType) {
        Log.i(TAG, "detonateSquid (" + squidType + ")");
        this.squids
                .findNotDetonatedSquidAndDetonate(squidType.length)
                .ifPresent(this.listener::onDetonateSquid);
    }
}
