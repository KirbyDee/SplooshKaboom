package com.kirbydee.splooshkaboom.controller;

import android.util.Log;

import com.kirbydee.splooshkaboom.model.gridcell.GridCell;
import com.kirbydee.splooshkaboom.model.gridcell.GridCellState;
import com.kirbydee.splooshkaboom.model.gridcell.Squid;
import com.kirbydee.splooshkaboom.model.gridcell.SquidType;
import com.kirbydee.splooshkaboom.model.gridcell.Water;
import com.kirbydee.splooshkaboom.model.cellview.BombCellView;
import com.kirbydee.splooshkaboom.model.cellview.GridCellView;
import com.kirbydee.splooshkaboom.model.cellview.SquidCellView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.kirbydee.splooshkaboom.model.gridcell.SquidType.SQUID_2;
import static com.kirbydee.splooshkaboom.model.gridcell.SquidType.SQUID_3;
import static com.kirbydee.splooshkaboom.model.gridcell.SquidType.SQUID_4;

public class GameController {

    private static final String TAG = GameController.class.getName();

    private static final int MAX_TURNS = 24;

    private List<GridCell> gridCells;

    private List<BombCellView> bombCellViews;

    private List<SquidCellView> squidCellViews;

    private int turnsLeft;

    public void reset() {
        Log.i(TAG, "reset grid cells");
        gridCells = new ArrayList<>(8*8);
        turnsLeft = MAX_TURNS;

        List<Squid> squid2 = initSquid(SQUID_2);
        gridCells.addAll(squid2);
        List<Squid> squid3 = initSquid(SQUID_3);
        gridCells.addAll(squid3);
        List<Squid> squid4 = initSquid(SQUID_4);
        gridCells.addAll(squid4);

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (!isUsed(row, column)) {
                    gridCells.add(new Water(row, column));
                }
            }
        }
    }

    private List<Squid> initSquid(SquidType squidType) {
        Log.i(TAG, "initSquid: " + squidType);
        Random rnd = new Random();
        int row = rnd.nextInt(8);
        int column = rnd.nextInt(8);
        int direction = rnd.nextInt(4);

        return initSquid(new ArrayList<>(squidType.length), squidType, squidType.length, row, column, direction);
    }

    private List<Squid> initSquid(List<Squid> squid, SquidType squidType, int restLength, int row, int column, int direction) {
        Log.i(TAG, "initSquid (" + squidType + ", " + row + ", " + column + ", " + direction + ")");
        if (restLength == 0) {
            Log.i(TAG, "squid successfully created");
            return squid;
        }

        if (row >= 8 || column >= 8 || row < 0 || column < 0 || isUsed(row, column)) {
            Log.i(TAG, "squid failed to create, try again");
            return initSquid(squidType);
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

        return initSquid(squid, squidType, restLength - 1, rowNew, columnNew, direction);
    }

    private boolean isUsed(int row, int column) {
        Log.i(TAG, "check if grid cell exists (" + row + ", " + column + ")");
        return gridCells.stream()
                .anyMatch(c -> c.isCorrectGrid(row, column));
    }

    public boolean hasBeenShotAlready(GridCellView view) {
        Log.i(TAG, "hasBeenShotAlready (" + view + ")");
        return gridCells.stream()
                .filter(c -> c.isCorrectGrid(view))
                .findAny()
                .filter(GridCell::cannotBeHit)
                .isPresent();
    }

    public GridCellState shoot(GridCellView view) {
        Log.i(TAG, "shoot (" + view + ")");
        turnsLeft--;
        Log.i(TAG, "Turns left: " + turnsLeft);
        return gridCells.stream()
                .filter(c -> c.isCorrectGrid(view))
                .findAny()
                .filter(GridCell::canBeHit)
                .map(GridCell::hit)
                .orElse(GridCellState.UNKNOWN);
    }

    public void checkSquids() {
        Log.i(TAG, "checkSquids");
        for (SquidType squidType : SquidType.values()) {
            if (checkIfSquidIsKaboom(squidType)) {
                detonateSquid(squidType);
            }
        }
    }

    private boolean checkIfSquidIsKaboom(SquidType squidType) {
        Log.i(TAG, "checkIfSquidTypeIsGone (" + squidType + ")");
        return gridCells.stream()
                .filter(c -> c instanceof Squid)
                .map(c -> (Squid) c)
                .filter(s -> s.getTpe() == squidType)
                .allMatch(s -> s.getCurrentState() == GridCellState.KABOOM);
    }

    public boolean isWin() {
        Log.i(TAG, "isWin");
        return turnsLeft >= 0 && gridCells.stream()
                .noneMatch(c -> c.getCurrentState() == GridCellState.SQUID);
    }

    public boolean isLoss() {
        Log.i(TAG, "isLoss");
        return turnsLeft <= 0 && gridCells.stream()
                .anyMatch(c -> c.getCurrentState() == GridCellState.SQUID);
    }

    public boolean gameFinished() {
        Log.i(TAG, "gameFinished");
        return turnsLeft <= 0;
    }

    public void addBomb(BombCellView view) {
        Log.i(TAG, "addBomb: " + view);
        if (bombCellViews == null) {
            bombCellViews = new ArrayList<>();
        }
        bombCellViews.add(view);
        view.enable();
    }

    public void detonateBomb() {
        Log.i(TAG, "detonateBomb");
        bombCellViews.stream()
                .filter(b -> b.getBombIndex() == (MAX_TURNS - turnsLeft - 1))
                .findAny()
                .ifPresent(BombCellView::disable);
    }

    public void addSquid(SquidCellView view) {
        Log.i(TAG, "addSquid: " + view);
        if (squidCellViews == null) {
            squidCellViews = new ArrayList<>();
        }
        squidCellViews.add(view);
        view.enable();
    }

    private void detonateSquid(SquidType squidType) {
        Log.i(TAG, "detonateSquid");
        squidCellViews.stream()
                .filter(b -> b.getSquidSize() == squidType.length)
                .findAny()
                .ifPresent(SquidCellView::disable);
    }
}
