package com.barclays.homeloan.service;

import java.util.List;

import com.barclays.homeloan.entity.Repayment;

public interface RepaymentService {
	
	public Repayment getEmiById(int id);
	public List<Repayment> getEmiByLoanId(int loan_id);
	public Repayment payEmi(int id);
	public String forPayEmi(int id);
	public String prePayEmi(int id, int months);
	
}
