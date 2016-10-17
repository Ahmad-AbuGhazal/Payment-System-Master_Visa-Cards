package edu.mum.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mum.model.CardType;
import edu.mum.repository.CreditCardTransactionRecordRepository;
import edu.mum.repository.MasterTransactionRecordRepository;
import edu.mum.repository.VisaTransactionRecordRepository;

@Service
@Transactional
public class CreditCardTransactionRecordRepositoryFactory {

	@Autowired
	private VisaTransactionRecordRepository visaTransactionRecordRepository;
	
	@Autowired
	private MasterTransactionRecordRepository masterTransactionRecordRepository;
	
	public CreditCardTransactionRecordRepository getCreditCardTransactionRecordRepository(CardType cardType){
		if(cardType == CardType.VISA){
			return visaTransactionRecordRepository;
		}else if(cardType == CardType.MASTERCARD){
			return masterTransactionRecordRepository;
		}else{
			return null;
		}
	}
}
