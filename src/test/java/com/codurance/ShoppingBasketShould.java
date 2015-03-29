package com.codurance;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingBasketShould {

	private List<Item> someItems;
	private static final Item firstItem = new Item("Item 1");
	private static final Item secondItem = new Item("Item 2");
	private List<String> outOfStockItemsList;
	private ShoppingBasket shoppingBasket;
	@Mock Inventory inventory;

	@Before
	public void initialise() {
		outOfStockItemsList = new ArrayList();
		shoppingBasket = aShoppingBasketWith(someItems);
	}

	@Test
	public void
	use_inventory_to_check_all_items_are_in_stock() {
		given(inventory.has(any(Item.class))).willReturn(true);

		assertThat(shoppingBasket.checkItemsAreInStock(), is(outOfStockItemsList));
	}

	@Test
	public void
	use_inventory_to_check_if_some_items_are_not_in_stock() {
		outOfStockItemsList.add(secondItem.getItemName());

		given(inventory.has(firstItem)).willReturn(true);
		given(inventory.has(secondItem)).willReturn(false);

		assertThat(shoppingBasket.checkItemsAreInStock(), is(outOfStockItemsList));
	}

	private ShoppingBasket aShoppingBasketWith(List<Item> someItems) {
		someItems = asList(firstItem, secondItem);

		ShoppingBasket basket = new ShoppingBasket(inventory);
		for (Item item : someItems) {
			basket.add(item);
		}
		return basket;
	}
}
