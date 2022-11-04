package com.barclays.homeloan.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barclays.homeloan.entity.Loan;
import com.barclays.homeloan.entity.Repayment;
import com.barclays.homeloan.entity.SavingAccount;
import com.barclays.homeloan.repository.LoanRepository;
import com.barclays.homeloan.repository.RepaymentRepository;
import com.barclays.homeloan.repository.SavingRepository;
import com.barclays.homeloan.service.RepaymentService;
import com.barclays.homeloan.utils.EmiManager;

@Service
public class RepaymentServiceImpl implements RepaymentService {

	@Autowired
	private EmiManager emiManager;
	
	@Autowired
	private SavingRepository savingRepository;

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private RepaymentRepository repayRepository;
	
	@Override
	public Repayment getEmiById(int id) {
		Optional<Repayment> repayment = repayRepository.findById(id);

		return repayment.get();
	}

	@Override
	public List<Repayment> getEmiByLoanId(int loan_id) {
		Loan loan = loanRepository.findById(loan_id).get();
		List<Repayment> repayment = repayRepository.findByLoanId(loan);
		return repayment;
	}

	@Override
	public Repayment payEmi(int id) {
		Loan loan = loanRepository.findById(id).get();
		int savingAccountId = loan.getSavingAccount();
		List<Repayment> lst = repayRepository.findByLoanIdAndStatus(loan, "Pending");
		Repayment repay = lst.get(0);

		SavingAccount acc = savingRepository.findById(savingAccountId).get();
		float balance = acc.getBalance();
		float deductAmount = repay.getEmi();
		acc.setBalance(balance - deductAmount);
		repay.setStatus("Paid");
		repayRepository.save(repay);
		savingRepository.save(acc);

		return repay;
	}

	@Override
	public String forPayEmi(int id) {
		Loan loan = loanRepository.findById(id).get();

		int savingAccountId = loan.getSavingAccount();
		List<Repayment> paidList = repayRepository.findByLoanIdAndStatus(loan, "Paid");
		if (paidList.size() < 3) {
			return "Loan foreclosure forbidden !";
		}
		List<Repayment> unPaidEmiList = repayRepository.findByLoanIdAndStatus(loan, "Pending");
		Repayment unPaidEmi = unPaidEmiList.get(0);
		unPaidEmiList.remove(0);
		int outstanding = (int) unPaidEmi.getOutstanding();
		unPaidEmi.setInterestamount(0);
		unPaidEmi.setPrincipalamount(0);
		unPaidEmi.setEmi(outstanding);
		unPaidEmi.setDate(LocalDate.now());
		unPaidEmi.setStatus("Paid");
		repayRepository.save(unPaidEmi);
		for (Repayment r : unPaidEmiList) {
			r.setStatus("Cancelled");
			repayRepository.save(r);
		}
		SavingAccount acc = savingRepository.findById(savingAccountId).get();
		float currBalance = acc.getBalance();
		acc.setBalance(currBalance - outstanding);
		savingRepository.save(acc);
		loan.setStatus("Closed");
		loanRepository.save(loan);

		return "Foreclosed Successfully";
	}

	@Override
	public String prePayEmi(int id, int months) {
		Loan loan = loanRepository.findById(id).get();
		int savingAccountId = loan.getSavingAccount();
		SavingAccount savingAccount = savingRepository.findById(savingAccountId).get();
		List<Repayment> lst = repayRepository.findByLoanIdAndStatus(loan, "Pending");
		Repayment repay = lst.get(0);
		if(lst.size()<months) {
			return "please enter correct tenure in months. The amount of remaining months are: "+lst.size();
		}
		float monthlyEmi = repay.getEmi();
		/*if(lst.size()==months) {
			return "Please enter tenure between 3 and "+String.valueOf(loan.getTenure()-months);
		}*/
		if(months<3 && lst.size()>=3)
		{
			return "please enter tenure between 3 and "+lst.size();
		}
		
		float totalEmi = repay.getEmi()*months;
		float interest = repay.getInterestamount();
		float newOutstanding = repay.getOutstanding()+interest-totalEmi;
		repay.setPrincipalamount(totalEmi-interest);
		repay.setEmi(totalEmi);
		repay.setStatus("Paid");
		repayRepository.save(repay);	
		float balance = savingAccount.getBalance();
		savingAccount.setBalance(balance-totalEmi);
		savingRepository.save(savingAccount);
		
		lst.remove(0);
		int monthsTenure = lst.size();
		float newEmi = emiManager.CalculateEmi(newOutstanding,monthsTenure );
		while(newEmi<0 && monthsTenure>1)
		{
			monthsTenure = monthsTenure-1;
			newEmi = emiManager.CalculateEmi(newOutstanding,monthsTenure);
		}
		
		float rate = EmiManager.INTEREST;
		for(Repayment rp: lst) {
			if(newOutstanding>0)
			{
				interest = newOutstanding * rate;
				float principal = newEmi - interest;
				rp.setEmi(newEmi);
				rp.setInterestamount(interest);
				rp.setPrincipalamount(principal);
				rp.setOutstanding(newOutstanding);
				repayRepository.save(rp);
				newOutstanding = newOutstanding-principal;
			}
			else
			{
				rp.setEmi(0);
				rp.setInterestamount(0);
				rp.setPrincipalamount(0);
				rp.setOutstanding(0);
				rp.setStatus("Cancelled");
				repayRepository.save(rp);
				loan.setStatus("Closed");
				loanRepository.save(loan);
			}
		}

		return "Emi Paid succesfully for "+String.valueOf(months)+" months";
	}

}
