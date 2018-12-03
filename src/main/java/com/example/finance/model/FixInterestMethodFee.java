package com.example.finance.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 賦金額固定型の元利均等計算を行います。
 * 
 * @author Akira Abe
 *
 */
public class FixInterestMethodFee implements FeeCalculationStrategy {

	@Override
	public List<CashflowDetail> calculate(Fee fee) {
		// returnするオブジェクトを生成します。
		List<CashflowDetail> result = new ArrayList<>();

		// 期間分ループしながら残債計算をします。
		Long balance = fee.getPrincipal();
		for (int i = 0; i < fee.getTerm(); i++) {
			CashflowDetail detail = new CashflowDetail();
			detail.setInterest((long) (balance * fee.getRate() / 1200));
			detail.setAmount(fee.getAmount());
			detail.setPrincipal(fee.getAmount() - detail.getInterest());
			balance -= detail.getPrincipal();
			detail.setBalance(balance);
			result.add(detail);
		}

		// 値をreturnします。
		return result;
	}

	@Override
	public Long payAmount(Fee fee) {
		return fee.getAmount();
	}

}