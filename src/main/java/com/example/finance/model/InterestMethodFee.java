package com.example.finance.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 元利均等の計算を行います。
 * 
 * @author Akira Abe
 *
 */
public class InterestMethodFee implements FeeCalculationStrategy {

	/**
	 * 元利均等の残債計算を行います。
	 * 
	 * @return キャッシュフローのList
	 */
	@Override
	public List<CashflowDetail> calculate(Fee fee) {
		
		Long amount = payAmount(fee);
		
		// returnするオブジェクトを生成します。
		List<CashflowDetail> result = new ArrayList<>();

		// 期間分ループしながら残債計算をします。
		Long balance = fee.getPrincipal();
		for (int i = 0; i < fee.getTerm(); i++) {
			CashflowDetail detail = new CashflowDetail();
			boolean isEndOfList = (i == (fee.getTerm()- 1));
			if (isEndOfList) {
				detail.setInterest((long) (balance * fee.getRate()/ 1200));
				detail.setPrincipal(balance);
				detail.setAmount(detail.getInterest() + detail.getPrincipal());
				detail.setBalance(0L);
				result.add(detail);
				break;
			}
			detail.setInterest((long) (balance * fee.getRate()/ 1200));
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
	 * 賦金額（元利合計額）を算出します。
	 * 
	 * @return 賦金額
	 */
	@Override
	public Long payAmount(Fee fee) {
		Double monthlyRate = fee.getRate() / 1200;
		Double paymentRate = monthlyRate * Math.pow((1 + monthlyRate), fee.getTerm())
				/ (Math.pow((1 + monthlyRate), fee.getTerm()) - 1);
		return (long) Math.floor(fee.getPrincipal() * paymentRate);
	}

}
