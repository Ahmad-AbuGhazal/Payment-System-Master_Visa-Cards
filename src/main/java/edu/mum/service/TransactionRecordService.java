package edu.mum.service;

import edu.mum.model.MasterTransactionRecord;
import edu.mum.model.TransactionRecord;
import edu.mum.model.VisaTransactionRecord;

public interface TransactionRecordService {
	TransactionRecord saveVisaRecord(VisaTransactionRecord visaTransactionRecord);
	TransactionRecord saveMasterRecord(MasterTransactionRecord masterTransactionRecord);
}
