package com.kirbydee.splooshkaboom.presenter;

import android.util.Log;

import com.kirbydee.splooshkaboom.contract.ShopContract;
import com.kirbydee.splooshkaboom.model.counter.Rupees;
import com.kirbydee.splooshkaboom.model.textstate.ShopState;
import com.kirbydee.splooshkaboom.utils.Storage;
import com.kirbydee.splooshkaboom.view.layoutviews.shop.ShopItemView;

import java.util.Set;

import static com.kirbydee.splooshkaboom.model.media.Sound.BEEDLE_BYE;
import static com.kirbydee.splooshkaboom.model.media.Sound.BEEDLE_THANKYOU;
import static com.kirbydee.splooshkaboom.model.media.Video.SHOP_BYE;
import static com.kirbydee.splooshkaboom.model.textstate.ShopState.BROWSE_ITEM_1;
import static com.kirbydee.splooshkaboom.model.textstate.ShopState.BROWSE_ITEM_2;
import static com.kirbydee.splooshkaboom.model.textstate.ShopState.BROWSE_ITEM_3;
import static com.kirbydee.splooshkaboom.model.textstate.ShopState.BUY_ITEM_1;
import static com.kirbydee.splooshkaboom.model.textstate.ShopState.BUY_ITEM_2;
import static com.kirbydee.splooshkaboom.model.textstate.ShopState.BUY_ITEM_3;
import static com.kirbydee.splooshkaboom.model.textstate.ShopState.BYE;
import static com.kirbydee.splooshkaboom.model.textstate.ShopState.IDLE;
import static com.kirbydee.splooshkaboom.model.textstate.ShopState.INTRO_1;
import static com.kirbydee.splooshkaboom.model.textstate.ShopState.INTRO_2;

public class ShopPresenter extends TextPresenter<ShopContract.View, ShopState> implements ShopContract.Presenter {

    private static final String TAG = ShopPresenter.class.getName();

    private ShopItemView selectedItem;

    public ShopPresenter(ShopContract.View view, Storage storage) {
        super(view, storage);
    }

    @Override
    public void onIntro() {
        Log.i(TAG, "onIntro");
        setState(INTRO_1);
        showText();
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed");
        if (this.selectedItem != null) {
            onDeselect();
        }
        else if (getState() != BYE) {
            bye();
        }
    }

    private void bye() {
        Log.i(TAG, "bye");
        setState(BYE);
        this.view.playVideo(SHOP_BYE);
        this.view.playSound(BEEDLE_BYE);
        showText();
    }

    @Override
    public void onDeselect() {
        Log.i(TAG, "onDeselect");
        this.view.deselectItem(this.selectedItem);
        this.selectedItem = null;
        this.view.hideBuyDialog();
        onIdle();
    }

    @Override
    public void onSelect(ShopItemView view) {
        Log.i(TAG, "onSelect (" + view + ")");
        this.selectedItem = view;

        ShopState state;
        switch (view.getItemIndex()) {
            case 1:
                state = BROWSE_ITEM_1;
                break;
            case 2:
                state = BROWSE_ITEM_2;
                break;
            case 3:
                state = BROWSE_ITEM_3;
                break;
            default:
                onIdle();
                return;
        }
        setState(state);
        showText();
        this.view.selectItem(this.selectedItem);
        this.view.enableShopItemsToSelect(false);
        this.view.forceFinishText();
    }

    @Override
    public void onBuy() {
        Log.i(TAG, "onBuy");

        ShopState state;
        if (this.selectedItem == null) {
            // something went wrong..
            state = IDLE;
        }
        else {
            switch (this.selectedItem.getItemIndex()) {
                case 1:
                    state = BUY_ITEM_1;
                    break;
                case 2:
                    state = BUY_ITEM_2;
                    break;
                case 3:
                    state = BUY_ITEM_3;
                    break;
                default:
                    state = IDLE;
            }
        }
        if (state == IDLE) {
            onIdle();
            return;
        }

        // exchange for rupees
        decreaseRupees(
                Rupees.of(this.selectedItem.getRupees())
        );

        // store what has been bought
        Set<Integer> boughtItems = this.storage.getBoughtItemIndexes();
        boughtItems.add(this.selectedItem.getItemIndex());
        this.storage.storeBoughtItemIndexes(boughtItems);

        // set view
        setState(state);
        showText();
        this.view.playSound(BEEDLE_THANKYOU);
        this.view.forceFinishText();
    }

    @Override
    public void onClickScreen() {
        Log.i(TAG, "onClickScreen");
        switch (getState()) {
            case INTRO_1:
                onIntro1Click();
                break;
            case INTRO_2:
                onIntro2Click();
                break;
            case BROWSE_ITEM_1:
            case BROWSE_ITEM_2:
            case BROWSE_ITEM_3:
                onBrowseItem();
                break;
            case BUY_ITEM_1:
            case BUY_ITEM_2:
            case BUY_ITEM_3:
                onBuyFinished();
                break;
            case BYE:
                onBye();
                break;
            default:
                // do nothing
                break;
        }
    }

    @Override
    public void onTextFinished() {
        Log.i(TAG, "onTextFinished");
        switch (getState()) {
            case IDLE:
                break;
            case BROWSE_ITEM_1:
            case BROWSE_ITEM_2:
            case BROWSE_ITEM_3:
                super.onTextFinished();
                onBrowseItem();
                break;
            case BYE:
                onBye();
                break;
            default:
                super.onTextFinished();
                break;
        }
    }

    private void onIntro1Click() {
        Log.i(TAG, "onIntro1Click");
        onClickForceTextToFinish(() -> {
            setState(INTRO_2);
            showText();
        });
    }

    private void onIntro2Click() {
        Log.i(TAG, "onIntro2Click");
        onClickForceTextToFinish(this::onIdle);
    }

    private void onBrowseItem() {
        Log.i(TAG, "onBrowseItem");
        onClickForceTextToFinish(() -> {
            this.view.enableScreenClick(false);
            this.view.showBuyDialog(this.selectedItem);

            // check if buy-able
            Rupees rupees = this.storage.getRupees();
            this.view.enableBuyButton(
                    !rupees.isLessThan(
                            Rupees.of(this.selectedItem.getRupees())
                    )
            );
        });
    }

    private void onBye() {
        Log.i(TAG, "onBye");
        onClickForceTextToFinish(() -> {
            this.view.enableScreenClick(false);
            this.view.backToMenu();
        });
    }

    private void onBuyFinished() {
        Log.i(TAG, "onBuyFinished");
        this.view.itemSold(this.selectedItem);
        this.selectedItem = null;
        onIdle();
    }

    private void onIdle() {
        Log.i(TAG, "onIdle");
        setState(IDLE);
        showText();

        // special here, idle state cannot click the screen anymore also text is shown immediately
        this.view.enableScreenClick(false);
        this.view.forceFinishText();
        this.view.enableShopItemsToSelect();
        this.view.showTextBoxNext(false);
    }
}
