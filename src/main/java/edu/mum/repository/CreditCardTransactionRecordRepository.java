package edu.mum.repository;

import java.util.List;

import edu.mum.model.TransactionRecord;

public interface CreditCardTransactionRecordRepository {
	List<TransactionRecord> findAllTransactionRecordRepository();
}
