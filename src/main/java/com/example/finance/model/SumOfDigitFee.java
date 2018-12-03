package com.example.finance.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 七八分法による収益を表現するドメインクラスです。
 * 
 * @author Akira Abe
 *
 */
public class SumOfDigitFee implements FeeCalculationStrategy {

	/**
	 * 七八分法の計算を行い金額のListを返却します。
	 * 
	 * @return 金額のリスト
	 */
	@Override
	public List<CashflowDetail> calculate(Fee fee) {
		// 総和を求める
		Integer sum = (fee.getTerm() + 1) * fee.getTerm()/ 2;

		// Feeを按分する
		List<Long> result = new ArrayList<>();
		for (int height = fee.getTerm(); height > 0; height--) {

			// 最終回の調整
			if (height == 1) {
				result.add(fee.getFee() - result.stream().mapToLong(x -> x).sum());
				break;
			}

			result.add(fee.getFee() * height / sum);
		}

		return convert(result);
	}
	
	private List<CashflowDetail> convert(List<Long> result) {

		List<CashflowDetail> details = new ArrayList<>();
		for (Long fee : result) {
			CashflowDetail detail = new CashflowDetail();
			detail.setInterest(fee);
			details.add(detail);
		}
		return details;
	}

	@Override
	public Long payAmount(Fee fee) {
		return null;
	}
}
