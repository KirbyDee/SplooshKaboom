package com.kirbydee.splooshkaboom.model.tile.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import static com.kirbydee.splooshkaboom.model.tile.game.GameTileSquidType.SQUID_2;
import static com.kirbydee.splooshkaboom.model.tile.game.GameTileSquidType.SQUID_3;
import static com.kirbydee.splooshkaboom.model.tile.game.GameTileSquidType.SQUID_4;
import static com.kirbydee.splooshkaboom.utils.Consts.GAME_COLUMN_COUNT;
import static com.kirbydee.splooshkaboom.utils.Consts.GAME_ROW_COUNT;
import static com.kirbydee.splooshkaboom.utils.Consts.MAX_TURNS;

public class GameBoard {

    private static final String TAG = GameBoard.class.getName();

    private List<GameTile> gameTiles;

    private int turnsLeft;

    public GameBoard() {
        this.turnsLeft = MAX_TURNS;
        initGameGrid();
    }

    private void initGameGrid() {
        Log.i(TAG, "initGameGrid");
        this.gameTiles = new ArrayList<>(GAME_ROW_COUNT * GAME_COLUMN_COUNT);

        this.gameTiles.addAll(initSquidType(SQUID_2));
        this.gameTiles.addAll(initSquidType(SQUID_3));
        this.gameTiles.addAll(initSquidType(SQUID_4));

        IntStream.range(0, GAME_COLUMN_COUNT)
                .forEach(this::initGridCellWater);
    }

    private void initGridCellWater(int row) {
        IntStream.range(0, GAME_COLUMN_COUNT)
                .filter(column -> !isUsed(row, column))
                .forEach(column -> this.gameTiles.add(new GameTileWater(row, column)));
    }

    private List<GameTileSquid> initSquidType(GameTileSquidType squidType) {
        Log.i(TAG, "initSquid: " + squidType);
        Random rnd = new Random();
        int row = rnd.nextInt(GAME_ROW_COUNT);
        int column = rnd.nextInt(GAME_COLUMN_COUNT);
        int direction = rnd.nextInt(4);

        return initSquidType(new ArrayList<>(squidType.length), squidType, squidType.length, row, column, direction);
    }

    private List<GameTileSquid> initSquidType(List<GameTileSquid> gameGridSquid, GameTileSquidType squidType, int restLength, int row, int column, int direction) {
        Log.i(TAG, "initSquid (" + squidType + ", " + row + ", " + column + ", " + direction + ")");
        if (restLength == 0) {
            Log.i(TAG, "squid successfully created");
            return gameGridSquid;
        }

        if (row >= GAME_ROW_COUNT || column >= GAME_COLUMN_COUNT || row < 0 || column < 0 || isUsed(row, column)) {
            Log.i(TAG, "squid failed to create, try again");
            return initSquidType(squidType);
        }
        gameGridSquid.add(new GameTileSquid(row, column, squidType));

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

        return initSquidType(gameGridSquid, squidType, restLength - 1, rowNew, columnNew, direction);
    }

    private boolean isUsed(int row, int column) {
        Log.i(TAG, "check if grid cell exists (" + row + ", " + column + ")");
        return this.gameTiles.stream()
                .anyMatch(c -> c.isCorrectTile(row, column));
    }

    public Optional<GameTile> findTile(int rowIndex, int columnIndex) {
        Log.i(TAG, "findTile (" + rowIndex + ", " + columnIndex + ")");
        return this.gameTiles.stream()
                .filter(g -> g.isCorrectTile(rowIndex, columnIndex))
                .findAny();
    }

    public int decreaseTurns() {
        Log.i(TAG, "decreaseTurns");
        this.turnsLeft--;
        Log.i(TAG, "Turns left: " + this.turnsLeft);
        return this.turnsLeft;
    }

    public boolean checkIfSquidIsKaboom(GameTileSquidType squidType) {
        Log.i(TAG, "checkIfSquidTypeIsGone (" + squidType + ")");
        return this.gameTiles.stream()
                .filter(c -> c instanceof GameTileSquid)
                .map(c -> (GameTileSquid) c)
                .filter(s -> s.getTpe() == squidType)
                .allMatch(s -> s.getCurrentState() == GameTileState.KABOOM);
    }

    public boolean isWin() {
        Log.i(TAG, "isWin");
        return this.turnsLeft >= 0 && this.gameTiles.stream()
                .noneMatch(c -> c.getCurrentState() == GameTileState.SQUID);
    }

    public boolean isLoss() {
        Log.i(TAG, "isLoss");
        return this.turnsLeft <= 0 && this.gameTiles.stream()
                .anyMatch(c -> c.getCurrentState() == GameTileState.SQUID);
    }

    public boolean gameFinished() {
        Log.i(TAG, "gameFinished");
        return this.turnsLeft <= 0;
    }

    @Override
    public String toString() {
        return "GameBoard{" +
                "gameTiles=" + gameTiles +
                '}';
    }
}
