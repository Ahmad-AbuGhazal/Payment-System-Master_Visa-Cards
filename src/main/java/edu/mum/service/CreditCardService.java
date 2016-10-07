package edu.mum.service;

import edu.mum.model.RequestedCard;

public interface CreditCardService {
	char verifyCreditCard(RequestedCard requestedCard);
	char afterPlaceOrder(RequestedCard requestedCard);
}
