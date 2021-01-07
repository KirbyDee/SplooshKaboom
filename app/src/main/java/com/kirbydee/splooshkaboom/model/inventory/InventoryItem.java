package com.kirbydee.splooshkaboom.model.inventory;

import java.util.Arrays;

public enum InventoryItem {

    HEART_PIECE(1),
    TREASURE_MAP(2),
    TRIFORCE(3);

    public final int id;

    public static InventoryItem of(int id) {
        return Arrays.stream(InventoryItem.values())
                .filter(item -> item.id == id)
                .findAny()
                .orElse(null);
    }

    InventoryItem(int id) {
        this.id = id;
    }
}
