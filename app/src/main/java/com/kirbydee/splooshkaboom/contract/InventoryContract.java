package com.kirbydee.splooshkaboom.contract;

import com.kirbydee.splooshkaboom.view.layoutviews.inventory.InventoryView;

public class InventoryContract {

    public interface Presenter {

        void select(InventoryView view);

        void deselect();

        void onBackPressed();
    }

    public interface View {

        void showTreasureMap(boolean show);

        void showHeartPiece(boolean show);

        void showTriforce(boolean show);

        void select(InventoryView view);

        void deselect(InventoryView view);

        void backToMenu();
    }
}
