package com.codurance.ECommerce;

import java.util.List;
import com.codurance.Communications.Emailer;
import com.codurance.Stock.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutShould {
	private static final String PAYMENT_SUCCESSFUL_STATUS = "Payment successful";
	public static final String ITEM1_NAME = "Item 1";
	public static final String ITEM2_NAME = "Item 2";
	public static final Integer ITEM1_PRICE = 10;
	public static final Integer ITEM2_PRICE = 30;
	@Mock PaymentGateway paymentGateway;
	@Mock Emailer emailer;

	@Test
	public void
	send_confirmation_email_to_user_if_payment_is_successful() {
		Checkout checkout = new Checkout(paymentGateway, emailer);

		Item item1 = new Item(ITEM1_NAME, ITEM1_PRICE);
		Item item2 = new Item(ITEM2_NAME, ITEM2_PRICE);
		List<Item> someItems = asList(item1, item2);

		given(paymentGateway.process(any(Payment.class))).willReturn(PAYMENT_SUCCESSFUL_STATUS);

		checkout.payFor(someItems);

		verify(emailer).send(PAYMENT_SUCCESSFUL_STATUS);
	}

}
