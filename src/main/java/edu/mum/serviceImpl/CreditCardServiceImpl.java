package edu.mum.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mum.model.CreditCard;
import edu.mum.model.Master;
import edu.mum.model.RequestedCard;
import edu.mum.model.Visa;
import edu.mum.repository.MasterRepository;
import edu.mum.repository.VisaRepository;
import edu.mum.service.CreditCardService;

@Service
@Transactional
public class CreditCardServiceImpl implements CreditCardService {

	@Autowired
	private VisaRepository visaRepository;

	@Autowired
	private MasterRepository masterRepository;

	@Override
	public char verifyCreditCard(RequestedCard requestedCard) {
		char ch = ' ';

		String cardType = requestedCard.getCardType();

		if (cardType == null) {
			return 'N';
		} else {
			cardType = cardType.toLowerCase();
		}

		// if(cardType.equals("visa")){
		// List<Visa> visaCards =
		// visaRepository.findByCardNum(requestedCard.getCardNum());
		// ch = verifyHelper(requestedCard, new
		// ArrayList<CreditCard>(visaCards));
		// }else if(cardType.equals("master")){
		// List<Master> masterCards =
		// masterRepository.findByCardNum(requestedCard.getCardNum());
		// ch = verifyHelper(requestedCard, new
		// ArrayList<CreditCard>(masterCards));
		// }else{
		// ch = 'N';
		// }

		switch (cardType) {
		case "visa":
			List<Visa> visaCards = visaRepository.findByCardNum(requestedCard.getCardNum());
			ch = verifyHelper(requestedCard, new ArrayList<CreditCard>(visaCards));
			break;

		case "master":
			List<Master> masterCards = masterRepository.findByCardNum(requestedCard.getCardNum());
			ch = verifyHelper(requestedCard, new ArrayList<CreditCard>(masterCards));
			break;

		default:
			ch = 'N';
		}

		return ch;
	}

	public char afterPlaceOrder(RequestedCard requestedCard) {
		char ch = verifyCreditCard(requestedCard);

		if (ch == 'N') {
			return 'N';
		} else {
			String cardType = requestedCard.getCardType().toLowerCase();
			List<CreditCard> cards = null;

			switch (cardType) {
			case "visa":
				List<Visa> visaCards = visaRepository.findByCardNum(requestedCard.getCardNum());
				cards = new ArrayList<CreditCard>(visaCards);
				break;

			case "master":
				List<Master> masterCards = masterRepository.findByCardNum(requestedCard.getCardNum());
				cards = new ArrayList<CreditCard>(masterCards);
				break;

			default:
				cards = new ArrayList<CreditCard>();
				return 'N';
			}

			CreditCard card = cards.get(0);
			card.setAvailableCredit(card.getAvailableCredit() - requestedCard.getPurchaseAmount());

			if (cardType.equals("visa")) {
				visaRepository.save((Visa) card);
			} else if (cardType.equals("master")) {
				masterRepository.save((Master) card);
			}

			return 'Y';
		}
	}

	private static char verifyHelper(RequestedCard requestedCard, List<CreditCard> cards) {

		String cardType = requestedCard.getCardType().toLowerCase();

		if (cards.size() == 1) {
			CreditCard card = cards.get(0);

			String cardHolder = card.getCardHolder();
			String securityCode = card.getSecurityCode();
			Date expiration = card.getExpiration();
			boolean status = card.isStatus();
			float availableCredit = card.getAvailableCredit();

			if (!cardHolder.toLowerCase().equals(requestedCard.getCardHolder().toLowerCase())) {
				return 'N';
			} else if (!securityCode.equals(requestedCard.getSecurityCode())) {
				return 'N';
			} else if (expiration.compareTo(requestedCard.getExpiration()) != 0) {
				return 'N';
			} else if (!status) {
				return 'N';
			} else if (availableCredit < requestedCard.getPurchaseAmount()) {
				return 'N';
			}

			return 'Y';
		} else if (cards.size() == 0) {
			return 'N';
		} else {
			return 'N';
		}
	}

	public static void main(String[] args) {
		CreditCardServiceImpl ccs = new CreditCardServiceImpl();

		RequestedCard rc1 = new RequestedCard();
		rc1.setCardNum("123456");
		rc1.setCardHolder("Lin");
		rc1.setCardType("visa");
		rc1.setSecurityCode("123");
		rc1.setExpiration(new Date(1111111111));
		rc1.setPurchaseAmount(1000.00f);

		RequestedCard rc2 = new RequestedCard();
		rc2.setCardNum("789012");
		rc2.setCardHolder("Weiwei");
		rc2.setCardType("master");
		rc2.setSecurityCode("456");
		rc2.setExpiration(new Date(1111111111));
		rc2.setPurchaseAmount(2000.00f);

		Visa v = new Visa();
		v.setCardNum("123456");
		v.setCardHolder("Lin");
		v.setExpiration(new Date(1111111111));
		v.setSecurityCode("123");
		v.setStatus(true);
		v.setAvailableCredit(500.00f);

		Master m = new Master();
		m.setCardNum("789012");
		m.setCardHolder("Weiwei");
		m.setExpiration(new Date(1111111111));
		m.setSecurityCode("456");
		m.setStatus(true);
		m.setAvailableCredit(3000.00f);

		List<CreditCard> cards = new ArrayList<CreditCard>();
		cards.add(m);

		System.out.println(verifyHelper(rc2, cards));

		ccs.afterPlaceOrder(rc2);
		// System.out.println(m.getAvailableCredit());
	}

	// private char verifyMasterHelper(RequestedCard requestedCard) {
	//
	// }
}
