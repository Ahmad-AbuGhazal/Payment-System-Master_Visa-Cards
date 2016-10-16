package edu.mum.service.impl;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mum.model.CreditCard;
import edu.mum.model.MasterTransactionRecord;
import edu.mum.model.RequestedCard;
import edu.mum.model.TransactionRecord;
import edu.mum.model.VisaTransactionRecord;
import edu.mum.repository.CreditCardRepository;
import edu.mum.repository.CreditCardTransactionRecordRepository;
import edu.mum.service.CreditCardService;
import edu.mum.service.TransactionRecordService;

@Service
@Transactional
public class CreditCardServiceImpl implements CreditCardService {

	@Autowired
	private TransactionRecordService transactionRecordService;
	
	@Autowired
	private CreditCardRepositoryFactory  creditCardRepositoryFactory;
	
	@Autowired
	private CreditCardTransactionRecordRepositoryFactory creditCardTransactionRecordRepositoryFactory;
	
	@Autowired
	private CreditCardTransactionRecordFactory creditCardTransactionRecordFactory;

	public char verifyCreditCard(RequestedCard requestedCard) {
		char ch = ' ';

		String cardType = requestedCard.getCardType();

		if (cardType == null) {
			return 'N';
		} else {
			cardType = cardType.toLowerCase();
		}
		
		CreditCardRepository creditCardRepository = creditCardRepositoryFactory.getCreditCardRepository(cardType);
		ch = verifyHelper(requestedCard, new ArrayList<CreditCard>(creditCardRepository.findByCardNum(requestedCard.getCardNum())));

		return ch;
	}

	public char afterPlaceOrder(RequestedCard requestedCard) {
		char ch = verifyCreditCard(requestedCard);
		String cardType = requestedCard.getCardType().toLowerCase();
		TransactionRecord transactionRecord = null;
		
		transactionRecord = creditCardTransactionRecordFactory.getTransactionRecord(cardType);
		transactionRecord = initTransactionRecord(transactionRecord, requestedCard);

		if (ch == 'N') {
			transactionRecord.setTransactionSuccess(false);
			saveRecord(cardType, transactionRecord);
			return 'N';
		} else {
			List<CreditCard> cards = null;
			
			CreditCardRepository creditCardRepository = creditCardRepositoryFactory.getCreditCardRepository(cardType);
			cards = new ArrayList<CreditCard>(creditCardRepository.findByCardNum(requestedCard.getCardNum()));

			CreditCard card = cards.get(0);
			card.setAvailableCredit(card.getAvailableCredit() - requestedCard.getPurchaseAmount());
			creditCardRepository.save(card);

			transactionRecord.setTransactionSuccess(true);
			saveRecord(cardType, transactionRecord);

			return 'Y';
		}
	}

	private void saveRecord(String cardType, TransactionRecord transactionRecord) {
		
		
		
		if (cardType.equals("visa")) {
			transactionRecordService.saveVisaRecord((VisaTransactionRecord) transactionRecord);
		} else {
			transactionRecordService.saveMasterRecord((MasterTransactionRecord) transactionRecord);
		}
	}

	private TransactionRecord initTransactionRecord(TransactionRecord transactionRecord, RequestedCard requestedCard) {
		String cardType = requestedCard.getCardType().toLowerCase();
		transactionRecord = setTransactionNum(transactionRecord, cardType);
		transactionRecord.setCardHolder(requestedCard.getCardHolder());
		transactionRecord.setCardNum(requestedCard.getCardNum());
		transactionRecord.setDate(new Date());
		transactionRecord.setTransactionAmount(requestedCard.getPurchaseAmount());
		transactionRecord.setStatus(true);
		
		return transactionRecord;		
	}

	private TransactionRecord setTransactionNum(TransactionRecord transactionRecord, String cardType) {
		int totalRecordsCount = 0;
		
		CreditCardTransactionRecordRepository creditCardTransactionRecordRepository = 
				creditCardTransactionRecordRepositoryFactory.getCreditCardTransactionRecordRepository(cardType);
		
		totalRecordsCount = creditCardTransactionRecordRepository.findAllTransactionRecordRepository().size();

		StringBuilder sb = new StringBuilder();
		totalRecordsCount++;

		String totalRecordsCountStr = totalRecordsCount + "";
		int bits = (totalRecordsCountStr).length();
		int restZeroes = 10 - bits;

		while (restZeroes > 0) {
			sb.append("0");
			restZeroes--;
		}

		sb.append(totalRecordsCountStr);
		String transactionNum = cardType + sb.toString();
		transactionRecord.setTransactionNum(transactionNum);

		return transactionRecord;
	}

	private char verifyHelper(RequestedCard requestedCard, List<CreditCard> cards) {

		String cardType = requestedCard.getCardType().toLowerCase();

		if (cards.size() == 1) {
			CreditCard card = cards.get(0);

			String cardHolder = card.getCardHolder();
			String securityCode = card.getSecurityCode();
			boolean status = card.isStatus();
			float availableCredit = card.getAvailableCredit();

			// Check the year and month only.
//			Date expiration = card.getExpiration();
//			YearMonth ymExpiration = convertDateToYearMonth(expiration);
//			YearMonth ymRequestedCardExpiration = convertDateToYearMonth(requestedCard.getExpiration());

			if (!cardHolder.toLowerCase().equals(requestedCard.getCardHolder().toLowerCase())) {
				return 'N';
			} else if (!securityCode.equals(requestedCard.getSecurityCode())) {
				return 'N';
			}else if (!status) {
				return 'N';
			} else if (availableCredit < requestedCard.getPurchaseAmount()) {
				return 'N';
			}
			
//			 else if (ymExpiration.compareTo(ymRequestedCardExpiration) != 0) {
//					return 'N';
//				} 

			return 'Y';
		} else if (cards.size() == 0) {
			return 'N';
		} else {
			return 'N';
		}
	}

	private YearMonth convertDateToYearMonth(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		return YearMonth.from(localDate);
	}
}
