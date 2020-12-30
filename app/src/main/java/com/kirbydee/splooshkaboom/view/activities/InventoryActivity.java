package com.kirbydee.splooshkaboom.view.activities;

import android.os.Bundle;
import android.util.Log;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.contract.InventoryContract;
import com.kirbydee.splooshkaboom.model.inventory.InventoryItem;
import com.kirbydee.splooshkaboom.model.media.Sound;
import com.kirbydee.splooshkaboom.presenter.InventoryPresenter;
import com.kirbydee.splooshkaboom.view.layoutviews.inventory.InventoryDisplayView;
import com.kirbydee.splooshkaboom.view.layoutviews.inventory.InventoryItemView;
import com.kirbydee.splooshkaboom.view.layoutviews.inventory.InventoryView;

import java.util.Set;

import static com.kirbydee.splooshkaboom.model.media.Sound.INVENTORY_BACKGROUND;

public class InventoryActivity extends MediaBaseActivity implements InventoryContract.View,
        InventoryView.Listener, InventoryItemView.Listener, InventoryDisplayView.Listener {

    private static final String TAG = InventoryActivity.class.getName();

    private InventoryPresenter presenter;

    private InventoryDisplayView displayView;

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
    public void onCreate(InventoryDisplayView view) {
        Log.i(TAG, "onCreate (" + view + ")");
        this.displayView = view;
    }

    @Override
    public void onSelected(InventoryView view) {
        Log.i(TAG, "onSelected (" + view + ")");
        this.presenter.select(view);
    }

    @Override
    public void onDeselected(InventoryView view) {
        Log.i(TAG, "onDeselected (" + view + ")");
        this.presenter.deselect();
    }

    @Override
    public void showInventoryItem(InventoryItem item) {
        Log.i(TAG, "showInventoryItem (" + item + ")");
        this.displayView.setInventoryItem(item);
    }

    @Override
    public void deselect(InventoryView view) {
        Log.i(TAG, "deselect (" + view + ")");
        view.deselect();
        this.displayView.resetInventoryItem();
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
}