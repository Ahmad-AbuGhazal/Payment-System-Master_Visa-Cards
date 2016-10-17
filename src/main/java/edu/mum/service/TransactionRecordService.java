package edu.mum.service;

import edu.mum.model.CardType;
import edu.mum.model.TransactionRecord;

public interface TransactionRecordService {
	public TransactionRecord saveCreditCardRecord(CardType cardType, TransactionRecord transactionRecord);
}
