package edu.mum.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mum.model.CardType;
import edu.mum.model.CreditCard;
import edu.mum.model.Master;
import edu.mum.model.RequestedCard;
import edu.mum.model.Visa;
import edu.mum.repository.MasterRepository;
import edu.mum.repository.VisaRepository;
import edu.mum.service.CreditCardService;
import edu.mum.utils.PaymentUtils;

@Service
@Transactional
public class CreditCardServiceImpl implements CreditCardService {

	@Autowired
	private VisaRepository visaRepository;

	@Autowired
	private MasterRepository masterRepository;

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
				if (!PaymentUtils.CheckValidNumber(requestedCard.getCardNum())) {
					ch = 'N';
				} else {
					if (CardType.detect(requestedCard.getCardNum()) == CardType.VISA) {
						Visa visa = visaRepository.findByCardNum(requestedCard.getCardNum()).get(0);
						if (visa == null) {
							ch = 'N';
						} else {
							if (!verifyCard(visa, requestedCard))
								ch = 'N';
							else {
								ch = 'Y';
							}
						}
					} else {
						if (CardType.detect(requestedCard.getCardNum()) == CardType.MASTERCARD) {
							Master master = masterRepository.findByCardNum(requestedCard.getCardNum()).get(0);
							if (master == null) {
								ch = 'N';
							} else {
								if (!verifyCard(master, requestedCard))
									ch = 'N';
								else {
									ch = 'Y';
								}
							}
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
			if (CardType.detect(requestedCard.getCardNum()) == CardType.VISA) {
				Visa visa = visaRepository.findByCardNum(requestedCard.getCardNum()).get(0);

				if (visa.getAvailableCredit() < requestedCard.getPurchaseAmount()) {
					return 'N';
				} else {
					visa.setAvailableCredit(visa.getAvailableCredit() - requestedCard.getPurchaseAmount());

					// update the table
					visaRepository.save(visa);
					return 'Y';
				}
			} else if (CardType.detect(requestedCard.getCardNum()) == CardType.MASTERCARD) {
				Master master = masterRepository.findByCardNum(requestedCard.getCardNum()).get(0);

				master.setAvailableCredit(master.getAvailableCredit() - requestedCard.getPurchaseAmount());
				if (master.getAvailableCredit() < requestedCard.getPurchaseAmount()) {
					return 'N';
				} else {
					master.setAvailableCredit(master.getAvailableCredit() - requestedCard.getPurchaseAmount());
					masterRepository.save(master);
					return 'Y';
				}
			} else
				return 'N';

		}
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
