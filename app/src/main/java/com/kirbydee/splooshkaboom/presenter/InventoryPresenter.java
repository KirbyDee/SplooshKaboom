package com.kirbydee.splooshkaboom.presenter;

import android.util.Log;

import com.kirbydee.splooshkaboom.contract.InventoryContract;
import com.kirbydee.splooshkaboom.model.inventory.InventoryItem;
import com.kirbydee.splooshkaboom.view.layoutviews.inventory.InventoryView;

public class InventoryPresenter implements InventoryContract.Presenter {

    private static final String TAG = InventoryPresenter.class.getName();

    // view
    private final InventoryContract.View view;

    // selected view
    private InventoryView selectedView;

    public InventoryPresenter(InventoryContract.View view) {
        this.view = view;
    }

    @Override
    public void select(InventoryView view) {
        Log.i(TAG, "onSelected (" + view + ")");
        if (this.selectedView != null) {
            deselect();
        }
        this.selectedView = view;

        // show inventory item
        InventoryItem item = InventoryItem.of(view.getItemIndex());
        this.view.select(this.selectedView);
        switch(item) {
            case HEART_PIECE:
                this.view.showHeartPiece(true);
                this.view.showTreasureMap(false);
                this.view.showTriforce(false);
                break;
            case TREASURE_MAP:
                this.view.showHeartPiece(false);
                this.view.showTreasureMap(true);
                this.view.showTriforce(false);
                break;
            case TRIFORCE:
                this.view.showHeartPiece(false);
                this.view.showTreasureMap(false);
                this.view.showTriforce(true);
                break;
        }
    }

    @Override
    public void deselect() {
        Log.i(TAG, "deselect");
        this.view.deselect(this.selectedView);
        this.selectedView = null;
        this.view.showHeartPiece(false);
        this.view.showTreasureMap(false);
        this.view.showTriforce(false);
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed");
        if (this.selectedView != null) {
            deselect();
        }
        else {
            this.view.backToMenu();
        }
    }
}
