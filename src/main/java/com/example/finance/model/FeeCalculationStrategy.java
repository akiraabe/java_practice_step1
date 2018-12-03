package com.example.finance.model;

import java.util.List;

/**
 * 
 * 収益計算のStrategyのインタフェースです。
 * @author Akira Abe
 *
 */
public interface FeeCalculationStrategy {

	/**
	 * 明細展開を行います。
	 * @param fee 収益
	 * @return 返済明細
	 */
	public abstract List<CashflowDetail> calculate(Fee fee);

	/**
	 * 賦金額を算出します。
	 * @param fee 収益
	 * @return 賦金額
	 */
	public abstract Long payAmount(Fee fee);
	
}
