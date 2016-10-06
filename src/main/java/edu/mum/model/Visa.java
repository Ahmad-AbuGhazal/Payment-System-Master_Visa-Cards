package edu.mum.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Visa {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String cardHoler;
	
	private String cardNum;
	
	private String securityCode;
	
	private Date expiration;
	
	private boolean status;
	
	private Address address;

	public String getCardHoler() {
		return cardHoler;
	}

	public void setCardHoler(String cardHoler) {
		this.cardHoler = cardHoler;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
