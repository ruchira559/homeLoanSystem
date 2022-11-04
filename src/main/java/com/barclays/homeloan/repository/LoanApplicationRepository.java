package com.barclays.homeloan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barclays.homeloan.entity.LoanApplication;



public interface LoanApplicationRepository extends JpaRepository<LoanApplication,Integer> {

}
