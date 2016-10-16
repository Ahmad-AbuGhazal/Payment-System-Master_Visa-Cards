package edu.mum.service;

import edu.mum.model.TransactionRecord;

public interface TransactionRecordService {
	public TransactionRecord saveCreditCardRecord(String cardType, TransactionRecord transactionRecord);
}
