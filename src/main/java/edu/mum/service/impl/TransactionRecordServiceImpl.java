package edu.mum.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mum.model.MasterTransactionRecord;
import edu.mum.model.TransactionRecord;
import edu.mum.model.VisaTransactionRecord;
import edu.mum.repository.MasterTransactionRecordRepository;
import edu.mum.repository.VisaTransactionRecordRepository;
import edu.mum.service.TransactionRecordService;

@Service
@Transactional
public class TransactionRecordServiceImpl implements TransactionRecordService {
	
	@Autowired
	private VisaTransactionRecordRepository visaTransactionRecordRepository;
	
	@Autowired
	private MasterTransactionRecordRepository masterTransactionRecordRepository;
	
	public TransactionRecord saveVisaRecord(VisaTransactionRecord visaTransactionRecord){
		return visaTransactionRecordRepository.save(visaTransactionRecord);
	}
	
	public TransactionRecord saveMasterRecord(MasterTransactionRecord masterTransactionRecord){
		return masterTransactionRecordRepository.save(masterTransactionRecord);
	}
}
