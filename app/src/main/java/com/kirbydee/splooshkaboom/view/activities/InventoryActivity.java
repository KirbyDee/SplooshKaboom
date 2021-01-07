package com.kirbydee.splooshkaboom.view.activities;

import android.os.Bundle;
import android.util.Log;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.contract.InventoryContract;
import com.kirbydee.splooshkaboom.model.media.Sound;
import com.kirbydee.splooshkaboom.presenter.InventoryPresenter;
import com.kirbydee.splooshkaboom.view.layoutviews.inventory.InventoryItemView;
import com.kirbydee.splooshkaboom.view.layoutviews.inventory.InventoryView;
import com.kirbydee.splooshkaboom.view.layoutviews.inventory.display.HeartPieceView;
import com.kirbydee.splooshkaboom.view.layoutviews.inventory.display.InventoryDisplayBackgroundView;
import com.kirbydee.splooshkaboom.view.layoutviews.inventory.display.TreasureView;
import com.kirbydee.splooshkaboom.view.layoutviews.inventory.display.TriforceView;

import java.util.Set;

import static com.kirbydee.splooshkaboom.model.media.Sound.INVENTORY_BACKGROUND;

public class InventoryActivity extends MediaBaseActivity implements InventoryContract.View,
        InventoryView.Listener, InventoryItemView.Listener, InventoryDisplayBackgroundView.Listener,
        HeartPieceView.Listener, TreasureView.Listener, TriforceView.Listener {

    private static final String TAG = InventoryActivity.class.getName();

    // presenter
    private InventoryContract.Presenter presenter;

    // inventory display background view
    private InventoryDisplayBackgroundView inventoryDisplayBackgroundView;

    // item views
    private HeartPieceView heartPieceView;
    private TreasureView treasureView;
    private TriforceView triforceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        setContentView(R.layout.inventory);
    }

    @Override
    protected Sound getBackgroundSound() {
        return INVENTORY_BACKGROUND;
    }

    @Override
    protected void init() {
        super.init();
        Log.i(TAG, "init");

        this.presenter = new InventoryPresenter(this);
    }

    @Override
    public void onCreate(InventoryItemView view) {
        Log.i(TAG, "onCreate (" + view + ")");

        // either show or hide the item
        Set<Integer> boughtItems = getStorage().getBoughtItemIndexes();
        view.showItem(boughtItems.contains(view.getItemIndex()));
    }

    @Override
    public void onCreate(InventoryView view) {
        Log.i(TAG, "onCreate (" + view + ")");

        // either make the view clickable or not
        Set<Integer> boughtItems = getStorage().getBoughtItemIndexes();
        view.setClickable(boughtItems.contains(view.getItemIndex()));
    }

    @Override
    public void onSelected(InventoryView view) {
        Log.i(TAG, "onSelected (" + view + ")");
        this.presenter.select(view);
    }

    @Override
    public void showTreasureMap(boolean show) {
        Log.i(TAG, "showTreasureMap");
        this.treasureView.show(show);
    }

    @Override
    public void showHeartPiece(boolean show) {
        Log.i(TAG, "showHeartPiece");
        this.heartPieceView.show(show);
    }

    @Override
    public void showTriforce(boolean show) {
        Log.i(TAG, "showTriforce");
        this.triforceView.show(show);
    }

    @Override
    public void select(InventoryView view) {
        Log.i(TAG, "select (" + view + ")");
        view.select();
        this.inventoryDisplayBackgroundView.unShow();
    }

    @Override
    public void deselect(InventoryView view) {
        Log.i(TAG, "deselect (" + view + ")");
        view.deselect();
        this.inventoryDisplayBackgroundView.show();
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed");
        this.presenter.onBackPressed();
    }

    @Override
    public void backToMenu() {
        Log.i(TAG, "backToMenu");
        changeActivity(MenuActivity.class);
    }

    @Override
    public void onCreate(TreasureView view) {
        Log.i(TAG, "onCreate (" + view + ")");
        this.treasureView = view;
        this.treasureView.unShow();
    }

    @Override
    public void onCreate(HeartPieceView view) {
        Log.i(TAG, "onCreate (" + view + ")");
        this.heartPieceView = view;
        this.heartPieceView.unShow();
    }

    @Override
    public void onCreate(TriforceView view) {
        Log.i(TAG, "onCreate (" + view + ")");
        this.triforceView = view;
        this.triforceView.unShow();
    }

    @Override
    public void onCreate(InventoryDisplayBackgroundView view) {
        Log.i(TAG, "onCreate (" + view + ")");
        this.inventoryDisplayBackgroundView = view;
        this.inventoryDisplayBackgroundView.show();
    }
}