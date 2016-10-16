package edu.mum.service.impl;

import org.springframework.stereotype.Service;

import edu.mum.model.MasterTransactionRecord;
import edu.mum.model.TransactionRecord;
import edu.mum.model.VisaTransactionRecord;

@Service
public class CreditCardTransactionRecordFactory {
	
	public TransactionRecord getTransactionRecord(String cardType){
		if(cardType.equals("visa")){
			return new VisaTransactionRecord();
		}else if(cardType.equals("master")){
			return new MasterTransactionRecord();
		}else{
			return null;
		}
	}
}
