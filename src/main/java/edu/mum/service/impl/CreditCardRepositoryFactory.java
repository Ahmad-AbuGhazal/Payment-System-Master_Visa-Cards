package edu.mum.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mum.model.CardType;
import edu.mum.repository.CreditCardRepository;
import edu.mum.repository.MasterRepository;
import edu.mum.repository.VisaRepository;

@Service
@Transactional
public class CreditCardRepositoryFactory {
	
	@Autowired
	private VisaRepository visaRepository;
	
	@Autowired
	private MasterRepository masterRepository;
	
	public CreditCardRepository getCreditCardRepository(CardType cardType){
		if(cardType == CardType.VISA){
			return visaRepository;
		}else if(cardType == CardType.MASTERCARD){
			return masterRepository;
		}else{
			return null;
		}
	}
}
