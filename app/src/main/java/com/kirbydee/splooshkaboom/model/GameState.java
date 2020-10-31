package com.kirbydee.splooshkaboom.model;

import android.util.Log;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.model.gridcell.GridCell;
import com.kirbydee.splooshkaboom.model.gridcell.GridCellState;
import com.kirbydee.splooshkaboom.model.gridcell.Squid;
import com.kirbydee.splooshkaboom.model.gridcell.Water;
import com.kirbydee.splooshkaboom.view.ui.BombCellView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameState {

    private static final String TAG = GameState.class.getName();

    private static List<GridCell> gridCells;

    private static List<BombCellView> bombCellViews;

    private static final int MAX_TURNS = 24;

    private static int turnsLeft;

    public static void reset() {
        Log.i(TAG, "reset grid cells");
        gridCells = new ArrayList<>(8*8);
        turnsLeft = MAX_TURNS;

        List<Squid> squid2 = initSquid(2);
        gridCells.addAll(squid2);
        List<Squid> squid3 = initSquid(3);
        gridCells.addAll(squid3);
        List<Squid> squid4 = initSquid(4);
        gridCells.addAll(squid4);

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (!isUsed(row, column)) {
                    gridCells.add(new Water(row, column));
                }
            }
        }
    }

    private static List<Squid> initSquid(int squidLength) {
        Log.i(TAG, "initSquid: " + squidLength);
        Random rnd = new Random();
        int row = rnd.nextInt(8);
        int column = rnd.nextInt(8);
        int direction = rnd.nextInt(4);

        return initSquid(new ArrayList<>(squidLength), squidLength, squidLength, row, column, direction);
    }

    private static List<Squid> initSquid(List<Squid> squid, int squidLength, int restLength, int row, int column, int direction) {
        Log.i(TAG, "initSquid (" + squidLength + ", " + row + ", " + column + ", " + direction + ")");
        if (restLength == 0) {
            Log.i(TAG, "squid successfully created");
            return squid;
        }

        if (row >= 8 || column >= 8 || row < 0 || column < 0 || isUsed(row, column)) {
            Log.i(TAG, "squid failed to create, try again");
            return initSquid(squidLength);
        }
        squid.add(new Squid(row, column));

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

        return initSquid(squid, squidLength, restLength - 1, rowNew, columnNew, direction);
    }

    private static boolean isUsed(int row, int column) {
        Log.i(TAG, "check if grid cell exists (" + row + ", " + column + ")");
        return gridCells.stream()
                .anyMatch(c -> c.isCorrectGrid(row, column));
    }

    public static GridCellState shoot(int row, int column) {
        Log.i(TAG, "shoot grid cell (" + row + ", " + column + ")");
        turnsLeft--;
        Log.i(TAG, "Turns left: " + turnsLeft);
        return gridCells.stream()
                .filter(c -> c.isCorrectGrid(row, column))
                .findAny()
                .filter(GridCell::canBeHit)
                .map(GridCell::hit)
                .orElse(GridCellState.UNKNOWN);
    }

    public static boolean isWin() {
        Log.i(TAG, "isWin");
        return turnsLeft >= 0 && gridCells.stream()
                .noneMatch(c -> c.getCurrentState() == GridCellState.SQUID);
    }

    public static boolean isLoss() {
        Log.i(TAG, "isLoss");
        return turnsLeft <= 0 && gridCells.stream()
                .anyMatch(c -> c.getCurrentState() == GridCellState.SQUID);
    }

    public static void addBomb(BombCellView view) {
        Log.i(TAG, "addBomb: " + view);
        if (bombCellViews == null) {
            bombCellViews = new ArrayList<>();
        }
        bombCellViews.add(view);
        view.setBackgroundResource(R.drawable.bomb_active);
    }

    public static void detonateBomb() {
        Log.i(TAG, "detonateBomb");
        bombCellViews.stream()
                .filter(b -> b.getBombIndex() == (MAX_TURNS - turnsLeft + 1))
                .findAny()
                .ifPresent(b -> b.setBackgroundResource(R.drawable.bomb_nonactive));
    }
}
