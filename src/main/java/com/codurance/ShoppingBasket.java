package com.codurance;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasket {
    private List<Item> items = new ArrayList();
    private Inventory inventory;

    public ShoppingBasket(Inventory inventory) {
        this.inventory = inventory;
    }

    public List<String> checkItemsAreInStock() {
        List<String> outOfStockItems = new ArrayList();
        for(Item item : items) {
            if(!inventory.has(item)) {
                outOfStockItems.add(item.getItemName());
            }
        }
        return outOfStockItems;
    }

    public void add(Item item) {
        items.add(item);
    }
}
