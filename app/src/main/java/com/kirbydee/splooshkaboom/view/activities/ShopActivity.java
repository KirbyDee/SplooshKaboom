package com.kirbydee.splooshkaboom.view.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kirbydee.splooshkaboom.R;
import com.kirbydee.splooshkaboom.contract.ShopContract;
import com.kirbydee.splooshkaboom.model.media.Sound;
import com.kirbydee.splooshkaboom.presenter.ShopPresenter;
import com.kirbydee.splooshkaboom.view.layoutviews.dialog.ShopDialogView;
import com.kirbydee.splooshkaboom.view.layoutviews.shop.ShopItemView;
import com.kirbydee.splooshkaboom.view.layoutviews.textbox.ShopTextFormatter;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static com.kirbydee.splooshkaboom.model.media.Sound.BEEDLE_OHH;
import static com.kirbydee.splooshkaboom.model.media.Sound.SHOP_BACKGROUND;
import static com.kirbydee.splooshkaboom.model.media.Video.SHOP_IDLE;
import static com.kirbydee.splooshkaboom.utils.Consts.SHOP_ACTIVITY_BUY_DIALOG_SHOW_DELAY;
import static com.kirbydee.splooshkaboom.utils.Consts.SHOP_ACTIVITY_MAX_BEEDLE_SOUND_DELAY;
import static com.kirbydee.splooshkaboom.utils.Consts.SHOP_ACTIVITY_MIN_BEEDLE_SOUND_DELAY;
import static com.kirbydee.splooshkaboom.utils.PredicateExt.not;
import static com.kirbydee.splooshkaboom.view.layoutviews.textbox.TextSpeed.FAST;

public class ShopActivity extends TextBaseActivity<ShopContract.Presenter> implements ShopContract.View,
        ShopItemView.Listener, ShopDialogView.Listener {

    private static final String TAG = ShopActivity.class.getName();

    private Set<ShopItemView> shopItems;

    private ShopDialogView shopDialogView;

    private Button buyButton;

    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        setContentView(R.layout.shop);
    }

    @Override
    protected void setUpViews() {
        super.setUpViews();
        Log.i(TAG, "setUpViews");

        // buttons
        this.buyButton = findViewById(R.id.shop_item_buy);
        this.cancelButton = findViewById(R.id.shop_item_cancel);

        // set text box configs
        setTextBoxFormatter(new ShopTextFormatter(this.shopItems));
        setTextSpeed(FAST);

        // start background video
        play(SHOP_IDLE);
        this.presenter.onIntro();
        playBeedleOhh(0);
    }

    @Override
    protected int getScreenViewId() {
        Log.i(TAG, "getScreenViewId");
        return R.id.shopScreen;
    }

    @Override
    protected int getTextBoxId() {
        Log.i(TAG, "getTextBoxId");
        return R.id.shopTextView;
    }

    @Override
    protected int getTextBoxNextId() {
        Log.i(TAG, "getTextBoxNextId");
        return R.id.shopTextNext;
    }

    private void playBeedleOhh(long delay) {
        Log.i(TAG, "playBeedleOhh (" + delay + ")");
        play(BEEDLE_OHH, mp -> playBeedleOhh(
                ThreadLocalRandom
                        .current()
                        .nextLong(SHOP_ACTIVITY_MIN_BEEDLE_SOUND_DELAY, SHOP_ACTIVITY_MAX_BEEDLE_SOUND_DELAY)
        ), delay);
    }

    @Override
    protected int getVideoViewId() {
        Log.i(TAG, "getVideoViewId");
        return R.id.shopVideoView;
    }

    @Override
    protected void setUpListeners() {
        super.setUpListeners();
        Log.i(TAG, "setUpListeners");

        // buttons
        this.buyButton.setOnClickListener(this::buyItem);
        this.cancelButton.setOnClickListener(this::cancelBuyItem);
    }

    private void buyItem(View view) {
        Log.i(TAG, "buyItem (" + view + ")");
        this.presenter.onBuy();
        hideBuyDialog();
    }

    private void cancelBuyItem(View view) {
        Log.i(TAG, "cancelBuyItem (" + view + ")");
        this.presenter.onDeselect();
        hideBuyDialog();
    }

    @Override
    protected ShopContract.Presenter getPresenter() {
        Log.i(TAG, "getPresenter");
        return new ShopPresenter(this, getStorage());
    }

    @Override
    protected Sound getBackgroundSound() {
        Log.i(TAG, "getBackgroundSound");
        return SHOP_BACKGROUND;
    }

    @Override
    public void onCreate(ShopItemView view) {
        Log.i(TAG, "onCreate (" + view+ ")");
        if (this.shopItems == null) {
            this.shopItems = new HashSet<>();
        }
        this.shopItems.add(view);
        view.setClickable(false);

        // show itemSold sign if it already has been bought
        Set<Integer> boughtItems = getStorage().getBoughtItemIndexes();
        if (boughtItems.contains(view.getItemIndex())) {
            view.itemSold();
        }
    }

    @Override
    public void enableShopItemsToSelect(boolean enable) {
        Log.i(TAG, "enableShopItemsToSelect (" + enable+ ")");
        this.shopItems
                .stream()
                .filter(not(ShopItemView::isSold))
                .forEach(v -> v.setClickable(enable));
    }

    @Override
    public void onClick(ShopItemView view) {
        Log.i(TAG, "onClick (" + view+ ")");
        this.presenter.onSelect(view);
    }

    @Override
    public void selectItem(ShopItemView view) {
        Log.i(TAG, "selectItem (" + view + ")");
        view.selectItem();
    }

    @Override
    public void deselectItem(ShopItemView view) {
        Log.i(TAG, "deselectItems (" + view + ")");
        view.deselectItem();
    }

    @Override
    public void itemSold(ShopItemView view) {
        Log.i(TAG, "itemSold (" + view + ")");
        view.itemSold();
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
    public void onCreate(ShopDialogView view) {
        Log.i(TAG, "onCreate (" + view + ")");
        this.shopDialogView = view;
    }

    @Override
    public void showBuyDialog(ShopItemView view) {
        Log.i(TAG, "showBuyDialog (" + view + ")");

        // show dialog after some delay
        runAfterDelay(
                () -> this.shopDialogView.fadeIn(),
                SHOP_ACTIVITY_BUY_DIALOG_SHOW_DELAY
        );
    }

    @Override
    public void enableBuyButton(boolean enable) {
        Log.i(TAG, "enableBuyButton (" + enable + ")");
        this.buyButton.setEnabled(enable);
    }

    @Override
    public void hideBuyDialog() {
        Log.i(TAG, "hideBuyDialog");
        this.shopDialogView.fadeOut();
    }
}