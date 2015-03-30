package com.codurance.ECommerce;

import java.util.Arrays;
import java.util.List;
import com.codurance.Communications.Emailer;
import com.codurance.Stock.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutShould {
	private static final String PAYMENT_SUCCESSFUL = "Payment successful";
	private static final String CREDIT_CHECK_FAILED = "User failed credit check";
	public static final String ITEM1_NAME = "Item 1";
	public static final String ITEM2_NAME = "Item 2";
	public static final Integer ITEM1_PRICE = 10;
	public static final Integer ITEM2_PRICE = 30;
	private Checkout checkout;
	private List<Item> someItems;
	@Mock PaymentGateway paymentGateway;
	@Mock Emailer emailer;

	@Before
	public void initialise() {
		checkout = new Checkout(paymentGateway, emailer);
		someItems = createSomeItems();
	}

	@Test
	public void
	send_confirmation_email_to_user_if_payment_is_successful() {
		given(paymentGateway.process(any(Payment.class))).willReturn(PAYMENT_SUCCESSFUL);

		checkout.payFor(someItems);

		verify(emailer).send(PAYMENT_SUCCESSFUL);
	}

	@Test public void
	receive_error_message_if_payment_is_unsuccessful() {
		given(paymentGateway.process(any(Payment.class))).willReturn(CREDIT_CHECK_FAILED);

		checkout.payFor(someItems);

		verify(emailer, never()).send(PAYMENT_SUCCESSFUL);
	}

	private List<Item> createSomeItems() {
		Item item1 = new Item(ITEM1_NAME, ITEM1_PRICE);
		Item item2 = new Item(ITEM2_NAME, ITEM2_PRICE);
		return Arrays.asList(item1, item2);
	}
}
