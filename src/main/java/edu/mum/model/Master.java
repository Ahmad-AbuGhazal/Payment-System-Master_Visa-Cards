package edu.mum.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Master implements CreditCard {

	@Id
	@GeneratedValue
	private long id;

	private String cardHolder;

	private String cardNum;

	private String securityCode;

	private Date expiration;

	private float maxCredit;

	private float availableCredit;

	private boolean status;

	// private Address address;

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

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public float getMaxCredit() {
		return maxCredit;
	}

	public void setMaxCredit(float maxCredit) {
		this.maxCredit = maxCredit;
	}

	public float getAvailableCredit() {
		return availableCredit;
	}

	public void setAvailableCredit(float availableCredit) {
		this.availableCredit = availableCredit;
	}

	
	// public Address getAddress() {
	// return address;
	// }
	//
	// public void setAddress(Address address) {
	// this.address = address;
	// }
}
