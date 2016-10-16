package edu.mum.repository;

import java.util.List;

import edu.mum.model.CreditCard;

public interface CreditCardRepository {
	List<CreditCard> findByCardNum(String cardnum);
	CreditCard save(CreditCard creditCard);
}
