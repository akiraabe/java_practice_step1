package com.example.finance.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.example.finance.web.FinanceForm;

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

	/** Strategy */
	@Transient
	private FeeCalculationStrategy strategy;


	/**
	 * Default constructor.
	 */
	public Fee() {
	}

	/**
	 * 引数を取るコンストラクターです。
	 * @param form 入力画面
	 */
	public Fee(FinanceForm form) {
		super();
		this.calculationType = form.getType();
		switch (calculationType) {
		case ("1"):
			this.principal = Long.parseLong(form.getPrincipal());
			this.term = form.getTerm();
			this.rate = Double.parseDouble(form.getRate());
			this.amount = Long.parseLong(form.getAmount());
			this.strategy = new FixInterestMethodFee();
			this.cashflowDetails = strategy.calculate(this);
			this.fee = this.cashflowDetails.stream().mapToLong(s -> s.getInterest()).sum();
			break;

		case ("2"):
			this.principal = Long.parseLong(form.getPrincipal());
			this.term = form.getTerm();
			this.rate = Double.parseDouble(form.getRate());
			this.strategy = new InterestMethodFee();
			this.amount = strategy.payAmount(this);
			this.cashflowDetails = strategy.calculate(this);
			this.fee = this.cashflowDetails.stream().mapToLong(s -> s.getInterest()).sum();
			break;

		case ("3"):
			this.term = form.getTerm();
			this.fee = Long.parseLong(form.getFee());
			this.strategy = new SumOfDigitFee();
			this.cashflowDetails = strategy.calculate(this);
			break;
		}
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
			switch (calculationType) {
			case "1":
				this.strategy = new FixInterestMethodFee();
				this.cashflowDetails = strategy.calculate(this);
				break;
			case "2":
				this.strategy = new InterestMethodFee(); // StrategyPattern
				this.cashflowDetails = strategy.calculate(this);
				break;
			case "3":
				this.strategy = new SumOfDigitFee(); // StrategyPattern
				this.cashflowDetails = strategy.calculate(this);
				break;
			default:
				System.out.println("error"); // ここには到達しないはず。
			}	
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
