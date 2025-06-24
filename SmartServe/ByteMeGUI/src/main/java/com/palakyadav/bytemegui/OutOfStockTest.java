package com.palakyadav.bytemegui;

import org.junit.Test;
import static org.junit.Assert.*;

public class OutOfStockTest {

    @Test
    public void testOutOfStockItem() {
        Item outOfStockItem = new Item(555, "Out of Stock Item", "snacks", 30, false);
        Admin.getMenu().put(outOfStockItem.getId(), outOfStockItem);

        boolean itemExists = Admin.getMenu().containsKey(outOfStockItem.getId());
        assertTrue("Item should exist in the menu", itemExists);

        Item retrievedItem = Admin.getMenu().get(outOfStockItem.getId());
        assertFalse("Item should be out of stock", retrievedItem.getAvailability());
    }

    @Test
    public void testAvailableItem() {
        Item availableItem = new Item(556, "Available Item", "snacks", 50, true);
        Admin.getMenu().put(availableItem.getId(), availableItem);

        boolean itemExists = Admin.getMenu().containsKey(availableItem.getId());
        assertTrue("Item should exist in the menu", itemExists);

        Item retrievedItem = Admin.getMenu().get(availableItem.getId());
        assertTrue("Item should be available", retrievedItem.getAvailability());
    }

    @Test
    public void testOutOfStockWhenUpdated() {
        Item item = new Item(557, "Update Availability Item", "beverages", 20, true);
        Admin.getMenu().put(item.getId(), item);

        Item retrievedItem = Admin.getMenu().get(item.getId());
        assertTrue("Item should be available initially", retrievedItem.getAvailability());

        retrievedItem.setAvailability(false);

        assertFalse("Item should now be out of stock", retrievedItem.getAvailability());
    }
}

