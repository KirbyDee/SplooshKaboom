package com.kirbydee.splooshkaboom.contract;

import com.kirbydee.splooshkaboom.view.layoutviews.shopitem.ShopItemView;

public class ShopContract {

    public interface Presenter extends TextContract.Presenter {

        void onIntro();

        void onBackPressed();

        void onSelect(ShopItemView view);

        void onDeselect();

        void onBuy();
    }

    public interface View extends TextContract.View {

        void backToMenu();

        void selectItem(ShopItemView view);

        void deselectItem(ShopItemView view);

        void itemSold(ShopItemView view);

        default void enableShopItemsToSelect() {
            enableShopItemsToSelect(true);
        }

        void enableShopItemsToSelect(boolean enable);

        void showBuyDialog(ShopItemView view);

        void hideBuyDialog();

        default void enableBuyButton() {
            enableBuyButton(true);
        }

        void enableBuyButton(boolean enable);
    }
}
