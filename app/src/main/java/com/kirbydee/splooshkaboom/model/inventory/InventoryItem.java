package com.kirbydee.splooshkaboom.model.inventory;

import java.util.Arrays;

public enum InventoryItem {

    HEART_PIECE(0, ""),
    TREASURE_MAP(1, "treasure_map_open"),
    TRIFORCE(2, "");

    public final int id;

    public final String res;

    public static InventoryItem of(int id) {
        return Arrays.stream(InventoryItem.values())
                .filter(item -> item.id == id)
                .findAny()
                .orElse(null);
    }

    InventoryItem(int id, String res) {
        this.id = id;
        this.res = res;
    }
}
