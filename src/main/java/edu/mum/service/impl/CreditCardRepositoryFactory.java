package edu.mum.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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
	
	public CreditCardRepository getCreditCardRepository(String cardType){
		if(cardType.equals("visa")){
			return visaRepository;
		}else if(cardType.equals("master")){
			return masterRepository;
		}else{
			return null;
		}
	}
}
