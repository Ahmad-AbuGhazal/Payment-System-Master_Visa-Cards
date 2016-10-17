package edu.mum.utils;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mum.model.CreditCard;
import edu.mum.model.RequestedCard;
import edu.mum.model.TransactionRecord;

public class CreditCardUtils {
//	public static char verifyHelper(RequestedCard requestedCard, List<CreditCard> cards) {
//
////		String cardType = requestedCard.getCardType().toLowerCase();
//
//		if (cards.size() == 1) {
//			CreditCard card = cards.get(0);
//
//			String cardHolder = card.getCardHolder();
//			String securityCode = card.getSecurityCode();
//			boolean status = card.isStatus();
//			float availableCredit = card.getAvailableCredit();
//
//			// Check the year and month only.
//			Date expiration = card.getExpiration();
//			YearMonth ymExpiration = convertDateToYearMonth(expiration);
//			YearMonth ymRequestedCardExpiration = convertDateToYearMonth(requestedCard.getExpiration());
//
//			if (!cardHolder.toLowerCase().equals(requestedCard.getCardHolder().toLowerCase())) {
//				return 'N';
//			} else if (!securityCode.equals(requestedCard.getSecurityCode())) {
//				return 'N';
//			} else if (!status) {
//				return 'N';
//			} else if (availableCredit < requestedCard.getPurchaseAmount()) {
//				return 'N';
//			}
//			
////			else if (ymExpiration.compareTo(ymRequestedCardExpiration) != 0) {
////				return 'N';
////			} 
//
//			return 'Y';
//		} else if (cards.size() == 0) {
//			return 'N';
//		} else {
//			return 'N';
//		}
//	}
	
	public static TransactionRecord setTransactionNum(TransactionRecord transactionRecord, String cardType, int totalRecordsCount) {
		
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
	
	private static YearMonth convertDateToYearMonth(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		return YearMonth.from(localDate);
	}
}
