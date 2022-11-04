package com.barclays.homeloan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.barclays.homeloan.repository.LoanRepository;
import com.barclays.homeloan.service.LoanService;

@RestController
public class LoanController {
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	
	@Autowired
	LoanRepository loanRepository;
	
	@Autowired
	LoanService loanService;
	
	
	

}
