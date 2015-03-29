package com.codurance;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasket {
    private List<Item> items = new ArrayList();
    private Inventory inventory;
    private final Messenger messenger;

    public ShoppingBasket(Inventory inventory, Messenger messenger) {
        this.inventory = inventory;
        this.messenger = messenger;
    }

    public void checkItemsAreInStock() {
        List<String> outOfStockItems = new ArrayList();

        for(Item item : items) {
            if(!inventory.has(item)) {
                outOfStockItems.add(item.getItemName());
            }
        }

        informUser(outOfStockItems);
    }

    public void add(Item item) {
        items.add(item);
    }

    private void informUser(List<String> outOfStockItems) {
        if(!outOfStockItems.isEmpty()) {
            messenger.informUser(outOfStockItems);
        }
    }
}
