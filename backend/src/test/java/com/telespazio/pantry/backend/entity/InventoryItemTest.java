package com.telespazio.pantry.backend.entity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class InventoryItemTest {

    @Test
    void isLowStock_whenQuantityBelowThreshold_returnsTrue() {
        InventoryItem item = new InventoryItem("Rice", 5, 10);

        assertTrue(item.isLowStock());
    }

    @Test
    void isLowStock_whenQuantityEqualsThreshold_returnsFalse() {
        InventoryItem item = new InventoryItem("Rice", 10, 10);

        assertFalse(item.isLowStock());
    }

    @Test
    void isLowStock_whenQuantityAboveThreshold_returnsFalse() {
        InventoryItem item = new InventoryItem("Rice", 15, 10);

        assertFalse(item.isLowStock());
    }
}
