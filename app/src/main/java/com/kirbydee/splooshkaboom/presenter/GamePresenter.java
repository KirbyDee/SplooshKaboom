package com.kirbydee.splooshkaboom.presenter;

import android.util.Log;

import com.kirbydee.splooshkaboom.contract.GameContract;
import com.kirbydee.splooshkaboom.model.counter.Counter;
import com.kirbydee.splooshkaboom.model.tile.game.GameBoard;
import com.kirbydee.splooshkaboom.model.tile.game.GameTile;
import com.kirbydee.splooshkaboom.model.tile.game.GameTileSquidType;
import com.kirbydee.splooshkaboom.model.tile.game.GameTileState;
import com.kirbydee.splooshkaboom.model.tile.state.Bombs;
import com.kirbydee.splooshkaboom.model.tile.state.Squids;

import java.util.Arrays;

import static com.kirbydee.splooshkaboom.utils.Consts.MAX_TURNS;

public class GamePresenter implements GameContract.Presenter {

    private static final String TAG = GamePresenter.class.getName();

    // Models
    private GameBoard gameBoard;
    private Squids squids;
    private Bombs bombs;
    private Counter counter;

    // view
    private final GameContract.View view;

    public GamePresenter(GameContract.View view) {
        this.view = view;
    }

    @Override
    public void reset() {
        Log.i(TAG, "reset");
        initGameBoard();
        initBombs();
        initSquids();
        initCounter();
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
    }

    @Override
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
                this.view.onSploosh(gameTile);
                break;
            case KABOOM:
                Log.i(TAG, "GameGridState: " + state);
                this.view.onKaboom(gameTile);
                break;
            default:
                Log.i(TAG, "unsupported GameGridState: " + state);
                // do nothing
        }
    }

    private void checkForWinLoss() {
        Log.i(TAG, "checkForWinLoss");
        if (this.gameBoard.isWin()) {
            this.view.onWin(this.counter);
        }
        else if (this.gameBoard.isLoss()) {
            this.view.onLoss();
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
                .ifPresent(this.view::onDetonateBomb);

        this.counter.increase();
        this.view.onCounterChange(this.counter);
    }

    private void detonateSquid(GameTileSquidType squidType) {
        Log.i(TAG, "detonateSquid (" + squidType + ")");
        this.squids
                .findNotDetonatedSquidAndDetonate(squidType.length)
                .ifPresent(this.view::onDetonateSquid);
    }
}
