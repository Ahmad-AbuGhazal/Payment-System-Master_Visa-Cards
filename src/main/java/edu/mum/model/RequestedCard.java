package edu.mum.model;

import java.util.Date;

/**
 * Class for @RequestBody
 * @author Lin
 *
 */

public class RequestedCard {
	
	private String cardType;
	
	private String cardHolder;

	private String cardNum;

	private String securityCode;

	private Date expiration;

	private float purchaseAmount;

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date date) {
		this.expiration = date;
	}

	public float getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(float purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}
}
