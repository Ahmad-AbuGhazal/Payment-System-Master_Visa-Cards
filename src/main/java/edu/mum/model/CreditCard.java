package edu.mum.model;

import java.util.Date;

public interface CreditCard {
	public String getCardHolder();

	public void setCardHolder(String cardHolder);

	public String getCardNum();

	public void setCardNum(String cardNum);

	public String getSecurityCode();

	public void setSecurityCode(String securityCode);

	public Date getExpiration();

	public void setExpiration(Date expiration);

	public boolean isStatus();

	public void setStatus(boolean status);

	public float getMaxCredit();

	public void setMaxCredit(float maxCredit);

	public float getAvailableCredit();

	public void setAvailableCredit(float availableCredit);
}
