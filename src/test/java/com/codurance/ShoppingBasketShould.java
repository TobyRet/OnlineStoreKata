package com.codurance;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingBasketShould {

	private final Item firstItem = new Item("Item 1");
	private final Item secondItem = new Item("Item 2");
	private List<String> outOfStockItemsList;
	private ShoppingBasket shoppingBasket;
	private List<Item> someItems;
	@Mock Inventory inventory;
	@Mock Messenger messenger;
	@Mock Checkout checkout;

	@Before
	public void initialise() {
		someItems = asList(firstItem, secondItem);
		shoppingBasket = aShoppingBasketWith(someItems);
		outOfStockItemsList = new ArrayList();
	}

	@Test
	public void
	proceed_with_payment_if_all_items_are_in_stock() {
		given(inventory.has(any(Item.class))).willReturn(true);

		shoppingBasket.checkItemsAreInStock();

		verify(messenger, never()).informUser(outOfStockItemsList);
		verify(checkout, times(1)).payFor(someItems);
	}

	@Test
	public void
	not_proceed_with_payment_if_all_items_are_not_in_stock() {
		outOfStockItemsList = asList(firstItem.getItemName(), secondItem.getItemName());

		given(inventory.has(firstItem)).willReturn(false);
		given(inventory.has(secondItem)).willReturn(false);

		shoppingBasket.checkItemsAreInStock();

		verify(messenger, times(1)).informUser(outOfStockItemsList);
		verify(checkout, never()).payFor(someItems);
	}

	private ShoppingBasket aShoppingBasketWith(List<Item> someItems) {
		ShoppingBasket basket = new ShoppingBasket(inventory, messenger, checkout);

		for (Item item : someItems) {
			basket.add(item);
		}
		return basket;
	}
}
