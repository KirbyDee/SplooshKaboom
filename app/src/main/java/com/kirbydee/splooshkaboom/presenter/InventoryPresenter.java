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
        Log.i(TAG, "select (" + view + ")");
        if (this.selectedView != null) {
            boolean isSame = this.selectedView.getItemIndex() == view.getItemIndex();
            deselect();
            if (isSame) {
                return;
            }
        }

        this.selectedView = view;
        showView(true);
        this.view.select(this.selectedView);
    }

    @Override
    public void deselect() {
        Log.i(TAG, "deselect");
        if (this.selectedView == null) {
            return;
        }

        this.view.deselect(this.selectedView);
        showView(false);
        this.selectedView = null;
    }

    private void showView(boolean show) {
        Log.i(TAG, "showView (" + show + ")");
        InventoryItem item = InventoryItem.of(this.selectedView.getItemIndex());
        switch(item) {
            case HEART_PIECE:
                this.view.showHeartPiece(show);
                break;
            case TREASURE_MAP:
                this.view.showTreasureMap(show);
                break;
            case TRIFORCE:
                this.view.showTriforce(show);
                break;
        }
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
