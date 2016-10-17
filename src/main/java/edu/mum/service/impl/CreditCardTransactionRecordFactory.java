package edu.mum.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import edu.mum.model.CardType;
import edu.mum.model.MasterTransactionRecord;
import edu.mum.model.TransactionRecord;
import edu.mum.model.VisaTransactionRecord;

@Service
@Transactional
public class CreditCardTransactionRecordFactory {
	
	public TransactionRecord getTransactionRecord(CardType cardType){
		if(cardType == CardType.VISA){
			return new VisaTransactionRecord();
		}else if(cardType == CardType.MASTERCARD){
			return new MasterTransactionRecord();
		}else{
			return null;
		}
	}
}
