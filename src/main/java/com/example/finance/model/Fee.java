package com.example.finance.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "fee")
public class Fee implements Serializable {

	private static final long serialVersionUID = -3416171104136562971L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** 元本 */
	private Long principal;

	/** 手数料 */
	private Long fee;

	/** 期間（月数） */
	private Integer term;

	/** 金利 */
	private Double rate;

	/** 賦金額 */
	private Long amount;

	/** 計算種類 */
	private String calculationType;

	/** Cashflows */
	@Transient
	private List<CashflowDetail> cashflowDetails;

	/**
	 * 引数を取るコンストラクターです。
	 * 
	 * @param param
	 *            引数のMap
	 * @param calculationType
	 *            計算種類
	 */
	public Fee(Map<String, Object> param, String calculationType) {
		super();
		this.id = 1L;
		this.principal = (Long) param.get("principal");
		this.term = (Integer) param.get("term");
		this.rate = (Double) param.get("rate");
		this.amount = (Long) param.get("amount");
		this.calculationType = calculationType;
		this.cashflowDetails = calculate();
		this.fee = this.cashflowDetails.stream().mapToLong(s -> s.getInterest()).sum();
	}

	/**
	 * Default constructor.
	 */
	public Fee() {
	}
	
	
	//TODO このメソッドは将来不要となります。
	private List<CashflowDetail> calculate() {

		CashflowDetail cd = new CashflowDetail();
		cd.setAmount(100L);
		cd.setBalance(200L);
		cd.setInterest(300L);
		cd.setPrincipal(400L);
		List<CashflowDetail> cds = new ArrayList<>();
		cds.add(cd);
		return cds;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the principal
	 */
	public Long getPrincipal() {
		return principal;
	}

	/**
	 * @param principal
	 *            the principal to set
	 */
	public void setPrincipal(Long principal) {
		this.principal = principal;
	}

	/**
	 * @return the fee
	 */
	public Long getFee() {
		return fee;
	}

	/**
	 * @param fee
	 *            the fee to set
	 */
	public void setFee(Long fee) {
		this.fee = fee;
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
	public Double getRate() {
		return rate;
	}

	/**
	 * @param rate
	 *            the rate to set
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}

	/**
	 * @return the calculationType
	 */
	public String getCalculationType() {
		return calculationType;
	}

	/**
	 * @param calculationType
	 *            the calculationType to set
	 */
	public void setCalculationType(String calculationType) {
		this.calculationType = calculationType;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	/**
	 * @return the cashflowDetails
	 */
	public List<CashflowDetail> getCashflowDetails() {
		if (cashflowDetails != null) {
			return cashflowDetails;
		} else {
			this.cashflowDetails = calculate();
		}
		return cashflowDetails;
	}

	/**
	 * @param cashflowDetails
	 *            the cashflowDetails to set
	 */
	public void setCashflowDetails(List<CashflowDetail> cashflowDetails) {
		this.cashflowDetails = cashflowDetails;
	}

}
