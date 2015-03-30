package com.codurance.ECommerce;

import java.util.ArrayList;
import java.util.List;
import com.codurance.Communications.Messenger;
import com.codurance.Stock.Inventory;
import com.codurance.Stock.Item;

public class ShoppingBasket {
    private List<Item> items = new ArrayList();
    private List<String> outOfStockItems = new ArrayList<String>();
    private Inventory inventory;
    private final Messenger messenger;
    private final Checkout checkout;

    public ShoppingBasket(Inventory inventory, Messenger messenger, Checkout checkout) {
        this.inventory = inventory;
        this.messenger = messenger;
        this.checkout = checkout;
    }

    public void checkItemsAreInStock() {
        listOutOfStockItems();

        if(outOfStockItems.isEmpty()) {
            checkout.payFor(items);
        } else {
            messenger.informUser(outOfStockItems);
        }
    }

    private void listOutOfStockItems() {
        for(Item item : items) {
            if(!inventory.has(item)) {
                outOfStockItems.add(item.getItemName());
            }
        }
    }

    public void add(Item item) {
        items.add(item);
    }
}
