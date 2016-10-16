package edu.mum.model;

import java.util.Date;

public interface TransactionRecord {
	public String getTransactionNum();

	public void setTransactionNum(String transactionNum);

	public String getCardHolder();

	public void setCardHolder(String cardHolder);

	public String getCardNum();

	public void setCardNum(String cardNum);

	public Date getDate();

	public void setDate(Date date);

	public float getTransactionAmount();

	public void setTransactionAmount(float transactionAmount);

	public boolean isTransactionSuccess();

	public void setTransactionSuccess(boolean transactionSuccess);

	public boolean isStatus();

	public void setStatus(boolean status);
}
