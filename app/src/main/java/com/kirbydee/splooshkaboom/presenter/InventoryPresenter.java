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
        this.view.showInventoryItem(InventoryItem.of(view.getId()));
    }

    @Override
    public void deselect() {
        Log.i(TAG, "deselect");
        this.selectedView = null;
        this.view.deselect(this.selectedView);
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
