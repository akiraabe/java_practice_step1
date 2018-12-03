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

import com.example.finance.model.CashflowDetail;

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
		// TODO とりあえず、元本、期間、利率指定の元利均等にのみ対応します。
		this.principal = (Long) param.get("principal");
		this.term = (Integer) param.get("term");
		this.rate = (Double) param.get("rate");
		this.amount = (Long) amount();
		this.calculationType = calculationType;
		this.cashflowDetails = calculate();
		this.fee = this.cashflowDetails.stream().mapToLong(s -> s.getInterest()).sum();
	}

	/**
	 * Default constructor.
	 */
	public Fee() {
	}

	/**
	 * 賦金額を計算します。
	 * 
	 * @return 賦金額
	 */
	private Long amount() {
		Double monthlyRate = this.getRate() / 1200;
		Double paymentRate = monthlyRate * Math.pow((1 + monthlyRate), this.getTerm())
				/ (Math.pow((1 + monthlyRate), this.getTerm()) - 1);
		return (long) Math.floor(this.getPrincipal() * paymentRate);
	}

	/**
	 * 返済明細の展開をします。
	 * @return 返済明細
	 */
	private List<CashflowDetail> calculate() {

        Long amount = this.amount;
		// returnするオブジェクトを生成します。
		List<CashflowDetail> result = new ArrayList<>();

		// 期間分ループしながら残債計算をします。
		Long balance = this.getPrincipal();
		for (int i = 0; i < this.getTerm(); i++) {
			CashflowDetail detail = new CashflowDetail();
			boolean isEndOfList = (i == (this.getTerm()- 1));
			if (isEndOfList) {
				detail.setInterest((long) (balance * this.getRate()/ 1200));
				detail.setPrincipal(balance);
				detail.setAmount(detail.getInterest() + detail.getPrincipal());
				detail.setBalance(0L);
				result.add(detail);
				break;
			}
			detail.setInterest((long) (balance * this.getRate()/ 1200));
			detail.setAmount(amount);
			detail.setPrincipal(amount - detail.getInterest());
			balance -= detail.getPrincipal();
			detail.setBalance(balance);
			result.add(detail);
		}

		// 値をreturnします。
		return result;
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
