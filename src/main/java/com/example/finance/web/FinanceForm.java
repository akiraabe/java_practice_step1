package com.example.finance.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.util.StringUtils;

/**
 * 
 * 入力画面のFormオブジェクトです。
 * 
 * @author Akira Abe
 *
 */
public class FinanceForm {

	private String principal;

	@NotNull
	@Positive
	private Integer term;

	private String rate;
	private String amount;
	private String fee;
	private String type;

	private List<TypeBean> typeBeanList;

	// 関連チェック結果表示用のフィールド
	private boolean validForFixInterestMethod;
	private boolean validForInterestMethod;
	private boolean validForSumOfDigit;

	/**
	 * Default constructor.
	 */
	public FinanceForm() {
		super();
		createTypeBeanList();
	}

	@AssertTrue(message = "賦金額固定残債計算の場合の必須項目入力誤りがあります")
	public boolean isValidForFixInterestMethod() {

		if ("1".equals(type)) {
			if (StringUtils.isEmpty(principal)) {
				return false;
			}

			try {
				Long.parseLong(principal);
			} catch (NumberFormatException e) {
				return false;
			}

			if (StringUtils.isEmpty(amount)) {
				return false;
			}

			try {
				Long.parseLong(amount);
			} catch (NumberFormatException e) {
				return false;
			}

			if (StringUtils.isEmpty(rate)) {
				return false;
			}
			
			try {
				Double.parseDouble(rate);
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}
	
	@AssertTrue(message = "元本利率指定の場合の必須項目入力誤りがあります")
	public boolean isValidForInterestMethod() {

		if ("2".equals(type)) {
			if (StringUtils.isEmpty(principal)) {
				return false;
			}

			try {
				Long.parseLong(principal);
			} catch (NumberFormatException e) {
				return false;
			}

			if (StringUtils.isEmpty(rate)) {
				return false;
			}
			
			try {
				Double.parseDouble(rate);
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}

	@AssertTrue(message = "七八分法の場合の必須項目入力誤りがあります")
	public boolean isValidForSumOfDigit() {

		if ("3".equals(type)) {
			if (StringUtils.isEmpty(fee)) {
				return false;
			}

			try {
				Long.parseLong(fee);
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}

	private void createTypeBeanList() {
		typeBeanList = new ArrayList<>();
		TypeBean bean1 = new TypeBean("1", "賦金額固定残債計算");
		TypeBean bean2 = new TypeBean("2", "元本利率指定残債計算");
		TypeBean bean3 = new TypeBean("3", "七八分法");
		typeBeanList.add(bean1);
		typeBeanList.add(bean2);
		typeBeanList.add(bean3);
	}

	/**
	 * @return the principal
	 */
	public String getPrincipal() {
		return principal;
	}

	/**
	 * @param principal
	 *            the principal to set
	 */
	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	/**
	 * @return the term
	 */
	public Integer getTerm() {
		return term;
	}

	/**
	 * @param term
	 *            the term to set
	 */
	public void setTerm(Integer term) {
		this.term = term;
	}

	/**
	 * @return the rate
	 */
	public String getRate() {
		return rate;
	}

	/**
	 * @param rate
	 *            the rate to set
	 */
	public void setRate(String rate) {
		this.rate = rate;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the fee
	 */
	public String getFee() {
		return fee;
	}

	/**
	 * @param fee
	 *            the fee to set
	 */
	public void setFee(String fee) {
		this.fee = fee;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the typeBeanList
	 */
	public List<TypeBean> getTypeBeanList() {
		return typeBeanList;
	}

	/**
	 * @param typeBeanList
	 *            the typeBeanList to set
	 */
	public void setTypeBeanList(List<TypeBean> typeBeanList) {
		this.typeBeanList = typeBeanList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FinanceForm [principal=" + principal + ", term=" + term + ", rate=" + rate + ", amount=" + amount
				+ ", fee=" + fee + ", type=" + type + "]";
	}
}
