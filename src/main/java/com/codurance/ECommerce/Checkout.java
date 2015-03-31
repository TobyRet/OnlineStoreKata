package com.codurance.ECommerce;

import java.util.List;
import com.codurance.Communications.Emailer;
import com.codurance.Stock.Item;

public class Checkout {

	public static final String PAYMENT_SUCCESSFUL = "Payment successful";
	private final PaymentGateway paymentGateway;
	private final Emailer emailer;

	public Checkout(PaymentGateway paymentGateway, Emailer emailer) {
		this.paymentGateway = paymentGateway;
		this.emailer = emailer;
	}

	public String payFor(List<Item> items) {
		Payment payment = total(items);
		String paymentGateWayMessage = paymentGateway.process(payment);

		if (paymentGateWayMessage.equals(PAYMENT_SUCCESSFUL)) {
			emailer.send(PAYMENT_SUCCESSFUL);
		}
		return paymentGateWayMessage;
	}

	private Payment total(List<Item> items) {
		Integer itemTotal = 0;

		for (Item item : items) {
			itemTotal = itemTotal + item.price();
		}

		return new Payment(itemTotal);
	}
}
