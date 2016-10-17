package edu.mum.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mum.model.CardType;
import edu.mum.model.CreditCard;
import edu.mum.model.RequestedCard;
import edu.mum.model.TransactionRecord;
import edu.mum.repository.CreditCardRepository;
import edu.mum.repository.CreditCardTransactionRecordRepository;
import edu.mum.service.CreditCardService;
import edu.mum.service.TransactionRecordService;
import edu.mum.utils.PaymentUtils;

@Service
@Transactional
public class CreditCardServiceImpl implements CreditCardService {

	@Autowired
	private CreditCardRepositoryFactory creditCardRepositoryFactory;

	@Autowired
	private CreditCardTransactionRecordFactory creditCardTransactionRecordFactory;

	@Autowired
	private CreditCardTransactionRecordRepositoryFactory creditCardTransactionRecordRepositoryFactory;

	@Autowired
	private TransactionRecordService transactionRecordService;

	@Override
	public char verifyCreditCard(RequestedCard requestedCard) {
		char ch = 'N';

		if (requestedCard == null)
			ch = 'N';
		else {
			boolean isNumber = PaymentUtils.isNumeric(requestedCard.getCardNum());
			if (!isNumber) {
				ch = 'N';
			} else {
				CardType cardType = CardType.detect(requestedCard.getCardNum());
				if (!PaymentUtils.CheckValidNumber(requestedCard.getCardNum())) {
					ch = 'N';
				} else {
					CreditCardRepository creditCardRepository = creditCardRepositoryFactory
							.getCreditCardRepository(cardType);
					if (creditCardRepository == null) {
						return 'N';
					}

					CreditCard creditCard = creditCardRepository.findByCardNum(requestedCard.getCardNum()).get(0);

					if (creditCard == null) {
						ch = 'N';
					} else {
						if (!verifyCard(creditCard, requestedCard))
							ch = 'N';
						else {
							ch = 'Y';
						}
					}
				}
			}
		}

		return ch;
	}

	public char afterPlaceOrder(RequestedCard requestedCard) {
		char ch = verifyCreditCard(requestedCard);

		if (ch == 'N') {
			return 'N';
		} else {
			TransactionRecord transactionRecord = null;
			CardType cardType = CardType.detect(requestedCard.getCardNum());
			transactionRecord = creditCardTransactionRecordFactory
					.getTransactionRecord(CardType.detect(requestedCard.getCardNum()));
			transactionRecord = initTransactionRecord(transactionRecord, requestedCard);

			CreditCardRepository creditCardRepository = creditCardRepositoryFactory.getCreditCardRepository(cardType);
			if (creditCardRepository == null) {
				return 'N';
			}

			CreditCard creditCard = creditCardRepository.findByCardNum(requestedCard.getCardNum()).get(0);

			if (creditCard.getAvailableCredit() < requestedCard.getPurchaseAmount()) {
				transactionRecord.setTransactionSuccess(false);
				saveRecord(cardType, transactionRecord);
				return 'N';
			} else {
				creditCard.setAvailableCredit(creditCard.getAvailableCredit() - requestedCard.getPurchaseAmount());

				// update the table
				creditCardRepository.save(creditCard);

				transactionRecord.setTransactionSuccess(true);
				saveRecord(cardType, transactionRecord);

				return 'Y';
			}
		}
	}

	private TransactionRecord initTransactionRecord(TransactionRecord transactionRecord, RequestedCard requestedCard) {
		CardType cardType = CardType.detect(requestedCard.getCardNum());
		transactionRecord = setTransactionNum(transactionRecord, cardType);
		transactionRecord.setCardHolder(requestedCard.getCardHolder());
		transactionRecord.setCardNum(requestedCard.getCardNum());
		transactionRecord.setDate(new Date());
		transactionRecord.setTransactionAmount(requestedCard.getPurchaseAmount());
		transactionRecord.setStatus(true);

		return transactionRecord;
	}

	private TransactionRecord setTransactionNum(TransactionRecord transactionRecord, CardType cardType) {
		int totalRecordsCount = 0;

		CreditCardTransactionRecordRepository creditCardTransactionRecordRepository = creditCardTransactionRecordRepositoryFactory
				.getCreditCardTransactionRecordRepository(cardType);

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

	private void saveRecord(CardType cardType, TransactionRecord transactionRecord) {
		transactionRecordService.saveCreditCardRecord(cardType, transactionRecord);
	}

	private static final boolean verifyCard(CreditCard creditCard, RequestedCard requestedCard) {
		if (!creditCard.getCardHolder().toLowerCase().equals(requestedCard.getCardHolder().toLowerCase())) {
			return false;
		} else if (!creditCard.getSecurityCode().equals(requestedCard.getSecurityCode())) {
			return false;
		} else {
			SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");
			format.format(creditCard.getExpiration());
			format.format(requestedCard.getExpiration());

			Calendar calenderCard = Calendar.getInstance();
			Calendar calenderRequested = Calendar.getInstance();

			calenderCard.setTime(creditCard.getExpiration());
			calenderRequested.setTime(requestedCard.getExpiration());

			if (!((calenderCard.YEAR == calenderRequested.YEAR) && (calenderCard.MONTH == calenderRequested.MONTH))) {
				return false;
			} else
				return true;
		}
	}

}
