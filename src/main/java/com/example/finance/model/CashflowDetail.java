package com.example.finance.model;

/**
 * 
 * 返済明細のデータクラスです。
 * 
 * @author Akira Abe
 *
 */
public class CashflowDetail {
	
	/** 利息 */
	private Long interest;
	
	/** 元本 */
	private Long principal;
	
	/** 賦金額 */
	private Long amount;
	
	/** 元本残  */
	private Long balance;

	/**
	 * @return the interest
	 */
	public Long getInterest() {
		return interest;
	}

	/**
	 * @param interest the interest to set
	 */
	public void setInterest(Long interest) {
		this.interest = interest;
	}

	/**
	 * @return the principal
	 */
	public Long getPrincipal() {
		return principal;
	}

	/**
	 * @param principal the principal to set
	 */
	public void setPrincipal(Long principal) {
		this.principal = principal;
	}

	/**
	 * @return the amount
	 */
	public Long getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Long amount) {
		this.amount = amount;
	}

	/**
	 * @return the balance
	 */
	public Long getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(Long balance) {
		this.balance = balance;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CashflowDetail [interest=" + interest + ", principal=" + principal + ", amount=" + amount + ", balance="
				+ balance + "]";
	}
}
