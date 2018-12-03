package com.example.finance.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.example.finance.web.FinanceForm;

/**
 * 
 * 収益計算のドメインロジックのテストです。
 * 
 * @author Akira Abe
 *
 */
public class FeeTest {
	
	@Test
	public void testFixInterestMethodFee() {

		FinanceForm form = new FinanceForm();
		form.setPrincipal("10000");
		form.setAmount("500");
		form.setTerm(12);
		form.setRate("4.5");
		form.setType("1");
		
		Fee fee = new Fee(form);

		List<CashflowDetail> cashflowDetails = getCashflowDetail(fee);
		assertThat(cashflowDetails.size(), is(12));

		CashflowDetail firstRecrod = getFirstRecord(cashflowDetails);
		assertThat(firstRecrod.getInterest(), is(37L));
		assertThat(firstRecrod.getPrincipal(), is(463L));
		assertThat(firstRecrod.getAmount(), is(500L));
		assertThat(firstRecrod.getBalance(), is(9537L));
		

		CashflowDetail lastRecrod = getLastRecord(cashflowDetails);
		assertThat(lastRecrod.getInterest(), is(18L));
		assertThat(lastRecrod.getPrincipal(), is(482L));
		assertThat(lastRecrod.getAmount(), is(500L));
		assertThat(lastRecrod.getBalance(), is(4328L));
		
	}
	
	@Test
	public void testInterestMethodFee() {

		FinanceForm form = new FinanceForm();
		form.setPrincipal("10000");
		form.setTerm(12);
		form.setRate("4.5");
		form.setType("2");
		
		Fee fee = new Fee(form);

		List<CashflowDetail> cashflowDetails = getCashflowDetail(fee);
		assertThat(cashflowDetails.size(), is(12));

		CashflowDetail firstRecrod = getFirstRecord(cashflowDetails);
		assertThat(firstRecrod.getInterest(), is(37L));
		assertThat(firstRecrod.getPrincipal(), is(816L));
		assertThat(firstRecrod.getAmount(), is(853L));
		assertThat(firstRecrod.getBalance(), is(9184L));
		

		CashflowDetail lastRecrod = getLastRecord(cashflowDetails);
		assertThat(lastRecrod.getInterest(), is(3L));
		assertThat(lastRecrod.getPrincipal(), is(854L));
		assertThat(lastRecrod.getAmount(), is(857L));
		assertThat(lastRecrod.getBalance(), is(0L));
		
	}
	
	@Test
	public void testSumOfDigitFee() {

		
		FinanceForm form = new FinanceForm();
		form.setFee("10000");
		form.setTerm(12);
		form.setType("3");

		Fee fee = new Fee(form);

		List<CashflowDetail> cashflowDetails = getCashflowDetail(fee);
		assertThat(cashflowDetails.size(), is(12));

		CashflowDetail firstRecrod = getFirstRecord(cashflowDetails);
		assertThat(firstRecrod.getInterest(), is(1538L));
		

		CashflowDetail lastRecrod = getLastRecord(cashflowDetails);
		assertThat(lastRecrod.getInterest(), is(133L));
		
	}

	
	private List<CashflowDetail> getCashflowDetail(Fee fee) {
		return fee.getCashflowDetails();
	}

	private CashflowDetail getFirstRecord(List<CashflowDetail> cashflowDetails) {
		return cashflowDetails.get(0);
	}
	
	private CashflowDetail getLastRecord(List<CashflowDetail> cashflowDetails) {
		return cashflowDetails.get(cashflowDetails.size()-1);
	}

}
