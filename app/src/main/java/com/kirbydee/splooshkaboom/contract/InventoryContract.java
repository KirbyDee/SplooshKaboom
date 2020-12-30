package com.kirbydee.splooshkaboom.contract;

import com.kirbydee.splooshkaboom.model.inventory.InventoryItem;
import com.kirbydee.splooshkaboom.view.layoutviews.inventory.InventoryView;

public class InventoryContract {

    public interface Presenter {

        void select(InventoryView view);

        void deselect();

        void onBackPressed();
    }

    public interface View {

        void showInventoryItem(InventoryItem item);

        void deselect(InventoryView view);

        void backToMenu();
    }
}
