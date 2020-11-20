package com.kirbydee.splooshkaboom.controller;

import android.content.Context;
import android.util.Log;

import com.kirbydee.splooshkaboom.model.counter.Counter;
import com.kirbydee.splooshkaboom.model.grid.game.GameGrid;
import com.kirbydee.splooshkaboom.model.grid.game.GameGridState;
import com.kirbydee.splooshkaboom.model.grid.game.Squid;
import com.kirbydee.splooshkaboom.model.grid.game.SquidType;
import com.kirbydee.splooshkaboom.model.grid.game.Water;
import com.kirbydee.splooshkaboom.model.grid.state.BombStateGrid;
import com.kirbydee.splooshkaboom.model.grid.state.SquidStateGrid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static com.kirbydee.splooshkaboom.model.grid.game.SquidType.SQUID_2;
import static com.kirbydee.splooshkaboom.model.grid.game.SquidType.SQUID_3;
import static com.kirbydee.splooshkaboom.model.grid.game.SquidType.SQUID_4;
import static com.kirbydee.splooshkaboom.utils.Consts.BOMB_COUNT;
import static com.kirbydee.splooshkaboom.utils.Consts.GAME_COLUMN_COUNT;
import static com.kirbydee.splooshkaboom.utils.Consts.GAME_ROW_COUNT;
import static com.kirbydee.splooshkaboom.utils.Consts.MAX_TURNS;
import static com.kirbydee.splooshkaboom.utils.Consts.SQUID_COUNT;

public class GameController {

    private static final String TAG = GameController.class.getName();


    private List<GameGrid> gameGrids;

    private List<BombStateGrid> bombStateGrids;

    private List<SquidStateGrid> squidCellGrids;

    private Counter counter;

    private Counter record;

    private int turnsLeft;

    private Listener listener;

    public interface Listener {

        void onCounterChange(Counter count);

        void onRecordChange(Counter count);

        void onSploosh(GameGrid gameGrid);

        void onKaboom(GameGrid gameGrid);

        void onDetonateSquid(SquidStateGrid squidStateGrid);

        void onDetonateBomb(BombStateGrid bombStateGrid);

        void onWin();

        void onLoss();
    }

    public GameController(Context context) {
        if (context instanceof GameController.Listener) {
            this.listener = (GameController.Listener) context;
        }
    }

    public void reset() {
        Log.i(TAG, "reset");
        this.turnsLeft = MAX_TURNS;
        initGameGrid();
        initBombs();
        initSquids();
        initCounter();
        initRecord();
    }

    private void initBombs() {
        Log.i(TAG, "initBombs");
        this.bombStateGrids = new ArrayList<>(BOMB_COUNT);
        IntStream.rangeClosed(0, BOMB_COUNT)
                .mapToObj(BombStateGrid::new)
                .forEach(this.bombStateGrids::add);
    }

    private void initSquids() {
        Log.i(TAG, "initSquids");
        this.squidCellGrids = new ArrayList<>(SQUID_COUNT);
        IntStream.rangeClosed(0, SQUID_COUNT)
                .mapToObj(SquidStateGrid::new)
                .forEach(this.squidCellGrids::add);
    }

    private void initCounter() {
        Log.i(TAG, "initCounter");
        this.counter = new Counter(0);
        this.listener.onCounterChange(this.counter);
    }

    private void initRecord() {
        Log.i(TAG, "initRecord");
        this.record = new Counter(15);  // TODO from shared preferences
        this.listener.onRecordChange(this.record);
    }

    // TODO: move to another class
    private void initGameGrid() {
        Log.i(TAG, "initGameGrid");
        this.gameGrids = new ArrayList<>(GAME_ROW_COUNT * GAME_COLUMN_COUNT);

        List<Squid> squid2 = initSquidType(SQUID_2);
        this.gameGrids.addAll(squid2);
        List<Squid> squid3 = initSquidType(SQUID_3);
        this.gameGrids.addAll(squid3);
        List<Squid> squid4 = initSquidType(SQUID_4);
        this.gameGrids.addAll(squid4);

        IntStream.rangeClosed(0, GAME_COLUMN_COUNT)
                .forEach(this::initGridCellWater);
    }

    private void initGridCellWater(int row) {
        IntStream.rangeClosed(0, GAME_COLUMN_COUNT)
                .filter(column -> !isUsed(row, column))
                .forEach(column -> this.gameGrids.add(new Water(row, column)));
    }

    private List<Squid> initSquidType(SquidType squidType) {
        Log.i(TAG, "initSquid: " + squidType);
        Random rnd = new Random();
        int row = rnd.nextInt(GAME_ROW_COUNT);
        int column = rnd.nextInt(GAME_COLUMN_COUNT);
        int direction = rnd.nextInt(4);

        return initSquidType(new ArrayList<>(squidType.length), squidType, squidType.length, row, column, direction);
    }

    private List<Squid> initSquidType(List<Squid> squid, SquidType squidType, int restLength, int row, int column, int direction) {
        Log.i(TAG, "initSquid (" + squidType + ", " + row + ", " + column + ", " + direction + ")");
        if (restLength == 0) {
            Log.i(TAG, "squid successfully created");
            return squid;
        }

        if (row >= GAME_ROW_COUNT || column >= GAME_COLUMN_COUNT || row < 0 || column < 0 || isUsed(row, column)) {
            Log.i(TAG, "squid failed to create, try again");
            return initSquidType(squidType);
        }
        squid.add(new Squid(row, column, squidType));

        int rowNew = row;
        int columnNew = column;
        switch (direction) {
            case 0:
                rowNew++;
                break;
            case 1:
                rowNew--;
                break;
            case 2:
                columnNew++;
                break;
            default:
                columnNew--;
                break;
        }

        return initSquidType(squid, squidType, restLength - 1, rowNew, columnNew, direction);
    }

    private boolean isUsed(int row, int column) {
        Log.i(TAG, "check if grid cell exists (" + row + ", " + column + ")");
        return this.gameGrids.stream()
                .anyMatch(c -> c.isCorrectGrid(row, column));
    }

    public void onShoot(int rowIndex, int columnIndex) {
        Log.i(TAG, "onShoot (" + rowIndex + ", " + columnIndex + ")");
        GameGrid gameGrid = this.gameGrids.stream()
                .filter(g -> g.isCorrectGrid(rowIndex, columnIndex))
                .findAny()
                .orElse(null);
        if (gameGrid == null || gameFinished() || gameGrid.cannotBeShot()) {
            Log.i(TAG, "Cannot be shot");
            return;
        }

        // we can shoot it -> decrease turn
        this.turnsLeft--;
        Log.i(TAG, "Turns left: " + this.turnsLeft);

        // shoot
        shoot(gameGrid);
        detonateBomb();
        checkSquids();
        checkForWinLoss();
    }

    private void shoot(GameGrid gameGrid) {
        Log.i(TAG, "shoot (" + gameGrid + ")");
        gameGrid.shoot();
        GameGridState state = gameGrid.getCurrentState();

        // check what state the shot grid is now
        switch (state) {
            case SPLOOSH:
                Log.i(TAG, "GameGridState: " + state);
                this.listener.onSploosh(gameGrid);
                break;
            case KABOOM:
                Log.i(TAG, "GameGridState: " + state);
                this.listener.onKaboom(gameGrid);
                break;
            default:
                Log.i(TAG, "unsupported GameGridState: " + state);
                // do nothing
        }
    }

    private void checkForWinLoss() {
        Log.i(TAG, "checkForWinLoss");
        if (isWin()) {
            this.listener.onWin();
        }
        else if (isLoss()) {
            this.listener.onLoss();
        }
    }

    private void checkSquids() {
        Log.i(TAG, "checkSquids");
        Arrays.stream(SquidType.values())
                .filter(this::checkIfSquidIsKaboom)
                .forEach(this::detonateSquid);
    }

    private boolean checkIfSquidIsKaboom(SquidType squidType) {
        Log.i(TAG, "checkIfSquidTypeIsGone (" + squidType + ")");
        return this.gameGrids.stream()
                .filter(c -> c instanceof Squid)
                .map(c -> (Squid) c)
                .filter(s -> s.getTpe() == squidType)
                .allMatch(s -> s.getCurrentState() == GameGridState.KABOOM);
    }

    private boolean isWin() {
        Log.i(TAG, "isWin");
        return this.turnsLeft >= 0 && this.gameGrids.stream()
                .noneMatch(c -> c.getCurrentState() == GameGridState.SQUID);
    }

    private boolean isLoss() {
        Log.i(TAG, "isLoss");
        return this.turnsLeft <= 0 && this.gameGrids.stream()
                .anyMatch(c -> c.getCurrentState() == GameGridState.SQUID);
    }

    private boolean gameFinished() {
        Log.i(TAG, "gameFinished");
        return this.turnsLeft <= 0;
    }

    private void detonateBomb() {
        Log.i(TAG, "detonateBomb");
        this.bombStateGrids.stream()
                .filter(b -> b.getBombIndex() == (MAX_TURNS - this.turnsLeft - 1))
                .findAny()
                .ifPresent(this.listener::onDetonateBomb);

        this.counter.increase();
        this.listener.onCounterChange(this.counter);
    }

    private void detonateSquid(SquidType squidType) {
        Log.i(TAG, "detonateSquid");
        this.squidCellGrids.stream()
                .filter(b -> b.getSquidSize() == squidType.length)
                .findAny()
                .ifPresent(this.listener::onDetonateSquid);
    }
}
