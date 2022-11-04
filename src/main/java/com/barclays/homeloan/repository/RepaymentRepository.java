package com.barclays.homeloan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barclays.homeloan.entity.Loan;
import com.barclays.homeloan.entity.Repayment;


public interface RepaymentRepository extends JpaRepository<Repayment,Integer> {

//	@Query(value = "select * from savingaccount s where s.email = ?1", nativeQuery = true)
	
	
	List<Repayment> findByLoanIdAndStatus(Loan loanId, String status);
	List<Repayment> findByLoanId(Loan loan_id);
	
}
