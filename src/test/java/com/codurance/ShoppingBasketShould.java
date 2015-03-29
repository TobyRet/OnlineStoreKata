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

	private static final Item firstItem = new Item("Item 1");
	private static final Item secondItem = new Item("Item 2");
	private List<String> outOfStockItemsList;
	private ShoppingBasket shoppingBasket;
	private List<Item> someItems;
	@Mock Inventory inventory;
	@Mock Messenger messenger;

	@Before
	public void initialise() {
		outOfStockItemsList = new ArrayList();
		shoppingBasket = aShoppingBasketWith(someItems);
	}

	@Test
	public void
	use_inventory_to_check_all_items_are_in_stock() {
		given(inventory.has(any(Item.class))).willReturn(true);

		shoppingBasket.checkItemsAreInStock();

		verify(messenger, never()).informUser(outOfStockItemsList);
	}

	@Test
	public void
	use_inventory_to_check_if_some_items_are_not_in_stock() {
		outOfStockItemsList.add(secondItem.getItemName());

		given(inventory.has(firstItem)).willReturn(true);
		given(inventory.has(secondItem)).willReturn(false);

		shoppingBasket.checkItemsAreInStock();

		verify(messenger, times(1)).informUser(outOfStockItemsList);
	}

	private ShoppingBasket aShoppingBasketWith(List<Item> someItems) {
		someItems = asList(firstItem, secondItem);

		ShoppingBasket basket = new ShoppingBasket(inventory, messenger);

		for (Item item : someItems) {
			basket.add(item);
		}
		return basket;
	}
}
